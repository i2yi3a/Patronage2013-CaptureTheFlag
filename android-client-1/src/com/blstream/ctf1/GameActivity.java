package com.blstream.ctf1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.blstream.ctf1.gamemodels.BaseModel;
import com.blstream.ctf1.gamemodels.GameModel;
import com.blstream.ctf1.gamemodels.PlayerModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * @author Rafal Olichwer, Miłosz Skalski
 */

public class GameActivity extends FragmentActivity implements OnMapClickListener {

    private static final byte RED_TEAM = 0;
    private static final byte BLUE_TEAM = 1;
	private GoogleMap mGoogleMap;
    private SupportMapFragment mSupportMapFragment;
    private double mLatitude;
    private double mLongitude;
    private double mRadius;
    private double mLatitudeRed;
    private double mLongitudeRed;
    private double mLatitudeBlue;
    private double mLongitudeBlue;
    private Marker mMarkerArea;
    private Circle mCircle;
    private BaseModel mBlueBase;
    private BaseModel mRedBase;
    private BaseModel mBlueBaseFlag;
    private BaseModel mRedBaseFlag;
    private GameModel mGameModel;
    private String mId;
    private int mRedTeamScore;
    private int mBlueTeamScore;
    private Date mTimestamp;
    private PlayerModel ja;
    private Polyline mLine;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mGoogleMap = mSupportMapFragment.getMap();
        Intent intent = getIntent();
        mLatitude = intent.getDoubleExtra("latitude", 0.0);
        mLongitude = intent.getDoubleExtra("longitude", 0.0);
        mRadius = intent.getDoubleExtra("radius", 0.0);
        mLatitudeRed = intent.getDoubleExtra("latitudeRed", 0.0);
        mLongitudeRed = intent.getDoubleExtra("longitudeRed", 0.0);
        mLatitudeBlue = intent.getDoubleExtra("latitudeBlue", 0.0);
        mLongitudeBlue = intent.getDoubleExtra("longitudeBlue", 0.0);
        
        if (intent.getStringExtra(Constants.EXTRA_KEY_ID) != null) {
			mId = intent.getStringExtra(Constants.EXTRA_KEY_ID);
			
		}
        LatLng coordsArea = new LatLng(mLatitude, mLongitude);
        LatLng coordsRed = new LatLng(mLatitudeRed, mLongitudeRed);
        LatLng coordsBlue = new LatLng(mLatitudeBlue, mLongitudeBlue);

        if (mGoogleMap != null) {
            mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
            mGoogleMap.setOnMapClickListener((OnMapClickListener) this);
            calculatedCamera();
            //mMarkerArea = mGoogleMap.addMarker(new MarkerOptions().position(coordsArea).icon(BitmapDescriptorFactory.fromResource(R.drawable.center)));
            mCircle = mGoogleMap.addCircle(new CircleOptions().center(coordsArea).radius(mRadius).strokeColor(Color.GREEN));
            mRedBase = new BaseModel(coordsRed,R.drawable.base_red,mGoogleMap);
            mBlueBase = new BaseModel(coordsBlue,R.drawable.base_blue,mGoogleMap);
            mBlueBase.setVisible(false);
            mRedBase.setVisible(false);
            mRedBaseFlag = new BaseModel(coordsRed,R.drawable.base_red_flag,mGoogleMap);
            mBlueBaseFlag = new BaseModel(coordsBlue,R.drawable.base_blue_flag,mGoogleMap);
            
        }
        ja = new PlayerModel(coordsArea,R.drawable.jenkins,mGoogleMap);
        ja.setHasFlag(false);
        ja.setTeam(RED_TEAM);
        List<PlayerModel> gracze = new ArrayList<PlayerModel>();
        gracze.add(ja);
        mGameModel = new GameModel(coordsArea,isGameStarted(),mId,mTimestamp,gracze,mRedTeamScore,mBlueTeamScore);
       
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    private void calculatedCamera() {
        long Rad = 6371000; // distance of earth's radius in meters
        double d = mRadius / (double) Rad;
        double brng = Math.toRadians(90);
        double lat1 = Math.toRadians(mLatitude);
        double lng1 = Math.toRadians(mLongitude);
        double resultLat1 = Math.asin(Math.sin(lat1) * Math.cos(d) + Math.cos(lat1) * Math.sin(d) * Math.cos(brng));
        double resultLng1 = lng1 + Math.atan2(Math.sin(brng) * Math.sin(d) * Math.cos(lat1), Math.cos(d) - Math.sin(lat1) * Math.sin(resultLat1));
        resultLat1 = Math.toDegrees(resultLat1);
        resultLng1 = Math.toDegrees(resultLng1);
        Log.i("CTF", "resultLat1: " + resultLat1 + " resultLng1: " + resultLng1);

        double lat2 = Math.toRadians(mLatitude);
        double lng2 = Math.toRadians(mLongitude);
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

    @Override
    public void onMapClick(LatLng latLng) {
    	mGameModel.getPlayers().get(0).move(latLng);
    	mRedBase.move(new LatLng(mLatitudeRed,mLongitudeRed)); 
    	mBlueBase.move(new LatLng(mLatitudeBlue,mLongitudeBlue));
    	boolean flag = mGameModel.getPlayers().get(0).isHasFlag();
    	mGameModel.getPlayers().get(0).setLatLng(latLng);
    	mGameModel.getPlayers().get(0).setHasFlag(!flag);
    	updateModel(mGameModel);
       
    }
    
    public boolean isGameStarted() {
    	return false;       
    }
  
    public boolean isInGameArea(LatLng latLng){
    	float[] result = new float[1];
    	Location.distanceBetween(latLng.latitude, latLng.longitude, mLatitude,mLongitude, result);
    	if(result[0] > mRadius)
    		return false;
    	else
    		return true;
    }
    
    public void updateModel(GameModel gameModel) {
    	mGameModel = gameModel;
    	setFlagInBase(!isFlagCarried(mGameModel,RED_TEAM),RED_TEAM);
    	setFlagInBase(!isFlagCarried(mGameModel,BLUE_TEAM),BLUE_TEAM);
    	
    	if(!isInGameArea(mGameModel.getPlayers().get(0).getLatLng())) {
    		if(mLine != null)
    			mLine.remove();
    		mLine = mGoogleMap.addPolyline(new PolylineOptions()
    	     .add(mGameModel.getPlayers().get(0).getLatLng(), new LatLng(mLatitude,mLongitude))
    	     .width(5)
    	     .color(Color.RED));
    		mLine.setVisible(true);
    	} else {
    		if(mLine != null)
    			mLine.setVisible(false);
    	}
    	
    }
    
    public boolean isFlagCarried(GameModel gameModel,byte team) {
    	List<PlayerModel> players= gameModel.getPlayers();
    	for(int i = 0; i < players.size(); i++)
    	{
    		if(players.get(i).getTeam() == team) {
    			if(players.get(i).isHasFlag())
    				return true;
    		}
    	}
    	return false;
    }
    
    public void setFlagInBase(boolean flagInBase,byte team) {
     		if(team == RED_TEAM) {
    			mRedBase.setVisible(!flagInBase);
    			mRedBaseFlag.setVisible(flagInBase);
    		} else {
    			mBlueBase.setVisible(!flagInBase);
    			mBlueBaseFlag.setVisible(flagInBase);
    		}	
    }
    
}
