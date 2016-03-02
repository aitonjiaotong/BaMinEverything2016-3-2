package com.example.zjb.bamin.qicheguanjia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class MainQiCheGuanJiaActivity extends AppCompatActivity
{
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_qicheguanjia);
    }

    public void chabanweizhang(View v)
    {
        intent.setClass(MainQiCheGuanJiaActivity.this, AActivity.class);
        startActivity(intent);
    }

    public void daijiaofakuan(View v)
    {
        intent.setClass(MainQiCheGuanJiaActivity.this, BActivity.class);
        startActivity(intent);
    }

    public void cheliangnianjian(View v)
    {
        intent.setClass(MainQiCheGuanJiaActivity.this, CActivity.class);
        startActivity(intent);
    }

    public void other(View v)
    {
        Toast.makeText(MainQiCheGuanJiaActivity.this,"功能未完善，届时会跳转到相关界面",Toast.LENGTH_SHORT).show();
    }

    public void dingdan(View v)
    {
        Toast.makeText(MainQiCheGuanJiaActivity.this,"功能未完善，届时会跳转到相关界面,\n可参考总APP界面个人中心的风格",Toast.LENGTH_SHORT).show();
    }

    public void my(View v)
    {
        Toast.makeText(MainQiCheGuanJiaActivity.this,"功能未完善，届时会跳转到相关界面,\n可参考总APP界面个人中心的风格",Toast.LENGTH_SHORT).show();
    }

    public void msg(View v)
    {
        Toast.makeText(MainQiCheGuanJiaActivity.this,"功能未完善，届时会跳转消息中心界面",Toast.LENGTH_SHORT).show();
    }
}
