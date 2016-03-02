package com.example.zjb.bamin.busline.busline_aiton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.zjb.bamin.R;

public class CollectionAddActivity extends AppCompatActivity implements View.OnClickListener
{

    private TextView mCollection_add_cancel;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_add);
        initUI();
    }

    private void initUI ()
    {
        mCollection_add_cancel = (TextView) findViewById(R.id.tv_collection_add_cancel);
        mCollection_add_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick (View v)
    {
        switch (v.getId())
        {
            case R.id.tv_collection_add_cancel:
                finish();
                break;
        }
    }
}
