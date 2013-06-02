package com.blstream.ctf2.game.objects;

import android.content.Context;
import android.graphics.Bitmap;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.domain.Localization;
import com.blstream.ctf2.game.objects.image.AndroidGameObjectImage;
import com.google.android.gms.maps.model.LatLng;
/**
 * 
 * @author Karol Firmanty
 *
 */
public abstract class GameObject {
	private Localization mLocalization;
	private Constants.GAME_OBJECT_TYPE mType;
	private Constants.TEAM mTeam;
	private AndroidGameObjectImage mImage;

	public GameObject(Constants.GAME_OBJECT_TYPE type, Constants.TEAM team, Localization localization,Context context) {
		mType = type;
		mTeam = team;
		mLocalization = localization;
		mImage = new AndroidGameObjectImage(type, team, context);
	}

	public Constants.TEAM getTeam() {
		return mTeam;
	}

	public void setLocalization(Localization latLng) {
		mLocalization = latLng;
	}

	public Localization getLocalization() {
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
