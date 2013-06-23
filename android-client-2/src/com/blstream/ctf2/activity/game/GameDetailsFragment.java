package com.blstream.ctf2.activity.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.domain.GameDetails;

/**
 * 
 * @author Rafal Tatol
 * 
 */
public class GameDetailsFragment extends Fragment {

	private TextView mGameNameTextView;
	private TextView mDescriptionTextView;
	private TextView mDurationIdTextView;
	private TextView mLocalizationTextView;
	private TextView mStatusTextView;
	private TextView mOwnerTextView;
	private TextView mTimeStartTextView;
	private TextView mPointsMaxTextView;
	private TextView mPlayersMaxTextView;
	private TextView mTeamRedName;
	private TextView mTeamBlueName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.game_details_fragment, container, false);
		container.requestTransparentRegion(view);
		GameDetails gameDetails = (GameDetails) getArguments().getSerializable(Constants.KEY_GAME_DETAILS);
		if (gameDetails == null) {
			return view;
		}
		mGameNameTextView = (TextView) view.findViewById(R.id.textViewGameName);
		mGameNameTextView.setSelected(true);
		mDescriptionTextView = (TextView) view.findViewById(R.id.textViewDescription);
		mDurationIdTextView = (TextView) view.findViewById(R.id.textViewDuration);
		mLocalizationTextView = (TextView) view.findViewById(R.id.textViewLocalization);
		mStatusTextView = (TextView) view.findViewById(R.id.textViewStatus);
		mOwnerTextView = (TextView) view.findViewById(R.id.textViewOwner);
		mTimeStartTextView = (TextView) view.findViewById(R.id.textViewTimeStart);
		mPointsMaxTextView = (TextView) view.findViewById(R.id.textViewPointsMax);
		mPlayersMaxTextView = (TextView) view.findViewById(R.id.textViewPlayersMax);
		mTeamRedName = (TextView) view.findViewById(R.id.textViewTeamRed);
		mTeamBlueName = (TextView) view.findViewById(R.id.textViewTeamBlue);

		mGameNameTextView.setText(gameDetails.getName());
		mDescriptionTextView.setText(gameDetails.getDescription());
		String duration = String.valueOf(gameDetails.getDuration() / 60 + "h " + gameDetails.getDuration() % 60 + "min");
		mDurationIdTextView.setText(duration);
		mLocalizationTextView.setText(gameDetails.getLocalization().getName());
		mStatusTextView.setText(gameDetails.getStatus());
		mOwnerTextView.setText(gameDetails.getOwner());
		mTimeStartTextView.setText(gameDetails.getTimeStart());
		mPointsMaxTextView.setText(gameDetails.getPointsMax().toString());
		mPlayersMaxTextView.setText(gameDetails.getPlayersMax().toString());
		mTeamRedName.setText(gameDetails.getTeamRed().getName());
		mTeamBlueName.setText(gameDetails.getTeamBlue().getName());

		return view;
	}
}
