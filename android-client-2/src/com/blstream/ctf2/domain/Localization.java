package com.blstream.ctf2.domain;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Rafal Tatol
 * @author [mod]Marcin Sareło
 */
public class Localization implements Serializable {

	private static final long serialVersionUID = 1L;
	private Double lat;
	private Double lng;

	public Localization() {
	}

	public Localization(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public LatLng getLatLang() {
		return new LatLng(this.lat, this.lng);
	}

	@Override
	public String toString() {
		return this.lat.toString() + " " + this.lng.toString();
	}
}
