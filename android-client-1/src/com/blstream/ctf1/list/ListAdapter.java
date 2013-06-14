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
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<GameBasicInfo> {

	private List<GameBasicInfo> items;

	public ListAdapter(Context context, List<GameBasicInfo> result) {
		super(context, R.layout.list_single_item, result);
		this.items = result;
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
			gameName.setSelected(true);
			TextView gameLocalization = (TextView) rowView.findViewById(R.id.game_localization);
			gameLocalization.setSelected(true);
			TextView gameTime = (TextView) rowView.findViewById(R.id.game_time);
			TextView gamePlayers = (TextView) rowView.findViewById(R.id.game_players);
			TextView gameMaxPlayers = (TextView) rowView.findViewById(R.id.game_max_players);
			ImageView gameCircleO = (ImageView) rowView.findViewById(R.id.players_circle_opened);
			ImageView gameSignedLable = (ImageView) rowView.findViewById(R.id.signed_game_label);
			gameSignedLable.setImageResource(R.drawable.list_signed_label);
			
			if (gameID != null) {
				gameID.setText(value.getId());
			}
			if (gameName != null) {
				gameName.setText(value.getName());
			}
			if (gameLocalization != null) {
				Double radius = value.getLocalization().getRadius()/1000;
				gameLocalization.setText(radius.toString() + "KM - " + value.getLocalization().getName().toUpperCase());
			}
			if (gameTime != null) {
				gameTime.setText(value.getTimeStart().toString());
			}
			if (gamePlayers != null) {
				gamePlayers.setText(value.getPlayersCount().toString());
			}
			if (gameMaxPlayers != null) {
				gameMaxPlayers.setText(value.getPlayersMax().toString());
			}
			if (gameCircleO != null) {
				if (gamePlayers.getText() == gameMaxPlayers.getText()) {
					gameCircleO.setImageResource(R.drawable.list_closed_circle);
				} else {
					gameCircleO.setImageResource(R.drawable.list_opened_circle);
				}
			}
			if (gameSignedLable != null) {
				if (false) { // if (do³¹czy³êm do gry) {
					gameSignedLable.setVisibility(View.VISIBLE);
				} else {
					gameSignedLable.setVisibility(View.GONE);
				}
			}
		}
		return rowView;
	}
}
