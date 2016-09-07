package com.example.santa.wunderlist;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by santa on 16/7/3.
 */
public class LetterDrawable extends Drawable {
    private int mAlpha = 0;
    private Paint mCirclePaint = new Paint();
    private Paint mTextPaint = new Paint();
    private String mText = "null";

    public LetterDrawable(String str) {
        initPaint();
        mText = str;
    }

    public LetterDrawable(String str, int circleColor, int textColor) {
        initPaint();
        setColor(circleColor, textColor);
        mText = str;
    }

    public void initPaint(){
        mCirclePaint.setStrokeCap(Paint.Cap.SQUARE);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.BLUE);

        mTextPaint.setStrokeCap(Paint.Cap.ROUND);
        mTextPaint.setStrokeJoin(Paint.Join.ROUND);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.CYAN);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        //mTextPaint.setTextSize(700);

//        mTextPaint
    }


    @Override
    public void draw(Canvas canvas) {
        final int saveCount = canvas.save();
        Rect bounds = getBounds();
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), (bounds.width()- 30)/2, mCirclePaint);

        final int textSize = bounds.width()*2/4;
        mTextPaint.setTextSize(textSize);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (bounds.height() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(mText, bounds.centerX(), baseline, mTextPaint);

        canvas.restoreToCount(saveCount);
    }

    @Override
    public void setAlpha(int alpha) {
        mAlpha = alpha;
    }

    public void setCircleColor(int color) {
        mCirclePaint.setColor(color);
    }

    public void setTextColor(int color) {
        mTextPaint.setColor(color);
    }

    public void setColor(int circleColor, int textColor) {
        mCirclePaint.setColor(circleColor);
        mTextPaint.setColor(textColor);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mCirclePaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
