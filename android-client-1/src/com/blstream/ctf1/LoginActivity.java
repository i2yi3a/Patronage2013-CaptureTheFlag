/**
 * @author Milosz_Skalski
 * @author Rafal_Olichwer
 */
package com.blstream.ctf1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blstream.ctf1.asynchronous.Login;
import com.blstream.ctf1.service.NetworkService;
import com.blstream.ctf1.tracker.ClickTracker;

//TODO move header of class here
public class LoginActivity extends Activity implements OnClickListener {

	private Button mBtnLogin;
	private Button mBtnRegistration;
	private EditText mEditLogin;
	private EditText mEditPassword;
	@Override //TODO add empty line to seperate method from fields
	protected void onCreate(Bundle savedInstanceState) {
		
		//TODO remove empty lines
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mBtnLogin = (Button) findViewById(R.id.btnLogin);
		mBtnLogin.setOnClickListener(this);
		mBtnRegistration = (Button) findViewById(R.id.btnRegistration);
		mBtnRegistration.setOnClickListener(this);
		
		mEditLogin = (EditText) findViewById(R.id.editLogin);
		mEditPassword = (EditText) findViewById(R.id.editPassword);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch(v.getId()) {
			case R.id.btnLogin:
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.get(0);
				ClickTracker.saveClick(this, mBtnLogin);
				String login = mEditLogin.getText().toString();
				String password = mEditPassword.getText().toString();
				
				
				if(correctData(login, password)) {
					if (NetworkService.isDeviceOnline(this)) {
						Login loginTask = new Login(this, GameListActivity.class, login, password);
						loginTask.execute();
					}
					else {
						Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
					}
				}
				break;
					
			case R.id.btnRegistration:
				ClickTracker.saveClick(this, mBtnRegistration);
				intent = new Intent(this, RegisterActivity.class);
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
				info+='\n';//TODO hardcoded value should be final static 
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
