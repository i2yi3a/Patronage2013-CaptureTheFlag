package com.blstream.ctf1.asynchronous;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.R;
import com.blstream.ctf1.service.GameService;

/**
 * @author Piotr Marczycki
 */
public class LeaveGame extends AsyncTask<Void, Void, Void> {
	private Activity mCurrentActivity;
	private String mGameId;
	private String mErrorString;
	private ProgressDialog mLoadingDialog;
	private GameService mGameService;

	@Override
	protected void onPreExecute() {
		mLoadingDialog = ProgressDialog.show(mCurrentActivity, mCurrentActivity.getResources().getString(R.string.loading), mCurrentActivity.getResources()
				.getString(R.string.loading_message));
	}

	public LeaveGame(Activity currentActivity, String gameId) {
		mCurrentActivity = currentActivity;
		mGameId = gameId;
		mGameService = new GameService(mCurrentActivity);
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			if (mGameService.isLoggedPlayerSignedUpForGame(mGameId)) {
				mGameService.signOutFromGame(mGameId);
			} else {
				mErrorString = mCurrentActivity.getResources().getString(R.string.error_code_103);
			}
		} catch (Exception e) {
			mErrorString = e.getLocalizedMessage();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		mLoadingDialog.dismiss();
		if (mErrorString != null) {
			Toast.makeText(mCurrentActivity, mErrorString, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mCurrentActivity, R.string.game_left, Toast.LENGTH_SHORT).show();
		}
	}
}
