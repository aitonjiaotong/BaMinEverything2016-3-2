package com.example.zjb.bamin;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zjb.bamin.everything_fragment.MainEverytingFragment;
import com.example.zjb.bamin.everything_fragment.MineEverythingFragment;
import com.example.zjb.bamin.everything_fragment.OrderEverythingFragment;

public class EverytingActivity extends AppCompatActivity {
    private String[] tabsItem = new String[]{
            "首页",
            "订单",
            "个人中心",
    };
    private Class[] fragment = new Class[]{
            MainEverytingFragment.class,
            OrderEverythingFragment.class,
            MineEverythingFragment.class,
    };
    private int[] imgRes = new int[]{
            R.drawable.apollo_ic_userinfo_selector,
            R.drawable.ic_home_order_selector,
            R.drawable.ic_home_me_selector
    };
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everyting);
        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtab);
        for (int i = 0; i < tabsItem.length; i++) {
            View inflate = getLayoutInflater().inflate(R.layout.tabs_item, null);
            TextView tabs_text = (TextView) inflate.findViewById(R.id.tabs_text);
            ImageView tabs_img = (ImageView) inflate.findViewById(R.id.tabs_img);
            tabs_text.setText(tabsItem[i]);
            tabs_img.setImageResource(imgRes[i]);
            mTabHost.addTab(mTabHost.newTabSpec(tabsItem[i]).setIndicator(inflate), fragment[i], null);
        }
    }

}
