package com.example.santa.wunderlist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ArrayList<HashMap<String, Object>> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        initRecyclerView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(new PlusDrawable());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddTipActivity.class);
                startActivity(intent);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.add_list);
        imageView.setImageDrawable(new PlusDrawable(getResources().getColor(R.color.addListColor)));
    }


    public void initRecyclerView() {
        initData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MainAdapter(this, mData));
    }

    private void initToolBar() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        assert toolBar != null;
        toolBar.setTitle("Santa");
        toolBar.setNavigationIcon(new LetterDrawable("S", this.getResources().getColor(R.color.letterColor), Color.WHITE));
        setSupportActionBar(toolBar);
    }

    private void initData() {
        mData = new ArrayList<>();
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(MainAdapter.TITLE, "收件箱");
            map.put(MainAdapter.REMIND, new LetterDrawable("1", this.getResources().getColor(R.color.remindBackColor), this.getResources().getColor(R.color.remindNumColor)));
            map.put(MainAdapter.TIPNUM, "12");
            mData.add(map);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(MainAdapter.TITLE, "今天");
            map.put(MainAdapter.REMIND, new LetterDrawable("1", this.getResources().getColor(R.color.remindBackColor), this.getResources().getColor(R.color.remindNumColor)));
            map.put(MainAdapter.TIPNUM, "2");
            mData.add(map);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(MainAdapter.TITLE, "周");
            map.put(MainAdapter.REMIND, new LetterDrawable("1", this.getResources().getColor(R.color.remindBackColor), this.getResources().getColor(R.color.remindNumColor)));
            map.put(MainAdapter.TIPNUM, "1");
            mData.add(map);
        }
        {
            HashMap<String ,Object> map = new HashMap<>();
            map.put(MainAdapter.TITLE, "初稿");
            mData.add(map);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
