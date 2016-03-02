package com.example.zjb.bamin;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.constant.Constant;
import com.example.zjb.bamin.models.about_user.User;
import com.example.zjb.bamin.utils.SmsContent;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SmsLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mPhone_num_cancle;
    private ImageView mSms_cancle;
    private EditText mPhone_num;
    private EditText mSms;
    private Button mSendSms;
    private Button mLogin;
    private Runnable mR;
    private int[] mI;
    private boolean isSend = false;
    private String mPhoneNum;
    private EventHandler mEh;
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_login);
        SMSSDK.initSDK(this, "ee115b63d2b6", "243a1d6a31d6832bb156c85a48da0fea");
        initUI();
        setListener();
        sms();
        SmsContent content = new SmsContent(SmsLoginActivity.this, new Handler(), mSms);
        // 注册短信变化监听
        this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, content);
    }

    private void sms() {
        mEh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                switch (event) {
                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            toast("短信验证成功");

                            //向后台服务推送用户短信验证成功，发送手机号----start----//
                            String url = Constant.URLFromAiTon.HOST+"front/FrontLogin?phone=" + mPhoneNum;
                            HTTPUtils.get(SmsLoginActivity.this, url, new VolleyListener() {
                                public void onErrorResponse(VolleyError volleyError) {
                                }

                                public void onResponse(String s) {
                                    mUser = GsonUtils.parseJSON(s, User.class);
                                    //存储手机号和用户id到本地
                                    SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
                                    SharedPreferences.Editor edit = sp.edit();
                                    edit.putString("phoneNum", mPhoneNum);
                                    edit.putString("id", mUser.getId() + "");
                                    edit.commit();
                                    finish();
                                }
                            });
                            //向后台服务推送用户短信验证成功，发送手机号----end----//
                        } else {
                            toast("短信验证失败");
                        }
                        break;
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            toast("获取验证码成功");
                        } else {
                            toast("获取验证码失败" + "开发期间每个手机号每天只能接收5条短信，换个手机号试一试");
                        }
                        break;
                }
            }
        };
        SMSSDK.registerEventHandler(mEh);
    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SmsLoginActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEh);
    }

    private void initUI() {
        mPhone_num_cancle = (ImageView) findViewById(R.id.phone_num_cancle);
        mSms_cancle = (ImageView) findViewById(R.id.sms_cancle);
        mPhone_num = (EditText) findViewById(R.id.phone_num);
        mSms = (EditText) findViewById(R.id.sms);
        mSendSms = (Button) findViewById(R.id.sendSms);
        mLogin = (Button) findViewById(R.id.login);
    }

    private void setListener() {
        mLogin.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        mPhone_num.addTextChangedListener(new MyPhoneNumTextWatcher());
        mSms.addTextChangedListener(new MySmsTextWatcher());
        mPhone_num_cancle.setOnClickListener(this);
        mSms_cancle.setOnClickListener(this);
        mSendSms.setOnClickListener(this);
    }

    class MyPhoneNumTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                mPhone_num_cancle.setVisibility(View.VISIBLE);

            } else {
                mPhone_num_cancle.setVisibility(View.INVISIBLE);
            }
            if (s.length() == 11) {
                mSms.requestFocus();
                if (!isSend) {
                    mSendSms.setEnabled(true);
                }
            } else {
                mSendSms.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class MySmsTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                mSms_cancle.setVisibility(View.VISIBLE);
            } else {
                mSms_cancle.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * 验证手机格式
     */
    public boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                SMSSDK.submitVerificationCode("+86", mPhoneNum, mSms.getText().toString().trim());
                break;
            case R.id.iv_back:
                finish();
                AnimFromRightToLeft();
                break;
            case R.id.phone_num_cancle:
                mPhone_num.setText("");
                break;
            case R.id.sms_cancle:
                mSms.setText("");
                break;
            case R.id.sendSms:
                mPhoneNum = mPhone_num.getText().toString().trim();
                boolean mobileNO = isMobileNO(mPhoneNum);
                if (mobileNO) {
                    SMSSDK.getVerificationCode("+86", mPhoneNum);
                    mSendSms.setEnabled(false);
                    mI = new int[]{60};

                    mR = new Runnable() {
                        @Override
                        public void run() {
                            mSendSms.setText((mI[0]--) + "秒后重发");
                            if (mI[0] == 0) {
                                mSendSms.setEnabled(true);
                                mSendSms.setText("获取验证码");
                                isSend = false;
                                return;
                            } else {
                                isSend = true;
                            }
                            mSendSms.postDelayed(mR, 1000);
                        }
                    };
                    mSendSms.postDelayed(mR, 0);

                    mLogin.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(SmsLoginActivity.this, "输入的手机格式有误", Toast.LENGTH_SHORT).show();
                    mPhone_num.setText("");
                }
                break;
        }
    }

    private void AnimFromRightToLeft() {
        overridePendingTransition(R.anim.fade_in,R.anim.push_left_out );
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            finish();
            AnimFromRightToLeft();
        }
        return super.onKeyDown(keyCode, event);
    };
}
