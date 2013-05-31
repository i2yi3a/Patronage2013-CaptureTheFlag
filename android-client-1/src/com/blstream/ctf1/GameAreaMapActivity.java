package com.blstream.ctf1;

import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Miłosz Skalski
 */

public class GameAreaMapActivity extends FragmentActivity implements OnMapClickListener {

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
    private boolean mRed;
    private Marker mMarkerBlue;
    private Marker mMarkerRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_area_map);
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
        mRed = true;

        LatLng coordsArea = new LatLng(mLatitude, mLongitude);
        LatLng coordsRed = new LatLng(mLatitudeRed, mLongitudeRed);
        LatLng coordsBlue = new LatLng(mLatitudeBlue, mLongitudeBlue);

        if (mGoogleMap != null) {
            mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
            mGoogleMap.setOnMapClickListener((OnMapClickListener) this);
            calculatedCamera();
            mMarkerArea = mGoogleMap.addMarker(new MarkerOptions().position(coordsArea).icon(BitmapDescriptorFactory.fromResource(R.drawable.center)));
            mCircle = mGoogleMap.addCircle(new CircleOptions().center(coordsArea).radius(mRadius).strokeColor(Color.GREEN));
            mMarkerRed = mGoogleMap.addMarker(new MarkerOptions().position(coordsRed).icon(BitmapDescriptorFactory.fromResource(R.drawable.base_red)));
            mMarkerBlue = mGoogleMap.addMarker(new MarkerOptions().position(coordsBlue).icon(BitmapDescriptorFactory.fromResource(R.drawable.base_blue)));
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", mLatitude);
        bundle.putDouble("longitude", mLongitude);
        bundle.putDouble("radius", mRadius);
        bundle.putDouble("latitudeRed", mLatitudeRed);
        bundle.putDouble("longitudeRed", mLongitudeRed);
        bundle.putDouble("latitudeBlue", mLatitudeBlue);
        bundle.putDouble("longitudeBlue", mLongitudeBlue);
        Intent myIntent = new Intent();
        myIntent.putExtras(bundle);
        setResult(RESULT_OK, myIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_area_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                mLatitude = mGoogleMap.getCameraPosition().target.latitude;
                mLongitude = mGoogleMap.getCameraPosition().target.longitude;

                LatLngBounds bounds = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;
                float[] results = new float[1];
                Location.distanceBetween(mGoogleMap.getCameraPosition().target.latitude, mGoogleMap.getCameraPosition().target.longitude,
                        mGoogleMap.getCameraPosition().target.latitude, bounds.southwest.longitude, results);
                mRadius = results[0];
                Log.d("CTF", "Save");
                Log.d("CTF", "latitude: " + mLatitude + " longitude: " + mLongitude + " radius: " + mRadius);
                LatLng coords = new LatLng(mLatitude, mLongitude);
                mMarkerArea.setPosition(coords);

			/*
             * mGoogleMap.addMarker(new MarkerOptions().position(new
			 * LatLng(resultLat1, resultLng1)).icon(
			 * BitmapDescriptorFactory.fromResource(R.drawable.base_blue)));
			 * mGoogleMap.addMarker(new MarkerOptions().position(new
			 * LatLng(resultLat2, resultLng2)).icon(
			 * BitmapDescriptorFactory.fromResource(R.drawable.base_red)));
			 */
                mCircle.setRadius(mRadius);
                mCircle.setCenter(coords);
                break;
            case R.id.action_red_base:
                mRed = true;
                break;
            case R.id.action_blue_base:
                mRed = false;
                break;
        }
        return super.onOptionsItemSelected(item);
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
        Log.d("CTF", "CTF CLICK MAP");
        if (mRed) {
            mMarkerRed.setPosition(latLng);
            mLatitudeRed = latLng.latitude;
            mLongitudeRed = latLng.longitude;
        } else {
            mMarkerBlue.setPosition(latLng);
            mLatitudeBlue = latLng.latitude;
            mLongitudeBlue = latLng.longitude;
        }
    }
}
