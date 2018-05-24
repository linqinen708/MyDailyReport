package com.qyd.mydailyreport.bean;

import java.util.List;

/**日报详情*/
public class ReportDetailBean {


    private List<ReportDetailListBean> reportDetailList;

    public List<ReportDetailListBean> getReportDetailList() {
        return reportDetailList;
    }

    public void setReportDetailList(List<ReportDetailListBean> reportDetailList) {
        this.reportDetailList = reportDetailList;
    }

    public static class ReportDetailListBean {
        /**
         * id : 32
         * uid : 2
         * department : Android
         * name : lin
         * content : dfdfdf2132323
         * date : 2017-10-23
         * next_plan : qqq
         * feedback_suggestion : aaaa
         */

        private int id;
        private int uid;
        private String department;
        private String name;
        private String content;
        private String date;
        private String next_plan;
        private String feedback_suggestion;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNext_plan() {
            return next_plan;
        }

        public void setNext_plan(String next_plan) {
            this.next_plan = next_plan;
        }

        public String getFeedback_suggestion() {
            return feedback_suggestion;
        }

        public void setFeedback_suggestion(String feedback_suggestion) {
            this.feedback_suggestion = feedback_suggestion;
        }
    }
}
