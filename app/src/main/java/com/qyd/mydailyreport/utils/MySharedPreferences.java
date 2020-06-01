package com.qyd.mydailyreport.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.linqinen.library.utils.LogT;

/**
 * Created by lin on 2016/11/24.
 */

public class MySharedPreferences {


    private SharedPreferences mSharedPreferences;
    private Context mContext;

    private boolean wakeUp1Password;
    private String cityListString;


    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     */
    private static class SingletonHolder {
        private static MySharedPreferences instance = new MySharedPreferences();
    }

    public static MySharedPreferences getInstance() {
        return SingletonHolder.instance;
    }

    private MySharedPreferences(){
        LogT.i("初始化MySharedPreferences");
//        if (mContext != null) {
//            mSharedPreferences = mContext.getSharedPreferences("user_info", Context.MODE_PRIVATE);
//        }
    }

    public void init(Context context){
        LogT.i("初始化init:"+context);
        mContext = context;
        if (mContext != null) {
            mSharedPreferences = mContext.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        }
    }

//    /**初始化本地的缓存数据*/
//    public void initSharedPreferencesData(){
//        setAccessToken(null);
//        setAutoLogin(false);
//        setUserId(null);
//        setMobile(null);
//        setRegistered(false);
//        setWakeUpSwitch(false);
//    }

    /**
     * 清除本地数据,accessToken不能为null，需要为""，否则有错误
     */
    public void clearData() {
            mSharedPreferences.edit().clear().apply();
//        if (mSharedPreferences != null) {
//        }else{
//            LogT.i("mSharedPreferences为空");
//        }
    }

//    public void deleteSharedPreference(Context context) {
//        HomeActivity.accessToken = "";
//        HomeActivity.autoLogin = false;
////        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
//        deleteFilesByDirectory(new File(context.getFilesDir().getPath() + context.getPackageName() + "/shared_prefs"));
//    }
//
//    /**
//     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
//     */
//    private void deleteFilesByDirectory(File directory) {
//        LogT.i("directory:"+directory);
//        if (directory != null && directory.exists() && directory.isDirectory()) {
//            for (File item : directory.listFiles()) {
//                item.delete();
//            }
//        }
//    }


    /**id*/
    public int getId() {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        return mSharedPreferences.getInt("id",0);
    }

    public void setId(int id) {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        mSharedPreferences.edit().putInt("id", id).apply();
    }
    /**token*/
    public String getToken() {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        return mSharedPreferences.getString("token",null);
    }

    public void setToken(String token) {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        mSharedPreferences.edit().putString("token", token).apply();
    }
    /**账号*/
    public String getAccount() {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        return mSharedPreferences.getString("account",null);
    }

    public void setAccount(String account) {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        mSharedPreferences.edit().putString("account", account).apply();
    }
    /**密码*/
    public String getPassword() {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        return mSharedPreferences.getString("password",null);
    }

    public void setPassword(String password) {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        mSharedPreferences.edit().putString("password", password).apply();
    }
    /**姓名*/
    public String getName() {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        return mSharedPreferences.getString("name",null);
    }

    public void setName(String name) {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        mSharedPreferences.edit().putString("name", name).apply();
    }

    /**部门*/
    public String getDepartment() {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        return mSharedPreferences.getString("department",null);
    }

    public void setDepartment(String department) {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        mSharedPreferences.edit().putString("department", department).apply();
    }
    /**部门*/
    public String getPosition() {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        return mSharedPreferences.getString("position",null);
    }

    public void setPosition(String position) {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        mSharedPreferences.edit().putString("position", position).apply();
    }
    /**手机号*/
    public String getPhone() {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        return mSharedPreferences.getString("phone",null);
    }

    public void setPhone(String phone) {
        if(mSharedPreferences == null){
            throw new NullPointerException("你还没有调用init(Context context)方法进行初始化");
        }
        mSharedPreferences.edit().putString("phone", phone).apply();
    }



    /**我的头像地址*/
    public String getMyHeadPortraitPath() {
        return mSharedPreferences.getString("myHeadPortraitPath", null);
    }

    public void setMyHeadPortraitPath(String myHeadPortraitPath) {
        mSharedPreferences.edit().putString("myHeadPortraitPath", myHeadPortraitPath).apply();
    }











}
