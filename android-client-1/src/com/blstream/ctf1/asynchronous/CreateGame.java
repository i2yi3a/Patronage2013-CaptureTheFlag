package com.blstream.ctf1.asynchronous;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.Constants;
import com.blstream.ctf1.R;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.domain.Localization;
import com.blstream.ctf1.service.GameService;

/**
 * @author Rafal Olichwer
 */
public class CreateGame extends AsyncTask<Void, Void, Void> {

	private Activity mCurrentActivity;

	private Class<?> mSuccessfullActivity;

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

	private String errorString;

	private ProgressDialog loadingDialog;

	@Override
	protected void onPreExecute() {
		loadingDialog = ProgressDialog.show(mCurrentActivity, mCurrentActivity.getResources().getString(R.string.loading), mCurrentActivity.getResources()
				.getString(R.string.loading_message));

	}

	public CreateGame(Activity currentActivity, Class<?> successfullActivity, GameExtendedInfo gameInfo) {
		mCurrentActivity = currentActivity;
		mSuccessfullActivity = successfullActivity;
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
	}

	@Override
	protected Void doInBackground(Void... params) {
		GameService gameService = new GameService(mCurrentActivity);
		try {
			gameService.createGame(mGameName, mDescription, mTimeStart, mDuration, mPointsMax, mPlayersMax, mLocalizationName, mLat, mLng, mRadius);
			// no sense to catch others exceptions all are handled in that same
			// way
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		loadingDialog.dismiss();
		if (errorString != null) {
			Toast.makeText(mCurrentActivity, errorString, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mCurrentActivity, R.string.game_created, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(mCurrentActivity, mSuccessfullActivity);
			mCurrentActivity.startActivity(intent);
		}
	}

}
