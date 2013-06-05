package com.blstream.ctf1.gamemodels;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * @author Piotr Marczycki
 */
public class PlayerModel extends GameMapMarker implements GameEntity {
	private LatLng latLng;
	private byte team; // Consider other type
	private boolean hasFlag;
	private boolean isHeadingCenter;
	private boolean isHeadingBase;

	public PlayerModel() {
		super(new LatLng(0.0, 0.0));
	}

	public PlayerModel(LatLng position, int resourceIconId, GoogleMap googleMap) {
		super(position);
		this.setIcon(resourceIconId).pinTo(googleMap);
	}

	public PlayerModel(LatLng latLng, byte team, boolean hasFlag, boolean isHeadingCenter, boolean isHeadingBase) {
		super(latLng);
		this.latLng = latLng;
		this.team = team;
		this.hasFlag = hasFlag;
		this.isHeadingCenter = isHeadingCenter;
		this.isHeadingBase = isHeadingBase;
	}

	public LatLng getLatLng() {
		return this.latLng;
		// return this.getPosition();
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
		// this.setPosition(latLng);
	}

	public byte getTeam() {
		return team;
	}

	public void setTeam(byte team) {
		this.team = team;
	}

	public boolean isHasFlag() {
		return hasFlag;
	}

	public void setHasFlag(boolean hasFlag) {
		this.hasFlag = hasFlag;
	}

	public boolean isHeadingCenter() {
		return isHeadingCenter;
	}

	public void setHeadingCenter(boolean isHeadingCenter) {
		this.isHeadingCenter = isHeadingCenter;
	}

	public boolean isHeadingBase() {
		return isHeadingBase;
	}

	public void setHeadingBase(boolean isHeadingBase) {
		this.isHeadingBase = isHeadingBase;
	}

	@Override
	public void move(LatLng newPosition) {
		mMarker.setPosition(newPosition);
	}
}
