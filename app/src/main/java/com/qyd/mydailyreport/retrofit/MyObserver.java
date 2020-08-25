package com.qyd.mydailyreport.retrofit;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.$Gson$Types;
import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.BuildConfig;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.widget.dialog.AppLoadingDialog;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by Ian on 2019/11/26.
 * https://www.jianshu.com/p/54c43dca83d4
 * DisposableObserver 对onSubscribe 额外进行了封装，
 */
public abstract class MyObserver<T> extends DisposableObserver<T> {

    private Context mContext;
    private AppLoadingDialog progressDialog;
    private String msg = "加载中...";

    private boolean isShowProgress = true;
    private boolean isShowToast = true;

    public void setShowToast(boolean showToast) {
        isShowToast = showToast;
    }

    private String message = "网络异常";


    public MyObserver() {

    }

    public MyObserver(Context context) {
        mContext = context;
        showProgressDialog();
    }

    public MyObserver(Context context, String msg) {
        mContext = context;
        this.msg = msg;
        showProgressDialog();
    }

    public MyObserver(Context context, boolean isShowProgress) {
        mContext = context;
        this.isShowProgress = isShowProgress;
        showProgressDialog();
    }

    public MyObserver(Context context, String msg, boolean isShowProgress) {
        mContext = context;
        this.msg = msg;
        this.isShowProgress = isShowProgress;
        showProgressDialog();
    }


    private void showProgressDialog() {
        if (mContext != null && isShowProgress) {
            if (progressDialog == null) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_app_loading, null);
                final ProgressBar progressBar = view.findViewById(R.id.progress_bar);
                progressDialog = new AppLoadingDialog(mContext);

                //setOnCancelListener 是用户主动取消，setOnDismissListener则是弹框消失
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        LogT.i("用户主动取消请求isDisposed():" + isDisposed());
                        //如果用户主动取消进度弹框，则取消网络请求
                        if (!isDisposed()) {
                            dispose();
                        }
                        LogT.i("用户主动取消请求11isDisposed():" + isDisposed());
                    }
                });
                progressDialog.show();
            } else if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onNext(T t) {
//        LogT.i("显示数据");
        onSuccess(t);
    }

    protected abstract void onSuccess(T bean);

    @Override
    public void onError(Throwable throwable) {

        LogT.e("mContext:" + mContext + ",出错了:" + throwable);
        onError(throwable, true);
    }

    /**
     * @param isShowToast 默认是true，如果不想显示toast信息，设为false
     */
    protected void onError(Throwable throwable, boolean isShowToast) {
//        LogT.i("出错了:" + throwable);
        dismissProgressDialog();
        this.isShowToast = isShowToast;
        if (throwable instanceof SocketTimeoutException) {
            showErrorToast("网络超时", throwable.getMessage());
        } else if (throwable instanceof JsonSyntaxException) {
            /*一般是后台给的字段类型与前端默认的不一致，最典型的是其它约定条款other_agreements*/
            showErrorToast("服务器语法异常", throwable.getMessage());
        } else if (throwable instanceof IllegalArgumentException) {
            /*一般是参数为null导致的*/
            showErrorToast("参数异常", throwable.getMessage());
        } else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            if (httpException.response() != null) {
//                if (mContext != null && (httpException.code() == HttpError.LOGIN_EXPIRES)) {
//                    SPHelper.clearToLogin();
//                    mContext.startActivity(new Intent(mContext, LoginActivity.class).putExtra(MyExtra.LOGIN_EXPIRE,true));
//                    showErrorToast("登陆过期，请重新登陆",HttpError.LOGIN_EXPIRES+"");
//                    return;
//                }
                httpException(httpException.code(), httpException.message());
                if (httpException.response().errorBody() != null) {
                    String error = null;
                    try {
                        error = httpException.response().errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (TextUtils.isEmpty(error)) {
                        showErrorToast("网络异常", throwable.getMessage());
                    } else {
                        showErrorToast("网络异常", throwable.getMessage());
                    }
                }
            } else {
                showErrorToast("网络异常", throwable.getMessage());
            }

        } else if (throwable instanceof ServerException) {
            onError(((ServerException) throwable).message);
        } else {
            showErrorToast("数据异常", throwable.getMessage());
        }
        throwable.printStackTrace();

    }

    protected void onError(String message) {
        if (isShowToast && mContext != null) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

    protected void httpException(int code, String message) {

    }

    private void showErrorToast(String text, String errorMessage) {
        if (isShowToast && mContext != null) {
            if (BuildConfig.DEBUG) {
                Toast.makeText(mContext, text + errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onComplete() {
//        LogT.i("http请求完成:" );
        dismissProgressDialog();
    }

    private Type getType() {
        //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        Type superclass = getClass().getGenericSuperclass();

        if (superclass instanceof Class) {
//      throw new RuntimeException("请传入实体类");
            return null;
        } else {
            //ParameterizedType参数化类型，即泛型
            ParameterizedType parameterized = (ParameterizedType) superclass;

            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            //将Java 中的Type实现,转化为自己内部的数据实现,得到gson解析需要的泛型
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }
    }

}
