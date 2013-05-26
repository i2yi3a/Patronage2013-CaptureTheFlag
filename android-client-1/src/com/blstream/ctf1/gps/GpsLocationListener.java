package com.blstream.ctf1.gps;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.SystemClock;

/**
 * @author Adrian Swarcewicz
 */
class GpsLocationListener implements LocationListener {

	private Gps mGps;
	private double mLatitude;
	private double mLongitude;
	private long mLastLocationChange;
	private boolean fix;

	public GpsLocationListener(Gps gps) {
		mGps = gps;
	}

	@Override
	public void onLocationChanged(Location location) {
		mLatitude = location.getLatitude();
		mLongitude = location.getLongitude();
		mLastLocationChange = SystemClock.elapsedRealtime();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
		mGps.onGpsEnabled();
		onFirstFixSearching();
	}

	@Override
	public void onProviderDisabled(String provider) {
		fix = false;
		mGps.onGpsDisabled();
	}

	public void onFirstFixSearching() {
		mGps.onFirstFixSearching();
	}

	public void onLostFix() {
		fix = false;
		mGps.onLostFix();
	}

	public void onFoundFix() {
		fix = true;
		mGps.onFoundFix();
	}

	public double getLatitude() {
		return mLatitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public long getLastLocationChange() {
		return mLastLocationChange;
	}

	public boolean isFix() {
		return fix;
	}

	public void setFix(boolean fix) {
		this.fix = fix;
	}
}
