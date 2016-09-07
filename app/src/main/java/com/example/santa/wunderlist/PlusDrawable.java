package com.example.santa.wunderlist;

import android.content.Context;
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
public class PlusDrawable extends Drawable {
    private Plus mPlus = new Plus();

    public PlusDrawable() {
    }

    public PlusDrawable(int color) {
        mPlus.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect b = getBounds();
        mPlus.draw(canvas, b);
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



    private class Plus{
        private Paint mPaint = new Paint();

        public Plus() {
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.WHITE);
            mPaint.setStrokeWidth(5);
        }

        public void draw(Canvas canvas, Rect bounds) {
            int width = bounds.width();
            int height = bounds.height();
            canvas.drawLine(width/2, 0, width/2, height, mPaint);
            canvas.drawLine(0, height/2, width, height/2, mPaint);
        }
        public void setColor(int color) {
            mPaint.setColor(color);
        }
    }
}
