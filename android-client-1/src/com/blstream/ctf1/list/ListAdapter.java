package com.blstream.ctf1.list;

/**
 * @author Mateusz Wisniewski
 */

import java.util.List;

import com.blstream.ctf1.R;
import com.blstream.ctf1.domain.GameBasicInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<GameBasicInfo> {

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
			TextView gameID = (TextView) rowView.findViewById(R.id.id);
			TextView gameName = (TextView) rowView.findViewById(R.id.game_name);
			TextView gameOwner = (TextView) rowView.findViewById(R.id.game_owner);
			TextView gameStatus = (TextView) rowView.findViewById(R.id.game_status);
			if (gameID != null) {
				gameID.setText(value.getId());
			}
			if (gameName != null) {
				gameName.setText(value.getName());
			}
			if (gameOwner != null) {
				gameOwner.setText(value.getOwner());
			}
			if (gameStatus != null) {
				gameStatus.setText(value.getGameStatusType().toString());
			}
		}
		return rowView;
	}
}
