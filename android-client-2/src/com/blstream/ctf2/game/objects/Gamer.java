package com.blstream.ctf2.game.objects;

import android.content.Context;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.domain.Localization;

/**
 * 
 * @author Karol Firmanty
 * 
 */
public class Gamer extends GameObject {

	private boolean mHasFlag;
	private String mName;

	public Gamer(Constants.TEAM team, Localization localization,Context context) {
		super(Constants.GAME_OBJECT_TYPE.GAMER, team, localization, context);
		mHasFlag = false;
	}
	
	public Gamer(String name, Constants.TEAM team, Localization localization,Context context) {
		super(Constants.GAME_OBJECT_TYPE.GAMER, team, localization, context);
		mHasFlag = false;
		mName = name;
	}
	
	public String getName(){
		return mName;
	}
	
	public void hasTakenFlag(){
		mHasFlag = true;
		changeGamerImage(getTeam(), true);
		
	}
	public void hasLostFlag(){
		mHasFlag = false;
		changeGamerImage(getTeam(), false);
	}

	public boolean isHasFlag() {
		return mHasFlag;
	}
	
	
	
}
