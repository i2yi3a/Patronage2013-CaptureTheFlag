package com.blstream.ctf2.activity.game.list;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.domain.GameDetails;
import com.blstream.ctf2.services.GameServices;

/**
 * 
 * @author Marcin Sareło
 */
public class GameListBaseActivity extends Fragment {

	protected ListView mGameListView;
	private ArrayAdapter<GameDetails> mAdapter;
	protected List<GameDetails> mGames = new ArrayList<GameDetails>();

	private Button mBtnVieProfile;
	private Button mBtnCreateNewGame;
	private Button mBtnLogout;

	GameServices mGameServices;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.game_list_tmplate_fragment, container, false);
		container.requestTransparentRegion(view);

		mGameListView = (ListView) view.findViewById(R.id.gameList);
		mGameListView.setClickable(true);
		mGameServices = new GameServices(getActivity());

		mGameListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent game_details_intent = new Intent("com.blstream.ctf2.GAMEDETAILSACTIVITY");
				game_details_intent.putExtra(Constants.ID, mGames.get(position).getId());
				startActivity(game_details_intent);
			}
		});

		return view;
	}

	public List<GameDetails> getGames() {
		return mGames;
	}

	public ListView getmGameListView() {
		return mGameListView;
	}

	public void setmGameListView(ListView mGameListView) {
		this.mGameListView = mGameListView;
	}

	public ArrayAdapter<GameDetails> getmAdapter() {
		return mAdapter;
	}

	public void setmAdapter(ArrayAdapter<GameDetails> mAdapter) {
		this.mAdapter = mAdapter;
	}

	public Button getmBtnVieProfile() {
		return mBtnVieProfile;
	}

	public void setmBtnVieProfile(Button mBtnVieProfile) {
		this.mBtnVieProfile = mBtnVieProfile;
	}

	public Button getmBtnCreateNewGame() {
		return mBtnCreateNewGame;
	}

	public void setmBtnCreateNewGame(Button mBtnCreateNewGame) {
		this.mBtnCreateNewGame = mBtnCreateNewGame;
	}

	public Button getmBtnLogout() {
		return mBtnLogout;
	}

	public void setmBtnLogout(Button mBtnLogout) {
		this.mBtnLogout = mBtnLogout;
	}

	public void setGames(List<GameDetails> games) {
		this.mGames = games;

		mAdapter = new GameListAdapter(getActivity(), games);
		mGameListView.setAdapter(mAdapter);
	}
}
