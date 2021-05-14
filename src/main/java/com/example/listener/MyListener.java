package com.example.listener;

import com.example.utils.DataConsumeUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 自定义的监听器
 * 用于监听Web应用的启动和关闭
 *
 * @author zhoupeng
 * @create time 2021-05-06-14:38
 */
//@WebListener//如果在web.xml中配置了<listener>这里就不需要@WebListener了
public class MyListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        System.out.println(servletContext.getContextPath());
        DataConsumeUtil.init();
        System.out.println("监听器初始化方法执行了。。。");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("监听器销毁方法执行了。。。");
    }
}
