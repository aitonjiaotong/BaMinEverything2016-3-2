package com.example.zjb.bamin.DaCheActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class ZiJiaZuChe04Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_jia_zu_che04);
    }
    public void zijiazuche04(View view) {
        Intent intent= new Intent();
        intent.setClass(ZiJiaZuChe04Activity.this, ZiJiaZuChe05Activity.class);
        startActivity(intent);
    }
}
