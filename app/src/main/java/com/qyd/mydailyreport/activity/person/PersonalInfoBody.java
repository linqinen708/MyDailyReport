package com.qyd.mydailyreport.activity.person;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.activity.BasicActivity;

/**
 * Created by Ian on 2018/8/28.
 */
public class PersonalInfoBody {

    private String token;
    /**部门*/
    private String department;
    /**姓名*/
    private String user_name;
    /**手机号*/
    private String phone;

    public PersonalInfoBody(String department, String user_name, String phone) {
        token = BasicActivity.token;
        this.department = department;
        this.user_name = user_name;
        this.phone = phone;

        LogT.i("数据：" + toString());
    }

    @Override
    public String toString() {
        return "PersonalInfoBody{" +
                "token='" + token + '\'' +
                ", department='" + department + '\'' +
                ", user_name='" + user_name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
