package com.example.zjb.bamin.kuaidiwuliu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class MainkuaidiwuliuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kuaidiwuliu);
    }
    public void shouye(View view){

    }
    public void dingdan(View view){
        Toast.makeText(MainkuaidiwuliuActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void gerenzhongxin(View view){
        Toast.makeText(MainkuaidiwuliuActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void kuaidixiadan(View view){
        Intent intent=new Intent();
        intent.setClass(MainkuaidiwuliuActivity.this,KuaiDiXiaDanActivity.class);
        startActivity(intent);
    }
    public void wuliuxiadan(View view){
        Intent intent=new Intent();
        intent.setClass(MainkuaidiwuliuActivity.this,WuLiuXiaDanActivity.class);
        startActivity(intent);
    }
    public void kuaidichaxun(View view){
        Intent intent=new Intent();
        intent.setClass(MainkuaidiwuliuActivity.this,KuaiDiChaXunActivity.class);
        startActivity(intent);
    }
    public void wuliuchaxun(View view){
        Intent intent=new Intent();
        intent.setClass(MainkuaidiwuliuActivity.this,WuLiuChaXunActivity.class);
        startActivity(intent);
    }
}
