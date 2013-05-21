package com.blstream.ctf1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.blstream.ctf1.service.NetworkOperationService;

/**
 * DO NOT USE STATIC METHODS!
 * 
 * @author Adrian Swarcewicz
 */
public class ProgressDialogNetworkOperation extends ProgressDialog {

	boolean mCancelAttemptBefore = false;

	private AsyncTask<?, ?, ?> mAsyncTask;

	private NetworkOperationService mNetworkOperationService;

	public ProgressDialogNetworkOperation(Context context, AsyncTask<?, ?, ?> asyncTask) {
		super(context);
		mAsyncTask = asyncTask;
	}

	public ProgressDialogNetworkOperation(Context context, AsyncTask<?, ?, ?> asyncTask, int theme) {
		super(context, theme);
		mAsyncTask = asyncTask;
	}

	public void setNetworkOperationService(NetworkOperationService networkOperationService) {
		this.mNetworkOperationService = networkOperationService;
	}

	@Override
	public void cancel() {
		if (mCancelAttemptBefore == false) {
			mCancelAttemptBefore = true;
//			AbortNetworkOperation abortNetworkOperation = new AbortNetworkOperation(mNetworkOperationService);
//			abortNetworkOperation.execute(); // why so much time to start execute on 4.2?
			mNetworkOperationService.abortNetworkOperation();
		} else {
			mAsyncTask.cancel(true);
			super.cancel();
		}
	}

}
