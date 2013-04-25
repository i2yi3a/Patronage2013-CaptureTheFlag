package com.blstream.ctf1.exception;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;

import com.blstream.ctf1.Constants;





/**
 * @author Adrian Swarcewicz
 */
public class CTFException extends Exception {

	private static final long serialVersionUID = -7158120379965739662L;

	private Integer mErrorCode;
	
	private Resources mResources;
	
	
	
	
	
	/**
	 * @param resources 
	 * 		needed to translate errorCode to error message stored in strings.xml
	 * @param errorCode
	 * @param errorMessage
	 */
	public CTFException(Resources resources, Integer errorCode, String errorMessage) {
		super(errorMessage);
		mResources = resources;
		mErrorCode = errorCode;
	}

	
	
	public Integer getErrorCode() {
		return mErrorCode;
	}
	
	
	
	/**
	 * @return change errorCode to String stored in strings.xml,
	 * if not found return getMessage() value.
	 */
	@Override
	public String getLocalizedMessage() {
		String localizedMessage = getMessage();
		if (mResources != null) {
			try {
				localizedMessage = mResources.getString(mResources.getIdentifier(Constants.PREFIX_ERROR_CODE
						+ mErrorCode, "string", Constants.PACKAGE_NAME));
			} catch (NotFoundException e) {
				// if not found getMessage() will be returned
			}
		}
		return localizedMessage;
	}



	@Override
	public String toString() {
		return "CTFException [mErrorCode=" + mErrorCode
				+ ", getMessage()=" + getMessage()
				+ ", getLocalizedMessage()=" + getLocalizedMessage() + "]";
	}
	
}
