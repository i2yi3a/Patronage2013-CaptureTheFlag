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

public class DescriptionActivity extends Activity {

	private EditText mGameDescriptionEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_description);
		mGameDescriptionEditText = (EditText) findViewById(R.id.editTextGameDescription);
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
		mGameDescriptionEditText.setText(details.getString(GameServices.DESCRIPTION));
	}

	public void onClickSaveButton(View v) {
		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "edit_save_description_click", null);
		Intent intentMessage = new Intent();
		intentMessage.putExtra(GameServices.DESCRIPTION, mGameDescriptionEditText.getText().toString());
		setResult(2, intentMessage);
		finish();
	}
}
