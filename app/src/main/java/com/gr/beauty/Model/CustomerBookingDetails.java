package com.gr.beauty.Model;

/**
 * Created by gaurav on 12/3/17.
 */

public class CustomerBookingDetails {

    private int _id;
    private String shopname;
    private long mobileNo;
    private String serviceName;
    private String date;

    public CustomerBookingDetails(String shopname, long mobileNo, String serviceName, String date) {
        this.date = date;
        this.mobileNo = mobileNo;
        this.serviceName = serviceName;
        this.shopname = shopname;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
}
