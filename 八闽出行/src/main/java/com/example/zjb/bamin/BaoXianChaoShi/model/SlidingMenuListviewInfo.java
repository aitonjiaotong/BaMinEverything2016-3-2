package com.example.zjb.bamin.BaoXianChaoShi.model;

/**
 * Created by zjb on 2016/1/29.
 */
public class SlidingMenuListviewInfo {
    private int imgRes;
    private String str;

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public SlidingMenuListviewInfo(int imgRes, String str) {
        this.imgRes = imgRes;
        this.str = str;
    }
}
