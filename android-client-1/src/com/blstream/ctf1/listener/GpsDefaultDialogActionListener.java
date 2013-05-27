package com.blstream.ctf1.listener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

import com.blstream.ctf1.R.string;
import com.blstream.ctf1.gps.Gps;
import com.blstream.ctf1.gps.GpsListener;

/**
 * @author Adrian Swarcewicz
 */
public class GpsDefaultDialogActionListener implements OnCancelListener, OnDismissListener, OnKeyListener {

	private Context mContext;
	private Dialog mDialog;
	private Gps mGps;
	private GpsListener mGpsListener;
	private ConfirmDialog mConfirmDialog;
	private boolean mAfterOnCancel;
	private String dialogTitle;
	private String dialogMessage;
	private String dialogAcceptButton;
	private String dialogCancelButton;

	public GpsDefaultDialogActionListener(Context context, Dialog dialog, Gps gps, GpsListener gpsListener) {
		mContext = context;
		mDialog = dialog;
		mGps = gps;
		mGpsListener = gpsListener;
		mConfirmDialog = new ConfirmDialog();

		dialogTitle = context.getString(string.gps_default_dialog_title);
		dialogMessage = context.getString(string.gps_default_dialog_message);
		dialogAcceptButton = context.getString(string.gps_default_dialog_accept_button);
		dialogCancelButton = context.getString(string.gps_default_dialog_cancel_button);
	}

	public String getDialogTitle() {
		return dialogTitle;
	}

	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}

	public String getDialogMessage() {
		return dialogMessage;
	}

	public void setDialogMessage(String dialogMessage) {
		this.dialogMessage = dialogMessage;
	}

	public String getDialogAcceptButton() {
		return dialogAcceptButton;
	}

	public void setDialogAcceptButton(String dialogAcceptButton) {
		this.dialogAcceptButton = dialogAcceptButton;
	}

	public String getDialogCancelButton() {
		return dialogCancelButton;
	}

	public void setDialogCancelButton(String dialogCancelButton) {
		this.dialogCancelButton = dialogCancelButton;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		// Dialogs should be still showed on cancel try. Confirm dialog is in
		// foreground and progress dialog in background
		mDialog.show();
		mConfirmDialog.show();
		mAfterOnCancel = true;
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		// on dismiss progress dialog and confirm dialog will be dismissed,
		// except dismiss after cancel
		if (mAfterOnCancel == false) {
			mConfirmDialog.dismiss();
		}
		mAfterOnCancel = false;
	}

	// Prevent dismiss dialog by menu key
	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return false;
	}

	private void cancelGpsUpdate() {
		mGpsListener.onGpsCanceled();
	}

	/**
	 * Confirm dialog with accept and cancel button
	 * 
	 * @author Adrian Swarcewicz
	 */
	private class ConfirmDialog implements OnClickListener, OnCancelListener {

		private AlertDialog mAlertDialog;

		private void makeAlertDialog() {
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
			dialogBuilder.setTitle(dialogTitle);
			dialogBuilder.setMessage(dialogMessage);
			dialogBuilder.setPositiveButton(dialogAcceptButton, this);
			dialogBuilder.setNegativeButton(dialogCancelButton, this);
			dialogBuilder.setOnCancelListener(this);
			mAlertDialog = dialogBuilder.create();
		}

		public void show() {
			if (mAlertDialog == null) {
				makeAlertDialog();
			}
			mAlertDialog.show();
		}

		public void dismiss() {
			if (mAlertDialog != null) {
				mAlertDialog.dismiss();
			}
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == DialogInterface.BUTTON_POSITIVE) {
				cancelGpsUpdate();
			} else if (which == DialogInterface.BUTTON_NEGATIVE) {
				onCancel(mAlertDialog);
			}
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			dialog.dismiss();
			if (mGps.isFix() == false || (mGps.isLocationUpdatesActive() && mGps.isGpsEnabled() == false)) {
				mDialog.show();
			}
		}
	}
}