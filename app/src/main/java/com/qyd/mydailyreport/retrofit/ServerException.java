package com.qyd.mydailyreport.retrofit;

/**
 * Created by 林 on 2017/10/9.
 */

public class ServerException extends RuntimeException {

    public int code;
    public String message;

    public ServerException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
