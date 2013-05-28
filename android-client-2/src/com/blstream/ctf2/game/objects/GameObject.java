package com.blstream.ctf2.game.objects;

import android.content.Context;
import android.graphics.Bitmap;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.game.objects.image.AndroidGameObjectImage;
import com.google.android.gms.maps.model.LatLng;
/**
 * 
 * @author Karol Firmanty
 *
 */
public abstract class GameObject {
	private LatLng mLocalization;
	private Constants.GAME_OBJECT_TYPE mType;
	private Constants.TEAM mTeam;
	private AndroidGameObjectImage mImage;

	public GameObject(Constants.GAME_OBJECT_TYPE type, Constants.TEAM team, LatLng localization,Context context) {
		mType = type;
		mTeam = team;
		mLocalization = localization;
		mImage = new AndroidGameObjectImage(type, team, context);
	}

	public Constants.TEAM getTeam() {
		return mTeam;
	}

	public void setLocalization(LatLng latLng) {
		mLocalization = latLng;
	}

	public LatLng getLocalization() {
		return mLocalization;
	}

	public Constants.GAME_OBJECT_TYPE getType() {
		return mType;
	}
	
	public Bitmap getImage(){
		return mImage.getImage();
	}
	
	public void changeGamerImage(Constants.TEAM team, boolean hasFlag){
		mImage.changeGamerImage(team, hasFlag);
	}
}
