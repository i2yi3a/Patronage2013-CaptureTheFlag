package com.blstream.ctf2.activity.game.mod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.blstream.ctf2.R;
import com.blstream.ctf2.services.GameServices;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * @author Lukasz Dmitrowski
 */

public class BasicDataActivity extends Activity {

	public EditText mGameNameEditText;
	private EditText mGameMaxPointsEditText;
	private EditText mGameMaxPlayersEditText;
	private EditText mGameLocalizationNameEditText;
	private EditText mGameRadiusEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_data);

		mGameNameEditText = (EditText) findViewById(R.id.editTextGameName);
		mGameMaxPointsEditText = (EditText) findViewById(R.id.editTextGamePointsMax);
		mGameMaxPlayersEditText = (EditText) findViewById(R.id.editTextGamePlayersMax);
		mGameLocalizationNameEditText = (EditText) findViewById(R.id.editTextLocalizationName);
		mGameRadiusEditText = (EditText) findViewById(R.id.editTextGameRadius);

		fillFields();
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}

	private void fillFields() {
		Bundle details = getIntent().getExtras();
		mGameNameEditText.setText(details.getString(GameServices.NAME));
		mGameLocalizationNameEditText.setText(details.getString(GameServices.LOCALIZATION));
		mGameRadiusEditText.setText(details.getString(GameServices.RADIUS));
		mGameMaxPointsEditText.setText(details.getString(GameServices.POINTS_MAX));
		mGameMaxPlayersEditText.setText(details.getString(GameServices.PLAYERS_MAX));
	}

	public void onClickSaveButton(View v) {
		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "edit_save_basic_click", null);
		Intent intentMessage = new Intent();
		intentMessage.putExtra(GameServices.NAME, mGameNameEditText.getText().toString());
		intentMessage.putExtra(GameServices.POINTS_MAX, mGameMaxPointsEditText.getText().toString());
		intentMessage.putExtra(GameServices.PLAYERS_MAX, mGameMaxPlayersEditText.getText().toString());
		intentMessage.putExtra(GameServices.LOCALIZATION, mGameLocalizationNameEditText.getText().toString());
		intentMessage.putExtra(GameServices.RADIUS, mGameRadiusEditText.getText().toString());
		setResult(2, intentMessage);
		finish();
	}
}
