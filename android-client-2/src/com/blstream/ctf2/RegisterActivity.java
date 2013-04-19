package com.blstream.ctf2;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Player registration class
 * 
 * @author Rafal Tatol
 */
public class RegisterActivity extends Activity {

	private EditText mUsernameEditText;
	private EditText mPasswordEditText;
	private EditText mRepeatPassEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		mUsernameEditText = (EditText) findViewById(R.id.editTextUserame);
		mPasswordEditText = (EditText) findViewById(R.id.editTextPassword);
		mRepeatPassEditText = (EditText) findViewById(R.id.editTextRepeatPassword);
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

	public void onClickRegisterButton(View v) {
		// TODO REST
	}
}