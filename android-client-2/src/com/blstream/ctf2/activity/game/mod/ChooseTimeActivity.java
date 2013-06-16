package com.blstream.ctf2.activity.game.mod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.blstream.ctf2.R;
import com.blstream.ctf2.services.DateTimeServices;
import com.blstream.ctf2.services.GameServices;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * @author Lukasz Dmitrowski
 */

public class ChooseTimeActivity extends Activity {

	private TimePicker mGameTimePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_time);
		mGameTimePicker = (TimePicker) findViewById(R.id.timePickerTimeStart);
		mGameTimePicker.setIs24HourView(true);
		if (!(getIntent().getExtras().getString(GameServices.TIME_START)).isEmpty())
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
		String time = getIntent().getExtras().getString(GameServices.TIME_START);
		Log.i("TIME",time);
		DateTimeServices.setDefaultTime(time, mGameTimePicker);
	}

	public void onClickSaveButton(View v) {
		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "edit_save_date_click", null);
		Intent intentMessage = new Intent();
		intentMessage.putExtra(GameServices.TIME_START, DateTimeServices.timeToString(mGameTimePicker));
		setResult(5, intentMessage);
		finish();
	}
}
