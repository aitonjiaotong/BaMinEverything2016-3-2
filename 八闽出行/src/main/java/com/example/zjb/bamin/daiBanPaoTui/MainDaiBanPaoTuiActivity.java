package com.example.zjb.bamin.daiBanPaoTui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class MainDaiBanPaoTuiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_daibanpaotui);
    }
    public void shouye(View view){

    }
    public void dingdan(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void gerenzhongxin(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void paotui(View view){
        Intent intent=new Intent();
        intent.setClass(MainDaiBanPaoTuiActivity.this,PaoTuiActivity.class);
        startActivity(intent);
    }
    public void daijia(View view){
        Intent intent=new Intent();
        intent.setClass(MainDaiBanPaoTuiActivity.this,DaiJiaActivity.class);
        startActivity(intent);
    }
    public void jiaonafakuan(View view){
        Intent intent=new Intent();
        intent.setClass(MainDaiBanPaoTuiActivity.this,JiaoNaFaKuanActivity.class);
        startActivity(intent);
    }
    public void songcan(View view){
        Intent intent=new Intent();
        intent.setClass(MainDaiBanPaoTuiActivity.this,SongCanActivity.class);
        startActivity(intent);
    }
    public void jiazhengbaojie(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void weixiushutong(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void yuesaobaomu(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void kaisuohuansuo(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void zufangzupin(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void jiajiaotuoguan(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void xianhuadangao(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void xinfangershoufang(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void daimacaipiao(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void chusihaicejiaquan(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void gengduo(View view){
        Toast.makeText(MainDaiBanPaoTuiActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
}
