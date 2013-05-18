
package com.blstream.ctf1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.blstream.ctf1.tracker.IssueTracker;

/**
 * @author Mateusz Wi�niewski
 */
public class GameListActivity extends Activity implements OnClickListener{

	private Button mBtnCreateNewGame; 
	private Button mBtnLogout;
	private Button mBtnPlayerProfile;
	private String mId;
	
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
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_list, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;	
		switch (v.getId()) {
			case R.id.btnCreateNewGame:
				IssueTracker.saveClick(this, mBtnCreateNewGame);
				intent = new Intent(this, CreateGameActivity.class);
				startActivity(intent);
				break;
			case R.id.btnPlayerProfile:
				IssueTracker.saveClick(this, mBtnPlayerProfile);
				intent = new Intent(this, GameDetailsActivity.class);
				mId = "519753a2e4b06da6594775c0"; //will be deleted after list implementation complete
				intent.putExtra(Constants.EXTRA_KEY_ID, mId);
				startActivity(intent);
				break;
			case R.id.btnLogout:
				IssueTracker.saveClick(this, mBtnLogout);
				Toast.makeText(this, R.string.logout_succesful, Toast.LENGTH_SHORT).show();
				finish();
				break;
		}
	}

}
