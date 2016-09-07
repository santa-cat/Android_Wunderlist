package com.example.santa.wunderlist;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by santa on 16/7/14.
 */
public class TipListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TipListItemTouchHelperCallback.onDragListener {
    private Context mContext;
    private ArrayList<Integer> mDataTag;
    public final static int TAG_TIPTODO = 1;
    public final static int TAG_TIPDONE = 2;
    public final static int TAG_TIPTEXT = 3;
    private ArrayList<HashMap<String, Object>> mData;
    private ArrayList<HashMap<String, Object>> mDataTodo;
    private ArrayList<HashMap<String, Object>> mDataDone;
    public final static String CONTENT = "content";
//    private int textPosition;
    private RecyclerView.ViewHolder mTextHolder;
    private View.OnClickListener mListener;

    public TipListAdapter(Context context, ArrayList<HashMap<String, Object>> listTodo , ArrayList<HashMap<String, Object>> listDone, ArrayList<Integer> tag) {
        mContext = context;
        mDataTodo = listTodo;
        mDataDone = listDone;
        mDataTag = tag;

        mData = new ArrayList<>();
        mData.addAll(listTodo);

        initClickListener();
    }

    private void initClickListener() {
        mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, EditTipActivity.class);
                i.putExtra("content", ((TextView)v).getText());
                mContext.startActivity(i);
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TAG_TIPTODO || viewType == TAG_TIPDONE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_tiplist, parent, false);
            return new TipHolder(view);
        } else if(viewType == TAG_TIPTEXT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_tiptext, parent, false);
            return new TipTextHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(getItemViewType(position) == TAG_TIPTODO) {
            ((TipHolder)holder).content.setText(mData.get(position).get(CONTENT).toString());
            ((TipHolder)holder).starView.setListener(new StarViewImage.Listener() {
                @Override
                public void onClick(View v) {
                    if(((StarViewImage)v).isChecked()) {
                        onItemDrag(holder.getAdapterPosition(), 0);
                    }
                }
            });
            ((TipHolder)holder).content.setOnClickListener(mListener);
            ((TipHolder)holder).checkbox.setChecked(false);
            ((TipHolder)holder).checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeToFinish(holder);
                }
            });
            ((TipHolder)holder).swipeLayout.setListener(new SwipeLayout.OperatorListener() {
                @Override
                public void onDelete() {
                    deleteItem(holder.getAdapterPosition());
                }

                @Override
                public void onEdit() {

                }
            });
        } else if(getItemViewType(position) == TAG_TIPTEXT) {
            mTextHolder = holder;
            ((TipTextHolder)holder).content.setText(mData.get(position).get(CONTENT).toString());
            ((TipTextHolder)holder).content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeDoneTipVisiable(v);
                }
            });
        } else if (getItemViewType(position) == TAG_TIPDONE) {
            ((TipHolder)holder).content.setText(mData.get(position).get(CONTENT).toString());
            ((TipHolder)holder).content.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
            ((TipHolder)holder).content.getPaint().setAntiAlias(true);
            ((TipHolder)holder).checkbox.setChecked(true);
            ((TipHolder)holder).checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeToUnfinish(holder);
                }
            });
            ((TipHolder)holder).content.setOnClickListener(mListener);

        }
    }

    private void deleteItem(int position) {
        notifyItemRemoved(position);
        mDataTag.remove(0);
        mDataTodo.remove(position);
        mData.remove(position);
    }

    private void changeToUnfinish(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        mDataTag.remove(mDataTag.size()-1);
        mDataTag.add(0, TAG_TIPTODO);

        int index = position - mDataTodo.size();
//        Log.d("DEBUG", "mDataDone.get(index) = "+mDataDone.get(index));
        HashMap<String, Object> map = mDataDone.get(index);
        mDataTodo.add(mDataTodo.size()-1, mDataDone.get(index));
        mDataDone.remove(index);
        mData.clear();
        mData.addAll(mDataTodo);
        mData.addAll(mDataDone);
        notifyItemMoved(position, mTextHolder.getAdapterPosition());
    }

    private void changeToFinish(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        mDataTag.remove(0);
        mDataTag.add(TAG_TIPDONE);
//        notifyItemChanged(position);
//        mData
//        Log.d("DEBUG", " from position = "+position);
//        int i = mTextHolder.getAdapterPosition()+1;
//        Log.d("DEBUG", "to position = "+i);
//        Collections.swap(mData, position, mTextHolder.getAdapterPosition() + 1);
//        notifyItemMoved(position, mTextHolder.getAdapterPosition() + 1);


        mDataDone.add(0, mDataTodo.get(position));
        mDataTodo.remove(position);
        mData.clear();
        mData.addAll(mDataTodo);
        mData.addAll(mDataDone);
//        notifyDataSetChanged();
        notifyItemMoved(position, mTextHolder.getAdapterPosition());

//
////        int index = mData.indexOf(v.getText().toString());
//        Log.d("DEBUG", "position = "+position);
////        Log.d("DEBUG", "text = "+v.getText().toString());
//        onItemDrag(position, mTextHolder.getAdapterPosition() + 1);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {

        return mDataTag.get(position);
    }

    private void changeDoneTipVisiable(View view) {
        if (mData.size() == mDataDone.size() + mDataTodo.size()) {
            mData.removeAll(mDataDone);
            mData.get(mTextHolder.getAdapterPosition()).put(CONTENT, "显示已完成任务");
        } else {
            mData.addAll(mDataDone);
            mData.get(mTextHolder.getAdapterPosition()).put(CONTENT, "隐藏已完成任务");
        }

        notifyDataSetChanged();
    }

    @Override
    public boolean onItemDrag(int fromPosition, int toPosition) {
        Collections.swap(mData, fromPosition, toPosition);
        Collections.swap(mDataTodo, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    public class TipHolder extends RecyclerView.ViewHolder implements TipListItemTouchHelperCallback.OnDragVHListener {
        private TextView content;
        private CheckBox checkbox;
        private SwipeLayout swipeLayout;
        private StarViewImage starView;
        AnimatorSet upSet, downSet;
        public TipHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.tip_content);
            checkbox = (CheckBox) itemView.findViewById(R.id.tip_checkbox);
            starView = (StarViewImage) itemView.findViewById(R.id.tip_star);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.tip_layout);

            //创建动画
            ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f);
            ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f);
            ObjectAnimator upAnim = ObjectAnimator.ofFloat(itemView, "translationZ", 10);
            ObjectAnimator upColor = ObjectAnimator.ofArgb(itemView, "backgroundColor", Color.LTGRAY);
            upSet = new AnimatorSet();
            upSet.playSequentially(scaleXAnim, scaleYAnim, upAnim, upColor);
            upSet.setDuration(100);
            upSet.setInterpolator(new DecelerateInterpolator());

            ObjectAnimator downAnim = ObjectAnimator.ofFloat(itemView, "translationZ", 0);
            ObjectAnimator scaleXDownAnim = ObjectAnimator.ofFloat(itemView, "scaleX", 1.0f);
            ObjectAnimator scaleYDownAnim = ObjectAnimator.ofFloat(itemView, "scaleY", 1.0f);
            ObjectAnimator downColor = ObjectAnimator.ofArgb(itemView, "backgroundColor", 0);
            downSet = new AnimatorSet();
            downSet.playSequentially(scaleXDownAnim, scaleYDownAnim, downAnim, downColor);
            downSet.setDuration(100);
            downSet.setInterpolator(new DecelerateInterpolator());

        }

        @Override
        public void onItemFinish() {
            itemView.clearAnimation();
            downSet.start();
        }

        @Override
        public void onItemSelected() {
            itemView.clearAnimation();
            upSet.start();
        }
    }



    public class TipTextHolder extends RecyclerView.ViewHolder{
        private TextView content;
        public TipTextHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.tip_content);

        }
    }
}
