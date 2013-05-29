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
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.blstream.ctf1.asynchronous.GameList;
import com.blstream.ctf1.domain.GameBasicListFilter;
import com.blstream.ctf1.domain.GameStatusType;
import com.blstream.ctf1.tracker.IssueTracker;


public class GameListActivity extends ListActivity implements OnClickListener, OnTabChangeListener{

	private Button mBtnCreateGame;
	private GameBasicListFilter gameBasicListFilter;
	private TabHost mTabHost;
	private TabSpec mTab1, mTab2, mTab3, mTab4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_list);
		
		mBtnCreateGame = (Button) findViewById(R.id.btnCreateGame);
		mBtnCreateGame.setOnClickListener(this);
		gameBasicListFilter = new GameBasicListFilter();
		
		mTabHost = (TabHost) findViewById(R.id.tabHost);
		mTabHost.setup();
		mTab1 = createTabHost("TAB1", R.id.tab1,"Moje");
		mTab2 = createTabHost("TAB2", R.id.tab2,"Najbli¿ej");
		mTab3 = createTabHost("TAB3", R.id.tab3,"Zakoñczone");
		mTab4 = createTabHost("TAB4", R.id.tab4,"Wszystkie");
		mTabHost.setOnTabChangedListener(this);
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String mID = ((TextView) view.findViewById(R.id.id)).getText()
						.toString();
				Intent intent = new Intent(getApplicationContext(),
						GameDetailsActivity.class);
				intent.putExtra(Constants.EXTRA_KEY_ID, mID);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
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

	public TabSpec createTabHost(String tag, int id, String label) {
		TabSpec tabSpec;
		tabSpec = mTabHost.newTabSpec(tag).setContent(id).setIndicator(label);
		mTabHost.addTab(tabSpec);
		return tabSpec;
	}

	@Override
	public void onTabChanged(String tabId) {
		if(tabId == mTab1.getTag().toString()) {
			gameBasicListFilter = new GameBasicListFilter(null, null, true);
			GameList gameList = new GameList(this, gameBasicListFilter);
			gameList.execute();
		}
		if(tabId == mTab2.getTag().toString()) {
			//Filtruj po najblizszych lokalizacjach
		}
		if(tabId == mTab3.getTag().toString()) {
			gameBasicListFilter = new GameBasicListFilter(GameStatusType.COMPLETED.toString(), null, null);
			GameList gameList = new GameList(this, gameBasicListFilter);
			gameList.execute();
		}
		if(tabId == mTab4.getTag().toString()) {
			gameBasicListFilter = new GameBasicListFilter(null, null, null);
			GameList gameList = new GameList(this, gameBasicListFilter);
			gameList.execute();
		}
	}
}
