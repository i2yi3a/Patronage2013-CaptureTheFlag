package com.blstream.patronage.ctf.web.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: mkr
 * Date: 5/21/13
 */
public class TeamBaseUI {

    @JsonProperty("name")
    private String name;

    @JsonProperty("latLng")
    private Double[] latLng;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double[] getLatLng() {
        return latLng;
    }

    public void setLatLng(Double[] latLng) {
        this.latLng = latLng;
    }
}
