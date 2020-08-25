package com.qyd.mydailyreport.ui.activity.person;

import android.os.Bundle;
import android.view.View;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.retrofit.EmptyObject;
import com.qyd.mydailyreport.retrofit.MyRetrofit;
import com.qyd.mydailyreport.retrofit.RxSubscribe2;
import com.qyd.mydailyreport.ui.activity.base.BaseActivity;
import com.qyd.mydailyreport.utils.MySharedPreferences;
import com.qyd.mydailyreport.widget.MyCustomView01;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PersonalDataActivity extends BaseActivity {


    @BindView(R.id.mcv_name)
    MyCustomView01 mMcvName;
    @BindView(R.id.mcv_phone)
    MyCustomView01 mMcvPhone;
    @BindView(R.id.mcv_department)
    MyCustomView01 mMcvDepartment;
    @BindView(R.id.mcv_position)
    MyCustomView01 mMcvPosition;
    private String name, department, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);

        name = MySharedPreferences.getInstance().getName();
        department = MySharedPreferences.getInstance().getDepartment();
        phone = MySharedPreferences.getInstance().getPhone();

        LogT.i("name:" + name + ",department :" + department + ",phone:" + phone);

        mMcvName.setText(name);
        mMcvDepartment.setText(department);
        mMcvPhone.setText(phone);
        mMcvPosition.setText(MySharedPreferences.getInstance().getPosition());

    }

    /**
     * 修改个人资料
     */
    private void updatePersonalData() {

        PersonalInfoBody body = new PersonalInfoBody();
        body.setUser_name(mMcvName.getText().toString());
        body.setDepartment(mMcvDepartment.getText().toString());
        body.setPosition(mMcvPosition.getText().toString());
        body.setPhone(mMcvPhone.getText().toString());


        MyRetrofit.getInstance()
                .getRetrofitServiceImpl()
//                .updatePersonalData(mMcvDepartment.getText().toString())//发送http请求
                .updatePersonalData(body)//发送http请求
                .map(new MyRetrofit.ServerResponseFunc<EmptyObject>())
                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
                .observeOn(AndroidSchedulers.mainThread())//主线程启动观察者执行下面代码
                .subscribe(new RxSubscribe2<EmptyObject>(this) {
                    @Override
                    public void onNext(EmptyObject bean) {
                        MySharedPreferences.getInstance().setName(mMcvName.getText().toString());
                        MySharedPreferences.getInstance().setDepartment(mMcvDepartment.getText().toString());
                        MySharedPreferences.getInstance().setPosition(mMcvPosition.getText().toString());
                        MySharedPreferences.getInstance().setPhone(mMcvPhone.getText().toString());
                        showToast("修改成功");
                        setResult(RESULT_OK);                        finish();
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
