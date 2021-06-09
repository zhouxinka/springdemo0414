package com.example.test;

import com.example.entity.User;
import com.example.serviceImpl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhoupeng
 * @create time 2021-04-14-10:08
 */
public class Test {
    @Autowired
    private TeacherServiceImpl teacherServiceImpl;
    public static void main(String[] args) {
        ApplicationContext ap=new ClassPathXmlApplicationContext("spring-config.xml");
        User user = (User)ap.getBean("user");
        System.out.println(user.toString());

    }
    @org.junit.Test
    public void test(){
       ApplicationContext ap=new ClassPathXmlApplicationContext("spring-config.xml");
        User user = (User)ap.getBean("user");
        System.out.println(user.toString());
        Class<User> userClass = User.class;
        Class<? extends Class> aClass = userClass.getClass();
        Class<? extends Class> aClass1 = aClass.getClass();
        System.out.println(aClass.equals(aClass1));


    }
}
