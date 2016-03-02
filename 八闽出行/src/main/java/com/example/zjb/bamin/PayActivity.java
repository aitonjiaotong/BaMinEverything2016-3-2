package com.example.zjb.bamin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.alipay.PayResult;
import com.example.zjb.bamin.alipay.SignUtils;
import com.example.zjb.bamin.constant.Constant;
import com.example.zjb.bamin.models.about_order.QueryOrder;
import com.example.zjb.bamin.utils.TimeAndDateFormate;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PayActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler mHandlerTime = new Handler();
    //剩余时间
    private int lastTime = 1200;
    private TextView mTicket_count;
    private String mOrderState;
    private TextView mSurplusTime;
    //支付宝相关start
    // 商户PID
    public static final String PARTNER = "2088711900901747";
    // 商户收款账号
    public static final String SELLER = "lurenhua@aiton.com.cn";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALCKLf3VXqSQX3ABi8eAar58zVgQ6AjI7egEWaXLiJ+Dp2ZBGWom1d0LS3Ybdo3mgsCAQS/duufPzWPoPDVllTFk+2B+zfeTuXj7kDo3fVYt6laB07E0iijzMPFHSuMCFuqlS06dSSnzwzUpQ6fohVdyftoXzZyidFWAhJgrInUvAgMBAAECgYEAgtRkcRCHKv2zPJZouFh6wuBKbVFaI+iEJsXSigPkJBK1JBpqcziElWOhcM25dj+19mMV7BsuQEzHsZqRxCCcBtvu5+DztX1l8KmYpqdyccQ4m/oEVcVneWRDmt419+Tm2I7YEyoXERC6Gh1Dypw0A5SJdLibEfFeKoHEnoHzzkECQQDU4nQqjBu4uXbpfrjXKfUZN8+k5u/+4Yhy3VZOLUvmTQjqZVKeirGImjrddiRp56sFrmsZhanqK1UKieayI5rrAkEA1EtV6vIV9OpCgVeLaWLOYvK0eX3loiDlQc7IIAgt18dddlSUYLATFzrMt1dnEteyiVXNphwJwx8mqkWU4/Z1zQJAe1QhtlBq8u5HDGhEjyoYex6RJdhAKynfUaQWjr3BHc99HcXLQlvZE+k9tvTtjYkP0//Cvgtob2fhIXTYeFUWNwJAFOtfiglU9I9pAknYKQhdgg6cjiRDzpgjPzrbKZzkt5CjuxdWj7iKCQ34QlDQjWDH7RSRcT7uD1YwfzLgGx6cOQJAQ1tOCyvjcnetnCTNDOeCwaZF2LHtUWAnp6MyPE1Yq12RbjG0q6ai23a5tSWoHfCqHuA/77XXAShhj5dnQMHmRw==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //支付成功向金点通发送确认订单
                        confrimOrder();
                        setSuccessDialog("支付成功", "查看订单");
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            setFailDialog("支付结果确认中", "确认");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            cancleOrder();
                            setFailDialog("支付失败", "确认");
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(PayActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    private String mBookLogAID;
    private QueryOrder mQueryOrder;
    private RadioGroup mPay_mode;
    private RadioButton mRadioButton_zhifubao;
    private RadioButton mRadioButton_weixin;
    //支付方式
    private String payMode="支付宝";
    /**
     * 支付失败，取消订单
     */
    private void cancleOrder() {
        String url = Constant.URL.HOST +
                "SellTicket_NoBill_Cancel?scheduleCompanyCode="+"YongAn"+
                "&bookLogAID="+mBookLogAID;
        HTTPUtils.get(PayActivity.this, url, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Log.e("onResponse ", "onResponse取消订单" + s);
            }
        });
    }

    /**
     * 支付成功，确认订单
     */
    private void confrimOrder() {
        String url = Constant.URL.HOST +
                "SellTicket_NoBill_Confirm?scheduleCompanyCode=" + "YongAn" +
                "&bookLogAID=" + mBookLogAID;
        HTTPUtils.get(PayActivity.this, url, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Log.e("onResponse ", "onResponse确认订单" + s);
            }
        });
    }

    private Runnable mR;

    //支付宝相关以上end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initIntent();
        findID();
        queryOrderInfo();
        //查询剩余时间
        queryTime();
        setListener();
    }

    private void findID() {
        mPay_mode = (RadioGroup) findViewById(R.id.pay_mode);
        mRadioButton_zhifubao = (RadioButton) findViewById(R.id.radioButton_zhifubao);
        mRadioButton_weixin = (RadioButton) findViewById(R.id.radioButton_weixin);
    }

    private void queryOrderInfo() {
        String url = Constant.URL.HOST +
                "QueryBookLog?getTicketCodeOrAID=" + mBookLogAID;
        HTTPUtils.get(PayActivity.this, url, new VolleyListener() {
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
                    mQueryOrder = GsonUtils.parseJSON(result, QueryOrder.class);
                    initUI();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void queryTime() {
        String url_web = Constant.URL.HOST +
                "SellTicket_Other_NoBill_GetBookStateAndMinuteToConfirm?scheduleCompanyCode=" + "YongAn" + "" +
                "&bookLogID=" + mBookLogAID;
        HTTPUtils.get(PayActivity.this, url_web, new VolleyListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }

            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    mOrderState = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    Log.e("onResponse ", "onResponsepay" + mOrderState);
                    String time = mOrderState.substring(6, mOrderState.length());
                    /**
                     * 将字符截成数组
                     */
                    String replace = time.replace(".", ",");
                    String[] split = replace.split(",");
                    Log.e("onResponse ", "onResponse "+split[0]+"----"+split[1]);
                    lastTime = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
                    //设置票订单倒计时
                    setTime();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 票订单支付倒计时
     */
    private void setTime() {

        mR = new Runnable() {
            @Override
            public void run() {
                mSurplusTime.setText(TimeFormat(lastTime));
                if (lastTime == 0) {
                    return;
                }
                lastTime--;
                mHandlerTime.postDelayed(mR, 1000);
            }
        };
        mHandlerTime.postDelayed(mR, 0);
    }

    //将秒数转换成时间格式
    private String TimeFormat(int progress) {
        int min = progress / 60;
        int sec = progress % 60;
        //设置整数的输出格式：  %02d  d代表int  2代码位数    0代表位数不够时前面补0
        String time = String.format("%02d", min) + ":" + String.format("%02d", sec);
        return time;
    }

    private void initIntent() {
        Intent intent = getIntent();
        mBookLogAID = intent.getStringExtra("BookLogAID");
    }

    private void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.pay).setOnClickListener(this);
        mPay_mode.setOnCheckedChangeListener(new MyOnCheckChangeListener());
    }

    class MyOnCheckChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (mRadioButton_zhifubao.isChecked()){
                payMode="支付宝";
            }else if (mRadioButton_weixin.isChecked()){
//                payMode="微信";
                setDialog("微信支付暂未开通","确认");
            }
        }
    }

    private void initUI() {
        mTicket_count = (TextView) findViewById(R.id.ticket_count);
        mTicket_count.setText(mQueryOrder.getFullTicket() + "");
        TextView ticket_price = (TextView) findViewById(R.id.ticket_price);
        ticket_price.setText("¥" + mQueryOrder.getPrice());
        mSurplusTime = (TextView) findViewById(R.id.surplusTime);
        TextView textView_detialOrderDate = (TextView) findViewById(R.id.textView_detialOrderDate);
        textView_detialOrderDate.setText("出发日期"+ TimeAndDateFormate.dateFormate(mQueryOrder.getSetoutTime()));
        TextView textView_detialOrderStartPlace = (TextView) findViewById(R.id.textView_detialOrderStartPlace);
        textView_detialOrderStartPlace.setText(mQueryOrder.getStartSiteName());
        TextView textView_detialOrderEndPlace = (TextView) findViewById(R.id.textView_detialOrderEndPlace);
        textView_detialOrderEndPlace.setText(mQueryOrder.getEndSiteName());
        TextView textView_detialOrderTime = (TextView) findViewById(R.id.textView_detialOrderTime);
        textView_detialOrderTime.setText(TimeAndDateFormate.timeFormate(mQueryOrder.getSetoutTime()));
    }
    //支付宝------start

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay() {
        if (lastTime>0){
            String orderInfo = getOrderInfo("车票", "车票信息", mQueryOrder.getPrice()+"");

            /**
             * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
             */
            String sign = sign(orderInfo);
            try {
                /**
                 * 仅需对sign 做URL编码
                 */
                sign = URLEncoder.encode(sign, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            /**
             * 完整的符合支付宝参数规范的订单信息
             */
            final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(PayActivity.this);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(payInfo, true);

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }else{
            setFailDialog("支付超时，失败", "确认");
        }

    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        Log.e("", "" + key);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    //支付宝------end
    //支付成功dialog提示
    private void setSuccessDialog(String messageTxt, String iSeeTxt) {
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
        final AlertDialog dialog = builder.setView(commit_dialog)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.putExtra("BookLogAID",mBookLogAID);
                intent.setClass(PayActivity.this, OrderDeatilActivity.class);
                startActivity(intent);
            }
        });
    }
    //支付失败dialog提示
    private void setFailDialog(String messageTxt, String iSeeTxt) {
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
        final AlertDialog dialog = builder.setView(commit_dialog)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.putExtra("OrderDeatilActivity","OrderDeatilActivity");
                intent.setClass(PayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //dialog提示
    private void setDialog(String messageTxt, String iSeeTxt) {
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
        final AlertDialog dialog = builder.setView(commit_dialog)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mRadioButton_zhifubao.setChecked(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay:
                if ("支付宝".equals(payMode)){
                    pay();
                }
                break;
            case R.id.iv_back:
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(PayActivity.this, MainActivity.class);
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
            intent.setClass(PayActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
