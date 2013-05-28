package com.blstream.ctf2.services;

import android.app.AlertDialog;
import android.content.Context;

import com.blstream.ctf2.R;

/**
 * 
 * @author Lukasz Dmitrowski
 */

public class DialogServices {

	private Context mCtx;

	public DialogServices(Context ctx) {
		mCtx = ctx;
	}

	public void showAlert(int mAlertTextId) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
		builder.setMessage(mAlertTextId);
		builder.setPositiveButton(R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void showAlert(String mAlertText) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
		builder.setMessage(mAlertText);
		builder.setPositiveButton(R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
