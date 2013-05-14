package com.blstream.ctf1.tracker;

import org.acra.ACRA;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class IssueTracker {
	
	private static SharedPreferences mAcraPrefs;
	private static Editor mEditor;
	public static ClickTracker clickTracker;
	
	public IssueTracker(Context context) {
		clickTracker = new ClickTracker(context);
		ACRA.init((Application)context);
		setAcraFlag(true);
		setClickFlag(true);
		ClickTracker.init(context);
	}
	
	public static void setAcraFlag(boolean arg) {
		mAcraPrefs = ACRA.getACRASharedPreferences();
		mEditor = mAcraPrefs.edit();
		mEditor.putBoolean(ACRA.PREF_ENABLE_ACRA, arg);
		mEditor.commit();
	}
	
	public static void setClickFlag(boolean arg) {
		ClickTracker.setFlag(arg);
	}

}
