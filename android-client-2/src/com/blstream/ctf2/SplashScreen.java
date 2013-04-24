package com.blstream.ctf2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * SplashScreen class. 
 * Display splash screen for 2 seconds.
 * @author Marcin Szmit
 */
public class SplashScreen extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		Thread splash = new Thread() {
			public void run() {
				try {
					sleep(Constants.SPLASH_SHOW_TIME);
					throw new InterruptedException();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent intent = new Intent(
							"com.blstream.ctf2.LOGINACTIVITY");
					startActivity(intent);
					finish();
				}
			}
		};
		splash.start();
	}
}