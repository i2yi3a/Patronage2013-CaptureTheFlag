package com.blstream.ctf2.activity.game;

import android.content.Intent;
import android.graphics.Paint.Join;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.domain.GameDetails;
import com.blstream.ctf2.services.GameServices;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Rafal Tatol
 */
public class GameDetailsActivity extends FragmentActivity {

	private String mGameId;
	public TextView mGameNameTextView;
	public TextView mGameIdTextView;
	public TextView mDescriptionTextView;
	public TextView mDurationIdTextView;
	public TextView mLocalizationTextView;
	public TextView mLatitudeTextView;
	public TextView mLongitudeTextView;
	public TextView mRadiusTextView;
	public TextView mStatusTextView;
	public TextView mOwnerTextView;
	public TextView mTimeStartTextView;
	public TextView mPointsMaxTextView;
	public TextView mPlayersMaxTextView;
	public TextView mTeamRedName;
	public TextView mTeamRedBaseLocalization;
	public TextView mTeamBlueName;
	public TextView mTeamBlueBaseLocalization;
	public Button mJoinButton;
	public Button mEditButton;


	private GoogleMap mMap;	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_details);
		mGameId = getIntent().getExtras().getString(Constants.ID);
		mGameNameTextView = (TextView) findViewById(R.id.textViewGameName);
		mGameIdTextView = (TextView) findViewById(R.id.textViewGameId);
		mDescriptionTextView = (TextView) findViewById(R.id.textViewDescription);
		mDurationIdTextView = (TextView) findViewById(R.id.textViewDuration);
		mLocalizationTextView = (TextView) findViewById(R.id.textViewLocalization);
		mLatitudeTextView = (TextView) findViewById(R.id.textViewLat);
		mLongitudeTextView = (TextView) findViewById(R.id.textViewLng);
		mRadiusTextView = (TextView) findViewById(R.id.textViewRadius);
		mStatusTextView = (TextView) findViewById(R.id.textViewStatus);
		mOwnerTextView = (TextView) findViewById(R.id.textViewOwner);
		mTimeStartTextView = (TextView) findViewById(R.id.textViewTimeStart);
		mPointsMaxTextView = (TextView) findViewById(R.id.textViewPointsMax);
		mPlayersMaxTextView = (TextView) findViewById(R.id.textViewPlayersMax);
		mTeamRedName = (TextView) findViewById(R.id.textViewTeamRed);
		mTeamRedBaseLocalization = (TextView) findViewById(R.id.textViewTeamRedLocalization);
		mTeamBlueName = (TextView) findViewById(R.id.textViewTeamBlue);
		mTeamBlueBaseLocalization = (TextView) findViewById(R.id.textViewTeamBlueLocalization);

		mJoinButton = (Button) findViewById(R.id.joinButton);
		mEditButton = (Button) findViewById(R.id.editButton);

		mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGameIdTextView.setText(mGameId);
		GameServices mGameServices = new GameServices(this);
		mGameServices.getGameDetails(GameDetailsActivity.this, mGameId);
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}

	public void onClickButton(View v) {
		switch (v.getId()) {
		case R.id.joinButton:
			GameServices mGameServices = new GameServices(this);
			mGameServices.joinTheGame(GameDetailsActivity.this, mGameId);
			break;
		case R.id.editButton:
			break;
		case R.id.playersButton:
			Intent intent = new Intent("com.blstream.ctf2.GAMEPLAYERSACTIVITY");
			intent.putExtra(Constants.ID, mGameId);
			startActivity(intent);
			break;
		}
	}

	public void setMapElements(GameDetails d) {
		int radius = d.getLocalization().getRadius();
		LatLng centerPoint = new LatLng(d.getLocalization().getLat(), d.getLocalization().getLng());
		LatLng pointBaseRed = new LatLng(d.getTeamRed().getBaseLocalization().getLat(), d.getTeamRed().getBaseLocalization().getLng());
		LatLng pointBaseBlue = new LatLng(d.getTeamBlue().getBaseLocalization().getLat(), d.getTeamBlue().getBaseLocalization().getLng());

		final int MAP_HEIGHT = 300;
		final int MAP_PADDING = 50;

		mMap.addMarker(new MarkerOptions().position(pointBaseRed).title(d.getTeamRed().getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.red)));

		mMap.addMarker(new MarkerOptions().position(pointBaseBlue).title(d.getTeamBlue().getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue)));

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.heightPixels;

		mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getBounds(radius, centerPoint), width, MAP_HEIGHT, MAP_PADDING));

		CircleOptions circleOptions = new CircleOptions().center(centerPoint).radius(radius).strokeColor(Color.RED);

		mMap.addCircle(circleOptions);
	}

	public LatLngBounds getBounds(double radius, LatLng centerPoint) {
		final int DEGREE_180 = 180;
		final double r = radius / 1000;
		final double rEarth = 6378.137;
		LatLngBounds bounds = null;

		double leftLng = centerPoint.longitude - (r / rEarth) * (DEGREE_180 / Math.PI) / Math.cos(centerPoint.latitude * DEGREE_180 / Math.PI);
		LatLng leftPoint = new LatLng(centerPoint.latitude, leftLng);
		double rightLng = centerPoint.longitude + (r / rEarth) * (DEGREE_180 / Math.PI) / Math.cos(centerPoint.latitude * DEGREE_180 / Math.PI);
		LatLng rightPoint = new LatLng(centerPoint.latitude, rightLng);

		bounds = new LatLngBounds.Builder().include(rightPoint).include(leftPoint).build();
		return bounds;
	}
}
