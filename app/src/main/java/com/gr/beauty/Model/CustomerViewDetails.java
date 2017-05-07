package com.gr.beauty.Model;

import com.gr.beauty.View.SalonEditServices;

/**
 * Created by gaurav on 12/3/17.
 */

public class CustomerViewDetails {

    private int _id;
    private String shopName;
    private String areaName;
    private String cityName;
    private long mobileNo;

    public CustomerViewDetails(String shopName, String areaName, String cityName, long mobileNo) {
        this.areaName = areaName;
        this.cityName = cityName;
        this.shopName = shopName;
        this.mobileNo = mobileNo;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }
}
