package com.example.zjb.bamin.busline.busline_aiton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zjb.bamin.R;
import com.example.zjb.bamin.busline.busline_aition_utils.MyConstants;
import com.example.zjb.bamin.busline.busline_aiton_model.CorporationInfo;

import java.util.ArrayList;
import java.util.List;

public class ChooseCityActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView mIv_back;
    private ListView mLv_choose_city;
    private List<CorporationInfo> mListViewData = new ArrayList<CorporationInfo>();
    private MyListViewAdapter mAdapter;
    private int mPosition;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);

        initUI();
        setOnclick();
        initData();
    }


    private void initUI ()
    {
        mIv_back = (ImageView) findViewById(R.id.iv_choosecity_back);
        mLv_choose_city = (ListView) findViewById(R.id.lv_choose_city);
        mAdapter = new MyListViewAdapter();
        mLv_choose_city.setAdapter(mAdapter);
    }

    private void setOnclick ()
    {
        mIv_back.setOnClickListener(this);
        mLv_choose_city.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id)
            {
                mPosition = position;
                mAdapter.notifyDataSetChanged();
                Intent data = new Intent();
                data.putExtra(MyConstants.IntentKey.CHOOSE_CITY_KEY,mPosition);
                setResult(MyConstants.RequestAndResultCode.CHOOSE_CITY_ACTIVITY_RESUL, data);
                finish();
            }
        });
    }

    @Override
    public void onClick (View v)
    {
        switch (v.getId())
        {
            case R.id.iv_choosecity_back:
                finish();
                break;

            default:
                break;
        }

    }

    private void initData ()
    {
        mListViewData.add(new CorporationInfo("深圳", "合作方：\n深圳交通运输委员会\n深圳易行网交通科技有限公司\n数据试运营中，正在完善，敬请期待"));
        mListViewData.add(new CorporationInfo("厦门", "合作方：\n厦门公交集团有限公司\n数据试运营中，正在完善，敬请期待"));
        mListViewData.add(new CorporationInfo("东莞", "合作方：\n东莞市交通运输局\n数据试运营中，正在完善，敬请期待"));

        Intent intent = getIntent();
        mPosition = intent.getIntExtra(MyConstants.IntentKey.CHOOSE_CITY_KEY,-1);
        mAdapter.notifyDataSetChanged();
    }

    class MyListViewAdapter extends BaseAdapter
    {

        @Override
        public int getCount ()
        {
            return mListViewData.size();
        }

        @Override
        public Object getItem (int position)
        {
            return null;
        }

        @Override
        public long getItemId (int position)
        {
            return 0;
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent)
        {
            View layout = null;
            if (convertView == null)
            {
                layout = getLayoutInflater().inflate(R.layout.choose_city_listview_item, null);
            } else
            {
                layout = convertView;
            }
            ImageView iv_checked = (ImageView) layout.findViewById(R.id.iv_choosecity_checked);
            TextView title = (TextView) layout.findViewById(R.id.choose_city_title);
            TextView subTitle = (TextView) layout.findViewById(R.id.choose_city_subtitle);

            if (mListViewData != null && mListViewData.size() > 0)
            {
                title.setText(mListViewData.get(position).getTitle());
                subTitle.setText(mListViewData.get(position).getSubTitle());
            }

            if(mPosition == position)
            {
                iv_checked.setVisibility(View.VISIBLE);
            }else
            {
                iv_checked.setVisibility(View.GONE);
            }
            return layout;
        }
    }
}
