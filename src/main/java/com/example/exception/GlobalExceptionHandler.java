package com.example.exception;

import com.example.entity.Result;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义异常发生时候触发处理的类
 *
 * @author zhoupeng
 * @create time 2021-04-15-16:36
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger log = Logger.getLogger(GlobalExceptionHandler.class);
    //当抛出名字异常时触发这个方法,比如记录日志，转发到异常页面，通知邮件或者短信
    @ExceptionHandler(value = NameException.class)
        public ModelAndView handleNameException(Exception e){
            System.out.println("异常："+e);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("msg","名字不正确");
            modelAndView.setViewName("nameException");
            return modelAndView;

    }
    //处理未知异常
    @ExceptionHandler
    public ModelAndView handleUnknownException(Exception e){
        log.error("未知异常：",e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg","未知异常");
        modelAndView.setViewName("unknownException");
        return modelAndView;

    }

    @ExceptionHandler(MyException.class)
    public <T> Result<T> doMyException(MyException e) {
        return Result.error(e.getCode(), e.getMessage());
    }
}
