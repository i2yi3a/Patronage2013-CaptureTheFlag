package com.blstream.ctf1.asynchronous;

import java.util.List;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.blstream.ctf1.GameDetailsActivity;
import com.blstream.ctf1.R;
import com.blstream.ctf1.dialog.NetworkOperationProgressDialog;
import com.blstream.ctf1.list.Helper;
import com.blstream.ctf1.list.PlayersListAdapter;
import com.blstream.ctf1.service.GameService;

/**
 * @author Rafal Olichwer
 */
public class RefreshPlayersList extends AsyncTask<Void, Void, List<String>> {

	private GameDetailsActivity mCurrentActivity;

	private String mId;

	private String mMessageToShow;

	private GameService mGameService;

	private Boolean doInBackgroundSuccessful = false;

	private NetworkOperationProgressDialog loadingDialog;

	@Override
	protected void onPreExecute() {
		loadingDialog.setTitle(mCurrentActivity.getResources().getString(R.string.loading));
		loadingDialog.setMessage(mCurrentActivity.getResources().getString(R.string.loading_message));
		loadingDialog.setNetworkOperationService(mGameService);
		loadingDialog.show();
	}

	public RefreshPlayersList(GameDetailsActivity currentActivity, String id) {
		mCurrentActivity = currentActivity;
		mId = id;

		mGameService = new GameService(mCurrentActivity);
		loadingDialog = new NetworkOperationProgressDialog(mCurrentActivity, this);
	}

	@Override
	protected List<String> doInBackground(Void... params) {
		List<String> result = null;

		try {
			result = mGameService.getPlayersForGame(mId);
			doInBackgroundSuccessful = true;
			// no sense to catch others exceptions all are handled in that same
			// way
		} catch (Exception e) {
			if (mGameService.isNetworkOperationAborted()) {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.game_leave_canceled);
			} else {
				mMessageToShow = e.getLocalizedMessage();
			}
		}
		return result;
	}

	@Override
	protected void onPostExecute(List<String> result) {
		loadingDialog.dismiss();
		Toast.makeText(mCurrentActivity, mMessageToShow, Toast.LENGTH_SHORT).show();
		if (doInBackgroundSuccessful == true) {

			mCurrentActivity.setListAdapter(new PlayersListAdapter(mCurrentActivity, result));
			ListView playersList = (ListView) mCurrentActivity.findViewById(android.R.id.list);
			Helper.getListViewSize(playersList);
		}
	}

}
