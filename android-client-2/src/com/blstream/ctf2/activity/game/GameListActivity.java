package com.blstream.ctf2.activity.game;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.services.GameServices;
import com.blstream.ctf2.storage.entity.Game;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * 
 * @author Marcin Sareło
 * 
 */
public class GameListActivity extends Activity implements OnClickListener {

	private ListView list;
	private ArrayAdapter<String> adapter;
	private List<Game> games = new ArrayList<Game>();
	private GameServices mGameServices;
	private Button mBtnVieProfile;
	private Button mBtnCreateNewGame;
	private Button mBtnLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_list);
		list = (ListView) findViewById(R.id.listView);
		list.setClickable(true);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent game_details_intent = new Intent("com.blstream.ctf2.GAMEDETAILSACTIVITY");
				game_details_intent.putExtra(Constants.ID, games.get(position).getId());
				startActivity(game_details_intent);
			}
		});

		mBtnVieProfile = (Button) (Button) findViewById(R.id.view_profile_button);
		mBtnVieProfile.setOnClickListener(this);

		mBtnCreateNewGame = (Button) (Button) findViewById(R.id.create_new_game_button);
		mBtnCreateNewGame.setOnClickListener(this);

		mBtnLogout = (Button) (Button) findViewById(R.id.logout_button);
		mBtnLogout.setOnClickListener(this);
	}

	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.create_new_game_button:
			intent = new Intent(this, CreateGameActivity.class);
			startActivity(intent);
			break;
		// case R.id.view_profile_button:
		// intent = new Intent(this, UserProfile/DetailsActivity.class);
		// intent.putExtra();
		// startActivity(intent);
		// break;
		case R.id.logout_button:
			Toast.makeText(this, R.string.logout_succesful, Toast.LENGTH_SHORT).show();
			finish();
			break;

		}
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
		ArrayList<String> mGameNameList = new ArrayList<String>();
		// TODO Marcin Sareło: own adapter and view for element
		for (Game game : games) {
			mGameNameList.add(game.getName());
		}
		adapter = new ArrayAdapter<String>(this, R.layout.game_list_element, mGameNameList);
		list.setAdapter(adapter);
	}

	@Override
	protected void onStart() {
		EasyTracker.getInstance().activityStart(this);
		super.onStart();
	}

	@Override
	protected void onStop() {
		EasyTracker.getInstance().activityStop(this);
		super.onStop();
	}

	@Override
	protected void onResume() {
		// mGameServices.getGames(this);
		GetGames getGames = new GetGames(this);
		getGames.execute("");
		super.onResume();
	}

}
