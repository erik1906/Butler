package com.company.shidoris.butler.model.MapsDirection;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by isaac on 11/27/17.
 */

public class Legs implements Serializable{
    @SerializedName("duration")
    Duration duration;

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
