package com.blstream.ctf1.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.net.ConnectivityManager;

import com.blstream.ctf1.converter.InputStreamConverter;
import com.blstream.ctf1.converter.StringConverter;
import com.blstream.ctf1.exception.CTFException;

/**
 * @author Adrian Swarcewicz
 * @author Rafal Olichwer
 */
public class NetworkService {

	Context mContext;
	
	HttpClient mClient;
	
	HttpUriRequest mHttpUriRequest;

	/**
	 * @param context
	 *            needed to translate errorCode to error message stored in
	 *            strings.xml
	 */
	public NetworkService(Context context) {
		mContext = context;
	}

	/**
	 * @author Adrian Swarcewicz
	 * @return JSONArray received from server, or null if no json received
	 */
	private synchronized JSONArray executeRequest(HttpUriRequest httpUriRequest) throws ClientProtocolException, IOException, CTFException, JSONException {
		mHttpUriRequest = httpUriRequest;
		
		mClient = new DefaultHttpClient();
		HttpResponse response = mClient.execute(httpUriRequest);
		StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() > 400) {
			throw new CTFException(mContext.getResources(), statusLine.getStatusCode(), statusLine.toString());
		}

		String responseContent = InputStreamConverter.toString(response.getEntity().getContent());

		return StringConverter.toJSONArray(responseContent);
	}
	
	/**
	 * @author Adrian Swarcewicz
	 */
	public void abortRequest() {
		if (mHttpUriRequest != null && mClient != null) {
			mHttpUriRequest.abort();
//			mClient.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * @author Adrian Swarcewicz
	 */
	public boolean isRequestAborted() {
		return mHttpUriRequest.isAborted();
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
	 * @author Adrian Swarcewicz
	 * @param url
	 *            - possible query string should be appended to url
	 * @param headers
	 * @return JSONArray received from server, or null if no json received
	 */
	public JSONArray requestGet(String url, List<Header> headers) throws ClientProtocolException, IOException, CTFException, JSONException {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeaders((Header[]) headers.toArray(new Header[headers.size()]));
		return executeRequest(httpGet);
	}

	/**
	 * @author Adrian Swarcewicz
	 * @return JSONArray received from server, or null if no json received
	 */
	public JSONArray requestPost(String url, List<Header> headers, String body) throws ClientProtocolException, IOException, CTFException, JSONException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeaders((Header[]) headers.toArray(new Header[headers.size()]));
		httpPost.setEntity(new StringEntity(body));
		return executeRequest(httpPost);
	}

	/**
	 * @author Adrian Swarcewicz
	 * @param url
	 * @param headers
	 * @param body
	 *            - null to skip
	 * @return JSONArray received from server, or null if no json received
	 */
	public JSONArray requestPut(String url, List<Header> headers, String body) throws ClientProtocolException, IOException, CTFException, JSONException {
		HttpPut httpPut = new HttpPut(url);
		httpPut.setHeaders((Header[]) headers.toArray(new Header[headers.size()]));
		if (body != null) {
			httpPut.setEntity(new StringEntity(body));
		}
		return executeRequest(httpPut);
	}

	/**
	 * @author Adrian Swarcewicz
	 * @return JSONArray received from server, or null if no json received
	 */
	public JSONArray requestDelete(String url, List<Header> headers) throws ClientProtocolException, IOException, CTFException, JSONException {
		HttpDelete httpDelete = new HttpDelete(url);
		httpDelete.setHeaders((Header[]) headers.toArray(new Header[headers.size()]));
		return executeRequest(httpDelete);
	}
}
