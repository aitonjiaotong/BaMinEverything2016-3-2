package com.example.zjb.bamin.DaCheActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class JiGouYongChe03Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_gou_yong_che03);
    }
    public void jigouyongche03(View view) {
        Intent intent= new Intent();
        intent.setClass(JiGouYongChe03Activity.this, JiGouYongChe04Activity.class);
        startActivity(intent);
    }
}
