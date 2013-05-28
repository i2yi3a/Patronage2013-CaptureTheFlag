package com.blstream.ctf2.game.objects;

import android.content.Context;

import com.blstream.ctf2.Constants.GAME_OBJECT_TYPE;
import com.blstream.ctf2.Constants.TEAM;
import com.google.android.gms.maps.model.LatLng;
/**
 * 
 * @author Karol Firmanty
 *
 */
public class Base extends GameObject{
	private long mRadius;
	public Base(GAME_OBJECT_TYPE type, TEAM team, LatLng localization, Context context, long radius) {
		super(type, team, localization, context);
		mRadius = radius;
	}
	
	public long getRadius(){
		return mRadius;
	}

}
