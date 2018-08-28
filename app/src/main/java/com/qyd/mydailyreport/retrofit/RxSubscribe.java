package com.qyd.mydailyreport.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.linqinen.library.utils.LogT;

import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by 林 on 2017/10/13.
 * www.jianshu.com/p/cc064e3d5f21#
 */

public abstract class RxSubscribe<T> extends Subscriber<T> {
    private Context mContext;
    private ProgressDialog dialog;
    private String msg;

    /**
     * @param context context
     * @param msg     dialog message
     */
    public RxSubscribe(Context context, String msg) {
        this.mContext = context;
        this.msg = msg;
    }

    /**
     * @param context context
     */
    public RxSubscribe(Context context) {
        this(context, "加载中...");
    }

    private void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage(msg);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (!isUnsubscribed()) {
                        unsubscribe();
                    }
                }
            });
            dialog.show();
        } else if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable throwable) {
        LogT.i("出错了:" + throwable);
        if (throwable instanceof ServerException) {
            Toast.makeText(mContext, ((ServerException) throwable).message, Toast.LENGTH_LONG).show();
        } else {
            throwable.printStackTrace();
            if (throwable instanceof SocketTimeoutException) {
                Toast.makeText(mContext, "网络超时", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
//        if (TDevice.getNetworkType() == 0) {
//            _onError("网络不可用");
//        } else if (e instanceof ServerException) {
//            _onError(e.getMessage());
//        } else {
//            _onError("请求失败，请稍后再试...");
//        }
        dismissProgressDialog();
    }

    protected abstract void _onNext(T t);

//    protected abstract void _onError(String message);
}