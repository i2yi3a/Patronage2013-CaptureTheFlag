package com.blstream.ctf2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * SplashScreen class. Display splash screen for 2 seconds.
 * @author Marcin Szmit
 */
public class SplashScreen extends Activity {

	private boolean mIsBackButtonPressed;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
				if (!mIsBackButtonPressed) {
					Intent intent = new Intent(
							"com.blstream.ctf2.LOGINACTIVITY");
					startActivity(intent);
				}
			}
		}, Constants.SPLASH_SHOW_TIME);
	}

	@Override
	public void onBackPressed() {
		mIsBackButtonPressed = true;
		super.onBackPressed();
	}
}