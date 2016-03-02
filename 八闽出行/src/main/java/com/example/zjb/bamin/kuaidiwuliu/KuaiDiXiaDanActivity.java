package com.example.zjb.bamin.kuaidiwuliu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class KuaiDiXiaDanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuai_di_xia_dan);
    }
    public void tijiao(View view){
        finish();
    }
    public void dizhi(View view){
        Intent intent=new Intent();
        intent.setClass(KuaiDiXiaDanActivity.this,DiZhiActivity.class);
        startActivity(intent);
    }
}
