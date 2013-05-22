package com.blstream.ctf1.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.blstream.ctf1.Constants;
import com.blstream.ctf1.converter.GameBasicListFilterConverter;
import com.blstream.ctf1.converter.GameLocalizationListFilterConverter;
import com.blstream.ctf1.converter.JSONConverter;
import com.blstream.ctf1.domain.GameBasicInfo;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.domain.GameBasicListFilter;
import com.blstream.ctf1.domain.GameLocalizationListFilter;
import com.blstream.ctf1.domain.LoggedPlayer;
import com.blstream.ctf1.exception.CTFException;

/**
 * @author Adrian Swarcewicz, Rafał Olichwer, Piotr Marczycki
 */
public class GameService implements NetworkOperationService {

	private static final String ERROR_LOGGED_PLAYER_DATA_RETRIEVING = "Error occurred while retrieving stored logged player data";
	private static final int ERROR_CODE_LOGGED_PLAYER_DATA_RETRIEVING = 1001;
	Context mContext;
	NetworkService mNetworkService;
	StorageService mStorageService;

	/**
	 * @param context
	 *            needed to translate errorCode to error message stored in
	 *            strings.xml
	 */
	public GameService(Context context) {
		mContext = context;
		mNetworkService = new NetworkService(context);
		mStorageService = new StorageService(context);
	}

	@Override
	public void abortNetworkOperation() throws UnsupportedOperationException {
		mNetworkService.abortRequest();
	}

	@Override
	public boolean isNetworkOperationAborted() {
		return mNetworkService.isRequestAborted();
	}

	/**
	 * If logged player data not found throw CTFException with error_code_1001
	 * 
	 * @author Adrian Swarcewicz
	 */
	private LoggedPlayer getLoggedPlayer() throws CTFException {
		mStorageService.open();
		LoggedPlayer loggedPlayer = mStorageService.getLoggedPlayer();
		mStorageService.close();

		if (loggedPlayer == null) {
			throw new CTFException(mContext.getResources(), ERROR_CODE_LOGGED_PLAYER_DATA_RETRIEVING, ERROR_LOGGED_PLAYER_DATA_RETRIEVING);
		}

		return loggedPlayer;
	}

	/**
	 * @author Adrian Swarcewicz
	 */
	private List<Header> getGameHeaders() throws CTFException {
		LoggedPlayer loggedPlayer = getLoggedPlayer();

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));
		headers.add(new BasicHeader("Authorization", loggedPlayer.getTokenType() + " " + loggedPlayer.getAccessToken()));

		return headers;
	}

	public JSONObject toJSONObject(String id, String status, String gameName, String description, String timeStart, long duration, int pointsMax,
			int playersMax, String localizationName, double lat, double lng, int radius) throws JSONException, ClientProtocolException, IOException,
			CTFException {

		JSONObject jsonObject = new JSONObject();
		JSONObject localizationObject = new JSONObject();
		if (id != null)
			jsonObject.put("id", id);
		if (status != null)
			jsonObject.put("status", status);
		jsonObject.put("name", gameName);
		jsonObject.put("description", description);
		jsonObject.put("time_start", timeStart);
		jsonObject.put("duration", duration);
		jsonObject.put("points_max", pointsMax);
		jsonObject.put("players_max", playersMax);
		localizationObject.put("name", localizationName);
		localizationObject.put("radius", radius);
		JSONArray latLng = new JSONArray();
		latLng.put(lat);
		latLng.put(lng);
		localizationObject.put("latLng", latLng);
		localizationObject.put("radius", radius);
		jsonObject.put("localization", localizationObject);

		return jsonObject;
	}

	/**
	 * Method perform request to server use abortNetworkOperation() to abort.
	 */
	public void createGame(String gameName, String description, String timeStart, long duration, int pointsMax, int playersMax, String localizationName,
			double lat, double lng, int radius) throws JSONException, ClientProtocolException, IOException, CTFException {
		JSONObject jsonObject = toJSONObject(null, null, gameName, description, timeStart, duration, pointsMax, playersMax, localizationName, lat, lng, radius);

		JSONArray jsonArrayResult = mNetworkService.requestPost(Constants.URL_SERVER + Constants.URI_GAME, getGameHeaders(), jsonObject.toString());

		JSONObject jsonObjectResult = (JSONObject) jsonArrayResult.get(0);

		if (!jsonObjectResult.has("id")) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		}

	}

	/**
	 * Method perform request to server use abortNetworkOperation() to abort.
	 */
	public void editGame(String id, String status, String gameName, String description, String timeStart, long duration, int pointsMax, int playersMax,
			String localizationName, double lat, double lng, int radius) throws JSONException, ClientProtocolException, IOException, CTFException {
		JSONObject jsonObject = toJSONObject(id, status, gameName, description, timeStart, duration, pointsMax, playersMax, localizationName, lat, lng, radius);

		JSONArray jsonArrayResult = mNetworkService.requestPut(Constants.URL_SERVER + Constants.URI_GAME + '/' + id, getGameHeaders(), jsonObject.toString());

		if (jsonArrayResult == null) {
			throw new CTFException(mContext.getResources(), Constants.ERROR_CODE_UNEXPECTED_SERVER_RESPONSE, mContext.getResources().getString(
					mContext.getResources().getIdentifier(Constants.PREFIX_ERROR_CODE + Constants.ERROR_CODE_UNEXPECTED_SERVER_RESPONSE, "string",
							Constants.PACKAGE_NAME)));
		}

		JSONObject jsonObjectResult = jsonArrayResult.getJSONObject(0);

		if (jsonObjectResult.has("error_code")) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		}

	}

	/**
	 * Method perform request to server use abortNetworkOperation() to abort.
	 */
	public GameExtendedInfo getGameDetails(String id) throws JSONException, ClientProtocolException, IOException, CTFException, ParseException {
		JSONArray jsonArrayResult = mNetworkService.requestGet(Constants.URL_SERVER + Constants.URI_GAME + '/' + id, getGameHeaders());

		JSONObject jsonObjectResult = jsonArrayResult.getJSONObject(0);

		if (jsonObjectResult.has("error_code")) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		} else {
			GameExtendedInfo result = JSONConverter.toGameExtendedInfo(jsonObjectResult);
			return result;
		}

	}

	/**
	 * Method perform request to server use abortNetworkOperation() to abort.
	 * 
	 * @author Adrian Swarcewicz
	 * @param gameFilter
	 *            - null to skip
	 */
	public List<GameBasicInfo> getGameList(GameBasicListFilter gameFilter) throws JSONException, ClientProtocolException, IOException, CTFException {
		JSONArray jsonArrayResult = mNetworkService.requestGet(Constants.URL_SERVER + Constants.URI_GAME + "?" + GameBasicListFilterConverter.toQueryString(gameFilter),
				getGameHeaders());

		return JSONConverter.toGameBasicInfo(jsonArrayResult);
	}
	
	/**
	 * @author Adrian Swarcewicz
	 */
	public List<GameBasicInfo> getGameListByLocalization(GameLocalizationListFilter gameFilter) throws JSONException, ClientProtocolException, IOException, CTFException {
		JSONArray jsonArrayResult = mNetworkService.requestGet(Constants.URL_SERVER + Constants.URI_GAME + "/nearest?" + GameLocalizationListFilterConverter.toQueryString(gameFilter),
				getGameHeaders());

		return JSONConverter.toGameBasicInfo(jsonArrayResult);
	}

	/**
	 * Method perform request to server use abortNetworkOperation() to abort.
	 * 
	 * @author Adrian Swarcewicz
	 */
	public List<String> getPlayersForGame(String gameId) throws ClientProtocolException, IOException, JSONException, CTFException {
		JSONArray jsonArrayResult = mNetworkService.requestGet(Constants.URL_SERVER + Constants.URI_GAME + "/" + gameId + "/players", getGameHeaders());

		return JSONConverter.toPlayerNameStrings(jsonArrayResult);
	}

	/**
	 * Method perform request to server use abortNetworkOperation() to abort.
	 * 
	 * @author Adrian Swarcewicz
	 */
	public boolean isLoggedPlayerSignedInForGame(String gameId) throws ClientProtocolException, IOException, JSONException, CTFException {
		return getPlayersForGame(gameId).contains(getLoggedPlayer().getLogin());
	}

	/**
	 * Method perform request to server use abortNetworkOperation() to abort.
	 * 
	 * @author Adrian Swarcewicz
	 */
	public void signInForGame(String gameId) throws ClientProtocolException, IOException, JSONException, CTFException {
		JSONObject jsonObjectResult = mNetworkService.requestPut(Constants.URL_SERVER + Constants.URI_GAME + "/" + gameId + "/signIn", getGameHeaders(), null)
				.getJSONObject(0);

		if (jsonObjectResult.getInt("error_code") != 0) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		}
	}

	/**
	 * Method perform request to server use abortNetworkOperation() to abort.
	 * 
	 * @author Adrian Swarcewicz
	 */
	public void signOutFromGame(String gameId) throws ClientProtocolException, IOException, JSONException, CTFException {
		JSONObject jsonObjectResult = mNetworkService.requestPut(Constants.URL_SERVER + Constants.URI_GAME + "/" + gameId + "/signOut", getGameHeaders(), null)
				.getJSONObject(0);

		if (jsonObjectResult.getInt("error_code") != 0) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		}
	}

	/**
	 * Method perform request to server use abortNetworkOperation() to abort.
	 * 
	 * @author Adrian Swarcewicz
	 */
	public void deleteGame(String gameId) throws ClientProtocolException, IOException, JSONException, CTFException {
		JSONObject jsonObjectResult = mNetworkService.requestDelete(Constants.URL_SERVER + Constants.URI_GAME + "/" + gameId, getGameHeaders())
				.getJSONObject(0);

		if (jsonObjectResult.getInt("error_code") != 0) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		}
	}

	/**
	 * Method perform request to server use abortNetworkOperation() to abort.
	 * 
	 * @author Piotr Marczycki
	 */
	public boolean isLoggedPlayerGameOwner(String gameId) throws ClientProtocolException, JSONException, IOException, CTFException, ParseException {
		return getGameDetails(gameId).getOwner().equals(getLoggedPlayer().getLogin());
	}
}
