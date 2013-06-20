package com.blstream.ctf2.activity.game;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.domain.GameLocalization;
import com.blstream.ctf2.domain.Team;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameDetailsMapFragment extends Fragment {

	private GoogleMap mMap;
	private TextView mGameNameTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.game_details_map_fragment, container, false);
		mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		mGameNameTextView = (TextView) view.findViewById(R.id.textViewGameName);
		
		GameLocalization gameArea = (GameLocalization) getArguments().getSerializable(Constants.KEY_GAME_AREA);
		Team teamRed = (Team) getArguments().getSerializable(Constants.KEY_TEAM_RED);
		Team teamBlue = (Team) getArguments().getSerializable(Constants.KEY_TEAM_BLUE);
		
		drawMarkers(gameArea,teamRed,teamBlue);
		
		String gameName = getArguments().getString(Constants.KEY_GAME_NAME);
		mGameNameTextView.setText(gameName);
		mGameNameTextView.setSelected(true);
		
		return view;
	}

	@Override
	public void onDestroyView() {
		try {
			SupportMapFragment fragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map));
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.remove(fragment);
			ft.commit();
		} catch (Exception e) {
		}
		super.onDestroyView();
	}

	public void drawMarkers(GameLocalization gameArea, Team teamRed, Team teamBlue ) {
		int radius = gameArea.getRadius();
		LatLng centerPoint = gameArea.getLatLang();
		LatLng pointBaseRed = teamRed.getBaseLocalization().getLatLang();
		LatLng pointBaseBlue = teamBlue.getBaseLocalization().getLatLang();

		mMap.addMarker(new MarkerOptions().position(pointBaseRed).title(teamRed.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.red)));
		mMap.addMarker(new MarkerOptions().position(pointBaseBlue).title(teamBlue.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue)));

		final int MAP_PADDING = 5;
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int height = metrics.heightPixels;
		int width = metrics.widthPixels;
		mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getBounds(radius, centerPoint), width, height, MAP_PADDING));

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
