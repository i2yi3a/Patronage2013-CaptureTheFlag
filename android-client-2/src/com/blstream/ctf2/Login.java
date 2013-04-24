package com.blstream.ctf2;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Login class
 * @author Karol Firmanty
 */
public class Login {
private Context ctx;

	public Login(Context ctx){
		this.ctx = ctx;
	}
	
	/*
	 * Return values
	 * 0 everything ok
	 * -1 bad credentials
	 * -2 problem with connection 
	 */
	public int userLogin(String userName, String password){
		if(!isOnline()) return -2;
		int result = -1;
		 HttpClient httpclient = new DefaultHttpClient();
	        try {
	            HttpPost httpPost = new HttpPost(Constants.URL_SERVER+Constants.URI_LOGIN);
	            httpPost.addHeader("Content-type" ,"application/x-www-form-urlencoded");
	            StringEntity stringEntity = new StringEntity("client_id=mobile_android&client_secret=secret&grant_type=password&username="+
	            userName+"&password="+password);
	            httpPost.setEntity(stringEntity);
	            HttpResponse response = httpclient.execute(httpPost);
	            result = afterLogin(response);
	        } catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            httpclient.getConnectionManager().shutdown();
	        }
		return result;
	}
	
	public int afterLogin(HttpResponse response){
		String jsonString;
		JSONObject jsonObject;
		try {
			jsonString = EntityUtils.toString(response.getEntity());
			jsonObject = new JSONObject(jsonString);
			if(jsonObject.has("error")){
				return -1;
			}
			else if(jsonObject.has("access_token")){
				//SAVE TO DATABASE
				return 0;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
	
}
