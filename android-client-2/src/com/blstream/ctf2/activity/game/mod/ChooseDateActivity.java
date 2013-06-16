package com.blstream.ctf2.activity.game.mod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.blstream.ctf2.R;
import com.blstream.ctf2.services.DateTimeServices;
import com.blstream.ctf2.services.GameServices;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * @author Lukasz Dmitrowski
 */

public class ChooseDateActivity extends Activity {

	private DatePicker mGameDatePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_date);
		mGameDatePicker = (DatePicker) findViewById(R.id.datePickerTimeStart);
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
		String date = getIntent().getExtras().getString(GameServices.TIME_START);
		DateTimeServices.setDefaultDate(date, mGameDatePicker);
	}

	public void onClickSaveButton(View v) {
		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "edit_save_date_click", null);
		Intent intentMessage = new Intent();
		intentMessage.putExtra(GameServices.TIME_START, DateTimeServices.dateToString(mGameDatePicker));
		setResult(2, intentMessage);
		finish();
	}
}
