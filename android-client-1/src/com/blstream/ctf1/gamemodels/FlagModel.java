package com.blstream.ctf1.gamemodels;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * @author Piotr Marczycki
 */
public class FlagModel extends GameMapMarker implements GameEntity {

	public FlagModel(LatLng position, int resourceIconId, GoogleMap googleMap) {
		super(position);
		this.setIcon(resourceIconId).pinTo(googleMap);
	}

	@Override
	public void move(LatLng distance) {
		// don't move flag
	}
}
