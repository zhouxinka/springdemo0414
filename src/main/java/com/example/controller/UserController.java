package com.example.controller;

import com.example.dataSource.DynamicDataSource;
import com.example.entity.User;
import com.example.exception.MyException;
import com.example.service.UserService;
import com.example.utils.Global;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public User get(@RequestParam(required = false) String id){
        System.out.println("UserController里面的ModelAttribute注解的方法里面的id:"+id);
        User user = null;
        if(StringUtils.isNotBlank(id)){
            user = userServiceImpl.getUserById(Integer.parseInt(id));
        }
        if(user==null){
            user = new User();
        }
        System.out.println("UserController里面的ModelAttribute注解的方法里面的user:"+user.toString());
        return user;
    }

    /**
     * 前端输入/a/login，跳转到登录页面
     * @return
     */
    @RequestMapping(value="/login")
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
            addMessage(redirectAttributes,"登陆失败，用户名或密码错误！！！");
            return "redirect:"+Global.getAdminPath()+"/login";
        }
        //将当前登录的用户存入session
        HttpSession session = request.getSession();session.setAttribute("CURRENT_USER",user);
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

    @RequestMapping(value="/getUserById")
    @ResponseBody
    public User getUserById(){
        log.error("getUserById...");
        User user = userServiceImpl.getUserById(3);
        return user;
    }

    @RequestMapping(value="/addUser")
    public String addUser(User user, HttpServletRequest request){
        log.error("addUser...");
        user.setName("blincon");
        user.setAge(55);
        user.setGender("male");
        userServiceImpl.addUser(user,request);
        //重定向到a/userInfo请求
        return "redirect:/a/userInfo";
    }

    @RequestMapping(value="/details")
    public String userDetails(User user, Model model){
        model.addAttribute("userdetails",user);
        return "userdetails";
    }
    @RequestMapping(value="/testMyException")
    public void testMyException(){
        System.out.println("testMyException...");
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

    @PostConstruct
    public void initMethod(){
        DynamicDataSource.setDatasourceOne();//切换数据库
        //获取当前所在的数据库
        String currentDatasource= DynamicDataSource.getCurrentLookupKey();
        System.out.println("UserController中的初始化方法执行了。。。");
    }
}

