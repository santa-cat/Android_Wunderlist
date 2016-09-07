package com.example.santa.wunderlist;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by santa on 16/7/14.
 */
public class StarEditDrawable extends Drawable {
    private int starCheckedColor = Color.WHITE;
    private int STATE_CHECKED = 1;
    private int STATE_UNCHECKED = 2;
    private int STATE = STATE_UNCHECKED;

    private Star mStar = new Star();

    public StarEditDrawable() {
    }

    public StarEditDrawable(int color) {
        mStar.setColor(color);
    }

    public void changeState() {
        STATE = (STATE_CHECKED == STATE) ? STATE_UNCHECKED : STATE_CHECKED;
        invalidateSelf();

    }

    @Override
    public void draw(Canvas canvas) {
        Rect b = getBounds();
        if (STATE == STATE_CHECKED) {
            mStar.setPaintStyle(Paint.Style.FILL);
        } else {
            mStar.setPaintStyle(Paint.Style.STROKE);
        }
        mStar.draw(canvas, b);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

}
