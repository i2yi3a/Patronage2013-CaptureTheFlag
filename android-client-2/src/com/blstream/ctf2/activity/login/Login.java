package com.blstream.ctf2.activity.login;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.LoginRegisterActivity;
import com.blstream.ctf2.R;
import com.blstream.ctf2.exception.CtfException;
import com.blstream.ctf2.services.HttpServices;
import com.blstream.ctf2.services.UserServices;
import com.blstream.ctf2.storage.entity.User;

/**
 * Login class
 * 
 * @author Karol Firmanty
 */
public class Login extends AsyncTask<String, Void, String> {
	private Context mCtx;
	private String mUserName;
	private HttpServices mHttpServices;
	private ProgressDialog mProgressDialog;

	public Login(Context ctx) {
		mCtx = ctx;
		mHttpServices = new HttpServices(mCtx);

	}

	public void userLogin(String userName, String password) throws CtfException,Exception{
		mUserName = userName;
		String params = "client_id=mobile_android&client_secret=secret&grant_type=password&username=" + userName + "&password=" + password;

		List<Header> headersList = new LinkedList<Header>();
		headersList.add(new BasicHeader("Content-type", "application/x-www-form-urlencoded"));
		String response = mHttpServices.postRequest(Constants.URI_LOGIN, params, headersList);
		JSONObject jsonObject = new JSONObject(response);
		if (jsonObject.has("error")) {
			throw new CtfException(jsonObject.getString("error_description"));
		} else if (jsonObject.has("access_token")) {
			UserServices userServices = new UserServices(mCtx);
			User user = userServices.addNewPlayer(mUserName);
			user.setToken(jsonObject.getString("access_token"));
			user.setTokenType((jsonObject.getString("token_type")));
			user.setScope(jsonObject.getString("scope"));
			userServices.updateUser(user);
		}

	}

	@Override
	protected String doInBackground(String... userCredentials) {
		try {
			userLogin(userCredentials[0], userCredentials[1]);
			return mCtx.getString(R.string.login_successful);
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog = new ProgressDialog(mCtx);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		LoginRegisterActivity loginActivity = (LoginRegisterActivity) mCtx;
		loginActivity.loginResultNotification(result);
		mProgressDialog.dismiss();
	}

}
