package com.gr.beauty.Model;

/**
 * Created by gaurav on 12/3/17.
 */

public class SalonBookingDetails {

    private int _id;
    private String customerName;
    private long mobileNo;
    private String serviceName;
    private String date;

    public SalonBookingDetails(String customerName, long mobileNo, String serviceName, String date) {
        this.customerName = customerName;
        this.date = date;
        this.mobileNo = mobileNo;
        this.serviceName = serviceName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
}
