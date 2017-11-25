package com.company.shidoris.butler.model.MapsDirection;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Erik on 24/11/2017.
 */

public class OverviewPolyline implements Serializable{

    @SerializedName("points")
    String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
