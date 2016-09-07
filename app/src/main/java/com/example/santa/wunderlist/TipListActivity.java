package com.example.santa.wunderlist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by santa on 16/7/15.
 */
public class TipListActivity extends AppCompatActivity {
    private ArrayList<HashMap<String, Object>> mDataTodo;
    private ArrayList<HashMap<String, Object>> mDataDone;
    private ArrayList<Integer> mDataTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiplist);

        initToolBar();
        initEditText();
        initRecyclerView();
        initPopuMenu();
    }

    private void initToolBar() {
        Toolbar toolBar = (Toolbar) this.findViewById(R.id.toolbar);
        assert toolBar != null;
        toolBar.setTitle("");
        setSupportActionBar(toolBar);

        //设置左上角返回箭头
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initEditText(){
        ImageView editPlus = (ImageView) findViewById(R.id.edit_plus);
        editPlus.setImageDrawable(new PlusDrawable(this.getResources().getColor(R.color.white)));
        EditText editText = (EditText) findViewById(R.id.tip_edit);
        editText.clearFocus();
//        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initRecyclerView() {
        initData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tip_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new TipListAdapter(this, mDataTodo, mDataDone, mDataTag));
        ItemTouchHelper.Callback callback = new TipListItemTouchHelperCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initPopuMenu(){

        PopupWindowItem itemSort = (PopupWindowItem) findViewById(R.id.pop_sort);
        itemSort.setText("排序");
        itemSort.setMenuItem(getPopMenuSortData());

        PopupWindowItem itemMore = (PopupWindowItem) findViewById(R.id.pop_more);
        itemMore.setText("更多");
        itemMore.setMenuItem(getPopMenuMoreData());
    }

    private ArrayList<HashMap<String, Object>> getPopMenuSortData() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put(PopupWindowItem.TEXT, "按字母顺序排序");
            data.add(map);
        }
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put(PopupWindowItem.TEXT, "按到期日排序");
            data.add(map);
        }
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put(PopupWindowItem.TEXT, "按创建日排序");
            data.add(map);
        }
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put(PopupWindowItem.TEXT, "按受托人排序");
            data.add(map);
        }
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put(PopupWindowItem.TEXT, "按优先级排序");
            data.add(map);
        }
        return data;
    }

    private ArrayList<HashMap<String, Object>> getPopMenuMoreData() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put(PopupWindowItem.TEXT, "编辑清单");
            data.add(map);
        }
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put(PopupWindowItem.TEXT, "勿扰");
            data.add(map);
        }
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put(PopupWindowItem.TEXT, "以电子邮件发送清单");
            data.add(map);
        }
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put(PopupWindowItem.TEXT, "打印清单");
            data.add(map);
        }
        return data;
    }

    private void initData() {
        mDataTag = new ArrayList<>();
        mDataTodo = new ArrayList<>();
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "学习下拉刷新的字符串特效,学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习学习");
            mDataTodo.add(map);
            mDataTag.add(TipListAdapter.TAG_TIPTODO);

        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "ImageLoader的工作原理");
            mDataTodo.add(map);
            mDataTag.add(TipListAdapter.TAG_TIPTODO);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "recyclerview嵌套");
            mDataTodo.add(map);
            mDataTag.add(TipListAdapter.TAG_TIPTODO);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "买鸡蛋");
            mDataTodo.add(map);
            mDataTag.add(TipListAdapter.TAG_TIPTODO);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "买猫砂,买猫砂,买猫砂,买猫砂");
            mDataTodo.add(map);
            mDataTag.add(TipListAdapter.TAG_TIPTODO);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "textview.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); ");
            mDataTodo.add(map);
            mDataTag.add(TipListAdapter.TAG_TIPTODO);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "买包子");
            mDataTodo.add(map);
            mDataTag.add(TipListAdapter.TAG_TIPTODO);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "显示已完成任务");
            mDataTodo.add(map);
            mDataTag.add(TipListAdapter.TAG_TIPTEXT);
        }

        mDataDone = new ArrayList<>();
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "timefly app完成基本, timefly app完成基本timefly app完成基本timefly app完成基本timefly app完成基本timefly app完成基本");
            mDataTag.add(TipListAdapter.TAG_TIPDONE);
            mDataDone.add(map);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "viewpager实现轮播");
            mDataTag.add(TipListAdapter.TAG_TIPDONE);
            mDataDone.add(map);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "周杰伦音乐界面基本完成");
            mDataTag.add(TipListAdapter.TAG_TIPDONE);
            mDataDone.add(map);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(TipListAdapter.CONTENT, "好奇心界面");
            mDataTag.add(TipListAdapter.TAG_TIPDONE);
            mDataDone.add(map);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tiplist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_edit:
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
