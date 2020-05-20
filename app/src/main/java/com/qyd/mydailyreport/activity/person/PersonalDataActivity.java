package com.qyd.mydailyreport.activity.person;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.activity.BasicActivity;
import com.qyd.mydailyreport.retrofit.EmptyObject;
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

public class PersonalDataActivity extends BasicActivity {

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_department)
    EditText mEtDepartment;
    @BindView(R.id.et_phone)
    EditText mEtPhone;

    private String name, department, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);

        name = MySharedPreferences.getInstance().getName();
        department = MySharedPreferences.getInstance().getDepartment();
        phone = MySharedPreferences.getInstance().getPhone();

        mEtName.setText(name);
        mEtDepartment.setText(department);
        mEtPhone.setText(phone);

    }

    /**
     * 修改个人资料
     */
    private void updatePersonalData() {

        department = mEtDepartment.getText().toString();
        name = mEtName.getText().toString();
        phone = mEtPhone.getText().toString();


        MyRetrofit.getInstance()
                .getRetrofitServiceImpl()
                .updatePersonalData(new PersonalInfoBody(department,name,phone))//发送http请求
                .map(new MyRetrofit.ServerResponseFunc<EmptyObject>())
                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
                .observeOn(AndroidSchedulers.mainThread())//主线程启动观察者执行下面代码
                .subscribe(new RxSubscribe2<EmptyObject>(this) {
                    @Override
                    public void onNext(EmptyObject bean) {
                        MySharedPreferences.getInstance().setName(name);
                        MySharedPreferences.getInstance().setDepartment(department);
                        MySharedPreferences.getInstance().setPhone(phone);
                        Toast.makeText(getBaseContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
        ;

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                updatePersonalData();
                break;
//            case R.id.:
//                break;
            default:
                break;
        }
    }

}
