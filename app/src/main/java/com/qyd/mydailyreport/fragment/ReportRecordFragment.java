package com.qyd.mydailyreport.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.activity.BasicActivity;
import com.qyd.mydailyreport.bean.CurrentReportDetailBean;
import com.qyd.mydailyreport.databinding.FragmentReportRecordBinding;
import com.qyd.mydailyreport.retrofit.MyRetrofit;
import com.qyd.mydailyreport.retrofit.RxSubscribe2;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ReportRecordFragment extends BasicFragment {


//    public static ReportRecordFragment newInstance(String param1, String param2) {
//        ReportRecordFragment fragment = new ReportRecordFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

//    private MyDailyReportDetailAdapter mAdapter;

    private FragmentReportRecordBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_report_record, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_record, container, false);

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                LogT.i("year:" + year + ",month:" + month + ",dayOfMonth:" + dayOfMonth);
                getCurrentReport(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        });

//        initRecyclerView();

        return binding.getRoot();
    }




    private void initRecyclerView() {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mAdapter = new YyzProductDetailAdapter(this, mList);
//        mAdapter = new MyDailyReportDetailAdapter(getActivity());
//        mRecyclerView.setAdapter(mAdapter);

    }


    private void getCurrentReport(String date) {

        Map<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("token", BasicActivity.token);

        LogT.i("map:" + MyRetrofit.getParameters(map));
        MyRetrofit.getInstance()
                .getRetrofitServiceImpl()
                .getCurrentReport(map)//发送http请求
                .map(new MyRetrofit.ServerResponseFunc<CurrentReportDetailBean>())
                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
                .observeOn(AndroidSchedulers.mainThread())//发送请求给主线程执行下面代码
                .subscribe(new RxSubscribe2<CurrentReportDetailBean>(getActivity()) {
                    @Override
                    public void onNext(CurrentReportDetailBean bean) {
                        LogT.i("bean:" + bean.toString());
                        binding.includeMyRecord.setReportDetailBeanBean(bean.getReportDetailBean());
//                        if (bean.getReportDetailList().size() > 0) {
//                            mAdapter.getItems().clear();
//                            mAdapter.getItems().addAll(bean.getReportDetailList());
//                        } else {
//                            Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
//                        }
                    }

                });

    }
}
