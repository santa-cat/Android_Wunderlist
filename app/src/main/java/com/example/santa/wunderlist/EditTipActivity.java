package com.example.santa.wunderlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by santa on 16/7/16.
 */
public class EditTipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipedit);
        Intent i = getIntent();


        EditText editText = (EditText) findViewById(R.id.edit_content);
        editText.setText(i.getStringExtra("content"));

        View reback = findViewById(R.id.edit_reback);
        reback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
