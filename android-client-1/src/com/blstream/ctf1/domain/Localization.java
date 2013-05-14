package com.blstream.ctf1.domain;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Adrian Swarcewicz
 */
public class Localization {
	String name;
	
	Long radius;
	
	LatLng latLng;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getRadius() {
		return radius;
	}

	public void setRadius(Long radius) {
		this.radius = radius;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}
}
