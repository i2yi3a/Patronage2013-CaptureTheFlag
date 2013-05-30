package com.blstream.ctf1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Miłosz Skalski
 **/

public class    GameDetailsMapActivity extends FragmentActivity {

	private GoogleMap mGoogleMap;
	private SupportMapFragment mSupportMapFragment;
	private double latitude;
	private double longitude;
	private double radius;
	private Marker marker;
	private Circle circle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_details_map);
		mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mGoogleMap = mSupportMapFragment.getMap();

		Intent intent = getIntent();
		latitude = intent.getDoubleExtra("latitude", 0.0);
		longitude = intent.getDoubleExtra("longitude", 0.0);
		radius = intent.getDoubleExtra("radius", 0.0);

		LatLng coords = new LatLng(latitude, longitude);

		if (mGoogleMap != null) {
			mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
			calculatedCamera();
			marker = mGoogleMap.addMarker(new MarkerOptions().position(coords).icon(BitmapDescriptorFactory.fromResource(R.drawable.center)));
			circle = mGoogleMap.addCircle(new CircleOptions().center(coords).radius(radius).strokeColor(Color.GREEN));
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}




	private void calculatedCamera() {
		long Rad = 6371000; // distance of earth's radius in meters
		double d = radius / (double) Rad;
		double brng = Math.toRadians(90);
		double lat1 = Math.toRadians(latitude);
		double lng1 = Math.toRadians(longitude);
		double resultLat1 = Math.asin(Math.sin(lat1) * Math.cos(d) + Math.cos(lat1) * Math.sin(d) * Math.cos(brng));
		double resultLng1 = lng1 + Math.atan2(Math.sin(brng) * Math.sin(d) * Math.cos(lat1), Math.cos(d) - Math.sin(lat1) * Math.sin(resultLat1));
		resultLat1 = Math.toDegrees(resultLat1);
		resultLng1 = Math.toDegrees(resultLng1);
		Log.i("CTF", "resultLat1: " + resultLat1 + " resultLng1: " + resultLng1);

		double lat2 = Math.toRadians(latitude);
		double lng2 = Math.toRadians(longitude);
		double resultLat2 = Math.asin(Math.sin(lat2) * Math.cos(d) + Math.cos(lat2) * Math.sin(d) * Math.cos(brng));
		double resultLng2 = lng2 - Math.atan2(Math.sin(brng) * Math.sin(d) * Math.cos(lat2), Math.cos(d) - Math.sin(lat2) * Math.sin(resultLat2));
		resultLat2 = Math.toDegrees(resultLat2);
		resultLng2 = Math.toDegrees(resultLng2);
		Log.i("CTF", "resultLat2: " + resultLat2 + " resultLng2: " + resultLng2);

		LatLngBounds latLonBound = new LatLngBounds(new LatLng(resultLat2, resultLng2), new LatLng(resultLat1, resultLng1));
		Log.d("CTF", "latLonBound: " + latLonBound);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLonBound, metrics.widthPixels, metrics.widthPixels, 0));
	}
}
