package com.blstream.ctf1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.blstream.ctf1.asynchronous.DeleteGame;
import com.blstream.ctf1.asynchronous.GameDetails;
import com.blstream.ctf1.asynchronous.JoinGame;
import com.blstream.ctf1.asynchronous.LeaveGame;
import com.blstream.ctf1.tracker.IssueTracker;

/**
 * @author Rafal_Olichwer, Piotr Marczycki
 */

public class GameDetailsActivity extends Activity implements OnClickListener {

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
		Bundle bundle = getIntent().getExtras();
        if(bundle.getString(Constants.EXTRA_KEY_ID) != null) {
            mId = bundle.getString(Constants.EXTRA_KEY_ID);
        }
		GameDetails gameDetails = new GameDetails(this,mId);
		gameDetails.execute();
	}
	

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btnJoin:
			IssueTracker.saveClick(this, mBtnJoin);
			JoinGame joinGameAsync = new JoinGame(this, mId);
			joinGameAsync.execute();
			break;
		case R.id.btnLeave:
			IssueTracker.saveClick(this, mBtnLeave);
			LeaveGame leaveGameAsync = new LeaveGame(this, mId);
			leaveGameAsync.execute();
			break;
		case R.id.btnEdit:
			IssueTracker.saveClick(this, mBtnEdit);
			intent = new Intent(this, CreateGameActivity.class);
			intent.putExtra(Constants.EXTRA_KEY_ID, mId);
			startActivity(intent);
			break;
		case R.id.btnDelete:
			IssueTracker.saveClick(this, mBtnDelete);
			DeleteGame deleteGameAsync = new DeleteGame(this, mId);
			deleteGameAsync.execute();
			break;
		}
	}
}
