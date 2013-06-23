package com.blstream.ctf2.game.objects.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.Constants.TEAM;
import com.blstream.ctf2.R;

/**
 * 
 * @author Karol Firmanty
 * 
 */
public class AndroidGameObjectImage implements GameObjectImage {
	private Bitmap mImage;
	private Context mCtx;

	public AndroidGameObjectImage(Constants.GAME_OBJECT_TYPE type, Constants.TEAM team, Context context) {
		mCtx = context;
		switch (type) {
		case GAMER:
			loadGamerImage(team);
			break;

		case FLAG:
			loadFlagImage(team);
			break;

		case BASE:
			loadBaseImage(team);
		}
	}

	// TODO change commented lines when assets become available
	@Override
	public void loadGamerImage(Constants.TEAM team) {
		switch (team) {
		case TEAM_RED:
			 mImage = BitmapFactory.decodeResource(mCtx.getResources(),R.drawable.red_single);
			break;
		case TEAM_BLUE:
			 mImage = BitmapFactory.decodeResource(mCtx.getResources(),R.drawable.blue_single);
			break;
		}

	}

	@Override
	public void loadFlagImage(Constants.TEAM team) {
		switch (team) {
		case TEAM_RED:
			mImage = BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.red);
			break;
		case TEAM_BLUE:
			mImage = BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.blue);
			break;
		}
	}

	@Override
	public void loadBaseImage(TEAM team) {
		switch (team) {
		case TEAM_RED:
			// mImage = BitmapFactory.decodeResource(mCtx.getResources(),R.drawable.redBase);
			break;
		case TEAM_BLUE:
			// mImage = BitmapFactory.decodeResource(mCtx.getResources(),R.drawable.blueBase);
			break;
		}
	}

	public Bitmap getImage() {
		return mImage;
	}

	@Override
	public void changeGamerImage(TEAM team, boolean hasFlag) {
		if(hasFlag){
			switch (team) {
			case TEAM_RED:
				 mImage = BitmapFactory.decodeResource(mCtx.getResources(),R.drawable.red_with_blue);
				break;
			case TEAM_BLUE:
				 mImage = BitmapFactory.decodeResource(mCtx.getResources(),R.drawable.blue_with_red);
				break;
			}
		}
		else{
			switch (team) {
			case TEAM_RED:
				 mImage = BitmapFactory.decodeResource(mCtx.getResources(),R.drawable.red_single);
				break;
			case TEAM_BLUE:
				 mImage = BitmapFactory.decodeResource(mCtx.getResources(),R.drawable.blue_single);
				break;
			}
		}
		
	}

}
