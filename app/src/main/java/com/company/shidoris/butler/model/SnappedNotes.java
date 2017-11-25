package com.company.shidoris.butler.model;

import java.io.Serializable;

/**
 * Created by Erik on 23/11/2017.
 */

public class SnappedNotes implements Serializable {
    private LocationResponse[] location;

    public LocationResponse[] getLocation() {
        return location;
    }

    public void setLocation(LocationResponse[] location) {
        this.location = location;
    }
}
