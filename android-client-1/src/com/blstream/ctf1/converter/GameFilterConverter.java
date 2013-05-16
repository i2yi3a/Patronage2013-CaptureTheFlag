package com.blstream.ctf1.converter;

import org.json.JSONException;
import org.json.JSONObject;

import com.blstream.ctf1.domain.GameFilter;

/**
 * @author Adrian Swarcewicz
 */
public class GameFilterConverter {

	/**
	 * @param gameFilter
	 * @return JSONObject, can be empty if gameFilter is null or no filters set
	 * @throws JSONException
	 * @author Adrian Swarcewicz
	 */
	public static JSONObject toJSONObject(GameFilter gameFilter) throws JSONException {
		JSONObject result = new JSONObject();
		if (gameFilter != null) {
			result.put("name", gameFilter.getName());
			result.put("status", gameFilter.getStatus());
			result.put("myGames", gameFilter.getMyGames());
		}
		return result;
	}
}
