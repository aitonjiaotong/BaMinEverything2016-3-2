package com.example.zjb.bamin.busline.busline_aiton_model;

/**
 * Created by zjb on 2016/2/16.
 */
public class BusLineInfo {
    private String busLineNum;
    private String busLineDirection;

    public BusLineInfo(String busLineNum, String busLineDirection) {
        this.busLineNum = busLineNum;
        this.busLineDirection = busLineDirection;
    }

    public String getBusLineNum() {
        return busLineNum;
    }

    public void setBusLineNum(String busLineNum) {
        this.busLineNum = busLineNum;
    }

    public String getBusLineDirection() {
        return busLineDirection;
    }

    public void setBusLineDirection(String busLineDirection) {
        this.busLineDirection = busLineDirection;
    }
}
