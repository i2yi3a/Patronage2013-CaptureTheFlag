package com.blstream.ctf1.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * From InputStream to any class
 * 
 * @author Adrian Swarcewicz
 */
public class InputStreamConverter {

	/**
	 * @author Adrian Swarcewicz
	 */
	public static String toString(InputStream content) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		String line;
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		return builder.toString();
	}
}
