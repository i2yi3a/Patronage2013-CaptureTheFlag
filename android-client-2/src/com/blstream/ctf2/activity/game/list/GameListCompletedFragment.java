package com.blstream.ctf2.activity.game.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 
 * @author Marcin Sareło
 */
public class GameListCompletedFragment extends GameListBaseActivity {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		mGameServices.getCompletedGames(this);

		return view;
	}

}
