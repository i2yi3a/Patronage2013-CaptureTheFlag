package com.blstream.ctf1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.blstream.ctf1.asynchronous.DeleteGame;
import com.blstream.ctf1.asynchronous.GameDetails;
import com.blstream.ctf1.asynchronous.JoinGame;
import com.blstream.ctf1.asynchronous.LeaveGame;
import com.blstream.ctf1.asynchronous.RefreshPlayersList;
import com.blstream.ctf1.domain.GameStatusType;
import com.blstream.ctf1.domain.LoggedPlayer;
import com.blstream.ctf1.service.NetworkService;
import com.blstream.ctf1.service.StorageService;
import com.blstream.ctf1.tracker.IssueTracker;

/**
 * @author Rafal_Olichwer, Piotr Marczycki, Mateusz Wisniewski
 */

public class GameDetailsActivity extends ListActivity implements OnClickListener, OnTabChangeListener {

	public TextView mTextGameName;
	public TextView mTextGameDescription;
	public TextView mTextGameDuration;
	public TextView mTextLocName;
	public TextView mTextLocRadius;
	public TextView mTextGameStatus;
	public TextView mTextGameOwner;
	public TextView mTextGameDate;
	public TextView mTextGamePlayersMax;
	public TextView mTextGamePointsMax;
	public TextView mTextGameID;
	public Button mBtnJoin;
	public Button mBtnLeave;
	public Button mBtnEdit;
	public Button mBtnDelete;
	private String mId;
	private TabHost mTabHost;
	private TabSpec mTab1, mTab2, mTab3, mTab4;
	public double longitude;
	public double latitude;
	public long radius;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_details);

		mTextGameName = (TextView) findViewById(R.id.DetailsGameName);
		mTextGameDescription = (TextView) findViewById(R.id.DetailsDescription);
		mTextGameDuration = (TextView) findViewById(R.id.DetailsDuration);
		mTextLocName = (TextView) findViewById(R.id.LocName);
		mTextLocRadius = (TextView) findViewById(R.id.LocRadius);
		mTextGameStatus = (TextView) findViewById(R.id.GameStatus);
		mTextGameOwner = (TextView) findViewById(R.id.Owner);
		mTextGameDate = (TextView) findViewById(R.id.DetailsGameDate);
		mTextGamePlayersMax = (TextView) findViewById(R.id.GamePlayersMax);
		mTextGamePointsMax = (TextView) findViewById(R.id.GamePointsMax);
		mTextGameID = (TextView) findViewById(R.id.ID);
		mBtnJoin = (Button) findViewById(R.id.btnJoin);
		mBtnJoin.setOnClickListener(this);
		mBtnLeave = (Button) findViewById(R.id.btnLeave);
		mBtnLeave.setOnClickListener(this);
		mBtnEdit = (Button) findViewById(R.id.btnEdit);
		mBtnEdit.setOnClickListener(this);
		mBtnDelete = (Button) findViewById(R.id.btnDelete);
		mBtnDelete.setOnClickListener(this);
		Bundle extras = getIntent().getExtras();
		if (extras.getString(Constants.EXTRA_KEY_ID) != null) {
			mId = extras.getString(Constants.EXTRA_KEY_ID);
			
		}
        if (NetworkService.isDeviceOnline(this)) {
            GameDetails gameDetails = new GameDetails(this, mId);
            gameDetails.execute();
        } else {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
        
        
        mTabHost = (TabHost) findViewById(R.id.tabhostDetails);
		mTabHost.setup();
		mTab1 = createTabHost("TAB1", R.id.ScrollView,"Wszystkie");
		mTab4  = mTabHost.newTabSpec("TAB4").setIndicator("mapa");
		
		Intent intent = new Intent(this,GameDetailsMapActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("radius", radius);
		mTab4.setContent(intent);
		mTabHost.addTab(mTab4);
        mTabHost.setCurrentTab(0);
		mTabHost.setOnTabChangedListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btnJoin:
			IssueTracker.saveClick(this, mBtnJoin);
            if (NetworkService.isDeviceOnline(this)) {
                JoinGame joinGameAsync = new JoinGame(this, mId);
                joinGameAsync.execute();
                RefreshPlayersList refreshPlayersListJoin = new RefreshPlayersList(this,mId);
                refreshPlayersListJoin.execute();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
			break;
		case R.id.btnLeave:
			IssueTracker.saveClick(this, mBtnLeave);
            if (NetworkService.isDeviceOnline(this)) {
                LeaveGame leaveGameAsync = new LeaveGame(this, mId);
                leaveGameAsync.execute();
                RefreshPlayersList refreshPlayersListLeave = new RefreshPlayersList(this,mId);
                refreshPlayersListLeave.execute();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
			break;
		case R.id.btnEdit:
			IssueTracker.saveClick(this, mBtnEdit);
			intent = new Intent(this, CreateGameActivity.class);
			intent.putExtra(Constants.EXTRA_KEY_ID, mId);
			startActivity(intent);
			finish();
			break;
		case R.id.btnDelete:
			IssueTracker.saveClick(this, mBtnDelete);
            if (NetworkService.isDeviceOnline(this)) {
                DeleteGame deleteGameAsync = new DeleteGame(this, mId);
                deleteGameAsync.execute();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
			break;
		}
	}
	
	public void isStatusNew(String status) {
		if(status.equals(GameStatusType.NEW.toString()))
			mBtnEdit.setEnabled(true);
		else
			mBtnEdit.setEnabled(false);
	}
	
	public void isUserOwner(String owner) {
		StorageService storageService = new StorageService(this);
		storageService.open();
		LoggedPlayer loggedPlayer = storageService.getLoggedPlayer();
		if(loggedPlayer.getLogin().equals(owner)) {
			mBtnEdit.setVisibility(View.VISIBLE);
			mBtnDelete.setVisibility(View.VISIBLE);
		} else {
			mBtnEdit.setVisibility(View.GONE);
			mBtnDelete.setVisibility(View.GONE);
			
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
			
		}
		if(tabId == mTab2.getTag().toString())
			Log.v("TAB2", "TAB2 WCISNIETY!");
		if(tabId == mTab3.getTag().toString())
			Log.v("TAB3", "TAB3 WCISNIETY!");
		if(tabId == mTab4.getTag().toString()) {
			
		}
	}
}
