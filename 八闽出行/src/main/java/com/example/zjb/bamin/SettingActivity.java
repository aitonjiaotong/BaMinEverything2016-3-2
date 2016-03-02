package com.example.zjb.bamin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mExit;
    private String mPhoneNum;
    private boolean isLogined = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        initUI();
        setListener();
    }

    private void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.normalProblem).setOnClickListener(this);
        findViewById(R.id.softInfo).setOnClickListener(this);
        findViewById(R.id.ours).setOnClickListener(this);
        findViewById(R.id.rl_feedback).setOnClickListener(this);
        mExit.setOnClickListener(this);
    }

    private void initUI() {
        mExit = (Button) findViewById(R.id.exit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mPhoneNum = sp.getString("phoneNum", "");
        if ("".equals(mPhoneNum)) {
            mExit.setVisibility(View.INVISIBLE);
        }else{
            isLogined=true;
            mExit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent= new Intent();
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                AnimFromRightToLeft();
                break;
            case R.id.rl_feedback:
                intent.setClass(SettingActivity.this, FeedBackActivity.class);
                startActivity(intent);
                animFromLeftToRight();
                break;
            case R.id.normalProblem:
                intent.setClass(SettingActivity.this,NormalProblem.class);
                startActivity(intent);
                animFromLeftToRight();
                break;
            case R.id.softInfo:
                intent.setClass(SettingActivity.this,SoftInfo.class);
                startActivity(intent);
                animFromLeftToRight();
                break;
            case R.id.ours:
                intent.setClass(SettingActivity.this,Ours.class);
                startActivity(intent);
                animFromLeftToRight();
                break;
            case R.id.exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("提醒");
                builder.setMessage("确定要退出吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.clear();
                        edit.commit();
                        mExit.setVisibility(View.INVISIBLE);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }
    /**
     * 界面跳转动画
     */
    private void animFromLeftToRight() {
        overridePendingTransition(R.anim.push_right_in, R.anim.fade_out);
    }
    private void AnimFromRightToLeft() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            finish();
            AnimFromRightToLeft();
        }
        return super.onKeyDown(keyCode, event);
    };
}
