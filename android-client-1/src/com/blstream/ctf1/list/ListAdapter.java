package com.blstream.ctf1.list;

import java.util.List;

import com.blstream.ctf1.R;
import com.blstream.ctf1.domain.GameBasicInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<GameBasicInfo>{

	private List<GameBasicInfo> items;

	public ListAdapter(Context context, List<GameBasicInfo> items) {
		super(context, R.layout.list_single_item, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View rowView = convertView;
	    if (rowView == null) {
	        LayoutInflater inflater = LayoutInflater.from(getContext());
	        rowView = inflater.inflate(R.layout.list_single_item, null);
	    }
	    
	    GameBasicInfo value = items.get(position);
	    
	    if (value != null) {
	    	TextView gameName = (TextView) rowView.findViewById(R.id.game_name);
	    	TextView gameID = (TextView) rowView.findViewById(R.id.id);
	    	if(gameName != null) {
	    		gameName.setText(value.getName());
	    	}
	    	if(gameID != null) {
	    		gameID.setText(value.getId());
	    	}
	    }
		return rowView;
	}
}
