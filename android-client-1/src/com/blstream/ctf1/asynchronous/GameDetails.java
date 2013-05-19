package com.blstream.ctf1.asynchronous;

import java.text.SimpleDateFormat;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.Constants;
import com.blstream.ctf1.GameDetailsActivity;
import com.blstream.ctf1.R;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.service.GameService;

/**
 * @author Rafal Olichwer
 */
public class GameDetails extends AsyncTask<Void, Void, GameExtendedInfo> {

	private GameDetailsActivity mCurrentActivity;

	private String mId;

	private String errorString;

	private ProgressDialog loadingDialog;

	@Override
	protected void onPreExecute() {
		loadingDialog = ProgressDialog.show(mCurrentActivity, mCurrentActivity.getResources().getString(R.string.loading), mCurrentActivity.getResources()
				.getString(R.string.loading_message));

	}

	public GameDetails(GameDetailsActivity currentActivity, String id) {
		mCurrentActivity = currentActivity;
		mId = id;
	}

	@Override
	protected GameExtendedInfo doInBackground(Void... params) {
		GameService gameService = new GameService(mCurrentActivity);
		GameExtendedInfo result = null;
		try {
			result = gameService.getGameDetails(mId);

			// no sense to catch others exceptions all are handled in that same
			// way
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
		}
		return result;
	}

	@Override
	protected void onPostExecute(GameExtendedInfo result) {
		loadingDialog.dismiss();
		if (errorString != null) {
			Toast.makeText(mCurrentActivity, errorString, Toast.LENGTH_SHORT).show();
		} else {
			mCurrentActivity.mTextGameName.setText(result.getName());
			mCurrentActivity.mTextGameDescription.setText(result.getDescription());
			mCurrentActivity.mTextGameDuration.setText(Long.toString(result.getDuration()));
			mCurrentActivity.mTextLocName.setText(result.getLocalization().getName());
			mCurrentActivity.mTextLocRadius.setText(Long.toString(result.getLocalization().getRadius()));
			mCurrentActivity.mTextGameStatus.setText(result.getGameStatusType().toString());
			mCurrentActivity.mTextGameOwner.setText(result.getOwner());
			mCurrentActivity.mTextGameDate.setText(new SimpleDateFormat(Constants.DATE_FORMAT + " " + Constants.TIME_FORMAT).format(result.getTimeStart()));
			mCurrentActivity.mTextGamePlayersMax.setText(Integer.toString(result.getPlayersMax()));
			mCurrentActivity.mTextGamePointsMax.setText(Integer.toString(result.getPointsMax()));
			mCurrentActivity.mTextGameID.setText(result.getId());
			mCurrentActivity.isStatusNew(result.getGameStatusType().toString());
		}
	}

}
