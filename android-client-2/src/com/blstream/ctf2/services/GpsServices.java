package com.blstream.ctf2.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.blstream.ctf2.R;
import com.blstream.ctf2.exception.CtfException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;

/**
 * TODO: to make it handler or asynctask, create method to send player location
 * to server (waiting for api)
 * 
 * @author Karol Firmanty
 * 
 */
public class GpsServices {
	private Context mCtx;

	public GpsServices(Context context) {
		mCtx = context;
	}

	public LatLng getYourLatLang() throws CtfException {
		areServicesAvailable();
		LocationManager locationManager = (LocationManager) mCtx.getSystemService(Context.LOCATION_SERVICE);
		Location location = (Location) locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		return new LatLng(location.getLatitude(), location.getLongitude());
	}

	public void areServicesAvailable() throws CtfException {
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mCtx);
		if (ConnectionResult.SUCCESS != result) {
			throw new CtfException(mCtx.getString(R.string.google_services_not_available));
		}
	}
}
