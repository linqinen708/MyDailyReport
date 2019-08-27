package com.qyd.mydailyreport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.bean.LoginBean;
import com.qyd.mydailyreport.retrofit.MyRetrofit;
import com.qyd.mydailyreport.retrofit.RxSubscribe2;
import com.qyd.mydailyreport.utils.MySharedPreferences;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BasicActivity {

    @BindView(R.id.autoCompleteTextView_account)
    AutoCompleteTextView mAutoCompleteTextViewAccount;
    @BindView(R.id.autoCompleteTextView_password)
    AutoCompleteTextView mAutoCompleteTextViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MySharedPreferences.getInstance().getName() != null) {
            startActivity(new Intent(this, HomeActivity2.class));
            finish();
        } else {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
        }
    }

    /**
     * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。
     * 行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，
     * 可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。
     * 不要把计算工作放在 io() 中，可以避免创建不必要的线程。
     * <p>
     * subscribeOn()改变的是订阅的线程，即call()执行的线程。
     * observeOn()改变的是发送的线程，即onNext()的过程，
     * 注意，即我们在逻辑中通过操作符对数据进行修改都是在发送的过程中。除了最初Observable创建的过程。
     * <p>
     * subscribeOn()是在call()方法中起作用，而observeOn()实在onNext()中作用
     * <p>
     * subscribeOn改变了observable本身产生事件的schedule以及发出事件后相关处理事件的程序所在的scheduler，
     * 而obseveron仅仅是改变了对发出事件后相关处理事件的程序所在的scheduler
     */
    private void httpPostLogin() {

        if(TextUtils.isEmpty(mAutoCompleteTextViewAccount.getText())){
            Toast.makeText(getBaseContext(),"请输入账号",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(mAutoCompleteTextViewPassword.getText())){
            Toast.makeText(getBaseContext(),"请输入密码",Toast.LENGTH_SHORT).show();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("account", mAutoCompleteTextViewAccount.getText().toString());
        map.put("password", mAutoCompleteTextViewPassword.getText().toString());

        MyRetrofit.getInstance()
                .getRetrofitServiceImpl()
                .loginByPost2(map)//发送http请求
                .map(new MyRetrofit.ServerResponseFunc<LoginBean>())
                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
                .observeOn(AndroidSchedulers.mainThread())//发送请求给主线程执行下面代码
                .compose(this.<LoginBean>bindToLifecycle())
                .subscribe(new RxSubscribe2<LoginBean>(this) {
                    @Override
                    public void onNext(LoginBean bean) {
                        if(bean == null){
                            Toast.makeText(getBaseContext(),"数据异常",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        LogT.i("bean:" + bean.toString());
                        MySharedPreferences.getInstance().setName(bean.getUserBean().getName());
                        MySharedPreferences.getInstance().setDepartment(bean.getUserBean().getDepartment());
                        MySharedPreferences.getInstance().setId(bean.getUserBean().getId());
                        MySharedPreferences.getInstance().setToken(bean.getUserBean().getToken());
                        startActivity(new Intent(LoginActivity.this, HomeActivity2.class));
                        finish();
                    }
                });

    }


    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                httpPostLogin();
                break;
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
