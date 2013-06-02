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
public class Gamer extends GameObject {

	private boolean mHasFlag;

	public Gamer(Constants.TEAM team, Localization localization,Context context) {
		super(Constants.GAME_OBJECT_TYPE.GAMER, team, localization, context);
		mHasFlag = false;
	}
	
	public void hasTakenFlag(){
		mHasFlag = true;
		changeGamerImage(getTeam(), true);
		
	}
	public void hasLostFlag(){
		mHasFlag = false;
		changeGamerImage(getTeam(), false);
	}
	
}
