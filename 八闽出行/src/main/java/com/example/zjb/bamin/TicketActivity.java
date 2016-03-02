package com.example.zjb.bamin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.constant.Constant;
import com.example.zjb.bamin.models.about_ticket.TicketInfo;
import com.example.zjb.bamin.utils.DateCompareUtil;
import com.example.zjb.bamin.utils.LogUtil;
import com.google.gson.reflect.TypeToken;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TicketActivity extends AppCompatActivity implements View.OnClickListener {
    private Calendar c = Calendar.getInstance();

    private ListView mLv_ticket;
    private TicketListViewAdapter mAdapter = new TicketListViewAdapter();
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private TextView mTv_today;
    private TextView mTv_yesterday;
    private TextView mTv_tomorrow;
    private ImageView mBack;
    private String mResult;
    private List<TicketInfo> mTicketInfoList = new ArrayList<>();
    private String start;
    private String end;
    private String mDateMath;
    private String mCheckedTime;
    private String mCurrentTime;
    private ProgressBar mRefrash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        initIntent();
        initUI();
        initData();
        setOnclick();
    }

    private void initData() {
        mTicketInfoList.clear();
        mRefrash.setVisibility(View.VISIBLE);
        mLv_ticket.setVisibility(View.GONE);
        String url_web = Constant.URL.HOST +
                "GetSellableScheduleByStartCityName?executeDate=" + mCheckedTime +
                "&startCityName=" + URLEncoder.encode(start )+
                "&endSiteNamePart=" + URLEncoder.encode(end );
        HTTPUtils.get(TicketActivity.this, url_web, new VolleyListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }

            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    mResult = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    Log.e("onResponse ", "onResponse 车票"+mResult);
                    Type type = new TypeToken<ArrayList<TicketInfo>>() {
                    }.getType();
                    mTicketInfoList = GsonUtils.parseJSONArray(mResult, type);
                    mAdapter.notifyDataSetChanged();
                    mRefrash.setVisibility(View.GONE);
                    mLv_ticket.setVisibility(View.VISIBLE);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initIntent() {
        Intent intent = getIntent();

        mYear = intent.getIntExtra(Constant.IntentKey.CURR_YEAR, -1);
        mMonth = intent.getIntExtra(Constant.IntentKey.CURR_MONTH, -1);
        mDayOfMonth = intent.getIntExtra(Constant.IntentKey.CURR_DAY_OF_MONTH, -1);
        start = intent.getStringExtra(Constant.IntentKey.FINAIL_SET_OUT_STATION);
        LogUtil.show("initIntent TicketActivity-->start:", start);
        end = intent.getStringExtra(Constant.IntentKey.FINAIL_ARRIVE_STATION);
        LogUtil.show("initIntent TicketActivity-->end", end);
    }

    private void initUI() {
        mBack = (ImageView) findViewById(R.id.iv_back);
        mRefrash = (ProgressBar) findViewById(R.id.refrash);
        initBtnForTranTime();
        initTicketListView();
    }

    private void initBtnForTranTime() {
        mTv_today = (TextView) findViewById(R.id.tv_today);
        mCheckedTime = mYear+"-"+mMonth+"-"+mDayOfMonth;
        mCurrentTime = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
        mTv_today.setText(mCheckedTime);
        mTv_yesterday = (TextView) findViewById(R.id.tv_yesterday);
        mTv_tomorrow = (TextView) findViewById(R.id.tv_tomorrow);
        checkTimeBtn();
    }

    private void checkTimeBtn() {
        try {
            mDateMath = DateCompareUtil.DateMath(mCheckedTime, mCurrentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("等于".equals(mDateMath)) {
            mTv_yesterday.setEnabled(false);
            mTv_yesterday.setTextColor(Color.parseColor("#C0C0C0"));
        } else{
            mTv_yesterday.setEnabled(true);
            mTv_yesterday.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if("等于2".equals(mDateMath)) {
            mTv_tomorrow.setEnabled(false);
            mTv_tomorrow.setTextColor(Color.parseColor("#C0C0C0"));
        }else{
            mTv_tomorrow.setEnabled(true);
            mTv_tomorrow.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    private void initTicketListView() {
        mLv_ticket = (ListView) findViewById(R.id.lv_ticket);
        mLv_ticket.setAdapter(mAdapter);
    }

    private void setOnclick() {

        mTv_yesterday.setOnClickListener(this);
        mTv_tomorrow.setOnClickListener(this);
        mBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_yesterday:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //得到指定模范的时间
                try {
                    Date d = sdf.parse(mCheckedTime);
                    long d1=d.getTime()-24 * 3600 * 1000;
                    mCheckedTime = sdf.format(d1);
                    mTv_today.setText(mCheckedTime);
                    checkTimeBtn();
                    initData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_tomorrow:
                //得到指定模范的时间
                SimpleDateFormat sdf01 = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date d = sdf01.parse(mCheckedTime);
                    long d1=d.getTime()+24 * 3600 * 1000;
                    mCheckedTime = sdf01.format(d1);
                    mTv_today.setText(mCheckedTime);
                    checkTimeBtn();
                    initData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    class TicketListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTicketInfoList.size();
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
            View layout = getLayoutInflater().inflate(R.layout.list_item_ticket, null);
            TextView start_time = (TextView) layout.findViewById(R.id.start_time);
            TextView start_station = (TextView) layout.findViewById(R.id.start_station);
            TextView destination = (TextView) layout.findViewById(R.id.destination);
            TextView ticket_price = (TextView) layout.findViewById(R.id.ticket_price);
            final TicketInfo ticketInfo = mTicketInfoList.get(position);
            layout.findViewById(R.id.reserve).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent();
                    intent.putExtra("ticketInfo",ticketInfo);
                    intent.setClass(TicketActivity.this,FillinOrderActivity.class);
                    startActivity(intent);
                }
            });
            //获取车票信息
            String outTime = timeFormate(ticketInfo.getSetoutTime());
            start_time.setText(outTime);
            start_station.setText(ticketInfo.getStationName());
            destination.setText(ticketInfo.getEndSiteName());
            ticket_price.setText("¥" + ticketInfo.getFullPrice());
            return layout;
        }
    }

    private String timeFormate(String setoutTime) {
        long longtime = Long.parseLong(setoutTime.substring(6, setoutTime.length() - 2));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(longtime);
    }
}
