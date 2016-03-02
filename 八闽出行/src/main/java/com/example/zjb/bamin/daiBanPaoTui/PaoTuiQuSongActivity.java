package com.example.zjb.bamin.daiBanPaoTui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class PaoTuiQuSongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pao_tui_qu_song);
    }
    public void xiayibu(View view){
        Intent intent=new Intent();
        intent.setClass(PaoTuiQuSongActivity.this, PaoTuiQuSong02Activity.class);
        startActivity(intent);
    }
}
