package com.blstream.ctf1.domain;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Adrian Swarcewicz
 */
public class Localization {

	String name;

	Double radius;

	LatLng latLng;

	public Localization() {
	}

	public Localization(String name, Double radius, LatLng latLng) {
		this.name = name;
		this.radius = radius;
		this.latLng = latLng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	@Override
	public String toString() {
		return "Localization [name=" + name + ", radius=" + radius + ", latLng=" + latLng + "]";
	}
}
