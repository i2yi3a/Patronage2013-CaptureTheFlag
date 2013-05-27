package com.blstream.ctf1.gps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.blstream.ctf1.R.string;
import com.blstream.ctf1.listener.GpsDefaultDialogActionListener;

/**
 * Call startLocationUpdates(), progress dialog will be show if waiting for gps
 * or fix. Waiting can be cancel by user, then attemptToCancelGpsMessage will be
 * show. And after cancel, GpsListener.onGpsCanceled will be call. If cancel
 * should be make programmatically instead by user call stopLocationUpdates().
 * Status of location updates can be check by isLocationUpdatesActive. Fix and
 * Gps status can be check too.
 * 
 * @author Adrian Swarcewicz
 */
public class GpsDefault extends Gps {

	private Context mContext;
	private ProgressDialog mProgressDialog;

	public GpsDefault(Context context, GpsListener gpsListener) {
		super(context);
		mContext = context;

		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setTitle(context.getString(string.gps_default_dialog_title));
		GpsDefaultDialogActionListener dialogActionListener = new GpsDefaultDialogActionListener(context, mProgressDialog, this, gpsListener);
		mProgressDialog.setOnCancelListener(dialogActionListener);
		mProgressDialog.setOnDismissListener(dialogActionListener);
		mProgressDialog.setOnKeyListener(dialogActionListener);
	}

	@Override
	protected void onStopLocationUpdates() {
		mProgressDialog.dismiss();
	}

	@Override
	protected void onGpsDisabled() {
		mProgressDialog.setMessage(mContext.getString(string.gps_disabled));
		if (((Activity) mContext).isFinishing() == false) {
			mProgressDialog.show();
		}
	}

	@Override
	protected void onGpsEnabled() {
		mProgressDialog.dismiss();
	}

	@Override
	protected void onFirstFixSearching() {
		mProgressDialog.setMessage(mContext.getString(string.gps_first_fix_searching));
		if (((Activity) mContext).isFinishing() == false) {
			mProgressDialog.show();
		}
	}

	@Override
	protected void onFoundFix() {
		mProgressDialog.dismiss();
		Toast.makeText(mContext, mContext.getString(string.gps_found_fix), Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onLostFix() {
		mProgressDialog.setMessage(mContext.getString(string.gps_lost_fix));
		if (((Activity) mContext).isFinishing() == false) {
			mProgressDialog.show();
		}
	}
}