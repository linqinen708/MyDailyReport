package com.qyd.mydailyreport.ui.activity.person;

/**
 * Created by Ian on 2018/8/28.
 */
public class PersonalInfoBody {

    /**部门*/
    private String department;
    /**职位*/
    private String position;
    /**姓名*/
    private String user_name;
    /**手机号*/
    private String phone;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PersonalInfoBody() {
    }

    @Override
    public String toString() {
        return "PersonalInfoBody{" +
                ", department='" + department + '\'' +
                ", user_name='" + user_name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
