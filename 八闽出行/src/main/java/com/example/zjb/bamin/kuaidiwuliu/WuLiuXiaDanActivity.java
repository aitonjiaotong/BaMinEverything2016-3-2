package com.example.zjb.bamin.kuaidiwuliu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class WuLiuXiaDanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wu_liu_xia_dan);
    }

    public void tijiao(View view){
        finish();
    }
    public void dizhi(View view){
        Intent intent=new Intent();
        intent.setClass(WuLiuXiaDanActivity.this,DiZhiActivity.class);
        startActivity(intent);
    }
}
