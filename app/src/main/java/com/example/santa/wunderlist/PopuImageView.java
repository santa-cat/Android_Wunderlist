package com.example.santa.wunderlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * Created by santa on 16/7/21.
 */
public class PopuImageView extends ImageView implements View.OnClickListener{

    private View contentView;
    private TimeSelectorView timeSelectorView;
    private PopupWindow mPopupWindow;
    private String mText = null;
    private Drawable mDrawableUnChecked;
    private Drawable mDrawableChecked;
    private PopuListener mListener;


    public PopuImageView(Context context) {
        this(context, null);
    }

    public PopuImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopuImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PopuImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOnClickListener(this);

//        mDrawableChecked = context.getResources().getDrawable(R.mipmap.deadday_ed, null);
//        mDrawableUnChecked = context.getResources().getDrawable(R.mipmap.deadday, null);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PopuImageView, defStyleAttr, defStyleRes);
        mDrawableChecked = a.getDrawable(R.styleable.PopuImageView_piv_drawableChecked);
        mDrawableUnChecked = a.getDrawable(R.styleable.PopuImageView_piv_drawableUnChecked);
        String timeType = a.getString(R.styleable.PopuImageView_piv_TimeViewType);
        a.recycle();
        setImageDrawable(mDrawableUnChecked);

        contentView = LayoutInflater.from(context).inflate(R.layout.popu_timeselector, null);
        timeSelectorView = (TimeSelectorView) contentView.findViewById(R.id.timeselector);
        timeSelectorView.setListener(getTimeChangeListener());
        if (null != timeType && !timeType.equals("")) {
            timeSelectorView.setTimeSelectorType(context, timeType);
        }
        /**初始化PopupWindow*/
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);// 取得焦点
        //点击推出,要设置backgroundDrawable
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        /**设置PopupWindow弹出和退出时候的动画效果*/
//        mPopupWindow.setAnimationStyle(R.style.animotorPdop);
        mPopupWindow.setOutsideTouchable(true);


    }



    private TimeSelectorView.TimeChangeListener getTimeChangeListener() {
        TimeSelectorView.TimeChangeListener listener = new TimeSelectorView.TimeChangeListener() {
            @Override
            public void scrollFinish(String time) {
                setImageDrawable(mDrawableChecked);
                mText = time;
            }

            @Override
            public void onFinish() {
//                mPopupWindow.dismiss();
                popupDismiss();
            }

            @Override
            public void onCancle() {
//                mPopupWindow.dismiss();
                popupDismiss();
                setImageDrawable(mDrawableUnChecked);
                mText = null;
            }
        };
        return listener;
    }

    @Override
    public void onClick(View v) {
        popupShow(v);
    }

    private void popupShow(View v){
        if(null != mListener) {
            mListener.onShow();
        }
        mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    private void popupDismiss() {
        mPopupWindow.dismiss();
        if(null != mListener) {
            mListener.onDismiss();
        }
    }

    public void setPopupListener(PopuListener listener) {
        mListener = listener;
    }

    public interface PopuListener{
        void onDismiss();
        void onShow();
    }

}
