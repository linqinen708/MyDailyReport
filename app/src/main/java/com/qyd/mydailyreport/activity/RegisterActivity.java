package com.qyd.mydailyreport.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.bean.LoginBean;
import com.qyd.mydailyreport.retrofit.MyRetrofit;
import com.qyd.mydailyreport.retrofit.RxSubscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.autoCompleteTextView_account)
    AutoCompleteTextView mAutoCompleteTextViewAccount;
    @BindView(R.id.autoCompleteTextView_password)
    AutoCompleteTextView mAutoCompleteTextViewPassword;
    @BindView(R.id.autoCompleteTextView_department)
    AutoCompleteTextView mAutoCompleteTextViewDepartment;
    @BindView(R.id.autoCompleteTextView_name)
    AutoCompleteTextView mAutoCompleteTextViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    /**注册*/
    private void registerAccountByPost() {

        Map<String, Object> map = new HashMap<>();
        map.put("account", mAutoCompleteTextViewAccount.getText().toString());
        map.put("password", mAutoCompleteTextViewPassword.getText().toString());
        map.put("department", mAutoCompleteTextViewDepartment.getText().toString());
        map.put("name", mAutoCompleteTextViewName.getText().toString());

        MyRetrofit.getInstance()
                .getRetrofitServiceImpl()
                .registerAccountByPost(map)//发送http请求
                .map(new MyRetrofit.ServerResponseFunc<LoginBean>())
                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
                .observeOn(AndroidSchedulers.mainThread())//主线程启动观察者执行下面代码
                .subscribe(new RxSubscribe<LoginBean>(this) {
                    @Override
                    protected void _onNext(LoginBean bean) {
                        LogT.i("bean:" + bean.toString());
                        Toast.makeText(getBaseContext(),"注册成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                ;

    }

    @OnClick(R.id.button_register)
    public void onViewClicked() {
        registerAccountByPost();
    }
}
