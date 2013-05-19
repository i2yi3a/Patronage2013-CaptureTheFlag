package com.blstream.ctf1.pickers;

import java.util.Calendar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.app.Dialog;

/**
 * @author Milosz_Skalski
 */

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private Handler dialogPickerHandler;

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putString("data", day + "-" + (month+1) + "-" + year);
		msg.setData(data);
		dialogPickerHandler.sendMessage(msg);
	}

	public void setHandler(Handler h) {
		dialogPickerHandler = h;
	}
}
