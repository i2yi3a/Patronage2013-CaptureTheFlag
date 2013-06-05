package com.blstream.ctf1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.blstream.ctf1.asynchronous.DeleteGame;
import com.blstream.ctf1.asynchronous.GameDetails;
import com.blstream.ctf1.asynchronous.JoinGame;
import com.blstream.ctf1.asynchronous.LeaveGame;
import com.blstream.ctf1.asynchronous.RefreshPlayersList;
import com.blstream.ctf1.domain.GameStatusType;
import com.blstream.ctf1.domain.LoggedPlayer;
import com.blstream.ctf1.service.NetworkService;
import com.blstream.ctf1.service.StorageService;
import com.blstream.ctf1.tracker.IssueTracker;
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

/**
 * @author Rafal_Olichwer, Piotr Marczycki, Mateusz Wisniewski
 */

public class GameDetailsActivity extends FragmentActivity implements OnClickListener, OnTabChangeListener {

	public TextView mTextGameName;
	public TextView mTextGameDescription;
	public TextView mTextGameDuration;
	public TextView mTextLocName;
	public TextView mTextLocRadius;
	public TextView mTextGameStatus;
	public TextView mTextGameOwner;
	public TextView mTextGameDate;
	public TextView mTextGamePlayersMax;
	public TextView mTextGamePointsMax;
	public TextView mTextGameID;
	public Button mBtnJoin;
	public Button mBtnLeave;
	public Button mBtnEdit;
	public Button mBtnDelete;
	public Button mBtnPlay;
	private String mId;
	private TabHost mTabHost;
	private TabSpec mTab1,mTab2, mTab3;
	public double longitude;
	public double latitude;
	public double radius;
	public double latitudeRed;
	public double longitudeRed;
	public double latitudeBlue;
	public double longitudeBlue;
	private GoogleMap mGoogleMap;
	private SupportMapFragment mSupportMapFragment;
	private Marker marker;
	private Circle circle;
	public ListFragment mListFragment;
    private Marker mMarkerBlue;
    private Marker mMarkerRed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_details);

		mTextGameName = (TextView) findViewById(R.id.DetailsGameName);
		mTextGameDescription = (TextView) findViewById(R.id.DetailsDescription);
		mTextGameDuration = (TextView) findViewById(R.id.DetailsDuration);
		mTextLocName = (TextView) findViewById(R.id.LocName);
		mTextLocRadius = (TextView) findViewById(R.id.LocRadius);
		mTextGameStatus = (TextView) findViewById(R.id.GameStatus);
		mTextGameOwner = (TextView) findViewById(R.id.Owner);
		mTextGameDate = (TextView) findViewById(R.id.DetailsGameDate);
		mTextGamePlayersMax = (TextView) findViewById(R.id.GamePlayersMax);
		mTextGamePointsMax = (TextView) findViewById(R.id.GamePointsMax);
		mTextGameID = (TextView) findViewById(R.id.ID);
		mBtnJoin = (Button) findViewById(R.id.btnJoin);
		mBtnJoin.setOnClickListener(this);
		mBtnLeave = (Button) findViewById(R.id.btnLeave);
		mBtnLeave.setOnClickListener(this);
		mBtnEdit = (Button) findViewById(R.id.btnEdit);
		mBtnEdit.setOnClickListener(this);
		mBtnDelete = (Button) findViewById(R.id.btnDelete);
		mBtnDelete.setOnClickListener(this);
		mBtnPlay = (Button) findViewById(R.id.btnPlay);
		mBtnPlay.setOnClickListener(this);
		Bundle extras = getIntent().getExtras();
		if (extras.getString(Constants.EXTRA_KEY_ID) != null) {
			mId = extras.getString(Constants.EXTRA_KEY_ID);
			
		}
        if (NetworkService.isDeviceOnline(this)) {
            GameDetails gameDetails = new GameDetails(this, mId);
            gameDetails.execute();
        } else {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
        
        
        mTabHost = (TabHost) findViewById(R.id.tabhostDetails);
		mTabHost.setup();
		mTab1 = createTabHost("TAB1", R.id.ScrollView,"Szczegoly");
		mTab2 = createTabHost("TAB2", R.id.playersList,"Lista graczy");
		mTab3  = createTabHost("TAB3",R.id.mapDetails,"Mapa");
				
        mTabHost.setCurrentTab(0);
		mTabHost.setOnTabChangedListener(this);
		mListFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.playersList);
		
		
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btnJoin:
			IssueTracker.saveClick(this, mBtnJoin);
            if (NetworkService.isDeviceOnline(this)) {
                JoinGame joinGameAsync = new JoinGame(this, mId);
                joinGameAsync.execute();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
			break;
		case R.id.btnLeave:
			IssueTracker.saveClick(this, mBtnLeave);
            if (NetworkService.isDeviceOnline(this)) {
                LeaveGame leaveGameAsync = new LeaveGame(this, mId);
                leaveGameAsync.execute();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
			break;
		case R.id.btnEdit:
			IssueTracker.saveClick(this, mBtnEdit);
			intent = new Intent(this, CreateGameActivity.class);
			intent.putExtra(Constants.EXTRA_KEY_ID, mId);
			startActivity(intent);
			finish();
			break;
		case R.id.btnPlay:
				IssueTracker.saveClick(this, mBtnPlay);
				intent = new Intent(this, GameActivity.class);
				intent.putExtra(Constants.EXTRA_KEY_ID, mId);
		        intent.putExtra("latitude", latitude);
		        intent.putExtra("longitude", longitude);
		        intent.putExtra("radius", radius);
		        intent.putExtra("latitudeRed", latitudeRed);
		        intent.putExtra("longitudeRed", longitudeRed);
		        intent.putExtra("latitudeBlue", latitudeBlue);
		        intent.putExtra("longitudeBlue", longitudeBlue);
				startActivity(intent);
				finish();
				break;
		case R.id.btnDelete:
			IssueTracker.saveClick(this, mBtnDelete);
            if (NetworkService.isDeviceOnline(this)) {
                DeleteGame deleteGameAsync = new DeleteGame(this, mId);
                deleteGameAsync.execute();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
			break;
		}
	}
	
	public void isStatusNew(String status) {
		if(status.equals(GameStatusType.NEW.toString()))
			mBtnEdit.setEnabled(true);
		else
			mBtnEdit.setEnabled(false);
	}
	
	public void isUserOwner(String owner) {
		StorageService storageService = new StorageService(this);
		storageService.open();
		LoggedPlayer loggedPlayer = storageService.getLoggedPlayer();
		if(loggedPlayer.getLogin().equals(owner)) {
			mBtnEdit.setVisibility(View.VISIBLE);
			mBtnDelete.setVisibility(View.VISIBLE);
		} else {
			mBtnEdit.setVisibility(View.GONE);
			mBtnDelete.setVisibility(View.GONE);
			
		}
		storageService.close();
			
			
	}
	
	public TabSpec createTabHost(String tag, int id, String label) {
		TabSpec tabSpec;
		tabSpec = mTabHost.newTabSpec(tag).setContent(id).setIndicator(label);
		mTabHost.addTab(tabSpec);
		
		return tabSpec;
	}

	@Override
	public void onTabChanged(String tabId) {
		if(tabId == mTab1.getTag().toString()) {
			
		}
		if(tabId == mTab2.getTag().toString()) {
			RefreshPlayersList refresh = new RefreshPlayersList(this,mId);
			refresh.execute();
		}
		if(tabId == mTab3.getTag().toString())
			Log.v("TAB3", "TAB3 WCISNIETY!");
		//if(tabId == mTab4.getTag().toString()) {
			
		//}
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
	
	public void setupMap() {
		mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapDetails);
		mGoogleMap = mSupportMapFragment.getMap();
		
		LatLng coords = new LatLng(latitude, longitude);
        LatLng coordsRed = new LatLng(latitudeRed, longitudeRed);
        LatLng coordsBlue = new LatLng(latitudeBlue, longitudeBlue);

		if (mGoogleMap != null) {
			mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
			calculatedCamera();
			marker = mGoogleMap.addMarker(new MarkerOptions().position(coords).icon(BitmapDescriptorFactory.fromResource(R.drawable.center)));
			circle = mGoogleMap.addCircle(new CircleOptions().center(coords).radius(radius).strokeColor(Color.GREEN));
			mMarkerRed = mGoogleMap.addMarker(new MarkerOptions().position(coordsRed).icon(BitmapDescriptorFactory.fromResource(R.drawable.base_red)));
	        mMarkerBlue = mGoogleMap.addMarker(new MarkerOptions().position(coordsBlue).icon(BitmapDescriptorFactory.fromResource(R.drawable.base_blue)));
		}
	}
}
