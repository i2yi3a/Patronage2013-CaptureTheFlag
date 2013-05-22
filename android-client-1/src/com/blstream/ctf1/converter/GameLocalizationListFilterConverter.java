package com.blstream.ctf1.converter;

import org.json.JSONException;
import org.json.JSONObject;

import com.blstream.ctf1.domain.GameLocalizationListFilter;

/**
 * @author Adrian Swarcewicz
 */
public class GameLocalizationListFilterConverter {

	private static final String STATUS = "status";
	private static final String RANGE = "range";
	private static final String LAT_LNG = "latLng";

	/**
	 * @author Adrian Swarcewicz
	 * @return JSONObject, can be empty if gameFilter is null or no filters set
	 */
	public static JSONObject toJSONObject(GameLocalizationListFilter gameFilter) throws JSONException {
		JSONObject result = new JSONObject();
		if (gameFilter != null) {
			result.put(LAT_LNG, LatLngConverter.toString(gameFilter.getLatLng()));
			result.put(RANGE, gameFilter.getRange());
			result.put(STATUS, gameFilter.getGameStatusType());
		}
		return result;
	}

	/**
	 * @author Adrian Swarcewicz
	 */
	public static String toQueryString(GameLocalizationListFilter gameFilter) throws JSONException {
		return JSONConverter.toQueryString(toJSONObject(gameFilter));
	}
}
