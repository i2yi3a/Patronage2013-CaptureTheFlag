package com.blstream.ctf2.activity.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.R;
import com.blstream.ctf2.domain.GameDetails;
import com.blstream.ctf2.services.UserServices;

/**
 * 
 * @author Rafal Tatol
 *
 */
public class GameDetailsFragment extends Fragment {

	public final static String STATUS_NEW = "NEW";
	public final static String STATUS_IN_PROGRESS = "IN_PROGRESS";

	public TextView mGameNameTextView;
	public TextView mGameIdTextView;
	public TextView mDescriptionTextView;
	public TextView mDurationIdTextView;
	public TextView mLocalizationTextView;
	public TextView mLatitudeTextView;
	public TextView mLongitudeTextView;
	public TextView mRadiusTextView;
	public TextView mStatusTextView;
	public TextView mOwnerTextView;
	public TextView mTimeStartTextView;
	public TextView mPointsMaxTextView;
	public TextView mPlayersMaxTextView;
	public TextView mTeamRedName;
	public TextView mTeamRedBaseLocalization;
	public TextView mTeamBlueName;
	public TextView mTeamBlueBaseLocalization;
	public Button mJoinButton;
	public Button mEditButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.game_details_fragment, container, false);
		container.requestTransparentRegion(view);
		GameDetails gameDetails = (GameDetails) getArguments().getSerializable(Constants.KEY_GAME_DETAILS);
		if (gameDetails == null) {
			return view;
		}
		mGameNameTextView = (TextView) view.findViewById(R.id.textViewGameName);
		mGameIdTextView = (TextView) view.findViewById(R.id.textViewGameId);
		mDescriptionTextView = (TextView) view.findViewById(R.id.textViewDescription);
		mDurationIdTextView = (TextView) view.findViewById(R.id.textViewDuration);
		mLocalizationTextView = (TextView) view.findViewById(R.id.textViewLocalization);
		mLatitudeTextView = (TextView) view.findViewById(R.id.textViewLat);
		mLongitudeTextView = (TextView) view.findViewById(R.id.textViewLng);
		mRadiusTextView = (TextView) view.findViewById(R.id.textViewRadius);
		mStatusTextView = (TextView) view.findViewById(R.id.textViewStatus);
		mOwnerTextView = (TextView) view.findViewById(R.id.textViewOwner);
		mTimeStartTextView = (TextView) view.findViewById(R.id.textViewTimeStart);
		mPointsMaxTextView = (TextView) view.findViewById(R.id.textViewPointsMax);
		mPlayersMaxTextView = (TextView) view.findViewById(R.id.textViewPlayersMax);
		mTeamRedName = (TextView) view.findViewById(R.id.textViewTeamRed);
		mTeamBlueName = (TextView) view.findViewById(R.id.textViewTeamBlue);
		mJoinButton = (Button) view.findViewById(R.id.joinButton);
		mEditButton = (Button) view.findViewById(R.id.editButton);

		mGameNameTextView.setText(gameDetails.getName());
		mGameIdTextView.setText(gameDetails.getId());
		mDescriptionTextView.setText(gameDetails.getDescription());
		mDurationIdTextView.setText(gameDetails.getDuration().toString());
		mLocalizationTextView.setText(gameDetails.getLocalization().getName());
		mLatitudeTextView.setText(gameDetails.getLocalization().getLat().toString());
		mLongitudeTextView.setText(gameDetails.getLocalization().getLng().toString());
		mRadiusTextView.setText(gameDetails.getLocalization().getRadius().toString());
		mStatusTextView.setText(gameDetails.getStatus());
		mOwnerTextView.setText(gameDetails.getOwner());
		mTimeStartTextView.setText(gameDetails.getTimeStart());
		mPointsMaxTextView.setText(gameDetails.getPointsMax().toString());
		mPlayersMaxTextView.setText(gameDetails.getPlayersMax().toString());
		mTeamRedName.setText(gameDetails.getTeamRed().getName());
		mTeamBlueName.setText(gameDetails.getTeamBlue().getName());
		if (gameDetails.getStatus().equals(STATUS_NEW) || gameDetails.getStatus().equals(STATUS_IN_PROGRESS)) {
			mJoinButton.setEnabled(true);
			if (gameDetails.getOwner().equals(new UserServices(getActivity()).getUser().getName())) {
				mEditButton.setEnabled(true);
			}
		}
		return view;
	}
}
