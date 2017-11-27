package com.company.shidoris.butler.model.MapsDirection;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Erik on 24/11/2017.
 */

public class Routes implements Serializable{

    @SerializedName("overview_polyline")
    OverviewPolyline overviewPolyline;

    @SerializedName("legs")
    Legs[] legs;

    public Legs[] getLegs() {
        return legs;
    }

    public void setLegs(Legs[] legs) {
        this.legs = legs;
    }

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }
}
