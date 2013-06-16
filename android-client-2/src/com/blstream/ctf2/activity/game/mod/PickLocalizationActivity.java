package com.blstream.ctf2.activity.game.mod;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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
	private Marker mCenterMarker;
	private Marker mBlueMarker;
	private Marker mRedMarker;
	byte mMarkerFlag;
	String sRadius;
	double mRadius;
	Localization mRedFlag;
	Localization mBlueFlag;
	Localization mCenter;
	Circle mCircle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picklocalization);

		mMarkerFlag = 0;
		FragmentManager myFragmentManager = getSupportFragmentManager();
		SupportMapFragment mySupportMapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.map);
		mMap = mySupportMapFragment.getMap();
		mMap.setMyLocationEnabled(true);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setOnMapClickListener((OnMapClickListener) this);
		mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(53.428976, 14.556434)));
		mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

		mRedFlag = new Localization(0, 0);
		mBlueFlag = new Localization(0, 0);
		mCenter = new Localization(0, 0);
		sRadius = getIntent().getExtras().getString(GameServices.RADIUS);
		Log.i("Radius",sRadius);
		mRadius = Double.parseDouble(sRadius);
	}

	@Override
	public void onMapClick(LatLng touchedPoint) {

		if (mMarkerFlag == 1) {
			if (null != mRedMarker)
				mRedMarker.remove();
			mRedMarker = mMap.addMarker(new MarkerOptions().position(touchedPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.red))
					.title(Constants.KEY_TEAM_RED));
			mRedFlag.setLat(mRedMarker.getPosition().latitude);
			mRedFlag.setLng(mRedMarker.getPosition().longitude);
		} else if (mMarkerFlag == 2) {
			if (null != mBlueMarker)
				mBlueMarker.remove();
			mBlueMarker = mMap.addMarker(new MarkerOptions().position(touchedPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue))
					.title(Constants.KEY_TEAM_BLUE));
			mBlueFlag.setLat(mBlueMarker.getPosition().latitude);
			mBlueFlag.setLng(mBlueMarker.getPosition().longitude);
		} else if (mMarkerFlag == 0) {
			if (null != mCenterMarker)
				mCenterMarker.remove();
			mCenterMarker = mMap.addMarker(new MarkerOptions().position(touchedPoint)
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title(Constants.KEY_GAME_AREA));

			mCenter.setLat(mCenterMarker.getPosition().latitude);
			mCenter.setLng(mCenterMarker.getPosition().longitude);
			
			if (null != mCircle)
				mCircle.remove();
			mCircle = mMap.addCircle(new CircleOptions().center(mCenterMarker.getPosition()).radius(mRadius).strokeColor(Color.RED));
		}
	}

	public void onClickRedButton(View v) {
		mMarkerFlag = 1;
	}

	public void onClickLocButton(View v) {
		mMarkerFlag = 0;
	}

	public void onClickBlueButton(View v) {
		mMarkerFlag = 2;
	}

	public void onClickSaveButton(View v) {
		String sLat, sLng;
		Intent intentMessage = new Intent();

		sLat = mRedFlag.getLat().toString();
		sLng = mRedFlag.getLng().toString();
		intentMessage.putExtra(GameServices.RED_BASE_LAT, sLat);
		intentMessage.putExtra(GameServices.RED_BASE_LNG, sLng);

		sLat = mBlueFlag.getLat().toString();
		sLng = mBlueFlag.getLng().toString();
		intentMessage.putExtra(GameServices.BLUE_BASE_LAT, sLat);
		intentMessage.putExtra(GameServices.BLUE_BASE_LNG, sLng);

		sLat = mCenter.getLat().toString();
		sLng = mCenter.getLng().toString();
		intentMessage.putExtra(GameServices.LAT, sLat);
		intentMessage.putExtra(GameServices.LNG, sLng);

		setResult(6, intentMessage);
		finish();
	}

}
