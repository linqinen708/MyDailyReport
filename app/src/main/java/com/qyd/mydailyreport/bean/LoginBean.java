package com.qyd.mydailyreport.bean;

/**
 * Created by 林 on 2017/10/9.
 */

public class LoginBean {


    /**
     * user : {"id":33,"token":"6EEB37C705204F77992BADC16F7C42B3","account":"linqinen708","password":"lin123456","department":"Android","user_name":"林勤恩","phone":"18217088618"}
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * id : 33
         * token : 6EEB37C705204F77992BADC16F7C42B3
         * account : linqinen708
         * password : lin123456
         * department : Android
         * user_name : 林勤恩
         * phone : 18217088618
         */

        private int id;
        private String token;
        private String account;
        private String password;
        private String department;
        private String user_name;
        private String phone;

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
    }
}
