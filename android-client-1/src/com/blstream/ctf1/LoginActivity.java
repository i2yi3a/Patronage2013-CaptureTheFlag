/**
 * @author Milosz_Skalski
 */
package com.blstream.ctf1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {

	private Button btnLogin;
	private Button btnRegistration;
	private EditText editLogin;
	private EditText editPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		btnRegistration = (Button) findViewById(R.id.btnRegistration);
		btnRegistration.setOnClickListener(this);
		
		editLogin = (EditText) findViewById(R.id.editLogin);
		editPassword = (EditText) findViewById(R.id.editPassword);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;

		switch(v.getId())
		{
		case R.id.btnLogin:
			// instruction login
			String login = editLogin.getText().toString();
			String password = editPassword.getText().toString();
			
			break;
		case R.id.btnRegistration:
			intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			break;
		}
	}
}
