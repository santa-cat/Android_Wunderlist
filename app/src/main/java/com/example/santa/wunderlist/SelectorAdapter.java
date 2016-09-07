package com.example.santa.wunderlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by santa on 16/7/19.
 */
public class SelectorAdapter extends BaseAdapter {
    private ArrayList<String> mData;
    private Context mContext;

    public SelectorAdapter(Context context, ArrayList<String> list) {
        mData = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position%mData.size());
    }

    @Override
    public long getItemId(int position) {
        return position%mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SelectorHolder selectorHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_selector, null);
            selectorHolder = new SelectorHolder();
            selectorHolder.textView = (TextView) convertView.findViewById(R.id.selector_text);
            convertView.setTag(selectorHolder);
        } else {
            selectorHolder = (SelectorHolder) convertView.getTag();
        }
        selectorHolder.textView.setText(mData.get(position%mData.size()));
        return convertView;
    }

    private class SelectorHolder {
        public TextView textView;
    }
}
