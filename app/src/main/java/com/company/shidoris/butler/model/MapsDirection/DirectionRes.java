package com.company.shidoris.butler.model.MapsDirection;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Erik on 24/11/2017.
 */

public class DirectionRes implements Serializable {
    @SerializedName("routes")
    Routes[] routes;
    String status;

    public Routes[] getRoutes() {
        return routes;
    }

    public void setRoutes(Routes[] routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
