package com.qyd.mydailyreport.ui.activity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.ui.activity.base.BaseActivity;
import com.qyd.mydailyreport.adapter.MyDailyReportDetailAdapter;
import com.qyd.mydailyreport.bean.ReportDetailBean;
import com.qyd.mydailyreport.retrofit.MyRetrofit;
import com.qyd.mydailyreport.retrofit.RxSubscribe2;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OneDayDailyReportDetailActivity extends BaseActivity {


    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    private MyDailyReportDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day_daily_report_detail);
        ButterKnife.bind(this);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                LogT.i("year:" + year + ",month:" + month + ",dayOfMonth:" + dayOfMonth);
                getReportDetailList(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new YyzProductDetailAdapter(this, mList);
        mAdapter = new MyDailyReportDetailAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

    }


    private void getReportDetailList(String date) {

        Map<String, Object> map = new HashMap<>();
        map.put("date", date);

        LogT.i("map:" + MyRetrofit.getParameters(map));
        MyRetrofit.getInstance()
                .getRetrofitServiceImpl()
                .getReportDetailByDate(map)//发送http请求
                .map(new MyRetrofit.ServerResponseFunc<ReportDetailBean>())
                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
                .observeOn(AndroidSchedulers.mainThread())//发送请求给主线程执行下面代码
                .subscribe(new RxSubscribe2<ReportDetailBean>(this) {
                    @Override
                    public void onNext(ReportDetailBean bean) {
                        LogT.i("bean:" + bean.toString());
                        if (bean.getReportDetailList().size() > 0) {
                            mAdapter.getItems().clear();
                            mAdapter.getItems().addAll(bean.getReportDetailList());
                        } else {
                            Toast.makeText(getBaseContext(), "暂无数据", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }
}
