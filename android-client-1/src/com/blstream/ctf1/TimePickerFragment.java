package com.blstream.ctf1;

import java.util.Calendar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.app.Dialog;
import android.app.TimePickerDialog;

/**
 * @author Milosz_Skalski
 */

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	private Handler dialogPickerHandler;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Message msg = new Message();
        Bundle data = new Bundle();
        data.putString("time", hourOfDay + ":" + minute + ":00");
        msg.setData(data);
        dialogPickerHandler.sendMessage(msg);
	}
	
	public void setHandler(Handler h) {
		dialogPickerHandler = h;
	}
}
