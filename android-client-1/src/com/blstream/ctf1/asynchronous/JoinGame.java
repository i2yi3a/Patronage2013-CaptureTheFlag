package com.blstream.ctf1.asynchronous;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.ProgressDialogNetworkOperation;
import com.blstream.ctf1.R;
import com.blstream.ctf1.service.GameService;

/**
 * @author Piotr Marczycki
 */
public class JoinGame extends AsyncTask<Void, Void, Void> {
	private Activity mCurrentActivity;
	private String mGameId;
	private String mMessageToShow;
	private ProgressDialogNetworkOperation loadingDialog;
	private GameService mGameService;

	@Override
	protected void onPreExecute() {
		loadingDialog.setTitle(mCurrentActivity.getResources().getString(R.string.loading));
		loadingDialog.setMessage(mCurrentActivity.getResources().getString(R.string.loading_message));
		loadingDialog.setNetworkOperationService(mGameService);
		loadingDialog.show();
	}

	public JoinGame(Activity currentActivity, String gameId) {
		mCurrentActivity = currentActivity;
		mGameId = gameId;

		mMessageToShow = mCurrentActivity.getResources().getString(R.string.game_joined);
		mGameService = new GameService(mCurrentActivity);
		loadingDialog = new ProgressDialogNetworkOperation(mCurrentActivity, this);
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			if (!mGameService.isLoggedPlayerSignedInForGame(mGameId)) {
				mGameService.signInForGame(mGameId);
			} else {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.error_code_already_signed);
			}
		} catch (Exception e) {
			if (mGameService.isNetworkOperationAborted()) {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.game_join_canceled);
			} else {
				mMessageToShow = e.getLocalizedMessage();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void successful) {
		loadingDialog.dismiss();
		Toast.makeText(mCurrentActivity, mMessageToShow, Toast.LENGTH_SHORT).show();

	}
}
