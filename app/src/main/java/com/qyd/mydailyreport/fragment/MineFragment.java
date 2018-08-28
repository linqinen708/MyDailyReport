package com.qyd.mydailyreport.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linqinen.library.widget.CircleImageView;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.activity.LoginActivity;
import com.qyd.mydailyreport.activity.person.PersonalDataActivity;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvName.setText(MySharedPreferences.getInstance().getName());
        mTvDepartment.setText(MySharedPreferences.getInstance().getDepartment());
        mTvPhone.setText(MySharedPreferences.getInstance().getPhone());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * Fragment中使用toolbar
     * https://www.cnblogs.com/mengdd/p/5590634.html
     */
//    private void initToolbar(){
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//    }

    @OnClick({R.id.textView9, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView9:
                startActivity(new Intent(getContext(), PersonalDataActivity.class));
                break;
            case R.id.btn_exit:
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
                break;
        }
    }
}
