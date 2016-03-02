package com.example.zjb.bamin.DaCheActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class DaiJiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_jia);
    }
    public void daijia(View view) {
        Intent intent= new Intent();
        intent.setClass(DaiJiaActivity.this, DaiJia02Activity.class);
        startActivity(intent);
    }
}
