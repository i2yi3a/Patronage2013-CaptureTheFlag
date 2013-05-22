package com.blstream.ctf2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.blstream.ctf2.services.HttpServices;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * Player registration class
 * 
 * @author Rafal Tatol
 * @author Lukasz Dmitrowski
 */
public class RegisterActivity extends Activity {

	private ProgressDialog mProgressDialog;
	private EditText mUsernameEditText;
	private EditText mPasswordEditText;
	private EditText mRepeatPassEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage("Connecting...");
		mUsernameEditText = (EditText) findViewById(R.id.editTextUserame);
		mPasswordEditText = (EditText) findViewById(R.id.editTextPassword);
		mRepeatPassEditText = (EditText) findViewById(R.id.editTextRepeatPassword);
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}

	public void onClickRegisterButton(View v) {
		String mUsername = mUsernameEditText.getText().toString();
		String mPassword = mPasswordEditText.getText().toString();
		String mRepPass = mRepeatPassEditText.getText().toString();

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "register_click", null);

		if (isOnline() && correctData(mUsername, mPassword, mRepPass)) {
			JSONObject newPlayer = new JSONObject();
			try {
				newPlayer.put(Constants.PLAYER_USERNAME, mUsername);
				newPlayer.put(Constants.PLAYER_PASSWORD, mPassword);
			} catch (JSONException e) {
				Log.e("onClickRegisterButton JSONException", e.toString());
			}
			new RegisterPlayer().execute(newPlayer);
		}
	}

	public boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.no_internet_connection);
			builder.setPositiveButton(R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
			return false;
		}
	}

	public boolean correctData(String mUsername, String mPassword, String mRepPass) {
		if (mUsername.length() < Constants.MIN_LENGTH_LOGIN || !mPassword.equals(mRepPass) || mPassword.length() < Constants.MIN_LENGTH_PASSWORD) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.incorrect_data_description);
			builder.setTitle(R.string.incorrect_data);
			builder.setPositiveButton(R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
			return false;
		}
		return true;
	}

	public void showSuccessDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
		AlertDialog dialog;
		builder.setMessage(R.string.registration_successful);
		builder.setTitle(R.string.recorded);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				finish();
			}
		});
		dialog = builder.create();
		dialog.show();
	}

	public void showErrorDialog(int errorTitle, int errorMessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
		AlertDialog dialog;
		builder.setMessage(errorMessage);
		builder.setTitle(errorTitle);
		builder.setPositiveButton(R.string.ok, null);
		dialog = builder.create();
		dialog.show();
	}

	/**
	 * @author Rafal Tatol
	 */
	private class RegisterPlayer extends AsyncTask<JSONObject, Void, JSONObject> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (!mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}
		}

		@Override
		protected JSONObject doInBackground(JSONObject... params) {
			JSONObject playerData = params[0];
			JSONObject jsonResponse = null;
			try {
				String body = playerData.toString();
				List<Header> headers = new ArrayList<Header>();
				headers.add(new BasicHeader(Constants.ACCEPT, Constants.APPLICATION_JSON));
				headers.add(new BasicHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON));
				HttpServices httpService = new HttpServices(RegisterActivity.this);
				String response = httpService.postRequest(Constants.URI_PLAYERS_ADD, body, headers);
				jsonResponse = new JSONObject(response);
			} catch (ParseException e) {
				Log.e("PostDataTask ParseException", e.toString());
			} catch (IOException e) {
				Log.e("PostDataTask IOException", e.toString());
			} catch (JSONException e) {
				Log.e("PostDataTask JSONException", e.toString());
			} catch (Exception e) {
				Log.e("PostDataTask", e.toString());
			}
			return jsonResponse;
		}

		@Override
		protected void onPostExecute(JSONObject jsonResponse) {
			try {
				Log.i("response JSON string", jsonResponse.toString());
				int errorCode = jsonResponse.getInt(Constants.RESPONSE_ERROR_CODE);

				switch (errorCode) {
				case Constants.ERROR_CODE_SUCCESS:
					showSuccessDialog();
					break;
				case Constants.ERROR_CODE_PLAYER_ALREADY_EXISTS:
					showErrorDialog(R.string.error, R.string.player_already_exists);
					break;
				case Constants.ERROR_CODE_CANNOT_CREATE_NEW_PLAYER:
					showErrorDialog(R.string.error, R.string.cannot_create_new_player);
					break;
				default:
					showErrorDialog(R.string.unknown_error, R.string.unknown_error);
					break;
				}
			} catch (ParseException e) {
				Log.e("onPostExecute ParseException", e.toString());
			} catch (JSONException e) {
				Log.e("onPostExecute JSONException", e.toString());
			} catch (Exception e) {
				Log.e("onPostExecute Exception", e.toString());
			}

			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}

		}
	}
}