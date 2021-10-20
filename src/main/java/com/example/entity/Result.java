package com.example.entity;

/**
 * @author zhoupeng
 * @create time 2021-09-07-15:56
 */
public class Result<T> {
    private String code;
    private String message;
    public static <T> Result<T> error(String code, String msg) {
        Result<T> result = new Result();
        result.setCode(code);
        result.setMessage(msg);
        System.out.println(code);
        System.out.println(msg);
        return result;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
