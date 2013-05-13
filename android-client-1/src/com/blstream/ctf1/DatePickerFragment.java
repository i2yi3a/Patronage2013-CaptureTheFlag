package com.blstream.ctf1;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.app.Dialog;


//public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener   {
public class DatePickerFragment extends FragmentActivity implements DatePickerDialog.OnDateSetListener   {
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
    //    return new DatePickerDialog(getActivity(), this, year, month, day);
        return new DatePickerDialog(this, this, year, month, day);
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// TODO Auto-generated method stub
		
	}

}
