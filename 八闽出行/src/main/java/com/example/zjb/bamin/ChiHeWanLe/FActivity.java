package com.example.zjb.bamin.ChiHeWanLe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class FActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f);
    }

    public void btn_detail(View v)
    {
        Toast.makeText(FActivity.this, "跳转到商家详情页面", Toast.LENGTH_SHORT).show();
    }

    public void back(View v)
    {
        finish();
    }
}
