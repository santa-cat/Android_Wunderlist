package com.example.santa.wunderlist;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by santa on 16/7/18.
 */
public class AddTipActivity extends AppCompatActivity implements AddTipFragment.ClickListener, GroupSelectFragment.ClickItemListener {
    private String[] mGroupData = {"收件箱", "初稿", "学习"};
    private int mGroupSelected = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtip);
//        initEditText();
        setDefaultFragment();
        initCancle();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        AddTipFragment fragment = new AddTipFragment();
        fragment.setListener(this);
        transaction.replace(R.id.id_content, fragment);
        transaction.commit();
    }

    //跳转到group选择页面
    @Override
    public void onClick(Fragment curFragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        GroupSelectFragment fragment = new GroupSelectFragment();
        fragment.setListener(this);
//        transaction.hide(curFragment);
        transaction.replace(R.id.id_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public String[] getGroupData() {
        return mGroupData;
    }

    public int getCurSelected() {
        return mGroupSelected;
    }

    public String getCurGroup() {
        return mGroupData[mGroupSelected];
    }

    @Override
    public void selectChange(int position) {
        mGroupSelected = position;
    }

    public void initCancle() {
        View view = findViewById(R.id.addtip_cancle);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTipActivity.this.finish();
            }
        });
    }
}
