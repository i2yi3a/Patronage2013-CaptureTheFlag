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
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.blstream.ctf2.services.HttpServices;

/**
 * @author Rafal Tatol
 * 
 */
public class Register extends AsyncTask<JSONObject, Void, JSONObject> {

	private Context mContext;
	private ProgressDialog mProgressDialog;

	public Register(Context ctx) {
		mContext = ctx;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage(mContext.getResources().getString(R.string.connecting));
		mProgressDialog.show();
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
			HttpServices httpService = new HttpServices(mContext);
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

	public void showSuccessDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		AlertDialog dialog;
		builder.setMessage(R.string.registration_successful);
		builder.setTitle(R.string.recorded);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = ((Activity) mContext).getIntent();
				((Activity) mContext).finish();
				((Activity) mContext).startActivity(intent);
			}
		});
		dialog = builder.create();
		dialog.show();
	}

	public void showErrorDialog(int errorTitle, int errorMessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		AlertDialog dialog;
		builder.setMessage(errorMessage);
		builder.setTitle(errorTitle);
		builder.setPositiveButton(R.string.ok, null);
		dialog = builder.create();
		dialog.show();
	}

}
