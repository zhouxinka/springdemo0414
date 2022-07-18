package com.example.controller;

import com.example.dataSource.DynamicDataSource;
import com.example.entity.User;
import com.example.exception.MyException;
import com.example.service.UserService;
import com.example.utils.Global;
import com.example.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;

/**
 * @author zhoupeng
 * @create time 2021-04-14-10:28
 */
@Controller
@RequestMapping("${adminPath}")
public class UserController extends BaseController {
    //这里使用接口指向子类实现（不建议使用UserServiceImpl userServiceImpl）
    //从而解决事务中的动态代理出错的问题
    @Autowired
    private UserService userServiceImpl;

    @ModelAttribute
    public User get(@RequestParam(required = false) String id,HttpServletRequest request){
        System.out.println("UserController里面的ModelAttribute注解的方法里面的id:"+id);
        User user = null;
        if(StringUtils.isNotBlank(id)){
            user = userServiceImpl.getUserById(Integer.parseInt(id));
        }
        if(user==null){
            user = new User();
        }
        System.out.println("UserController里面的ModelAttribute注解的方法里面的user:"+user.toString());
        //获取servletContext
        ServletContext servletContext = request.getSession().getServletContext();
        //将servletContext的所有属性打印出来
        Enumeration<String> attributeNames = servletContext.getAttributeNames();
        while(attributeNames.hasMoreElements()){
            String s = attributeNames.nextElement();
            System.out.println("servletContext的属性:"+s);
        }
        //获取子容器（spring mvc的容器）
        WebApplicationContext webApplicationContext = (WebApplicationContext) servletContext.getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.springMVC");
        String applicationName = webApplicationContext.getApplicationName();
        System.out.println("applicationName:"+applicationName);
        //子容器中获取UserController的bean
        UserController bean = webApplicationContext.getBean(UserController.class);
        System.out.println(bean.toString());
        return user;
    }

    /**
     * 前端输入/a/login，跳转到登录页面
     * @return
     */
    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 登录过程。登录成功则跳转到首页，否则跳转到登录页。
     *@RequestParam(value="password")
     * 这个注解是解决前端参数名字跟形参不一致问题
     * 将前端参数”password“与形参”psd“进行绑定
     */
    @RequestMapping(value="/doLogin")
    public String doLogin(String username,@RequestParam(value="password") String psd, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        System.out.println("UserController里面的doLogin方法的username:"+username);
        System.out.println("UserController里面的doLogin方法的psd:"+psd);
        User user = userServiceImpl.getUserByName(username);
        if(user==null){
            //增加flash信息提示用户名或者密码错误，login页面也要增加sys:messages标签
            addMessage(redirectAttributes,"登陆失败，用户名或密码错误！！！");
            return "redirect:"+Global.getAdminPath()+"/login";
        }
        //将当前登录的用户存入session
        HttpSession session = request.getSession();
        session.setAttribute("CURRENT_USER",user);
        return "index";
    }

    /**
     * 前端输入/a/userInfo，跳转到查看所有用户的页面
     * @return
     */
    @RequestMapping(value="/userInfo")
    public String userInfo(){
        return "userInfo";
    }

    /**
     * 前端发送ajax,获取所有用户，返回json数据
     * @param user
     * @return
     */
    @RequestMapping(value="/findAllUser")
    @ResponseBody
    public List<User> findAllUser(User user){
        List<User> allUser = userServiceImpl.findAllUser(user);
        return allUser;
    }
    /**
     * 通过ID获取用户
     * @return
     */
    @RequestMapping(value="/getUserById")
    @ResponseBody
    public User getUserById(){
        User user = userServiceImpl.getUserById(3);
        return user;
    }
    /**
     * 增加用户
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value="/addUser")
    public String addUser(User user, HttpServletRequest request){
        user.setName("blincon");
        user.setAge(55);
        user.setGender("male");
        userServiceImpl.addUser(user,request);
        //重定向到a/userInfo请求
        return "redirect:/a/userInfo";
    }
    /**
     * 获取用户详情，跳转到用户详情页面
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value="/details")
    public String userDetails(User user, Model model){
        model.addAttribute("userdetails",user);
        return "userdetails";
    }
    /**
     * 测试使用@RestControllerAdvice注解的全局异常处理类来处理异常
     * 当抛出MyException异常的时候
     * 就会触发GlobalExceptionHandler里面的doMyException方法
     */
    @RequestMapping(value="/testMyException")
    public void testMyException(){
        MyException.throwMyException("404","资源不存在！！！");
    }
    @RequestMapping(value="/delete")
    public String delete(User user, RedirectAttributes redirectAttributes){
        System.out.println("UserController的delete方法里面的user:"+user.toString());
        userServiceImpl.deleteUser(user);
        //删除成功后给页面添加一个flash信息
        addMessage(redirectAttributes,"删除成功！","页面刷新了！");
        return "redirect:"+ Global.getAdminPath()+"/userInfo";
    }

    /**
     * 接受httpClient发送的get请求
     */
    @GetMapping(value = "/testHttpClientWithGet", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String testHttpClientWithGet(HttpServletRequest request){
        System.out.println("接受到get请求的参数是："+request.getParameter("name"));
        System.out.println("接受到get请求的参数是："+request.getParameter("age"));
        return "{\"message\":\"成功接受到get请求！！！\"}";
    }

    /**
     * 接受httpClient发送的post请求
     */
    @PostMapping(value = "/testHttpClientWithPost",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String testHttpClientWithPost(HttpServletRequest request) throws IOException {
        InputStreamReader inputStreamReader = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        try {
            inputStreamReader = new InputStreamReader(request.getInputStream(),"UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;

            while((line = bufferedReader.readLine())!=null){
                sb.append(line);
            }
            if(sb.length()>0){
                System.out.println("接受到Post请求的参数是："+sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStreamReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "{\"message\":\"成功接受到post请求，参数是：\""+sb.toString()+"}";
    }
    /**
     * 接受httpClient发送的post请求
     */
    @PostMapping(value = "/testHttpClientWithPost2",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String testHttpClientWithPost2(HttpServletRequest request, HttpServletResponse response) {
        // 因为表单数据是键值对形式发送来的，
        // 所以这里用request.getParameterNames()获取所有的键值对
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            System.out.println("key="+key+"    value="+value);
        }
        return "{\"message\":\"成功接受到post2请求！！！\"}";
    }

    /**
     * 使用@PostConstruct初始化方法
     * 里面切换数据库
     */
    @PostConstruct
    public void initMethod(){
        //切换到第一个数据库
        DynamicDataSource.setDatasourceOne();
        //获取当前所在的数据库
        String currentDatasource= DynamicDataSource.getCurrentLookupKey();
        System.out.println("当前所在的数据库是："+currentDatasource);
    }
    @RequestMapping("/testPost")
    public void testPost(){
        //SimpleUrlHandlerMapping
        String url = "http://localhost:8080/springdemo0414/a/testHttpClientWithPost";
        String params = "{\"id\":\"2\",\"name\":\"麦卡唐尼\"}";
        String result = HttpClientUtil.doPost(url, params);
        System.out.println("测试使用HttpClient发送Post请求的返回值是："+result);
    }

    @Override
    public String toString() {
        return "UserController{}";
    }
}

