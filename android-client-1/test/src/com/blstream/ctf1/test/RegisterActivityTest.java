package com.blstream.ctf1.test;

import junit.framework.Assert;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.blstream.ctf1.LoginActivity;
import com.blstream.ctf1.R;
import com.blstream.ctf1.RegisterActivity;
import com.blstream.ctf1.test.utils.Orientation;
import com.blstream.ctf1.test.utils.Preconditions;
import com.jayway.android.robotium.solo.Solo;

/**
 * 
 * @author Piotr Marczycki
 * 
 */
public class RegisterActivityTest extends
		ActivityInstrumentationTestCase2<RegisterActivity> {
	private Solo mSolo;

	private final String mLoginShort = "logi";
	private final String mPasswordShort = "pswd";
	private final String mLoginLong = "longLogin";
	private final String mPasswordLong = "longPassword";
	private final int mLength = 5;

	private RegisterActivity mRegisterActivity;
	private EditText mLoginEditText;
	private EditText mPasswordEditText;
	private EditText mPasswordMatchEditText;

	private String mBackButton;
	private String mRegisterButton;

	private String mLoginTooShortMessage;
	private String mPasswordTooShortMessage;
	private String mPasswordDoesntMatchMessage;
	private String mRegisterSuccessful;
	private String mLoginInUse;
	private String mBothTooShortMessage;
	private String mAllBadTExtFields;

	public RegisterActivityTest() {
		super(RegisterActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mSolo = new Solo(getInstrumentation(), getActivity());
		checkPreconditions();

		mRegisterActivity = (RegisterActivity) mSolo.getCurrentActivity();
		Assert.assertNotNull("LoginActivity is null.", mRegisterActivity);

		mLoginEditText = (EditText) mRegisterActivity
				.findViewById(R.id.editLoginReg);
		Assert.assertNotNull("editLoginReg EditText is null.", mLoginEditText);

		mPasswordEditText = (EditText) mRegisterActivity
				.findViewById(R.id.editPasswordReg);
		Assert.assertNotNull("editPasswordReg EditText is null.",
				mPasswordEditText);

		mPasswordMatchEditText = (EditText) mRegisterActivity
				.findViewById(R.id.editPassword2Reg);
		Assert.assertNotNull("editPassword2Reg EditText is null.",
				mPasswordMatchEditText);

		mBackButton = mRegisterActivity.getResources().getString(R.string.back);
		Assert.assertNotNull("R.string.back String is null.", mBackButton);

		mRegisterButton = mRegisterActivity.getResources().getString(
				R.string.register);
		Assert.assertNotNull("R.string.register String is null.",
				mRegisterButton);

		mLoginTooShortMessage = mRegisterActivity.getResources().getString(
				R.string.login_too_short);
		Assert.assertNotNull("R.string.login_too_short String is null.",
				mLoginTooShortMessage);

		mPasswordTooShortMessage = mRegisterActivity.getResources().getString(
				R.string.password_too_short);
		Assert.assertNotNull("R.string.password_too_short String is null.",
				mPasswordTooShortMessage);

		mPasswordDoesntMatchMessage = mRegisterActivity.getResources().getString(
				R.string.passwords_not_equal);
		Assert.assertNotNull("R.string.passwords_not_equal String is null.",
				mPasswordDoesntMatchMessage);

		mRegisterSuccessful = mRegisterActivity.getResources().getString(
				R.string.registration_successful);
		Assert.assertNotNull("R.string.register_successful String is null.",
				mRegisterSuccessful);

		mLoginInUse = mRegisterActivity.getResources().getString(
				R.string.error_code_101);
		Assert.assertNotNull("R.string.error_code_101 String is null.",
				mLoginInUse);

		mBothTooShortMessage = mLoginTooShortMessage + '\n'
				+ mPasswordTooShortMessage;
		mAllBadTExtFields = mLoginTooShortMessage + '\n'
				+ mPasswordTooShortMessage + '\n' + mPasswordDoesntMatchMessage;
	}

	// TODO Robotium enterText password visible
	public void testRegistationFormInput() throws Exception {
		mSolo.enterText(mLoginEditText, mLoginShort);
		mSolo.enterText(mPasswordEditText, mPasswordLong);
		mSolo.enterText(mPasswordMatchEditText, mPasswordLong);
		mSolo.clickOnButton(mRegisterButton);
		Assert.assertTrue("Expected too short login message.",
				mSolo.searchText(mLoginTooShortMessage));
		mSolo.clearEditText(mLoginEditText);
		mSolo.clearEditText(mPasswordEditText);
		mSolo.clearEditText(mPasswordMatchEditText);

		mSolo.enterText(mLoginEditText, mLoginLong);
		mSolo.enterText(mPasswordEditText, mPasswordShort);
		mSolo.enterText(mPasswordMatchEditText, mPasswordShort);
		mSolo.clickOnButton(mRegisterButton);
		Assert.assertTrue("Expected too short password message.",
				mSolo.searchText(mPasswordTooShortMessage));
		mSolo.clearEditText(mLoginEditText);
		mSolo.clearEditText(mPasswordEditText);
		mSolo.clearEditText(mPasswordMatchEditText);

		mSolo.enterText(mLoginEditText, mLoginShort);
		mSolo.enterText(mPasswordEditText, mPasswordShort);
		mSolo.enterText(mPasswordMatchEditText, mPasswordShort);
		mSolo.clickOnButton(mRegisterButton);
		Assert.assertTrue("Expected too short login and password message.",
				mSolo.searchText(mBothTooShortMessage));
		mSolo.clearEditText(mLoginEditText);
		mSolo.clearEditText(mPasswordEditText);
		mSolo.clearEditText(mPasswordMatchEditText);
		
		mSolo.enterText(mLoginEditText, mLoginShort);
		mSolo.enterText(mPasswordEditText, mPasswordShort);
		mSolo.enterText(mPasswordMatchEditText, mPasswordLong);
		mSolo.clickOnButton(mRegisterButton);
		Assert.assertTrue("Expected too short login, password and password no match message.",
				mSolo.searchText(mAllBadTExtFields));
		mSolo.clearEditText(mLoginEditText);
		mSolo.clearEditText(mPasswordEditText);
		mSolo.clearEditText(mPasswordMatchEditText);

		mSolo.enterText(mLoginEditText, mLoginLong);
		mSolo.enterText(mPasswordEditText, mPasswordLong);
		mSolo.enterText(mPasswordMatchEditText, mPasswordLong);
		mSolo.clickOnButton(mRegisterButton);
		Assert.assertTrue("Expected login to exist message.",
				mSolo.searchText(mLoginInUse));
	}
	
	// TODO Figure out how to test successful registration
	public void testRegistration() {
		fail();
	}

	public void testOrientationChanged() throws Exception {
		mSolo.enterText(mLoginEditText, mLoginLong);
		mSolo.enterText(mPasswordEditText, mPasswordLong);
		mSolo.enterText(mPasswordMatchEditText, mPasswordLong);
		Orientation.change(mSolo);
		Assert.assertTrue("Typed login not preserved.",
				mSolo.searchText(mLoginLong));
		Assert.assertTrue("Typed password's not preserved.",
				mSolo.searchText(mPasswordLong,2));
	}

	public void testBackButton() {
		mSolo.getCurrentActivity().startActivity(new Intent(mSolo.getCurrentActivity(), LoginActivity.class));

		Activity firstObserved = mSolo.getCurrentActivity();
		firstObserved.startActivity(new Intent(firstObserved, RegisterActivity.class));
		mSolo.clickOnButton(mBackButton);
		
		Activity secondObserved = mSolo.getCurrentActivity();
		System.out.println(firstObserved + " =? " + secondObserved);
		Assert.assertNotSame("Back button should not leave RegisterActivity.", firstObserved, secondObserved);
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
