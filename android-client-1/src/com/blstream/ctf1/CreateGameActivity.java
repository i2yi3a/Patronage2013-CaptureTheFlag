package com.blstream.ctf1;
/**
 * @author Milosz_Skalski
 * @TODO in switch btnCreate: where to get: lat lng and radius
 **/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.blstream.ctf1.asynchronous.CreateGame;
import com.blstream.ctf1.service.NetworkService;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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
			ClickTracker.saveClick(this, mBtnCancel);
			finish();
			break;
		case R.id.btnCreate:	
			ClickTracker.saveClick(this, mBtnCreate);
			String mGameName = mEditGameName.getText().toString();
			String mGameDescription = mEditGameDescription.getText().toString();
			String mLocationName = mEditLocationName.getText().toString();
			
			String mStartDate = mEditStartDate.getText().toString();
			mStartDate = mStartDate.replace(".", "-");
			mStartDate = mStartDate.replace("/", "-");
			
			String mStartTime = mEditStartTime.getText().toString();

			String mPlayingTimeTmp = mEditPlayingTime.getText().toString();
			String mMaxPlayersTmp = mEditMaxPlayers.getText().toString();
			String mMaxPointsTmp = mEditMaxPoints.getText().toString();
			
			if(correctData(mGameName, mLocationName, mStartDate, mStartTime, mPlayingTimeTmp)){
				if (NetworkService.isDeviceOnline(this)) {
					Log.d("Data", "" +mStartDate + "  " + mStartTime);
					
					if(mMaxPlayersTmp.length() == 0)
						mMaxPlayersTmp = "120"; // no limits?
					if(mMaxPointsTmp.length() == 0)
						mMaxPointsTmp = "120"; // no limits?
					
					long mPlayingTime = Integer.parseInt(mPlayingTimeTmp)*60*1000; // convert min on milliseconds
					int mMaxPlayers = Integer.parseInt(mMaxPlayersTmp);
					int mMaxPoints = Integer.parseInt(mMaxPointsTmp);
					
					CreateGame createGame = new CreateGame(this, CreateGameActivity.class, mGameName, mGameDescription, 
							mStartDate + " " + mStartTime, mPlayingTime, mMaxPoints, mMaxPlayers, mLocationName, 0.0 ,0.0 ,1 ); // here change: lat lng and radius
					createGame.execute();
				}
				else{
					Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}
	
	private boolean correctData(String gameName, String locationName, String mStartDate, String mStartTime, String playingTime){
		String info = "";
		boolean result = false;
		
		if(gameName.length() < 1){
			info+=getResources().getString(R.string.game_name_too_short);
		}
		
		if(locationName.length() < 1){
			if(!info.isEmpty()) info += '\n';
			info+=getResources().getString(R.string.location_name_too_short);
		}
				
		if(mStartDate.length() < 1){
			if(!info.isEmpty()) info += '\n';
			info+=getResources().getString(R.string.start_date_too_short);			
		} else {
			int tmp = 0;
			for(int i=0; i<mStartDate.length(); i++){
				if(mStartDate.charAt(i) == '-') 
					tmp++;
			}
			if(tmp != 2){
				if(!info.isEmpty()) info += '\n';
				info+=getResources().getString(R.string.start_date_incorrect);	
			}else{
				SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy"); 
				Date startDate = null;
				try {
					startDate = dateFormatter.parse(mStartDate);
					Log.d("Data", startDate.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(!info.isEmpty()) info += '\n';
					info+=getResources().getString(R.string.start_date_incorrect);	
				}	
			}
		}
		
		if(mStartTime.length() < 1){
			if(!info.isEmpty()) info += '\n';
			info+=getResources().getString(R.string.start_time_too_short);			
		} else{ 
			if( mStartTime.indexOf(":") == -1 || mStartTime.indexOf(":") == mStartTime.length()-1 || mStartTime.indexOf(":") == 0 || mStartTime.indexOf(":") != mStartTime.lastIndexOf(":") ){ //  no there ":" || ":" is end line || ":" is first char || ":" only one can be used
				if(!info.isEmpty()) info += '\n';
				info+=getResources().getString(R.string.start_time_incorrect);
			}else{
				Log.d("Data", "H:" + mStartTime.substring(0, mStartTime.indexOf(":")) + " M:" + mStartTime.substring(mStartTime.indexOf(":")+1, mStartTime.length()) );
				if( Integer.parseInt( mStartTime.substring(0, mStartTime.indexOf(":")) ) > 23 || Integer.parseInt(mStartTime.substring(mStartTime.indexOf(":")+1, mStartTime.length()))  > 59 )  {
					if(!info.isEmpty()) info += '\n';
					info+=getResources().getString(R.string.start_time_number_too_large);					
				}
			}
		}
		
		if(playingTime.length() < 1){
			if(!info.isEmpty()) info += '\n';
			info+=getResources().getString(R.string.playing_time_too_short);
		}
		
		if (info.isEmpty()) {
			result = true;
		} else {
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
		}
		
		return result;
	}

}
