package com.example.zjb.bamin.ChiHeWanLe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class CActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
    }

   public void search(View v)
   {
       Toast.makeText(CActivity.this,"跳转到该地区搜索到的酒店列表界面",Toast.LENGTH_SHORT).show();
   }
    public void other(View v)
    {
        Toast.makeText(CActivity.this,"该功能暂未完善，敬请期待！",Toast.LENGTH_SHORT).show();
    }
    public void back(View v)
    {
        finish();
    }
}
