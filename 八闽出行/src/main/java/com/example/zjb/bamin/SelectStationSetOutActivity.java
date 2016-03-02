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
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.PinyinUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.constant.Constant;
import com.example.zjb.bamin.models.about_companysubzone.CompanySubZone;
import com.example.zjb.bamin.models.about_companysubzone.SubZone_;
import com.example.zjb.bamin.sql.MySqLite;
import com.example.zjb.bamin.utils.GetLastWordUtil;
import com.example.zjb.bamin.utils.LogUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectStationSetOutActivity extends AppCompatActivity implements View.OnClickListener
{

    //数据库相关----------start
    private String TAB_NAME = "setout";
    private int mVersion = 1;
    private SQLiteDatabase mDb;
    private ContentValues mValues;
    //数据库相关----------end
    private boolean isCommonlyAddr = true;
    private boolean isDone;
    private ImageView mIv_back;
    private ListView mLv_commonly_used_address;
    //常用地址List数据列表
    private List<String> mComUsedAddrData = new ArrayList<String>();
    private CommuonUsedAddrAdapter mCommUseAddrAdapter = new CommuonUsedAddrAdapter();

    //整个Json返回值的数据List
    private List<CompanySubZone> SetOutData = new ArrayList<CompanySubZone>();
    //实际出发地的选择地区名称列表
    private List<SubZone_> mAddressSetOutData = new ArrayList<>();
    private GridViewAdapter mSetOutAddressAdapter = new GridViewAdapter();

    //拼音List数据列表
    private List<String> pinYin_data = new ArrayList<String>();
    //搜索List数据列表
    private List<String> searchAddrData = new ArrayList<String>();
    private SearchAddrAdapter mSearchAdapter = new SearchAddrAdapter();

    private TextView mTv_btn_set_out;
    private TextView mTv_btn_comm_used_addr;
    private EditText mEt_search_zone;
    private ImageView mIv_clear;
    private ListView mLv_search_addr;
    private String mUser_input;
    private GridView mGv_address_set_out;
    private LinearLayout mLl_for_progress_bar;
    private String mMPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station_set_out);


        MySqLite mySqLite = new MySqLite(SelectStationSetOutActivity.this, mVersion);
        mDb = mySqLite.getWritableDatabase();
        mValues = new ContentValues();

        initUI();
        setOnclick();
        initData();
    }


    private void initUI()
    {
        //ActionBar上的回退键
        mIv_back = (ImageView) findViewById(R.id.iv_back);
        //选择出发城市列表Tab
        mTv_btn_set_out = (TextView) findViewById(R.id.tv_btn_set_out);
        //选择出发城市列表加载友好提示
        mLl_for_progress_bar = (LinearLayout) findViewById(R.id.ll_for_progress_bar);
        //选择常用地址列表Tab
        mTv_btn_comm_used_addr = (TextView) findViewById(R.id.tv_btn_comm_used_addr);

        initCommonlyUsedAddr();
        initSetOutAddr();
        initUserSearchAddr();
        initEditText();
    }

    //初始化选择出发城市列表
    private void initSetOutAddr()
    {
        mGv_address_set_out = (GridView) findViewById(R.id.gridview_address_set_out);
        mGv_address_set_out.setAdapter(mSetOutAddressAdapter);
        mGv_address_set_out.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent data = new Intent();
                if ("沙县".equals(mAddressSetOutData.get(position).getZoneName()))
                {
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, mAddressSetOutData.get(position).getZoneName());
                } else
                {
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(mAddressSetOutData.get(position).getZoneName()));
                }
                setResult(Constant.RequestAndResultCode.RESULT_CODE_SET_OUT_ADDR, data);
                //保存用户选择后的地址到本地，储存为常用地址---start
                if (!"".equals(mMPhoneNum))
                {
                    //判断数据库中是否有保存过的数据
                    Cursor mCursor_query = mDb.query(TAB_NAME, new String[]{"addr_name"}, "addr_name=?", new String[]{mAddressSetOutData.get(position).getZoneName()}, null, null, null);
                    if (!mCursor_query.moveToNext())
                    {
                        mValues.put("addr_name", mAddressSetOutData.get(position).getZoneName());
                        mDb.insert(TAB_NAME, null, mValues);
                    } else
                    {
                        mDb.delete(TAB_NAME, "addr_name = ?", new String[]{mAddressSetOutData.get(position).getZoneName()});
                        mValues.put("addr_name", mAddressSetOutData.get(position).getZoneName());
                        mDb.insert(TAB_NAME, null, mValues);
                    }
                    mCursor_query.close();
                }
                //保存用户选择后的地址到本地，储存为常用地址---end
                finish();
            }
        });
    }

    //初始化用户搜索列表
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
                if ("沙县".equals(searchAddrData.get(position)))
                {
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, searchAddrData.get(position));
                } else
                {
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(searchAddrData.get(position)));
                }
                setResult(Constant.RequestAndResultCode.RESULT_CODE_SET_OUT_ADDR, data);
                //保存用户选择后的地址到本地，储存为常用地址---start
                if (!"".equals(mMPhoneNum))
                {
                    //判断数据库中是否有保存过的数据
                    Cursor mCursor_query = mDb.query(TAB_NAME, new String[]{"addr_name"}, "addr_name=?", new String[]{searchAddrData.get(position)}, null, null, null);
                    if (!mCursor_query.moveToNext())
                    {
                        mValues.put("addr_name", searchAddrData.get(position));
                        mDb.insert(TAB_NAME, null, mValues);
                    } else
                    {
                        mDb.delete(TAB_NAME, "addr_name = ?", new String[]{searchAddrData.get(position)});
                        mValues.put("addr_name", searchAddrData.get(position));
                        mDb.insert(TAB_NAME, null, mValues);
                    }
                    mCursor_query.close();
                }
                //保存用户选择后的地址到本地，储存为常用地址---end
                finish();
            }
        });
    }




    //初始化常用地址列表
    private void initCommonlyUsedAddr()
    {
        mLv_commonly_used_address = (ListView) findViewById(R.id.lv_commonly_used_address);
        mLv_commonly_used_address.setAdapter(mCommUseAddrAdapter);
        mLv_commonly_used_address.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent data = new Intent();
                if ("沙县".equals(mComUsedAddrData.get(position)))
                {
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, mComUsedAddrData.get(position));
                } else
                {
                    LogUtil.show("onItemClick SelectStationSetOutActivity", GetLastWordUtil.GetRidOfLastWord(mComUsedAddrData.get(position)));
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(mComUsedAddrData.get(position)));
                }
                setResult(Constant.RequestAndResultCode.RESULT_CODE_SET_OUT_ADDR, data);
                finish();
            }
        });
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mMPhoneNum = sp.getString("phoneNum", "");
        if (!"".equals(mMPhoneNum))
        {
            //登陆状态下 查询本地数据库中是否有保存常用地址
            queryDB();
            mCommUseAddrAdapter.notifyDataSetChanged();
        }

    }

    //初始化EditText控件
    private void initEditText()
    {
        mIv_clear = (ImageView) findViewById(R.id.iv_clear);
        mEt_search_zone = (EditText) findViewById(R.id.et_search_zone);
        //初始化EditText默认在常用地址Tab时不可编辑--start
        mEt_search_zone.setEnabled(false);
        mEt_search_zone.setBackgroundResource(R.drawable.bg_cardview_gray);
        //初始化EditText默认在常用地址Tab时不可编辑--end
        mEt_search_zone.addTextChangedListener(new TextWatcher()
        {
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!isCommonlyAddr)
                {
                    mUser_input = s.toString();
                    if (count == 0)
                    {
                        mLv_search_addr.setVisibility(View.GONE);
                        mGv_address_set_out.setVisibility(View.VISIBLE);
                        mIv_clear.setVisibility(View.GONE);
                    } else
                    {
                        mLv_search_addr.setVisibility(View.VISIBLE);
                        mGv_address_set_out.setVisibility(View.GONE);
                        mIv_clear.setVisibility(View.VISIBLE);
                    }
                    searchAddrData.clear();
                    mSearchAdapter.notifyDataSetChanged();
                    //比对用户输入的内容，并提取更新显示相关控件
                    for (int i = 0; i < mAddressSetOutData.size(); i++)
                    {
                        String zoneName = mAddressSetOutData.get(i).getZoneName();
                        if (mAddressSetOutData.get(i).getZoneName().startsWith(mUser_input.trim()) || pinYin_data.get(i).startsWith(mUser_input.trim()))
                        {
                            searchAddrData.add(zoneName);
                            mSearchAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            public void afterTextChanged(Editable s)
            {
            }
        });
    }

    public void initData()
    {
        HTTPUtils.get(SelectStationSetOutActivity.this, Constant.URLFromAiTon.GET_COMPANY_SUBZONE, new VolleyListener()
        {
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            public void onResponse(String s)
            {
                Type type = new TypeToken<ArrayList<CompanySubZone>>()
                {
                }.getType();
                SetOutData = GsonUtils.parseJSONArray(s, type);
                /**----移除列表中ZoneName为"市区"的下标值----**/
                /****注意:目前返回的Json该值位于List的最后一个元素****/
                for (int i = 0; i < SetOutData.size(); i++)
                {
                    for (int j = 0; j < SetOutData.get(i).getSubZones().size(); j++)
                    {
                        mAddressSetOutData.addAll(SetOutData.get(i).getSubZones().get(j).getSubZones());
                        mAddressSetOutData.remove((mAddressSetOutData.size() - 1));
                    }

                }
                if (mAddressSetOutData != null && mAddressSetOutData.size() > 0)
                {
                    pinYin_data = getPinYin(mAddressSetOutData);
                    mSetOutAddressAdapter.notifyDataSetChanged();
                    isDone = true;
                    mLl_for_progress_bar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setOnclick()
    {
        mIv_back.setOnClickListener(this);
        mTv_btn_set_out.setOnClickListener(this);
        mTv_btn_comm_used_addr.setOnClickListener(this);
        mIv_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                AnimFromRightToLeft();
                break;
            case R.id.iv_clear:
                mEt_search_zone.setText("");
                mLv_search_addr.setVisibility(View.GONE);
                mGv_address_set_out.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_btn_set_out:
                isCommonlyAddr = false;
                mLl_for_progress_bar.setVisibility(View.VISIBLE);
                mTv_btn_set_out.setBackgroundResource(R.color.tabs_select);
                mTv_btn_set_out.setTextColor(getResources().getColor(R.color.white));
                mTv_btn_comm_used_addr.setBackgroundResource(R.color.gray);
                mTv_btn_comm_used_addr.setTextColor(getResources().getColor(R.color.fillin_order_pay_gray_bg));
                mLv_commonly_used_address.setVisibility(View.GONE);
                mLv_search_addr.setVisibility(View.GONE);
                mGv_address_set_out.setVisibility(View.VISIBLE);
                if (!isDone)
                {
                    initData();
                } else
                {
                    mLl_for_progress_bar.setVisibility(View.GONE);
                }
                //设置EditText默认在常用地址Tab时可编辑--start
                mEt_search_zone.setEnabled(true);
                mEt_search_zone.setBackgroundResource(R.drawable.bg_cardview);
                //设置EditText默认在常用地址Tab时可编辑--end
                break;
            case R.id.tv_btn_comm_used_addr:
                isCommonlyAddr = true;
                mTv_btn_comm_used_addr.setBackgroundResource(R.color.tabs_select);
                mTv_btn_comm_used_addr.setTextColor(getResources().getColor(R.color.white));
                mTv_btn_set_out.setBackgroundResource(R.color.gray);
                mTv_btn_set_out.setTextColor(getResources().getColor(R.color.fillin_order_pay_gray_bg));
                mLv_commonly_used_address.setVisibility(View.VISIBLE);
                mGv_address_set_out.setVisibility(View.GONE);
                mLl_for_progress_bar.setVisibility(View.GONE);
                //设置EditText默认在常用地址Tab时不可编辑--start
                mEt_search_zone.setEnabled(false);
                mEt_search_zone.setBackgroundResource(R.drawable.bg_cardview_gray);
                //设置EditText默认在常用地址Tab时不可编辑--end
                //TODO 查询本地数据库，将常用选择过的地址加载并显示
                break;
        }
    }

    /**
     * 汉字转拼音并截取首字母
     */
    public List<String> getPinYin(List<SubZone_> data)
    {
        List<String> pinyin_list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++)
        {
            String pingYin = PinyinUtils.getAlpha(data.get(i).getZoneName());
            String str_lower = pingYin.toLowerCase();
            pinyin_list.add(str_lower);
        }
        return pinyin_list;
    }

    /**
     * 常用地址列表适配器
     */
    class CommuonUsedAddrAdapter extends BaseAdapter
    {
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
     * 用户搜索时显示列表的适配器
     */
    class SearchAddrAdapter extends BaseAdapter
    {
        public int getCount()
        {
            return searchAddrData.size();
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
            if (searchAddrData != null && searchAddrData.size() > 0)
            {
                tv_city_search.setText(searchAddrData.get(position));
            }
            return layout;
        }
    }

    /**
     * GridView 出发城市列表适配器
     */
    class GridViewAdapter extends BaseAdapter
    {
        public int getCount()
        {
            if (mAddressSetOutData != null && mAddressSetOutData.size() > 0)
            {
                return mAddressSetOutData.size();
            } else
            {
                return 0;
            }
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
            View layout = getLayoutInflater().inflate(R.layout.list_item_city_set_out, null);
            TextView tv_cityName = (TextView) layout.findViewById(R.id.tv_city);
            TextView pinyin = (TextView) layout.findViewById(R.id.tv_pinyin);
            if (mAddressSetOutData != null && mAddressSetOutData.size() > 0)
            {
                tv_cityName.setText(mAddressSetOutData.get(position).getZoneName());
            }
            if (pinYin_data != null && pinYin_data.size() > 0)
            {
                pinyin.setText(pinYin_data.get(position));
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
