package com.blstream.ctf2.activity.game;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.services.UserServices;

/**
 * 
 * @author Rafal Tatol
 * 
 */
public class GameDetailsPlayersFragment extends Fragment {

	private ListView mListView;
	private ArrayAdapter<String> mListAdapter;
	private List<String> mPlayersList;
	private TextView mGameNameTextView;
	private Button mJoinButton;
	private Button mLeaveButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.game_details_players_fragment, container, false);
		container.requestTransparentRegion(view);

		mGameNameTextView = (TextView) view.findViewById(R.id.textViewGameName);
		mJoinButton = (Button) view.findViewById(R.id.joinButton);
		mLeaveButton = (Button) view.findViewById(R.id.leaveButton);
		
		// players list
		mPlayersList = new ArrayList<String>();
		mListView = (ListView) view.findViewById(R.id.list_players);
		mListView.setEmptyView(view.findViewById(R.id.list_empty));
		mPlayersList = (List<String>) getArguments().getStringArrayList(Constants.KEY_PLAYERS);
		mListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.players_list_item, mPlayersList);
		mListView.setAdapter(mListAdapter);

		// game name title
		String gameName = getArguments().getString(Constants.KEY_GAME_NAME);
		mGameNameTextView.setText(gameName);
		mGameNameTextView.setSelected(true);
		
		// Leave and Join buttons
		String gameStatus = getArguments().getString(Constants.KEY_GAME_STATUS);
		String userName = new UserServices(getActivity()).getUser().getName();
		if (gameStatus.equals(Constants.GAME_STATUS_NEW) || gameStatus.equals(Constants.GAME_STATUS_IN_PROGRESS)) {
			if (mPlayersList.contains(userName)) {
				mLeaveButton.setVisibility(View.VISIBLE);
			} else {
				mJoinButton.setVisibility(View.VISIBLE);
			}
		} else {
			mJoinButton.setVisibility(View.INVISIBLE);
			mLeaveButton.setVisibility(View.INVISIBLE);
		}

		return view;
	}

}
