package com.blstream.ctf2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.DatePicker;

/**
 * Game creation class
 * 
 * @author Kamil Wisniewski
 */

public class CreateGameActivity extends Activity {
	
	private EditText mGameName;
	private EditText mGameDescription;
	private TimePicker mGameTimeStart;
	private Button mPickLocalization;
	private Button mCreateGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creategame);
		
		mGameName = (EditText) findViewById(R.id.editTextGameName);
		mGameDescription = (EditText) findViewById(R.id.editTextGameDescription);
		mGameTimeStart = (TimePicker) findViewById(R.id.timePickerTimeStart);
		mPickLocalization = (Button) findViewById(R.id.buttonPickLocalization);
		
		mPickLocalization.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent1 = new Intent("com.blstream.ctf2.PICKLOCALIZATIONACTIVITY");
				startActivity(intent1);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	

}
