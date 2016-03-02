package com.example.zjb.bamin.ChiHeWanLe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class EActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e);
    }

    public void btn_back(View v)
    {
        finish();
    }

    public void btnbaidu(View v)
    {
        Toast.makeText(EActivity.this,"跳转至商品详情页面",Toast.LENGTH_SHORT).show();
    }
}
