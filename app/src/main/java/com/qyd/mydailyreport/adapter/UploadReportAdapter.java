package com.qyd.mydailyreport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.widget.MyEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林 on 2017/10/13.
 */

public class UploadReportAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<String> mList;
    private List<MyEditText> mEditTextList = new ArrayList<>();

    public List<MyEditText> getEditTextList() {
        return mEditTextList;
    }

    public UploadReportAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    private class SimpleViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        MyEditText content;
        Button add, remove;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.tv_id);
            content = (MyEditText) itemView.findViewById(R.id.et_content);
            add = (Button) itemView.findViewById(R.id.btn_add);
            remove = (Button) itemView.findViewById(R.id.btn_remove);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_upload_report, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int newPosition = holder.getAdapterPosition();
        final SimpleViewHolder viewHolder = (SimpleViewHolder) holder;
        viewHolder.id.setText(newPosition + 1 + "、");
//        if(mStringList != null && newPosition  < mStringList.size() && mStringList.get(newPosition) != null){
//            LogT.i("触发:"  );
//            viewHolder.content.setText(mStringList.get(newPosition));
//        }
//        mList.set(newPosition, viewHolder.content);
        if(!mEditTextList.contains(viewHolder.content)){
//            LogT.i(":" + viewHolder.content);
            mEditTextList.add(viewHolder.content);
        }
//        LogT.i("viewHolder.content.:" + viewHolder.content+",mList.get(newPosition):"+mList.get(newPosition)+",newPosition:" + newPosition+ ", mList:"  + mList.toString());
        LogT.i("mList:" + mList.toString());
        viewHolder.content.setText(mList.get(newPosition));

        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LogT.i("add_newPosition:" + newPosition + ","+mList.toString());
//                mList.set(newPosition,viewHolder.content.getText().toString());
//                LogT.i(":" +mEditTextList.toString() );
//                for (int i = 0; i < mEditTextList.size(); i++) {
//                    mList.set(i,mEditTextList.get(i).getText().toString());
//                }
//                LogT.i("1111:" +viewHolder.content.getText().toString() +","+mList.toString());
                for (int i = 0; i < mEditTextList.size(); i++) {
                    mEditTextList.get(i).setAfterTextChanged(null);
                }
                mList.add(newPosition + 1, "");
//                LogT.i("2222:" + mList.toString());

//                LogT.i("mList.toString():" +mList.toString() );
                notifyDataSetChanged();
//                notifyItemInserted(newPosition+1);
            }
        });
        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogT.i("remove_newPosition:" + newPosition);
                if (mList.size() > 1) {
                    LogT.i("remove1111:" + mList.toString());
                    mList.remove(newPosition);
                    LogT.i("remove2222:" + mList.toString());
//                    mEditTextList.remove(mEditTextList.size()-1);
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(mContext,"再删就没有了",Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.content.setAfterTextChanged(new MyEditText.AfterTextChangedListener() {
            @Override
            public void content(String content) {
               LogT.i("add_newPosition:" + newPosition + ","+content);

                mList.set(newPosition,content);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
