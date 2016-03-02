package com.example.zjb.bamin.models.about_ticket;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/20.
 */
public class TicketInfo implements Serializable
{

    /**
     * ExecuteScheduleID : 2016-01-16-de4914b9-2558-4b8f-be12-746089247d4d
     * ExecuteDate : /Date(1455638400000)/
     * SetoutTime : /Date(1455686400000)/
     * State : 1
     * ScheduleType : 1
     * StartSiteID : 350702001
     * StartSiteName : 南平
     * EndSiteID : 350102001
     * EndSiteName : 福州北站
     * Limit : -1
     * StationName : 南平汽车站
     * TimeOffset : 0
     * GateName : 4
     * ScheduleBelong : 福州北站
     * PlanScheduleCode : 南加FZ003
     * LineName : 南平-福州北站（竹歧）
     * EndTicketTime : /Date(1455686400000)/
     * FullPrice : 74
     * HalfPrice : 37
     * Mileage : 187
     * FreeSeats : 0
     * LineViaSiteDesc : 南平,福州北站
     * CoachSeatNumber : 38
     * MaxTicket : 0
     * CoachGradeName : 中型高一
     * SeatTypeName : 普通
     * Rebate : false
     * ScheduleAttribute : 2
     * OuternalShare : true
     * StationCanTakeTicket : false
     * CompanyCode : NanPing
     * EndZoneName : 福建省 福州市 鼓楼区
     * RebateDescript : null
     * InsurePrice : 0
     */

    private String ExecuteScheduleID;
    private String ExecuteDate;
    private String SetoutTime;
    private int State;
    private int ScheduleType;
    private String StartSiteID;
    private String StartSiteName;
    private String EndSiteID;
    private String EndSiteName;
    private int Limit;
    private String StationName;
    private int TimeOffset;
    private String GateName;
    private String ScheduleBelong;
    private String PlanScheduleCode;
    private String LineName;
    private String EndTicketTime;
    private double FullPrice;
    private double HalfPrice;
    private int Mileage;
    private int FreeSeats;
    private String LineViaSiteDesc;
    private int CoachSeatNumber;
    private int MaxTicket;
    private String CoachGradeName;
    private String SeatTypeName;
    private boolean Rebate;
    private int ScheduleAttribute;
    private boolean OuternalShare;
    private boolean StationCanTakeTicket;
    private String CompanyCode;
    private String EndZoneName;
    private Object RebateDescript;
    private int InsurePrice;

    public void setExecuteScheduleID(String ExecuteScheduleID) {
        this.ExecuteScheduleID = ExecuteScheduleID;
    }

    public void setExecuteDate(String ExecuteDate) {
        this.ExecuteDate = ExecuteDate;
    }

    public void setSetoutTime(String SetoutTime) {
        this.SetoutTime = SetoutTime;
    }

    public void setState(int State) {
        this.State = State;
    }

    public void setScheduleType(int ScheduleType) {
        this.ScheduleType = ScheduleType;
    }

    public void setStartSiteID(String StartSiteID) {
        this.StartSiteID = StartSiteID;
    }

    public void setStartSiteName(String StartSiteName) {
        this.StartSiteName = StartSiteName;
    }

    public void setEndSiteID(String EndSiteID) {
        this.EndSiteID = EndSiteID;
    }

    public void setEndSiteName(String EndSiteName) {
        this.EndSiteName = EndSiteName;
    }

    public void setLimit(int Limit) {
        this.Limit = Limit;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }

    public void setTimeOffset(int TimeOffset) {
        this.TimeOffset = TimeOffset;
    }

    public void setGateName(String GateName) {
        this.GateName = GateName;
    }

    public void setScheduleBelong(String ScheduleBelong) {
        this.ScheduleBelong = ScheduleBelong;
    }

    public void setPlanScheduleCode(String PlanScheduleCode) {
        this.PlanScheduleCode = PlanScheduleCode;
    }

    public void setLineName(String LineName) {
        this.LineName = LineName;
    }

    public void setEndTicketTime(String EndTicketTime) {
        this.EndTicketTime = EndTicketTime;
    }

    public void setFullPrice(int FullPrice) {
        this.FullPrice = FullPrice;
    }

    public void setHalfPrice(int HalfPrice) {
        this.HalfPrice = HalfPrice;
    }

    public void setMileage(int Mileage) {
        this.Mileage = Mileage;
    }

    public void setFreeSeats(int FreeSeats) {
        this.FreeSeats = FreeSeats;
    }

    public void setLineViaSiteDesc(String LineViaSiteDesc) {
        this.LineViaSiteDesc = LineViaSiteDesc;
    }

    public void setCoachSeatNumber(int CoachSeatNumber) {
        this.CoachSeatNumber = CoachSeatNumber;
    }

    public void setMaxTicket(int MaxTicket) {
        this.MaxTicket = MaxTicket;
    }

    public void setCoachGradeName(String CoachGradeName) {
        this.CoachGradeName = CoachGradeName;
    }

    public void setSeatTypeName(String SeatTypeName) {
        this.SeatTypeName = SeatTypeName;
    }

    public void setRebate(boolean Rebate) {
        this.Rebate = Rebate;
    }

    public void setScheduleAttribute(int ScheduleAttribute) {
        this.ScheduleAttribute = ScheduleAttribute;
    }

    public void setOuternalShare(boolean OuternalShare) {
        this.OuternalShare = OuternalShare;
    }

    public void setStationCanTakeTicket(boolean StationCanTakeTicket) {
        this.StationCanTakeTicket = StationCanTakeTicket;
    }

    public void setCompanyCode(String CompanyCode) {
        this.CompanyCode = CompanyCode;
    }

    public void setEndZoneName(String EndZoneName) {
        this.EndZoneName = EndZoneName;
    }

    public void setRebateDescript(Object RebateDescript) {
        this.RebateDescript = RebateDescript;
    }

    public void setInsurePrice(int InsurePrice) {
        this.InsurePrice = InsurePrice;
    }

    public String getExecuteScheduleID() {
        return ExecuteScheduleID;
    }

    public String getExecuteDate() {
        return ExecuteDate;
    }

    public String getSetoutTime() {
        return SetoutTime;
    }

    public int getState() {
        return State;
    }

    public int getScheduleType() {
        return ScheduleType;
    }

    public String getStartSiteID() {
        return StartSiteID;
    }

    public String getStartSiteName() {
        return StartSiteName;
    }

    public String getEndSiteID() {
        return EndSiteID;
    }

    public String getEndSiteName() {
        return EndSiteName;
    }

    public int getLimit() {
        return Limit;
    }

    public String getStationName() {
        return StationName;
    }

    public int getTimeOffset() {
        return TimeOffset;
    }

    public String getGateName() {
        return GateName;
    }

    public String getScheduleBelong() {
        return ScheduleBelong;
    }

    public String getPlanScheduleCode() {
        return PlanScheduleCode;
    }

    public String getLineName() {
        return LineName;
    }

    public String getEndTicketTime() {
        return EndTicketTime;
    }

    public double getFullPrice() {
        return FullPrice;
    }

    public double getHalfPrice() {
        return HalfPrice;
    }

    public int getMileage() {
        return Mileage;
    }

    public int getFreeSeats() {
        return FreeSeats;
    }

    public String getLineViaSiteDesc() {
        return LineViaSiteDesc;
    }

    public int getCoachSeatNumber() {
        return CoachSeatNumber;
    }

    public int getMaxTicket() {
        return MaxTicket;
    }

    public String getCoachGradeName() {
        return CoachGradeName;
    }

    public String getSeatTypeName() {
        return SeatTypeName;
    }

    public boolean isRebate() {
        return Rebate;
    }

    public int getScheduleAttribute() {
        return ScheduleAttribute;
    }

    public boolean isOuternalShare() {
        return OuternalShare;
    }

    public boolean isStationCanTakeTicket() {
        return StationCanTakeTicket;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public String getEndZoneName() {
        return EndZoneName;
    }

    public Object getRebateDescript() {
        return RebateDescript;
    }

    public int getInsurePrice() {
        return InsurePrice;
    }
}
