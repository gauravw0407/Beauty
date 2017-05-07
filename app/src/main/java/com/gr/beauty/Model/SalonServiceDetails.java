package com.gr.beauty.Model;

/**
 * Created by gaurav on 10/3/17.
 */

public class SalonServiceDetails {

    private int _id;
    private String serviceName;
    private String servicePrice;
    private String serviceSeats;

    public SalonServiceDetails(String serviceName, String servicePrice, String serviceSeats) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.serviceSeats = serviceSeats;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceSeats() {
        return serviceSeats;
    }

    public void setServiceSeats(String serviceSeats) {
        this.serviceSeats = serviceSeats;
    }
}
