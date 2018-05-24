package com.qyd.mydailyreport.retrofit;

/**
 * Created by 林 on 2017/10/13.
 */

//接口，用来回调
public interface SubscribeOnNextListener<T> {
    void onNext(T t);
}