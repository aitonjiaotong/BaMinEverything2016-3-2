package com.example.zjb.bamin.qicheguanjia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class CActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_qicheguanjia);
    }

    public void back(View v)
    {
        finish();
    }

    public void yuyue(View v)
    {
        Toast.makeText(CActivity.this, "跳转到预约完成提示界面", Toast.LENGTH_SHORT).show();
    }

    public void cancel(View v)
    {
        finish();
    }

    public void msg(View v)
    {
        Toast.makeText(CActivity.this,"功能未完善，届时会跳转消息中心界面",Toast.LENGTH_SHORT).show();
    }
}
