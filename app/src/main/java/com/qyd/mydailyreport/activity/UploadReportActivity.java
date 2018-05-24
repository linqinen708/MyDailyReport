package com.qyd.mydailyreport.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.linqinen.library.utils.FormatUtils;
import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.adapter.UploadReportAdapter;
import com.qyd.mydailyreport.retrofit.EmptyObject;
import com.qyd.mydailyreport.retrofit.MyRetrofit;
import com.qyd.mydailyreport.retrofit.ServerException;
import com.qyd.mydailyreport.utils.MySharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**上传日报界面*/
@SuppressLint("SetTextI18n")
public class UploadReportActivity extends BasicActivity {

    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    private String name, department,date;

    private UploadReportAdapter mAdapter;
    private List<String> mList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_report);
        ButterKnife.bind(this);



        name = MySharedPreferences.getInstance().getName();
        department = MySharedPreferences.getInstance().getDepartment();
        date = FormatUtils.dateYMD(System.currentTimeMillis());

        mTvName.setText("姓名："+name);
        mTvDepartment.setText("部门："+department);
        mTvDate.setText("日期："+date);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new UploadReportAdapter(this,mList);
        mList.add(null);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void uploadReportDetail() {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mList.size(); i++) {
            stringBuilder.append(i+1).append("、").append(mList.get(i)).append(";");
            if(i != mList.size()-1){
                stringBuilder.append("\n");
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("department", department);
        map.put("content", stringBuilder);
        map.put("date", date);

        LogT.i("map:" + MyRetrofit.getParameters(map));

        MyRetrofit.getInstance()
                .getRetrofitServiceImpl()
                .uploadReportDetail(map)//发送http请求
                .map(new MyRetrofit.ServerResponseFunc<EmptyObject>())//自定义的错误
                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
                .observeOn(AndroidSchedulers.mainThread())//发送请求给主线程执行下面代码
                .subscribe(new Action1<EmptyObject>() {
                    @Override
                    public void call(EmptyObject bean) {

                        LogT.i("bean:" + bean.toString());
                        Toast.makeText(getBaseContext(),"发送成功",Toast.LENGTH_SHORT).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogT.i("出错了:" + throwable);
                        if (throwable instanceof ServerException) {
                            Toast.makeText(getBaseContext(), ((ServerException) throwable).message, Toast.LENGTH_SHORT).show();
                        } else {
                            throwable.printStackTrace();
                        }
                    }
                });

    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        uploadReportDetail();
    }
}
