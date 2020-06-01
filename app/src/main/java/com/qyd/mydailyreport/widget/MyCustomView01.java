package com.qyd.mydailyreport.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qyd.mydailyreport.R;
import com.qyd.mydailyreport.utils.DensityUtil;

import androidx.constraintlayout.widget.ConstraintLayout;


/**
 * Created by Ian on 2019/11/21.
 */
public class MyCustomView01 extends FrameLayout {


    private Context mContext;


    private boolean isArrowVisible, isBottomLineVisible, isRedTagVisible, isSingleLine;
    private boolean isEditable;

    private int endIconRes;
    private ImageView ivEndIcon;

    private String title, valueHint;

    private CharSequence mcv02_value;

    private TextView tvTitle, tvValue;

    private EditText etValue;

    private View viewBottomLine;

    private int titleColor, valueColor;
    private int titleSize, titleWidth, valueSize;

    private int inputType;
    private int inputLength;

    public void setMcv01_title(String title){
        setTitle(title);
    }

    public void setTitle(String title) {
        this.title = title;
        if (isRedTagVisible) {
            setTitleRedTag();
        } else {
            tvTitle.setText(title);
        }
    }

    /**
     * 为title 设置 红星标记，比如 您的房间：*
     */
    public void setTitleRedTag() {
        SpannableString spannableString = new SpannableString(title + "*");
//        spannableString.setSpan(new RelativeSizeSpan(0.5f), totalFee.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), title.length(), title.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTitle.setText(spannableString);
    }

    private TextView getValueObject() {
        if (etValue == null) {
            return tvValue;
        } else {
            return etValue;
        }
    }

    public void setMcv01_value(String mcv02_value) {
        this.mcv02_value = mcv02_value;
        getValueObject().setText(mcv02_value);
    }

    public void setText(CharSequence value){
        setValue(value);
    }

    public CharSequence getText(){
        return getValue();
    }

    public String getValue() {
        return getValueObject().getText().toString();
    }

    public void setValue(CharSequence value) {
        this.mcv02_value = value;
        getValueObject().setText(mcv02_value);
        if (isEditable && etValue != null) {
            etValue.setSelection(etValue.getText().length());
        }
    }

    public String getValueHint() {
        return valueHint;
    }

    public void setValueHint(String valueHint){
        this.valueHint = valueHint;
        getValueObject().setHint(valueHint);
    }


    public void setTitleColor(int titleColor) {
        this.titleColor = mContext.getResources().getColor(titleColor);
        tvTitle.setTextColor(this.titleColor);
    }

    public void setValueColor(int valueColor) {
        this.valueColor = mContext.getResources().getColor(valueColor);
        getValueObject().setTextColor(this.valueColor);
    }

    public void setArrowVisible(boolean arrowVisible) {
        isArrowVisible = arrowVisible;
        if (isArrowVisible) {
            ivEndIcon.setVisibility(View.VISIBLE);
        } else {
            ivEndIcon.setVisibility(View.GONE);
        }
    }

    public void setArrowVisibleAndClickable(boolean arrowVisible) {
        setArrowVisible(arrowVisible);
        setClickable(arrowVisible);
    }

    public MyCustomView01(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public MyCustomView01(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initAttr(attrs);
        initView(context);
    }

    public MyCustomView01(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(attrs);
        initView(context);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MyCustomView01);
        title = a.getString(R.styleable.MyCustomView01_mcv01_title);
        titleColor = a.getColor(R.styleable.MyCustomView01_mcv01_title_color, getResources().getColor(R.color.gray_8C9198));
        titleSize = a.getInt(R.styleable.MyCustomView01_mcv01_title_size, 14);
        titleWidth = a.getDimensionPixelSize(R.styleable.MyCustomView01_mcv01_title_width, DensityUtil.dp2px(mContext, 70));

        mcv02_value = a.getString(R.styleable.MyCustomView01_mcv01_value);
        valueHint = a.getString(R.styleable.MyCustomView01_mcv01_value_hint);
        inputType = a.getInt(R.styleable.MyCustomView01_mcv01_value_input_type, 0);
        inputLength = a.getInt(R.styleable.MyCustomView01_mcv01_value_length, 0);
        valueColor = a.getColor(R.styleable.MyCustomView01_mcv01_value_color, getResources().getColor(R.color.black_333333));
        valueSize = a.getInt(R.styleable.MyCustomView01_mcv01_title_size, 14);
        isEditable = a.getBoolean(R.styleable.MyCustomView01_mcv01_value_editable, false);

        endIconRes = a.getResourceId(R.styleable.MyCustomView01_mcv01_end_icon, R.drawable.arrow_right);
        isArrowVisible = a.getBoolean(R.styleable.MyCustomView01_mcv01_end_icon_visible, false);
        isBottomLineVisible = a.getBoolean(R.styleable.MyCustomView01_mcv01_view_bottom_line_visible, true);
        isRedTagVisible = a.getBoolean(R.styleable.MyCustomView01_mcv01_title_tag_visible, false);
        isSingleLine = a.getBoolean(R.styleable.MyCustomView01_mcv01_single_line, true);

        a.recycle();

    }

    /**
     * 初始化控件
     */
    private void initView(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.my_custom_view01_layout, this, true);
        tvTitle = view.findViewById(R.id.tv_title);

        if (isEditable) {
            etValue = view.findViewById(R.id.et_value);
            initValue(etValue);

            /*如果输入类型是电话号码，并且没有额外设置长度，则默认长度为11*/
            if (inputType == 1) {
                etValue.setInputType(InputType.TYPE_CLASS_PHONE);
                if (inputLength == 0) {
                    inputLength = 11;
                }
            }
            if (inputLength > 0) {
                etValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(inputLength)});
            }

            /*如果输入类型是电话号码，并且没有额外设置长度，则默认长度为11*/
            if (inputType == 1) {
                etValue.setInputType(InputType.TYPE_CLASS_PHONE);
                if (inputLength == 0) {
                    inputLength = 11;
                }
            }
            if (inputLength > 0) {
                etValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(inputLength)});
            }

            etValue.setSelection(etValue.getText().length());
        }else{
            tvValue = view.findViewById(R.id.tv_value);
            initValue(tvValue);
        }

        initTitle();

        ivEndIcon = view.findViewById(R.id.iv_end_arrow);
        ivEndIcon.setImageResource(endIconRes);

        if (isArrowVisible) {
            ivEndIcon.setVisibility(View.VISIBLE);
        }
        View viewBottomLine = view.findViewById(R.id.view_bottom_line);

        if (isBottomLineVisible) {
            viewBottomLine.setVisibility(View.VISIBLE);
        } else {
            viewBottomLine.setVisibility(View.GONE);
        }

    }

    private void initTitle(){

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) tvTitle.getLayoutParams();

        layoutParams.topToTop = getValueObject().getId();
        layoutParams.width = titleWidth;
        tvTitle.setLayoutParams(layoutParams);

        if (isRedTagVisible) {
            setTitleRedTag();
        } else {
            tvTitle.setText(title);
        }
        tvTitle.setTextSize(titleSize);
        tvTitle.setTextColor(titleColor);
    }

    private void initValue(TextView textView){
        textView.setVisibility(View.VISIBLE);
        textView.setText(mcv02_value);
        textView.setTextSize(valueSize);
        textView.setHint(valueHint);
        textView.setTextColor(valueColor);
        if (isSingleLine) {
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        }

    }

    public void addTextChangedListener(TextWatcher watcher) {
        if(etValue != null){
            etValue.addTextChangedListener(watcher);
        }
    }
}
