package com.blstream.ctf1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blstream.ctf1.asynchronous.CreateGame;
import com.blstream.ctf1.asynchronous.EditGame;
import com.blstream.ctf1.asynchronous.EditGameDetails;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.domain.GameStatusType;
import com.blstream.ctf1.domain.Localization;
import com.blstream.ctf1.pickers.DatePickerFragment;
import com.blstream.ctf1.pickers.TimePickerFragment;
import com.blstream.ctf1.service.NetworkService;
import com.blstream.ctf1.tracker.IssueTracker;
import com.google.android.gms.maps.model.LatLng;

/**
 * @author Milosz_Skalski
 **/

public class CreateGameActivity extends FragmentActivity implements OnClickListener {

	private Button mBtnCancel;
	private Button mBtnCreate;
	public Button mBtnStartDate;
	public Button mBtnStartTime;
	private Button mBtnMap;
	public EditText mEditGameName;
	public EditText mEditGameDescription;
	public EditText mEditLocationName;
	public EditText mEditPlayingTime;
	public EditText mEditMaxPlayers;
	public EditText mEditMaxPoints;

	// default values
    public double latitude = 53.432766;
    public double longitude = 14.548001;
    public double radius = 1000;

	private String info;
	private String mId;
	private int code = 1;

	Handler handlerTime = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String time = msg.getData().getString("time");
			String data = msg.getData().getString("data");
			if (!(time == null)) {
				Log.d("CTF ", "CTF czas:" + time);
				mBtnStartTime.setText(time);
			}
			if (!(data == null)) {
				Log.d("CTF ", "CTF data:" + data);
				mBtnStartDate.setText(data);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);

		mBtnCancel = (Button) findViewById(R.id.btnCancel);
		mBtnCreate = (Button) findViewById(R.id.btnCreate);
		mBtnStartDate = (Button) findViewById(R.id.btnStartDate);
		mBtnStartTime = (Button) findViewById(R.id.btnStartTime);
		mBtnMap = (Button) findViewById(R.id.btnMap);
		mBtnCancel.setOnClickListener(this);
		mBtnCreate.setOnClickListener(this);
		mBtnStartDate.setOnClickListener(this);
		mBtnStartTime.setOnClickListener(this);
		mBtnMap.setOnClickListener(this);

		mEditGameName = (EditText) findViewById(R.id.editGameName);
		mEditGameDescription = (EditText) findViewById(R.id.editGameDescription);
		mEditLocationName = (EditText) findViewById(R.id.editLocationName);
		mEditPlayingTime = (EditText) findViewById(R.id.editPlayingTime);
		mEditMaxPlayers = (EditText) findViewById(R.id.editMaxPlayers);
		mEditMaxPoints = (EditText) findViewById(R.id.editMaxPoints);

		Intent intent = getIntent();
		mId = intent.getStringExtra(Constants.EXTRA_KEY_ID);
		if (mId == null) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			int hour = c.get(Calendar.HOUR_OF_DAY) + 2;
			int minute = 0;
			mBtnStartTime.setText(hour + ":" + minute + ":00");
			mBtnStartDate.setText(year + "-" + (month + 1) + "-" + day);
			mBtnCreate.setText(R.string.create_game);
		} else {
			mBtnCreate.setText(R.string.edit_game);
			EditGameDetails editGameDetails = new EditGameDetails(this, mId);
			editGameDetails.execute();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnCancel:
			IssueTracker.saveClick(this, mBtnCancel);
			finish();
			break;

		case R.id.btnCreate:
			onCreateGameBtnClicked();
			break;

		case R.id.btnStartDate:
			DatePickerFragment datePicker;
			datePicker = new DatePickerFragment();
			datePicker.setHandler(handlerTime);
			datePicker.show(getSupportFragmentManager(), "datePicker");
			break;

		case R.id.btnStartTime:
			TimePickerFragment timePicker;
			timePicker = new TimePickerFragment();
			timePicker.setHandler(handlerTime);
			timePicker.show(getSupportFragmentManager(), "timePicker");
			break;

		case R.id.btnMap:
			Intent intent = new Intent(this, GameAreaMapActivity.class);
			intent.putExtra("latitude", latitude);
			intent.putExtra("longitude", longitude);
			intent.putExtra("radius", radius);
			startActivityForResult(intent, code);
			break;
		}
	}

	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent data) {
		super.onActivityResult(reqCode, resCode, data);
		Log.d("CTF", "CTF onActivityResult");
		Bundle bundle = data.getExtras();
		if (bundle != null) {

			latitude = bundle.getDouble("latitude");
			longitude = bundle.getDouble("longitude");
			radius = bundle.getDouble("radius");
			Log.d("CTF", "Last settings");
			Log.d("CTF", "latitude: " + latitude + " longitude: " + longitude + " radius: " + radius);
		}
	}

	private void onCreateGameBtnClicked() {
		IssueTracker.saveClick(this, mBtnCreate);

		String mLocationName = mEditLocationName.getText().toString();
		String mGameName = mEditGameName.getText().toString();
		String mStartDate = mBtnStartDate.getText().toString();
		String mStartTime = mBtnStartTime.getText().toString();
		String mPlayingTimeTmp = mEditPlayingTime.getText().toString();
		String mMaxPlayersTmp = mEditMaxPlayers.getText().toString();
		String mMaxPointsTmp = mEditMaxPoints.getText().toString();

		boolean correct = correctDateTime(mStartDate, mStartTime);
		boolean correct2 = correctData(mGameName, mLocationName, mPlayingTimeTmp);

		if (correct && correct2) {
			if (NetworkService.isDeviceOnline(this)) {
				if (mMaxPlayersTmp.length() == 0)
					mMaxPlayersTmp = "120"; // no limits?
				if (mMaxPointsTmp.length() == 0)
					mMaxPointsTmp = "120"; // no limits?

				long mPlayingTime = Integer.parseInt(mPlayingTimeTmp) * 60 * 1000;
				int mMaxPlayers = Integer.parseInt(mMaxPlayersTmp);
				int mMaxPoints = Integer.parseInt(mMaxPointsTmp);
				Localization localization = new Localization();
				GameExtendedInfo gameInfo = new GameExtendedInfo();
				gameInfo.setName(mEditGameName.getText().toString());
				gameInfo.setDescription(mEditGameDescription.getText().toString());
				localization.setName(mEditLocationName.getText().toString());
				localization.setRadius(radius);
				localization.setLatLng(new LatLng(latitude, longitude));
				gameInfo.setLocalization(localization);
				Date timeStart = null;
				try {
					timeStart = new SimpleDateFormat(Constants.DATE_FORMAT + " " + Constants.TIME_FORMAT).parse(mStartDate + " " + mStartTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				gameInfo.setTimeStart(timeStart);
				gameInfo.setDuration(mPlayingTime);
				gameInfo.setPlayersMax(mMaxPlayers);
				gameInfo.setPointsMax(mMaxPoints);

				if (mId == null) {
					CreateGame createGame = new CreateGame(this, gameInfo);
					createGame.execute();
				} else {
					if(NetworkService.isDeviceOnline(this)) {
						EditGame editGame = new EditGame(this, mId, GameStatusType.NEW.toString(), gameInfo);
						editGame.execute();
					}else {
						Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT);
					}
				}
			} else {
				Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private boolean correctDateTime(String mStartDate, String mStartTime) {
		info = Constants.EMPTY_STRING;
		final Calendar c = Calendar.getInstance();
		int actualYear = c.get(Calendar.YEAR);
		int actualMonth = c.get(Calendar.MONTH);
		int actualDay = c.get(Calendar.DAY_OF_MONTH) + 1;
		int actualHour = c.get(Calendar.HOUR_OF_DAY);
		int actualMinute = c.get(Calendar.MINUTE);

		int year = Integer.parseInt(mStartDate.substring(0, mStartDate.indexOf("-")));
		int month = Integer.parseInt(mStartDate.substring(mStartDate.indexOf("-") + 1, mStartDate.lastIndexOf("-")));
		int day = Integer.parseInt(mStartDate.substring(mStartDate.lastIndexOf("-") + 1));
		int hour = Integer.parseInt(mStartTime.substring(0, mStartTime.indexOf(":")));
		int minute = Integer.parseInt(mStartTime.substring(mStartTime.indexOf(":") + 1, mStartTime.lastIndexOf(":")));
		Log.d("CTF ", "CTF date correct " + year + ":" + month + ":" + day + "   " + hour + ":" + minute);

		if (year > actualYear)
			return true;
		if (year < actualYear) {
			info += getResources().getString(R.string.start_date_past);
			return false;
		}
		if (month > actualMonth)
			return true;
		if (month < actualMonth) {
			info += getResources().getString(R.string.start_date_past);
			return false;
		}
		if (day > actualDay)
			return true;
		if (day < actualDay) {
			info += getResources().getString(R.string.start_date_past);
			return false;
		}
		if (hour > actualHour)
			return true;
		if (hour < actualHour) {
			info += getResources().getString(R.string.start_date_past);
			return false;
		}
		if (minute > actualMinute)
			return true;

		info += getResources().getString(R.string.start_date_past);
		return false;
	}

	private boolean correctData(String gameName, String locationName, String playingTime) {
		boolean result = false;

		if (gameName.isEmpty()) {
			if (!info.isEmpty())
				info += Constants.NEW_LINE;
			info += getResources().getString(R.string.game_name_too_short);
		}

		if (locationName.isEmpty()) {
			if (!info.isEmpty())
				info += Constants.NEW_LINE;
			info += getResources().getString(R.string.location_name_too_short);
		}

		if (playingTime.isEmpty()) {
			if (!info.isEmpty())
				info += Constants.NEW_LINE;
			info += getResources().getString(R.string.playing_time_too_short);
		}

		if (info.isEmpty()) {
			result = true;
		} else {
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
		}

		return result;
	}

}
