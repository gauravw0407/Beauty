package com.gr.beauty.Model;

/**
 * Created by gaurav on 7/3/17.
 */

public class CustomerDetails {

    private int _id;
    private String name;
    private long mobileNo;
    private String password;

    public  CustomerDetails(){
    }

    public CustomerDetails(String name, long mobileNo, String password) {
        this._id = _id;
        this.name = name;
        this.mobileNo = mobileNo;
        this.password = password;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
