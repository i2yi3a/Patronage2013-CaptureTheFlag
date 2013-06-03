package com.blstream.ctf2.activity.game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.services.DateTimeServices;
import com.blstream.ctf2.services.DialogServices;
import com.blstream.ctf2.services.GameServices;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * @author Lukasz Dmitrowski
 */

public class EditGameActivity extends Activity {

	private String mGameId;
	public EditText mGameNameEditText;
	private EditText mGameDescriptionEditText;
	private EditText mGameDurationEditText;
	private EditText mGameMaxPointsEditText;
	private EditText mGameMaxPlayersEditText;
	private EditText mGameLocalizationNameEditText;
	private EditText mGameRadiusEditText;
	private TextView mLocalizationTV;
	private TimePicker mGameTimeStartTimePicker;
	private DatePicker mGameDateStartDatePicker;
	private DialogServices mAlert;
	private String mGameLat, mGameLong;
	private String mAlertInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editgame);

		mGameNameEditText = (EditText) findViewById(R.id.editTextGameName);
		mGameDescriptionEditText = (EditText) findViewById(R.id.editTextGameDescription);
		mGameDurationEditText = (EditText) findViewById(R.id.editTextGameDuration);
		mGameMaxPointsEditText = (EditText) findViewById(R.id.editTextGamePointsMax);
		mGameMaxPlayersEditText = (EditText) findViewById(R.id.editTextGamePlayersMax);
		mGameLocalizationNameEditText = (EditText) findViewById(R.id.editTextLocalizationName);
		mGameRadiusEditText = (EditText) findViewById(R.id.editTextGameRadius);
		mGameDateStartDatePicker = (DatePicker) findViewById(R.id.datePickerTimeStart);
		mGameTimeStartTimePicker = (TimePicker) findViewById(R.id.timePickerTimeStart);
		mLocalizationTV = (TextView) findViewById(R.id.localizationTextView);

		mAlert = new DialogServices(this);
		mAlertInfo = new String();

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2) {
			if (null != data) {
				mGameLat = data.getStringExtra("lat");
				mGameLong = data.getStringExtra("long");
				mLocalizationTV.setText("Latitude: " + mGameLat + "\nLongitude: " + mGameLong);
			}
		}
	}

	public void onClickPickLocalization(View v) {
		Intent intentGetMessage = new Intent(this, PickLocalizationActivity.class);
		startActivityForResult(intentGetMessage, 2);
	}

	private void fillFields() {
		Bundle details = getIntent().getExtras();
		mGameId = details.getString(Constants.ID);
		mGameNameEditText.setText(details.getString(GameServices.NAME));
		mGameDescriptionEditText.setText(details.getString(GameServices.DESCRIPTION));
		mGameDurationEditText.setText(details.getString(GameServices.DURATION));
		mGameLocalizationNameEditText.setText(details.getString(GameServices.LOCALIZATION));
		mGameRadiusEditText.setText(details.getString(GameServices.RADIUS));
		mGameMaxPointsEditText.setText(details.getString(GameServices.POINTS_MAX));
		mGameMaxPlayersEditText.setText(details.getString(GameServices.PLAYERS_MAX));
		
		DateTimeServices.setDefaultTime(details.getString(GameServices.TIME_START), mGameDateStartDatePicker, mGameTimeStartTimePicker);
	}

	private void editGame() {
		String mGameName = mGameNameEditText.getText().toString();
		String mGameDescription = mGameDescriptionEditText.getText().toString();
		String mGameDuration = mGameDurationEditText.getText().toString();
		String mGameMaxPoints = mGameMaxPointsEditText.getText().toString();
		String mGameMaxPlayers = mGameMaxPlayersEditText.getText().toString();
		String mGameLocalizationName = mGameLocalizationNameEditText.getText().toString();
		String mGameRadius = mGameRadiusEditText.getText().toString();
		String mGameDateTime = DateTimeServices.dateTimeToString(mGameTimeStartTimePicker, mGameDateStartDatePicker);

		if (mGameName.isEmpty())
			mAlertInfo += "\n" + this.getString(R.string.please_enter_gamename);
		if (mGameDescription.isEmpty())
			mAlertInfo += "\n" + this.getString(R.string.please_enter_description);
		if (mGameDuration.isEmpty())
			mAlertInfo += "\n" + this.getString(R.string.please_enter_duration);
		if (mGameMaxPoints.isEmpty())
			mAlertInfo += "\n" + this.getString(R.string.please_enter_points);
		if (mGameMaxPlayers.isEmpty())
			mAlertInfo += "\n" + this.getString(R.string.please_enter_players);
		if (mGameLocalizationName.isEmpty())
			mAlertInfo += "\n" + this.getString(R.string.please_enter_loc_name);
		if (mGameDateTime.isEmpty())
			mAlertInfo += "\n" + this.getString(R.string.wrong_date);
		if (mGameRadius.isEmpty())
			mAlertInfo += "\n" + this.getString(R.string.please_enter_radius);

		if (mAlertInfo.isEmpty()) {
			JSONObject editedGame = new JSONObject();
			JSONObject localization = new JSONObject();
			JSONObject redTeamBase = new JSONObject();
			JSONObject blueTeamBase = new JSONObject();
			GameServices mGameServices = new GameServices(EditGameActivity.this);
			try {
				editedGame.put("id", mGameId);
				editedGame.put(GameServices.NAME, mGameName);
				editedGame.put(GameServices.DESCRIPTION, mGameDescription);
				editedGame.put(GameServices.TIME_START, mGameDateTime);
				editedGame.put(GameServices.STATUS, "NEW");
				editedGame.put(GameServices.DURATION, mGameDuration);
				editedGame.put(GameServices.POINTS_MAX, mGameMaxPoints);
				editedGame.put(GameServices.PLAYERS_MAX, mGameMaxPlayers);
				localization.put(GameServices.NAME, mGameLocalizationName);
				JSONArray latLngLoc = new JSONArray();
				// latLngLoc.put(mGameLong);
				// latLngLoc.put(mGameLat);
				latLngLoc.put("53.440864");
				latLngLoc.put("14.539547");
				localization.put(GameServices.LAT_LNG, latLngLoc);
				localization.put(GameServices.RADIUS, mGameRadius);
				editedGame.put(GameServices.LOCALIZATION, localization);
				redTeamBase.put(GameServices.NAME, "RED TEAM");
				JSONArray latLngRed = new JSONArray();
				latLngRed.put("53.442736");
				latLngRed.put("14.537723");
				redTeamBase.put(GameServices.LAT_LNG, latLngRed);
				editedGame.put(GameServices.RED_TEAM_BASE, redTeamBase);
				blueTeamBase.put(GameServices.NAME, "BLUE TEAM");
				JSONArray latLngBlue = new JSONArray();
				latLngBlue.put("53.439516");
				latLngBlue.put("14.540963");
				blueTeamBase.put(GameServices.LAT_LNG, latLngBlue);
				editedGame.put(GameServices.BLUE_TEAM_BASE, blueTeamBase);
				mGameServices.editGame(this, editedGame);
				Intent backToGameList = new Intent("com.blstream.ctf2.GAMELISTACTIVITY");
				startActivity(backToGameList);
			} catch (JSONException e) {
				Log.e("CreateGame JSONException", e.toString());
			}
		} else {
			mAlert.showAlert(mAlertInfo);
			mAlertInfo = "";
		}
	}

	public void onClickCreateGameButton(View v) {
		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "create_game_click", null);
		try {
			editGame();
		} catch (Exception e) {
			Log.e("onClickEditGameButton", e.toString());
		}
	}
}
