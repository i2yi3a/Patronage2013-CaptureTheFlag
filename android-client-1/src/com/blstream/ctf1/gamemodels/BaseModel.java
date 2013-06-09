package com.blstream.ctf1.gamemodels;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * @author Piotr Marczycki, Rafal Olichwer
 */
public class BaseModel extends GameMapMarker implements GameEntity {
	private LatLng latLng;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BaseModel() {
		super(new LatLng(0.0, 0.0));
	}

	public BaseModel(LatLng position, int resourceIconId, GoogleMap googleMap) {
		super(position);
		this.latLng = position;
		this.setIcon(resourceIconId).pinTo(googleMap);
	}

	public BaseModel(LatLng latLng) {
		super(latLng);
		this.latLng = latLng;
	}
	public LatLng getLatLng() {
		return this.latLng;
		// return this.getPosition();
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
		// this.setPosition(latLng);
	}
	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}


	@Override
	public void move(LatLng newPosition) {
		mMarker.setPosition(newPosition);
	}
	
}
