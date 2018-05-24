package com.qyd.mydailyreport.bean;

/**
 * Created by 林 on 2017/9/30.
 */

public class ResultBean {


    /**
     * code : 1001
     * message : 账户不存在
     * attr : {}
     */

    private int code;
    private String message;
    private AttrBean attr;

    @Override
    public String toString() {
        return "ResultBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", attr=" + attr +
                '}';
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

    public AttrBean getAttr() {
        return attr;
    }

    public void setAttr(AttrBean attr) {
        this.attr = attr;
    }

    public static class AttrBean {
    }
}
