package com.blstream.ctf1;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class GameAreaMapActivity extends FragmentActivity {

	private GoogleMap mGoogleMap;
	private SupportMapFragment mSupportMapFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_area_map);
		mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mGoogleMap = mSupportMapFragment.getMap();
		
		LatLng coords = new LatLng(53.432766,14.548001);
		CameraPosition cameraPosition = new CameraPosition.Builder()
	    .target(coords)      // Sets the center of the map to Mountain View
	    .zoom(17)                   // Sets the zoom
//	    .bearing(90)                // Sets the orientation of the camera to east
//	    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
	    .build();                   // Creates a CameraPosition from the builder
		mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		float zoom = mGoogleMap.getCameraPosition().zoom;
		Log.d("CTF", "CTF cords:" + coords + "  zoom:  " + zoom );
	}

}
