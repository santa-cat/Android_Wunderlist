package com.example.santa.wunderlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by santa on 16/7/19.
 */
public class TimeSelectorView extends LinearLayout {
    private LinearLayout mContainerContent;
    private RelativeLayout mContainerHeader;
    private TextView mHeaderTitle;
    private TextView mHeadeBottomLeft;
    private TextView mHeaderBottomRight;
    private Paint mPaint = new Paint();
    private ArrayList<ListView> mTimeView;
    private int mTimeViewMidLine = -1;
    private int mChildHeight = -1;
    //options
    private int mTextSize = 18;
    private int mTitleColor = Color.BLACK;
    private int mBottomColor = 0xff378ad3;

    private ImageView imageViewTop;
    private ImageView imageViewBottom;
    private RelativeLayout layout;
    private int imageColor = 0xffffffff;
    private float imageAlpha = 0.75f;

    private String mType = "y-m-d";

    private TimeChangeListener mListener;

    public TimeSelectorView(Context context) {
        this(context, null);
    }

    public TimeSelectorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeSelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TimeSelectorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        setBackgroundColor(0xffffffff);

        float density = context.getResources().getDisplayMetrics().density;


        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeSelectorView, defStyleAttr, defStyleRes);

        String text = array.getString(R.styleable.TimeSelectorView_tsv_type);
        if (text !=null && !text.equals("")) {
            mType = text;
        }
//        mType = array.getString(R.styleable.TimeSelectorView_tsv_type);
        array.recycle();


        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.GRAY);

        mContainerHeader = new RelativeLayout(context);
        mContainerHeader.setBackgroundColor(0xfff9f9f9);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mContainerHeader.setLayoutParams(layoutParams);
        addView(mContainerHeader);

        mHeaderTitle = new TextView(context);
        RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l.addRule(RelativeLayout.CENTER_IN_PARENT);
        mHeaderTitle.setLayoutParams(l);
        mHeaderTitle.setTextSize(mTextSize);
        mHeaderTitle.setTextColor(mTitleColor);
        mHeaderTitle.setText("日期");
        mContainerHeader.addView(mHeaderTitle);

        l = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        l.addRule(RelativeLayout.CENTER_VERTICAL);
        mHeadeBottomLeft = new TextView(context);
        mHeadeBottomLeft.setPadding((int) (density*8), (int) (density*8), (int) (density*8), (int) (density*8));
        mHeadeBottomLeft.setLayoutParams(l);
        mHeadeBottomLeft.setTextSize(mTextSize);
        mHeadeBottomLeft.setTextColor(mBottomColor);
        mHeadeBottomLeft.setText("移除");
        mHeadeBottomLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mListener) {
                    mListener.onCancle();
                }
            }
        });
        mContainerHeader.addView(mHeadeBottomLeft);

        l = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        l.addRule(RelativeLayout.CENTER_VERTICAL);
        mHeaderBottomRight = new TextView(context);
        mHeaderBottomRight.setLayoutParams(l);
        mHeaderBottomRight.setTextSize(mTextSize);
        mHeaderBottomRight.setTextColor(mBottomColor);
        mHeaderBottomRight.setPadding((int) (density*8), (int) (density*8), (int) (density*8), (int) (density*8));
        mHeaderBottomRight.setText("完成");
        mHeaderBottomRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mListener) {
                    setText();
                    mListener.onFinish();
                }
            }
        });
        mContainerHeader.addView(mHeaderBottomRight);


        layout = new RelativeLayout(context);
        layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        layout.setBackgroundColor(Color.YELLOW);
        addView(layout);

        mTimeView = new ArrayList<>();
        mContainerContent = new LinearLayout(context);
        mContainerContent.setOrientation(HORIZONTAL);
        layout.addView(mContainerContent);

        setTimeSelectorType(context, mType);



        imageViewTop = new ImageView(context);
        imageViewTop.setBackgroundColor(imageColor);
        imageViewTop.setAlpha(imageAlpha);

        imageViewBottom = new ImageView(context);
        imageViewBottom.setBackgroundColor(imageColor);
        imageViewBottom.setAlpha(imageAlpha);
    }


    private void clearTimeSelector(){
        for (int i = 0 ; i<mTimeView.size();i++) {
            mContainerContent.removeView(mTimeView.get(i));
        }
        mTimeView.clear();
    }

    public void setTimeSelectorType(Context context ,String type) {
        clearTimeSelector();

        Calendar calendar = Calendar.getInstance();
        Log.d("DEBUG time", calendar.getTimeInMillis()+"");
        Log.d("DEBUG time", calendar.get(Calendar.MONTH)+"");
        Log.d("DEBUG time", calendar.get(Calendar.DAY_OF_MONTH)+"");
        Log.d("DEBUG time", calendar.get(Calendar.HOUR_OF_DAY)+"");
        Log.d("DEBUG time", calendar.get(Calendar.MINUTE)+"");

        ListView listView;
        if (type.contains("y") || type.contains("Y")) {
            listView = new ListView(context);
            ArrayList<String> years = SelectorContanst.getYears();
            listView.setAdapter(new SelectorAdapter(context, years));
            listView.setSelection(calendar.get(Calendar.YEAR) - 1900 + ((Integer.MAX_VALUE/years.size())/2)*years.size());
            mTimeView.add(listView);
        }
        if ((type.contains("m") || type.contains("M"))) {
            listView = new ListView(context);
            ArrayList<String> months = SelectorContanst.getMonths();
            listView.setAdapter(new SelectorAdapter(context, months));
            listView.setSelection(calendar.get(Calendar.MONTH) + ((Integer.MAX_VALUE/months.size())/2)*months.size());
            mTimeView.add(listView);
        }

        if (type.contains("d") || type.contains("D")) {
            listView = new ListView(context);
            ArrayList<String> days = SelectorContanst.getDays();
            listView.setAdapter(new SelectorAdapter(context, days));
            listView.setSelection(calendar.get(Calendar.DAY_OF_MONTH)-1 + ((Integer.MAX_VALUE/days.size())/2)*days.size());
            mTimeView.add(listView);
        }

        if (type.contains("h") || type.contains("H")) {
            listView = new ListView(context);
            ArrayList<String> hours = SelectorContanst.getHours();
            listView.setAdapter(new SelectorAdapter(context, SelectorContanst.getHours()));
            listView.setSelection(calendar.get(Calendar.HOUR_OF_DAY) + ((Integer.MAX_VALUE/hours.size())/2)*hours.size());
            mTimeView.add(listView);
        }

        if (type.contains("min") || type.contains("MIN")) {
            listView = new ListView(context);
            ArrayList<String> mins = SelectorContanst.getMins();
            listView.setAdapter(new SelectorAdapter(context, mins));
            listView.setSelection(calendar.get(Calendar.MINUTE) + ((Integer.MAX_VALUE/mins.size())/2)*mins.size());
            mTimeView.add(listView);
        }

        LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutParams.gravity = Gravity.CENTER;
        for (int i = 0 ; i<mTimeView.size();i++) {
            mTimeView.get(i).setLayoutParams(layoutParams);
//            mTimeView.get(i).setSelection(Integer.MAX_VALUE/2+1);
            mTimeView.get(i).setDividerHeight(0);
            mTimeView.get(i).setOnScrollListener(new TSScrollListener());
            mContainerContent.addView(mTimeView.get(i));
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private class TSScrollListener implements AbsListView.OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    //根据最顶端的便宜判断滑动后应该往哪边滑
                    View viewTop = (view.getChildAt(0));
                    if (viewTop.getTop() < 0) {
                        if (Math.abs(viewTop.getTop()) > viewTop.getHeight()/2) {
                            ((ListView)view).smoothScrollToPosition(view.getFirstVisiblePosition() + view.getChildCount()-1);
                        } else {
                            ((ListView)view).smoothScrollToPosition(view.getFirstVisiblePosition());
                        }
                    }
                    setText();
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }

    //将字符串写给textview
    private void setText(){
        if (mListener != null) {
            String time="";
            for (int i = 0 ; i<mTimeView.size(); i++) {
                ListView listView = mTimeView.get(i);
                time = time.concat(listView.getAdapter().getItem(listView.getFirstVisiblePosition() + (listView.getChildCount()-1)/2).toString());
            }
            mListener.scrollFinish(time);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //第一次进入需要调用
        if(mTimeViewMidLine == -1) {
            mTimeViewMidLine = (getHeight() + mContainerHeader.getBottom()) / 2;
            mChildHeight = ((ListView) mContainerContent.getChildAt(0)).getChildAt(0).getHeight();
            //以下两个都需要算出高度
            createImage();
            selectCurTime();
        }

        canvas.drawLine(0, mTimeViewMidLine - mChildHeight/2, getWidth(), mTimeViewMidLine - mChildHeight/2, mPaint);
        canvas.drawLine(0, mTimeViewMidLine + mChildHeight/2, getWidth(), mTimeViewMidLine + mChildHeight/2, mPaint);
    }


    private void selectCurTime() {
        for (int i = 0 ; i<mTimeView.size(); i++) {
            ListView listView = mTimeView.get(i);
            listView.setSelection(listView.getFirstVisiblePosition() -  (listView.getChildCount()-1)/2  );
        }
    }

    private void createImage(){
        imageViewTop.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, layout.getHeight()/2 -  mChildHeight/2));
        layout.addView(imageViewTop);

        RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, layout.getHeight()/2 -  mChildHeight/2);
        l.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        imageViewBottom.setLayoutParams(l);
        layout.addView(imageViewBottom);
    }

    public void setListener(TimeChangeListener listener) {
        mListener = listener;
    }

    public interface TimeChangeListener{
        void scrollFinish(String time);
        void onFinish();
        void onCancle();
    }
}
