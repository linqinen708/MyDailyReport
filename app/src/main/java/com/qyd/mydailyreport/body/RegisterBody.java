package com.qyd.mydailyreport.body;

/**
 * Created by Ian on 2018/8/28.
 */
public class RegisterBody {

    /**账户*/
    private String account;
    /**密码*/
    private String password;

    /**部门*/
    private String department;
    /**姓名*/
    private String user_name;
    /**手机号*/
    private String phone;

    public RegisterBody(String account, String password, String department, String user_name, String phone) {
        this.account = account;
        this.password = password;
        this.department = department;
        this.user_name = user_name;
        this.phone = phone;
    }
}
