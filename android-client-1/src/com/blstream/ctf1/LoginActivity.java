/**
 * @author Milosz_Skalski
 * @author Rafal_Olichwer
 */
package com.blstream.ctf1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blstream.ctf1.asynchronous.Login;
import com.blstream.ctf1.service.NetworkService;

public class LoginActivity extends Activity implements OnClickListener {

	private Button mBtnLogin;
	private Button mBtnRegistration;
	private EditText mEditLogin;
	private EditText mEditPassword;
	private SharedPreferences preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mBtnLogin = (Button) findViewById(R.id.btnLogin);
		mBtnLogin.setOnClickListener(this);
		mBtnRegistration = (Button) findViewById(R.id.btnRegistration);
		mBtnRegistration.setOnClickListener(this);
		
		mEditLogin = (EditText) findViewById(R.id.editLogin);
		mEditPassword = (EditText) findViewById(R.id.editPassword);
		preferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		String log;
		SharedPreferences.Editor editor;
		switch(v.getId()) {
			case R.id.btnLogin:
				// instruction login
				String login = mEditLogin.getText().toString();
				String password = mEditPassword.getText().toString();
				log = preferences.getString("LOG", "defValue");
				log+="Login Button Clicked in LoginActivity";
				editor = preferences.edit();
				editor.putString("LOG", log);
				editor.commit();
				
				
				if(correctData(login, password)) {
					if (NetworkService.isDeviceOnline(this)) {
						Login loginTask = new Login(this, LoginActivity.class, login, password);
						loginTask.execute();
					}
					else {
						Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
					}
				}
				break;
					
			case R.id.btnRegistration:
				intent = new Intent(this, RegisterActivity.class);
				log = preferences.getString("LOG", "defValue");
				log+="Register Button Clicked in LoginActivity";
				editor = preferences.edit();
				editor.putString("LOG", log);
				editor.commit();
				startActivity(intent);
				break;
		}
	}
	private boolean correctData(String login, String password)
	{
		String info="";
		
		if(login.length()<5) 
		{
			info+=getResources().getString(R.string.login_too_short);
		}
		
		if(password.length()<5) 
		{
			if(!info.isEmpty())
				info+='\n';
			info+=getResources().getString(R.string.password_too_short) + '\n';
		}
		
		if(info.isEmpty())
		{
			return true;
		}
		
		Toast.makeText(this,info, Toast.LENGTH_SHORT).show();
		return false;
	}
}
