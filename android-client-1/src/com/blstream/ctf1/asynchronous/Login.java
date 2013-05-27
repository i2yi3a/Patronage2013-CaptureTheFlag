package com.blstream.ctf1.asynchronous;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.R;
import com.blstream.ctf1.dialog.NetworkOperationProgressDialog;
import com.blstream.ctf1.domain.LoggedPlayer;
import com.blstream.ctf1.service.PlayerService;
import com.blstream.ctf1.service.StorageService;

/**
 * @author Adrian Swarcewicz
 * @author Rafal Olichwer
 */
public class Login extends AsyncTask<Void, Void, Boolean> {

	private Activity mCurrentActivity;

	private Class<?> mSuccessfullActivity;

	private String mUsername;

	private String mPassword;
	
	private String mMessageToShow;
	
	private PlayerService mPlayerService;
	
	private StorageService mStorageService;

	private NetworkOperationProgressDialog loadingDialog;

	@Override
	protected void onPreExecute() {
		loadingDialog.setTitle(mCurrentActivity.getResources().getString(R.string.loading));
		loadingDialog.setMessage(mCurrentActivity.getResources().getString(R.string.loading_message));
		loadingDialog.setNetworkOperationService(mPlayerService);
		loadingDialog.show();
	}

	public Login(Activity currentActivity, Class<?> successfullActivity,
			String username, String password) {
		mCurrentActivity = currentActivity;
		mSuccessfullActivity = successfullActivity;
		mUsername = username;
		mPassword = password;
		
		mMessageToShow = mCurrentActivity.getResources().getString(R.string.login_successful);
		mPlayerService = new PlayerService(mCurrentActivity);
		loadingDialog = new NetworkOperationProgressDialog(mCurrentActivity, this);
		mStorageService = new StorageService(mCurrentActivity);
		
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		Boolean successful = false;
		try {
			LoggedPlayer loggedPlayer = mPlayerService.login(mUsername,
					mPassword);
			mStorageService.open();
			mStorageService.saveLoggedPlayer(loggedPlayer);
			mStorageService.close();
			successful = true;
			// no sense to catch others exceptions all are handled in that same
			// way
		} catch (Exception e) {
			if (mPlayerService.isNetworkOperationAborted()) {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.login_canceled);
			} else {
				mMessageToShow = e.getLocalizedMessage();
			}
		}
		return successful;
	}

	@Override
	protected void onPostExecute(Boolean successful) {
		loadingDialog.dismiss();
		Toast.makeText(mCurrentActivity, mMessageToShow, Toast.LENGTH_SHORT)
					.show();
		if(successful == true) {
			Intent intent = new Intent(mCurrentActivity, mSuccessfullActivity);
			mCurrentActivity.startActivity(intent);
		}
	}

}
