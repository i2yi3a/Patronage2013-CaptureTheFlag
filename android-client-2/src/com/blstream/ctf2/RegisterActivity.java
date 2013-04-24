package com.blstream.ctf2;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Player registration class
 * 
 * @author Rafal Tatol
 */
public class RegisterActivity extends Activity {
	
	private EditText mUsernameEditText;
	private EditText mPasswordEditText;
	private EditText mRepeatPassEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
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
		
		if (isOnline() && correctData(mUsername, mPassword, mRepPass)) {
			JSONObject newPlayer = new JSONObject();
			try {
				newPlayer.put(Constants.PLAYER_USERNAME, mUsername);
				newPlayer.put(Constants.PLAYER_PASSWORD, mPassword);
			} catch (JSONException e) {
				Log.e("onClickRegisterButton JSONException", e.toString());
			}
			new PostDataTask().execute(newPlayer);
		}
	}

	public boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			Toast.makeText(getApplicationContext(), "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
			return false;
		}
	}

	public boolean correctData(String mUsername, String mPassword, String mRepPass) {
		if (mUsername.length() < 5 || !mPassword.equals(mRepPass) || mPassword.length() < 5) {
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

	private class PostDataTask extends AsyncTask<JSONObject, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(JSONObject... params) {		
			JSONObject jsonObject = params[0];
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.URL_SERVER + Constants.URI_PLAYERS_ADD);
			HttpResponse response = null;
			String jsonString = null;
			JSONObject jsonResponse = null;
			try {
				StringEntity se = new StringEntity(jsonObject.toString());
				httppost.setEntity(se);
				httppost.setHeader("Accept", "application/json");
				httppost.setHeader("Content-type", "application/json");
				response = httpclient.execute(httppost);
				jsonString = EntityUtils.toString(response.getEntity());
				jsonResponse = new JSONObject(jsonString);
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

				AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
				AlertDialog dialog;

				switch (errorCode) {
				case 0:
					builder.setMessage(R.string.registration_successful);
					builder.setTitle(R.string.recorded);
					builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							finish();
						}
					});
					dialog = builder.create();
					dialog.show();
					break;
				case 2:
				case 101: //someone from the Back-End changed the "error_code":2 to "error_code":101, so I leave temporarily
					String errorDescription = jsonResponse.getString(Constants.RESPONSE_DESCRIPTION);
					builder.setMessage(errorDescription);
					builder.setTitle(R.string.player_already_exists);
					builder.setPositiveButton(R.string.ok, null);
					dialog = builder.create();
					dialog.show();
					break;
				default:
					String unknownError = "error_code" + errorCode;
					builder.setMessage(unknownError);
					builder.setTitle(unknownError);
					builder.setPositiveButton(R.string.ok, null);
					dialog = builder.create();
					dialog.show();
					break;
				}
			} catch (ParseException e) {
				Log.e("onPostExecute ParseException", e.toString());
			} catch (JSONException e) {
				Log.e("onPostExecute JSONException", e.toString());
			} catch (Exception e) {
				Log.e("onPostExecute Exception", e.toString());
			}
		}
	}
}