package com.blstream.ctf2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blstream.ctf2.activity.login.LoginActivity;

/**
 * SplashScreen class. Display splash screen for 2 seconds.
 * 
 * @author Marcin Szmit
 */
public class SplashScreen extends Activity {

	private Handler handler;
	private Thread thread;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		handler = new Handler();
		handler.postDelayed(thread = new Thread() {
			@Override
			public void run() {
				Intent login = new Intent(SplashScreen.this, LoginActivity.class);
				SplashScreen.this.startActivity(login);
				SplashScreen.this.finish();
			}
		}, Constants.SPLASH_SHOW_TIME);
	}

	@Override
	public void onBackPressed() {
		handler.removeCallbacks(thread);
		finish();
	}

}
