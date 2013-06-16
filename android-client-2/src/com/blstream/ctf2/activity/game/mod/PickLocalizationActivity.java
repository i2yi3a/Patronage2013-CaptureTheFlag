package com.blstream.ctf2.activity.game.mod;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.domain.Localization;
import com.blstream.ctf2.services.GameServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
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
	private Marker centerMarker;
	private Marker blueMarker;
	private Marker redMarker;
	byte markerFlag;
	int radius;
	Localization redFlag;
	Localization blueFlag;
	Localization center;
	Circle circle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picklocalization);

		markerFlag = 0;
		FragmentManager myFragmentManager = getSupportFragmentManager();
		SupportMapFragment mySupportMapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.map);
		mMap = mySupportMapFragment.getMap();
		mMap.setMyLocationEnabled(true);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setOnMapClickListener((OnMapClickListener) this);
		mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(53.428976, 14.556434)));
		mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

		redFlag = new Localization(0, 0);
		blueFlag = new Localization(0, 0);
		center = new Localization(0, 0);
		radius = getIntent().getExtras().getInt(GameServices.RADIUS);
	}

	@Override
	public void onMapClick(LatLng touchedPoint) {

		if (markerFlag == 1) {
			if (null != redMarker)
				redMarker.remove();
			redMarker = mMap.addMarker(new MarkerOptions().position(touchedPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.red))
					.title(Constants.KEY_TEAM_RED));
			redFlag.setLat(redMarker.getPosition().latitude);
			redFlag.setLng(redMarker.getPosition().longitude);
		} else if (markerFlag == 2) {
			if (null != blueMarker)
				blueMarker.remove();
			blueMarker = mMap.addMarker(new MarkerOptions().position(touchedPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue))
					.title(Constants.KEY_TEAM_BLUE));
			blueFlag.setLat(blueMarker.getPosition().latitude);
			blueFlag.setLng(blueMarker.getPosition().longitude);
		} else if (markerFlag == 0) {
			if (null != centerMarker)
				centerMarker.remove();
			centerMarker = mMap.addMarker(new MarkerOptions().position(touchedPoint)
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title(Constants.KEY_GAME_AREA));
			
			if (null != circle)
				circle.remove();
			circle = mMap.addCircle(new CircleOptions().center(centerMarker.getPosition()).radius(2000).strokeColor(Color.RED));
			
			center.setLat(centerMarker.getPosition().latitude);
			center.setLng(centerMarker.getPosition().longitude);
		}
	}

	public void onClickRedButton(View v) {
		markerFlag = 1;
	}

	public void onClickLocButton(View v) {
		markerFlag = 0;
	}

	public void onClickBlueButton(View v) {
		markerFlag = 2;
	}

	public void onClickSaveButton(View v) {
		String sLat, sLng;
		Intent intentMessage = new Intent();

		sLat = redFlag.getLat().toString();
		sLng = redFlag.getLng().toString();
		intentMessage.putExtra(GameServices.RED_BASE_LAT, sLat);
		intentMessage.putExtra(GameServices.RED_BASE_LNG, sLng);

		sLat = blueFlag.getLat().toString();
		sLng = blueFlag.getLng().toString();
		intentMessage.putExtra(GameServices.BLUE_BASE_LAT, sLat);
		intentMessage.putExtra(GameServices.BLUE_BASE_LNG, sLng);

		sLat = center.getLat().toString();
		sLng = center.getLng().toString();
		intentMessage.putExtra(GameServices.LAT, sLat);
		intentMessage.putExtra(GameServices.LNG, sLng);

		setResult(6, intentMessage);
		finish();
	}

}
