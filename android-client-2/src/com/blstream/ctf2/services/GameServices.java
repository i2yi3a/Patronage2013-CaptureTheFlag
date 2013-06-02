package com.blstream.ctf2.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.activity.game.GameDetailsActivity;
import com.blstream.ctf2.activity.game.GameListActivity;
import com.blstream.ctf2.domain.GameDetails;
import com.blstream.ctf2.domain.GameLocalization;
import com.blstream.ctf2.domain.Localization;
import com.blstream.ctf2.domain.Team;
import com.blstream.ctf2.storage.entity.Game;

/**
 * 
 * @author Marcin Sareło [mod] Rafal Tatol
 * 
 */
public class GameServices extends Services {

	public final static String NAME = "name";
	public final static String DESCRIPTION = "description";
	public final static String DURATION = "duration";
	public final static String STATUS = "status";
	public final static String OWNER = "owner";
	public final static String TIME_START = "time_start";
	public final static String POINTS_MAX = "points_max";
	public final static String PLAYERS_MAX = "players_max";
	public final static String LOCALIZATION = "localization";
	public final static String LAT = "latitude";
	public final static String LNG = "longitude";
	public final static String RADIUS = "radius";
	public final static String LAT_LNG = "latLng";
	public final static String RED_TEAM_BASE = "red_team_base";
	public final static String BLUE_TEAM_BASE = "blue_team_base";
	public final static String GAME_LIST_EMPTY = "Game list is empty";
	public final static String GAMES = "games";
	public final static String PLAYERS = "players";

	private String playerToken = "";
	private String typeToken = "";

	public GameServices(Context mCtx) {
		UserServices userServices = new UserServices(mCtx);
		this.playerToken = userServices.getUser().getToken();
		this.typeToken = userServices.getUser().getTokenType();
		Log.i("my token ", this.playerToken);
	}

	public void getGames(GameListActivity gameListActivity) {
		GetGames getGamesTask = new GetGames(gameListActivity);
		getGamesTask.execute("");
	}

	public void getGameDetails(GameDetailsActivity gameDetailsTabsActivity, String mGameId) {
		GetGameDetails getGameDetailsTask = new GetGameDetails(gameDetailsTabsActivity);
		getGameDetailsTask.execute(mGameId);
	}

	public void getGamePlayers(GameDetailsActivity gameDetailsTabsActivity, String mGameId) {
		GetGamePlayers getGamePlayersTask = new GetGamePlayers(gameDetailsTabsActivity);
		getGamePlayersTask.execute(mGameId);
	}

	public void joinTheGame(GameDetailsActivity mGameDetailsActivity, String mGameId) {
		JoinTheGame joinTheGame = new JoinTheGame(mGameDetailsActivity);
		joinTheGame.execute(mGameId);
	}

	public void createGame(Activity activityForCtx, JSONObject params) {
		CreateGameTask createGameTask = new CreateGameTask(activityForCtx);
		createGameTask.execute(params);
	}

	public void editGame(Activity activityForCtx, JSONObject params) {
		EditGameTask editGameTask = new EditGameTask(activityForCtx);
		editGameTask.execute(params);
	}

	/**
	 * 
	 * @author Rafal Tatol [mod] Marcin Sareło
	 */
	private class GetGameDetails extends AsyncTask<String, Void, JSONObject> {

		private GameDetailsActivity mGameDetailsTabsActivity;
		private ProgressDialog mProgressDialog;

		public GetGameDetails(GameDetailsActivity gameDetailsTabsActivity) {
			mGameDetailsTabsActivity = gameDetailsTabsActivity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(mGameDetailsTabsActivity);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setMessage(mGameDetailsTabsActivity.getResources().getString(R.string.connecting));
			mProgressDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			JSONObject result = null;
			String response = getGameDetails(params[0]);
			try {
				result = new JSONObject(response);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(JSONObject jsonResult) {
			super.onPostExecute(jsonResult);
			GameDetails gameDetails = null;
			try {
				if (!jsonResult.has(Constants.ERROR)) {
					gameDetails = jsonToGameDetails(jsonResult);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(mGameDetailsTabsActivity);
					AlertDialog dialog;
					builder.setMessage(jsonResult.getString(Constants.ERROR_DESCRIPTION));
					builder.setTitle(jsonResult.getString(Constants.ERROR));
					builder.setPositiveButton(R.string.ok, null);
					dialog = builder.create();
					dialog.show();
				}
			} catch (JSONException e) {
				Log.e("onPostExecute JSONException", e.toString());
			} catch (Exception e) {
				Log.e("onPostExecute Exception", e.toString());
			}
			fillGameDetailsActivity(gameDetails);
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
		}
		
		public void fillGameDetailsActivity(GameDetails gameDetails) {
			mGameDetailsTabsActivity.setGameDetails(gameDetails);
		}

		public GameDetails jsonToGameDetails(JSONObject jsonObject) throws JSONException {

			GameDetails gameDetails = new GameDetails();
			gameDetails.setId(jsonObject.getString(Constants.ID));
			gameDetails.setName(jsonObject.getString(NAME));
			gameDetails.setDescription(jsonObject.getString(DESCRIPTION));
			gameDetails.setDuration(jsonObject.getInt(DURATION));
			gameDetails.setStatus(jsonObject.getString(STATUS));
			gameDetails.setOwner(jsonObject.getString(OWNER));
			gameDetails.setTimeStart(jsonObject.getString(TIME_START));
			gameDetails.setPointsMax(jsonObject.getInt(POINTS_MAX));
			gameDetails.setPlayersMax(jsonObject.getInt(PLAYERS_MAX));

			GameLocalization localization = new GameLocalization();
			JSONObject jsonLocalization = jsonObject.getJSONObject(LOCALIZATION);
			localization.setName(jsonLocalization.getString(NAME));
			localization.setRadius(jsonLocalization.getInt(RADIUS));
			JSONArray jsonLatLng = jsonLocalization.getJSONArray(LAT_LNG);
			localization.setLat(jsonLatLng.getDouble(0));
			localization.setLng(jsonLatLng.getDouble(1));
			gameDetails.setLocalization(localization);

			Team teamRed = new Team();
			JSONObject jsonTeamRed = jsonObject.getJSONObject(RED_TEAM_BASE);
			teamRed.setName(jsonTeamRed.getString(NAME));
			Localization localizationTeamRed = new Localization();
			JSONArray jsonLatLngRed = jsonTeamRed.getJSONArray(LAT_LNG);
			localizationTeamRed.setLat(jsonLatLngRed.getDouble(0));
			localizationTeamRed.setLng(jsonLatLngRed.getDouble(1));
			teamRed.setBaseLocalization(localizationTeamRed);
			gameDetails.setTeamRed(teamRed);

			Team teamBlue = new Team();
			JSONObject jsonTeamBlue = jsonObject.getJSONObject(BLUE_TEAM_BASE);
			teamBlue.setName(jsonTeamBlue.getString(NAME));
			Localization localizationTeamBlue = new Localization();
			JSONArray jsonLatLngBlue = jsonTeamBlue.getJSONArray(LAT_LNG);
			localizationTeamBlue.setLat(jsonLatLngBlue.getDouble(0));
			localizationTeamBlue.setLng(jsonLatLngBlue.getDouble(1));
			teamBlue.setBaseLocalization(localizationTeamBlue);
			gameDetails.setTeamBlue(teamBlue);

			return gameDetails;
		}

		public String getGameDetails(String gameId) {
			String result = null;
			try {
				List<Header> headers = new ArrayList<Header>();
				headers.add(new BasicHeader(Constants.ACCEPT, Constants.APPLICATION_JSON));
				headers.add(new BasicHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON));
				headers.add(new BasicHeader(Constants.AUTHORIZATION, typeToken + " " + playerToken));

				HttpServices httpService = new HttpServices(mGameDetailsTabsActivity);
				result = httpService.getRequest(Constants.URI_GAMES + "/" + gameId, headers);
				Log.i("getGameDetails result: ", result);
			} catch (Exception e) {
				Log.e("getGameDetails ERROR", e.toString());
			}
			return result;
		}
	}

	/**
	 * 
	 * @author Marcin Sareło [mod] Rafal Tatol
	 */
	private class GetGames extends AsyncTask<String, Void, JSONArray> {

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
			mProgressDialog.setMessage(mGameListActivity.getResources().getString(R.string.connecting));
			mProgressDialog.show();
		}

		@Override
		protected JSONArray doInBackground(String... params) {
			JSONArray result = null;
			String response = getGames();
			try {
				JSONObject jsonResponse = new JSONObject(response);
				result = jsonResponse.getJSONArray(GAMES);
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
					builder.setMessage(GAME_LIST_EMPTY);
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
			try {
				List<Header> headers = new ArrayList<Header>();
				headers.add(new BasicHeader(Constants.ACCEPT, Constants.APPLICATION_JSON));
				headers.add(new BasicHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON));
				headers.add(new BasicHeader(Constants.AUTHORIZATION, typeToken + " " + playerToken));
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

			for (int i = 0; i < jsonArray.length(); i++) {
				Game game = new Game();
				JSONObject jsonObject;
				try {
					jsonObject = jsonArray.getJSONObject(i);
					game.setId(jsonObject.getString(Constants.ID));
					game.setName(jsonObject.getString(NAME));
					game.setStatus(jsonObject.getString(STATUS));
					game.setOwner(jsonObject.getString(OWNER));
				} catch (JSONException e) {
					Log.e("getGame ERROR", e.toString());
				}
				games.add(game);
			}
			return games;
		}
	}

	/**
	 * 
	 * @author Rafal Tatol
	 */
	private class GetGamePlayers extends AsyncTask<String, Void, String> {

		private GameDetailsActivity mGameDetailsTabsActivity;
		private ProgressDialog mProgressDialog;

		public GetGamePlayers(GameDetailsActivity gameDetailsTabsActivity) {
			mGameDetailsTabsActivity = gameDetailsTabsActivity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(mGameDetailsTabsActivity);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setMessage(mGameDetailsTabsActivity.getResources().getString(R.string.connecting));
			mProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String response = null;
			response = getGamePlayers(params[0]);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				JSONObject jsonResponse = new JSONObject(result);
				JSONArray jsonArray = jsonResponse.getJSONArray(PLAYERS);
				fillPlayersList(jsonArray);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
		}

		public String getGamePlayers(String gameId) {
			String result = null;
			try {
				List<Header> headers = new ArrayList<Header>();
				headers.add(new BasicHeader(Constants.ACCEPT, Constants.APPLICATION_JSON));
				headers.add(new BasicHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON));
				headers.add(new BasicHeader(Constants.AUTHORIZATION, typeToken + " " + playerToken));

				HttpServices httpService = new HttpServices(mGameDetailsTabsActivity);
				result = httpService.getRequest(Constants.URI_GAMES + "/" + gameId + Constants.PLAYERS, headers);
				Log.i("getGamePlayers result: ", result);
			} catch (Exception e) {
				Log.e("getGamePlayers ERROR", e.toString());
			}
			return result;
		}

		public void fillPlayersList(JSONArray jsonArray) throws JSONException {
			ArrayList<String> playersList = new ArrayList<String>();
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					playersList.add(jsonArray.get(i).toString());
				}
			}
			Log.i("playersList.size = ", String.valueOf(playersList.size()));
			mGameDetailsTabsActivity.showPlayersFragment(playersList);
		}
	}

	/**
	 * 
	 * @author Marcin Sareło
	 */
	private class JoinTheGame extends AsyncTask<String, Void, String> {

		private GameDetailsActivity mGameDetailsActivity;
		private ProgressDialog mProgressDialog;

		public JoinTheGame(GameDetailsActivity gameDetailsActivity) {
			mGameDetailsActivity = gameDetailsActivity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(mGameDetailsActivity);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setMessage("Joining the game...");
			mProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String response = null;
			response = joinTheGameRequest(params[0]);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject jsonResult;
			try {
				jsonResult = new JSONObject(result);
				if (!jsonResult.has(Constants.ERROR)) {
					Toast.makeText(mGameDetailsActivity, R.string.game_sign_in, Toast.LENGTH_SHORT).show();

				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(mGameDetailsActivity);
					AlertDialog dialog;
					builder.setMessage(jsonResult.getString(Constants.ERROR_DESCRIPTION));
					builder.setTitle(jsonResult.getString(Constants.ERROR));
					builder.setPositiveButton(R.string.ok, null);
					dialog = builder.create();
					dialog.show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
		}

		public String joinTheGameRequest(String gameId) {
			String result = null;
			try {
				List<Header> headers = new ArrayList<Header>();
				headers.add(new BasicHeader(Constants.ACCEPT, Constants.APPLICATION_JSON));
				headers.add(new BasicHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON));
				headers.add(new BasicHeader(Constants.AUTHORIZATION, typeToken + " " + playerToken));

				HttpServices httpService = new HttpServices(mGameDetailsActivity);
				result = httpService.putRequest(Constants.URI_GAMES + "/" + gameId + Constants.SIGN_IN, headers);
				Log.i("joinTheGame result: ", result);
			} catch (Exception e) {
				Log.e("joinTheGame ERROR", e.toString());
			}
			return result;
		}
	}

	/**
	 * @author Lukasz Dmitrowski
	 */

	private class CreateGameTask extends AsyncTask<JSONObject, Void, JSONObject> {
		private HttpServices mHttpServices;
		private Context mCtx;
		private DialogServices mDialog;

		public CreateGameTask(Activity activityForCtx) {
			mCtx = activityForCtx;
			mDialog = new DialogServices(mCtx);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog.showConnectingDialog();
		}

		@Override
		protected JSONObject doInBackground(JSONObject... params) {
			JSONObject gameData = params[0];
			JSONObject jsonResponse = null;
			String body = gameData.toString();
			List<Header> headersList = new LinkedList<Header>();
			headersList.add(new BasicHeader(Constants.ACCEPT, Constants.APPLICATION_JSON));
			headersList.add(new BasicHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON));
			headersList.add(new BasicHeader("Authorization", typeToken + " " + playerToken));
			String response = "";
			mHttpServices = new HttpServices(mCtx);
			try {
				if (mHttpServices.isOnline()) {
					response = mHttpServices.postRequest(Constants.URI_GAMES, body, headersList);
					jsonResponse = new JSONObject(response);
				}
			} catch (IllegalArgumentException e) {
				Log.e("CreateGameTask IllegalArgumentException", e.toString());
			} catch (ParseException e) {
				Log.e("CreateGameTask ParseException", e.toString());
			} catch (IOException e) {
				Log.e("CreateGameTask IOException", e.toString());
			} catch (Exception e) {
				Log.e("CreateGameTask", e.toString());
			}
			return jsonResponse;
		}

		@Override
		protected void onPostExecute(JSONObject jsonResponse) {
			super.onPostExecute(jsonResponse);
			Log.i("CreateGameTask response", jsonResponse.toString());
			if (mDialog.checkConnectingDialog()) {
				mDialog.disableConnectingDialog();
			}
		}
	}

	private class EditGameTask extends AsyncTask<JSONObject, Void, JSONObject> {
		private HttpServices mHttpServices;
		private Context mCtx;
		private DialogServices mDialog;

		public EditGameTask(Activity activityForCtx) {
			mCtx = activityForCtx;
			mDialog = new DialogServices(mCtx);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog.showConnectingDialog();
		}

		@Override
		protected JSONObject doInBackground(JSONObject... params) {
			JSONObject gameData = params[0];
			JSONObject jsonResponse = null;
			String gameId = null;
			try {
				gameId = gameData.getString(Constants.ID);
			} catch (JSONException e) {
				Log.e("EditGameTask getString gameId from JSON ", e.toString());
			}
			List<Header> headersList = new LinkedList<Header>();
			headersList.add(new BasicHeader(Constants.ACCEPT, Constants.APPLICATION_JSON));
			headersList.add(new BasicHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON));
			headersList.add(new BasicHeader("Authorization", typeToken + " " + playerToken));
			String response = null;
			mHttpServices = new HttpServices(mCtx);
			try {
				if (mHttpServices.isOnline()) {
					response = mHttpServices.putRequest(Constants.URI_GAMES + "/" + gameId, headersList, gameData);
					jsonResponse = new JSONObject(response);
				}
			} catch (IllegalArgumentException e) {
				Log.e("EditGameTask IllegalArgumentException", e.toString());
			} catch (ParseException e) {
				Log.e("EditGameTask ParseException", e.toString());
			} catch (IOException e) {
				Log.e("EditGameTask IOException", e.toString());
			} catch (Exception e) {
				Log.e("EditGameTask", e.toString());
			}
			return jsonResponse;
		}

		@Override
		protected void onPostExecute(JSONObject jsonResponse) {
			super.onPostExecute(jsonResponse);
			Log.i("EditGameTask response", jsonResponse.toString());
			if (mDialog.checkConnectingDialog()) {
				mDialog.disableConnectingDialog();
			}
		}
	}
}
