package com.blstream.ctf1.gamemodels;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Piotr Marczycki
 */
public interface GameEntity {
	public void move(LatLng distance);
}
