package com.example.zjb.bamin.models.about_zones;


import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class SubZone {

    @Expose
    private Integer ZoneID;
    @Expose
    private String ZoneCode;
    @Expose
    private String ZoneName;
    @Expose
    private Integer ParentZoneID;
    @Expose
    private String FullCode;
    @Expose
    private String FullName;
    @Expose
    private List<SubZone_> SubZones = new ArrayList<SubZone_>();

    /**
     * 
     * @return
     *     The ZoneID
     */
    public Integer getZoneID() {
        return ZoneID;
    }

    /**
     * 
     * @param ZoneID
     *     The ZoneID
     */
    public void setZoneID(Integer ZoneID) {
        this.ZoneID = ZoneID;
    }

    /**
     * 
     * @return
     *     The ZoneCode
     */
    public String getZoneCode() {
        return ZoneCode;
    }

    /**
     * 
     * @param ZoneCode
     *     The ZoneCode
     */
    public void setZoneCode(String ZoneCode) {
        this.ZoneCode = ZoneCode;
    }

    /**
     * 
     * @return
     *     The ZoneName
     */
    public String getZoneName() {
        return ZoneName;
    }

    /**
     * 
     * @param ZoneName
     *     The ZoneName
     */
    public void setZoneName(String ZoneName) {
        this.ZoneName = ZoneName;
    }

    /**
     * 
     * @return
     *     The ParentZoneID
     */
    public Integer getParentZoneID() {
        return ParentZoneID;
    }

    /**
     * 
     * @param ParentZoneID
     *     The ParentZoneID
     */
    public void setParentZoneID(Integer ParentZoneID) {
        this.ParentZoneID = ParentZoneID;
    }

    /**
     * 
     * @return
     *     The FullCode
     */
    public String getFullCode() {
        return FullCode;
    }

    /**
     * 
     * @param FullCode
     *     The FullCode
     */
    public void setFullCode(String FullCode) {
        this.FullCode = FullCode;
    }

    /**
     * 
     * @return
     *     The FullName
     */
    public String getFullName() {
        return FullName;
    }

    /**
     * 
     * @param FullName
     *     The FullName
     */
    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    /**
     * 
     * @return
     *     The SubZones
     */
    public List<SubZone_> getSubZones() {
        return SubZones;
    }

    /**
     * 
     * @param SubZones
     *     The SubZones
     */
    public void setSubZones(List<SubZone_> SubZones) {
        this.SubZones = SubZones;
    }

}
