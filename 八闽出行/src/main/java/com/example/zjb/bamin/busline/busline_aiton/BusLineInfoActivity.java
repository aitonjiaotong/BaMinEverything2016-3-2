package com.example.zjb.bamin.busline.busline_aiton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zjb.bamin.R;

import java.util.ArrayList;
import java.util.List;

public class BusLineInfoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView mListView2;
    private MyAdapter mAdapter;
    private int isOpen = -1;
    private List<String> satationInfo = new ArrayList<>();
    private TextView mBusLineDirection;
    private TextView mBusLineNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_line_info);
        initUI();
        setListener();
        initDate();
    }

    private void initDate() {
        Intent intent = getIntent();
        String busLineNum = intent.getStringExtra("busLineNum");
        mBusLineNum.setText(busLineNum);
        if ("1".equals(busLineNum)) {
            mBusLineDirection.setText("徐碧");
            satationInfo.clear();
            satationInfo.add("徐碧");
            satationInfo.add("机电技校");
            satationInfo.add("吉祥福邸");
            satationInfo.add("八中");
            satationInfo.add("东新五路");
            satationInfo.add("阳光城");
            satationInfo.add("东新四路");
            satationInfo.add("兴业证卷");
            satationInfo.add("三明市场");
            satationInfo.add("第一医院");
            satationInfo.add("市人大");
            satationInfo.add("附小");
            satationInfo.add("麒麟山");
            satationInfo.add("新泉路口");
            satationInfo.add("市建委");
            satationInfo.add("省一建");
            satationInfo.add("下洋");
            satationInfo.add("三中");
            satationInfo.add("三元税务");
            satationInfo.add("妇幼保健");
            satationInfo.add("城东");
            satationInfo.add("火车站");
            satationInfo.add("客运西站（招呼站）");
            satationInfo.add("客运西站");
            satationInfo.add("四中");
            satationInfo.add("白沙");
            satationInfo.add("索桥西");
            satationInfo.add("化工厂");
            satationInfo.add("水厂");
            satationInfo.add("三钢");
            satationInfo.add("汽车站");
            satationInfo.add("三明市场");
            satationInfo.add("兴业证卷");
            satationInfo.add("市社保中心");
            satationInfo.add("阳光城");
            satationInfo.add("东西五路");
            satationInfo.add("八中");
            satationInfo.add("吉祥福邸");
            satationInfo.add("机电技校");
            satationInfo.add("徐碧");

        } else if ("58".equals(busLineNum)) {
            mBusLineDirection.setText("上河城");
            satationInfo.add("三明一中");
            satationInfo.add("城关");
            satationInfo.add("火车站");
            satationInfo.add("客运西站（招呼站）");
            satationInfo.add("客运西站");
            satationInfo.add("四中");
            satationInfo.add("白沙");
            satationInfo.add("索桥西");
            satationInfo.add("化工厂");
            satationInfo.add("水厂");
            satationInfo.add("三钢");
            satationInfo.add("正顺庙");
            satationInfo.add("碧海花园西");
            satationInfo.add("列西");
            satationInfo.add("梅列医院");
            satationInfo.add("三明广场");
            satationInfo.add("兴业证券");
            satationInfo.add("市社保中心");
            satationInfo.add("阳光城");
            satationInfo.add("东新五路");
            satationInfo.add("八中");
            satationInfo.add("吉祥福邸");
            satationInfo.add("机电技校");
            satationInfo.add("徐碧");
            satationInfo.add("体育场馆北");
            satationInfo.add("体育场馆东");
            satationInfo.add("上河城路");
            satationInfo.add("上知园");
            satationInfo.add("上真园");
            satationInfo.add("上河城");
        } else if ("66".equals(busLineNum)) {
            mBusLineDirection.setText("碧湖");
            satationInfo.add("三明九中");
            satationInfo.add("学生公寓");
            satationInfo.add("下洋");
            satationInfo.add("省一建");
            satationInfo.add("市建委");
            satationInfo.add("新泉路口");
            satationInfo.add("麒麟山");
            satationInfo.add("附小");
            satationInfo.add("市政府东");
            satationInfo.add("市政府");
            satationInfo.add("兴业证券");
            satationInfo.add("龙盛广场");
            satationInfo.add("梅列市场");
            satationInfo.add("市政务中心西");
            satationInfo.add("东安路");
            satationInfo.add("东新五路");
            satationInfo.add("五路大桥");
            satationInfo.add("八中");
            satationInfo.add("吉祥福邸");
            satationInfo.add("徐新璐");
            satationInfo.add("国安");
            satationInfo.add("市公安局");
            satationInfo.add("左海家具");
            satationInfo.add("体育场馆南");
            satationInfo.add("三农福利区");
            satationInfo.add("荣王集团");
            satationInfo.add("碧湖");
        }
//        satationInfo.add("红星美凯龙总站");
//        satationInfo.add("钟宅红星美凯龙");
//        satationInfo.add("钟宅");
//        satationInfo.add("BRT湖里大道");
//        satationInfo.add("双十中学枋湖校区");
//        satationInfo.add("车管所");
//        satationInfo.add("枋湖路");
//        satationInfo.add("梧桐");
//        satationInfo.add("枋湖村");
//        satationInfo.add("枋湖西路");
//        satationInfo.add("枋湖客运中心");
//        satationInfo.add("禾山路");
//        satationInfo.add("薛岭");
//        satationInfo.add("白果山");
//        satationInfo.add("武警支队");
//        satationInfo.add("仙岳花园");
//        satationInfo.add("莲岳路北");
//        satationInfo.add("槟榔路");
//        satationInfo.add("槟榔新村");
//        satationInfo.add("凤屿路口");
//        satationInfo.add("新村");
//        satationInfo.add("闽南大厦");
//        satationInfo.add("湖滨中路");
//        satationInfo.add("太湖新城");
//        satationInfo.add("豆仔尾路口");
//        satationInfo.add("嘉美花园");
//        satationInfo.add("思北路口");
//        satationInfo.add("开河路口");
//        satationInfo.add("鹭江道");
//        satationInfo.add("轮渡邮局");
//        satationInfo.add("海滨大厦");
//        satationInfo.add("轮渡公交场");
    }

    private void setListener() {
        mListView2.setOnItemClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
    }

    private void initUI() {
        mBusLineNum = (TextView) findViewById(R.id.busLineNum);
        mBusLineDirection = (TextView) findViewById(R.id.busLineDirection);
        mListView2 = (ListView) findViewById(R.id.listView2);
        mAdapter = new MyAdapter();
        mListView2.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isOpen = position;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return satationInfo.size();
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
            View inflate = null;
            if (position == 0) {
                inflate = getLayoutInflater().inflate(R.layout.busline_listitem_head, null);
                TextView busStationHead = (TextView) inflate.findViewById(R.id.busStationHead);
                busStationHead.setText(satationInfo.get(0));
            } else if (position == satationInfo.size() - 1) {
                inflate = getLayoutInflater().inflate(R.layout.busline_listitem_foot, null);
                TextView busStationHead = (TextView) inflate.findViewById(R.id.busStationFoot);
                busStationHead.setText(satationInfo.get(satationInfo.size() - 1));
            } else {
                inflate = getLayoutInflater().inflate(R.layout.busline_listitem, null);
                TextView busStation = (TextView) inflate.findViewById(R.id.busStation);
                busStation.setText(satationInfo.get(position));
                LinearLayout hideView = (LinearLayout) inflate.findViewById(R.id.hideView);
                RelativeLayout location = (RelativeLayout) inflate.findViewById(R.id.location);

                if (position == isOpen) {
                    hideView.setVisibility(View.VISIBLE);
                    location.setVisibility(View.VISIBLE);
                } else {
                    hideView.setVisibility(View.GONE);
                    location.setVisibility(View.GONE);
                }
            }

            return inflate;
        }
    }
}
