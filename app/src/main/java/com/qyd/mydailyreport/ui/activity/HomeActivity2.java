package com.qyd.mydailyreport.ui.activity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.linqinen.library.adapter.MyFragmentPagerAdapter;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.ui.activity.base.BaseActivity;
import com.qyd.mydailyreport.ui.fragment.DepartmentFragment;
import com.qyd.mydailyreport.ui.fragment.MineFragment;
import com.qyd.mydailyreport.ui.fragment.ReportRecordFragment;
import com.qyd.mydailyreport.utils.MySharedPreferences;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity2 extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home2);
//        DataBindingUtil.setContentView(this,R.layout.activity_home2);
//        ActivityHomeBinding binding;
        ButterKnife.bind(this);

//        id = MySharedPreferences.getInstance().getId();
        initViewPager();
    }

    private void initViewPager(){
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        myFragmentPagerAdapter.addFragment(new DepartmentFragment(), "部门汇报");
        myFragmentPagerAdapter.addFragment(new ReportRecordFragment(), "记录");
        myFragmentPagerAdapter.addFragment(new MineFragment(), "我的");
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
