package com.blstream.ctf1.asynchronous;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.GameListActivity;
import com.blstream.ctf1.R;
import com.blstream.ctf1.domain.GameBasicInfo;
import com.blstream.ctf1.list.ListAdapter;
import com.blstream.ctf1.service.GameService;

/**
 * @author Adrian Swarcewicz
 */
public class GameList extends AsyncTask<Void, Void, List<GameBasicInfo>> {

	private ProgressDialog mLoadingDialog;

	private GameListActivity mCurrentActivity;

	private String errorString;

	public GameList(GameListActivity currentActivity) {
		super();
		this.mCurrentActivity = currentActivity;
	}

	@Override
	protected void onPreExecute() {
		mLoadingDialog = ProgressDialog.show(mCurrentActivity, mCurrentActivity.getResources().getString(R.string.loading), mCurrentActivity.getResources()
				.getString(R.string.loading_message));
	}

	@Override
	protected List<GameBasicInfo> doInBackground(Void... params) {
		GameService gameService = new GameService(mCurrentActivity);

		List<GameBasicInfo> gameBasicInfos = null;
		try {
			// TODO: FILTER
			gameBasicInfos = gameService.getGameList(null);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
		}

		return gameBasicInfos;
	}

	@Override
	protected void onPostExecute(List<GameBasicInfo> result) {
		mLoadingDialog.dismiss();
		if (errorString != null) {
			Toast.makeText(mCurrentActivity, errorString, Toast.LENGTH_SHORT).show();
		} else {
			mCurrentActivity.setListAdapter(new ListAdapter(mCurrentActivity, result));
		}
	}

}
