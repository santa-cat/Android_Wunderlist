package com.example.santa.wunderlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by santa on 16/7/14.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
    private Context mContext;
    private ArrayList<HashMap<String, Object>> mData;
    public final static String ICON = "icon";
    public final static String TITLE = "title";
    public final static String REMIND = "remind";
    public final static String TIPNUM = "tipnum";

    public MainAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mainlist, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        holder.title.setText(mData.get(position).get(TITLE).toString());
        if (mData.get(position).get(REMIND) != null) {
            holder.remindNum.setImageDrawable((Drawable) mData.get(position).get(REMIND));
        }
        if (mData.get(position).get(TIPNUM) != null) {
            holder.tipNum.setText(mData.get(position).get(TIPNUM).toString());
        }
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, TipListActivity.class);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView title;
        private ImageView remindNum;
        private TextView tipNum;

        public MainHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.main_icon);
            title = (TextView) itemView.findViewById(R.id.main_title);
            remindNum = (ImageView) itemView.findViewById(R.id.main_remindNum);
            tipNum = (TextView) itemView.findViewById(R.id.main_tipNum);
        }
    }
}
