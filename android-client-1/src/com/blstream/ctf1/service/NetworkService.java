package com.blstream.ctf1.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;

import com.blstream.ctf1.Constants;
import com.blstream.ctf1.domain.LoggedPlayer;
import com.blstream.ctf1.exception.CTFException;



/**
 * @author Adrian Swarcewicz
 * @author Rafal Olichwer
 */
public class NetworkService {
	
	Resources mResources;
	
	
	
	
	
	/**
	 * @param resources
	 * 		needed to translate errorCode to error message stored in strings.xml
	 */
	public NetworkService(Resources resources) {
		mResources = resources;
	}
	
	
	
	public static Boolean isDeviceOnline(Context context) {
		Boolean result = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting() == true) {
			result = true;
		}
		
		return result;
	}
	
	
	
	/**
	 * @param jsonObject
	 * @return query string based on jsonObject or null if no keys-value pair
	 *         found in jsonObject
	 * @throws JSONException
	 */
	public static String jsonToQueryString(JSONObject jsonObject)
			throws JSONException {
		
		StringBuilder stringBuilder = new StringBuilder();
		Iterator<?> jsonIterator = jsonObject.keys();

		if (!jsonIterator.hasNext()) {
			return null;
		}

		while (jsonIterator.hasNext()) {
			String key = (String) jsonIterator.next();
			Object value = jsonObject.get(key);
			stringBuilder.append(key + "=" + value + "&");
		}

		stringBuilder.setLength(stringBuilder.length() - 1);

		return stringBuilder.toString();
	}
	
	
	
	/**
	 * @param url
	 * @param headers
	 * @param body
	 * @return JSONObject received from server
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public JSONObject requestPost(String url, List<Header> headers, String body)
			throws ClientProtocolException, IOException, JSONException {

		HttpClient client = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeaders((Header[]) headers.toArray(new Header[headers
				.size()]));
		httpPost.setEntity(new StringEntity(body));

		HttpResponse response = client.execute(httpPost);
		StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() == 500) {
			throw new IOException(mResources.getString(mResources
					.getIdentifier(Constants.PREFIX_ERROR_CODE + 500, "string",
							Constants.PACKAGE_NAME)));
		}

		InputStream content = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				content));

		String line;
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}

		return new JSONObject(builder.toString());
	}
	
	
	
	public void registerPlayer(String username, String password)
			throws JSONException, ClientProtocolException, IOException,
			CTFException {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", username);
		jsonObject.put("password", password);

		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-Type", "application/json"));

		JSONObject jsonObjectResult = requestPost(Constants.URL_SERVER
				+ Constants.URI_REGISTER_PLAYER, headers, jsonObject.toString());

		if (jsonObjectResult.getInt("error_code") != 0) {
			throw new CTFException(mResources,
					jsonObjectResult.getInt("error_code"),
					jsonObjectResult.getString("error_description"));
		}

	}
	
	
	
	public LoggedPlayer login(String username, String password) throws CTFException, JSONException, ClientProtocolException, IOException {
		
		LoggedPlayer result = new LoggedPlayer();
		
		List<Header> headers = new LinkedList<Header>();
		headers.add(new BasicHeader("Content-type", "application/x-www-form-urlencoded"));
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("client_id", Constants.CLIENT_ID);
		jsonObject.put("client_secret", Constants.CLIENT_SECRET);
		jsonObject.put("grant_type", "password");
		jsonObject.put("username", username);
		jsonObject.put("password", password);
		
		JSONObject jsonObjectResult = requestPost(Constants.URL_SERVER
				+ Constants.URI_LOGIN_PLAYER, headers, jsonToQueryString(jsonObject));
		if(jsonObjectResult.has("error")) {
			if (jsonObjectResult.getString("error").equals("invalid_grant")) {
				if(jsonObjectResult.getString("error_description").equals("Bad credentials"))
					throw new CTFException(mResources,
							Constants.ERROR_CODE_BAD_USERNAME,
						jsonObjectResult.getString("error_description"));
				else
					throw new CTFException(mResources,
							Constants.ERROR_CODE_BAD_PASSWORD,
						jsonObjectResult.getString("error_description"));
			} else if(jsonObjectResult.getString("error").equals("invalid_token"))
				throw new CTFException(mResources,
						Constants.ERROR_CODE_BAD_TOKEN,
					jsonObjectResult.getString("error_description"));
		} else {
			result.setLogin(username);
			result.setAccessToken(jsonObjectResult.getString("access_token").toString());
			result.setScope(jsonObjectResult.getString("scope").toString());
			result.setTokenType(jsonObjectResult.getString("token_type").toString());
		}
		return result;
	}
	
}
