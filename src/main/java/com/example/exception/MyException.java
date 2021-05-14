package com.example.exception;

/**
 * 自定义的异常
 *
 * @author zhoupeng
 * @create time 2021-04-15-16:29
 */
public class MyException extends Exception{
    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }
}
