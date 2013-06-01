package com.blstream.ctf2.activity.game;

import com.blstream.ctf2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

/**
 * 
 * @author Rafal Tatol
 * 
 */
public class GameActivity extends FragmentActivity {

	private GoogleMap mMap;
	private Circle mCircle;
	private Marker mPositionMarker;
	private ImageView mArrowImageView;
	private TextView mDistanceTextView;
	private TextView mInfoTextView;

	// mockups xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	int gameRadius = 2000;
	LatLng gameAreaPosition = new LatLng(53.440864, 14.539547);
	int gameRadius2 = 3123;
	LatLng gameAreaPosition2 = new LatLng(54.440864, 15.539547);

	LatLng myPosition1 = new LatLng(53.410864, 14.579547);
	LatLng myPosition2 = new LatLng(53.440864, 14.499547);
	LatLng myPosition3 = new LatLng(53.440864, 14.529547);
	// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.getUiSettings().setRotateGesturesEnabled(false);
		mArrowImageView = (ImageView) findViewById(R.id.imageViewArrow);
		mDistanceTextView = (TextView) findViewById(R.id.textViewDistance);
		mInfoTextView = (TextView) findViewById(R.id.textViewInfo1);
	}

	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * TODO
		 * get player GPS localization (GpsService),
		 * post player localization to server, 
		 * get game data from server CTFPAT-360,
		 */
		// demo
		updateMapBeforeGame(myPosition1, gameAreaPosition, gameRadius);
	}

	public void onClickButton(View v) {
		switch (v.getId()) {
		// demo
		// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
		case R.id.buttonTeleport1:
			updateMapBeforeGame(myPosition2, gameAreaPosition, gameRadius);
			break;
		case R.id.buttonTeleport2:
			updateMapBeforeGame(myPosition3, gameAreaPosition, gameRadius);
			break;
		case R.id.buttonTeleport3:
			updateMapBeforeGame(myPosition1, gameAreaPosition2, gameRadius2);
			break;
		// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
		}
	}

	public void updateMapBeforeGame(LatLng player, LatLng game, int radius) {
		drawGameArea(game, radius);
		drawPlayerMarker(player);
		// TODO time to start
		updateNavigationInfoBeforeGame(player, game, radius); 
	}

	public void drawGameArea(LatLng centerPoint, int radius) {
		if (mCircle != null) {
			mCircle.remove();
		}
		mCircle = mMap.addCircle(new CircleOptions().center(centerPoint).radius(radius).strokeColor(Color.RED));
	}

	public void drawPlayerMarker(LatLng player) {
		if (mPositionMarker != null) {
			mPositionMarker.remove();
		}
		// TODO add marker icon .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow_up)));
		mPositionMarker = mMap.addMarker(new MarkerOptions().position(player));
		// TODO camera zoom?
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(player, 15));
	}

	public void updateNavigationInfoBeforeGame(LatLng player, LatLng game, int gameRadius) {
		int distanceToGameArea = getDistance(player, game) - gameRadius;
		if (distanceToGameArea > 0) {
			if (mArrowImageView.getVisibility() == View.INVISIBLE) {
				mArrowImageView.setVisibility(View.VISIBLE);
			}
			if (mDistanceTextView.getVisibility() == View.INVISIBLE) {
				mDistanceTextView.setVisibility(View.VISIBLE);
			}
			double bearing = getBearing(player, game);
			rotateNavigateArrow(bearing);
			mDistanceTextView.setText(String.valueOf(distanceToGameArea) + "m");
			// TODO add res strings
			mInfoTextView.setText("Jesteś poza obszarem rozgrywki - obszar osiągniesz kierując się zgodnie ze wskazówkami na ekranie");
		} else {
			mArrowImageView.setVisibility(View.INVISIBLE);
			mDistanceTextView.setVisibility(View.INVISIBLE);
			// TODO add res strings
			mInfoTextView.setText("Jesteś w obszarze rozgrywki czekaj na rozpoczęcie rozgrywki - czas do rozpoczęcia XX:XX:XX");
		}
	}

	public void rotateNavigateArrow(double bearing) {
		final int px = 32;
		final int py = 32;
		Matrix matrix = new Matrix();
		mArrowImageView.setScaleType(ScaleType.MATRIX);
		matrix.postRotate((float) bearing, px, py);
		mArrowImageView.setImageMatrix(matrix);
	}

	/**
	 * @author Rafal Tatol 
	 *  - return distance in meters, between two LatLng points
	 */
	public int getDistance(LatLng start, LatLng end) {
		final double earthRadius = 3958.75;
		final int meterConversion = 1609;
		int result = 0;
		double latDiff = Math.toRadians(end.latitude - start.latitude);
		double lngDiff = Math.toRadians(end.longitude - start.longitude);
		double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) + Math.cos(Math.toRadians(start.latitude)) * Math.cos(Math.toRadians(end.latitude))
				* Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = earthRadius * c;

		result = (int) Math.ceil(distance * meterConversion);
		return result;
	}

	/**
	 * @author Rafal Tatol 
	 * - return bearing in the range 0° - 360° between two LatLng points
	 */
	public double getBearing(LatLng player, LatLng destination) {
		double bearing = 0;
		double radiansLatX = Math.toRadians(player.latitude);
		double radiansLatY = Math.toRadians(destination.latitude);
		double radiansLngDelta = Math.toRadians(destination.longitude - player.longitude);
		double a = Math.sin(radiansLngDelta) * Math.cos(radiansLatY);
		double b = Math.cos(radiansLatX) * Math.sin(radiansLatY) - Math.sin(radiansLatX) * Math.cos(radiansLatY) * Math.cos(radiansLngDelta);
		bearing = Math.atan2(a, b) * (180 / Math.PI);
		// Normalize the result (in the range 0° - 360°)
		bearing = (bearing + 360) % 360; 

		return bearing;
	}
}
