package com.example.zjb.bamin.ChiHeWanLe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zjb.bamin.R;

import java.util.ArrayList;
import java.util.List;

public class MainCheHeWanLeActivity extends AppCompatActivity
{

    private Intent intent = new Intent();
    private List<Class> mList = new ArrayList<Class>();
    private LinearLayout mLl_bg;
    private LinearLayout mLl_for_tab01;
    private LinearLayout mLl_for_tab02_and_tab03;
    private LinearLayout mLl_for_tab04;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chihewanle);
        mList.add(AActivity.class);
        mList.add(BActivity.class);
        mList.add(CActivity.class);
        mList.add(DActivity.class);
        mList.add(EActivity.class);
        mList.add(FActivity.class);
        mList.add(GActivity.class);
        mList.add(HActivity.class);
        mList.add(IActivity.class);
        mList.add(JActivity.class);
        mList.add(KActivity.class);
        mList.add(LActivity.class);
        mLl_bg = (LinearLayout) findViewById(R.id.ll_bg);
        mLl_for_tab01 = (LinearLayout) findViewById(R.id.ll_for_tab01);
        mLl_for_tab02_and_tab03 = (LinearLayout) findViewById(R.id.ll_for_tab02_and_tab03);
        mLl_for_tab04 = (LinearLayout) findViewById(R.id.ll_for_tab04);
        mLl_for_tab01.setVisibility(View.VISIBLE);
        mLl_for_tab02_and_tab03.setVisibility(View.GONE);
        mLl_for_tab04.setVisibility(View.GONE);
    }

    public void meishi(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(0));
        startActivity(intent);
    }

    public void meiyuan(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, MeiYuanActivity.class);
        startActivity(intent);
    }

    public void dianying(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(1));
        startActivity(intent);
    }

    public void jiudian(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(2));
        startActivity(intent);
    }

    public void waimai(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(3));
        startActivity(intent);
    }

    public void KTV(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(4));
        startActivity(intent);
    }

    public void didaomeishi(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(5));
        startActivity(intent);
    }

    public void meinv(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(6));
        startActivity(intent);
    }

    public void yundongjianshen(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(7));
        startActivity(intent);
    }

    public void zuyuanmo(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(8));
        startActivity(intent);
    }

    public void xianhuadangao(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(9));
        startActivity(intent);
    }

    public void jingdianmenpiao(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(10));
        startActivity(intent);
    }

    public void more(View v)
    {
        intent.setClass(MainCheHeWanLeActivity.this, mList.get(11));
        startActivity(intent);
    }

    public void btn_detail(View v)
    {
        Toast.makeText(MainCheHeWanLeActivity.this, "跳转到商家详情页面", Toast.LENGTH_SHORT).show();
    }


    public void login_out(View v)
    {
        mLl_bg.setBackgroundResource(R.mipmap.a05);
    }

    public void my_about(View v)
    {
        Toast.makeText(MainCheHeWanLeActivity.this, "跳转到优惠券及常见问题相关页面", Toast.LENGTH_SHORT).show();
    }

    public void my_order(View v)
    {
        Toast.makeText(MainCheHeWanLeActivity.this, "跳转到我的订单详情", Toast.LENGTH_SHORT).show();
    }

    public void tab01(View v)
    {
        mLl_for_tab01.setVisibility(View.VISIBLE);
        mLl_for_tab02_and_tab03.setVisibility(View.GONE);
        mLl_for_tab04.setVisibility(View.GONE);
        mLl_bg.setBackgroundResource(R.mipmap.a011);
    }

    public void tab02(View v)
    {
        mLl_for_tab01.setVisibility(View.GONE);
        mLl_for_tab02_and_tab03.setVisibility(View.VISIBLE);
        mLl_for_tab04.setVisibility(View.GONE);
        mLl_bg.setBackgroundResource(R.mipmap.a022);
    }

    public void tab03(View v)
    {
        mLl_for_tab01.setVisibility(View.GONE);
        mLl_for_tab02_and_tab03.setVisibility(View.VISIBLE);
        mLl_for_tab04.setVisibility(View.GONE);
        mLl_bg.setBackgroundResource(R.mipmap.a03);
    }

    public void tab04(View v)
    {
        mLl_for_tab01.setVisibility(View.GONE);
        mLl_for_tab02_and_tab03.setVisibility(View.GONE);
        mLl_for_tab04.setVisibility(View.VISIBLE);
        mLl_bg.setBackgroundResource(R.mipmap.a04);
    }


}
