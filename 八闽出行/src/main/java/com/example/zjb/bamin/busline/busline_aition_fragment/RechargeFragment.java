package com.example.zjb.bamin.busline.busline_aition_fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zjb.bamin.R;
import com.example.zjb.bamin.busline.busline_aiton.PayBusLineActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class RechargeFragment extends Fragment implements View.OnClickListener {

    private View mLayout;
    private TextView[] mCharge = new TextView[6];

    public RechargeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_recharge, null);
        initUI();
        setListener();
        return mLayout;
    }

    private void setListener() {
        for (int i = 0; i < mCharge.length; i++) {
            mCharge[i].setOnClickListener(this);
        }
        mLayout.findViewById(R.id.rechargeButton).setOnClickListener(this);
    }

    private void initUI() {
        mCharge[0] = (TextView) mLayout.findViewById(R.id.charge500);
        mCharge[1] = (TextView) mLayout.findViewById(R.id.charge300);
        mCharge[2] = (TextView) mLayout.findViewById(R.id.charge200);
        mCharge[3] = (TextView) mLayout.findViewById(R.id.charge100);
        mCharge[4] = (TextView) mLayout.findViewById(R.id.charge50);
        mCharge[5] = (TextView) mLayout.findViewById(R.id.charge20);
    }


    @Override
    public void onClick(View v) {
        Intent intent= new Intent();
        switch (v.getId()) {
            case R.id.rechargeButton:
                intent.setClass(getActivity(),PayBusLineActivity.class);
                startActivity(intent);
                break;
            case R.id.charge500:
                selectValue(0);
                break;
            case R.id.charge300:
                selectValue(1);
                break;
            case R.id.charge200:
                selectValue(2);
                break;
            case R.id.charge100:
                selectValue(3);
                break;
            case R.id.charge50:
                selectValue(4);
                break;
            case R.id.charge20:
                selectValue(5);
                break;
        }
    }

    private void selectValue(int valueID) {
        mCharge[(valueID+0)%6].setBackgroundResource(R.color.bg_green_normal);
        mCharge[(valueID+1)%6].setBackgroundResource(R.color.bg_red);
        mCharge[(valueID+2)%6].setBackgroundResource(R.color.bg_red);
        mCharge[(valueID+3)%6].setBackgroundResource(R.color.bg_red);
        mCharge[(valueID+4)%6].setBackgroundResource(R.color.bg_red);
        mCharge[(valueID+5)%6].setBackgroundResource(R.color.bg_red);
    }
}
