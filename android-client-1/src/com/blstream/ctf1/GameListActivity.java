package com.blstream.ctf1;

/**
 * @author Mateusz Wisniewski
 */

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.blstream.ctf1.asynchronous.GameList;
import com.blstream.ctf1.asynchronous.GameListByLocalization;
import com.blstream.ctf1.domain.GameBasicListFilter;
import com.blstream.ctf1.domain.GameLocalizationListFilter;
import com.blstream.ctf1.domain.GameStatusType;
import com.blstream.ctf1.tracker.IssueTracker;

public class GameListActivity extends ListActivity implements OnClickListener,
		OnTabChangeListener {

	private Button mBtnCreateGame;
	private GameBasicListFilter gameBasicListFilter;
	private GameLocalizationListFilter gameLocalizationListFilter;
	private TabHost mTabHost;
	private TabSpec mTab1, mTab2, mTab3, mTab4;
	private TextView mTabTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_list);

		mBtnCreateGame = (Button) findViewById(R.id.btnCreateGame);
		mBtnCreateGame.setOnClickListener(this);
		gameBasicListFilter = new GameBasicListFilter();

		mTabHost = (TabHost) findViewById(R.id.tabHost);
		mTabHost.setup();
		mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
		
		mTab1 = setupTabHost(new TextView(this), R.string.tab_my_games);
		mTab2 = setupTabHost(new TextView(this), R.string.tab_closest_games);
		mTab3 = setupTabHost(new TextView(this), R.string.tab_finished_games);
		mTab4 = setupTabHost(new TextView(this), R.string.tab_all_games);
		
		mTabHost.setCurrentTabByTag(mTab4.getTag());
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

	private TabSpec setupTabHost(final View view, Integer tag) {
		View tabView = createTabView(mTabHost.getContext(), tag);
		TabSpec setContent = mTabHost.newTabSpec(tag.toString()).setIndicator(tabView)
				.setContent(new TabContentFactory() {
					public View createTabContent(String tag) {
						return view;
					}
				});
		mTabHost.addTab(setContent);
		return setContent;
	}

	private View createTabView(Context context, Integer text) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);
		mTabTextView = (TextView) view.findViewById(R.id.tabsText);
		mTabTextView.setText(text);
		return view;
	}

	@Override
	protected void onResume() {
		GameList gameList = new GameList(this, gameBasicListFilter);
		gameList.execute();
		super.onResume();
	}
	
	@Override
	public void onTabChanged(String tabId) {
		if (tabId == mTab1.getTag().toString()) {
			gameBasicListFilter = new GameBasicListFilter(null, null, true);
			GameList gameList = new GameList(this, gameBasicListFilter);
			gameList.execute();
		}
		if (tabId == mTab2.getTag().toString()) {
		/*	gameLocalizationListFilter = new GameLocalizationListFilter(null);
			GameListByLocalization gameList = new GameListByLocalization(this, gameLocalizationListFilter);
			gameList.execute(); */
		}
		if (tabId == mTab3.getTag().toString()) {
			gameBasicListFilter = new GameBasicListFilter(
					GameStatusType.COMPLETED.toString(), null, null);
			GameList gameList = new GameList(this, gameBasicListFilter);
			gameList.execute();
		}
		if (tabId == mTab4.getTag().toString()) {
			gameBasicListFilter = new GameBasicListFilter(null, null, null);
			GameList gameList = new GameList(this, gameBasicListFilter);
			gameList.execute();
		}
	}
}
