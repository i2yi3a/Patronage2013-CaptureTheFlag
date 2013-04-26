package com.blstream.ctf1.test.utils;

import android.content.res.Configuration;

import com.jayway.android.robotium.solo.Solo;

/**
 * 
 * @author Piotr Marczycki
 * 
 */
public class Orientation {
	public static void change(Solo mSolo) {
		if (mSolo.getCurrentActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			mSolo.setActivityOrientation(Solo.LANDSCAPE);
		if (mSolo.getCurrentActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			mSolo.setActivityOrientation(Solo.PORTRAIT);
	}
}
