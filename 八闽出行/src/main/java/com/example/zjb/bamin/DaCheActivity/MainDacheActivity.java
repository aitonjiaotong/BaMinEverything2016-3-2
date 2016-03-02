package com.example.zjb.bamin.DaCheActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class MainDacheActivity extends AppCompatActivity {

    private LinearLayout mJiemian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dache);
        mJiemian = (LinearLayout) findViewById(R.id.jiemian);
    }

    public void shouye(View view) {
        mJiemian.setBackgroundResource(R.mipmap.yongche01);
    }

    public void dache(View view) {
        Intent intent= new Intent();
        intent.setClass(MainDacheActivity.this,DaCheActivity.class);
        startActivity(intent);
    }
    public void tejiayouhui(View view) {
        Intent intent= new Intent();
        intent.setClass(MainDacheActivity.this,TeJiaTaoCanActivity.class);
        startActivity(intent);
    }
    public void daijia(View view) {
        Intent intent= new Intent();
        intent.setClass(MainDacheActivity.this,DaiJiaActivity.class);
        startActivity(intent);
    }
    public void jigouyongche(View view) {
        Intent intent= new Intent();
        intent.setClass(MainDacheActivity.this,JiGouYongCheActivity.class);
        startActivity(intent);
    }
    public void mendian(View view) {
        Intent intent= new Intent();
        intent.setClass(MainDacheActivity.this,MenDianActivity.class);
        startActivity(intent);
    }
    public void zijiazhuche(View view) {
        Intent intent= new Intent();
        intent.setClass(MainDacheActivity.this,ZiJiaZuCheActivity.class);
        startActivity(intent);
    }
    public void dingdan(View view) {
        Toast.makeText(MainDacheActivity.this, "功能暂时未做", Toast.LENGTH_SHORT).show();
    }
    public void gerenzhongxin(View view) {
        Toast.makeText(MainDacheActivity.this, "功能暂时未做", Toast.LENGTH_SHORT).show();
    }
}
