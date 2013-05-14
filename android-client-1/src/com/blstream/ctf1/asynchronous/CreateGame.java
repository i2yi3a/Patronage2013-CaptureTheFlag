package com.blstream.ctf1.asynchronous;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.blstream.ctf1.R;
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

	private int mRadius;

	private String errorString;

	private ProgressDialog loadingDialog;

	@Override
	protected void onPreExecute() {
		loadingDialog = ProgressDialog.show(mCurrentActivity, mCurrentActivity
				.getResources().getString(R.string.loading), mCurrentActivity
				.getResources().getString(R.string.loading_message));

	}

	public CreateGame(Activity currentActivity, Class<?> successfullActivity,
			String gameName, String description, String timeStart,
			long duration, int pointsMax, int playersMax,
			String localizationName, double lat, double lng, int radius) {
		mCurrentActivity = currentActivity;
		mSuccessfullActivity = successfullActivity;
		mGameName = gameName;
		mDescription = description;
		mTimeStart = timeStart;
		mDuration = duration;
		mPointsMax = pointsMax;
		mPlayersMax = playersMax;
		mLocalizationName = localizationName;
		mLat = lat;
		mLng = lng;
		mRadius = radius;
	}

	@Override
	protected Void doInBackground(Void... params) {
		GameService gameService = new GameService(mCurrentActivity);
		try {
			gameService.createGame(mGameName, mDescription, mTimeStart,
					mDuration, mPointsMax, mPlayersMax, mLocalizationName,
					mLat, mLng, mRadius);

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
			Toast.makeText(mCurrentActivity, errorString, Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(mCurrentActivity, R.string.game_created,
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(mCurrentActivity, mSuccessfullActivity);
			mCurrentActivity.startActivity(intent);
		}
	}

}
