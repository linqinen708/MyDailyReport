package com.qyd.mydailyreport.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.aspsine.swipetoloadlayout.view.ClassicLoadMoreFooterView;
import com.aspsine.swipetoloadlayout.view.TwitterRefreshHeaderView;
import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.activity.person.PersonalDataActivity;
import com.qyd.mydailyreport.adapter.MyDailyReportDetailAdapter;
import com.qyd.mydailyreport.bean.ReportDetailBean;
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

public class HomeActivity extends BasicActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadMoreFooterView)
    ClassicLoadMoreFooterView mLoadMoreFooterView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    @BindView(R.id.refreshHeaderView)
    TwitterRefreshHeaderView mRefreshHeaderView;

    private MyDailyReportDetailAdapter mAdapter;

    private int pageNo = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        /*多重databinding时*/
//        binding.includeAppBarHome.toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        initRecyclerView();
        getReportDetailList();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new YyzProductDetailAdapter(this, mList);
        mAdapter = new MyDailyReportDetailAdapter(this);

        mRecyclerView.setAdapter(mAdapter);

        mSwipeToLoadLayout.setTargetView(mRecyclerView);
        mSwipeToLoadLayout.setHeaderView(mRefreshHeaderView);
        mSwipeToLoadLayout.setFooterView(mLoadMoreFooterView);

        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);

        /*配合上拉加载更多数据来使用*/
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        mSwipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });


    }

    @Override
    public void onRefresh() {
        LogT.i("正在刷新:");
 /*下拉刷新数据时，重置pageNumber = 1*/
        pageNo = 1;
        getReportDetailList();

    }

    @Override
    public void onLoadMore() {
        LogT.i("加载更多数据:");
        pageNo++;
        getReportDetailList();
    }

    private void getReportDetailList() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", MySharedPreferences.getInstance().getName());
        map.put("pageNo", pageNo);

        LogT.i("map:" + MyRetrofit.getParameters(map));
        MyRetrofit.getInstance()
                .getRetrofitServiceImpl()
                .getReportDetailList(map)//发送http请求
                .map(new MyRetrofit.ServerResponseFunc<ReportDetailBean>())
                .subscribeOn(Schedulers.io())//切换到io线程执行Http请求
                .observeOn(AndroidSchedulers.mainThread())//发送请求给主线程执行下面代码
                .subscribe(new RxSubscribe2<ReportDetailBean>(this) {
                    @Override
                    public void onNext(ReportDetailBean bean) {
                        LogT.i("bean:" + bean.toString());
                        if(pageNo == 1){
                            mAdapter.getItems().clear();
                        }
                        if(bean.getReportDetailList().size() > 0){
                            mAdapter.setShowFootView(false);
                        }else{
                            mAdapter.setShowFootView(true);
                        }
                        mAdapter.getItems().addAll(bean.getReportDetailList());
                        mSwipeToLoadLayout.setRefreshing(false);
                        mSwipeToLoadLayout.setLoadingMore(false);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        mSwipeToLoadLayout.setRefreshing(false);
                        mSwipeToLoadLayout.setLoadingMore(false);
                    }
                });
//                .subscribe(new Action1<ReportDetailBean>() {
//                    @Override
//                    public void call(ReportDetailBean bean) {
//                        LogT.i("bean:" + bean.toString());
//                        if(pageNo == 1){
//                            mAdapter.getItems().clear();
//                        }
//                        if(bean.getReportDetailList().size() > 0){
//                            mAdapter.setShowFootView(false);
//                        }else{
//                            mAdapter.setShowFootView(true);
//                        }
//                        mAdapter.getItems().addAll(bean.getReportDetailList());
//                        mSwipeToLoadLayout.setRefreshing(false);
//                        mSwipeToLoadLayout.setLoadingMore(false);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//
//                    }
//                });

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivity(new Intent(this,UploadReportActivity.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(this,OneDayDailyReportDetailActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this,UpdateOldReportActivity.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(this,PersonalDataActivity.class));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            new AlertDialog.Builder(this)
                    .setTitle("是否退出当前账号[" + MySharedPreferences.getInstance().getName() + "]？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MySharedPreferences.getInstance().setName(null);
                            startActivity(new Intent(getBaseContext(), LoginActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
