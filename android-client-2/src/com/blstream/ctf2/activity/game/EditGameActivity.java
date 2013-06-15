package com.blstream.ctf2.activity.game;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.activity.game.mod.PickLocalizationActivity;
import com.blstream.ctf2.services.DialogServices;
import com.blstream.ctf2.services.GameServices;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * @author Lukasz Dmitrowski
 */

public class EditGameActivity extends Activity {

	private String mGameId;
	private ListView list;
	public String mGameName;
	private String mGameDescription;
	private String mGameDuration;
	private String mGameMaxPoints;
	private String mGameMaxPlayers;
	private String mGameLocalizationName;
	private String mGameRadius;
	private String mGameDate;
	private String mGameTime;
	private String mGameDateTime;
	private DialogServices mAlert;
	private String mGameLat;
	private String mGameLng;
	private String mRedBaseLat, mRedBaseLng;
	private String mBlueBaseLat, mBlueBaseLng;
	private String mAlertInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editgame);

		list = (ListView) findViewById(R.id.listView);
		list.setClickable(true);
		final ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(getString(R.string.basic_data));
		arrayList.add(getString(R.string.game_description));
		arrayList.add(getString(R.string.choose_date));
		arrayList.add(getString(R.string.choose_time));
		arrayList.add(getString(R.string.game_map));
		ArrayAdapter<String> listAdapter;
		listAdapter = new ArrayAdapter<String>(this, R.layout.textview_mod_list, arrayList);
		list.setAdapter(listAdapter);

		mAlert = new DialogServices(this);
		mAlertInfo = new String();

		fillFields();

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String listElement = ((TextView) view).getText().toString();

				Intent intent = null;
				if (listElement.equals("Dane gry")) {
					intent = new Intent("com.blstream.ctf2.BASICDATAACTIVITY");
					intent.putExtra(GameServices.NAME, mGameName);
					intent.putExtra(GameServices.POINTS_MAX, mGameMaxPoints);
					intent.putExtra(GameServices.PLAYERS_MAX, mGameMaxPlayers);
					intent.putExtra(GameServices.LOCALIZATION, mGameLocalizationName);
					intent.putExtra(GameServices.RADIUS, mGameRadius);
					startActivityForResult(intent, 2);
				}
				if (listElement.equals(getString(R.string.game_description))) {
					intent = new Intent("com.blstream.ctf2.DESCRIPTIONACTIVITY");
					intent.putExtra(GameServices.DESCRIPTION, mGameDescription);
					startActivityForResult(intent, 3);
				}
				if (listElement.equals(getString(R.string.choose_date))) {
					intent = new Intent("com.blstream.ctf2.CHOOSEDATEACTIVITY");
					intent.putExtra(GameServices.TIME_START, mGameDate);
					startActivityForResult(intent, 4);
				}

				if (listElement.equals(getString(R.string.choose_time))) {
					intent = new Intent("com.blstream.ctf2.CHOOSETIMEACTIVITY");
					intent.putExtra(GameServices.TIME_START, mGameTime);
					startActivityForResult(intent, 5);
				}
				if (listElement.equals(getString(R.string.game_map))) {
					intent = new Intent("com.blstream.ctf2.PICKLOCALIZATIONACTIVITY");
					startActivityForResult(intent, 6);
				}

			}
		});
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
				mGameName = data.getStringExtra(GameServices.NAME);
				mGameMaxPlayers = data.getStringExtra(GameServices.PLAYERS_MAX);
				mGameMaxPoints = data.getStringExtra(GameServices.POINTS_MAX);
				mGameLocalizationName = data.getStringExtra(GameServices.LOCALIZATION);
				mGameRadius = data.getStringExtra(GameServices.RADIUS);
			}
		}
		if (requestCode == 3) {
			if (null != data) {
				mGameDescription = data.getStringExtra(GameServices.DESCRIPTION);
			}
		}
		if (requestCode == 4) {
			if (null != data) {
				mGameDate = data.getStringExtra(GameServices.TIME_START);
			}
		}
		if (requestCode == 5) {
			if (null != data) {
				mGameTime = data.getStringExtra(GameServices.TIME_START);
			}
		}
		if (requestCode == 6) {
			if (null != data) {
				if (!((data.getStringExtra(GameServices.LAT).equals("0.0")) || (data.getStringExtra(GameServices.LNG).equals("0.0")))) {
					mGameLat = data.getStringExtra(GameServices.LAT);
					mGameLng = data.getStringExtra(GameServices.LNG);
					Log.i("mGameLat: ", mGameLat);
					Log.i("mGameLong: ", mGameLng);
				}

				if (!((data.getStringExtra(GameServices.RED_BASE_LAT).equals("0.0")) || (data.getStringExtra(GameServices.RED_BASE_LNG).equals("0.0")))) {
					mRedBaseLat = data.getStringExtra(GameServices.RED_BASE_LAT);
					mRedBaseLng = data.getStringExtra(GameServices.RED_BASE_LNG);
					Log.i("mRedLat: ", mRedBaseLat);
					Log.i("mRedLong: ", mRedBaseLng);
				}

				if (!((data.getStringExtra(GameServices.BLUE_BASE_LAT).equals("0.0")) || (data.getStringExtra(GameServices.BLUE_BASE_LNG).equals("0.0")))) {
					mBlueBaseLat = data.getStringExtra(GameServices.BLUE_BASE_LAT);
					mBlueBaseLng = data.getStringExtra(GameServices.BLUE_BASE_LNG);
				}
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
		mGameName = details.getString(GameServices.NAME);
		mGameDuration = details.getString(GameServices.DURATION);
		mGameDescription = details.getString(GameServices.DESCRIPTION);
		mGameLocalizationName = details.getString(GameServices.LOCALIZATION);
		mGameRadius = details.getString(GameServices.RADIUS);
		mGameMaxPoints = details.getString(GameServices.POINTS_MAX);
		mGameMaxPlayers = details.getString(GameServices.PLAYERS_MAX);
		mGameDate = details.getString(GameServices.TIME_START).substring(0, 10);
		Log.i("Date", mGameDate);
		mGameTime = details.getString(GameServices.TIME_START).substring(11, 16);
		Log.i("Time", mGameTime);
		mGameDateTime = mGameDate + " " + mGameTime + ":00";
		Log.i("mGameDateTime", mGameDateTime);
		mGameLat = details.getString(GameServices.LAT);
		mGameLng = details.getString(GameServices.LNG);
		mRedBaseLat = details.getString(GameServices.RED_BASE_LAT);
		mRedBaseLng = details.getString(GameServices.RED_BASE_LNG);
		mBlueBaseLat = details.getString(GameServices.BLUE_BASE_LAT);
		mBlueBaseLng = details.getString(GameServices.BLUE_BASE_LNG);
		Log.i("center", mGameLat);
		Log.i("center", mGameLng);
		Log.i("red", mRedBaseLat);
		Log.i("red", mRedBaseLng);
		Log.i("blue", mBlueBaseLat);
		Log.i("blue", mBlueBaseLat);
	}

	private void editGame() {
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
		if (mGameRadius.isEmpty())
			mAlertInfo += "\n" + this.getString(R.string.please_enter_radius);

		mGameDateTime = mGameDate + " " + mGameTime + ":00";
		Log.i("Data do zmiany", mGameDateTime);

		if (mAlertInfo.isEmpty()) {
			JSONObject editedGame = new JSONObject();
			JSONObject localization = new JSONObject();
			JSONObject redTeamBase = new JSONObject();
			JSONObject blueTeamBase = new JSONObject();
			GameServices mGameServices = new GameServices(EditGameActivity.this);
			try {
				editedGame.put(Constants.ID, mGameId);
				editedGame.put(GameServices.NAME, mGameName);
				editedGame.put(GameServices.DESCRIPTION, mGameDescription);
				editedGame.put(GameServices.TIME_START, mGameDateTime);
				editedGame.put(GameServices.STATUS, "NEW");
				editedGame.put(GameServices.DURATION, mGameDuration);
				editedGame.put(GameServices.POINTS_MAX, mGameMaxPoints);
				editedGame.put(GameServices.PLAYERS_MAX, mGameMaxPlayers);
				localization.put(GameServices.NAME, mGameLocalizationName);
				JSONArray latLngLoc = new JSONArray();
				latLngLoc.put(mGameLat);
				latLngLoc.put(mGameLng);
				localization.put(GameServices.LAT_LNG, latLngLoc);
				localization.put(GameServices.RADIUS, mGameRadius);
				editedGame.put(GameServices.LOCALIZATION, localization);
				redTeamBase.put(GameServices.NAME, "RED TEAM");
				JSONArray latLngRed = new JSONArray();
				latLngRed.put(mRedBaseLat);
				latLngRed.put(mRedBaseLng);
				redTeamBase.put(GameServices.LAT_LNG, latLngRed);
				editedGame.put(GameServices.RED_TEAM_BASE, redTeamBase);
				blueTeamBase.put(GameServices.NAME, "BLUE TEAM");
				JSONArray latLngBlue = new JSONArray();
				latLngBlue.put(mBlueBaseLat);
				latLngBlue.put(mBlueBaseLng);
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

	public void onClickEditGameButton(View v) {
		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "edit_save_click", null);
		try {
			editGame();
		} catch (Exception e) {
			Log.e("onClickEditGameButton", e.toString());
		}
	}
}
