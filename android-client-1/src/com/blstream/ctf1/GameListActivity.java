/**
 * @author Mateusz Wisniewski
 */

package com.blstream.ctf1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.blstream.ctf1.asynchronous.GameList;
import com.blstream.ctf1.tracker.IssueTracker;

public class GameListActivity extends ListActivity implements OnClickListener {

	private Button mBtnCreateGame, mBtnLogout, mBtnProfile;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_list);

		mBtnCreateGame = (Button) findViewById(R.id.btnCreateGame);
		mBtnCreateGame.setOnClickListener(this);
		mBtnLogout = (Button) findViewById(R.id.btnLogout);
		mBtnLogout.setOnClickListener(this);
		mBtnProfile = (Button) findViewById(R.id.btnProfile);
		mBtnProfile.setOnClickListener(this);

		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String mID = ((TextView) view.findViewById(R.id.id)).getText().toString();
				String gameName = ((TextView) view.findViewById(R.id.game_name)).getText().toString();
				Intent intent = new Intent(getApplicationContext(),	GameDetailsActivity.class);
				// sending data to new activity
				intent.putExtra(Constants.EXTRA_KEY_ID, mID);
				intent.putExtra("GAME_NAME", gameName);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		GameList gameList = new GameList(this);
		gameList.execute();
		super.onResume();
	}

	@Override
	public void onClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.btnCreateGame:
			IssueTracker.saveClick(this, mBtnCreateGame);
			intent = new Intent(this, CreateGameActivity.class);
			startActivity(intent);
			break;
		case R.id.btnLogout:
			IssueTracker.saveClick(this, mBtnLogout);
			finish();
			// do something with token after logout
			break;
		case R.id.btnProfile:
			IssueTracker.saveClick(this, mBtnProfile);
			break;
		}
	}
}
