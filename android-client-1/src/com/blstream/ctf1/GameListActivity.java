package com.blstream.ctf1;

import com.blstream.ctf1.tracker.IssueTracker;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Mateusz Wisniewski
 */
public class GameListActivity extends ListActivity implements OnClickListener{
	
	private Button mBtnCreateGame;
	private String mId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_list);		
		
		mBtnCreateGame = (Button) findViewById(R.id.btnCreateGame);
		mBtnCreateGame.setOnClickListener(this);
		
		// Add Adapter
		
		ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() { // listening to single list item on click
    		@Override
        	public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
        	  String gameName = ((TextView) view).getText().toString(); // selected item 
        	  // Launching new Activity on selecting single List Item
        	  Intent intent = new Intent(getApplicationContext(), GameDetailsActivity.class);
        	  // sending data to new activity
        	  intent.putExtra("game_name", gameName);
        	  startActivity(intent);
          }
        });
	}

	@Override
	public void onClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.btnCreateGame:
			IssueTracker.saveClick(this, mBtnCreateGame);
			intent = new Intent(this, CreateGameActivity.class);
			startActivity(intent);
			break;		
		}
	}
}
