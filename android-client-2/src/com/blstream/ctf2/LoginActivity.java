package com.blstream.ctf2;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * 
 * @author Rafal Tatol
 * @author Lukasz Dmitrowski
 * @author Karol Firmanty
 * 
 */
public class LoginActivity extends Activity {

	private EditText 	mUsernameEditText;
	private EditText 	mPasswordEditText;
	private	Login		login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mUsernameEditText = (EditText) findViewById(R.id.editTextLoginUserame);
		mPasswordEditText = (EditText) findViewById(R.id.editTextLoginPassword);
		login = new Login(this.getApplicationContext());
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

	public void onClickButton(View v) {
		switch (v.getId()) {
		case R.id.loginButton:
			EasyTracker.getTracker().sendEvent("ui_action", "button_press", "login_click", null);
			if (!(mUsernameEditText.getText().toString().isEmpty()) && !(mPasswordEditText.getText().toString().isEmpty())){
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				int result = login.userLogin(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
				if(result == -1){
					alertDialogBuilder.setMessage(R.string.login_failed);
					alertDialogBuilder.setPositiveButton(R.string.ok, null);
					alertDialogBuilder.show();
				}
				else if(result == 0){
					alertDialogBuilder.setMessage(R.string.login_successful);
					alertDialogBuilder.setPositiveButton(R.string.ok, null);
					alertDialogBuilder.show();
				}
				else if(result == -2){
					alertDialogBuilder.setMessage(R.string.no_connection);
					alertDialogBuilder.setPositiveButton(R.string.ok, null);
					alertDialogBuilder.show();
				}
			}
			break;
		case R.id.registrationButton:
			EasyTracker.getTracker().sendEvent("ui_action", "button_press", "registration_click", null);
			Intent intent = new Intent("com.blstream.ctf2.REGISTERACTIVITY");
			startActivity(intent);
			break;
		}
	}

}
