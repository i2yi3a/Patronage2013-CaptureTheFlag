package com.blstream.ctf1.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
	 * @param url
	 * @param jsonObject representing request parameters
	 * @return JSONObject received from server
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public JSONObject request(String url, JSONObject jsonObject) throws ClientProtocolException, IOException, JSONException {
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity(jsonObject.toString()));

		HttpResponse response = client.execute(httpPost);
		StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() == 500) {
			throw new IOException(
						mResources.getString(mResources
						.getIdentifier(Constants.PREFIX_ERROR_CODE + 500, "string",
						Constants.PACKAGE_NAME))
					);
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
	
	
	
	public void registerPlayer(String username, String password) throws JSONException, ClientProtocolException, 
			IOException, CTFException {
		
		JSONObject jsonObjectParam = new JSONObject();
		jsonObjectParam.put("username", username);
		jsonObjectParam.put("password", password);
		
		JSONObject jsonObjectResult = request(Constants.URL_SERVER
				+ Constants.URI_REGISTER_PLAYER, jsonObjectParam);
		
		if (jsonObjectResult.getInt("error_code") != 0) {
			throw new CTFException(mResources,
					jsonObjectResult.getInt("error_code"),
					jsonObjectResult.getString("error_description"));
		}
		
	}
	
	
	
	//TODO;
	public LoggedPlayer login(String username, String password) throws CTFException {
		return null;
	}
	
}
