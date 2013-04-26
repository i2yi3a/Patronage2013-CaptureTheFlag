package com.blstream.ctf1.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.blstream.ctf1.LoginActivity;
import com.blstream.ctf1.R;
import com.blstream.ctf1.RegisterActivity;
import com.blstream.ctf1.test.utils.Constants;
import com.blstream.ctf1.test.utils.Orientation;
import com.blstream.ctf1.test.utils.Preconditions;
import com.jayway.android.robotium.solo.Solo;

/**
 * 
 * @author Piotr Marczycki
 * 
 */
public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {
	private Solo mSolo;

	private final String mLoginShort = "logi";
	private final String mPasswordShort = "pswd";
	private final String mLoginLong = "longLogin";
	private final String mPasswordLong = "longPassword";
	private final int mLength = 5;

	private LoginActivity mLoginActivity;
	private EditText mLoginEditText;
	private EditText mPasswordEditText;

	private String mLoginButton;
	private String mRegistrationButton;

	private String mLoginTooShortMessage;
	private String mPasswordTooShortMessage;
	private String mBothTooShortMessage;

	public LoginActivityTest() {
		super(LoginActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mSolo = new Solo(getInstrumentation(), getActivity());
		checkPreconditions();

		mLoginActivity = (LoginActivity) mSolo.getCurrentActivity();
		Assert.assertNotNull("LoginActivity is null.", mLoginActivity);

		mLoginEditText = (EditText) mLoginActivity.findViewById(R.id.editLogin);
		Assert.assertNotNull("editLogin EditText is null.", mLoginEditText);

		mPasswordEditText = (EditText) mLoginActivity
				.findViewById(R.id.editPassword);
		Assert.assertNotNull("editPassword EditText is null.",
				mPasswordEditText);

		mLoginButton = mLoginActivity.getResources().getString(R.string.login);
		Assert.assertNotNull("R.string.login String is null.", mLoginButton);

		mRegistrationButton = mLoginActivity.getResources().getString(
				R.string.registration);
		Assert.assertNotNull("R.string.registration String is null.",
				mRegistrationButton);

		mLoginTooShortMessage = mLoginActivity.getResources().getString(
				R.string.login_too_short);
		Assert.assertNotNull("R.string.login_too_short String is null.",
				mLoginTooShortMessage);

		mPasswordTooShortMessage = mLoginActivity.getResources().getString(
				R.string.password_too_short);
		Assert.assertNotNull("R.string.password_too_short String is null.",
				mPasswordTooShortMessage);

		mBothTooShortMessage = mLoginTooShortMessage + "\n"
				+ mPasswordTooShortMessage;
	}

	// TODO Robotium enterText password visible
	public void testCredentialsLength() throws Exception {
		mSolo.enterText(mLoginEditText, mLoginShort);
		mSolo.enterText(mPasswordEditText, mPasswordLong);
		mSolo.clickOnButton(mLoginButton);
		Assert.assertTrue("Expected too short login message.",
				mSolo.searchText(mLoginTooShortMessage));
		mSolo.clearEditText(mLoginEditText);
		mSolo.clearEditText(mPasswordEditText);

		mSolo.enterText(mLoginEditText, mLoginLong);
		mSolo.enterText(mPasswordEditText, mPasswordShort);
		mSolo.clickOnButton(mLoginButton);
		Assert.assertTrue("Expected too short password message.",
				mSolo.searchText(mPasswordTooShortMessage));
		mSolo.clearEditText(mLoginEditText);
		mSolo.clearEditText(mPasswordEditText);

		mSolo.enterText(mLoginEditText, mLoginShort);
		mSolo.enterText(mPasswordEditText, mPasswordShort);
		mSolo.clickOnButton(mLoginButton);
		Assert.assertTrue("Expected too short password message.",
				mSolo.searchText(mBothTooShortMessage));
		mSolo.clearEditText(mLoginEditText);
		mSolo.clearEditText(mPasswordEditText);

		mSolo.enterText(mLoginEditText, mLoginLong);
		mSolo.enterText(mPasswordEditText, mPasswordLong);
		mSolo.clickOnButton(mLoginButton);
		fail();
	}

	public void testOrientationChanged() throws Exception {
		mSolo.enterText(mLoginEditText, mLoginLong);
		mSolo.enterText(mPasswordEditText, mPasswordLong);
		Orientation.change(mSolo);
		Assert.assertTrue("Typed login not preserved.",
				mSolo.searchText(mLoginLong));
		Assert.assertTrue("Typed password not preserved.",
				mSolo.searchText(mPasswordLong));
	}

	public void testRegistrationTransition() {
		mSolo.clickOnButton(mRegistrationButton);
		if (mSolo.waitForActivity(RegisterActivity.class, Constants.DELAY)) {
			Assert.assertSame("Did not transition to RegisterActivity",
					RegisterActivity.class, mSolo.getCurrentActivity()
							.getClass());
		} else {
			fail("Did not transition in time.");
		}
	}

	@Override
	protected void tearDown() throws Exception {
		mSolo.finishOpenedActivities();
		try {
			mSolo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		super.tearDown();
	}

	// TODO First reliable test as testPreconditions()
	public void checkPreconditions() throws Exception {
		Assert.assertTrue("Login too short.",
				Preconditions.stringLengthGreaterThan(mLoginLong, mLength));
		Assert.assertTrue("Password too short.",
				Preconditions.stringLengthGreaterThan(mPasswordLong, mLength));
		Assert.assertFalse("Login too long.",
				Preconditions.stringLengthGreaterThan(mLoginShort, mLength));
		Assert.assertFalse("Password too long.",
				Preconditions.stringLengthGreaterThan(mPasswordShort, mLength));
	}
}
