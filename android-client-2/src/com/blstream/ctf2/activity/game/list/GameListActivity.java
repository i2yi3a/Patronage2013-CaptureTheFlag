package com.blstream.ctf2.activity.game.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.blstream.ctf2.R;
import com.blstream.ctf2.activity.game.CreateGameActivity;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * 
 * @author Marcin Sareło [mod] Rafal Tatol
 */
public class GameListActivity extends FragmentActivity {

	final static String TAB_MY_GAMES = "My games";
	final static String TAB_CLOSEST_GAMES = "Nearest games";
	final static String TAB_FINISHED_GAMES = "Completed games";
	final static String TAB_ALL_GAMES = "All games";

	private GameListMyFragment mGameListMyFragment;
	private GameListNearestFragment mGameListNearestFragment;
	private GameListCompletedFragment mGameListCompletedFragment;
	private GameListAllFragment mGameListAllFragment;

	private TabHost mTabHost;

	private Button mBtnVieProfile;
	private Button mBtnCreateNewGame;
	private Button mBtnLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_game_list);

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setOnTabChangedListener(listener);
		mTabHost.setup();

		mTabHost.clearAllTabs();

		initializeTabs();

		mBtnCreateNewGame = (Button) findViewById(R.id.create_new_game_button);

	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	public void onClickButton(View v) {

		Intent intent = null;
		switch (v.getId()) {
		case R.id.create_new_game_button:
			intent = new Intent(this, CreateGameActivity.class);
			startActivity(intent);
			break;
		case R.id.logout_button:
			Toast.makeText(this, R.string.logout_succesful, Toast.LENGTH_SHORT).show();
			// TODO logout implementation (Marcin Sareło?)
			finish();
			break;
		}
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

	OnTabChangeListener listener = new OnTabChangeListener() {
		public void onTabChanged(String tabId) {
			if (tabId.equals(TAB_MY_GAMES)) {
				showMyGames();
			} else if (tabId.equals(TAB_CLOSEST_GAMES)) {
				showNearestGames();

			} else if (tabId.equals(TAB_FINISHED_GAMES)) {
				showCompletedGames();
			} else if (tabId.equals(TAB_ALL_GAMES)) {
				showAllGames();
			}

		}
	};

	public void showMyGames() {
		mGameListMyFragment = new GameListMyFragment();
		pushFragments(mGameListMyFragment);
	}

	public void showNearestGames() {
		mGameListNearestFragment = new GameListNearestFragment();
		pushFragments(mGameListNearestFragment);
	}

	public void showCompletedGames() {
		mGameListCompletedFragment = new GameListCompletedFragment();
		pushFragments(mGameListCompletedFragment);
	}

	public void showAllGames() {

		mGameListAllFragment = new GameListAllFragment();
		pushFragments(mGameListAllFragment);
	}

	public void pushFragments(Fragment fragment) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(android.R.id.tabcontent, fragment);
		ft.commit();
	}

	public void initializeTabs() {
		TabHost.TabSpec spec = null;
		mTabHost.setCurrentTab(0);

		spec = mTabHost.newTabSpec(TAB_MY_GAMES);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(android.R.id.tabcontent);
			}
		});
		spec.setIndicator(createTabView(R.string.game_list_my));
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(TAB_CLOSEST_GAMES);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(android.R.id.tabcontent);
			}
		});
		spec.setIndicator(createTabView(R.string.game_list_nearest));
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(TAB_FINISHED_GAMES);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(android.R.id.tabcontent);
			}
		});
		spec.setIndicator(createTabView(R.string.game_list_Completed));
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(TAB_ALL_GAMES);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(android.R.id.tabcontent);
			}
		});
		spec.setIndicator(createTabView(R.string.game_list_all));
		mTabHost.addTab(spec);
	}

	private View createTabView(final int id) {
		View view = LayoutInflater.from(this).inflate(R.layout.game_list_tabs, null);
		((TextView) view.findViewById(R.id.tab_text)).setText(id);
		return view;
	}

}
