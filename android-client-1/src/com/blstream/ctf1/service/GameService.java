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

	public JSONObject JSON(String id, String gameName, String description, String timeStart, long duration, int pointsMax, int playersMax,
			String localizationName, double lat, double lng, int radius) throws JSONException, ClientProtocolException, IOException, CTFException {

		JSONObject jsonObject = new JSONObject();
		JSONObject localizationObject = new JSONObject();
		JSONObject latlngObject = new JSONObject();
		if (id != null)
			jsonObject.put("id", id);
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

	// TODO method too long. please split it.
	// for example json part may be another method etc...
	// TODO hardoced values. Please convert all hardoced string to
	// public/private final static fields
	public void createGame(String gameName, String description, String timeStart, long duration, int pointsMax, int playersMax, String localizationName,
			double lat, double lng, int radius) throws JSONException, ClientProtocolException, IOException, CTFException {

		mStorageService.open();
		LoggedPlayer loggedPlayer = mStorageService.getLoggedPlayer();
		mStorageService.close();

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));
		headers.add(new BasicHeader("Authorization", "Bearer " + loggedPlayer.getAccessToken()));

		JSONObject jsonObject = JSON(null, gameName, description, timeStart, duration, pointsMax, playersMax, localizationName, lat, lng, radius);

		JSONArray jsonArrayResult = mNetworkService.requestPost(Constants.URL_SERVER + Constants.URI_GAME, headers, jsonObject.toString());

		JSONObject jsonObjectResult = (JSONObject) jsonArrayResult.get(0);

		if (!jsonObjectResult.has("id")) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		}

	}

	public void editGame(String id, String gameName, String description, String timeStart, long duration, int pointsMax, int playersMax,
			String localizationName, double lat, double lng, int radius) throws JSONException, ClientProtocolException, IOException, CTFException {

		mStorageService.open();
		LoggedPlayer loggedPlayer = mStorageService.getLoggedPlayer();
		mStorageService.close();

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));
		headers.add(new BasicHeader("Authorization", "Bearer " + loggedPlayer.getAccessToken()));

		JSONObject jsonObject = JSON(id, gameName, description, timeStart, duration, pointsMax, playersMax, localizationName, lat, lng, radius);

		JSONArray jsonArrayResult = mNetworkService.requestPut(Constants.URL_SERVER + Constants.URI_GAME, headers, jsonObject.toString());

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

		mStorageService.open();
		LoggedPlayer loggedPlayer = mStorageService.getLoggedPlayer();
		mStorageService.close();

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));
		headers.add(new BasicHeader("Authorization", "Bearer " + loggedPlayer.getAccessToken()));

		JSONArray jsonArrayResult = mNetworkService.requestGet(Constants.URL_SERVER + Constants.URI_GAME + '/' + id, headers);

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
		LoggedPlayer loggedPlayer = getLoggedPlayer();

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));
		headers.add(new BasicHeader("Authorization", loggedPlayer.getTokenType() + " " + loggedPlayer.getAccessToken()));

		JSONObject jsonObjectFilter = GameFilterService.toJSONObject(gameFilter);

		JSONArray jsonArrayResult = mNetworkService.requestGet(Constants.URL_SERVER + Constants.URI_GAME + "?" + JSONConverter.toQueryString(jsonObjectFilter),
				headers);

		return JSONConverter.toGameBasicInfo(jsonArrayResult);
	}
}
