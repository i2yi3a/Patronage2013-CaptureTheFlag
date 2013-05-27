package com.blstream.ctf1.asynchronous;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.ProgressDialogNetworkOperation;
import com.blstream.ctf1.R;
import com.blstream.ctf1.dialog.NetworkOperationProgressDialog;
import com.blstream.ctf1.service.GameService;

/**
 * @author Piotr Marczycki,Rafal Olichwer
 */
public class DeleteGame extends AsyncTask<Void, Void, Boolean> {
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

	public DeleteGame(Activity currentActivity, String gameId) {
		mCurrentActivity = currentActivity;
		mGameId = gameId;
		
		mMessageToShow = mCurrentActivity.getResources().getString(R.string.game_deleted);
		mGameService = new GameService(mCurrentActivity);
		loadingDialog = new NetworkOperationProgressDialog(mCurrentActivity, this);
		
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		Boolean successful = false;
		try {
			if (mGameService.isLoggedPlayerGameOwner(mGameId)) {
				mGameService.deleteGame(mGameId);
				successful = true;
			} else {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.error_code_5);
			}
		} catch (Exception e) {
			if (mGameService.isNetworkOperationAborted()) {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.delete_game_canceled);
			} else {
				mMessageToShow = e.getLocalizedMessage();
			}
		}
		return successful;
	}

	@Override
	protected void onPostExecute(Boolean successful) {
		loadingDialog.dismiss();
		
		// TODO Change "Resource with ID ... doesn't exist" server response
		// to game_already_deleted or something similiar
		Toast.makeText(mCurrentActivity, mMessageToShow, Toast.LENGTH_SHORT).show();
		if(successful == true){
			mCurrentActivity.finish();
		}
	}
}
