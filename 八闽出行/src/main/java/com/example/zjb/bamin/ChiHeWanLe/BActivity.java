package com.example.zjb.bamin.ChiHeWanLe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class BActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    public void reyingyingpian(View v)
    {
        Toast.makeText(BActivity.this, "跳转到该影片详情页面", Toast.LENGTH_SHORT).show();
    }

    public void items_yingpian(View v)
    {
        Toast.makeText(BActivity.this, "跳转到该影片的团购详情页面", Toast.LENGTH_SHORT).show();
    }

    public void back(View v)
    {
        finish();
    }
}
