package com.company.shidoris.butler.model;

import java.util.List;

/**
 * Created by Erik on 17/11/2017.
 */

public class Request {
//    pick up at, deliver at, a list of items and the total cost.

    private String id;
    private String status;
    private String date;
    private String pickupLat;
    private String pickupLong;
    private String deliverLat;
    private String deliverLong;
    private List<Product> productList;

    public Request() {
    }

    public Request(String status, String date, String pickupLat, String pickupLong, String deliverLat, String deliverLong, List<Product> productList) {
        this.status = status;
        this.date = date;
        this.pickupLat = pickupLat;
        this.pickupLong = pickupLong;
        this.deliverLat = deliverLat;
        this.deliverLong = deliverLong;
        this.productList = productList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(String pickupLat) {
        this.pickupLat = pickupLat;
    }

    public String getPickupLong() {
        return pickupLong;
    }

    public void setPickupLong(String pickupLong) {
        this.pickupLong = pickupLong;
    }

    public String getDeliverLat() {
        return deliverLat;
    }

    public void setDeliverLat(String deliverLat) {
        this.deliverLat = deliverLat;
    }

    public String getDeliverLong() {
        return deliverLong;
    }

    public void setDeliverLong(String deliverLong) {
        this.deliverLong = deliverLong;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
