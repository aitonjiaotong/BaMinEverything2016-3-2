package com.example.zjb.bamin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class CouponInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView_redBag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_info);
        findID();
        initUI();
        setListener();
    }

    private void initUI() {
        mListView_redBag.setAdapter(new MyAdapter());
    }

     class MyAdapter extends BaseAdapter {

             @Override
             public int getCount() {
                 return 5;
             }

             @Override
             public Object getItem(int position) {
                 return null;
             }

             @Override
             public long getItemId(int position) {
                 return 0;
             }

             @Override
             public View getView(int position, View convertView, ViewGroup parent) {
                 View inflate = getLayoutInflater().inflate(R.layout.redbag_item, null);
                 return inflate;
             }
         }
    private void findID() {
        mListView_redBag = (ListView) findViewById(R.id.listView_redBag);
    }

    private void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                AnimFromRightToLeft();
                break;
        }
    }

    private void AnimFromRightToLeft() {
        overridePendingTransition(R.anim.fade_in,R.anim.push_left_out );
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            finish();
            AnimFromRightToLeft();
        }
        return super.onKeyDown(keyCode, event);
    };
}
