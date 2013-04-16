package com.blstream.ctf2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * @author Marcin Szmit
 * 
 *         SplashScreen class
 * 
 *         Display splash screen for 2 seconds.
 * 
 */
public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		Thread splash = new Thread() {
			public void run() {
				try {
					sleep(2000);

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent openMainActivity = new Intent(
							"com.blstream.ctf2.MAINACTIVITY");
					startActivity(openMainActivity);
					finish();
				}
			}
		};
		splash.start();
	}

}
