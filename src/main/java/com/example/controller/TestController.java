package com.example.controller;

import com.example.entity.ConsumeData;
import com.example.entity.DBProperties;
import com.example.utils.DataConsumeUtil;
import com.example.utils.DataSourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试用的Controller
 *
 * @author zhoupeng
 * @create time 2021-05-12-9:27
 */
@Controller
@RequestMapping("${adminPath}")
public class TestController extends BaseController{
    @Autowired
    private DBProperties dbProperties;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value="/getDBProperties")
    public void getDBProperties() {
        System.out.println("TestController的getDBProperties方法里面的dbProperties.getType():"+dbProperties.getType());
    }
    @RequestMapping(value="/testJdbcTemplate")
    public void testJdbcTemplate(){
        //这是spring注入的jdbcTemplate
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
}
