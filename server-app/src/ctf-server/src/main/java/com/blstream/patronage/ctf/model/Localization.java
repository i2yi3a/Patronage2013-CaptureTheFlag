package com.blstream.patronage.ctf.model;

public class Localization {

    private String name;

    private Double radius;

    private Double[] latLng;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRadius() {
        double radiusDouble = Double.parseDouble(String.format("%.1f", radius));
        return radiusDouble;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double[] getLatLng() {
        return latLng;
    }

    public void setLatLng(Double[] latLng) {
        this.latLng = latLng;
    }
}
