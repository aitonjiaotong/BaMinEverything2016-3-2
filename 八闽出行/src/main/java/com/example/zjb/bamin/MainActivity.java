package com.example.zjb.bamin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zjb.bamin.fragment.Fragment01;
import com.example.zjb.bamin.fragment.Fragment02;
import com.example.zjb.bamin.fragment.MineFragment;

public class MainActivity extends AppCompatActivity {
    private String[] tabsItem = new String[]{
            "查询",
            "订单",
            "我的",
    };
    private Class[] fragment = new Class[]{
            Fragment01.class,
            Fragment02.class,
            MineFragment.class,
    };
    private int[] imgRes = new int[]{
            R.drawable.ic_home_search_selector,
            R.drawable.ic_home_order_selector,
            R.drawable.ic_home_me_selector
    };
    private FragmentTabHost mTabHost;
    private BroadcastReceiver recevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String orderDeatilActivity = intent.getStringExtra("OrderDeatilActivity");
            if ("OrderDeatilActivity".equals(orderDeatilActivity)) {
                mTabHost.setCurrentTab(1);
            } else {
                mTabHost.setCurrentTab(0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intent = new IntentFilter();
        intent.addAction("OrderDeatilActivity");
        registerReceiver(recevier, intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(recevier);
    }
    /**
     * 双击退出应用
     */
//    private long currentTime = 0;
//    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
//        if(keyCode== KeyEvent.KEYCODE_BACK){
//            if(System.currentTimeMillis()-currentTime>1000){
//                Toast toast = Toast.makeText(MainActivity.this, "双击退出应用", Toast.LENGTH_SHORT);
//                toast.show();
//                currentTime=System.currentTimeMillis();
//                return false;
//            }else{
//                return super.onKeyDown(keyCode, event);
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    };
}
