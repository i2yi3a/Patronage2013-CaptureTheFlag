package com.blstream.ctf1.asynchronous;

import android.os.AsyncTask;

import com.blstream.ctf1.service.NetworkOperationService;

/**
 * @author Adrian Swarcewicz
 */
public class AbortNetworkOperation extends AsyncTask<Void, Void, UnsupportedOperationException> {

	private static final String NETWORK_OPERATION_SERVICE_CANNOT_BE_NULL = "Network operation service cannot be null";

	private NetworkOperationService mNetworkOperationService;

	public AbortNetworkOperation(NetworkOperationService networkOperationService) {
		super();
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
}
