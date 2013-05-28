package com.blstream.patronage.ctf.web.ui;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * User: mkr
 * Date: 5/21/13
 */
public class TeamBaseUI {

    @NotNull(message = "Team name cannot be Null")
    @NotEmpty(message = "Team name cannot be empty")
    private String name;

    @NotNull(message = "Team latLng cannot be Null")
    @NotEmpty(message = "Team latLng cannot be empty")
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
