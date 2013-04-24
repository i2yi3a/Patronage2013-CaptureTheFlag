package com.blstream.ctf2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * 
 * @author Rafal Tatol
 * 
 */
public class LoginActivity extends Activity {

	private EditText mUsernameEditText;
	private EditText mPasswordEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mUsernameEditText = (EditText) findViewById(R.id.editTextLoginUserame);
		mPasswordEditText = (EditText) findViewById(R.id.editTextLoginPassword);

	}

	public void onClickButton(View v) {
		switch (v.getId()) {
		case R.id.loginButton:
			// TODO Login
			break;
		case R.id.registrationButton:
			Intent intent = new Intent("com.example.ctftest.REGISTERACTIVITY");
			startActivity(intent);
			break;
		}
	}

}
