package com.blstream.ctf1.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.blstream.ctf1.Constants;
import com.blstream.ctf1.domain.LoggedPlayer;
import com.blstream.ctf1.exception.CTFException;



/**
 * @author Adrian Swarcewicz, Rafa³ Olichwer
 */
public class GameService {

	Context mContext;
	
	
	
	
	
	/**
	 * @param context
	 *            needed to translate errorCode to error message stored in
	 *            strings.xml
	 */
	public GameService(Context context) {
		mContext = context;
	}

	
	
	public void createGame(String gameName, String description,
			String timeStart, long duration, int pointsMax, int playersMax,
			String localizationName, double lat, double lng, int radius)
			throws JSONException, ClientProtocolException, IOException,
			CTFException {

		StorageService storageService = new StorageService(mContext);
		NetworkService networkService = new NetworkService(mContext);

		storageService.open();
		LoggedPlayer loggedPlayer = storageService.getLoggedPlayer();
		storageService.close();

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));
		headers.add(new BasicHeader("Authorization", "Bearer "
				+ loggedPlayer.getAccessToken()));

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
		latlngObject.put("lat", lat);
		latlngObject.put("lng", lng);
		localizationObject.put("latLng", latlngObject);
		jsonObject.put("localization", localizationObject);
		jsonObject.put("radius", radius);

		JSONObject jsonObjectResult = networkService.requestPost(
				Constants.URL_SERVER + Constants.URI_CREATE_GAME, headers,
				jsonObject.toString());

		if (jsonObjectResult.getInt("error_code") != 0) {
			throw new CTFException(mContext.getResources(),
					jsonObjectResult.getInt("error_code"),
					jsonObjectResult.getString("error_description"));
		}

	}
}
