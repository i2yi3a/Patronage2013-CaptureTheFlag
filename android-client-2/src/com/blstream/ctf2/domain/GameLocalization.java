package com.blstream.ctf2.domain;

/**
 * @author Rafal Tatol
 */
public class GameLocalization extends Localization {
	
	private String name;
	private Integer radius;
	
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
}
