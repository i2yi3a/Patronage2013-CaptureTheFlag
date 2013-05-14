package com.blstream.ctf1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.blstream.ctf1.asynchronous.GameDetails;

/**
 * @author Rafal_Olichwer
 */

public class GameDetailsActivity extends Activity implements OnClickListener {

	public TextView mTextGameName;
	public TextView mTextGameDescription;
	public TextView mTextGameDuration;
	public TextView mTextLocName;
	public TextView mTextLocRadius;
	public TextView mTextGameStatus;
	public TextView mTextGameOwner;
	public TextView mTextGamePlayersMax;
	public TextView mTextGamePointsMax;
	public TextView mTextGameID;
	
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
		mTextGamePlayersMax = (TextView) findViewById(R.id.GamePlayersMax);
		mTextGamePointsMax = (TextView) findViewById(R.id.GamePointsMax);
		mTextGameID = (TextView) findViewById(R.id.ID);
		GameDetails gameDetails = new GameDetails(this,"51927477e4b06da65947757b");
		gameDetails.execute();
	}
	

	@Override
	public void onClick(View v) {
		//switch (v.getId()) {
		
		//}
	}
	
	

}
