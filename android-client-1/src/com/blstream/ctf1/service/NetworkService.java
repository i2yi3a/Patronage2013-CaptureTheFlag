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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.net.ConnectivityManager;

import com.blstream.ctf1.exception.CTFException;

/**
 * @author Adrian Swarcewicz
 * @author Rafal Olichwer
 */
public class NetworkService {

	Context mContext;

	/**
	 * @param context
	 *            needed to translate errorCode to error message stored in
	 *            strings.xml
	 */
	public NetworkService(Context context) {
		mContext = context;
	}

	public static Boolean isDeviceOnline(Context context) {
		Boolean result = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting() == true) {
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
	public static String jsonToQueryString(JSONObject jsonObject) throws JSONException {
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
	 * @throws CTFException
	 * @author Adrian Swarcewicz
	 */
	public JSONArray requestPost(String url, List<Header> headers, String body) throws ClientProtocolException, IOException, JSONException, CTFException {
		HttpClient client = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeaders((Header[]) headers.toArray(new Header[headers.size()]));
		httpPost.setEntity(new StringEntity(body));

		HttpResponse response = client.execute(httpPost);
		StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() > 400) {
			throw new CTFException(mContext.getResources(), statusLine.getStatusCode(), statusLine.toString());
		}

		String responseContent = inputStreamToString(response.getEntity().getContent());

		return stringToJSONArray(responseContent);
	}

	/**
	 * @param url
	 * @param headers
	 * @param body
	 * @return JSONArray received from server, or null if no json received
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 * @author Adrian Swarcewicz
	 * @throws CTFException
	 * @throws NotFoundException
	 */
	public JSONArray requestPut(String url, List<Header> headers, String body) throws ClientProtocolException, IOException, JSONException, NotFoundException,
			CTFException {
		HttpClient client = new DefaultHttpClient();

		HttpPut httpPut = new HttpPut(url);
		httpPut.setHeaders((Header[]) headers.toArray(new Header[headers.size()]));
		httpPut.setEntity(new StringEntity(body));

		HttpResponse response = client.execute(httpPut);
		StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() > 400) {
			throw new CTFException(mContext.getResources(), statusLine.getStatusCode(), statusLine.toString());
		}

		String responseContent = inputStreamToString(response.getEntity().getContent());

		return stringToJSONArray(responseContent);
	}

	/**
	 * @author Adrian Swarcewicz
	 */
	private String inputStreamToString(InputStream content) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(content));

		String line;
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}

		return builder.toString();
	}

	/**
	 * @return JSONArray if string can be converted, or null if not
	 * @author Adrian Swarcewicz
	 */
	private JSONArray stringToJSONArray(String jsonString) throws JSONException {
		JSONArray result = null;
		Object json = new JSONTokener(jsonString).nextValue();
		if (json instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) json;
			result = new JSONArray();
			result.put(jsonObject);
		} else if (json instanceof JSONArray) {
			result = (JSONArray) json;
		}
		return result;
	}

	/**
	 * @param url
	 *            - possible query string should be appended to url
	 * @param headers
	 * @return JSONArray received from server, or null if no json received
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 * @throws CTFException
	 * @author Adrian Swarcewicz
	 */
	public JSONArray requestGet(String url, List<Header> headers) throws ClientProtocolException, IOException, JSONException, CTFException {
		HttpClient client = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeaders((Header[]) headers.toArray(new Header[headers.size()]));

		HttpResponse response = client.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() > 400) {
			throw new CTFException(mContext.getResources(), statusLine.getStatusCode(), statusLine.toString());
		}

		String responseContent = inputStreamToString(response.getEntity().getContent());

		return stringToJSONArray(responseContent);
	}

}
