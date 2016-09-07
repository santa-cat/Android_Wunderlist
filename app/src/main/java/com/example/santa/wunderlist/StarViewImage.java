package com.example.santa.wunderlist;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by santa on 16/7/15.
 */
public class StarViewImage extends ImageView implements View.OnClickListener{
    private StarViewDrawable starViewDrawable;
    private Listener mListener;

    public StarViewImage(Context context) {
        this(context, null);
    }

    public StarViewImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarViewImage(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public StarViewImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setPadding(0, 0, 0, 0);
        starViewDrawable = new StarViewDrawable();
        setImageDrawable(starViewDrawable);
        setOnClickListener(this);
    }

    private void setDrawableState() {
        starViewDrawable.changeState();
    }

    @Override
    public void onClick(View v) {
        setDrawableState();
        if (null != mListener) {
            mListener.onClick(this);
        }
    }

    public boolean isChecked() {
        return starViewDrawable.isChecked();
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public interface Listener {
        void onClick(View v);
    }
}
