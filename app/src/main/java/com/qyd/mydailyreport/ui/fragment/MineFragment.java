package com.qyd.mydailyreport.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.linqinen.library.utils.LogT;
import com.linqinen.library.widget.CircleImageView;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.ui.activity.LoginActivity;
import com.qyd.mydailyreport.ui.activity.person.PersonalDataActivity;
import com.qyd.mydailyreport.utils.MySharedPreferences;
import com.qyd.mydailyreport.widget.MyCustomView03;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 林 on 2017/10/20.
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.civ_head_portrait)
    CircleImageView mCivHeadPortrait;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_line)
    View mTvLine;
    @BindView(R.id.mcv_phone)
    MyCustomView03 mMcvPhone;
    @BindView(R.id.mcv_department)
    MyCustomView03 mMcvDepartment;
    @BindView(R.id.mcv_position)
    MyCustomView03 mMcvPosition;
    @BindView(R.id.mcv_modify)
    MyCustomView03 mMcvModify;
    @BindView(R.id.mcv_remind)
    MyCustomView03 mMcvRemind;
    @BindView(R.id.btn_exit)
    Button mBtnExit;


    Unbinder unbinder;

    private static final int REQUEST_CODE = 1001;

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
        mMcvDepartment.setText(MySharedPreferences.getInstance().getDepartment());
        mMcvPosition.setText(MySharedPreferences.getInstance().getPosition());
        mMcvPhone.setText(MySharedPreferences.getInstance().getPhone());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(REQUEST_CODE == requestCode && resultCode == Activity.RESULT_OK){
            LogT.i("刷新数据:" );
            mTvName.setText(MySharedPreferences.getInstance().getName());
            mMcvDepartment.setText(MySharedPreferences.getInstance().getDepartment());
            mMcvPhone.setText(MySharedPreferences.getInstance().getPhone());
            mMcvPosition.setText(MySharedPreferences.getInstance().getPosition());
        }
    }

    @OnClick({R.id.civ_head_portrait, R.id.mcv_modify, R.id.mcv_remind, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_head_portrait:
                break;
            case R.id.mcv_modify:
                startActivityForResult(new Intent(getContext(), PersonalDataActivity.class),REQUEST_CODE);
                break;
            case R.id.mcv_remind:
                break;
            case R.id.btn_exit:
                new AlertDialog.Builder(getActivity())
                        .setTitle("是否退出当前账号[" + MySharedPreferences.getInstance().getName() + "]？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MySharedPreferences.getInstance().setToken("");
                                LogT.i("name:" + MySharedPreferences.getInstance().getName());
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }


    /**
     * Fragment中使用toolbar
     * https://www.cnblogs.com/mengdd/p/5590634.html
     */
//    private void initToolbar(){
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//    }
}
