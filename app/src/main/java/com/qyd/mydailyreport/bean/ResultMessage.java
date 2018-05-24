package com.qyd.mydailyreport.bean;

import java.util.HashMap;
import java.util.Map;

public class ResultMessage{
    private int code = 0;
    private String message;
    private Map<String, Object> attr = new HashMap();

    public ResultMessage() {
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void raise(int code, String message) {
        this.code = code;
        this.message = message;
    }



    public void put(String key, Object obj) {
        this.attr.put(key, obj);
    }

    public Map<String, Object> getAttr() {
        return this.attr;
    }
}
