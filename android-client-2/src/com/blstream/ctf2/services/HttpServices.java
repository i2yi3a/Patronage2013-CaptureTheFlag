package com.blstream.ctf2.services;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;

/**
 * 
 * @author Karol Firmanty
 * [mod] Rafal Tatol
 */
public class HttpServices {

	private Context mCtx;

	public HttpServices(Context ctx) {
		mCtx = ctx;
	}

	public boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public String postRequest(String URI, String params, List<Header> headersList) throws Exception, IllegalArgumentException, IOException, ParseException {
		if (!isOnline()) {
			throw new Exception(mCtx.getString(R.string.no_connection));
		}
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(Constants.URL_SERVER + URI);
		httpPost.setHeaders((headersList.toArray(new Header[headersList.size()])));
		StringEntity stringEntity = new StringEntity(params);
		httpPost.setEntity(stringEntity);
		HttpResponse response = httpclient.execute(httpPost);
		return EntityUtils.toString(response.getEntity());
	}

	public String getRequest(String URI, List<Header> headersList) throws ClientProtocolException, IOException, Exception {
		if (!isOnline()) {
			throw new Exception(mCtx.getString(R.string.no_connection));
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(Constants.URL_SERVER + URI);
		httpGet.setHeaders(headersList.toArray(new Header[headersList.size()]));
		HttpResponse response = httpClient.execute(httpGet);
		return EntityUtils.toString(response.getEntity());
	}

}
