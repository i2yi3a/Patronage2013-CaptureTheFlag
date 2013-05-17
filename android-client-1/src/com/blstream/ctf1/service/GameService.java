package com.blstream.ctf1.service;

import java.io.IOException;
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
import com.blstream.ctf1.converter.GameFilterConverter;
import com.blstream.ctf1.converter.JSONConverter;
import com.blstream.ctf1.domain.GameBasicInfo;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.domain.GameFilter;
import com.blstream.ctf1.domain.LoggedPlayer;
import com.blstream.ctf1.exception.CTFException;

/**
 * @author Adrian Swarcewicz, Rafał Olichwer
 */
public class GameService {

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

	private LoggedPlayer getLoggedPlayer() {
		LoggedPlayer loggedPlayer = null;
		mStorageService.open();
		loggedPlayer = mStorageService.getLoggedPlayer();
		mStorageService.close();
		return loggedPlayer;
	}

	private List<Header> getGameHeaders() {
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
		JSONObject latlngObject = new JSONObject();
		if (id != null && status != null) {
			jsonObject.put("id", id);
			jsonObject.put("status", status);
		}
		jsonObject.put("name", gameName);
		jsonObject.put("description", description);
		jsonObject.put("time_start", timeStart);
		jsonObject.put("duration", duration);
		jsonObject.put("points_max", pointsMax);
		jsonObject.put("players_max", playersMax);
		localizationObject.put("name", localizationName);
		localizationObject.put("radius", radius);
		latlngObject.put("lat", lat);
		latlngObject.put("lng", lng);
		localizationObject.put("latLng", latlngObject);
		localizationObject.put("radius", radius);
		jsonObject.put("localization", localizationObject);

		return jsonObject;
	}

	public void createGame(String gameName, String description, String timeStart, long duration, int pointsMax, int playersMax, String localizationName,
			double lat, double lng, int radius) throws JSONException, ClientProtocolException, IOException, CTFException {
		JSONObject jsonObject = toJSONObject(null, null, gameName, description, timeStart, duration, pointsMax, playersMax, localizationName, lat, lng, radius);

		JSONArray jsonArrayResult = mNetworkService.requestPost(Constants.URL_SERVER + Constants.URI_GAME, getGameHeaders(), jsonObject.toString());

		JSONObject jsonObjectResult = (JSONObject) jsonArrayResult.get(0);

		if (!jsonObjectResult.has("id")) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		}

	}

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

	public GameExtendedInfo getGameDetails(String id) throws JSONException, ClientProtocolException, IOException, CTFException {
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
	 * before use, logged player data must be saved in storage
	 * 
	 * @param gameFilter
	 *            - null to skip
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @author Adrian Swarcewicz
	 * @throws CTFException
	 */
	public List<GameBasicInfo> getGameList(GameFilter gameFilter) throws JSONException, ClientProtocolException, IOException, CTFException {
		JSONArray jsonArrayResult = mNetworkService.requestGet(Constants.URL_SERVER + Constants.URI_GAME + "?" + GameFilterConverter.toQueryString(gameFilter),
				getGameHeaders());

		return JSONConverter.toGameBasicInfo(jsonArrayResult);
	}

	/**
	 * @author Adrian Swarcewicz
	 */
	public List<String> getPlayersForGame(String gameId) throws ClientProtocolException, IOException, JSONException, CTFException {
		JSONArray jsonArrayResult = mNetworkService.requestGet(Constants.URL_SERVER + Constants.URI_GAME + "/" + gameId + "/players", getGameHeaders());

		return JSONConverter.toPlayerNameStrings(jsonArrayResult);
	}

	/**
	 * @author Adrian Swarcewicz
	 */
	public boolean isLoggedPlayerSignInInGame(String gameId) throws ClientProtocolException, IOException, JSONException, CTFException {
		return getPlayersForGame(gameId).contains(getLoggedPlayer().getLogin());
	}

	/**
	 * @author Adrian Swarcewicz
	 */
	public void signInToGame(String gameId) throws ClientProtocolException, IOException, JSONException, CTFException {
		JSONObject jsonObjectResult = mNetworkService.requestPut(Constants.URL_SERVER + Constants.URI_GAME + "/" + gameId + "/signIn", getGameHeaders(), null)
				.getJSONObject(0);

		if (jsonObjectResult.getInt("error_code") != 0) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		}
	}

	/**
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
	 * @author Adrian Swarcewicz
	 */
	public void deleteGame(String gameId) throws ClientProtocolException, IOException, JSONException, CTFException {
		JSONObject jsonObjectResult = mNetworkService.requestDelete(Constants.URL_SERVER + Constants.URI_GAME + "/" + gameId, getGameHeaders())
				.getJSONObject(0);

		if (jsonObjectResult.getInt("error_code") != 0) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		}
	}
}
