package com.blstream.ctf1.gps;

import android.content.Context;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Adrian Swarcewicz
 */
public abstract class Gps {

	private LocationManager mLocationManager;
	private GpsLocationListener mGpsLocationListener;
	private GpsStatusListener mGpsStatusListener;
	private boolean locationUpdatesActive;

	public Gps(Context context) {
		mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		mGpsLocationListener = new GpsLocationListener(this);
		mGpsStatusListener = new GpsStatusListener(mGpsLocationListener);
	}

	public final void startLocationUpdates() {
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mGpsLocationListener);
		if (isGpsEnabled()) {
			onFirstFixSearching();
		}
		mLocationManager.addGpsStatusListener(mGpsStatusListener);
		locationUpdatesActive = true;
	}

	public final void stopLocationUpdates() {
		mLocationManager.removeUpdates(mGpsLocationListener);
		mLocationManager.removeGpsStatusListener(mGpsStatusListener);
		locationUpdatesActive = false;
		mGpsLocationListener.setFix(false);
		onStopLocationUpdates();
	}

	public final boolean isLocationUpdatesActive() {
		return locationUpdatesActive;
	}

	public final boolean isGpsEnabled() {
		return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public final boolean isFix() {
		return mGpsLocationListener.isFix();
	}
	
	public final Double getLatitude() {
		return mGpsLocationListener.getLatitude();
	}
	
	public final Double getLongitude() {
		return mGpsLocationListener.getLongitude();
	}
	
	public final LatLng getLatLng() {
		return new LatLng(getLatitude(), getLongitude());
	}
	
	protected abstract void onStopLocationUpdates();

	protected abstract void onGpsDisabled();
	
	protected abstract void onGpsEnabled();
	
	protected abstract void onFirstFixSearching();
	
	protected abstract void onFoundFix();

	protected abstract void onLostFix();
}