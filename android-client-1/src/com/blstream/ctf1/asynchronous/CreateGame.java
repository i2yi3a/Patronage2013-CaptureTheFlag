package com.blstream.ctf1.asynchronous;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.Constants;
import com.blstream.ctf1.ProgressDialogNetworkOperation;
import com.blstream.ctf1.R;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.domain.Localization;
import com.blstream.ctf1.service.GameService;

/**
 * @author Rafal Olichwer
 */
public class CreateGame extends AsyncTask<Void, Void, Boolean> {

	private Activity mCurrentActivity;

	private String mGameName;

	private String mDescription;

	private String mTimeStart;

	private long mDuration;

	private int mPointsMax;

	private int mPlayersMax;

	private String mLocalizationName;

	private double mLat;

	private double mLng;

	private double mRadius;

	private String mMessageToShow;
	
	private GameService mGameService;

	private ProgressDialogNetworkOperation loadingDialog;

	@Override
	protected void onPreExecute() {
		loadingDialog.setTitle(mCurrentActivity.getResources().getString(R.string.loading));
		loadingDialog.setMessage(mCurrentActivity.getResources().getString(R.string.loading_message));
		loadingDialog.setNetworkOperationService(mGameService);
		loadingDialog.show();
	}

	public CreateGame(Activity currentActivity, GameExtendedInfo gameInfo) {
		mCurrentActivity = currentActivity;
		mGameName = gameInfo.getName();
		mDescription = gameInfo.getDescription();
		Date timeStart = gameInfo.getTimeStart();
		mTimeStart = new SimpleDateFormat(Constants.DATE_FORMAT + " " + Constants.TIME_FORMAT).format(timeStart);
		mDuration = gameInfo.getDuration();
		mPointsMax = gameInfo.getPointsMax();
		mPlayersMax = gameInfo.getPlayersMax();
		Localization localization = gameInfo.getLocalization();
		mLocalizationName = localization.getName();
		mLat = localization.getLatLng().latitude;
		mLng = localization.getLatLng().longitude;
		mRadius = localization.getRadius();
		
		mMessageToShow = mCurrentActivity.getResources().getString(R.string.game_created);
		mGameService = new GameService(mCurrentActivity);
		loadingDialog = new ProgressDialogNetworkOperation(mCurrentActivity, this);
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		Boolean successful = false;
		try {
			mGameService.createGame(mGameName, mDescription, mTimeStart, mDuration, mPointsMax, mPlayersMax, mLocalizationName, mLat, mLng, mRadius);
			successful = true;
			// no sense to catch others exceptions all are handled in that same
			// way
		} catch (Exception e) {
			if (mGameService.isNetworkOperationAborted()) {
				mMessageToShow = mCurrentActivity.getResources().getString(R.string.create_game_canceled);
			} else {
				mMessageToShow = e.getLocalizedMessage();
			}
		}
		return successful;
	}

	@Override
	protected void onPostExecute(Boolean successful) {
		loadingDialog.dismiss();
		Toast.makeText(mCurrentActivity, mMessageToShow, Toast.LENGTH_SHORT).show();
		if(successful == true){
			mCurrentActivity.finish();
		}
	}

}
