package com.blstream.ctf1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;

public class CreateGameActivity extends Activity implements OnClickListener {

	private Button mBtnCancel;
	private Button mBtnCreate;
	private EditText mEditGameName;
	private EditText mEditGameDescription;
	private EditText mEditLocationName;
	private EditText mEditStartDate;
	private EditText mEditStartTime;
	private EditText mEditPlayingTime;
	private EditText mEditMaxPlayers;
	private EditText mEditMaxPoints;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
		
		mBtnCancel = (Button) findViewById(R.id.btnCancel);
		mBtnCreate = (Button) findViewById(R.id.btnCreate);
		mBtnCancel.setOnClickListener(this);
		mBtnCreate.setOnClickListener(this);
		
		mEditGameName = (EditText) findViewById(R.id.editGameName);
		mEditGameDescription = (EditText) findViewById(R.id.editGameDescription);
		mEditLocationName = (EditText) findViewById(R.id.editLocationName);
		mEditStartDate = (EditText) findViewById(R.id.editStartDate);
		mEditStartTime = (EditText) findViewById(R.id.editStartTime);
		mEditPlayingTime = (EditText) findViewById(R.id.editPlayingTime);
		mEditMaxPlayers = (EditText) findViewById(R.id.editMaxPlayers);
		mEditMaxPoints = (EditText) findViewById(R.id.editMaxPoints);	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btnCancel:
			finish();
			break;
		case R.id.btnCreate:		
			String gameName = mEditGameName.getText().toString();
			String gameDescription = mEditGameDescription.getText().toString();
			String locationName = mEditLocationName.getText().toString();
			
			//String startDate = mEditStartDate.getText().toString();
			//String startTime = mEditStartTime.getText().toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("m");
			try {
				Date startDate = sdf.parse(mEditStartDate.getText().toString());
				Date startTime = sdf2.parse(mEditStartTime.getText().toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			int playingTime = Integer.parseInt(mEditPlayingTime.getText().toString());
			int maxPlayers = Integer.parseInt(mEditMaxPlayers.getText().toString());
			int maxPoints = Integer.parseInt(mEditMaxPoints.getText().toString());
			
			break;
		}
	}

}
