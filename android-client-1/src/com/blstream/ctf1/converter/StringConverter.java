package com.blstream.ctf1.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * From String to any class
 * 
 * @author Adrian
 */
public class StringConverter {

	/**
	 * @return JSONArray if string can be converted, or null if not
	 * @author Adrian Swarcewicz
	 */
	public static JSONArray toJSONArray(String jsonString) throws JSONException {
		JSONArray result = null;
		Object json = new JSONTokener(jsonString).nextValue();
		if (json instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) json;
			result = new JSONArray();
			result.put(jsonObject);
		} else if (json instanceof JSONArray) {
			result = (JSONArray) json;
		}
		return result;
	}
}
