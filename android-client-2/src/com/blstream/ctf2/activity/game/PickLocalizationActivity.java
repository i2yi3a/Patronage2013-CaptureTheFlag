package com.blstream.ctf2.activity.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.blstream.ctf2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Pick Localization Activity
 * 
 * Activity displays Map with current position of the user; User can pick
 * localization for a game and define range of a game
 * 
 * @author Kamil Wisniewski
 * @author Lukasz Dmitrowski
 */

public class PickLocalizationActivity extends FragmentActivity implements OnMapClickListener {
	private GoogleMap mMap;
	private TextView locInfoTV;
	private Marker marker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picklocalization);

		locInfoTV = (TextView) findViewById(R.id.locinfo);
		FragmentManager myFragmentManager = getSupportFragmentManager();
		SupportMapFragment mySupportMapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.map);
		mMap = mySupportMapFragment.getMap();
		mMap.setMyLocationEnabled(true);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setOnMapClickListener((OnMapClickListener) this);
		mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(53.428976, 14.556434)));
		mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
	}

	@Override
	public void onMapClick(LatLng touchedPoint) {
		locInfoTV.setText(touchedPoint.toString());
		marker = mMap.addMarker(new MarkerOptions().position(touchedPoint).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
				.title("Localization"));

	}

	public void onClickChooseLocalizationButton(View v) {
		String sLat, sLong;
		Double dLat = new Double(0);
		Double dLong = new Double(0);
		if (marker != null) {
			dLat = marker.getPosition().latitude;
			dLong = marker.getPosition().longitude;
		}
		sLat = dLat.toString();
		sLong = dLong.toString();
		Intent intentMessage = new Intent();
		intentMessage.putExtra("lat", sLat);
		intentMessage.putExtra("long", sLong);
		setResult(2, intentMessage);
		finish();
	}

}
