package com.example.zjb.bamin.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.SelectStationArriveActivity;
import com.example.zjb.bamin.SelectStationSetOutActivity;
import com.example.zjb.bamin.SmsLoginActivity;
import com.example.zjb.bamin.TicketActivity;
import com.example.zjb.bamin.constant.Constant;
import com.example.zjb.bamin.customView.ViewPagerIndicator;
import com.example.zjb.bamin.models.about_banner.BannerInfo;
import com.example.zjb.bamin.utils.DateCompareUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Fragment01 extends Fragment implements View.OnClickListener
{

    private Calendar c = Calendar.getInstance();
    private View mLayout;
    private ViewPager mViewPager_banner;
    private int[] mImageID = new int[]{R.mipmap.banner01, R.mipmap.banner02, R.mipmap.banner03};
    private List<BannerInfo> bannerData = new ArrayList<BannerInfo>();
    private boolean isFrist = true;
    private boolean mDragging;
    private int mPagerCount = Integer.MAX_VALUE / 3;
    private LinearLayout mChooseSetOut;
    private LinearLayout mChooseArrive;
    private ImageView mIv_Exchange;
    private TextView mTv_setOut;
    private TextView mTv_arrive;
    private TextView mTv_date;
    private LinearLayout mChooseTime;
    private boolean isChoose;
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private Button mBtn_query;
    private boolean mDateCompare;
    private boolean isLogin = false;
    private String mPhoneNum;
    private AlertDialog mAlertDialog;
    private ViewPagerIndicator mViewPagerIndicator;

    public Fragment01()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (mLayout == null)
        {
            mLayout = inflater.inflate(R.layout.fragment_fragment01, null);
            initData();
            initUI();
            setOnClick();
        }
        return mLayout;
    }


    private void initData()
    {
        //初始化时间
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        //初始化Banner数据
        initBannerData();
    }

    private void initBannerData()
    {
        HTTPUtils.get(getActivity(), Constant.URLFromAiTon.GET_BANNER_IMG, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }

            @Override
            public void onResponse(String s)
            {
                Log.e("onResponse ", "onResponse banner"+s);
                Type type = new TypeToken<ArrayList<BannerInfo>>()
                {
                }.getType();
                bannerData = GsonUtils.parseJSONArray(s, type);
            }
        });
    }


    private void initUI()
    {
        initBanner();
        mChooseSetOut = (LinearLayout) mLayout.findViewById(R.id.ll_choose_set_out);
        mTv_setOut = (TextView) mLayout.findViewById(R.id.tv_set_out);
        mChooseArrive = (LinearLayout) mLayout.findViewById(R.id.ll_choose_arrive);
        mTv_arrive = (TextView) mLayout.findViewById(R.id.tv_arrive);
        mChooseTime = (LinearLayout) mLayout.findViewById(R.id.ll_time);
        mTv_date = (TextView) mLayout.findViewById(R.id.tv_date);
        mTv_date.setText(mYear + "-" + mMonth + "-" + mDayOfMonth);
        mIv_Exchange = (ImageView) mLayout.findViewById(R.id.iv_exchange);
        mBtn_query = (Button) mLayout.findViewById(R.id.btn_query);
    }

    /**
     * 设置监听
     */
    private void setOnClick()
    {
        mChooseSetOut.setOnClickListener(this);
        mChooseArrive.setOnClickListener(this);
        mChooseTime.setOnClickListener(this);
        mIv_Exchange.setOnClickListener(this);
        mBtn_query.setOnClickListener(this);
    }

    /**
     * 设置广告条
     */
    private void initBanner()
    {
        mViewPager_banner = (ViewPager) mLayout.findViewById(R.id.vp_headerview_pager);
        mViewPager_banner.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        mViewPager_banner.addOnPageChangeListener(new BannerOnPageChangeListener());
        mViewPagerIndicator = (ViewPagerIndicator) mLayout.findViewById(R.id.ViewPagerIndicator);
        if (isFrist)
        {
            autoScroll();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mPhoneNum = sp.getString("phoneNum", "");
        if ("".equals(mPhoneNum))
        {
            isLogin = false;
        } else
        {
            isLogin = true;
        }
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.ll_time:
                //跳转日期选择器
                showDatePickerDialog();
                break;
            case R.id.ll_choose_set_out:
                //跳转出发车站选择界面
                intent.setClass(getActivity(), SelectStationSetOutActivity.class);
                startActivityForResult(intent, Constant.RequestAndResultCode.REQUEST_CODE_CHOOSE_SET_OUT);
                animFromLeftToRight();
                break;
            case R.id.ll_choose_arrive:
                //跳转到达车站选择界面
                intent.setClass(getActivity(), SelectStationArriveActivity.class);
                startActivityForResult(intent, Constant.RequestAndResultCode.REQUEST_CODE_CHOOSE_ARRIVE);
                animFromLeftToRight();
                break;
            case R.id.iv_exchange:
                setIsChoose();
                //设置切换出发车站及到达车站
                if (!isChoose)
                {
                    Toast.makeText(getActivity(), "您暂未选择相关站点", Toast.LENGTH_SHORT).show();
                } else
                {
                    String setOutLocation = mTv_setOut.getText().toString();
                    String arriveLocation = mTv_arrive.getText().toString();
                    mTv_arrive.setText(setOutLocation);
                    mTv_setOut.setText(arriveLocation);
                }
                break;
            case R.id.btn_query:
                if (isLogin)
                {
                    setIsChoose();
                    if (!isChoose)
                    {
                        Toast.makeText(getActivity(), "您暂未选择相关站点", Toast.LENGTH_SHORT).show();

                    } else
                    {
                        intent.setClass(getActivity(), TicketActivity.class);
                        intent.putExtra(Constant.IntentKey.CURR_YEAR, mYear);
                        intent.putExtra(Constant.IntentKey.CURR_MONTH, mMonth);
                        intent.putExtra(Constant.IntentKey.CURR_DAY_OF_MONTH, mDayOfMonth);
                        String str_setOut = mTv_setOut.getText().toString();
                        String str_arrive = mTv_arrive.getText().toString();
                        intent.putExtra(Constant.IntentKey.FINAIL_SET_OUT_STATION, str_setOut);
                        intent.putExtra(Constant.IntentKey.FINAIL_ARRIVE_STATION, str_arrive);
                        startActivity(intent);
                    }
                } else
                {
                    intent.setClass(getActivity(), SmsLoginActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }

    private void setIsChoose()
    {
        if ("请选择出发地".equals(mTv_setOut.getText().toString().trim()) || "请选择目的地".equals(mTv_arrive.getText().toString().trim()))
        {
            isChoose = false;
        } else
        {
            isChoose = true;
        }
    }

    /**
     * 界面跳转动画
     */
    private void animFromLeftToRight()
    {
        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.fade_out);
    }

    public void showDatePickerDialog()
    {
        DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                try
                {
                    mDateCompare = DateCompareUtil.DateCompare(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth, c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
                    Log.e("onDateChanged ", "onDateChanged "+year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                if (mDateCompare)
                {
                    mYear = year;
                    mMonth = monthOfYear + 1;
                    mDayOfMonth = dayOfMonth;
                    mTv_date.setText(mYear + "-" + mMonth + "-" + mDayOfMonth);
//                    mAlertDialog.cancel();
                } else
                {
                    Toast.makeText(getActivity(), "预售三天内的车票，请重新选择！", Toast.LENGTH_SHORT).show();
                    mTv_date.setText(mYear + "-" + mMonth + "-" + mDayOfMonth);
                }
            }
        },mYear, mMonth - 1, mDayOfMonth).show(getActivity().getFragmentManager(),"datePicker");

    }

    class MyPagerAdapter extends FragmentPagerAdapter
    {

        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            if(bannerData.size()==0)
            {
                int pager_index = position % mImageID.length;
                return new BannerFragment(pager_index, mImageID[pager_index]);
            }else
            {
                int pager_index = position % bannerData.size();
                Log.e("getItem ", "getItem "+bannerData.get(pager_index).getUrl());
                return new BannerFragment(pager_index, bannerData.get(pager_index).getUrl(),bannerData.get(pager_index).getUrl2());
            }
        }

        @Override
        public int getCount()
        {
            return mPagerCount;
        }
    }

    class BannerOnPageChangeListener implements ViewPager.OnPageChangeListener
    {
        public void onPageScrollStateChanged(int state)
        {
            switch (state)
            {
                case ViewPager.SCROLL_STATE_IDLE:
                    mDragging = false;
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    mDragging = true;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    mDragging = false;
                    break;
                default:
                    break;
            }
        }

        public void onPageScrolled(int position, float arg1, int arg2)
        {
            position = position % 3;
            mViewPagerIndicator.move(arg1, position);
        }

        public void onPageSelected(int arg0)
        {
        }
    }

    private void autoScroll()
    {
        mViewPager_banner.setCurrentItem(mPagerCount / 2);
        mViewPager_banner.postDelayed(new Runnable()
        {
            public void run()
            {
                int position = mViewPager_banner.getCurrentItem() + 1;
                if (!mDragging)
                {
                    isFrist = false;
                    mViewPager_banner.setCurrentItem(position);
                }
                mViewPager_banner.postDelayed(this, 3000);
            }
        }, 3000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
        {
            switch (requestCode)
            {
                //选择出发地请求码
                case Constant.RequestAndResultCode.REQUEST_CODE_CHOOSE_SET_OUT:
                    switch (resultCode)
                    {
                        case Constant.RequestAndResultCode.RESULT_CODE_SET_OUT_ADDR:
                            //选择出发城市地址的返回码
                            mTv_setOut.setText(data.getStringExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME));
                            break;
                        case Constant.RequestAndResultCode.RESULT_CODE_SEARCH_ADDR:
                            //搜索出发城市地址的返回码
                            mTv_setOut.setText(data.getStringExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME));
                            break;
                        case Constant.RequestAndResultCode.RESULT_CODE_COMMONLY_USED_ADDR:
                            //常用出发城市地址的返回码
                            mTv_setOut.setText(data.getStringExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME));
                            break;
                    }
                    break;
                //选择目的地请求码
                case Constant.RequestAndResultCode.REQUEST_CODE_CHOOSE_ARRIVE:
                    switch (resultCode)
                    {
                        case Constant.RequestAndResultCode.RESULT_CODE_ARRIVE_ADDR:
                            //选择目的地的返回码
                            mTv_arrive.setText(data.getStringExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME));
                            break;
                        case Constant.RequestAndResultCode.RESULT_CODE_ARRIVE_SEARCH_ADDR:
                            //搜索目的地的返回码
                            Log.e("onActivityResult ", data.getStringExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME));
                            mTv_arrive.setText(data.getStringExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME));
                            break;
                        case Constant.RequestAndResultCode.RESULT_CODE_ARRIVE_COMMONLY_USED_ADDR:
                            //常用目的地的返回码
                            mTv_arrive.setText(data.getStringExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME));
                            break;
                    }
                    break;
            }
        }
    }
}
