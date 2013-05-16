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
import com.blstream.ctf1.domain.LoggedPlayer;
import com.blstream.ctf1.exception.CTFException;

//TODO remove empty lines

/**
 * @author Adrian Swarcewicz, Rafał Olichwer
 */
public class PlayerService {

	private Context mContext;

	public PlayerService(Context context) {
		mContext = context;
	}

	public void registerPlayer(String username, String password) throws JSONException, ClientProtocolException, IOException, CTFException {
		NetworkService networkService = new NetworkService(mContext);

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", username);
		jsonObject.put("password", password);

		JSONArray jsonArrayResult = networkService.requestPost(Constants.URL_SERVER + Constants.URI_REGISTER_PLAYER, headers, jsonObject.toString());
		JSONObject jsonObjectResult = (JSONObject) jsonArrayResult.get(0);

		if (jsonObjectResult.getInt("error_code") != 0) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		}

	}

	// TODO too long method. please split it
	public LoggedPlayer login(String username, String password) throws CTFException, JSONException, ClientProtocolException, IOException {

		LoggedPlayer result = new LoggedPlayer();
		NetworkService networkService = new NetworkService(mContext);

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Content-type", "application/x-www-form-urlencoded"));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("client_id", Constants.CLIENT_ID);
		jsonObject.put("client_secret", Constants.CLIENT_SECRET);
		jsonObject.put("grant_type", "password");
		jsonObject.put("username", username);
		jsonObject.put("password", password);

		JSONArray jsonArrayResult = networkService.requestPost(Constants.URL_SERVER + Constants.URI_LOGIN_PLAYER, headers,
				JSONConverter.toQueryString(jsonObject));

		JSONObject jsonObjectResult = (JSONObject) jsonArrayResult.get(0);

		if (jsonObjectResult.has("error")) {
			if (jsonObjectResult.getString("error").equals("invalid_grant")) {
				if (jsonObjectResult.getString("error_description").equals("Bad credentials"))
					throw new CTFException(mContext.getResources(), Constants.ERROR_CODE_BAD_USERNAME, jsonObjectResult.getString("error_description"));
				else
					throw new CTFException(mContext.getResources(), Constants.ERROR_CODE_BAD_PASSWORD, jsonObjectResult.getString("error_description"));
			} else if (jsonObjectResult.getString("error").equals("invalid_token"))
				throw new CTFException(mContext.getResources(), Constants.ERROR_CODE_BAD_TOKEN, jsonObjectResult.getString("error_description"));
		} else {
			result.setLogin(username);
			result.setAccessToken(jsonObjectResult.getString("access_token").toString());
			result.setScope(jsonObjectResult.getString("scope").toString());
			result.setTokenType(jsonObjectResult.getString("token_type").toString());
		}
		return result;
	}

}
