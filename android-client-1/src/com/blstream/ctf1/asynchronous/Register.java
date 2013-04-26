package com.blstream.ctf1.asynchronous;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.R;
import com.blstream.ctf1.service.NetworkService;


/**
 * @author Adrian Swarcewicz
 * @author Rafal Olichwer
 */
public class Register extends AsyncTask<Void, Void, Void> {
	
	private Activity mCurrentActivity;
	
	private Class<?> mSuccessfullActivity;

	private String mUsername;
	
	private String mPassword;
	
	private String errorString;
	
	private ProgressDialog loadingDialog;
	
	
	
	
	
	@Override
	protected void onPreExecute() {
		loadingDialog = ProgressDialog.show(mCurrentActivity,
				mCurrentActivity.getResources().getString(R.string.loading),
				mCurrentActivity.getResources().getString(R.string.loading_message));
		
		
	}



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
		loadingDialog.dismiss();
		if (errorString != null) {
			Toast.makeText(mCurrentActivity, errorString, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mCurrentActivity, R.string.register_successful, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(mCurrentActivity, mSuccessfullActivity);
			mCurrentActivity.startActivity(intent);
		}
	}

	
}
