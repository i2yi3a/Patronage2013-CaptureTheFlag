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
public class DeleteGame extends AsyncTask<Void, Void, Void> {
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

	public DeleteGame(Activity currentActivity, String gameId) {
		mCurrentActivity = currentActivity;
		mGameId = gameId;
		mGameService = new GameService(mCurrentActivity);
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			if (mGameService.isLoggedPlayerGameOwner(mGameId)) {
				mGameService.deleteGame(mGameId);
			} else {
				mErrorString = mCurrentActivity.getResources().getString(R.string.not_game_owner);
			}
		} catch (Exception e) {
			mErrorString = e.getLocalizedMessage();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		mLoadingDialog.dismiss();
		// TODO Change "Resource with ID ... doesn't exist" server response
		// to game_already_deleted or something similiar
		if (mErrorString != null) {
			Toast.makeText(mCurrentActivity, mErrorString, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mCurrentActivity, R.string.game_deleted, Toast.LENGTH_SHORT).show();
		}
	}
}
