package com.blstream.patronage.ctf.model;


public class RedTeamBase {

    private String name;
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
