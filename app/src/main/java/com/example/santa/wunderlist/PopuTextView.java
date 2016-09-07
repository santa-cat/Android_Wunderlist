package com.example.santa.wunderlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by santa on 16/7/20.
 */
public class PopuTextView extends RelativeLayout{
    private View contentView;
    private TimeSelectorView timeSelectorView;
    private PopupWindow mPopupWindow;
    private String mDefault = "请选择时间";
    private TextView mTextView;
    private ImageView mImageView;
    private TimeSelectorView.TimeChangeListener mListener;
    private Drawable mDrawable;
    private int mTextColorUnChecked = 0xff666666;
    private int mTextColorChecked = 0xff378ad3;
    private int mTextSize = 18;

    public PopuTextView(Context context) {
        this(context, null);
    }

    public PopuTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopuTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PopuTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        float density = context.getResources().getDisplayMetrics().density;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, dm);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PopuTextView, defStyleAttr, defStyleRes);
        String text = array.getString(R.styleable.PopuTextView_ptv_text);
        mTextSize = array.getDimensionPixelSize(R.styleable.PopuTextView_ptv_textSize, mTextSize);
        String timeType = array.getString(R.styleable.PopuTextView_ptv_TimeViewType);
        mTextColorChecked = array.getColor(R.styleable.PopuTextView_ptv_textCheckedColor, mTextColorChecked);
        array.recycle();


        mTextView = new TextView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        mTextView.setLayoutParams(layoutParams);
        mTextView.setTextColor(mTextColorUnChecked);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            }
        });

        if(text != null && !text.equals("")) {
            mDefault = text;
        }
        mTextView.setText(mDefault);
        addView(mTextView);

        mDrawable = context.getResources().getDrawable(R.mipmap.cancle1);

        mImageView = new ImageView(context);
        layoutParams = new LayoutParams((int)(density*12), (int)(density*12));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        mImageView.setLayoutParams(layoutParams);
        mImageView.setImageDrawable(mDrawable);
        mImageView.setVisibility(INVISIBLE);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(mDefault);
                mTextView.setTextColor(mTextColorUnChecked);
                v.setVisibility(INVISIBLE);
            }
        });
        addView(mImageView);

        contentView = LayoutInflater.from(context).inflate(R.layout.popu_timeselector, null);


        timeSelectorView = (TimeSelectorView) contentView.findViewById(R.id.timeselector);

        if (null != timeType && !timeType.equals("")) {
            timeSelectorView.setTimeSelectorType(context, timeType);
        }
//        timeSelectorView = new TimeSelectorView(context, attrs, defStyleAttr);

        timeSelectorView.setListener(initTimeChangeListener());

        /**初始化PopupWindow*/
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);// 取得焦点
        //点击推出,要设置backgroundDrawable
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        /**设置PopupWindow弹出和退出时候的动画效果*/
        mPopupWindow.setAnimationStyle(R.style.animotorPdop);
        mPopupWindow.setOutsideTouchable(true);

    }

    private TimeSelectorView.TimeChangeListener initTimeChangeListener() {
        mListener = new TimeSelectorView.TimeChangeListener() {

            @Override
            public void scrollFinish(String time) {
                mTextView.setText(time);
                mImageView.setVisibility(VISIBLE);
                mTextView.setTextColor(mTextColorChecked);
//                mImageView.setImageDrawable(mDrawable);
            }

            @Override
            public void onFinish() {
                mPopupWindow.dismiss();
            }

            @Override
            public void onCancle() {
                mTextView.setText(mDefault);
                mTextView.setTextColor(mTextColorUnChecked);
                mImageView.setVisibility(INVISIBLE);
                mPopupWindow.dismiss();
            }
        };
        return mListener;
    }




}
