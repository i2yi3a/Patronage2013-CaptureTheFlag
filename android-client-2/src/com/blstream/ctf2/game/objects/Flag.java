package com.blstream.ctf2.game.objects;

import android.content.Context;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.domain.Localization;
/**
 * 
 * @author Karol Firmanty
 *
 */
public class Flag extends GameObject{
	private boolean mIsCarried;
	private Localization mBaseLocalization;
	
	public Flag(Constants.TEAM team, Localization localization, Localization baseLocalization, Context context) {
		super(Constants.GAME_OBJECT_TYPE.FLAG, team, localization, context);
		mIsCarried = false;
		mBaseLocalization = baseLocalization;
	}
	
	public void hasBeenDropped(){
		setLocalization(mBaseLocalization);
		mIsCarried = false;
	}
	public void hasBeenTaken(){
		mIsCarried = true;
	}
	

}
