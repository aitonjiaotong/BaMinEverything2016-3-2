package com.example.zjb.bamin.BaoXianChaoShi;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zjb.bamin.BaoXianChaoShi.model.SlidingMenuListviewInfo;
import com.example.zjb.bamin.BaoXianChaoShi.util.ScreenUtils;
import com.example.zjb.bamin.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;

public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {
    private Class[] slidClass = new Class[]{
            WodebaodanActivity.class,
            WodedingdanActivity.class,
            WodehongbaoActivity.class,
            BeibaorenActivity.class,
            GerenxinxiActivity.class,
            XiaoxizhongxinActivity.class
    };
    private ArrayList<SlidingMenuListviewInfo> slidingMenuListviewInfoList = new ArrayList<>();
    private int[] imgRes = new int[]{
            R.mipmap.aa01,
            R.mipmap.aa02,
            R.mipmap.aa03,
            R.mipmap.aa04,
            R.mipmap.aa05,
            R.mipmap.aa06,
    };
    private String[] url = {
            "http://baoxian.pingan.com/pa18shopnst/u/wx/product/shortVehicle/price.shtml?sourceId=AMxJceGl&WT.mc_id=T00-SMPZ-001",
            "http://baoxian.pingan.com/pa18shopnst/u/wx/product/jiayouernv/price.shtml?sourceId=SbyOnqBo&WT.mc_id=T00-SMPZ-001",
            "http://baoxian.pingan.com/m/product/kangaiweishi.shtml?sourceId=SbyOnqBo&WT.mc_id=T00-SMPZ-001",
            "http://baoxian.pingan.com/pa18shopnst/u/wx/product/travelFreedom/price.shtml?sourceId=SbyOnqBo&WT.mc_id=T00-SMPZ-001",
            "http://baoxian.pingan.com/pa18shopnst/u/wx/product/vehicle/price.shtml?sourceId=SbyOnqBo&WT.mc_id=T00-SMPZ-001",
            "http://baoxian.pingan.com/m/product/kangaiweishi.shtml?sourceId=SbyOnqBo&WT.mc_id=T00-SMPZ-001"

    };
    private View mInflate;
    private SlidingMenu mSm;
    private ListView mListView;
    private TextView mHead;
    private RelativeLayout mFoot;
    private ListView mListView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_baoxian);
        initUI();
        initSlidingMenu();
        setListener();
    }

    private void initUI() {
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new MyAdapter());
        mHead = (TextView) findViewById(R.id.head);
        mFoot = (RelativeLayout) findViewById(R.id.foot);
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imgRes.length;
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
            View inflate = getLayoutInflater().inflate(R.layout.listview_item, null);
            ImageView img = (ImageView) inflate.findViewById(R.id.img);
            int screenWidth = ScreenUtils.getScreenWidth(getApplicationContext());
            ViewGroup.LayoutParams lp = img.getLayoutParams();
            lp.width = screenWidth;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            img.setLayoutParams(lp);

            img.setMaxWidth(screenWidth);
            img.setMaxHeight(screenWidth * 5);

            img.setImageResource(imgRes[position]);
            return inflate;
        }
    }

    private void setListener() {
        mFoot.setOnClickListener(this);
        mListView.setOnScrollListener(new MyScrollListener());
//        mListView.setOnItemClickListener(new MyItemClickListener());
        mInflate.findViewById(R.id.back_to_main).setOnClickListener(this);
        mInflate.findViewById(R.id.set).setOnClickListener(this);
    }

    class MyItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.putExtra("url", url[position]);
            intent.setClass(MainActivity.this, WebActivity.class);
            startActivity(intent);
        }
    }

    class MyScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: //
                    // mBusy = false;
                    Log.e("onScrollStateChanged ", "onScrollStateChanged " + "停止");

                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_down_in);
                    mHead.startAnimation(animation);
                    mHead.setVisibility(View.VISIBLE);
                    Animation animation02 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.congxiawangshangchuxian);

                    mFoot.startAnimation(animation02);
                    mFoot.setVisibility(View.VISIBLE);
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    // mBusy = true;
                    Log.e("onScrollStateChanged ", "onScrollStateChanged " + "正在滑动");
                    Animation animation01 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.congxiawangshangxiaoshi);
                    mHead.startAnimation(animation01);
                    mHead.setVisibility(View.GONE);
                    Animation animation03 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.congshangwangxiaxiaoshi);

                    mFoot.startAnimation(animation03);
                    mFoot.setVisibility(View.GONE);
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    // mBusy = true;
                    Log.e("onScrollStateChanged ", "onScrollStateChanged " + "开始滚动");

                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }

    private void initSlidingMenu() {
        mInflate = getLayoutInflater().inflate(R.layout.slidingmenu, null);
        setBehindContentView(mInflate);
        mSm = getSlidingMenu();
        mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSm.setMode(SlidingMenu.RIGHT);
        mSm.setBehindOffsetRes(R.dimen.sildmenu_offest);
        mSm.setShadowWidthRes(R.dimen.sildmenu_shadow);
        mSm.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                float scale = (float) (percentOpen * 0.25 + 0.75);
                canvas.scale(scale, scale, canvas.getWidth() / 2, canvas.getHeight() / 2);
            }
        });
        mSm.setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_down_in);
                mHead.startAnimation(animation);
                mHead.setVisibility(View.VISIBLE);
                Animation animation02 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.congxiawangshangchuxian);

                mFoot.startAnimation(animation02);
                mFoot.setVisibility(View.VISIBLE);
            }
        });
        mSm.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {
                Animation animation01 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.congxiawangshangxiaoshi);
                mHead.startAnimation(animation01);
                mHead.setVisibility(View.GONE);
                Animation animation03 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.congshangwangxiaxiaoshi);

                mFoot.startAnimation(animation03);
                mFoot.setVisibility(View.GONE);
            }
        });
        initslidingMenuListviewInfoListData();
        mListView2 = (ListView) mInflate.findViewById(R.id.listView2);
        mListView2.setAdapter(new MyAdapter2());
        mListView2.setOnItemClickListener(new MyItemClickListener2());
    }

    class MyItemClickListener2 implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, slidClass[position]);
            startActivity(intent);
        }
    }

    private void initslidingMenuListviewInfoListData() {
        slidingMenuListviewInfoList.add(new SlidingMenuListviewInfo(R.mipmap.sliding_item1_l1, "我的保单"));
        slidingMenuListviewInfoList.add(new SlidingMenuListviewInfo(R.mipmap.sliding_item2_l2, "我的订单"));
        slidingMenuListviewInfoList.add(new SlidingMenuListviewInfo(R.mipmap.sliding_item3_l3, "我的红包"));
        slidingMenuListviewInfoList.add(new SlidingMenuListviewInfo(R.mipmap.sliding_item4_l4, "常用被保人"));
        slidingMenuListviewInfoList.add(new SlidingMenuListviewInfo(R.mipmap.sliding_item5_l5, "个人信息"));
        slidingMenuListviewInfoList.add(new SlidingMenuListviewInfo(R.mipmap.sliding_item6_l6, "消息中心"));
    }

    class MyAdapter2 extends BaseAdapter {

        @Override
        public int getCount() {
            return slidingMenuListviewInfoList.size();
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
            View inflate = getLayoutInflater().inflate(R.layout.slidingitem, null);
            ImageView slidingImg = (ImageView) inflate.findViewById(R.id.slidingImg);
            TextView slidingStr = (TextView) inflate.findViewById(R.id.slidingStr);
            slidingImg.setImageResource(slidingMenuListviewInfoList.get(position).getImgRes());
            slidingStr.setText(slidingMenuListviewInfoList.get(position).getStr());
            return inflate;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.foot:
                toggle();
                break;
            case R.id.back_to_main:
                toggle();
                break;
            case R.id.set:
                intent.setClass(MainActivity.this, SetActivity.class);
                startActivity(intent);
                break;
        }
    }
}
