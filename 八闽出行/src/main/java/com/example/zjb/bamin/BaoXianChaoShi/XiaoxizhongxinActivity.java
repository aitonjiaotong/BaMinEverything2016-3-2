package com.example.zjb.bamin.BaoXianChaoShi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class XiaoxizhongxinActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaoxizhongxin);
    }

    public void back(View view){
        finish();
    }
}
