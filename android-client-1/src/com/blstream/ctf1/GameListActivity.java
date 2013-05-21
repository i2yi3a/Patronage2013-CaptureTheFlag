package com.blstream.ctf1;

/**
 * @author Mateusz Wisniewski
 */

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.blstream.ctf1.asynchronous.GameList;
import com.blstream.ctf1.tracker.IssueTracker;

public class GameListActivity extends ListActivity implements OnClickListener {

	private Button mBtnCreateGame, mBtnSearch, mContextMenu;
	private EditText mSearchGames;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_list);

		mBtnCreateGame = (Button) findViewById(R.id.btnCreateGame);
		mBtnCreateGame.setOnClickListener(this);
		mBtnSearch = (Button) findViewById(R.id.btnSearch);
		mBtnSearch.setOnClickListener(this);
		mContextMenu = (Button) findViewById(R.id.contextMenu);
		mContextMenu.setOnClickListener(this);
		mSearchGames = (EditText) findViewById(R.id.editTextSearchGame);
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String mID = ((TextView) view.findViewById(R.id.id)).getText().toString();
				String gameName = ((TextView) view.findViewById(R.id.game_name)).getText().toString();
				Intent intent = new Intent(getApplicationContext(),	GameDetailsActivity.class);
				intent.putExtra(Constants.EXTRA_KEY_ID, mID);
				intent.putExtra("GAME_NAME", gameName);
				startActivity(intent);
			}
		});
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_menu, menu);
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
		case R.id.btnSearch:
			break;
		case R.id.contextMenu:
			registerForContextMenu(view);
			openContextMenu(view);
			break;
		}
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.Profile:
			return true;
		case R.id.Settings:
			return true;
		case R.id.Logout:
			finish();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}	
}
