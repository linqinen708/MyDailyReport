package com.qyd.mydailyreport.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 林 on 2017/10/12.
 */

public class BasicActivity extends AppCompatActivity {

    public static String token = "";
    protected static int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }



    protected Map<String, Object> getMap(){

//        String token = MySharedPreferences.getInstance().getToken();
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("token", token);
        return map;
    }
}
