package com.example.exception;

/**
 * 名字异常
 *
 * @author zhoupeng
 * @create time 2021-04-15-16:31
 */
public class NameException extends MyException{
    public NameException() {
        super();
    }

    public NameException(String message) {
        super(message);
    }
}
