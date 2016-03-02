package com.example.zjb.bamin.qicheguanjia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class AActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_qicheguanjia);
    }

    public void back(View v)
    {
        finish();
    }

    public void search(View v)
    {
        Toast.makeText(AActivity.this, "跳转到查询结果页面", Toast.LENGTH_SHORT).show();
    }

    public void msg(View v)
    {
        Toast.makeText(AActivity.this,"功能未完善，届时会跳转消息中心界面",Toast.LENGTH_SHORT).show();
    }
}
