package com.qyd.mydailyreport.adapter;

import android.content.Context;

import com.linqinen.library.adapter.BaseBindingAdapter;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.bean.ReportDetailBean;
import com.qyd.mydailyreport.databinding.AdapterMyDailyReportDetailBinding;

/**
 * Created by 林 on 2017/10/11.
 */

public class MyDailyReportDetailAdapter extends BaseBindingAdapter<ReportDetailBean.ReportDetailListBean,AdapterMyDailyReportDetailBinding> {


    public MyDailyReportDetailAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_my_daily_report_detail;
    }

    @Override
    protected void onBindItem(AdapterMyDailyReportDetailBinding binding, ReportDetailBean.ReportDetailListBean bean, int position) {
        binding.setMBean(bean);
    }

}
