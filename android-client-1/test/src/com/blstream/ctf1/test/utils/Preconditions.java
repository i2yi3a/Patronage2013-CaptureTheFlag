package com.blstream.ctf1.test.utils;

/**
 * 
 * @author Piotr Marczycki
 * 
 */
public class Preconditions {

	public static boolean stringLengthGreaterThan(String mString, int mLength) {
		if (mString.length() > mLength) {
			return true;
		}
		return false;
	}
}
