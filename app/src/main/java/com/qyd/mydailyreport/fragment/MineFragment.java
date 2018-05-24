package com.qyd.mydailyreport.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linqinen.library.widget.CircleImageView;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.activity.LoginActivity;
import com.qyd.mydailyreport.utils.MySharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 林 on 2017/10/20.
 */

public class MineFragment extends BasicFragment {

    @BindView(R.id.civ_head_portrait)
    CircleImageView mCivHeadPortrait;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);

        mTvName.setText(MySharedPreferences.getInstance().getName());
        mTvDepartment.setText(MySharedPreferences.getInstance().getDepartment());


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_exit)
    public void onViewClicked() {

        new AlertDialog.Builder(getActivity())
                .setTitle("是否退出当前账号[" + MySharedPreferences.getInstance().getName() + "]？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MySharedPreferences.getInstance().setName(null);
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
