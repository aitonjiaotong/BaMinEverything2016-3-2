package com.example.zjb.bamin.daiBanPaoTui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class PaoTuiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pao_tui);
    }
    public void paotuishungou(View view){
        Intent intent=new Intent();
        intent.setClass(PaoTuiActivity.this, PaoTuiShunGouActivity.class);
        startActivity(intent);
    }
    public void paotuiqusong(View view){
        Intent intent=new Intent();
        intent.setClass(PaoTuiActivity.this, PaoTuiQuSongActivity.class);
        startActivity(intent);
    }
    public void paotuipaidui(View view){
        Intent intent=new Intent();
        intent.setClass(PaoTuiActivity.this, PaoTuiPaiDuiActivity.class);
        startActivity(intent);
    }
    public void sirendingzhi(View view){
        Intent intent=new Intent();
        intent.setClass(PaoTuiActivity.this, PaoTuiQuSongActivity.class);
        startActivity(intent);
    }
}
