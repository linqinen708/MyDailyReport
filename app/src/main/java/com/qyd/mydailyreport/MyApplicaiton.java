package com.qyd.mydailyreport;

import android.app.Application;

import com.qyd.mydailyreport.utils.MySharedPreferences;


/**
 * Created by 林 on 2017/10/9.
 */

public class MyApplicaiton extends Application {

//    private static MyApplicaiton myApplicaiton;

//    public static MyApplicaiton getMyApplicaiton() {
//        return myApplicaiton;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
//        myApplicaiton = this;

        MySharedPreferences.getInstance().init(this);
    }
}
