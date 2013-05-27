package com.blstream.ctf1.asynchronous;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.R;
import com.blstream.ctf1.dialog.NetworkOperationProgressDialog;
import com.blstream.ctf1.service.PlayerService;

/**
 * @author Adrian Swarcewicz
 * @author Rafal Olichwer
 */
public class Register extends AsyncTask<Void, Void, Boolean> {
	
	private Activity mCurrentActivity;
	private Class<?> mSuccessfullActivity;
	private String mUsername;
	private String mPassword;
	private String mMessageToShow;
	private NetworkOperationProgressDialog loadingDialog;
	private PlayerService mPlayerService;

	@Override
	protected void onPreExecute() {
		loadingDialog.setTitle(mCurrentActivity.getResources().getString(R.string.loading));
		loadingDialog.setMessage(mCurrentActivity.getResources().getString(R.string.loading_message));
		loadingDialog.setNetworkOperationService(mPlayerService);
		loadingDialog.show();
	}

	public Register(Activity currentActivity, Class<?> successfulActivity, String username, String password) {
		mCurrentActivity = currentActivity;
		mSuccessfullActivity = successfulActivity;
		mUsername = username;
		mPassword = password;
		
		mMessageToShow = mCurrentActivity.getResources().getString(R.string.registration_successful);
		mPlayerService = new PlayerService(mCurrentActivity);
		loadingDialog = new NetworkOperationProgressDialog(mCurrentActivity, this);
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		Boolean successful = false;
		try {
			mPlayerService.registerPlayer(mUsername, mPassword);
			successful = true;
		} catch (Exception e) {
			if (mPlayerService.isNetworkOperationAborted()) {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.registration_canceled);
			} else {
				mMessageToShow = e.getLocalizedMessage();
			}
		}
		return successful;
	}

	@Override
	protected void onPostExecute(Boolean successful) {
		loadingDialog.dismiss();
		Toast.makeText(mCurrentActivity, mMessageToShow, Toast.LENGTH_SHORT).show();
		if (successful == true) {
			Intent intent = new Intent(mCurrentActivity, mSuccessfullActivity);
			mCurrentActivity.startActivity(intent);
            mCurrentActivity.finish();
		}
	}

}
