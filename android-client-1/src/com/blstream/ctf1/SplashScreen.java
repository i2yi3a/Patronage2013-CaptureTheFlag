package com.blstream.ctf1;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

/**
 * @author Milosz_Skalski
 */

public class SplashScreen extends Activity {

	private Handler mHandler;
	private Thread mThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		mHandler = new Handler();
		mHandler.postDelayed(mThread = new Thread() {
			@Override
			public void run() {
				Intent register = new Intent(SplashScreen.this, LoginActivity.class);
				SplashScreen.this.startActivity(register);
				SplashScreen.this.finish();
			}
		}, Constants.SPLASHSCREEN_DELAY);
	}

	@Override
	public void onBackPressed() {
		mHandler.removeCallbacks(mThread);
		finish();
	}

}
