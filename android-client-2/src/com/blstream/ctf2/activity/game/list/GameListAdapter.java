package com.blstream.ctf2.activity.game.list;

import java.util.List;

import com.blstream.ctf2.R;
import com.blstream.ctf2.domain.GameDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author Marcin Sareło
 */
public class GameListAdapter extends ArrayAdapter<GameDetails> {

	private List<GameDetails> items;

	public GameListAdapter(Context context, List<GameDetails> items) {
		super(context, R.layout.game_list_element, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			rowView = inflater.inflate(R.layout.game_list_element, null);
		}

		GameDetails value = items.get(position);

		if (value != null) {

			TextView gameName = (TextView) rowView.findViewById(R.id.game_name);
			TextView gameLocalization = (TextView) rowView.findViewById(R.id.game_localization);
			TextView gameTime = (TextView) rowView.findViewById(R.id.game_time);
			TextView gamePlayersCount = (TextView) rowView.findViewById(R.id.game_players_count);
			TextView gamePlayersMax = (TextView) rowView.findViewById(R.id.game_players_max);

			if (gameName != null) {
				gameName.setText(value.getName());
			}
			if (gameLocalization != null) {
				gameLocalization.setText(value.getLocalization().getRadius().toString() + " - " + value.getLocalization().getName());
			}
			if (gameTime != null) {
				gameTime.setText(value.getTimeStart().toString());
			}
			if (gamePlayersMax != null) {
				gamePlayersMax.setText(value.getPlayersMax().toString());
			}
			if (gamePlayersCount != null) {
				gamePlayersCount.setText(value.getPlayersCount().toString());
			}
		}
		return rowView;
	}
}