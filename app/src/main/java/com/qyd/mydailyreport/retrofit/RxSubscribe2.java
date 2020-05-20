package com.qyd.mydailyreport.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.BuildConfig;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by 林 on 2019/07/03.
 */

public class RxSubscribe2<T> implements Observer<T> {
    private Context mContext;
    private ProgressDialog dialog;
    private String msg = "加载中...";

    private boolean isShowProgress = true;



    /**
     * @param context context
     * @param msg     dialog message
     */
    public RxSubscribe2(Context context, String msg) {
        this.mContext = context;
        this.msg = msg;
        showProgressDialog();
    }

    public RxSubscribe2(Context context, String msg, boolean isShowProgress) {
        this.mContext = context;
        this.msg = msg;
        this.isShowProgress = isShowProgress;
        showProgressDialog();
    }

    protected RxSubscribe2(Context context, boolean isShowProgress) {
        this(context, "加载中...", isShowProgress);
    }

    /**
     * @param context context
     */
    public RxSubscribe2(Context context) {
        this(context, "加载中...");
    }

    public RxSubscribe2() {
    }

    private void showProgressDialog() {
        if (mContext != null && isShowProgress) {
            if (dialog == null) {
                dialog = new ProgressDialog(mContext);
                dialog.setMessage(msg);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
                dialog.show();
            } else if (!dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T bean) {
    }


    @Override
    public void onError(Throwable throwable) {
        LogT.i("出错了:" + throwable);
        onError(throwable, true);
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }


    /**
     * @param isShowToast 默认是true，如果不想显示toast信息，设为false
     */
    protected void onError(Throwable throwable, boolean isShowToast) {
        LogT.i("出错了:" + throwable);
        if (isShowToast) {
            if (throwable instanceof ServerException) {
                onError(((ServerException) throwable).code, ((ServerException) throwable).message, true);
            } else {
                if (throwable instanceof SocketTimeoutException) {
                    Toast.makeText(mContext, "网络超时", Toast.LENGTH_SHORT).show();
                } else if (throwable instanceof JsonSyntaxException) {
                    /*一般是后台给的字段类型与前端默认的不一致，最典型的是其它约定条款other_agreements*/
                    showErrorToast("服务器语法异常", throwable.getMessage());
                } else if (throwable instanceof IllegalArgumentException) {
                    /*一般是参数为null导致的*/
                    showErrorToast("参数异常", throwable.getMessage());
                } else if (throwable instanceof HttpException) {
                    showErrorToast("网络异常", throwable.getMessage());
                } else {
                    Toast.makeText(mContext, "数据异常", Toast.LENGTH_SHORT).show();
                }
            }
        }
        throwable.printStackTrace();
        dismissProgressDialog();
    }

    protected void onError(int code, String message, boolean isShowToast) {
        if (isShowToast) {
            onError(code, message);
        }
    }

    protected void onError(int code, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    private void showErrorToast(String text, String errorMessage) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(mContext, text + errorMessage, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
        }
    }
}