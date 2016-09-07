package com.example.santa.wunderlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by santa on 16/7/15.
 */
public class StarEditImage extends ImageView implements View.OnClickListener{
    private StarEditDrawable starEditDrawable;
    private int mColor = Color.WHITE;

    public StarEditImage(Context context) {
        this(context, null);
    }

    public StarEditImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarEditImage(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public StarEditImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StarEditImage, defStyleAttr, defStyleRes);
        mColor = array.getColor(R.styleable.StarEditImage_sei_starColor, mColor);
        array.recycle();

        starEditDrawable = new StarEditDrawable(mColor);
        setImageDrawable(starEditDrawable);
        setOnClickListener(this);
    }



    private void setDrawableState() {
        starEditDrawable.changeState();
    }

    @Override
    public void onClick(View v) {
        setDrawableState();
    }
}
