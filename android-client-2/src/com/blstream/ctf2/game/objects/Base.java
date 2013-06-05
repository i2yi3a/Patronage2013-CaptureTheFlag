package com.blstream.ctf2.game.objects;

import android.content.Context;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.Constants.TEAM;
import com.blstream.ctf2.domain.Localization;

/**
 * 
 * @author Karol Firmanty
 *
 */
public class Base extends GameObject{
	private long mRadius;
	public Base(TEAM team, Localization localization, Context context, long radius) {
		super(Constants.GAME_OBJECT_TYPE.BASE, team, localization, context);
		mRadius = radius;
	}
	
	public long getRadius(){
		return mRadius;
	}

}
