package com.example.zjb.bamin.ChiHeWanLe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class DActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
    }

    public void back(View v)
    {
        finish();
    }

    public void btn_detail(View v)
    {
        Toast.makeText(DActivity.this,"跳转到商家详细页面",Toast.LENGTH_SHORT).show();
    }
}
