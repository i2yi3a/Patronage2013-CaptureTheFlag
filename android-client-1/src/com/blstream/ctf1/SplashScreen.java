/**
 * @author Milosz_Skalski
 */
package com.blstream.ctf1;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;


//TODO header of class should be here
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
		// TODO Auto-generated method stub //TODO please remove Auto-generated comments
		mHandler.removeCallbacks(mThread);
		finish();
	}
    
}
