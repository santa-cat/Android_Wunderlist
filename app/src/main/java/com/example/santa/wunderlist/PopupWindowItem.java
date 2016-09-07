package com.example.santa.wunderlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by santa on 16/7/15.
 */
public class PopupWindowItem extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private ImageView mImageView;
    private TextView mTextView;
    private PopupWindow popupWindow;
    private int mPopuViewHeight;
    private ListView listView;
    private View contentView;
    public final static String TEXT = "text";
    public final static String IMAGE = "image";
//    private String[] popText = {"a", "b", "ccccccc", "d", "e", "f", "g", "h"};
    private ArrayList<HashMap<String, Object>> popData;


    public PopupWindowItem(Context context) {
        this(context, null);
    }

    public PopupWindowItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupWindowItem(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PopupWindowItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOrientation(VERTICAL);
        mContext = context;

        mImageView = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        mImageView.setLayoutParams(layoutParams);
        addView(mImageView);

        mTextView = new TextView(context);
        mTextView.setLayoutParams(layoutParams);
        mTextView.setText("共享");
        mTextView.setTextColor(Color.WHITE);
        addView(mTextView);

        contentView = LayoutInflater.from(context).inflate(R.layout.popwindow_tiplist, null);

        listView = (ListView) contentView.findViewById(R.id.pop_listview);
        listView.setOnItemClickListener(new ItemClickListener());
        ViewStub viewStub= new ViewStub(context);
        listView.addFooterView(viewStub);
        /**初始化PopupWindow*/
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //点击推出,要设置backgroundDrawable
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        /**设置PopupWindow弹出和退出时候的动画效果*/
        popupWindow.setAnimationStyle(R.style.animation);
        popupWindow.setOutsideTouchable(true);
        setOnClickListener(this);

    }

    private class  ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
    }


    private ListAdapter getAdapter() {
        List<HashMap<String, Object>> data = new ArrayList<>();
        for (int i = 0 ; i<popData.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(TEXT, popData.get(i).get(TEXT));
            data.add(map);
        }

        return new SimpleAdapter(mContext, data, R.layout.popwindow_item, new String[]{TEXT}, new int[] {R.id.pop_text});
    }

    public void setImage(Drawable drawable) {
        if (null != mImageView) {
            mImageView.setImageDrawable(drawable);
        }
    }

    public void setText(String text) {
        if (null != mTextView) {
            mTextView.setText(text);
        }
    }

    public void setMenuItem(ArrayList<HashMap<String, Object>> list) {
        popData=list;
        listView.setAdapter(getAdapter());
        contentView.measure(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopuViewHeight = contentView.getMeasuredHeight();
    }




    @Override
    public void onClick(View v) {

        int[] location = new int[2];
        v.getLocationOnScreen(location);
        Log.d("DEBUG", "location[1] = " +location[1]);
        Log.d("DEBUG", "popupWindow.getHeight() = " +popupWindow.getHeight());
        Log.d("DEBUG", "mPopuViewHeight = " +mPopuViewHeight);
        if (popData != null) {
            popupWindow.showAtLocation(this, Gravity.NO_GRAVITY, 0, location[1] - mPopuViewHeight * popData.size() - listView.getDividerHeight());
        }
    }
}
