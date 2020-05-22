package com.qyd.mydailyreport.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by 林 on 2017/10/18.
 */

public class MyEditText extends AppCompatEditText {


    private AfterTextChangedListener mListener;

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (mListener != null) {
                mListener.content(s.toString());
            }
        }
    };

    public MyEditText(Context context) {
        this(context,null);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(watcher);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        super.addTextChangedListener(watcher);

    }

    public interface AfterTextChangedListener {
        void content(String content);
    }

    public void setAfterTextChanged(AfterTextChangedListener listener) {
        mListener = listener;

    }
}
