package com.blstream.ctf1.asynchronous;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.blstream.ctf1.GameDetailsActivity;
import com.blstream.ctf1.R;
import com.blstream.ctf1.list.Helper;
import com.blstream.ctf1.list.PlayersListAdapter;
import com.blstream.ctf1.service.GameService;

/**
 * @author Rafal Olichwer
 */
public class RefreshPlayersList extends AsyncTask<Void, Void, List<String>> {

	private GameDetailsActivity mCurrentActivity;

	private String mId;

	private String errorString;

	private ProgressDialog loadingDialog;

	@Override
	protected void onPreExecute() {
		loadingDialog = ProgressDialog.show(mCurrentActivity, mCurrentActivity.getResources().getString(R.string.loading), mCurrentActivity.getResources()
				.getString(R.string.loading_message));

	}

	public RefreshPlayersList(GameDetailsActivity currentActivity, String id) {
		mCurrentActivity = currentActivity;
		mId = id;
	}

	@Override
	protected List<String> doInBackground(Void... params) {
		GameService gameService = new GameService(mCurrentActivity);
		List<String> result = null;
		try {
			result = gameService.getPlayersForGame(mId);

			// no sense to catch others exceptions all are handled in that same
			// way
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
		}
		return result;
	}

	@Override
	protected void onPostExecute(List<String> result) {
		loadingDialog.dismiss();
		if (errorString != null) {
			Toast.makeText(mCurrentActivity, errorString, Toast.LENGTH_SHORT).show();
		} else {
			
			 mCurrentActivity.setListAdapter(new
			 PlayersListAdapter(mCurrentActivity,
			 result));
			 ListView playersList = (ListView) mCurrentActivity.findViewById(android.R.id.list);
			 Helper.getListViewSize(playersList);
		}
	}

}
