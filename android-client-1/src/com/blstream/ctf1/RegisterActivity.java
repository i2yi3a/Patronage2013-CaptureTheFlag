/**
 * @author Milosz_Skalski
 * @author Rafal_Olichwer
 */
package com.blstream.ctf1;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {

	private Button mBtnBack;
	private Button mBtnRegister;
	private EditText mEditLoginReg;
	private EditText mEditPasswordReg;
	private EditText mEditPassword2Reg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		mBtnBack = (Button) findViewById(R.id.btnBack);
		mBtnRegister = (Button) findViewById(R.id.btnRegister);
		mBtnBack.setOnClickListener(this);
		mBtnRegister.setOnClickListener(this);
		
		mEditLoginReg = (EditText) findViewById(R.id.editLoginReg);
		mEditPasswordReg = (EditText) findViewById(R.id.editPasswordReg);
		mEditPassword2Reg = (EditText) findViewById(R.id.editPassword2Reg);
		
		mBtnBack.setText(R.string.back);
		mBtnRegister.setText(R.string.register);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId())
		{
		case R.id.btnBack:
			finish();
			break;
		case R.id.btnRegister:
			// instruction register
			String login = mEditLoginReg.getText().toString();
			String password = mEditPasswordReg.getText().toString();
			String password2 = mEditPassword2Reg.getText().toString();
			String info="";
			if(login.length() < 5)
				info+=getResources().getString(R.string.login_too_short);
			
			if(password.length() < 5)
			{
				if(!info.isEmpty())
					info+='\n';
				info+=getResources().getString(R.string.password_too_short);
			}
			if( !password.equals(password2) )
			{
				if(!info.isEmpty())
					info+='\n';
				info+=getResources().getString(R.string.passwords_not_equal);
			}
			if(info.isEmpty())
			{
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				// TODO zapakowaæ sprawdzanie sieci jakos fajnie, zeby mozna bylo wywolywac z kazdej aktywnosci.
				if(!(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting()))
				{	
				    info+=getResources().getString(R.string.no_internet_connection);
				}
				
				JSONObject user = new JSONObject();
				try {
					user.put("username", login);
					user.put("password", password);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
					/*if(!info.isEmpty())
						info+='\n';
					info+=getResources().getString(R.string.login_exists);*/
				
				
				Toast.makeText(this,info, Toast.LENGTH_SHORT).show();
				break;
			}	
			else
			{
				Toast.makeText(this,info, Toast.LENGTH_SHORT).show();
				break;
			}	
			
		}
	}

}
