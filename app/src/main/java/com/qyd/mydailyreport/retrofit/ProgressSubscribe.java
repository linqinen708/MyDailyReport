package com.qyd.mydailyreport.retrofit;

import android.content.Context;

import rx.Subscriber;

/**
 * Created by 林 on 2017/10/13.
 */

public abstract class ProgressSubscribe<T> extends Subscriber<T> implements ProgressCancelListener {

    private Context mContext;
    private ProgressHandler mProgressHandler;
    public ProgressSubscribe(Context context){
        mContext = context;
        mProgressHandler = new ProgressHandler(mContext,this,true);
    }

    public void showProgressDialog(){
        if(mProgressHandler!=null){
            mProgressHandler.obtainMessage(ProgressHandler.SHOW_PROGRESS).sendToTarget();
        }
    }
    public void dismissProgressDialog(){
        if(mProgressHandler!=null){
            mProgressHandler.obtainMessage(ProgressHandler.DISMISS_PROGRESS).sendToTarget();
            mProgressHandler = null;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onProgressCanceled() {//取消请求
        if(!this.isUnsubscribed()){
            unsubscribe();
        }
    }

    protected abstract void _onNext(T t);
}
