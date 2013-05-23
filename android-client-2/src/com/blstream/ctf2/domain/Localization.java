package com.blstream.ctf2.domain;

/**
 * @author Rafal Tatol
 */
public class Localization {

	private Double lat;
	private Double lng;
	
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

	@Override
	public String toString() {
		return this.lat.toString() + " " + this.lng.toString();
	}
}
