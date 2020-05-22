package com.qyd.mydailyreport.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;

import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.ui.activity.base.BaseActivity;
import com.qyd.mydailyreport.bean.LoginBean;
import com.qyd.mydailyreport.body.RegisterBody;
import com.qyd.mydailyreport.retrofit.MyObserver;
import com.qyd.mydailyreport.retrofit.MyRetrofitHelper;
import com.qyd.mydailyreport.utils.MySharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.autoCompleteTextView_account)
    AutoCompleteTextView mAutoCompleteTextViewAccount;
    @BindView(R.id.autoCompleteTextView_password)
    AutoCompleteTextView mAutoCompleteTextViewPassword;
    @BindView(R.id.autoCompleteTextView_department)
    AutoCompleteTextView mAutoCompleteTextViewDepartment;
    @BindView(R.id.autoCompleteTextView_name)
    AutoCompleteTextView mAutoCompleteTextViewName;
    @BindView(R.id.autoCompleteTextView_phone)
    AutoCompleteTextView mAutoCompleteTextViewPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    /**
     * 注册
     */
    private void registerAccountByPost() {

//        Map<String, Object> map = new HashMap<>();
//        map.put("account", mAutoCompleteTextViewAccount.getText().toString());
//        map.put("password", mAutoCompleteTextViewPassword.getText().toString());
//        map.put("department", mAutoCompleteTextViewDepartment.getText().toString());
//        map.put("user_name", mAutoCompleteTextViewName.getText().toString());
//        map.put("phone", mAutoCompleteTextViewPhone.getText().toString());

//        MyRetrofit.getInstance()
//                .getRetrofitServiceImpl()
//                .registerAccountByPost()//发送http请求
//                .map(new MyRetrofit.ServerResponseFunc<LoginBean>())
//                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
//                .observeOn(AndroidSchedulers.mainThread())//主线程启动观察者执行下面代码
//                .subscribe(new RxSubscribe2<LoginBean>(this) {
//                    @Override
//                    public void onNext(LoginBean bean) {
//                        LogT.i("bean:" + bean.toString());
//                        MySharedPreferences.getInstance().setAccount(bean.getUser().getAccount());
//                        MySharedPreferences.getInstance().setPassword(bean.getUser().getPassword());
//                        Toast.makeText(getBaseContext(), "注册成功", Toast.LENGTH_SHORT).show();
//                        setResult(Activity.RESULT_OK);
//                        finish();
//                    }
//                })
//        ;
        MyRetrofitHelper.INSTANCE.httpRegisterAccount(new RegisterBody(
                        mAutoCompleteTextViewAccount.getText().toString(),
                        mAutoCompleteTextViewPassword.getText().toString(),
                        mAutoCompleteTextViewDepartment.getText().toString(),
                        mAutoCompleteTextViewName.getText().toString(),
                        mAutoCompleteTextViewPhone.getText().toString()),
                new MyObserver<LoginBean>(mContext) {
                    @Override
                    protected void onSuccess(LoginBean bean) {
                        MySharedPreferences.getInstance().setAccount(bean.getUser().getAccount());
                        MySharedPreferences.getInstance().setPassword(bean.getUser().getPassword());
                        showToast("注册成功");
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                });

    }

    private boolean checkPostParams() {
        if (TextUtils.isEmpty(mAutoCompleteTextViewAccount.getText())) {
            showToast("请填写账号");
            return false;
        }
        if (TextUtils.isEmpty(mAutoCompleteTextViewPassword.getText())) {
            showToast("请填写密码");
            return false;
        }
        if (TextUtils.isEmpty(mAutoCompleteTextViewDepartment.getText())) {
            showToast("请填写部门");
            return false;
        }
        if (TextUtils.isEmpty(mAutoCompleteTextViewName.getText())) {
            showToast("请填写姓名");
            return false;
        }
        if (TextUtils.isEmpty(mAutoCompleteTextViewPhone.getText())) {
            showToast("请填写手机号码");
            return false;
        }
        return true;
    }

    @OnClick(R.id.button_register)
    public void onViewClicked() {
        if (checkPostParams()) {
            registerAccountByPost();
        }
    }
}
