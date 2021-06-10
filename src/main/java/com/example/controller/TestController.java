package com.example.controller;

import com.example.entity.ConsumeData;
import com.example.entity.DBProperties;
import com.example.entity.User;
import com.example.utils.DataConsumeUtil;
import com.example.utils.DataSourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试用的Controller
 *
 * @author zhoupeng
 * @create time 2021-05-12-9:27
 */
@Controller
@RequestMapping("${adminPath}")
public class TestController extends BaseController{
    //@Autowired //不建议使用属性注入，建议使用构造方法注入
    private DBProperties dbProperties;

    //@Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value="/getDBProperties")
    public void getDBProperties() {
        System.out.println("TestController的getDBProperties方法里面的dbProperties.getType():"+dbProperties.getType());
    }

    @RequestMapping(value="/testJSP")
    public String test(HttpServletRequest request) {
        return "test";
    }

    @RequestMapping(value="/testData")
    public void testData(@RequestBody  User user) {
        System.out.println("TestController的testData方法里面的user:"+user.toString());
    }

    @RequestMapping(value="/testJdbcTemplate")
    public void testJdbcTemplate(){
        //这是spring注入的jdbcTemplate,在applicationContext.xml里面有配置
        String name = jdbcTemplate.queryForObject("select name from user where id=1 ",String.class);
        System.out.println("TestController的testJdbcTemplate方法里面的name:"+name);
        //这是通过构造方法自己new的JdbcTemplate
        JdbcTemplate j=new JdbcTemplate(DataSourceUtils.getDataSource());
        String n = j.queryForObject("select name from user where id=1 ",String.class);
        System.out.println("TestController的testJdbcTemplate方法里面的n:"+n);
    }
    @RequestMapping("/testDataConsumeUtil")
    public void testDataConsumeUtil(){
        try{
            ConsumeData consumeData = new ConsumeData();
            consumeData.setType("1");
            consumeData.setSdcId("Sample");
            consumeData.setDataset("1");
            consumeData.setSampleId("1");
            consumeData.setParamlistid("1");
            consumeData.setAlias("1");
            consumeData.setNotes(this.getClass().getSimpleName());
            //将consumeData对象添加到consumeQueue阻塞队列
            DataConsumeUtil.consumeQueue.put(consumeData);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
    /**
     * 这种加了@Autowired注解的无参构造方法会报错，因为程序会先执行此构造方法，
     * 后执行了加了@Autowired注解的属性注入
     * 所以在执行此构造方法时候会报空指针
     */
    //@Autowired
    //public TestController() {
        //System.out.println("TestController里面的构造方法执行了:"+dbProperties.getType());
    //}

    /**
     * 加了@Autowired注解的构造方法在程序启动时候会执行且给参数实现注入，
     * 如果不加@Autowired注解那么不会执行
     * @param
     */
    @Autowired
    public TestController(DBProperties dbProperties,JdbcTemplate jdbcTemplate) {
        System.out.println("TestController里面的有参构造方法执行了");
        this.dbProperties = dbProperties;
        this.jdbcTemplate = jdbcTemplate;
    }
}
