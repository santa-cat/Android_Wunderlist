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
public class StarViewDrawable extends Drawable {
    private int backgroundColor = Color.RED;
    private int starCheckedColor = Color.WHITE;
    private int starUnCheckedColor = 0xff666666;
    private Paint mPaint = new Paint();
    private Path path = new Path();
    private int STATE_CHECKED = 1;
    private int STATE_UNCHECKED = 2;
    private int STATE = STATE_UNCHECKED;

    private Star mStar = new Star();

    public StarViewDrawable() {
    }

    public StarViewDrawable(int color) {
        mStar.setColor(color);
    }

    public void changeState() {
        STATE = (STATE_CHECKED == STATE) ? STATE_UNCHECKED : STATE_CHECKED;
        invalidateSelf();
    }

    public boolean isChecked() {
        return STATE_CHECKED == STATE;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect b = getBounds();
        if (STATE == STATE_CHECKED) {
            calculateBackPath(b.width(), b.height());
            canvas.drawPath(path, mPaint);
            mStar.setColor(starCheckedColor);
            mStar.setPaintStyle(Paint.Style.FILL);
        } else {
            mStar.setColor(starUnCheckedColor);
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

    public void calculateBackPath(int width, int height) {
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        path.reset();
        path.moveTo(width/6, 0);
        path.lineTo(width/6, height*7/8);
        path.lineTo(width/2, height*6/8);
        path.lineTo(width*5/6, height*7/8);
        path.lineTo(width*5/6, 0);
        path.close();

    }
}
