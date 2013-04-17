/**
 * @author Milosz_Skalski
 */
package com.blstream.ctf1;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;



public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        
    	new Handler().postDelayed(new Thread() {
    		@Override
    		public void run() {
    			Intent register = new Intent(SplashScreen.this, LoginActivity.class);
    			SplashScreen.this.startActivity(register);
    			SplashScreen.this.finish();
    		}
    	}, Config.SPLASHSCREEN_DELAY);
   
    }
    
}
