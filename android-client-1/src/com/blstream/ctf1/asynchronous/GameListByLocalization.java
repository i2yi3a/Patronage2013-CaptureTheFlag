package com.blstream.ctf1.asynchronous;

/**
 * @author Mateusz Wisniewski
 */

import java.util.List;

import com.blstream.ctf1.GameListActivity;
import com.blstream.ctf1.R;
import com.blstream.ctf1.dialog.NetworkOperationProgressDialog;
import com.blstream.ctf1.domain.GameBasicInfo;
import com.blstream.ctf1.domain.GameLocalizationListFilter;
import com.blstream.ctf1.list.ListAdapter;
import com.blstream.ctf1.service.GameService;

import android.os.AsyncTask;
import android.widget.Toast;

public class GameListByLocalization extends AsyncTask<Void, Void, List<GameBasicInfo>>{

	private GameListActivity mCurrentActivity;
	private GameLocalizationListFilter gameLocalizationListFilter;
	private NetworkOperationProgressDialog mLoadingDialog;
	private GameService mGameService;
	private String mMessageToShow;
	private boolean doInBackgrounSuccessful = false;
	
	public GameListByLocalization(GameListActivity currentActivity, GameLocalizationListFilter gLLF) {
		this.mCurrentActivity = currentActivity;
		this.gameLocalizationListFilter = gLLF;
		
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
	protected List<GameBasicInfo> doInBackground(Void... params) {
		
		List<GameBasicInfo> gameBasicInfo = null;
		try {
			gameBasicInfo = mGameService.getGameListByLocalization(gameLocalizationListFilter);
			doInBackgrounSuccessful = true;
		} catch (Exception e) {
			if (mGameService.isNetworkOperationAborted()) {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.get_game_list_canceled);
			} else {
				mMessageToShow = e.getLocalizedMessage();
			}
		}
		return gameBasicInfo;
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

