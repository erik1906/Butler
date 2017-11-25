package com.company.shidoris.butler.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Erik on 23/11/2017.
 */

public class RoadsResponse implements Serializable {

    @SerializedName("snappedPoints")
    private SnappedNotes snappedNotes;

    public SnappedNotes getSnappedNotes() {
        return snappedNotes;
    }

    public void setSnappedNotes(SnappedNotes snappedNotes) {
        this.snappedNotes = snappedNotes;
    }
}
