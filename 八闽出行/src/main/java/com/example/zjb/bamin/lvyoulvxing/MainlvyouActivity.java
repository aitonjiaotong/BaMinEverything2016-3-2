package com.example.zjb.bamin.lvyoulvxing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class MainlvyouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lvyou);
    }
    public void chujingyou(View view){
        Intent intent= new Intent();
        intent.setClass(MainlvyouActivity.this, ChuJingYouActivity.class);
        startActivity(intent);
    }
    public void guoneiyou(View view){
        Intent intent= new Intent();
        intent.setClass(MainlvyouActivity.this, GuoNeiYouActivity.class);
        startActivity(intent);
    }
    public void zhoubianyou(View view){
        Intent intent= new Intent();
        intent.setClass(MainlvyouActivity.this, ZhouBianYouActivity.class);
        startActivity(intent);
    }
    public void dingdan(View view){
        Toast.makeText(MainlvyouActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void gerenzhongxin(View view){
        Toast.makeText(MainlvyouActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void pingtuanyou(View view){
        Toast.makeText(MainlvyouActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void sirendingzhi(View view){
        Toast.makeText(MainlvyouActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void zijiayou(View view){
        Toast.makeText(MainlvyouActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void jingdinaqupiao(View view){
        Toast.makeText(MainlvyouActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void jiudian(View view){
        Toast.makeText(MainlvyouActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void huochepiao(View view){
        Toast.makeText(MainlvyouActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void jipiao(View view){
        Toast.makeText(MainlvyouActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
    public void qianzheng(View view){
        Toast.makeText(MainlvyouActivity.this, "功能未完善", Toast.LENGTH_SHORT).show();
    }
}
