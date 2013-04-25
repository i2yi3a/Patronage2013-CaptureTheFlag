package com.blstream.ctf1.test;

import junit.framework.Assert;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.blstream.ctf1.Constants;
import com.blstream.ctf1.SplashScreen;
import com.blstream.ctf1.test.utils.Orientation;
import com.jayway.android.robotium.solo.Solo;

/**
 * 
 * @author Piotr Marczycki
 * 
 */
public class SplashScreenTest extends
		ActivityInstrumentationTestCase2<SplashScreen> {
	private Activity mFirstObserved;
	private Activity mSecondObserved;
	private Solo mSolo;

	public SplashScreenTest() {
		super(SplashScreen.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mFirstObserved = null;
		mSecondObserved = null;
		mSolo = new Solo(getInstrumentation(), getActivity());
	}

	public void testStartingNextActivity() throws Exception {
		mFirstObserved = mSolo.getCurrentActivity();
		synchronized (mSolo) {
			mSolo.wait(Constants.SPLASHSCREEN_DELAY);
		}
		mSecondObserved = mSolo.getCurrentActivity();
		Assert.assertNotSame("Transition did not occur in time.",
				mFirstObserved, mSecondObserved);
	}

	public void testAbortOnBackKey() throws Exception {
		mFirstObserved = mSolo.getCurrentActivity();
		mSolo.goBack();
		synchronized (mSolo) {
			mSolo.wait(Constants.SPLASHSCREEN_DELAY);
		}
		mSecondObserved = mSolo.getCurrentActivity();
		Assert.assertSame("Back key did not stop application.", mFirstObserved,
				mSecondObserved);
	}

	public void testOrientationChanged() throws Exception {
		Orientation.change(mSolo);
		synchronized (mSolo) {
			mSolo.wait(Constants.SPLASHSCREEN_DELAY);
		}
		mFirstObserved = mSolo.getCurrentActivity();
		mSolo.goBack();
		mSecondObserved = mSolo.getCurrentActivity();
		Assert.assertSame("Launched more than one activity.", mFirstObserved,
				mSecondObserved);
	}

	@Override
	protected void tearDown() throws Exception {
		mSolo.finishOpenedActivities();
		super.tearDown();
	}
}
