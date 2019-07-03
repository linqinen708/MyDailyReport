package com.qyd.mydailyreport.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;
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

public class PersonalDataActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_department)
    EditText mEtDepartment;

    private String name, department;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);

        name = MySharedPreferences.getInstance().getName();
        department = MySharedPreferences.getInstance().getDepartment();
        id = MySharedPreferences.getInstance().getId();

        mEtName.setText(name);
        mEtDepartment.setText(department);

    }

    /**
     * 修改个人资料
     */
    private void updatePersonalData() {

        name = mEtName.getText().toString();
        department = mEtDepartment.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("department", department);
        map.put("name", name);

        LogT.i("map:" + MyRetrofit.getParameters(map));

        MyRetrofit.getInstance()
                .getRetrofitServiceImpl()
                .updatePersonalData(map)//发送http请求
                .map(new MyRetrofit.ServerResponseFunc<EmptyObject>())
                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
                .observeOn(AndroidSchedulers.mainThread())//主线程启动观察者执行下面代码
                .subscribe(new RxSubscribe2<EmptyObject>(this) {
                    @Override
                    public void onNext(EmptyObject bean) {
                        MySharedPreferences.getInstance().setName(name);
                        MySharedPreferences.getInstance().setDepartment(department);
                        Toast.makeText(getBaseContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
        ;

    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        updatePersonalData();
    }
}
