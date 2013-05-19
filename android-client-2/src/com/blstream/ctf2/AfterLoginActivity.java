package com.blstream.ctf2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Temporary game menu
 * 
 * @author Kamil Wisniewski
 */

public class AfterLoginActivity extends Activity{
	
	private Button createGame;
	private Button editGame;
	private Button logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afterlogin);
		
		createGame = (Button) findViewById(R.id.createGameButton);
		createGame.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent1 = new Intent("com.blstream.ctf2.CREATEGAMEACTIVITY");
				startActivity(intent1);
			}
		});
		editGame = (Button) findViewById(R.id.createGameButton);
		logout = (Button) findViewById(R.id.createGameButton);
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
	
	public void onClickButton(View v) {
		switch (v.getId()) {
		case R.id.testButton: // TODO temporarily
			
			//519898b2e4b06da6594775e0 517629cee4b0b5f7642aa84d
			String gameId = "517629cee4b0b5f7642aa84d"; // TODO gameId == id of the selected game from the list
			
			Intent game_details_intent = new Intent("com.blstream.ctf2.GAMEDETAILSACTIVITY");
			game_details_intent.putExtra(Constants.ID, gameId);
			startActivity(game_details_intent);
			
			break;
		}
	}

}
