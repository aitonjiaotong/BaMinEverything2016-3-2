package com.example.zjb.bamin.busline.busline_aiton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.zjb.bamin.R;

public class DownLoadOffLineMapActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView mIvBack;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_off_line_map);

        initUI();
        setOnClick();
    }

    private void initUI ()
    {
        mIvBack = (ImageView) findViewById(R.id.iv_offline_back);
    }

    private void setOnClick ()
    {
        mIvBack.setOnClickListener(this);
    }


    @Override
    public void onClick (View v)
    {
        switch (v.getId())
        {
            case R.id.iv_offline_back:
                finish();
                break;

            default:
                break;
        }
    }
}
