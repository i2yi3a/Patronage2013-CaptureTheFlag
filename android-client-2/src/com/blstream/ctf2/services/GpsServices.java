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
//public class GpsServices implements LocationListener {
	public class GpsServices  {
	private Context mCtx;

	public GpsServices(Context context) {
		mCtx = context;
	}

	public LatLng getYourLatLang() throws CtfException {
		areServicesAvailable();
		LocationManager locationManager = (LocationManager) mCtx.getSystemService(Context.LOCATION_SERVICE);
		Location location = (Location) locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(location == null) throw new CtfException("getLastKnownLocation returned null");
		return new LatLng(location.getLatitude(), location.getLongitude());
	}

	public void areServicesAvailable() throws CtfException {
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mCtx);
		if (ConnectionResult.SUCCESS != result) {
			throw new CtfException(mCtx.getString(R.string.google_services_not_available));
		}
	}
	
	
// working code from google

//    private final Context mContext;
// 
//    // flag for GPS status
//    boolean isGPSEnabled = false;
// 
//    // flag for network status
//    boolean isNetworkEnabled = false;
// 
//    // flag for GPS status
//    boolean canGetLocation = false;
// 
//    Location location; // location
//    double latitude; // latitude
//    double longitude; // longitude
// 
//    // The minimum distance to change Updates in meters
//    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
// 
//    // The minimum time between updates in milliseconds
//    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
// 
//    // Declaring a Location Manager
//    protected LocationManager locationManager;
// 
//    public GpsServices(Context context) {
//        this.mContext = context;
//        getLocation();
//    }
// 
//    public Location getLocation() {
//        try {
//            locationManager = (LocationManager) mContext
//                    .getSystemService(Context.LOCATION_SERVICE);
// 
//            // getting GPS status
//            isGPSEnabled = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
// 
//            // getting network status
//            isNetworkEnabled = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
// 
//            if (!isGPSEnabled && !isNetworkEnabled) {
//                // no network provider is enabled
//            } else {
//                this.canGetLocation = true;
//                // First get location from Network Provider
//                if (isNetworkEnabled) {
//                    locationManager.requestLocationUpdates(
//                            LocationManager.NETWORK_PROVIDER,
//                            MIN_TIME_BW_UPDATES,
//                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                    Log.d("Network", "Network");
//                    if (locationManager != null) {
//                        location = locationManager
//                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                        if (location != null) {
//                            latitude = location.getLatitude();
//                            longitude = location.getLongitude();
//                        }
//                    }
//                }
//                // if GPS Enabled get lat/long using GPS Services
//                if (isGPSEnabled) {
//                    if (location == null) {
//                        locationManager.requestLocationUpdates(
//                                LocationManager.GPS_PROVIDER,
//                                MIN_TIME_BW_UPDATES,
//                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                        Log.d("GPS Enabled", "GPS Enabled");
//                        if (locationManager != null) {
//                            location = locationManager
//                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                            if (location != null) {
//                                latitude = location.getLatitude();
//                                longitude = location.getLongitude();
//                            }
//                        }
//                    }
//                }
//            }
// 
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
// 
//        return location;
//    }
//     
//    /**
//     * Stop using GPS listener
//     * Calling this function will stop using GPS in your app
//     * */
//    public void stopUsingGPS(){
//        if(locationManager != null){
//            locationManager.removeUpdates(GpsServices.this);
//        }       
//    }
//     
//    /**
//     * Function to get latitude
//     * */
//    public double getLatitude(){
//        if(location != null){
//            latitude = location.getLatitude();
//        }
//         
//        // return latitude
//        return latitude;
//    }
//     
//    /**
//     * Function to get longitude
//     * */
//    public double getLongitude(){
//        if(location != null){
//            longitude = location.getLongitude();
//        }
//         
//        // return longitude
//        return longitude;
//    }
//     
//    /**
//     * Function to check GPS/wifi enabled
//     * @return boolean
//     * */
//    public boolean canGetLocation() {
//        return this.canGetLocation;
//    }
//     
//    /**
//     * Function to show settings alert dialog
//     * On pressing Settings button will lauch Settings Options
//     * */

// 
//    @Override
//    public void onLocationChanged(Location location) {
//    }
// 
//    @Override
//    public void onProviderDisabled(String provider) {
//    }
// 
//    @Override
//    public void onProviderEnabled(String provider) {
//    }
// 
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//    }
// 
//    public IBinder onBind(Intent arg0) {
//        return null;
//    }
	
	
}
