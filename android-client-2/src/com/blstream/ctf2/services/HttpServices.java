package com.blstream.ctf2.services;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.exception.CtfException;

/**
 * 
 * @author Karol Firmanty
 * [mod] Rafal Tatol
 * [mod] Marcin Sareło
 */
public class HttpServices {

	private Context mCtx;
	private HttpClient httpClient;;

	public HttpServices(Context ctx) {
		mCtx = ctx;
		httpClient=new DefaultHttpClient();
	}

	public boolean isOnline() throws CtfException {
		ConnectivityManager connMgr = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			throw new CtfException(mCtx.getString(R.string.no_connection));
		}
	}

	public String postRequest(String URI, String params, List<Header> headersList) throws CtfException, IllegalArgumentException, IOException, ParseException {
		isOnline();
//		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(Constants.URL_SERVER + URI);
		httpPost.setHeaders((headersList.toArray(new Header[headersList.size()])));
		StringEntity stringEntity = new StringEntity(params);
		httpPost.setEntity(stringEntity);
		HttpResponse response = httpClient.execute(httpPost);
		return EntityUtils.toString(response.getEntity());
	}

	public String getRequest(String URI, List<Header> headersList) throws ClientProtocolException, IOException, CtfException {
		isOnline();
//		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(Constants.URL_SERVER + URI);
		httpGet.setHeaders(headersList.toArray(new Header[headersList.size()]));
		HttpResponse response = httpClient.execute(httpGet);
		return EntityUtils.toString(response.getEntity());
	}
	
	public String putRequest(String URI, List<Header> headersList) throws ClientProtocolException, IOException, CtfException {
		isOnline();
		HttpPut httpPut = new HttpPut(Constants.URL_SERVER + URI);
		httpPut.setHeaders(headersList.toArray(new Header[headersList.size()]));
		HttpResponse response = httpClient.execute(httpPut);
		return EntityUtils.toString(response.getEntity());
		
	}

	

}
