package com.blstream.patronage.ctf.web.ui;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 5/27/13
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocalizationUI {

    @NotNull(message = "Localization name cannot be Null")
    @NotEmpty(message = "Localization name cannot be empty")
    private String name;

    @NotNull(message = "Localization radius cannot be Null")
    @NotEmpty(message = "Localization radius cannot be empty")
    private Double radius;

    @NotNull(message = "Localization latLng cannot be Null")
    @NotEmpty(message = "Localization latLng cannot be empty")
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
