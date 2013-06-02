package com.blstream.ctf1.asynchronous;

import java.util.List;

import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.GameListActivity;
import com.blstream.ctf1.R;
import com.blstream.ctf1.dialog.NetworkOperationProgressDialog;
import com.blstream.ctf1.domain.GameBasicListFilter;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.list.ListAdapter;
import com.blstream.ctf1.service.GameService;

/**
 * @author Adrian Swarcewicz
 */
public class GameListByDetails extends AsyncTask<Void, Void, List<GameExtendedInfo>> {

	private GameListActivity mCurrentActivity;
	private GameBasicListFilter gameBasicListFilter;
	private NetworkOperationProgressDialog mLoadingDialog;
	private GameService mGameService;
	private String mMessageToShow;
	private boolean doInBackgrounSuccessful = false;

	public GameListByDetails(GameListActivity currentActivity, GameBasicListFilter gameBasicListFilter) {
		this.mCurrentActivity = currentActivity;
		this.gameBasicListFilter = gameBasicListFilter;

		mGameService = new GameService(mCurrentActivity);
		mLoadingDialog = new NetworkOperationProgressDialog(mCurrentActivity, this);
	}

	@Override
	protected void onPreExecute() {
		mLoadingDialog.setTitle(mCurrentActivity.getResources().getString(R.string.loading));
		mLoadingDialog.setMessage(mCurrentActivity.getResources().getString(R.string.loading_message));
		mLoadingDialog.setNetworkOperationService(mGameService);
		mLoadingDialog.show();
	}

	@Override
	protected List<GameExtendedInfo> doInBackground(Void... params) {

		List<GameExtendedInfo> gameBasicInfos = null;
		try {
			gameBasicInfos = mGameService.getGameDetailList(gameBasicListFilter);
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
	protected void onPostExecute(List<GameExtendedInfo> result) {
		mLoadingDialog.dismiss();
		if (doInBackgrounSuccessful == false) {
			Toast.makeText(mCurrentActivity, mMessageToShow, Toast.LENGTH_SHORT).show();
		} else {
			mCurrentActivity.setListAdapter(new ListAdapter(mCurrentActivity, result));
		}
	}

}
