package com.blstream.ctf2.activity.game;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.blstream.ctf2.R;

public class GameDetailsPlayersFragment extends Fragment {
	
	private ListView mListView;
	private ArrayAdapter<String> mListAdapter;
	private List<String> mPlayersList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.game_details_players_fragment, container, false);
		container.requestTransparentRegion(view);
		
		mPlayersList = new ArrayList<String>();
		mListView = (ListView) view.findViewById(R.id.list_players);
		mListView.setEmptyView(view.findViewById(R.id.list_empty));
		
		mPlayersList = (List<String>) getArguments().getStringArrayList("key");
		mListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.players_list_item, mPlayersList);
		mListView.setAdapter(mListAdapter);
		
		return view;
	}
	
}
