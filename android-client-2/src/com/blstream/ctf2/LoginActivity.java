package com.blstream.ctf2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;

/**
 * 
 * @author Rafal Tatol
 * @author Lukasz Dmitrowski
 * @author Karol Firmanty
 * 
 */
public class LoginActivity extends Activity {

	private EditText mUsernameEditText;
	private EditText mPasswordEditText;
	private Login login;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mUsernameEditText = (EditText) findViewById(R.id.editTextLoginUserame);
		mPasswordEditText = (EditText) findViewById(R.id.editTextLoginPassword);
		mProgressDialog = new ProgressDialog(LoginActivity.this);
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

	public void loginResultNotification(String notification) {
		mProgressDialog.dismiss();
		if(notification.equals(this.getString(R.string.login_successful))){
			Intent intent = new Intent("com.blstream.ctf2.AFTERLOGINACTIVITY");
			startActivity(intent);
		}
		else{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(notification);
			builder.setPositiveButton(R.string.ok, null);
			builder.show();
		}
	}

	public void onClickButton(View v) {
		switch (v.getId()) {
		case R.id.loginButton:
			EasyTracker.getTracker().sendEvent("ui_action", "button_press", "login_click", null);
			if (!(mUsernameEditText.getText().toString().isEmpty()) && !(mPasswordEditText.getText().toString().isEmpty())) {
				mProgressDialog.setTitle(R.string.login_progress);
				mProgressDialog.show();
				login = new Login(LoginActivity.this);
				login.execute(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
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
