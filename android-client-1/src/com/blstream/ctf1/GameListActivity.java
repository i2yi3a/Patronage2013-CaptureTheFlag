/**
 * @author Mateusz Wi�niewski
 */
package com.blstream.ctf1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class GameListActivity extends Activity implements OnClickListener{

	private Button mBtnCreateNewGame; 
	private Button mBtnLogout;
	private Button mBtnPlayerProfile;
	private Spinner mSpinGameStatesFilter;
	private Spinner mSpinSearchGames;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_list);
		
		mBtnCreateNewGame = (Button) findViewById(R.id.btnCreateNewGame);
		mBtnCreateNewGame.setOnClickListener(this);
		
		mBtnLogout = (Button) findViewById(R.id.btnLogout);
		mBtnLogout.setOnClickListener(this);
		
		mBtnPlayerProfile = (Button) findViewById(R.id.btnPlayerProfile);
		mBtnPlayerProfile.setOnClickListener(this);
		
		mSpinGameStatesFilter = (Spinner) findViewById(R.id.spinGameStates);
		ArrayAdapter<CharSequence> adapterFilter = ArrayAdapter.
				createFromResource(this, R.array.states_filter_array, R.layout.spinner_layout);
		adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinGameStatesFilter.setAdapter(adapterFilter);
		
		mSpinSearchGames = (Spinner) findViewById(R.id.spinSearchGames);
		ArrayAdapter<CharSequence> adapterSearch = ArrayAdapter.
				createFromResource(this, R.string.app_name/*spis gier*/, R.layout.spinner_layout);
		adapterSearch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinSearchGames.setAdapter(adapterSearch);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_list, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;	
		switch (v.getId()) {
			case R.id.btnCreateNewGame:
				ClickTracker.saveClick(this, mBtnCreateNewGame);
				intent = new Intent(this, CreateGameActivity.class);
				startActivity(intent);
				break;
			case R.id.btnPlayerProfile:
				ClickTracker.saveClick(this, mBtnPlayerProfile);
				//Dodanie okienka dialog
			case R.id.btnLogout:
				ClickTracker.saveClick(this, mBtnLogout);
				Toast.makeText(this, R.string.logout_succesful, Toast.LENGTH_SHORT).show();
				finish();
				break;
		}
	}

}
