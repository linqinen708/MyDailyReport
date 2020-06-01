package com.qyd.mydailyreport.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.qyd.mydailyreport.R;

import androidx.annotation.Nullable;


/**
 * Created by Ian on 2020/5/19.
 */
public class MyCustomView03 extends View {

    private Paint mPaintTitle;
    private Paint mPaintValue;
    private Paint mPaintRedTag;
    private Paint mPaintBottomLine;
    private Paint mPaintEndIcon;

    private Bitmap endIcon;

    private Context mContext;
    private float density;

    private boolean isEndIconVisible, isBottomLineVisible, isRedTagVisible, isSingleLine;

    private int endIconRes;

    private String mcv03_title;

    private String mcv03_value;

    private int titleColor, valueColor;
    private int titleSize, titleWidth, valueSize, valueGravity;

    public static final int GRAVITY_START = 0;
    public static final int GRAVITY_MIDDLE = 1;
    public static final int GRAVITY_END = 2;

    public MyCustomView03(Context context) {
        super(context);

        initData(context);
        initView();
    }

    public MyCustomView03(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initAttr(attrs);
        initView();
    }

    public MyCustomView03(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initAttr(attrs);
        initView();
    }

    private float dp2px(int value) {
        return density * value;
    }


    private void initAttr(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MyCustomView03);
        mcv03_title = a.getString(R.styleable.MyCustomView03_mcv03_title);
        titleColor = a.getColor(R.styleable.MyCustomView03_mcv03_title_color, getResources().getColor(R.color.gray_8C9198));
        titleSize = a.getInt(R.styleable.MyCustomView03_mcv03_title_size, 14);
        titleWidth = a.getDimensionPixelSize(R.styleable.MyCustomView03_mcv03_title_width, (int) dp2px(75));

        mcv03_value = a.getString(R.styleable.MyCustomView03_mcv03_value);
        valueColor = a.getColor(R.styleable.MyCustomView03_mcv03_value_color, getResources().getColor(R.color.black_333333));
        valueSize = a.getInt(R.styleable.MyCustomView03_mcv03_value_size, 14);
        valueGravity = a.getInt(R.styleable.MyCustomView03_mcv03_value_gravity, GRAVITY_START);

        endIconRes = a.getResourceId(R.styleable.MyCustomView03_mcv03_end_icon, R.drawable.arrow_right);
        isEndIconVisible = a.getBoolean(R.styleable.MyCustomView03_mcv03_end_icon_visible, false);
        isBottomLineVisible = a.getBoolean(R.styleable.MyCustomView03_mcv03_view_bottom_line_visible, true);
        isRedTagVisible = a.getBoolean(R.styleable.MyCustomView03_mcv03_title_tag_visible, false);
        isSingleLine = a.getBoolean(R.styleable.MyCustomView03_mcv03_single_line, true);

        a.recycle();

    }

    /**
     * 初始化数据
     */
    private void initData(Context context) {
        mContext = context;
        density = context.getResources().getDisplayMetrics().density;
//        LogT.i("density:" + density);
    }

    /**
     * 初始化控件
     */
    private void initView() {
//       LogT.i(" getId():" +  getId());
        initPaintTitle();
        initPaintValue();
        if (isRedTagVisible) {
            initPaintRedTag();
        }
        if (isBottomLineVisible) {
            initPaintBottomLine();
        }
        if (isEndIconVisible) {
            initPaintEndIcon();
        }
    }

    private void initPaintTitle() {
        mPaintTitle = new Paint();
        mPaintTitle.setColor(titleColor);
        mPaintTitle.setTextSize(dp2px(titleSize));
        mPaintTitle.setAntiAlias(true);
//        mPaintTitle.setStyle(Paint.Style.FILL);
//        mPaintTitle.set
//        mPaintTitle.setTypeface(Typeface.DEFAULT_BOLD);
//        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void initPaintValue() {
        mPaintValue = new Paint();
        mPaintValue.setColor(valueColor);
        mPaintValue.setTextSize(dp2px(valueSize));
        mPaintValue.setAntiAlias(true);
//        mPaintValue.setStyle(Paint.Style.FILL);
//        mPaintValue.setStrokeWidth(12);
        switch (valueGravity) {
            case GRAVITY_START:
                mPaintValue.setTextAlign(Paint.Align.LEFT);
                break;
            case GRAVITY_MIDDLE:
                mPaintValue.setTextAlign(Paint.Align.CENTER);
                break;
            case GRAVITY_END:
                mPaintValue.setTextAlign(Paint.Align.RIGHT);
                break;

        }
    }

    private void initPaintRedTag() {
        mPaintRedTag = new Paint();
        mPaintRedTag.setColor(Color.RED);
        mPaintRedTag.setTextSize(dp2px(titleSize));
        mPaintRedTag.setAntiAlias(true);
    }

    private void initPaintBottomLine() {
        mPaintBottomLine = new Paint();
        mPaintBottomLine.setColor(0xFFF1F2F4);
//        mPaintBottomLine.setStyle(Paint.Style.STROKE);
        mPaintBottomLine.setStrokeWidth(dp2px(1));
    }

    private void initPaintEndIcon() {
        if (mPaintEndIcon == null) {
            mPaintEndIcon = new Paint();
            endIcon = BitmapFactory.decodeResource(getResources(), endIconRes);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        LogT.i("getMeasuredWidth():" + getMeasuredWidth() + ", getMeasuredHeight():" + getMeasuredHeight() + ",getWidth():" + getWidth() + ",getHeight():" + getHeight());
    }


    @Override
    public void layout(int left, int top, int right, int bottom) {
        super.layout(left, top, right, bottom);
//        LogT.i("left:" + left + ", top:" + top + ", right:" + right + ", bottom:" + bottom);
//        LogT.i("getMeasuredWidth():" + getMeasuredWidth() + ", getMeasuredHeight():" + getMeasuredHeight() + ",getWidth():" + getWidth() + ",getHeight():" + getHeight());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogT.i("开始绘制");
        float textTitleY = getMeasuredHeight() - mPaintTitle.getTextSize() - dp2px(4);
        float textValueY = getMeasuredHeight() - mPaintValue.getTextSize() - dp2px(4);
        float endIconX = getMeasuredWidth() - dp2px(15);
        if (isEndIconVisible) {
            endIconX = getMeasuredWidth() - endIcon.getWidth() - dp2px(15);
        }
        if(!TextUtils.isEmpty(mcv03_title)){
        canvas.drawText(mcv03_title, dp2px(15), textTitleY, mPaintTitle);
        }
        if (!TextUtils.isEmpty(mcv03_value)) {
            switch (valueGravity) {
                case GRAVITY_START:
                    canvas.drawText(mcv03_value, dp2px(15) + titleWidth, textValueY, mPaintValue);
                    break;
                case GRAVITY_MIDDLE:
                    canvas.drawText(mcv03_value, (dp2px(15) + titleWidth + (endIconX - dp2px(5))) / 2, textValueY, mPaintValue);
                    break;
                case GRAVITY_END:
                    canvas.drawText(mcv03_value, endIconX - dp2px(5), textValueY, mPaintValue);
                    break;
            }
        }
        if (isRedTagVisible) {
            canvas.drawText("*", dp2px(15) + mcv03_title.length() * mPaintTitle.getTextSize(), textTitleY, mPaintRedTag);
        }

        if (isBottomLineVisible) {
            canvas.drawLine(0, getMeasuredHeight() - 2, getMeasuredWidth(), getMeasuredHeight() - 2, mPaintBottomLine);
        }

        if (isEndIconVisible) {
            canvas.drawBitmap(endIcon, endIconX, endIcon.getHeight() - dp2px(1), mPaintEndIcon);
        }
    }


    public String getMcv03_title() {
        return mcv03_title;
    }

    public void setMcv03_title(String mcv03_title) {
        this.mcv03_title = mcv03_title;
        invalidate();
    }

    public String getMcv03_value() {
        return mcv03_value;
    }

    public void setText(String text){
        setMcv03_value(text);
    }

    public void setMcv03_value(String mcv03_value) {
        this.mcv03_value = mcv03_value;
        invalidate();
    }

    public void setEndIconVisible(boolean endIconVisible) {
        isEndIconVisible = endIconVisible;
        if (isEndIconVisible) {
            initPaintEndIcon();
        }
        invalidate();
    }
}
