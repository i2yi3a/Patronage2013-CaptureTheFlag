package com.blstream.ctf1.asynchronous;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.blstream.ctf1.CreateGameActivity;
import com.blstream.ctf1.GameDetailsActivity;
import com.blstream.ctf1.R;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.service.GameService;

/**
 * @author Rafal Olichwer
 */
public class EditGameDetails extends AsyncTask<Void, Void, GameExtendedInfo> {

	private CreateGameActivity mCurrentActivity;
	
	private Class<?> mSuccessfullActivity;

	private String mId;

	private String errorString;

	private ProgressDialog loadingDialog;

	@Override
	protected void onPreExecute() {
		loadingDialog = ProgressDialog.show(mCurrentActivity, mCurrentActivity
				.getResources().getString(R.string.loading), mCurrentActivity
				.getResources().getString(R.string.loading_message));

	}

	public EditGameDetails(CreateGameActivity currentActivity, Class<?> successfullActivity,
			String id) {
		mCurrentActivity = currentActivity;
		mSuccessfullActivity = successfullActivity;
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
			Toast.makeText(mCurrentActivity, errorString, Toast.LENGTH_SHORT)
					.show();
		} else {
		//	mCurrentActivity.mEditGameName.setText(result.getName());
		//	mCurrentActivity.mEditGameDescription.setText(result.getDescription());
		//	mCurrentActivity.mEditPlayingTime.setText(Long.toString(result.getDuration()));
		//	mCurrentActivity.mEditLocationName.setText(result.getLocalization().getName());
		//	mCurrentActivity.mEditMaxPlayers.setText(Integer.toString(result.getPlayersMax()));
		//	mCurrentActivity.mEditMaxPoints.setText(Integer.toString(result.getPointsMax()));
			Intent intent = new Intent(mCurrentActivity, mSuccessfullActivity);
			mCurrentActivity.startActivity(intent);
		}
	}

}
