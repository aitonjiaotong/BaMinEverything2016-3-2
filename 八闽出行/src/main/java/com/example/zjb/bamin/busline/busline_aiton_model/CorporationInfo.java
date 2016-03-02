package com.example.zjb.bamin.busline.busline_aiton_model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015-12-16.
 */
public class CorporationInfo implements Serializable
{
    private static final long serialVersionUID = 5769542726008868123L;
    String mTitle;
    String mSubTitle;

    public CorporationInfo()
    {

    }

    public CorporationInfo (String title, String subTitle)
    {
        mTitle = title;
        mSubTitle = subTitle;
    }


    public String getTitle ()
    {
        return mTitle;
    }

    public void setTitle (String title)
    {
        mTitle = title;
    }

    public String getSubTitle ()
    {
        return mSubTitle;
    }

    public void setSubTitle (String subTitle)
    {
        mSubTitle = subTitle;
    }

    @Override
    public String toString ()
    {
        return "CorporationInfo{" +
                "mTitle='" + mTitle + '\'' +
                ", mSubTitle='" + mSubTitle + '\'' +
                '}';
    }

    @Override
    public boolean equals (Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CorporationInfo that = (CorporationInfo) o;

        if (mTitle != null ? !mTitle.equals(that.mTitle) : that.mTitle != null) return false;
        return !(mSubTitle != null ? !mSubTitle.equals(that.mSubTitle) : that.mSubTitle != null);

    }

    @Override
    public int hashCode ()
    {
        int result = mTitle != null ? mTitle.hashCode() : 0;
        result = 31 * result + (mSubTitle != null ? mSubTitle.hashCode() : 0);
        return result;
    }
}
