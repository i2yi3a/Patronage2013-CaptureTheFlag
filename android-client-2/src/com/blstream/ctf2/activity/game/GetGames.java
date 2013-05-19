package com.blstream.ctf2.activity.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.services.HttpServices;
import com.blstream.ctf2.services.UserServices;
import com.blstream.ctf2.storage.entity.Game;

/**
 * @author Marcin Sareło
 */
public class GetGames extends AsyncTask<String, Void, JSONArray> {
	public final static String NAME = "name";
	public final static String STATUS = "status";
	public final static String OWNER = "owner";

	private GameListActivity mGameListActivity;
	private ProgressDialog mProgressDialog;

	public GetGames(GameListActivity gameListActivity) {
		mGameListActivity = gameListActivity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog = new ProgressDialog(mGameListActivity);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage("Connecting...");
		mProgressDialog.show();
	}

	@Override
	protected JSONArray doInBackground(String... params) {
		JSONArray result = null;
		String response = getGames();
		try {
			result = new JSONArray(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(JSONArray jsonResult) {
		super.onPostExecute(jsonResult);
		try {
			if (jsonResult != null && jsonResult.length() > 0) {
				List<Game> games = getGamesFromJson(jsonResult);
				fillGameListActivity(games);
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(mGameListActivity);
				AlertDialog dialog;
				builder.setMessage("Game list is empty");
				builder.setTitle(Constants.ERROR);
				builder.setPositiveButton(R.string.ok, null);
				dialog = builder.create();
				dialog.show();
			}
		} catch (Exception e) {
			Log.e("onPostExecute Exception", e.toString());
		}
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	public void fillGameListActivity(List<Game> games) {
		mGameListActivity.setGames(games);

	}

	public String getGames() {
		String result = null;
		UserServices userServices = new UserServices(mGameListActivity);

		try {
			List<Header> headers = new ArrayList<Header>();
			headers.add(new BasicHeader("Accept", "application/json"));
			headers.add(new BasicHeader("Content-Type", "application/json"));
			headers.add(new BasicHeader("Authorization", userServices.getUser().getTokenType() + " " + userServices.getUser().getToken()));
			HttpServices httpService = new HttpServices(mGameListActivity);

			result = httpService.getRequest(Constants.URI_GAMES, headers);
			Log.i("getGames result: ", result);
		} catch (Exception e) {
			Log.e("getGames ERROR", e.toString());
		}
		return result;
	}

	public List<Game> getGamesFromJson(JSONArray jsonArray) {

		List<Game> games = new ArrayList<Game>();

		for (int i = 0; i < 6; i++) {
			Game game = new Game();
			JSONObject jsonObject;

			try {
				jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.has("name")) {

					game.setId(jsonObject.getString(Constants.ID));
					game.setName(jsonObject.getString("name"));
					game.setStatus(jsonObject.getString(STATUS));
					game.setOwner(jsonObject.getString(OWNER));
					games.add(game);
				}
			} catch (JSONException e) {
				Log.e("getGame ERROR", e.toString());
			}

		}

		return games;
	}
}