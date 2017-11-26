package com.company.shidoris.butler.model.view;

/**
 * Created by isaac on 11/26/17.
 */

public class DeliveryRegister {

    private String title;
    private String date;
    private String total;

    public DeliveryRegister(String title, String date, String total) {
        this.title = title;
        this.date = date;
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
