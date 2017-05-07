package com.gr.beauty.Model;

/**
 * Created by gaurav on 7/3/17.
 */

public class SalonDetails {

    private int _id;
    private String shopname;
    private long mobileNo;
    private String locality;
    private String area;
    private String city;
    private String state;
    private String password;

    public SalonDetails(){
    }

    public SalonDetails(String shopname, long mobileNo, String locality, String area, String city, String state, String password) {
        this.area = area;
        this.city = city;
        this.locality = locality;
        this.mobileNo = mobileNo;
        this.password = password;
        this.shopname = shopname;
        this.state = state;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
