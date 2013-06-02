package com.blstream.ctf2.activity.game;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.Constants.GAME_OBJECT_TYPE;
import com.blstream.ctf2.Constants.TEAM;
import com.blstream.ctf2.R;
import com.blstream.ctf2.domain.Localization;
import com.blstream.ctf2.domain.Team;
import com.blstream.ctf2.exception.CtfException;
import com.blstream.ctf2.game.objects.Base;
import com.blstream.ctf2.game.objects.Gamer;
import com.blstream.ctf2.services.GameServices;
import com.blstream.ctf2.services.GpsServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.util.Log;
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
	private List<Gamer> mGamers = new ArrayList<Gamer>();
	private List<Base> mBases = new ArrayList<Base>();

	private String mGameId;// need to take data from server
	private CountDownTimer mCountDownTimer;


	// mockups xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	int gameRadius = 2000;
	LatLng gameAreaPosition = new LatLng(53.440864, 14.539547);
	int gameRadius2 = 3123;
	LatLng gameAreaPosition2 = new LatLng(54.440864, 15.539547);

	LatLng myPosition1 = new LatLng(53.410864, 14.579547);
	LatLng myPosition2 = new LatLng(53.440864, 14.499547);
	LatLng myPosition3 = new LatLng(53.440864, 14.529547);

	private long startTime =0;
	private final long interval = 1 * 1000;

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
		
		String date = "2013-06-23 18:30:00";
		Date dateToStart = null;

		try {
			dateToStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
			Date now = new Date();

			startTime = dateToStart.getTime() - now.getTime();
		} catch (ParseException e) {

			e.printStackTrace();
		}
	}

	// mockups xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	private void mockData() {

		

		Localization palyerRed1LL = new Localization(53.442775, 14.537806);
		Localization palyerRed2LL = new Localization(53.443567, 14.547204);
		Localization palyerRed3LL = new Localization(53.432984, 14.547976);
		Localization palyerBlue1LL = new Localization(53.439094, 14.541343);
		Localization palyerBlue2LL = new Localization(53.44551, 14.530528);

		// baza red
		Localization baseRedLL = new Localization(53.44689, 14.525121);
		// baza blue
		Localization baseBlueLL = new Localization(53.432881, 14.563187);

		Team redTeam = new Team();
		redTeam.setBaseLocalization(baseRedLL);
		redTeam.setName("Rybacy");

		Team blueTeam = new Team();
		blueTeam.setBaseLocalization(baseBlueLL);
		blueTeam.setName("Górnicy");

		Base redBase = new Base(GAME_OBJECT_TYPE.BASE, TEAM.TEAM_RED, redTeam.getBaseLocalization(), this, 1l);
		Base blueBase = new Base(GAME_OBJECT_TYPE.BASE, TEAM.TEAM_BLUE, blueTeam.getBaseLocalization(), this, 1l);
		mBases.add(redBase);
		mBases.add(blueBase);

		Gamer b1 = new Gamer(TEAM.TEAM_BLUE, palyerBlue1LL, this);
		Gamer b2 = new Gamer(TEAM.TEAM_BLUE, palyerBlue2LL, this);

		Gamer r1 = new Gamer(TEAM.TEAM_RED, palyerRed1LL, this);

		Gamer r2 = new Gamer(TEAM.TEAM_RED, palyerRed2LL, this);
		Gamer r3 = new Gamer(TEAM.TEAM_RED, palyerRed3LL, this);

		mGamers.add(b1);
		mGamers.add(b2);
		mGamers.add(r1);
		mGamers.add(r2);
		mGamers.add(r3);
	}

	// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * TODO get player GPS localization (GpsService), post player
		 * localization to server, get game data from server CTFPAT-360,
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
		case R.id.ButtonShowObjects:
			mockData();
			drawPlayersMarkers();
			drawBasesMarkers();
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

	// demo
	private void drawPlayersMarkers() {

		for (Gamer gamer : mGamers) {
			if (gamer.getTeam() == TEAM.TEAM_RED) {

				mPositionMarker = mMap.addMarker(new MarkerOptions().position(gamer.getLocalization().getLatLang()).icon(
						BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

			} else {
				mPositionMarker = mMap.addMarker(new MarkerOptions().position(gamer.getLocalization().getLatLang()).icon(
						BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
			}
		}

	}

	// demo
	private void drawBasesMarkers() {

		for (Base base : mBases) {

			if (base.getTeam() == TEAM.TEAM_RED) {

				mPositionMarker = mMap.addMarker(new MarkerOptions().position(base.getLocalization().getLatLang()).title("Rybacy")
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.red)));
			} else {
				mPositionMarker = mMap.addMarker(new MarkerOptions().position(base.getLocalization().getLatLang()).title("Górnicy")
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.blue)));

			}

		}

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
		// TODO add marker icon
		// .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow_up)));
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

			mCountDownTimer = new InRangeCountDownTimer(startTime, interval);
			// text.setText(text.getText() + String.valueOf(startTime/1000));

			mCountDownTimer.start();
			mInfoTextView.setText("Jesteś w obszarze rozgrywki czekaj na rozpoczęcie rozgrywki - czas do rozpoczęcia " + String.valueOf(startTime / 1000));
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
	 * @author Rafal Tatol - return distance in meters, between two LatLng
	 *         points
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
	 * @author Rafal Tatol - return bearing in the range 0° - 360° between two
	 *         LatLng points
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

	class InRangeCountDownTimer extends CountDownTimer {
		public InRangeCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish() {
			mInfoTextView.setText("Jesteś w obszarze rozgrywki gra czeka na rozpoczęcie");

		}

		@Override
		public void onTick(long millisUntilFinished) {

			StringBuilder time = new StringBuilder();

			time.setLength(0);
			if (millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
				long count = millisUntilFinished / DateUtils.DAY_IN_MILLIS;
				if (count > 1)
					time.append(count).append(" dni ");
				else
					time.append(count).append(" dzień ");

				millisUntilFinished %= DateUtils.DAY_IN_MILLIS;
			}

			time.append(DateUtils.formatElapsedTime(Math.round(millisUntilFinished / 1000d)));

			mInfoTextView.setText("Jesteś w obszarze rozgrywki czekaj na rozpoczęcie rozgrywki - czas do rozpoczęcia " + time.toString());

			// mInfoTextView.setText("Jesteś w obszarze rozgrywki czekaj na rozpoczęcie rozgrywki - czas do rozpoczęcia"
			// + new
			// SimpleDateFormat("dd d HH:mm:ss").format(calendar.getTime()));

		}
	}
}
