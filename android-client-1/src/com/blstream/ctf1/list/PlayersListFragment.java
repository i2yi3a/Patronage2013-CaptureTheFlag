package com.blstream.ctf1.list;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class PlayersListFragment extends ListFragment {
	
		private String mTag;
		private PlayersListAdapter mAdapter;
		
		public PlayersListFragment() {
		}


		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			// this is really important in order to save the state across screen
			// configuration changes for example
			setRetainInstance(true);
			// you only need to instantiate these the first time your fragment is
			// created; then, the method above will do the rest
			
			

		}
		
		public void setPlayersAdapter(PlayersListAdapter adapter) {
			
			getListView().setAdapter(adapter);
			
		}

		
		


}
