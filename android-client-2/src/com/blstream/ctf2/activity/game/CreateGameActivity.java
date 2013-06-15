package com.blstream.ctf2.activity.game;

import java.util.Calendar;

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

import com.blstream.ctf2.R;
import com.blstream.ctf2.services.DialogServices;
import com.blstream.ctf2.services.GameServices;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * Game creation class
 * 
 * @author Kamil Wisniewski
 * @author Lukasz Dmitrowski
 */

public class CreateGameActivity extends Activity {

	private EditText mGameNameEditText;
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
		setContentView(R.layout.activity_creategame);

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

	private String dateTimeToString(TimePicker timeP, DatePicker dateP) {
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DATE, -1);
		int nowYear = c.get(Calendar.YEAR);
		int nowMonth = c.get(Calendar.MONTH);
		int nowDay = c.get(Calendar.DAY_OF_MONTH) + 1;
		int nowHour = c.get(Calendar.HOUR_OF_DAY);
		int nowMinute = c.get(Calendar.MINUTE);
		int year = dateP.getYear();
		int month = dateP.getMonth();
		int day = dateP.getDayOfMonth();
		int hour = timeP.getCurrentHour();
		int minute = timeP.getCurrentMinute();

		String dateTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
		
		if (year > nowYear)
			return dateTime;
		if (year < nowYear)
			return "";
		if (month > nowMonth)
			return dateTime;
		if (month < nowMonth)
			return "";
		if (day > nowDay)
			return dateTime;
		if (day < nowDay)
			return "";
		if (hour > nowHour)
			return dateTime;
		if (hour < nowHour)
			return "";
		if (minute > nowMinute)
			return dateTime;
		else
			return "";
	}

	public void onClickPickLocalization(View v) {
		//Intent intentGetMessage = new Intent(this, PickLocalizationActivity.class);
		//startActivityForResult(intentGetMessage, 2);
	}

	private void createGame() {
		String mGameName = mGameNameEditText.getText().toString();
		String mGameDescription = mGameDescriptionEditText.getText().toString();
		String mGameDuration = mGameDurationEditText.getText().toString();
		String mGameMaxPoints = mGameMaxPointsEditText.getText().toString();
		String mGameMaxPlayers = mGameMaxPlayersEditText.getText().toString();
		String mGameLocalizationName = mGameLocalizationNameEditText.getText().toString();
		String mGameRadius = mGameRadiusEditText.getText().toString();
		String mGameDateTime = dateTimeToString(mGameTimeStartTimePicker, mGameDateStartDatePicker);

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
			JSONObject newGame = new JSONObject();
			JSONObject localization = new JSONObject();
			JSONObject redTeamBase = new JSONObject();
			JSONObject blueTeamBase = new JSONObject();
			GameServices mGameServices = new GameServices(CreateGameActivity.this);
			try {
				newGame.put(GameServices.NAME, mGameName);
				newGame.put(GameServices.DESCRIPTION, mGameDescription);
				newGame.put(GameServices.TIME_START, mGameDateTime);
				newGame.put(GameServices.DURATION, mGameDuration);
				newGame.put(GameServices.POINTS_MAX, mGameMaxPoints);
				newGame.put(GameServices.PLAYERS_MAX, mGameMaxPlayers);
				localization.put(GameServices.NAME, mGameLocalizationName);
				JSONArray latLngLoc = new JSONArray();
				// latLngLoc.put(mGameLong);
				// latLngLoc.put(mGameLat);
				latLngLoc.put("53.440864");
				latLngLoc.put("14.539547");
				localization.put(GameServices.LAT_LNG, latLngLoc);
				localization.put(GameServices.RADIUS, mGameRadius);
				newGame.put(GameServices.LOCALIZATION, localization);
				redTeamBase.put(GameServices.NAME, "RED TEAM");
				JSONArray latLngRed = new JSONArray();
				latLngRed.put("53.442736");
				latLngRed.put("14.537723");
				redTeamBase.put(GameServices.LAT_LNG, latLngRed);
				newGame.put(GameServices.RED_TEAM_BASE, redTeamBase);
				blueTeamBase.put(GameServices.NAME, "BLUE TEAM");
				JSONArray latLngBlue = new JSONArray();
				latLngBlue.put("53.439516");
				latLngBlue.put("14.540963");
				blueTeamBase.put(GameServices.LAT_LNG, latLngBlue);
				newGame.put(GameServices.BLUE_TEAM_BASE, blueTeamBase);
				mGameServices.createGame(this, newGame);
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
			createGame();
		} catch (Exception e) {
			Log.e("onClickCreateGameButton", e.toString());
		}
	}
}
