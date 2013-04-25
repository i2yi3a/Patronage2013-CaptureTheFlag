package com.blstream.ctf1.asynchronous;

import com.blstream.ctf1.R;
import com.blstream.ctf1.service.NetworkService;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;


/**
 * @author Adrian Swarcewicz
 */
public class Register extends AsyncTask<Void, Void, Void> {
	
	private Activity mCurrentActivity;
	
	private Class<?> mSuccessfullActivity;

	private String mUsername;
	
	private String mPassword;
	
	private String errorString;
	
	
	
	
	
	public Register(Activity currentActivity, Class<?> successfullActivity, String username, String password) {
		mCurrentActivity = currentActivity;
		mSuccessfullActivity = successfullActivity;
		mUsername = username;
		mPassword = password;
	}
	
	
	
	@Override
	protected Void doInBackground(Void... params) {
		NetworkService networkService = new NetworkService(mCurrentActivity.getResources());
		try {
			networkService.registerPlayer(mUsername, mPassword);
		// no sense to catch others exceptions all are handled in that same way
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
		}
		return null;
	}



	@Override
	protected void onPostExecute(Void result) {
		if (errorString != null) {
			Toast.makeText(mCurrentActivity, errorString, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mCurrentActivity, R.string.register_successful, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(mCurrentActivity, mSuccessfullActivity);
			mCurrentActivity.startActivity(intent);
		}
	}

	
}
