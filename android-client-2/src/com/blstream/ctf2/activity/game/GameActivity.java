package com.blstream.ctf2.activity.game;

import com.blstream.ctf2.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class GameActivity extends FragmentActivity {
	
	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.getUiSettings().setRotateGesturesEnabled(false);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void onClickButton(View v) {
		/*
		 * switch (v.getId()) { case R.id.joinButton: break;
		 */
	}
}
