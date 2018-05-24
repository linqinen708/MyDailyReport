package com.qyd.mydailyreport.retrofit;

/**
 * Created by 林 on 2017/10/9.
 * http://www.jianshu.com/p/bd758f51742e
 */

public class HttpResult<T> {

    private int code;
    private String message;
    private T attr;

    public T getData() {
        return attr;
    }

    public void setData(T attr) {
        this.attr = attr;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

