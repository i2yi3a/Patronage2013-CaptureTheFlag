package com.blstream.ctf2.activity.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.services.GameServices;

/**
 * @author Rafal Tatol
 */
public class GameDetailsActivity extends Activity {

	private String mGameId;
	public TextView mGameNameTextView;
	public TextView mGameIdTextView;
	public TextView mDescriptionTextView;
	public TextView mDurationIdTextView;
	public TextView mLocalizationTextView;
	public TextView mLatitudeTextView;
	public TextView mLongitudeTextView;
	public TextView mRadiusTextView;
	public TextView mStatusTextView;
	public TextView mOwnerTextView;
	public TextView mTimeStartTextView;
	public TextView mPointsMaxTextView;
	public TextView mPlayersMaxTextView;
	public Button mJoinButton;
	public Button mEditButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_details);
		mGameId = getIntent().getExtras().getString(Constants.ID);
		mGameNameTextView = (TextView) findViewById(R.id.textViewGameName);
		mGameIdTextView = (TextView) findViewById(R.id.textViewGameId);
		mDescriptionTextView = (TextView) findViewById(R.id.textViewDescription);
		mDurationIdTextView = (TextView) findViewById(R.id.textViewDuration);
		mLocalizationTextView = (TextView) findViewById(R.id.textViewLocalization);
		mLatitudeTextView = (TextView) findViewById(R.id.textViewLat);
		mLongitudeTextView = (TextView) findViewById(R.id.textViewLng);
		mRadiusTextView = (TextView) findViewById(R.id.textViewRadius);
		mStatusTextView = (TextView) findViewById(R.id.textViewStatus);
		mOwnerTextView = (TextView) findViewById(R.id.textViewOwner);
		mTimeStartTextView = (TextView) findViewById(R.id.textViewTimeStart);
		mPointsMaxTextView = (TextView) findViewById(R.id.textViewPointsMax);
		mPlayersMaxTextView = (TextView) findViewById(R.id.textViewPlayersMax);
		mJoinButton = (Button) findViewById(R.id.joinButton);
		mEditButton = (Button) findViewById(R.id.editButton);
	}

	@Override
	protected void onResume() {
		super.onResume();
		GameServices mGameServices = new GameServices(this);
		mGameServices.getGameDetails(GameDetailsActivity.this, mGameId);
	}

	public void onClickButton(View v) {
		switch (v.getId()) {
		case R.id.joinButton:
			break;
		case R.id.editButton:
			break;
		}
	}
}
