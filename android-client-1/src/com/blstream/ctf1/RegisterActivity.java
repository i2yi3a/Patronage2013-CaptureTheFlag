/**
 * @author Milosz_Skalski
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

	private Button btnBack;
	private Button btnRegister;
	private EditText editLoginReg;
	private EditText editPasswordReg;
	private EditText editPassword2Reg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		btnBack = (Button) findViewById(R.id.btnBack);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnBack.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		
		editLoginReg = (EditText) findViewById(R.id.editLoginReg);
		editPasswordReg = (EditText) findViewById(R.id.editPasswordReg);
		editPassword2Reg = (EditText) findViewById(R.id.editPassword2Reg);
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
			String login = editLoginReg.getText().toString();
			String password = editPasswordReg.getText().toString();
			String password2 = editPassword2Reg.getText().toString();
			
			Toast.makeText(this, "Registration was successful", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
