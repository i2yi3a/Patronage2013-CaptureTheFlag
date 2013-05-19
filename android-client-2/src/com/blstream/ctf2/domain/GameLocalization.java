package com.blstream.ctf2.domain;

public class GameLocalization {

	private String name;
	private Integer radius;
	private Double lat;
	private Double lng;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getRadius() {
		return radius;
	}
	
	public void setRadius(Integer radius) {
		this.radius = radius;
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

}
