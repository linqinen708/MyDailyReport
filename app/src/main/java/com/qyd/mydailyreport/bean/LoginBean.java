package com.qyd.mydailyreport.bean;

/**
 * Created by 林 on 2017/10/9.
 */

public class LoginBean {


    /**
     * UserBean : {"id":2,"token":"573FD53BA45A4587B2A6F3CDE5710653","account":"linqinen708","password":"lin123456","department":"安卓","name":"lin123456"}
     */

    private UserBeanBean UserBean;

    public UserBeanBean getUserBean() {
        return UserBean;
    }

    public void setUserBean(UserBeanBean UserBean) {
        this.UserBean = UserBean;
    }

    public static class UserBeanBean {
        /**
         * id : 2
         * token : 573FD53BA45A4587B2A6F3CDE5710653
         * account : linqinen708
         * password : lin123456
         * department : 安卓
         * name : lin123456
         */

        private int id;
        private String token;
        private String account;
        private String password;
        private String department;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
