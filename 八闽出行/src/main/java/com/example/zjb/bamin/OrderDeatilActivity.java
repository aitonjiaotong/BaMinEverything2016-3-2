package com.example.zjb.bamin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.constant.Constant;
import com.example.zjb.bamin.models.about_order.QueryOrder;
import com.example.zjb.bamin.utils.TimeAndDateFormate;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class OrderDeatilActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView_detialOrderDate;
    private TextView textView_detialBookTicketDate;
    private TextView mTextView_detialOrderStartPlace;
    private QueryOrder mQueryOrder;
    private String mMOrderInfoBookLogAID;
    private ImageView mTiaoxingma;
    private TextView mTextView_detialOrderEndPlace;
    private TextView mTextView_detialOrderTime;
    private TextView mTextView_detialOrderTicketCount;
    private TextView mTextView_detialOrderTicketAllPrice;
    private TextView mTextView_detialOrderTicketphoneNum;
    private TextView mTextView_detialOrderTicketGetNum;
    private ProgressBar mProgressBar_detail_order;
    private LinearLayout mOrder_detail_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_deatil);
        initIntent();
        //查询订单
        findID();
        queryOrder();
        setListener();
    }

    private void findID() {
        mTiaoxingma = (ImageView) findViewById(R.id.tiaoxingma);
        mTextView_detialOrderDate = (TextView) findViewById(R.id.textView_detialOrderDate);
        textView_detialBookTicketDate = (TextView) findViewById(R.id.textView_detialBookTicketDate);
        mTextView_detialOrderStartPlace = (TextView) findViewById(R.id.textView_detialOrderStartPlace);
        mTextView_detialOrderEndPlace = (TextView) findViewById(R.id.textView_detialOrderEndPlace);
        mTextView_detialOrderTime = (TextView) findViewById(R.id.textView_detialOrderTime);
        mTextView_detialOrderTicketCount = (TextView) findViewById(R.id.textView_detialOrderTicketCount);
        mTextView_detialOrderTicketAllPrice = (TextView) findViewById(R.id.textView_detialOrderTicketAllPrice);
        mTextView_detialOrderTicketphoneNum = (TextView) findViewById(R.id.textView_detialOrderTicketphoneNum);
        mTextView_detialOrderTicketGetNum = (TextView) findViewById(R.id.textView_detialOrderTicketGetNum);
        mOrder_detail_linear = (LinearLayout) findViewById(R.id.order_detail);
        mProgressBar_detail_order = (ProgressBar) findViewById(R.id.progressBar_detail_order);
    }

    private void queryOrder() {
        mOrder_detail_linear.setVisibility(View.GONE);
        mProgressBar_detail_order.setVisibility(View.VISIBLE);
        String url = Constant.URL.HOST +
                "QueryBookLog?getTicketCodeOrAID="+mMOrderInfoBookLogAID;
        HTTPUtils.get(OrderDeatilActivity.this, url, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String result = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    mQueryOrder = GsonUtils.parseJSON(result, QueryOrder.class);
                    mOrder_detail_linear.setVisibility(View.VISIBLE);
                    mProgressBar_detail_order.setVisibility(View.GONE);
                    initUI();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取订单对象
     */
    private void initIntent() {
        Intent intent = getIntent();
        mMOrderInfoBookLogAID = intent.getStringExtra("BookLogAID");
    }

    private void setListener() {
        findViewById(R.id.ic_header_back).setOnClickListener(this);
    }

    private void initUI() {
        //生成条形码
        try {
            mTiaoxingma.setImageBitmap(CreateOneDCode(mQueryOrder.getGetTicketCode()));
        } catch (WriterException e) {
            e.printStackTrace();
        }

        mTextView_detialOrderDate.setText("出发日期"+TimeAndDateFormate.dateFormate(mQueryOrder.getSetoutTime()));
        textView_detialBookTicketDate.setText("订票日期"+TimeAndDateFormate.dateFormate(mQueryOrder.getBookTime()));
        mTextView_detialOrderStartPlace.setText(mQueryOrder.getStartSiteName());
        mTextView_detialOrderEndPlace.setText(mQueryOrder.getEndSiteName());
        mTextView_detialOrderTime.setText(TimeAndDateFormate.timeFormate(mQueryOrder.getSetoutTime()));
        mTextView_detialOrderTicketCount.setText("购票张数：" + mQueryOrder.getFullTicket());
        mTextView_detialOrderTicketAllPrice.setText("总支付金额：¥" + mQueryOrder.getPrice());
//        TextView textView_detialOrderTicketPassager = (TextView) findViewById(R.id.textView_detialOrderTicketPassager);
//        String passagerName="";
//        for (int i = 0; i < mOrderInfo.getTickets().size(); i++) {
//            passagerName = passagerName+mOrderInfo.getTickets().get(i).getPassengerName()+" ";
//        }
//        textView_detialOrderTicketPassager.setText("乘车人：" + passagerName);
        mTextView_detialOrderTicketphoneNum.setText("联系电话：" + mQueryOrder.getPhoneNumber());
        mTextView_detialOrderTicketGetNum.setText("取票号：" + mQueryOrder.getGetTicketCode());
    }

    /**
     * 用于将给定的内容生成成一维码 注：目前生成内容为中文的话将直接报错，要修改底层jar包的内容
     *
     * @param content 将要生成一维码的内容
     * @return 返回生成好的一维码bitmap
     * @throws WriterException WriterException异常
     */
    public Bitmap CreateOneDCode(String content) throws WriterException {
        // 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.CODE_128, 800, 200);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_header_back:
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(OrderDeatilActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.setAction("OrderDeatilActivity");
        intent.putExtra("OrderDeatilActivity", "OrderDeatilActivity");
        sendBroadcast(intent);
    }

    //重写back方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setClass(OrderDeatilActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
