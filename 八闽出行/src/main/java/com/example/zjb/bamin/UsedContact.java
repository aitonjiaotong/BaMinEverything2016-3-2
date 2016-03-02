package com.example.zjb.bamin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.constant.Constant;
import com.example.zjb.bamin.models.about_used_contact.UsedContactInfo;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常用乘客页面
 */
public class UsedContact extends AppCompatActivity implements View.OnClickListener {
    private List<UsedContactInfo> mUsedContactInfoList = new ArrayList<>();
    private RelativeLayout mNoneContact;
    private MyAdapter mAdapter;
    private ListView mUsed_contact_listview;
    private String mAddContact;
    private int isBianJi;
    private String mPhoneNum;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_used_contact);
        initItent();
        /**
         * 获取用户id
         */
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mPhoneNum = sp.getString("phoneNum", "");
        mId = sp.getString("id", "");
        initUI();
        /****
         * 添加测试数据
         */
        initData();
        setListener();
    }

    private void initItent() {
        Intent intent = getIntent();
        mAddContact = intent.getStringExtra("addContact");
    }


    private void initData() {
        String url = Constant.URLFromAiTon.HOST+"person/findperson";

        Map<String, String> map = new HashMap<>();
        map.put("account_id", mId);
        HTTPUtils.post(UsedContact.this, url, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Log.e("onResponse ", "onResponse查询用户乘客表"+s);
                Type type = new TypeToken<ArrayList<UsedContactInfo>>() {
                }.getType();
                mUsedContactInfoList = GsonUtils.parseJSONArray(s, type);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.add_passager).setOnClickListener(this);
        mUsed_contact_listview.setOnItemClickListener(new MyItemClickListener());
    }
    class MyItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent= new Intent();
            if ("FillinOrderActivity".equals(mAddContact)){
                intent.setAction("ticketPassager");
                intent.putExtra("ticketPassager",mUsedContactInfoList.get(position));
                sendBroadcast(intent);
                finish();
            }else if ("MineFragment".equals(mAddContact)){
                isBianJi=position;
                intent.putExtra("bianji","MineFragment");
                intent.putExtra("ticketPassager",mUsedContactInfoList.get(position));
                intent.setClass(UsedContact.this,AddFetcherActivity.class);
                startActivity(intent);
            }

        }
    }

    private void initUI() {
        mUsed_contact_listview = (ListView) findViewById(R.id.used_contact_listview);
        mAdapter = new MyAdapter();
        mUsed_contact_listview.setAdapter(mAdapter);
        mNoneContact = (RelativeLayout) findViewById(R.id.noneContact);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mUsedContactInfoList.size() > 0) {
            mNoneContact.setVisibility(View.GONE);
        } else {
            mNoneContact.setVisibility(View.VISIBLE);
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mUsedContactInfoList.size();
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
            View inflate = getLayoutInflater().inflate(R.layout.used_contact_listitem, null);
            TextView name = (TextView) inflate.findViewById(R.id.name);
            name.setText(mUsedContactInfoList.get(position).getName());
            TextView phone_num = (TextView) inflate.findViewById(R.id.phone_num);
            phone_num.setText(mUsedContactInfoList.get(position).getPhone());
            return inflate;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.add_passager:
                intent.setClass(UsedContact.this, AddFetcherActivity.class);
                startActivity(intent);
                AnimFromRightToLeftIN();
                break;
            case R.id.iv_back:
                finish();
                AnimFromRightToLeftOUT();
                break;
        }
    }

    private void AnimFromRightToLeftOUT() {
        overridePendingTransition(R.anim.fade_in,R.anim.push_left_out );
    }
    private void AnimFromRightToLeftIN() {
        overridePendingTransition(R.anim.push_left_in,R.anim.fade_out );
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            finish();
            AnimFromRightToLeftOUT();
        }
        return super.onKeyDown(keyCode, event);
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
