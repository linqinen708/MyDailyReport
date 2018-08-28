package com.qyd.mydailyreport.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.adapter.MyDailyReportDetailAdapter;
import com.qyd.mydailyreport.bean.ReportDetailBean;
import com.qyd.mydailyreport.retrofit.MyRetrofit;
import com.qyd.mydailyreport.retrofit.RxSubscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 林 on 2017/10/20.
 */

public class DepartmentFragment extends BasicFragment {
    Unbinder unbinder;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_select_date)
    TextView mTvSelectDate;
    @BindView(R.id.spinner)
    Spinner mSpinner;


    private MyDailyReportDetailAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_department, container, false);
        unbinder = ButterKnife.bind(this, view);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                LogT.i("year:" + year + ",month:" + month + ",dayOfMonth:" + dayOfMonth);
                getReportDetailList(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        });

        initRecyclerView();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**初始化数据*/
    private void initData() {
        final List<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyDailyReportDetailAdapter(getActivity());
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
                .subscribe(new RxSubscribe<ReportDetailBean>(getActivity()) {
                    @Override
                    protected void _onNext(ReportDetailBean bean) {
                        LogT.i("bean:" + bean.toString());
                        if (bean.getReportDetailList().size() > 0) {
                            mAdapter.getItems().clear();
                            mAdapter.getItems().addAll(bean.getReportDetailList());
                        } else {
                            Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }

}
