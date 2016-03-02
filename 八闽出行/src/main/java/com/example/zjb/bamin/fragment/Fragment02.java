package com.example.zjb.bamin.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.OrderDeatilActivity;
import com.example.zjb.bamin.PayActivity;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.constant.Constant;
import com.example.zjb.bamin.models.about_order.AccountOrder;
import com.example.zjb.bamin.models.about_order.QueryOrder;
import com.example.zjb.bamin.utils.TimeAndDateFormate;
import com.google.gson.reflect.TypeToken;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


public class Fragment02 extends Fragment implements WaveSwipeRefreshLayout.OnRefreshListener {

    private View mInflate;
    private TextView mNoneOrder;
    private WaveSwipeRefreshLayout mSwipe;
    private ListView mOrderListview;
    private String mId;
    private MyAdapter mMyAdapter;
    private List<AccountOrder> mAccountOrderList = new ArrayList<>();
    private List<QueryOrder> mQueryOrderList = new ArrayList<>();
    private List<String> orderStateList = new ArrayList<>();
    private boolean mIsupdata = false;

    public Fragment02() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_fragment02, null);
            /**
             * 获取用户id
             */
            SharedPreferences sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
            mId = sp.getString("id", "");
            initUI();
            /**
             * 根据用户id查询所有订单号
             */
            queryAccountIdToOrder();
        }

        return mInflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mId = sp.getString("id", "");
    }

    @Override
    public void onStop() {
        super.onStop();
        mSwipe.setRefreshing(false);
    }

    private void queryAccountIdToOrder() {
        mNoneOrder.setVisibility(View.GONE);
        mSwipe.setRefreshing(true);
        mOrderListview.setVisibility(View.INVISIBLE);
        String url = Constant.URLFromAiTon.HOST + "front/ladorderbyuser";
        Map<String, String> map = new HashMap<>();
        map.put("account_id", mId);
        HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                /**
                 *刷新时先清除，重新获取。
                 */
                mAccountOrderList.clear();
                mQueryOrderList.clear();
                orderStateList.clear();
                Log.e("onResponse ", "onResponse根据用户id查询素有订单返回值" + s);
                Type type = new TypeToken<ArrayList<AccountOrder>>() {
                }.getType();
                mAccountOrderList = GsonUtils.parseJSONArray(s, type);
                /**
                 * 翻转容器，让最近的排在最前面
                 */
                Collections.reverse(mAccountOrderList);
                /**
                 * 暂无订单显示与否
                 */
                if (mAccountOrderList.size() > 0) {
                    mNoneOrder.setVisibility(View.GONE);
                    /**
                     * 将所有订单对象和状态都实例化
                     */
                    QueryOrder queryOrder = new QueryOrder("1", 1, null, null, null, null, null, 1, false, false, null, null, null, null, null, 1.1, null, null, "正在生成");
                    for (int i = 0; i < mAccountOrderList.size(); i++) {
                        mQueryOrderList.add(queryOrder);
                        orderStateList.add("正在生成");
                    }
                    /**
                     * 查询所有订单号的对象和状态
                     */
                    for (int i = 0; i < mAccountOrderList.size(); i++) {
                        queryAllOrderInfo(i);
                        queryAllOrderState(i);
                    }
                } else {
                    mNoneOrder.setVisibility(View.VISIBLE);
                    mSwipe.setRefreshing(false);
                }
            }
        });
    }

    /**
     * 查询所有订单状态
     *
     * @param i
     */
    private void queryAllOrderState(final int i) {
        String url_web = Constant.URL.HOST +
                "SellTicket_Other_NoBill_GetBookStateAndMinuteToConfirm?scheduleCompanyCode=" + "YongAn" + "" +
                "&bookLogID=" + mAccountOrderList.get(i).getBookLogAID();
        HTTPUtils.get(getActivity(), url_web, new VolleyListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }

            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String result = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    String state = result.substring(2, 5);
                    orderStateList.remove(i);
                    orderStateList.add(i, state);
                    /**
                     * 防止刷新不一致崩掉
                     */
                    if (!("正在生成".equals(orderStateList.get(mQueryOrderList.size()-1))) && !("正在生成".equals(mQueryOrderList.get(mAccountOrderList.size()-1).getMyStateDesc()))) {
                        mMyAdapter.notifyDataSetChanged();
                        mSwipe.setRefreshing(false);
                        mIsupdata = true;
                        mOrderListview.setVisibility(View.VISIBLE);
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 查询所有订单号的对象
     */
    private void queryAllOrderInfo(final int i) {
        String url = Constant.URL.HOST +
                "QueryBookLog?getTicketCodeOrAID=" + mAccountOrderList.get(i).getBookLogAID();
        HTTPUtils.get(getActivity(), url, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Document doc = null;
                try {
                    Log.e("onResponse ", "onResponse所有订单信息" + s);
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String result = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    QueryOrder queryOrder = GsonUtils.parseJSON(result, QueryOrder.class);
                    mQueryOrderList.remove(i);
                    mQueryOrderList.add(i, queryOrder);
                    /**
                     * 防止刷新不一致崩掉
                     */
                    if (!("正在生成".equals(orderStateList.get(mQueryOrderList.size()-1))) && !("正在生成".equals(mQueryOrderList.get(mAccountOrderList.size()-1).getMyStateDesc()))) {
                        mMyAdapter.notifyDataSetChanged();
                        mSwipe.setRefreshing(false);
                        mIsupdata = true;
                        mOrderListview.setVisibility(View.VISIBLE);
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initUI() {
        mNoneOrder = (TextView) mInflate.findViewById(R.id.noneOrder);
        mSwipe = (WaveSwipeRefreshLayout) mInflate.findViewById(R.id.main_swipe);
        initWaveSwipeRefreshLayout();
        mOrderListview = (ListView) mInflate.findViewById(R.id.order_listView);
        mMyAdapter = new MyAdapter();
        mOrderListview.setAdapter(mMyAdapter);
        mOrderListview.setOnItemClickListener(new MyItemClickListener());
    }

    class MyItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if ("已确认".equals(orderStateList.get(position))) {
                Intent intent = new Intent();
                intent.putExtra("BookLogAID", mAccountOrderList.get(position).getBookLogAID());
                intent.setClass(getActivity(), OrderDeatilActivity.class);
                startActivity(intent);
            } else if ("未确认".equals(orderStateList.get(position))) {
                Intent intent = new Intent();
                intent.putExtra("BookLogAID", mAccountOrderList.get(position).getBookLogAID());
                intent.setClass(getActivity(), PayActivity.class);
                startActivity(intent);
            } else if ("已撤销".equals(orderStateList.get(position))) {
                setDialog("订单已撤销", "确认");
            } else if ("已取票".equals(orderStateList.get(position))) {
                setDialog("已取票", "确认");
            }
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mQueryOrderList.size();
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
            View inflate = getLayoutInflater(getArguments()).inflate(R.layout.order_listitem, null);
            TextView textView_startPlace = (TextView) inflate.findViewById(R.id.textView_startPlace);
            TextView textView_endPlace = (TextView) inflate.findViewById(R.id.textView_endPlace);
            TextView textView_startTime = (TextView) inflate.findViewById(R.id.textView_startTime);
            TextView textView_ticketCount = (TextView) inflate.findViewById(R.id.textView_ticketCount);
            TextView textView_ticketState = (TextView) inflate.findViewById(R.id.textView_ticketState);
            TextView textView_orderPrice = (TextView) inflate.findViewById(R.id.textView_orderPrice);
            if (mIsupdata) {
                textView_startPlace.setText(mQueryOrderList.get(position).getStartSiteName());
                textView_endPlace.setText(mQueryOrderList.get(position).getEndSiteName());
                textView_startTime.setText("出发日期：" + TimeAndDateFormate.dateFormate(mQueryOrderList.get(position).getSetoutTime()));
                textView_ticketCount.setText("票数：" + mQueryOrderList.get(position).getFullTicket());
                /**
                 * 修改返回状态字眼
                 */
                if ("已确认".equals(orderStateList.get(position))) {
                    textView_ticketState.setText("已支付");
                } else if ("未确认".equals(orderStateList.get(position))) {
                    textView_ticketState.setText("未支付");
                } else {
                    textView_ticketState.setText(orderStateList.get(position));
                }
                textView_orderPrice.setText(mQueryOrderList.get(position).getPrice() + "");
            }
            return inflate;
        }
    }

    private void initWaveSwipeRefreshLayout() {
        mSwipe.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mSwipe.setOnRefreshListener(this);
        mSwipe.setWaveColor(getResources().getColor(R.color.title_bar));
        mSwipe.setMaxDropHeight(mSwipe.getHeight() / 3);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
                queryAccountIdToOrder();
                // 更新が終了したらインジケータ非表示
    }

    /**
     * 设置dialog
     *
     * @param messageTxt
     * @param iSeeTxt
     */
    private void setDialog(String messageTxt, String iSeeTxt) {
        View commit_dialog = getLayoutInflater(getArguments()).inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.setView(commit_dialog)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
