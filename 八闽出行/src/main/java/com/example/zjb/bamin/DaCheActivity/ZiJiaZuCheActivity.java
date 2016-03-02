package com.example.zjb.bamin.DaCheActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class ZiJiaZuCheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_jia_zu_che);
    }
    public void zijiazhuche01(View view) {
        Intent intent= new Intent();
        intent.setClass(ZiJiaZuCheActivity.this, ZiJiaZuChe02Activity.class);
        startActivity(intent);
    }
}
