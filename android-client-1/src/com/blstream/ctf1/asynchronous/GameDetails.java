package com.blstream.ctf1.asynchronous;

import java.text.SimpleDateFormat;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.blstream.ctf1.Constants;
import com.blstream.ctf1.GameDetailsActivity;
import com.blstream.ctf1.R;
import com.blstream.ctf1.dialog.NetworkOperationProgressDialog;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.list.Helper;
import com.blstream.ctf1.list.PlayersListAdapter;
import com.blstream.ctf1.service.GameService;

/**
 * @author Rafal Olichwer
 */
public class GameDetails extends AsyncTask<Void, Void, GameExtendedInfo> {

	private GameDetailsActivity mCurrentActivity;

	private String mId;

	private String mMessageToShow;

	private GameService mGameService;

	private boolean doInBackgroundSuccessful = false;

	private NetworkOperationProgressDialog loadingDialog;

	@Override
	protected void onPreExecute() {
		loadingDialog.setTitle(mCurrentActivity.getResources().getString(R.string.loading));
		loadingDialog.setMessage(mCurrentActivity.getResources().getString(R.string.loading_message));
		loadingDialog.setNetworkOperationService(mGameService);
		loadingDialog.show();
	}

	public GameDetails(GameDetailsActivity currentActivity, String id) {
		mCurrentActivity = currentActivity;
		mId = id;

		mGameService = new GameService(mCurrentActivity);
		loadingDialog = new NetworkOperationProgressDialog(mCurrentActivity, this);
	}

	@Override
	protected GameExtendedInfo doInBackground(Void... params) {
		GameExtendedInfo result = null;
		try {
			result = mGameService.getGameDetails(mId);
			result.setPlayersList(mGameService.getPlayersForGame(mId));
			doInBackgroundSuccessful = true;
			// no sense to catch others exceptions all are handled in that same
			// way
		} catch (Exception e) {
			if (mGameService.isNetworkOperationAborted()) {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.game_details_canceled);
			} else {
				mMessageToShow = e.getLocalizedMessage();
			}
		}
		return result;
	}

	@Override
	protected void onPostExecute(GameExtendedInfo result) {
		loadingDialog.dismiss();
		Toast.makeText(mCurrentActivity, mMessageToShow, Toast.LENGTH_SHORT).show();
		if (doInBackgroundSuccessful == true) {
			mCurrentActivity.mTextGameName.setText(result.getName());
			mCurrentActivity.mTextGameDescription.setText(result.getDescription());
			mCurrentActivity.mTextGameDuration.setText(Long.toString(result.getDuration()));
			mCurrentActivity.mTextLocName.setText(result.getLocalization().getName());
			mCurrentActivity.mTextLocRadius.setText(Double.toString(result.getLocalization().getRadius()));
			mCurrentActivity.mTextGameStatus.setText(result.getGameStatusType().toString());
			mCurrentActivity.mTextGameOwner.setText(result.getOwner());
			mCurrentActivity.mTextGameDate.setText(new SimpleDateFormat(Constants.DATE_FORMAT + " " + Constants.TIME_FORMAT).format(result.getTimeStart()));
			mCurrentActivity.mTextGamePlayersMax.setText(Integer.toString(result.getPlayersMax()));
			mCurrentActivity.mTextGamePointsMax.setText(Integer.toString(result.getPointsMax()));
			mCurrentActivity.mTextGameID.setText(result.getId());
			mCurrentActivity.isStatusNew(result.getGameStatusType().toString());

			mCurrentActivity.setListAdapter(new PlayersListAdapter(mCurrentActivity, result.getPlayersList()));
			ListView playersList = (ListView) mCurrentActivity.findViewById(android.R.id.list);
			Helper.getListViewSize(playersList);
		}
	}

}
