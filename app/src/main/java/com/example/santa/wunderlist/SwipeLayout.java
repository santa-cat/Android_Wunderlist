package com.example.santa.wunderlist;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by santa on 16/7/16.
 */
public class SwipeLayout extends ViewGroup {
    private final static String DEBUG = "SwipeLayout";
    private ImageView mEditView;
    private ImageView mDeleteView;
    private LinearLayout mOpButtonLayout;
    private int mColorDelete = 0xffd94c45;
    private int mColorEdit = 0xff378ad3;
    private int mLastPosition = -1;
    private int mActivePointerId = -1;
    private View mContent;
    private float mDensity;
    private int mMoveMax;
    private int mMoveMin;
    private VelocityTracker mVelocityTracker;
    private OperatorListener mListener = null;
    private boolean isDragged = false;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SwipeLayout(final Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        mDensity = context.getResources().getDisplayMetrics().density;
        MarginLayoutParams layoutParams = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mEditView = new ImageView(context);
        mEditView.setLayoutParams(layoutParams);
        mEditView.setBackgroundColor(mColorEdit);
        mEditView.setPadding(getPxFormDp(8), getPxFormDp(8), getPxFormDp(8), getPxFormDp(8));
        mEditView.setImageResource(R.mipmap.setting);
        mEditView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onEdit();
                }
                Toast.makeText(context, "you have click the edit", Toast.LENGTH_SHORT).show();
                smoothScrollTo(mMoveMin);
            }
        });


        mDeleteView = new ImageView(context);
        mDeleteView.setLayoutParams(layoutParams);
        mDeleteView.setBackgroundColor(mColorDelete);
        mDeleteView.setPadding(getPxFormDp(8), getPxFormDp(8), getPxFormDp(8), getPxFormDp(8));
        mDeleteView.setImageDrawable(context.getResources().getDrawable(R.mipmap.delete));
        mDeleteView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onDelete();
                }
                Toast.makeText(context, "you have click the delete", Toast.LENGTH_SHORT).show();
                smoothScrollTo(mMoveMin);
            }
        });

        layoutParams = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //TODO:为啥没用
        layoutParams.setMargins(50, 50, 50, 50);
//        layoutParams.gravity = Gravity.CENTER;
        mOpButtonLayout = new LinearLayout(context);
        mOpButtonLayout.setLayoutParams(layoutParams);
//        mOpButtonLayout.setBackgroundColor(Color.BLACK);
        mOpButtonLayout.setOrientation(LinearLayout.HORIZONTAL);

        mOpButtonLayout.addView(mEditView);
        mOpButtonLayout.addView(mDeleteView);
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int paddingLeft   = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        MarginLayoutParams layoutParams;
        int heightMeasure = 0;
        int widthMeasure = 0;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (null != mContent) {
            measureChildWithMargins(mContent, widthMeasureSpec, 0, heightMeasureSpec, 0);
            layoutParams = (MarginLayoutParams) mContent.getLayoutParams();
            int height =  layoutParams.topMargin + layoutParams.bottomMargin + mContent.getMeasuredHeight() + paddingTop + paddingBottom;
            heightMeasure = Math.max(height, heightMeasure);
            widthMeasure += layoutParams.leftMargin + layoutParams.rightMargin + mContent.getMeasuredWidth() + paddingLeft + paddingRight;
        }

        //match
        if(null != mOpButtonLayout) {
            layoutParams = (MarginLayoutParams) mOpButtonLayout.getLayoutParams();
            measureChildWithMargins(mOpButtonLayout, widthMeasureSpec, 0, heightMeasureSpec, 0);
            int height =  layoutParams.topMargin + layoutParams.bottomMargin + mOpButtonLayout.getMeasuredHeight() + paddingTop + paddingBottom;
            if(layoutParams.height != LayoutParams.MATCH_PARENT) {
                heightMeasure = Math.max(height, heightMeasure);
            }
            widthMeasure += layoutParams.leftMargin + layoutParams.rightMargin + mOpButtonLayout.getMeasuredWidth() + paddingLeft + paddingRight;
            mMoveMax = layoutParams.leftMargin + layoutParams.rightMargin + mOpButtonLayout.getMeasuredWidth() + paddingLeft + paddingRight;
            mMoveMin = 0;
            Log.d(DEBUG, "margin = "+layoutParams.leftMargin);
        }



        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : widthMeasure, heightMode == MeasureSpec.EXACTLY ? heightSize : heightMeasure);

        forceUniformHeight(widthMeasureSpec);
    }


    private void forceUniformHeight(int WidthMeasureSpec) {
        int count = getChildCount();
        // Pretend that the linear layout has an exact size.
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY);
        for (int i = 0; i< count; ++i) {
            final View child = getChildAt(i);
//            if (child.getVisibility() != GONE) {
                LayoutParams lp = ((LayoutParams)child.getLayoutParams());

                if (lp.height == LayoutParams.MATCH_PARENT) {
                    // Temporarily force children to reuse their old measured height
                    // FIXME: this may not be right for something like wrapping text?
                    int oldWidth = lp.width;
                    lp.width = child.getMeasuredWidth();

                    // Remeasue with new dimensions
                    measureChildWithMargins(child, WidthMeasureSpec, 0, uniformMeasureSpec, 0);
                    lp.width = oldWidth;
                }
            }
//        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft   = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        if (null != mContent) {
            MarginLayoutParams lp = (MarginLayoutParams) mContent.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = paddingTop + lp.topMargin;
            final int right = left + mContent.getMeasuredWidth();
            final int bottom = top + mContent.getMeasuredHeight();
            mContent.layout(left, top, right, bottom);
        }

        if(null != mOpButtonLayout) {
            MarginLayoutParams lp = (MarginLayoutParams) mOpButtonLayout.getLayoutParams();
            final int left = getRight()+ lp.leftMargin + paddingLeft;
            final int right = left + mOpButtonLayout.getMeasuredWidth();

            final int top = getMeasuredHeight()/2 - mOpButtonLayout.getMeasuredHeight()/2;
            final int bottom = top + mOpButtonLayout.getMeasuredHeight();
            mOpButtonLayout.layout(left, top, right, bottom);
        }
//        if(null != mEditView) {
//            MarginLayoutParams lp = (MarginLayoutParams) mEditView.getLayoutParams();
//            final int left = getRight()- lp.rightMargin - paddingRight;
//            final int right = left + mEditView.getMeasuredWidth();
//            final int top = paddingTop + lp.topMargin;
//            final int bottom = top + mEditView.getMeasuredHeight();
//            mEditView.layout(left, top, right, bottom);
//        }
//
//        if(null != mDeleteView) {
//            MarginLayoutParams lp = (MarginLayoutParams) mDeleteView.getLayoutParams();
//            final int left = getRight()- lp.rightMargin - paddingRight;
//            final int right = left + mDeleteView.getMeasuredWidth();
//            final int top = paddingTop + lp.topMargin;
//            final int bottom = top + mDeleteView.getMeasuredHeight();
//            mDeleteView.layout(left, top, right, bottom);
//        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean isIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                Log.d(DEBUG ,"releaseVelocityTracker");
                break;
            case MotionEvent.ACTION_DOWN:
//                mScroller.abortAnimation();
                mLastPosition = (int)ev.getX();
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(ev);
                Log.d(DEBUG ,"initOrResetVelocityTracker");

                break;
            case MotionEvent.ACTION_MOVE:
                obtainVelocityTracker(ev);
                Log.d(DEBUG ,"obtainVelocityTracker");
                isIntercept = true;
                break;

        }
        return isIntercept || isNeedIntercept(ev);
    }



    private boolean isNeedIntercept(MotionEvent ev) {
        int offsetX = (int)ev.getX() - getLeft();
        int buttomLeft = getRight() - mOpButtonLayout.getWidth();

        Log.d(DEBUG, "offsetX = "+offsetX);
        Log.d(DEBUG, "buttomLeft = "+buttomLeft);
        return isDragged && (offsetX < buttomLeft);
    }
    
    
    //如果recycler滑动冲突
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(DEBUG, "opButtonWidth = "+opButtonWidth);
//        Log.d(DEBUG, "action = "+event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastPosition = (int) event.getX();
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                if(isNeedIntercept(event)) {
                    return true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                final int activePointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
                if (activePointerIndex == -1) {
                    Log.e("refresh", "Invalid pointerId=" + mActivePointerId + " in onTouchEvent");
                    break;
                }
                obtainVelocityTracker(event);
                int curPosition = (int) (event.getX());
                moveBy(mLastPosition - curPosition);
                requestLayout();
                mLastPosition = curPosition;
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, mActivePointerId);
                release(initialVelocity);

                break;
        }
        return super.onTouchEvent(event);
    }


    private void moveBy(int offset) {
//        Log.d(DEBUG, "mMoveMax = "+mMoveMax);
//        Log.d(DEBUG, "offset = "+offset);
//        Log.d(DEBUG, "getScrollX = "+getScrollX());

        scrollBy(getConsumedMove(offset), 0);
    }


    private int getConsumedMove(int offset) {
        final int curScrollX = getScrollX();
        if ((curScrollX + offset) > mMoveMax) {
            return mMoveMax - curScrollX;
        } else if (curScrollX + offset < mMoveMin) {
            return mMoveMin - curScrollX;
        }
        return offset;
    }


    private void obtainVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


    private boolean isAttachMaxMove() {
        return getScrollX() >= mMoveMax;
    }

    private boolean isAttachMinMove() {
        return getScrollX() <= mMoveMin;
    }

    private void release(int velocityX) {
//        Log.d(DEBUG, "velocityX = "+velocityX);
        //表示左滑
        if (velocityX < 0) {
            smoothScrollTo(mMoveMax);
            isDragged = true;
        } else {
            smoothScrollTo(mMoveMin);
            isDragged = false;
        }
    }

    private void smoothScrollTo(int targetPosition) {
        ValueAnimator animator = ObjectAnimator.ofFloat(getScrollX(), targetPosition);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curX = (float) animation.getAnimatedValue();
                moveBy((int)curX - getScrollX());
            }
        });
        animator.start();
    }

    private int getPxFormDp(int dp) {
        return (int) (mDensity*dp);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount()>1) {
            Log.e("refresh", "PullRefreshLayout only can host 1 elements");
            throw new IllegalStateException("PullRefreshLayout only can host 1 elements");
        } else {
            mContent = getChildAt(0);
        }
        addView(mOpButtonLayout);
//        addView(mDeleteView);
    }



    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        @SuppressWarnings({"unused"})
        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }


    public void setListener(OperatorListener listener) {
        mListener = listener;
    }

    public interface OperatorListener{
        //是否需要SwipeLayout
        void onDelete();
        void onEdit();
    }




}
