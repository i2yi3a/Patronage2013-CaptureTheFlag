package com.blstream.ctf1.asynchronous;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.service.NetworkOperationService;

/**
 * @author Adrian Swarcewicz
 */
public class AbortNetworkOperation extends AsyncTask<Void, Void, UnsupportedOperationException> {

	private static final String ERROR_ON_CANCEL_REQUEST = "Error on cancel request";

	private static final String NETWORK_OPERATION_SERVICE_CANNOT_BE_NULL = "Network operation service cannot be null";

	private Activity mCurrentActivity;

	private NetworkOperationService mNetworkOperationService;

	public AbortNetworkOperation(Activity currentActivity, NetworkOperationService networkOperationService) {
		super();
		this.mCurrentActivity = currentActivity;
		this.mNetworkOperationService = networkOperationService;
		if (networkOperationService == null) {
			throw new NullPointerException(NETWORK_OPERATION_SERVICE_CANNOT_BE_NULL);
		}
	}

	@Override
	protected UnsupportedOperationException doInBackground(Void... params) {
		UnsupportedOperationException result = null;
		try {
			mNetworkOperationService.abortNetworkOperation();
		} catch (UnsupportedOperationException e) {
			result = e;
		}
		return result;
	}

	@Override
	protected void onPostExecute(UnsupportedOperationException result) {
		if (result != null) {
			Toast.makeText(mCurrentActivity, ERROR_ON_CANCEL_REQUEST, Toast.LENGTH_LONG).show();
		}
		super.onPostExecute(result);
	}
}
