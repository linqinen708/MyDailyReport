package com.qyd.mydailyreport.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.ui.activity.base.BaseActivity;
import com.qyd.mydailyreport.adapter.UploadReportAdapter;
import com.qyd.mydailyreport.bean.CurrentReportDetailBean;
import com.qyd.mydailyreport.retrofit.EmptyObject;
import com.qyd.mydailyreport.retrofit.MyRetrofit;
import com.qyd.mydailyreport.retrofit.RxSubscribe2;
import com.qyd.mydailyreport.utils.MySharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("SetTextI18n")
public class UpdateOldReportActivity extends BaseActivity {


    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private UploadReportAdapter mAdapter;
    private List<String> mList = new ArrayList<>();

    private String name, department, date;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_old_report);
        ButterKnife.bind(this);

        initRecyclerView();
        getCurrentReport();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new YyzProductDetailAdapter(this, mList);
        mAdapter = new UploadReportAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void updateOldReportDetail() {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mList.size(); i++) {
            stringBuilder.append(i+1).append("、").append(mList.get(i)).append(";");
            if(i != mList.size()-1){
                stringBuilder.append("\n");
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("content", stringBuilder);
        map.put("date", date);

        LogT.i("map:" + MyRetrofit.getParameters(map));

        MyRetrofit.getInstance()
                .getRetrofitServiceImpl()
                .updateOldReportDetail(map)//发送http请求
                .map(new MyRetrofit.ServerResponseFunc<EmptyObject>())//自定义的错误
                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
                .observeOn(AndroidSchedulers.mainThread())//发送请求给主线程执行下面代码
                .subscribe(new RxSubscribe2<EmptyObject>(this) {
                    @Override
                    public void onNext(EmptyObject emptyObject) {
                        Toast.makeText(getBaseContext(), "发送成功", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void getCurrentReport() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", MySharedPreferences.getInstance().getName());

        LogT.i("map:" + MyRetrofit.getParameters(map));

        MyRetrofit.getInstance()
                .getRetrofitServiceImpl()
                .getCurrentReport(map)//发送http请求
                .map(new MyRetrofit.ServerResponseFunc<CurrentReportDetailBean>())//自定义的错误
                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
                .observeOn(AndroidSchedulers.mainThread())//发送请求给主线程执行下面代码
                .subscribe(new RxSubscribe2<CurrentReportDetailBean>(this) {

                    @Override
                    public void onNext(CurrentReportDetailBean currentReportDetailBean) {
                        LogT.i("bean:" + currentReportDetailBean.toString());
                        name = currentReportDetailBean.getReportDetailBean().getName();
                        date = currentReportDetailBean.getReportDetailBean().getDate();
                        department = currentReportDetailBean.getReportDetailBean().getDepartment();
                        id =currentReportDetailBean.getReportDetailBean().getId();

                        mTvName.setText("姓名：" + name);
                        mTvDepartment.setText("部门：" + department);
                        mTvDate.setText("日期：" + date);

                        String content[] = currentReportDetailBean.getReportDetailBean().getContent().split(";");

                        for (int i = 0; i < content.length; i++) {
                            if (i == 0) {
                                content[i] = content[i].substring(2);
                            } else {
                                content[i] = content[i].substring(3);
                            }
                        }

                        Collections.addAll(mList, content);
                        mAdapter.notifyDataSetChanged();
                    }
                });


    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        updateOldReportDetail();
    }
}
