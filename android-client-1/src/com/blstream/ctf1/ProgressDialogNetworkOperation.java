package com.blstream.ctf1;

import android.app.Activity;
import android.app.ProgressDialog;

import com.blstream.ctf1.asynchronous.AbortNetworkOperation;
import com.blstream.ctf1.service.NetworkOperationService;

/**
 * NOT USE STATIC METHODS!
 * 
 * @author Adrian Swarcewicz
 */
public class ProgressDialogNetworkOperation extends ProgressDialog {

	private NetworkOperationService mNetworkOperationService;

	private Activity mCurrentActivity;

	public ProgressDialogNetworkOperation(Activity currentActivity) {
		super(currentActivity);
	}

	public ProgressDialogNetworkOperation(Activity currentActivity, int theme) {
		super(currentActivity, theme);
	}

	public void setNetworkOperationService(NetworkOperationService networkOperationService) {
		this.mNetworkOperationService = networkOperationService;
	}

	@Override
	public void onBackPressed() {
		AbortNetworkOperation abortNetworkOperation = new AbortNetworkOperation(mCurrentActivity, mNetworkOperationService);
		abortNetworkOperation.execute();
		super.onBackPressed();
	}
}
