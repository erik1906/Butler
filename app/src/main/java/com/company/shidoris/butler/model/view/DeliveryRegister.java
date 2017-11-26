package com.company.shidoris.butler.model.view;

/**
 * Created by isaac on 11/26/17.
 */

public class DeliveryRegister {

    private String date;
    private String status;
    private String total;

    public DeliveryRegister(String date, String status, String total) {
        this.date = date;
        this.status = status;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
