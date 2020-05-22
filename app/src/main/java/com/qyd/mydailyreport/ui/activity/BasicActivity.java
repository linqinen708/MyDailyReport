package com.qyd.mydailyreport.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

/**
 * Created by 林 on 2017/10/12.
 */

public abstract class BasicActivity extends RxAppCompatActivity {

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
