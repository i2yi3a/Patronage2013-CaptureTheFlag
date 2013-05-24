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
 * TODO: to make it handler or asynctask
 * @author Karol Firmanty
 *
 */
public class GpsServices {
	private Context mCtx;
	public GpsServices(Context context) {
		mCtx = context;
	}

	public LatLng getYourLatLang() throws CtfException {
		if(!areServicesAvailable()){
			throw new CtfException(mCtx.getString(R.string.google_services_not_available));
		}
		else{
			LocationManager locationManager = (LocationManager)mCtx.getSystemService(Context.LOCATION_SERVICE); 
			Location location = (Location) locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			return new LatLng(location.getLatitude(),location.getLongitude());
		}
	}
	public boolean areServicesAvailable() {
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mCtx);
		if (ConnectionResult.SUCCESS == result) {
			return true;
		} else {
			//GooglePlayServicesUtil.getErrorString(result);
			return false;
		}
	}
}
