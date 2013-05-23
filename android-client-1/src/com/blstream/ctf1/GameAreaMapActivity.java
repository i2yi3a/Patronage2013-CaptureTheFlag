package com.blstream.ctf1;

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

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Miłosz Skalski
 **/

public class    GameAreaMapActivity extends FragmentActivity {

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
		setContentView(R.layout.activity_game_area_map);
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
		Bundle bundle = new Bundle();
		bundle.putDouble("latitude", latitude);
		bundle.putDouble("longitude", longitude);
		bundle.putDouble("radius", radius);
		Intent myIntent = new Intent();
		myIntent.putExtras(bundle);
		setResult(RESULT_OK, myIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save:
			latitude = mGoogleMap.getCameraPosition().target.latitude;
			longitude = mGoogleMap.getCameraPosition().target.longitude;

			LatLngBounds bounds = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;
			float[] results = new float[1];
			Location.distanceBetween(mGoogleMap.getCameraPosition().target.latitude, mGoogleMap.getCameraPosition().target.longitude,
					mGoogleMap.getCameraPosition().target.latitude, bounds.southwest.longitude, results);
			radius = results[0];
			Log.d("CTF", "Save");
			Log.d("CTF", "latitude: " + latitude + " longitude: " + longitude + " radius: " + radius);
			LatLng coords = new LatLng(latitude, longitude);
			marker.setPosition(coords);

			/*
			 * mGoogleMap.addMarker(new MarkerOptions().position(new
			 * LatLng(resultLat1, resultLng1)).icon(
			 * BitmapDescriptorFactory.fromResource(R.drawable.base_blue)));
			 * mGoogleMap.addMarker(new MarkerOptions().position(new
			 * LatLng(resultLat2, resultLng2)).icon(
			 * BitmapDescriptorFactory.fromResource(R.drawable.base_red)));
			 */
			circle.setRadius(radius);
			circle.setCenter(coords);
			break;
		}
		return super.onOptionsItemSelected(item);
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
