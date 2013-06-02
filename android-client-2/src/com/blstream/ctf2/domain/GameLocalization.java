package com.blstream.ctf2.domain;

import java.io.Serializable;

/**
 * @author Rafal Tatol
 */
public class GameLocalization extends Localization implements Serializable {

	private static final long serialVersionUID = 1L;

	public GameLocalization(double lat, double lng) {
		super(lat, lng);
	}

	public GameLocalization() {
	}

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
