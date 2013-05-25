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
import android.widget.TabHost.TabSpec;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.blstream.ctf1.asynchronous.GameList;
import com.blstream.ctf1.domain.GameBasicListFilter;
import com.blstream.ctf1.tracker.IssueTracker;

public class GameListActivity extends ListActivity implements OnClickListener {

	private Button mBtnCreateGame, mBtnSearch, mContextMenu;
	private EditText mSearchGames;
	private GameBasicListFilter gameBasicListFilter;
	private TabHost mTabHost;
	
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
		gameBasicListFilter = new GameBasicListFilter();
		
	//	createTabHost();
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String mID = ((TextView) view.findViewById(R.id.id)).getText().toString();
				Intent intent = new Intent(getApplicationContext(), GameDetailsActivity.class);
				intent.putExtra(Constants.EXTRA_KEY_ID, mID);
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
		GameList gameList = new GameList(this, gameBasicListFilter);
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
			IssueTracker.saveClick(this, mBtnSearch);
			gameBasicListFilter = new GameBasicListFilter(mSearchGames.getText().toString(), null, null);
			GameList gameList = new GameList(this, gameBasicListFilter);
			gameList.execute();
			break;
		case R.id.contextMenu:
			registerForContextMenu(view);
			openContextMenu(view);
			break;
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
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
	
	public void createTabHost() {
		mTabHost = (TabHost) findViewById(R.id.tabHost);
		mTabHost.setup();
		TabSpec spec1 = mTabHost.newTabSpec("TAB1");
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("Wszystkie");
		
		TabSpec spec2 = mTabHost.newTabSpec("TAB2");
		spec2.setContent(R.id.tab2);
		spec2.setIndicator("Najblizej");
		
		TabSpec spec3 = mTabHost.newTabSpec("TAB3");
		spec3.setContent(R.id.tab3);
		spec3.setIndicator("Zakonczone");
		
		TabSpec spec4 = mTabHost.newTabSpec("TAB4");
		spec4.setContent(R.id.tab4);
		spec4.setIndicator("Moje");
		
		mTabHost.addTab(spec1);
		mTabHost.addTab(spec2);
		mTabHost.addTab(spec3);
		mTabHost.addTab(spec4);
	}
}
