package com.blstream.ctf1.gamemodels;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Piotr Marczycki
 */
public class GameMapMarker {
	protected MarkerOptions mMarkerOptions;
	protected Marker mMarker;

	protected GameMapMarker(LatLng position) {
		mMarkerOptions = new MarkerOptions();
		mMarkerOptions.position(position);
	}

	protected GameMapMarker setIcon(int resourceIconId) {
		mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(resourceIconId));
		return this;
	}

	protected GameMapMarker pinTo(GoogleMap googleMap) {
		mMarker = googleMap.addMarker(mMarkerOptions);
		return this;
	}

	protected LatLng getPosition() {
		if (mMarker != null) {
			return mMarker.getPosition();
		}
		return null;
	}

	protected void setPosition(LatLng position) {
		if (mMarker != null) {
			mMarker.setPosition(position);
		}
	}
}
