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
	 * @author Adrian Swarcewicz
	 * @return JSONArray if string can be converted, or null if not
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

	/**
	 * @author Adrian Swarcewicz
	 */
	public static String urlEncode(String parameter) {
		// parameter = parameter.replace(";", "%3B");
		// parameter = parameter.replace("?", "%3F");
		// parameter = parameter.replace("/", "%2F");
		// parameter = parameter.replace(":", "%3A");
		// parameter = parameter.replace("#", "%23");
		// parameter = parameter.replace("&", "%26");
		// parameter = parameter.replace("=", "%3D");
		// parameter = parameter.replace("+", "%2B");
		// parameter = parameter.replace("$", "%24");
		// parameter = parameter.replace(",", "%2C");
		parameter = parameter.replace(" ", "%20");
		// parameter = parameter.replace("%", "%25");
		// parameter = parameter.replace("<", "%3C");
		// parameter = parameter.replace(">", "%3E");
		// parameter = parameter.replace("~", "%7E");
		return parameter;
	}
}
