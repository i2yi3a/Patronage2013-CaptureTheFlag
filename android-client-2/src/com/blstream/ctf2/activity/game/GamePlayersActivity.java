package com.blstream.ctf2.activity.game;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.services.GameServices;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * @author Rafal Tatol
 */
public class GamePlayersActivity extends ListActivity {

	private String mGameId;
	private ListView mListView;
	private ArrayAdapter<String> mListAdapter;
	private List<String> mPlayersList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_players);
		mGameId = getIntent().getExtras().getString(Constants.ID);

		mPlayersList = new ArrayList<String>();
		mListView = getListView();
		mListView.setEmptyView(findViewById(R.id.list_empty));
	}

	@Override
	protected void onResume() {
		super.onResume();
		GameServices mGameServices = new GameServices(this);
		mGameServices.getGamePlayers(GamePlayersActivity.this, mGameId);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}
	
	public void setListView(ArrayList<String> playersList) {
		mPlayersList = playersList;
		mListAdapter = new ArrayAdapter<String>(this, R.layout.players_list_item, mPlayersList);
		mListView.setAdapter(mListAdapter);
	}

}
