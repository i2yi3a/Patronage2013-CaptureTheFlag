package com.blstream.ctf2.exception;

import com.blstream.ctf2.Constants;

import android.content.res.Resources;
/**
 * 
 * @author Karol Firmanty
 *
 */
public class CtfException extends Exception{
	private static final long serialVersionUID = 677128549010362956L;
	private int mErrorCode;
	private Resources mResources;
	private String mMessage;
	
	/**
	 * 
	 * @param errorCode returned by server
	 */
	public CtfException(int errorCode, Resources resources){
		super();
		mResources = resources;
		mErrorCode = errorCode;
	}

	public CtfException(String message){
		super(message);
	}
	
	@Override
	public String getMessage(){
		if(super.getMessage() == null){
			String resourceName = Constants.ERROR_CODE_PREFIX+mErrorCode;
		if(mResources!=null){
			return mResources.getString(mResources.getIdentifier(resourceName, "string", Constants.PACKAGE));
			}
		else{
			return resourceName;
			}
		}
		else return super.getMessage();
	}
	
}
