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

	private final Context context;
	private List<GameBasicInfo> items;

	public ListAdapter(Context context, List<GameBasicInfo> items) {
		super(context, R.layout.list_single_item, items);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 
		View view = convertView;
	    if (view == null) {
	    	LayoutInflater inflater = (LayoutInflater) context
	    			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	view = inflater.inflate(R.layout.list_single_item, parent, false);
	    }
	    
	    GameBasicInfo item = items.get(position);
	    if (item != null) {
	    	TextView textView = (TextView) view.findViewById(R.id.game_name);
	    	if(textView != null) {
	    		textView.setText(String.format("%s", item.getName()));
	    	}
	    }
		return view;
	}
}
