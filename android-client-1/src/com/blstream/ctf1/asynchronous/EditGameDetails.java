package com.blstream.ctf1.asynchronous;

import java.text.SimpleDateFormat;

import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.Constants;
import com.blstream.ctf1.CreateGameActivity;
import com.blstream.ctf1.R;
import com.blstream.ctf1.dialog.NetworkOperationProgressDialog;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.service.GameService;

/**
 * @author Rafal Olichwer
 */
public class EditGameDetails extends AsyncTask<Void, Void, GameExtendedInfo> {

	private CreateGameActivity mCurrentActivity;

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

	public EditGameDetails(CreateGameActivity currentActivity, String id) {
		mCurrentActivity = currentActivity;
		mId = id;
		mMessageToShow = Constants.EMPTY_STRING;
		mGameService = new GameService(mCurrentActivity);
		loadingDialog = new NetworkOperationProgressDialog(mCurrentActivity, this);
	}

	@Override
	protected GameExtendedInfo doInBackground(Void... params) {
		GameExtendedInfo result = null;
		try {
			result = mGameService.getGameDetails(mId);
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
		if(!mMessageToShow.isEmpty())
			Toast.makeText(mCurrentActivity, mMessageToShow, Toast.LENGTH_SHORT).show();
		if (doInBackgroundSuccessful == true) {
			mCurrentActivity.mEditGameName.setText(result.getName());
			mCurrentActivity.mEditGameDescription.setText(result.getDescription());
			mCurrentActivity.mEditPlayingTime.setText(Long.toString(result.getDuration()));
			mCurrentActivity.mEditLocationName.setText(result.getLocalization().getName());
			mCurrentActivity.mEditMaxPlayers.setText(Integer.toString(result.getPlayersMax()));
			mCurrentActivity.mEditMaxPoints.setText(Integer.toString(result.getPointsMax()));
			mCurrentActivity.mBtnStartDate.setText(new SimpleDateFormat(Constants.DATE_FORMAT).format(result.getTimeStart()));
			mCurrentActivity.mBtnStartTime.setText(new SimpleDateFormat(Constants.TIME_FORMAT).format(result.getTimeStart()));
			mCurrentActivity.latitude = result.getLocalization().getLatLng().latitude;
			mCurrentActivity.longitude = result.getLocalization().getLatLng().longitude;
			mCurrentActivity.radius = result.getLocalization().getRadius();
		}
	}

}
