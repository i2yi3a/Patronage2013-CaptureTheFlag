package com.blstream.ctf1.asynchronous;

import java.util.List;

import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.GameListActivity;
import com.blstream.ctf1.ProgressDialogNetworkOperation;
import com.blstream.ctf1.R;
import com.blstream.ctf1.domain.GameBasicInfo;
import com.blstream.ctf1.domain.GameBasicListFilter;
import com.blstream.ctf1.list.ListAdapter;
import com.blstream.ctf1.service.GameService;

/**
 * @author Adrian Swarcewicz
 */
public class GameList extends AsyncTask<Void, Void, List<GameBasicInfo>> {

	private GameListActivity mCurrentActivity;
	private GameBasicListFilter gameBasicListFilter;
	private ProgressDialogNetworkOperation mLoadingDialog;
	private GameService mGameService;
	private String mMessageToShow;
	private boolean doInBackgrounSuccessful = false;

	public GameList(GameListActivity currentActivity, GameBasicListFilter gameBasicListFilter) {
		this.mCurrentActivity = currentActivity;
		this.gameBasicListFilter = gameBasicListFilter;

		mGameService = new GameService(mCurrentActivity);
		mLoadingDialog = new ProgressDialogNetworkOperation(mCurrentActivity, this);
	}

	@Override
	protected void onPreExecute() {
		mLoadingDialog.setTitle(mCurrentActivity.getResources().getString(R.string.loading));
		mLoadingDialog.setMessage(mCurrentActivity.getResources().getString(R.string.loading_message));
		mLoadingDialog.setNetworkOperationService(mGameService);
		mLoadingDialog.show();
	}

	@Override
	protected List<GameBasicInfo> doInBackground(Void... params) {

		List<GameBasicInfo> gameBasicInfos = null;
		try {
			gameBasicInfos = mGameService.getGameList(gameBasicListFilter);
			doInBackgrounSuccessful = true;
		} catch (Exception e) {
			if (mGameService.isNetworkOperationAborted()) {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.get_game_list_canceled);
			} else {
				mMessageToShow = e.getLocalizedMessage();
			}
		}

		return gameBasicInfos;
	}

	@Override
	protected void onPostExecute(List<GameBasicInfo> result) {
		mLoadingDialog.dismiss();
		if (doInBackgrounSuccessful == false) {
			Toast.makeText(mCurrentActivity, mMessageToShow, Toast.LENGTH_SHORT).show();
		} else {
			mCurrentActivity.setListAdapter(new ListAdapter(mCurrentActivity, result));
		}
	}

}
