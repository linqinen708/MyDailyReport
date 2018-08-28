package com.qyd.mydailyreport.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.linqinen.library.adapter.MyFragmentPagerAdapter;
import com.linqinen.library.widget.NoScrollViewPager;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.fragment.DepartmentFragment;
import com.qyd.mydailyreport.fragment.MineFragment;
import com.qyd.mydailyreport.fragment.ReportRecordFragment;
import com.qyd.mydailyreport.utils.MySharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity2 extends BasicActivity {

    @BindView(R.id.viewPager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home2);
//        DataBindingUtil.setContentView(this,R.layout.activity_home2);
//        ActivityHomeBinding binding;
        ButterKnife.bind(this);

        token = MySharedPreferences.getInstance().getToken();
        id = MySharedPreferences.getInstance().getId();
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
