package com.example.zjb.bamin.DaCheActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class JiGouYongCheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_gou_yong_che);
    }
    public void jigouyongche(View view) {
        Intent intent= new Intent();
        intent.setClass(JiGouYongCheActivity.this, JiGouYongChe02Activity.class);
        startActivity(intent);
    }
}
