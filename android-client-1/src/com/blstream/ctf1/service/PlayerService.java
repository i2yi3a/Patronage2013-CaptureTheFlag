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
import com.blstream.ctf1.JSONFields;
import com.blstream.ctf1.converter.JSONConverter;
import com.blstream.ctf1.domain.LoggedPlayer;
import com.blstream.ctf1.exception.CTFException;

/**
 * @author Adrian Swarcewicz, Rafał Olichwer
 */
public class PlayerService implements NetworkOperationService {

	public static final String BAD_CREDENTIALS = "Bad credentials";

	public static final String INVALID_GRANT = "invalid_grant";

	public static final String INVALID_TOKEN = "invalid_token";

	private Context mContext;
	
	private NetworkService mNetworkService;

	public PlayerService(Context context) {
		mContext = context;
		mNetworkService = new NetworkService(mContext);
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
	 * @author Adrian Swarcewicz 
	 */
	public void registerPlayer(String username, String password) throws JSONException, ClientProtocolException, IOException, CTFException {
		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(JSONFields.USERNAME, username);
		jsonObject.put(JSONFields.PASSWORD, password);

		JSONArray jsonArrayResult = mNetworkService.requestPost(Constants.URL_SERVER + Constants.URI_REGISTER_PLAYER, headers, jsonObject.toString());
		JSONObject jsonObjectResult = (JSONObject) jsonArrayResult.get(0);

		if (jsonObjectResult.getInt("error_code") != 0) {
			throw new CTFException(mContext.getResources(), jsonObjectResult.getInt("error_code"), jsonObjectResult.getString("error_description"));
		}

	}

	public JSONObject toJSONObject(String username, String password) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("client_id", Constants.CLIENT_ID);
		jsonObject.put("client_secret", Constants.CLIENT_SECRET);
		jsonObject.put("grant_type", "password");
		jsonObject.put(JSONFields.USERNAME, username);
		jsonObject.put(JSONFields.PASSWORD, password);

		return jsonObject;
	}

	public LoggedPlayer toLoggedPlayer(String username, JSONObject jsonObject) throws JSONException {
		LoggedPlayer result = new LoggedPlayer();
		result.setLogin(username);
		result.setAccessToken(jsonObject.getString("access_token"));
		result.setScope(jsonObject.getString("scope").toString());
		result.setTokenType(jsonObject.getString("token_type"));

		return result;
	}

	public LoggedPlayer login(String username, String password) throws CTFException, JSONException, ClientProtocolException, IOException {
		LoggedPlayer result = null;

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Content-type", "application/x-www-form-urlencoded"));

		JSONObject jsonObject = toJSONObject(username, password);

		JSONArray jsonArrayResult = mNetworkService.requestPost(Constants.URL_SERVER + Constants.URI_LOGIN_PLAYER, headers,
				JSONConverter.toQueryString(jsonObject));

		JSONObject jsonObjectResult = (JSONObject) jsonArrayResult.get(0);

		if (jsonObjectResult.has(JSONFields.ERROR)) {
			if (jsonObjectResult.getString(JSONFields.ERROR).equals(INVALID_GRANT)) {
				if (jsonObjectResult.getString(JSONFields.ERROR_DESCRIPTION).equals(BAD_CREDENTIALS))
					throw new CTFException(mContext.getResources(), Constants.ERROR_CODE_BAD_USERNAME, jsonObjectResult.getString(JSONFields.ERROR_DESCRIPTION));
				else
					throw new CTFException(mContext.getResources(), Constants.ERROR_CODE_BAD_PASSWORD, jsonObjectResult.getString(JSONFields.ERROR_DESCRIPTION));
			} else if (jsonObjectResult.getString(JSONFields.ERROR).equals(INVALID_TOKEN))
				throw new CTFException(mContext.getResources(), Constants.ERROR_CODE_BAD_TOKEN, jsonObjectResult.getString(JSONFields.ERROR_DESCRIPTION));
		} else {
			result = toLoggedPlayer(username, jsonObjectResult);
		}
		return result;
	}
}
