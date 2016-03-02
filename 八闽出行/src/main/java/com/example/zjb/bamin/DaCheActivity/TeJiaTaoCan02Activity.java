package com.example.zjb.bamin.DaCheActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class TeJiaTaoCan02Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_te_jia_tao_can02);
    }
    public void tejiataocan02(View view) {
        Intent intent= new Intent();
        intent.setClass(TeJiaTaoCan02Activity.this, JiGouYongChe03Activity.class);
        startActivity(intent);
    }
}
