package com.blstream.ctf2;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

import com.blstream.ctf2.activity.login.Login;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * 
 * @author Rafal Tatol
 * @author [Google Analytics] Lukasz Dmitrowski
 * @author Karol Firmanty
 * 
 */
public class LoginRegisterActivity extends FragmentActivity {

	final static String TAB_LOGIN = "Login tab";
	final static String TAB_REGISTER = "Register tab";

	private Context mContext;
	private TabHost mTabHost;
	private LoginFragment mLoginFragment;
	private RegisterFragment mRegisterFragment;
	private EditText mRegUsernameEditText;
	private EditText mRegPassEditText;
	private EditText mRegRePassEditText;
	private EditText mLogUsernameEditText;
	private EditText mLogPasswordEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_register);
		mContext = this;
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setOnTabChangedListener(mTabListener);
		mTabHost.setup();
		mLoginFragment = new LoginFragment();
		mRegisterFragment = new RegisterFragment();
		initializeTabs();
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}

	public void onClickButton(View v) {
		switch (v.getId()) {
		case R.id.registerButton:
			EasyTracker.getTracker().sendEvent("ui_action", "button_press", "register_click", null);
			registerPlayer();
			break;
		case R.id.loginButton:
			EasyTracker.getTracker().sendEvent("ui_action", "button_press", "login_click", null);
			loginPlayer();
			break;
		}
	}

	TabHost.OnTabChangeListener mTabListener = new TabHost.OnTabChangeListener() {
		public void onTabChanged(String tabId) {
			if (tabId.equals(TAB_LOGIN)) {
				pushFragments(mLoginFragment);
			} else if (tabId.equals(TAB_REGISTER)) {
				pushFragments(mRegisterFragment);
			}
		}
	};

	public void registerPlayer() {
		mRegUsernameEditText = (EditText) findViewById(R.id.editTextUserame);
		mRegPassEditText = (EditText) findViewById(R.id.editTextPassword);
		mRegRePassEditText = (EditText) findViewById(R.id.editTextRepeatPassword);
		String mUsername = mRegUsernameEditText.getText().toString();
		String mPassword = mRegPassEditText.getText().toString();
		String mRepPass = mRegRePassEditText.getText().toString();
		if (isOnline() && correctRegistrationData(mUsername, mPassword, mRepPass)) {
			JSONObject newPlayer = new JSONObject();
			try {
				newPlayer.put(Constants.PLAYER_USERNAME, mUsername);
				newPlayer.put(Constants.PLAYER_PASSWORD, mPassword);
			} catch (JSONException e) {
				Log.e("registerPlayer JSONException", e.toString());
			}
			Register registerPlayer = new Register(mContext);
			registerPlayer.execute(newPlayer);
		}
	}

	public void loginPlayer() {
		mLogUsernameEditText = (EditText) findViewById(R.id.editTextLoginUserame);
		mLogPasswordEditText = (EditText) findViewById(R.id.editTextLoginPassword);
		if (!(mLogUsernameEditText.getText().toString().isEmpty()) && !(mLogPasswordEditText.getText().toString().isEmpty())) {
			Login login = new Login(mContext);
			login.execute(mLogUsernameEditText.getText().toString(), mLogPasswordEditText.getText().toString());
		}
	}

	public void loginResultNotification(String notification) {
		if (notification.equals(this.getString(R.string.login_successful))) {
			Intent intent = new Intent("com.blstream.ctf2.GAMELISTACTIVITY");
			startActivity(intent);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(notification);
			builder.setPositiveButton(R.string.ok, null);
			builder.show();
		}
	}

	public boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setMessage(R.string.no_internet_connection);
			builder.setPositiveButton(R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
			return false;
		}
	}

	public boolean correctRegistrationData(String mUsername, String mPassword, String mRepPass) {
		if (mUsername.length() < Constants.MIN_LENGTH_LOGIN || !mPassword.equals(mRepPass) || mPassword.length() < Constants.MIN_LENGTH_PASSWORD) {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setMessage(R.string.incorrect_data_description);
			builder.setTitle(R.string.incorrect_data);
			builder.setPositiveButton(R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
			return false;
		}
		return true;
	}

	public void pushFragments(Fragment fragment) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(android.R.id.tabcontent, fragment);
		ft.commit();
	}

	public void initializeTabs() {
		TabHost.TabSpec spec = null;
		mTabHost.setCurrentTab(0);

		spec = mTabHost.newTabSpec(TAB_LOGIN);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(android.R.id.tabcontent);
			}
		});
		spec.setIndicator(createTabView(R.string.login));
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(TAB_REGISTER);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(android.R.id.tabcontent);
			}
		});
		spec.setIndicator(createTabView(R.string.registration));
		mTabHost.addTab(spec);
	}

	private View createTabView(final int id) {
		View view = LayoutInflater.from(this).inflate(R.layout.login_register_tabs, null);
		((TextView) view.findViewById(R.id.tab_text)).setText(id);
		return view;
	}

	public static class LoginFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.login_fragment, container, false);
			return view;
		}
	}

	public static class RegisterFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.register_fragment, container, false);
			return view;
		}
	}

}
