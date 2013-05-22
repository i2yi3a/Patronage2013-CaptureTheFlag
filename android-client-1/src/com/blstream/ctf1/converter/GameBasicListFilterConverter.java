package com.blstream.ctf1.converter;

import org.json.JSONException;
import org.json.JSONObject;

import com.blstream.ctf1.domain.GameBasicListFilter;

/**
 * @author Adrian Swarcewicz
 */
public class GameBasicListFilterConverter {

	private static final String MY_GAMES_ONLY = "myGamesOnly";
	private static final String STATUS = "status";
	private static final String NAME = "name";

	/**
	 * @author Adrian Swarcewicz
	 * @return JSONObject, can be empty if gameFilter is null or no filters set
	 */
	public static JSONObject toJSONObject(GameBasicListFilter gameFilter) throws JSONException {
		JSONObject result = new JSONObject();
		if (gameFilter != null) {
			result.put(NAME, gameFilter.getName());
			result.put(STATUS, gameFilter.getStatus());
			result.put(MY_GAMES_ONLY, gameFilter.getMyGamesOnly());
		}
		return result;
	}

	/**
	 * @author Adrian Swarcewicz
	 */
	public static String toQueryString(GameBasicListFilter gameFilter) throws JSONException {
		return JSONConverter.toQueryString(toJSONObject(gameFilter));
	}
}
