package com.blstream.ctf2.game.objects;

import android.content.Context;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.domain.Localization;
import com.google.android.gms.maps.model.LatLng;
/**
 * 
 * @author Karol Firmanty
 *
 */
public class Flag extends GameObject{
	private boolean mIsCarried;
	private Localization mBaseLocalization;
	
	public Flag(Constants.TEAM team, Localization localization, Localization BaseLocalization, Context context) {
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
