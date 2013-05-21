package com.blstream.patronage.ctf.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TeamBase {

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
