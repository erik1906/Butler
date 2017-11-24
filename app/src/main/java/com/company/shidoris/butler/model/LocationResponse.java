package com.company.shidoris.butler.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Erik on 23/11/2017.
 */

public class LocationResponse implements Serializable{


    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
