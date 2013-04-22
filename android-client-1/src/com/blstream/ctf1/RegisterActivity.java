/**
 * @author Milosz_Skalski
 * @author Rafal_Olichwer
 */
package com.blstream.ctf1;

import android.os.Bundle;
import android.app.Activity;
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
			if(login.length() < 5) {
				info+=getResources().getString(R.string.login_too_short)+'\n';
			} 
			if(password.length() < 5) {
				info+=getResources().getString(R.string.password_too_short)+'\n';
			} 
			if( !password.equals(password2) ) {
				info+=getResources().getString(R.string.passwords_not_equal)+'\n';
			}
					
			// need check internet connection
			
			if( 1!=1) {// need to check login
				info+=getResources().getString(R.string.login_exists)+'\n';
			}
						
			Toast.makeText(this,info, Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
