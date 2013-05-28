package com.blstream.ctf2.game.objects;

import android.content.Context;

import com.blstream.ctf2.Constants;
import com.google.android.gms.maps.model.LatLng;
/**
 * 
 * @author Karol Firmanty
 *
 */
public class Flag extends GameObject{
	private boolean mIsCarried;
	private LatLng mBaseLocalization;
	
	public Flag(Constants.TEAM team, LatLng localization, LatLng BaseLocalization, Context context) {
		super(Constants.GAME_OBJECT_TYPE.FLAG, team, localization, context);
		mIsCarried = false;
		mBaseLocalization = BaseLocalization;
	}
	
	public void hasBeenDropped(){
		setLocalization(mBaseLocalization);
		mIsCarried = false;
	}
	public void hasBeenTaken(){
		mIsCarried = true;
	}
	

}
