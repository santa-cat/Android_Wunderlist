package com.example.santa.wunderlist;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

/**
 * Created by santa on 16/7/15.
 */


public class Star{
    private Paint mPaint = new Paint();
    private Path path = new Path();
    private int starColor = Color.WHITE;

    public Star() {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(4);
    }

    public void draw(Canvas canvas, Rect bounds) {
        int width = bounds.width();
        int height = bounds.height();
        int r = Math.min(width, height) / 4;


        //画星星
        canvas.translate(width / 2, height / 2);
        canvas.rotate(-18);
        calculateStarPath(r, r*2/ 5);
        canvas.drawPath(path, mPaint);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }


    private void calculateStarPath(float outR, float inR) {
        path.reset();
        path.moveTo(outR*cos(72*0), outR*sin(72*0));
        path.moveTo(outR*cos(72*0), outR*sin(72*0));
        path.lineTo(inR*cos(72*0+36), inR*sin(72*0+36));
        path.lineTo(outR*cos(72*1), outR*sin(72*1));
        path.lineTo(inR*cos(72*1+36), inR*sin(72*1+36));
        path.lineTo(outR*cos(72*2), outR*sin(72*2));
        path.lineTo(inR*cos(72*2+36), inR*sin(72*2+36));
        path.lineTo(outR*cos(72*3), outR*sin(72*3));
        path.lineTo(inR*cos(72*3+36), inR*sin(72*3+36));
        path.lineTo(outR*cos(72*4), outR*sin(72*4));
        path.lineTo(inR*cos(72*4+36), inR*sin(72*4+36));
        path.close();
    }

    public void setPaintStyle(Paint.Style style) {
        mPaint.setStyle(style);
    }

    public void setmPaintColor(int color) {
        mPaint.setColor(color);
    }

    float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
    }

    float sin(int num){
        return (float) Math.sin(num*Math.PI/180);
    }
}
