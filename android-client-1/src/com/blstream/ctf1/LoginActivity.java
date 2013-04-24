/**
 * @author Milosz_Skalski
 * @author Rafal_Olichwer
 */
package com.blstream.ctf1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private Button mBtnLogin;
	private Button mBtnRegistration;
	private EditText mEditLogin;
	private EditText mEditPassword;
	
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
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;

		switch(v.getId()) {
			case R.id.btnLogin:
				// instruction login
				String login = mEditLogin.getText().toString();
				String password = mEditPassword.getText().toString();
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
					ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					
					if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting())
					    info+=getResources().getString(R.string.no_internet_connection);
					
					if(true) // need to check login
					{
						if(!info.isEmpty())
							info+='\n';
						info+=getResources().getString(R.string.login_exists);
					}
					if(!info.isEmpty())
					{	
						Toast.makeText(this, info, Toast.LENGTH_LONG).show();
						break;
					}
					else
					{
						
							
						
					}
				}
				else
				{
					Toast.makeText(this, info, Toast.LENGTH_LONG).show();
					break;
				}
			case R.id.btnRegistration:
				intent = new Intent(this, RegisterActivity.class);
				startActivity(intent);
				break;
		}
	}
}
