package com.example.zjb.bamin.ChiHeWanLe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class MeiYuanActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mei_yuan);
    }

    public void btnqianggou(View v)
    {
        startActivity(new Intent(MeiYuanActivity.this,QiangGuoActivity.class));
    }

    public void btnback(View v)
    {
        finish();
    }

    public void share(View v)
    {
        Toast.makeText(MeiYuanActivity.this,"已分享该信息到您的微博中",Toast.LENGTH_SHORT).show();
    }

    public void collect(View v)
    {
        Toast.makeText(MeiYuanActivity.this,"已收藏!",Toast.LENGTH_SHORT).show();
    }
}
