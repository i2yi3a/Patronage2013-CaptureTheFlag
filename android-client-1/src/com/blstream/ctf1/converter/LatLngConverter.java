package com.blstream.ctf1.converter;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Adrian Swarcewicz
 */
public class LatLngConverter {

	private static final String LATLNG_SEPARATOR = ",";

	public static String toString(LatLng latLng) {
		return latLng.latitude + LATLNG_SEPARATOR + latLng.longitude;
	}
}
