package com.blstream.ctf1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.blstream.ctf1.asynchronous.AbortNetworkOperation;
import com.blstream.ctf1.service.NetworkOperationService;

/**
 * DO NOT USE STATIC METHODS!
 * 
 * @author Adrian Swarcewicz
 */
public class ProgressDialogNetworkOperation extends ProgressDialog {
	
	boolean mPressedBefore = false;
	
	private AsyncTask<?, ?, ?> mAsyncTask;

	private NetworkOperationService mNetworkOperationService;

	private Activity mCurrentActivity;

	public ProgressDialogNetworkOperation(Activity currentActivity, AsyncTask<?, ?, ?> currentAsyncTask) {
		super(currentActivity);
		mAsyncTask = currentAsyncTask;
	}

	public ProgressDialogNetworkOperation(Activity currentActivity, AsyncTask<?, ?, ?> currentAsyncTask, int theme) {
		super(currentActivity, theme);
		mAsyncTask = currentAsyncTask;
	}

	public void setNetworkOperationService(NetworkOperationService networkOperationService) {
		this.mNetworkOperationService = networkOperationService;
	}

	@Override
	public void onBackPressed() {
		if (mPressedBefore == false) {
			mPressedBefore = true;
			AbortNetworkOperation abortNetworkOperation = new AbortNetworkOperation(mCurrentActivity, mNetworkOperationService);
			abortNetworkOperation.execute();
		} else {
			mAsyncTask.cancel(true);
			super.onBackPressed();
		}
	}
}
