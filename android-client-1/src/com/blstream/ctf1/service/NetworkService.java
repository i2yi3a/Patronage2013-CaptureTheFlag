package com.blstream.ctf1.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;

import com.blstream.ctf1.Constants;



/**
 * @author Adrian Swarcewicz
 * @author Rafal Olichwer
 */
public class NetworkService {
	
	Context mContext;
	
	
	
	
	
	/**
	 * @param context
	 * 		needed to translate errorCode to error message stored in strings.xml
	 */
	public NetworkService(Context context) {
		mContext = context;
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
	 * @author Adrian Swarcewicz
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
	 * @author Adrian Swarcewicz
	 */
	// TODO: should return JSONArray like a requestGet?
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
			throw new IOException(mContext.getResources().getString(
					mContext.getResources().getIdentifier(
							Constants.PREFIX_ERROR_CODE + 500, "string",
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

	
	
	/**
	 * @param url
	 * @param headers
	 * @param queryString
	 * @return JSONArray received from server
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 * @author Adrian Swarcewicz
	 */
	public JSONArray requestGet(String url, List<Header> headers, String queryString)
			throws ClientProtocolException, IOException, JSONException {

		if (queryString != null && !queryString.isEmpty()) {
			url += "?" + queryString;
		}
		
		HttpClient client = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeaders((Header[]) headers.toArray(new Header[headers
				.size()]));

		HttpResponse response = client.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() == 500) {
			throw new IOException(mContext.getResources().getString(
					mContext.getResources().getIdentifier(
							Constants.PREFIX_ERROR_CODE + 500, "string",
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

		return new JSONArray(builder.toString());
	}
}
