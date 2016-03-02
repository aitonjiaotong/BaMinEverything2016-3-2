package com.example.zjb.bamin.busline.busline_aition_utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MyOrientationListener implements SensorEventListener
{
    private SensorManager mSensorManager;//传感器的管理者
    private Context mContext;//上下文
    private Sensor mSensor;//传感器
    private float lastX;

    public MyOrientationListener (Context context)
    {
        this.mContext = context;
    }

    @SuppressWarnings("deprecation")
    public void start ()
    {
        ///拿到系统服务
        mSensorManager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null)
        {
            // 获得方向传感器
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }

        if (mSensor != null)
        {
            ////传感器管理 添加监听
            mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void stop ()
    {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged (Sensor arg0, int arg1)
    {
        // TODO Auto-generated method stub

    }

    @SuppressWarnings({"deprecation"})
    @Override
    public void onSensorChanged (SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
        {
            float x = event.values[SensorManager.DATA_X];

            if (Math.abs(x - lastX) > 1.0)
            {
                if (mOnOrientationListener != null)
                {
                    mOnOrientationListener.onOrientationChanged(x);
                }
            }
            lastX = x;

        }
    }

    private OnOrientationListener mOnOrientationListener;

    public void setOnOrientationListener (
            OnOrientationListener mOnOrientationListener)
    {
        this.mOnOrientationListener = mOnOrientationListener;
    }

    public interface OnOrientationListener
    {
        void onOrientationChanged(float x);
    }
}
