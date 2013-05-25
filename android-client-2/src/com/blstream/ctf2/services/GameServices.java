package com.blstream.ctf2.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.blstream.ctf2.activity.game.GamePlayersActivity;
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
	public final static String RADIUS = "radius";
	public final static String LAT_LNG = "latLng";
	public final static String STATUS_NEW = "NEW";
	public final static String STATUS_IN_PROGRESS = "IN_PROGRESS";
	public final static String RED_TEAM_BASE = "red_team_base";
	public final static String BLUE_TEAM_BASE = "blue_team_base";
	public final static String GAME_LIST_EMPTY = "Game list is empty";

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

	public void getGameDetails(GameDetailsActivity gameDetailsActivity, String mGameId) {
		GetGameDetails getGameDetailsTask = new GetGameDetails(gameDetailsActivity);
		getGameDetailsTask.execute(mGameId);
	}

	public void getGamePlayers(GamePlayersActivity gamePlayersActivity, String mGameId) {
		GetGamePlayers getGamePlayersTask = new GetGamePlayers(gamePlayersActivity);
		getGamePlayersTask.execute(mGameId);
	}
	public void joinTheGame(GameDetailsActivity mGameDetailsActivity, String mGameId) {
		JoinTheGame joinTheGame = new JoinTheGame(mGameDetailsActivity);
		joinTheGame.execute(mGameId);
	}

	/**
	 * 
	 * @author Rafal Tatol [mod] Marcin Sareło
	 */
	private class GetGameDetails extends AsyncTask<String, Void, JSONObject> {

		private GameDetailsActivity mGameDetailsActivity;
		private ProgressDialog mProgressDialog;

		public GetGameDetails(GameDetailsActivity gameDetailsActivity) {
			mGameDetailsActivity = gameDetailsActivity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(mGameDetailsActivity);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setMessage("Connecting...");
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
			try {
				if (!jsonResult.has(Constants.ERROR)) {
					GameDetails gameDetails = jsonToGameDetails(jsonResult);
					fillGameDetailsActivity(gameDetails);
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
				Log.e("onPostExecute JSONException", e.toString());
			} catch (Exception e) {
				Log.e("onPostExecute Exception", e.toString());
			}
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
		}

		public void fillGameDetailsActivity(GameDetails gameDetails) {
			mGameDetailsActivity.mGameNameTextView.setText(gameDetails.getName());
			mGameDetailsActivity.mGameIdTextView.setText(gameDetails.getId());
			mGameDetailsActivity.mDescriptionTextView.setText(gameDetails.getDescription());
			mGameDetailsActivity.mDurationIdTextView.setText(gameDetails.getDuration().toString());
			mGameDetailsActivity.mLocalizationTextView.setText(gameDetails.getLocalization().getName());
			mGameDetailsActivity.mLatitudeTextView.setText(gameDetails.getLocalization().getLat().toString());
			mGameDetailsActivity.mLongitudeTextView.setText(gameDetails.getLocalization().getLng().toString());
			mGameDetailsActivity.mRadiusTextView.setText(gameDetails.getLocalization().getRadius().toString());
			mGameDetailsActivity.mStatusTextView.setText(gameDetails.getStatus());
			mGameDetailsActivity.mOwnerTextView.setText(gameDetails.getOwner());
			mGameDetailsActivity.mTimeStartTextView.setText(gameDetails.getTimeStart());
			mGameDetailsActivity.mPointsMaxTextView.setText(gameDetails.getPointsMax().toString());
			mGameDetailsActivity.mPlayersMaxTextView.setText(gameDetails.getPlayersMax().toString());
			mGameDetailsActivity.mTeamRedName.setText(gameDetails.getTeamRed().getName());
			mGameDetailsActivity.mTeamRedBaseLocalization.setText(gameDetails.getTeamRed().getBaseLocalization().toString());
			mGameDetailsActivity.mTeamBlueName.setText(gameDetails.getTeamBlue().getName());
			mGameDetailsActivity.mTeamBlueBaseLocalization.setText(gameDetails.getTeamBlue().getBaseLocalization().toString());
			mGameDetailsActivity.setMapElements(gameDetails);
			if (gameDetails.getStatus().equals(STATUS_NEW)) {
				mGameDetailsActivity.mJoinButton.setEnabled(true);
				if (gameDetails.getOwner().equals(new UserServices(mGameDetailsActivity).getUser().getName())) {
					mGameDetailsActivity.mEditButton.setEnabled(true);
				}
			}
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

				HttpServices httpService = new HttpServices(mGameDetailsActivity);
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

		private GamePlayersActivity mGamePlayersActivity;
		private ProgressDialog mProgressDialog;

		public GetGamePlayers(GamePlayersActivity gamePlayersActivity) {
			mGamePlayersActivity = gamePlayersActivity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(mGamePlayersActivity);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setMessage("Connecting...");
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
				JSONArray jsonArray = new JSONArray(result);
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

				HttpServices httpService = new HttpServices(mGamePlayersActivity);
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
			mGamePlayersActivity.setListView(playersList);
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
}
