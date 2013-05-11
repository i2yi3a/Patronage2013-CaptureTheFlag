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

import com.blstream.ctf2.services.UserServices;
import com.blstream.ctf2.storage.entity.User;
import com.google.analytics.tracking.android.Log;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Login class
 * @author Karol Firmanty
 */
public class Login {
private Context mCtx;
private String mUserName;

	public Login(Context ctx){
		mCtx = ctx;
	}
	
	/**
	 * Return values
	 * 0 everything ok
	 * -1 bad credentials
	 * -2 problem with connection 
	 */
	public int userLogin(String userName, String password){
		if(!isOnline()) return -2;
		int result = -1;
		mUserName = userName;
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
				Log.e(e.toString());
			} catch (IOException e) {
				Log.e(e.toString());
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
				// Zapisywanie do bazy, oczekuje na fixa US
				UserServices userServices = new UserServices(mCtx);
				User user = userServices.addNewPlayer(mUserName);
				user.setToken(jsonObject.getString("access_token"));
				userServices.updateUser(user);
				return 0;
			}
		} catch (ParseException e) {
			Log.e(e.toString());
		} catch (IOException e) {
			Log.e(e.toString());
		} catch (JSONException e) {
			Log.e(e.toString());
		}
		return -1;
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
	
}