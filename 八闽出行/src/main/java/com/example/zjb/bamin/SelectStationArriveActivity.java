package com.example.zjb.bamin;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.constant.Constant;
import com.example.zjb.bamin.customView.MyGridView;
import com.example.zjb.bamin.models.about_companysubzone.CompanySubZone;
import com.example.zjb.bamin.models.about_companysubzone.SubZone_;
import com.example.zjb.bamin.models.about_sites.Sites;
import com.example.zjb.bamin.sql.MySqLite;
import com.example.zjb.bamin.utils.GetLastWordUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectStationArriveActivity extends AppCompatActivity implements View.OnClickListener
{
    //数据库相关----------start
    private String TAB_NAME = "arrive";
    private int mVersion = 1;
    private SQLiteDatabase mDb;
    private ContentValues mValues;
    //数据库相关----------end

    //控制子gridview的开关
    private int mIsOpen = -1;
    private int mShiPostion = 0;
    private ImageView mIv_back;
    //常用地址相关
    private ListView mLv_commonly_used_address;
    private List<String> mComUsedAddrData = new ArrayList<String>();
    private CommuonUsedAddrAdapter mAdapter = new CommuonUsedAddrAdapter();

    //省份的所有数据List列表
    private List<CompanySubZone> parent_list_data = new ArrayList<CompanySubZone>();
    //省份的所有数据List列表(省份名称的字符串)
    private List<String> parent_list_name = new ArrayList<String>();
    private List<SubZone_> parent_list_xianshi_name = new ArrayList<>();
    //关联省份与省份下一级的市
    private Map<String, List<String>> map = new HashMap<String, List<String>>();
    //搜索列表相关
    private List<Sites> mSitesData = new ArrayList<Sites>();
    private List<String> mUserSearchSitesData = new ArrayList<String>();

    private TextView mTv_btn_arrive;
    private TextView mTv_btn_comm_used_addr;
    private GridView mGridView_xianshi;
    private RelativeLayout mXianshi_rela;
    private MyGridViewAdapter mMyGridViewAdapter;
    private ProgressBar mRefreash_arrive;
    private ListView mArrive_listView;
    private MyArriveAdapter mMyArriveAdapter;
    private EditText mEt_search_city;
    private ListView mLv_search_addr;
    private String mUser_input;
    private boolean isCommonlyAddr = true;
    private ImageView mIv_clear;
    private SearchAddrAdapter mSearchAdapter = new SearchAddrAdapter();
    private String mMPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station_arrive);

        MySqLite mySqLite = new MySqLite(SelectStationArriveActivity.this, mVersion);
        mDb = mySqLite.getWritableDatabase();
        mValues = new ContentValues();

        initUI();
        setOnclick();
        initSitesData();
    }


    private void initUI()
    {
        mIv_clear = (ImageView) findViewById(R.id.iv_clear);
        mXianshi_rela = (RelativeLayout) findViewById(R.id.xianshi_rela);
        mGridView_xianshi = (GridView) findViewById(R.id.gridView_xianshi);
        mIv_back = (ImageView) findViewById(R.id.iv_back);
        //常用地址相关---------------start
        mLv_commonly_used_address = (ListView) findViewById(R.id.lv_commonly_used_address);
        mLv_commonly_used_address.setAdapter(mAdapter);
        mLv_commonly_used_address.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent();
                if ("沙县".equals(mComUsedAddrData.get(position)))
                {
                    intent.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, mComUsedAddrData.get(position));
                } else
                {
                    intent.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(mComUsedAddrData.get(position)));
                }
                setResult(Constant.RequestAndResultCode.RESULT_CODE_ARRIVE_COMMONLY_USED_ADDR, intent);
                finish();
            }
        });
        //获取登陆状态
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mMPhoneNum = sp.getString("phoneNum", "");
        if (!"".equals(mMPhoneNum))
        {
            //登陆状态下 查询本地数据库中是否有保存常用地址
            queryDB();
            mAdapter.notifyDataSetChanged();
        }
        //常用地址相关---------------end

        //省市listview
        mArrive_listView = (ListView) findViewById(R.id.arrive_listView);
        mMyArriveAdapter = new MyArriveAdapter();
        mArrive_listView.setAdapter(mMyArriveAdapter);
        mTv_btn_arrive = (TextView) findViewById(R.id.tv_btn_arrive);
        mTv_btn_comm_used_addr = (TextView) findViewById(R.id.tv_btn_comm_used_addr);
        mMyGridViewAdapter = new MyGridViewAdapter();
        mGridView_xianshi.setAdapter(mMyGridViewAdapter);
        mRefreash_arrive = (ProgressBar) findViewById(R.id.refreash_arrive);

        initEdiText();
        initUserSearchAddr();
    }

    private void initEdiText()
    {
        /**---------初始化EditText----------*/
        mEt_search_city = (EditText) findViewById(R.id.et_search_city);
        //初始化EditText默认在常用地址Tab时不可编辑--start
        mEt_search_city.setEnabled(false);
        mEt_search_city.setBackgroundResource(R.drawable.bg_cardview_gray);
        //初始化EditText默认在常用地址Tab时不可编辑--end
        /**---------初始化用户搜索列表ListView----------*/
        mEt_search_city.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!isCommonlyAddr)
                {
                    mUser_input = s.toString();
                    if (count == 0)
                    {
                        mLv_search_addr.setVisibility(View.GONE);
                        mArrive_listView.setVisibility(View.VISIBLE);
                        mIv_clear.setVisibility(View.GONE);
                    } else
                    {
                        mLv_search_addr.setVisibility(View.VISIBLE);
                        mArrive_listView.setVisibility(View.GONE);
                        mIv_clear.setVisibility(View.VISIBLE);

                        mUserSearchSitesData.clear();
                        mSearchAdapter.notifyDataSetChanged();
                        //比对用户输入的内容，并提取更新显示相关控件
                        for (int i = 0; i < mSitesData.size(); i++)
                        {
                            String siteName = mSitesData.get(i).getSiteName();
                            if (mSitesData.get(i).getSiteName().startsWith(mUser_input.trim()) || mSitesData.get(i).getSiteCode().toLowerCase().startsWith(mUser_input.trim().toLowerCase()))
                            {
                                mUserSearchSitesData.add(siteName);
                                mSearchAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }

    //初始化用户搜索列表ListView
    private void initUserSearchAddr()
    {
        mLv_search_addr = (ListView) findViewById(R.id.lv_search_address);
        mLv_search_addr.setAdapter(mSearchAdapter);
        mLv_search_addr.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent data = new Intent();
                if ("沙县".equals(mUserSearchSitesData.get(position)))
                {
                    data.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, mUserSearchSitesData.get(position));
                } else
                {
                    data.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(mUserSearchSitesData.get(position)));
                    Log.e("-->onItemClick ", mUserSearchSitesData.get(position));
                }
                setResult(Constant.RequestAndResultCode.RESULT_CODE_ARRIVE_SEARCH_ADDR, data);

                //保存用户选择后的地址到本地，储存为常用地址---start
                if (!"".equals(mMPhoneNum))
                {
                    //判断数据库中是否有保存过的数据
                    Cursor mCursor_query = mDb.query(TAB_NAME, new String[]{"addr_name"}, "addr_name=?", new String[]{mUserSearchSitesData.get(position)}, null, null, null);
                    if (!mCursor_query.moveToNext())
                    {
                        mValues.put("addr_name", mUserSearchSitesData.get(position));
                        mDb.insert(TAB_NAME, null, mValues);
                    } else
                    {
                        mDb.delete(TAB_NAME, "addr_name = ?", new String[]{mUserSearchSitesData.get(position)});
                        mValues.put("addr_name", mUserSearchSitesData.get(position));
                        mDb.insert(TAB_NAME, null, mValues);
                    }
                    mCursor_query.close();
                }
                //保存用户选择后的地址到本地，储存为常用地址---end
                finish();
            }
        });
    }

    /**
     * 用户搜索时显示列表的适配器
     */
    class SearchAddrAdapter extends BaseAdapter
    {
        public int getCount()
        {
            return mUserSearchSitesData.size();
        }

        public Object getItem(int position)
        {
            return null;
        }

        public long getItemId(int position)
        {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View layout = getLayoutInflater().inflate(R.layout.list_item_search_city, null);
            TextView tv_city_search = (TextView) layout.findViewById(R.id.tv_city);
            if (mUserSearchSitesData != null && mUserSearchSitesData.size() > 0)
            {
                tv_city_search.setText(mUserSearchSitesData.get(position));
            }
            return layout;
        }
    }

    class MyArriveAdapter extends BaseAdapter implements View.OnClickListener
    {

        private MyGridView mShi_gridView;
        private MyGridAdapter mMyGridAdapter;

        @Override
        public int getCount()
        {
            return parent_list_data.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View inflate = getLayoutInflater().inflate(R.layout.arrive_listitem, null);
            LinearLayout shi_linear = (LinearLayout) inflate.findViewById(R.id.shi_linear);
            TextView listTv = (TextView) inflate.findViewById(R.id.listTv);
            listTv.setText(parent_list_data.get(position).getZoneName());
            listTv.setTag(position);
            listTv.setOnClickListener(this);
            mShi_gridView = (MyGridView) inflate.findViewById(R.id.shi_gridView);
            mMyGridAdapter = new MyGridAdapter();
            mShi_gridView.setAdapter(mMyGridAdapter);
            mShi_gridView.setOnItemClickListener(new MyShiGridViewItemListener());
            if (position == mIsOpen)
            {
                mMyGridAdapter.notifyDataSetChanged();
                shi_linear.setVisibility(View.VISIBLE);
            } else
            {
                shi_linear.setVisibility(View.GONE);
            }
            return inflate;
        }

        class MyShiGridViewItemListener implements AdapterView.OnItemClickListener
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                parent_list_xianshi_name.clear();
                parent_list_xianshi_name.addAll(parent_list_data.get(mShiPostion).getSubZones().get(position).getSubZones());
                /**----移除列表中ZoneName为"市区"的下标值----**/
                /****注意:目前返回的Json该值位于List的最后一个元素****/
                parent_list_xianshi_name.remove(parent_list_xianshi_name.size() - 1);

                mMyGridViewAdapter.notifyDataSetChanged();
                mLv_commonly_used_address.setVisibility(View.GONE);
                mArrive_listView.setVisibility(View.GONE);
                mXianshi_rela.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.listTv:
                    int position = (int) v.getTag();
                    if (position == mIsOpen)
                    {
                        mIsOpen = -1;
                    } else
                    {
                        mIsOpen = position;
                        mShiPostion = position;
                    }
                    mMyArriveAdapter.notifyDataSetChanged();
                    break;
            }
        }

        class MyGridAdapter extends BaseAdapter
        {

            @Override
            public int getCount()
            {
                return parent_list_data.get(mShiPostion).getSubZones().size();
            }

            @Override
            public Object getItem(int position)
            {
                return null;
            }

            @Override
            public long getItemId(int position)
            {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View inflate = getLayoutInflater().inflate(R.layout.list_item_city_set_out, null);
                TextView shi_tv = (TextView) inflate.findViewById(R.id.tv_city);
                shi_tv.setText(parent_list_data.get(mShiPostion).getSubZones().get(position).getZoneName());
                return inflate;
            }
        }
    }

    class MyGridViewAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return parent_list_xianshi_name.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View inflate = getLayoutInflater().inflate(R.layout.list_item_city_set_out, null);
            TextView tv_city = (TextView) inflate.findViewById(R.id.tv_city);
            tv_city.setText(parent_list_xianshi_name.get(position).getZoneName());
            return inflate;
        }
    }

    // 初始化数据
    public void initData()
    {
        mRefreash_arrive.setVisibility(View.VISIBLE);
        mLv_commonly_used_address.setVisibility(View.GONE);
        mArrive_listView.setVisibility(View.GONE);
        mXianshi_rela.setVisibility(View.GONE);
        HTTPUtils.get(SelectStationArriveActivity.this, Constant.URLFromAiTon.GET_ZONE_STREE, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {
                /**--------解析Json------------------*/
                Type type = new TypeToken<ArrayList<CompanySubZone>>()
                {
                }.getType();
                ArrayList<CompanySubZone> o = GsonUtils.parseJSONArray(s, type);
                //加载解析Json得到各省份数据
                parent_list_data.clear();
                parent_list_data.addAll(o);
                for (int i = 0; i < parent_list_data.size(); i++)
                {
                    //获取各省份名称，放置于List容器中，用于适配器中更新相关数据
                    parent_list_name.add(i, parent_list_data.get(i).getZoneName());
                    List<String> list1 = new ArrayList<String>();//保存省份下一级的各市地区名称(字符串)
                    for (int j = 0; j < parent_list_data.get(i).getSubZones().size(); j++)
                    {
                        list1.add(parent_list_data.get(i).getSubZones().get(j).getZoneName());
                    }
                    map.put(parent_list_data.get(i).getZoneName(), list1);
                }
                mMyArriveAdapter.notifyDataSetChanged();
                mRefreash_arrive.setVisibility(View.GONE);
                mLv_commonly_used_address.setVisibility(View.GONE);
                mArrive_listView.setVisibility(View.VISIBLE);
                mXianshi_rela.setVisibility(View.GONE);
            }
        });
    }

    private void initSitesData()
    {
        HTTPUtils.get(SelectStationArriveActivity.this, Constant.URLFromAiTon.GET_SITE, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {
                Type type = new TypeToken<ArrayList<Sites>>()
                {
                }.getType();
                mSitesData = GsonUtils.parseJSONArray(s, type);
            }
        });
    }


    private void setOnclick()
    {
        mIv_back.setOnClickListener(this);
        mTv_btn_arrive.setOnClickListener(this);
        mTv_btn_comm_used_addr.setOnClickListener(this);
        findViewById(R.id.back_to_shengshi).setOnClickListener(this);
        mGridView_xianshi.setOnItemClickListener(new MyGridViewOnItemClickListener());
        mIv_clear.setOnClickListener(this);
    }

    class MyGridViewOnItemClickListener implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent();
            if ("沙县".equals(parent_list_xianshi_name.get(position).getZoneName()))
            {
                intent.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, parent_list_xianshi_name.get(position).getZoneName());
            } else
            {
                intent.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(parent_list_xianshi_name.get(position).getZoneName()));
            }
            setResult(Constant.RequestAndResultCode.RESULT_CODE_ARRIVE_ADDR, intent);
            //保存用户选择后的地址到本地，储存为常用地址---start
            if (!"".equals(mMPhoneNum))
            {
                //判断数据库中是否有保存过的数据
                Cursor mCursor_query = mDb.query(TAB_NAME, new String[]{"addr_name"}, "addr_name=?", new String[]{parent_list_xianshi_name.get(position).getZoneName()}, null, null, null);
                if (!mCursor_query.moveToNext())
                {
                    mValues.put("addr_name", parent_list_xianshi_name.get(position).getZoneName());
                    mDb.insert(TAB_NAME, null, mValues);
                } else
                {
                    mDb.delete(TAB_NAME, "addr_name = ?", new String[]{parent_list_xianshi_name.get(position).getZoneName()});
                    mValues.put("addr_name", parent_list_xianshi_name.get(position).getZoneName());
                    mDb.insert(TAB_NAME, null, mValues);
                }
                mCursor_query.close();
            }
            //保存用户选择后的地址到本地，储存为常用地址---end
            finish();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.back_to_shengshi:
                mLv_commonly_used_address.setVisibility(View.GONE);
                mArrive_listView.setVisibility(View.VISIBLE);
                mXianshi_rela.setVisibility(View.GONE);
                break;
            case R.id.iv_back:
                finish();
                AnimFromRightToLeft();
                break;
            case R.id.tv_btn_arrive:
                initData();
                isCommonlyAddr = false;
                mTv_btn_arrive.setBackgroundResource(R.color.tabs_select);
                mTv_btn_arrive.setTextColor(getResources().getColor(R.color.white));
                mTv_btn_comm_used_addr.setBackgroundResource(R.color.gray);
                mTv_btn_comm_used_addr.setTextColor(getResources().getColor(R.color.fillin_order_pay_gray_bg));
                mLv_commonly_used_address.setVisibility(View.GONE);
                mArrive_listView.setVisibility(View.VISIBLE);
                mXianshi_rela.setVisibility(View.GONE);
                //设置EditText默认在常用地址Tab时可编辑--start
                mEt_search_city.setEnabled(true);
                mEt_search_city.setBackgroundResource(R.drawable.bg_cardview);
                //设置EditText默认在常用地址Tab时可编辑--end
                break;
            case R.id.tv_btn_comm_used_addr:
                isCommonlyAddr = true;
                mTv_btn_comm_used_addr.setBackgroundResource(R.color.tabs_select);
                mTv_btn_comm_used_addr.setTextColor(getResources().getColor(R.color.white));
                mTv_btn_arrive.setBackgroundResource(R.color.gray);
                mTv_btn_arrive.setTextColor(getResources().getColor(R.color.fillin_order_pay_gray_bg));
                mLv_commonly_used_address.setVisibility(View.VISIBLE);
                mArrive_listView.setVisibility(View.GONE);
                mXianshi_rela.setVisibility(View.GONE);
                //设置EditText默认在常用地址Tab时不可编辑--start
                mEt_search_city.setEnabled(false);
                mEt_search_city.setBackgroundResource(R.drawable.bg_cardview_gray);
                //设置EditText默认在常用地址Tab时不可编辑--end
                break;
            case R.id.iv_clear:
                mEt_search_city.setText("");
                mLv_search_addr.setVisibility(View.GONE);
                mArrive_listView.setVisibility(View.VISIBLE);
                break;
        }
    }

    class CommuonUsedAddrAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            if (mComUsedAddrData != null && mComUsedAddrData.size() > 0)
            {
                return mComUsedAddrData.size();
            } else
            {
                return 1;
            }
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View layout = getLayoutInflater().inflate(R.layout.list_item_commonly_used_address, null);
            TextView tv_com_used_addr = (TextView) layout.findViewById(R.id.tv_commonly_used_address);
            if (mComUsedAddrData != null && mComUsedAddrData.size() > 0)
            {
                tv_com_used_addr.setText(mComUsedAddrData.get(position));
            } else
            {
                tv_com_used_addr.setText("没有查找到数据！");
            }
            return layout;
        }
    }

    /**
     * 查询本地数据库
     */
    public void queryDB()
    {
        Cursor mCursor_query = mDb.query(TAB_NAME, null, null, null, null, null, null);
        mComUsedAddrData.clear();
        boolean moveToFirst = mCursor_query.moveToFirst();
        while (moveToFirst)
        {
            String addr_name = mCursor_query.getString(mCursor_query.getColumnIndex("addr_name"));
            mComUsedAddrData.add(addr_name);
            moveToFirst = mCursor_query.moveToNext();
        }
        Collections.reverse(mComUsedAddrData);
        mCursor_query.close();
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
