/**
 * @author Milosz_Skalski
 * @author Rafal_Olichwer
 * @author Adrian Swarcewicz
 */
package com.blstream.ctf1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blstream.ctf1.asynchronous.Register;
import com.blstream.ctf1.service.NetworkService;



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
		switch (v.getId()) {
		
			case R.id.btnBack:
				ClickTracker.saveClick(this, mBtnBack);
				finish();
				break;
			case R.id.btnRegister:
				ClickTracker.saveClick(this, mBtnRegister);
				String login = mEditLoginReg.getText().toString();
				String password = mEditPasswordReg.getText().toString();
				String password2 = mEditPassword2Reg.getText().toString();
				
				if (correctData(login, password, password2)) {
					if (NetworkService.isDeviceOnline(this)) {
						Register register = new Register(this, LoginActivity.class, login, password);
						register.execute();
					}
					else {
						Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
					}
				}
				
				break;
		}
	}
	
	
	
	private boolean correctData(String login, String password, String password2) {
		boolean result = false;
		String info = "";
		if (login.length() < 5) {
			info+=getResources().getString(R.string.login_too_short);
		}
		
		if (password.length() < 5) {
			if(!info.isEmpty()) info += '\n';
			
			info += getResources().getString(R.string.password_too_short);
		}
		
		if (!password.equals(password2)) {
			if(!info.isEmpty())	info += '\n';
			info += getResources().getString(R.string.passwords_not_equal);
		}
		
		if (info.isEmpty()) {
			result = true;
		} else {
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
		}
		return result;
	}

}
