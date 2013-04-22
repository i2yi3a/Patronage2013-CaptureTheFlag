package com.blstream.ctf1;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;


import android.app.Application;

@ReportsCrashes(formKey = "", // will not be used
formUri = "http://ctfcrashreports.cba.pl/dodaj.php",
customReportContent = { ReportField.USER_COMMENT,ReportField.USER_CRASH_DATE,ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT },
mode = ReportingInteractionMode.DIALOG,
resToastText = R.string.crash_toast_text, // optional, displayed as soon as the crash occurs, before collecting data which can take a few seconds
resDialogText = R.string.crash_dialog_text,
resDialogIcon = android.R.drawable.ic_dialog_info, //optional. default is a warning sign
resDialogTitle = R.string.crash_dialog_title, // optional. default is your application name
resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, // optional. when defined, adds a user text field input with this text resource as a label
resDialogOkToast = R.string.crash_dialog_ok_toast // optional. displays a Toast message when the user accepts to send a report.
)

/**
 * 	
 * @author Rafal_Olichwer
 *
 */
public class CaptureTheFlagApplication extends Application {
	
	public void onCreate() {
	    // The following line triggers the initialization of ACRA
	    super.onCreate();
	    ACRA.init(this);
	}
}
