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
import com.blstream.ctf1.domain.GameBasicInfo;
import com.blstream.ctf1.domain.GameStatusType;
import com.blstream.ctf1.domain.LoggedPlayer;
import com.blstream.ctf1.exception.CTFException;
/**
 * @author Adrian Swarcewicz, Rafa� Olichwer
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

	public JSONObject JSONCreate(String gameName, String description,
			String timeStart, long duration, int pointsMax, int playersMax,
			String localizationName, double lat, double lng, int radius)
			throws JSONException, ClientProtocolException, IOException,
			CTFException {
		
		JSONObject jsonObject = new JSONObject();
		JSONObject localizationObject = new JSONObject();
		JSONObject latlngObject = new JSONObject();
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
	
	public JSONObject JSONEdit(String id,String gameName, String description,
			String timeStart, long duration, int pointsMax, int playersMax,
			String localizationName, double lat, double lng, int radius)
			throws JSONException, ClientProtocolException, IOException,
			CTFException {
		
		JSONObject jsonObject = new JSONObject();
		JSONObject localizationObject = new JSONObject();
		JSONObject latlngObject = new JSONObject();
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
	
	//TODO method too long. please split it.
	//for example json part may be another method etc...
	//TODO hardoced values. Please convert all hardoced string to public/private final static fields
	public void createGame(String gameName, String description,
			String timeStart, long duration, int pointsMax, int playersMax,
			String localizationName, double lat, double lng, int radius)
			throws JSONException, ClientProtocolException, IOException,
			CTFException {

		mStorageService.open();
		LoggedPlayer loggedPlayer = mStorageService.getLoggedPlayer();
		mStorageService.close();

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));
		headers.add(new BasicHeader("Authorization", "Bearer "
				+ loggedPlayer.getAccessToken()));

		JSONObject jsonObject = JSONCreate(gameName,description,timeStart,
				duration,pointsMax,playersMax,localizationName,lat,lng,radius);

		JSONObject jsonObjectResult = mNetworkService.requestPost(
				Constants.URL_SERVER + Constants.URI_CREATE_GAME, headers,
				jsonObject.toString());


		if (!jsonObjectResult.has("id")) {
			throw new CTFException(mContext.getResources(),
					jsonObjectResult.getInt("error_code"),
					jsonObjectResult.getString("error_description"));
		}

	}
	
	public void editGame(String id,String gameName, String description,
			String timeStart, long duration, int pointsMax, int playersMax,
			String localizationName, double lat, double lng, int radius)
			throws JSONException, ClientProtocolException, IOException,
			CTFException {

		mStorageService.open();
		LoggedPlayer loggedPlayer = mStorageService.getLoggedPlayer();
		mStorageService.close();

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));
		headers.add(new BasicHeader("Authorization", "Bearer "
				+ loggedPlayer.getAccessToken()));

		JSONObject jsonObject = JSONEdit(id,gameName,description,timeStart,
				duration,pointsMax,playersMax,localizationName,lat,lng,radius);

		JSONObject jsonObjectResult = mNetworkService.requestPut(
				Constants.URL_SERVER + Constants.URI_CREATE_GAME, headers,
				jsonObject.toString());


		if (jsonObjectResult.has("error_code")) {
			throw new CTFException(mContext.getResources(),
					jsonObjectResult.getInt("error_code"),
					jsonObjectResult.getString("error_description"));
		}

	}
	
	//TODO method too long. split it
	/**
	 * before use, logged player data must be saved in storage
	 * @param gameNameFilter
	 *            - null to skip
	 * @param gameStatusTypeFilter
	 *            - null to skip
	 * @param myGamesFilter
	 *            - null to skip
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @author Adrian Swarcewicz
	 */
	public List<GameBasicInfo> getGameList(String gameNameFilter,
			GameStatusType gameStatusTypeFilter, Boolean myGamesFilter)
			throws JSONException, ClientProtocolException, IOException {
		List<GameBasicInfo> gameBasicInfos = new LinkedList<GameBasicInfo>();

		mStorageService.open();
		LoggedPlayer loggedPlayer = mStorageService.getLoggedPlayer();
		mStorageService.close();

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));
		headers.add(new BasicHeader("Authorization", loggedPlayer
				.getTokenType() + " " + loggedPlayer.getAccessToken()));

		JSONObject jsonObjectFilter = new JSONObject();
		jsonObjectFilter.put("name", gameNameFilter);
		jsonObjectFilter.put("status", gameStatusTypeFilter);
		jsonObjectFilter.put("myGames", myGamesFilter);

		JSONArray jsonArrayResult = mNetworkService.requestGet(
				Constants.URL_SERVER + Constants.URI_GAME_LIST, headers,
				NetworkService.jsonToQueryString(jsonObjectFilter));

		for (int i = 0; i < jsonArrayResult.length(); i++) {
			GameBasicInfo gameBasicInfo = new GameBasicInfo();
			JSONObject jo = jsonArrayResult.getJSONObject(i);

			gameBasicInfo.setId(jo.getString("id"));
			gameBasicInfo.setName(jo.getString("name"));
			gameBasicInfo.setGameStatusType(GameStatusType.fromString(jo
					.getString("status")));
			gameBasicInfo.setOwner(jo.getString("owner"));

			gameBasicInfos.add(gameBasicInfo);
		}

		return gameBasicInfos;
	}
}
