package com.blstream.ctf1.asynchronous;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.ProgressDialogNetworkOperation;
import com.blstream.ctf1.R;
import com.blstream.ctf1.dialog.NetworkOperationProgressDialog;
import com.blstream.ctf1.service.GameService;

/**
 * @author Piotr Marczycki
 */
public class LeaveGame extends AsyncTask<Void, Void, Void> {
	private Activity mCurrentActivity;
	private String mGameId;
	private String mMessageToShow;
	private NetworkOperationProgressDialog loadingDialog;
	private GameService mGameService;

	@Override
	protected void onPreExecute() {
		loadingDialog.setTitle(mCurrentActivity.getResources().getString(R.string.loading));
		loadingDialog.setMessage(mCurrentActivity.getResources().getString(R.string.loading_message));
		loadingDialog.setNetworkOperationService(mGameService);
		loadingDialog.show();
	}

	public LeaveGame(Activity currentActivity, String gameId) {
		mCurrentActivity = currentActivity;
		mGameId = gameId;

		mMessageToShow = mCurrentActivity.getResources().getString(R.string.game_left);
		mGameService = new GameService(mCurrentActivity);
		loadingDialog = new NetworkOperationProgressDialog(mCurrentActivity, this);
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			if (mGameService.isLoggedPlayerSignedInForGame(mGameId)) {
				mGameService.signOutFromGame(mGameId);
			} else {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.error_code_103);
			}
		} catch (Exception e) {
			if (mGameService.isNetworkOperationAborted()) {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.game_leave_canceled);
			} else {
				mMessageToShow = e.getLocalizedMessage();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		loadingDialog.dismiss();
		Toast.makeText(mCurrentActivity, mMessageToShow, Toast.LENGTH_SHORT).show();
	}
}
