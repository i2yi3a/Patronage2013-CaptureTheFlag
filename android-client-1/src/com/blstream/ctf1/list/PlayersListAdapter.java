package com.blstream.ctf1.list;

/**
 * @author Mateusz Wisniewski, Rafał Olichwer
 */

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.blstream.ctf1.R;

public class PlayersListAdapter extends ArrayAdapter<String>{

	private List<String> items;

	public PlayersListAdapter(Context context, List<String> items) {
		super(context, R.layout.playerslist_single_item, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View rowView = convertView;
	    if (rowView == null) {
	        LayoutInflater inflater = LayoutInflater.from(getContext());
	        rowView = inflater.inflate(R.layout.playerslist_single_item, null);
	    }
	    
	    String value = items.get(position);
	    
	    if (value != null) {
	    	TextView player = (TextView) rowView.findViewById(R.id.player_name);
	    	player.setText(value);
	    	
	    }
		return rowView;
	}
}
