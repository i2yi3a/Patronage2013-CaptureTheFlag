package com.blstream.ctf1.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.blstream.ctf1.Constants;
import com.blstream.ctf1.JSONFields;
import com.blstream.ctf1.domain.GameBasicInfo;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.domain.GameStatusType;
import com.blstream.ctf1.domain.Localization;
import com.blstream.ctf1.gamemodels.GameModel;
import com.blstream.ctf1.gamemodels.PlayerModel;
import com.google.android.gms.maps.model.LatLng;

/**
 * From JSON to any class
 * 
 * @author Adrian Swarcewicz
 */
public class JSONConverter {

	/**
	 * @author Adrian Swarcewicz
	 */
	public static List<String> toPlayerNameStrings(JSONArray jsonArrayPlayerNames) throws JSONException {
		List<String> result = new LinkedList<String>();
		for (int i = 0; i < jsonArrayPlayerNames.length(); i++) {
			String playerName = (String) jsonArrayPlayerNames.get(i);
			result.add(playerName);
		}
		return result;
	}

	/**
	 * @param jsonObject
	 * @return query string based on jsonObject or null if no keys-value pair
	 *         found in jsonObject
	 * @throws JSONException
	 * @author Adrian Swarcewicz
	 */
	public static String toQueryString(JSONObject jsonObject) throws JSONException {
		StringBuilder stringBuilder = new StringBuilder();
		Iterator<?> jsonIterator = jsonObject.keys();

		if (!jsonIterator.hasNext()) {
			return null;
		}

		while (jsonIterator.hasNext()) {
			String key = (String) jsonIterator.next();
			String value = jsonObject.get(key).toString();
			stringBuilder.append(key + "=" + StringConverter.urlEncode(value) + "&");
		}

		stringBuilder.setLength(stringBuilder.length() - 1);

		return stringBuilder.toString();
	}

	/**
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 * @author Adrian Swarcewicz
	 */
	public static List<GameBasicInfo> toGameBasicInfo(JSONArray jsonArray) throws JSONException {
		List<GameBasicInfo> gameBasicInfos = new LinkedList<GameBasicInfo>();

		for (int i = 0; i < jsonArray.length(); i++) {
		GameBasicInfo gameBasicInfo = new GameBasicInfo();
		JSONObject jo = jsonArray.getJSONObject(i);

		try {
		gameBasicInfo.setId(jo.getString(JSONFields.ID));
		gameBasicInfo.setName(jo.getString(JSONFields.NAME));
		gameBasicInfo.setGameStatusType(GameStatusType.fromString(jo.getString(JSONFields.STATUS)));
		gameBasicInfo.setOwner(jo.getString(JSONFields.OWNER));
		gameBasicInfo.setPlayersCount(jo.getInt(JSONFields.PLAYERS_COUNT));
		gameBasicInfo.setPlayersMax(jo.getInt(JSONFields.PLAYERS_MAX));
		gameBasicInfo.setTimeStart(jo.getString(JSONFields.TIME_START));
		JSONObject jsonLocalization = jo.getJSONObject(JSONFields.LOCALIZATION);
		JSONArray latLng = jsonLocalization.getJSONArray(JSONFields.LAT_LNG);
		LatLng loc = new LatLng(latLng.getDouble(0), latLng.getDouble(1));
		Localization localization = new Localization(jsonLocalization.getString(JSONFields.NAME),jsonLocalization.getDouble(JSONFields.RADIUS),loc);
		gameBasicInfo.setLocalization(localization);
		gameBasicInfos.add(gameBasicInfo);
		} catch (JSONException e) {
		// if game doesn't have all required fields will be ignored
		}

		}
		return gameBasicInfos;
	}

	/**
	 * @param jsonObject
	 * @return GameExtendedInfo
	 * @throws JSONException
	 * @author Rafał Olichwer
	 */
	public static GameExtendedInfo toGameExtendedInfo(JSONObject jsonObject) throws JSONException, ParseException {
		GameExtendedInfo result = new GameExtendedInfo();
		try {
			result.setDescription(jsonObject.getString(JSONFields.DESCRIPTION));
			result.setDuration(toMinutes(jsonObject.getLong(JSONFields.DURATION)));
			result.setGameStatusType(GameStatusType.fromString(jsonObject.getString(JSONFields.STATUS)));
			JSONObject jsonLocalization = jsonObject.getJSONObject(JSONFields.LOCALIZATION);
			JSONArray jsonLatLng = jsonLocalization.getJSONArray(JSONFields.LAT_LNG);
			
			Localization localization = new Localization();
			localization.setName(jsonLocalization.getString(JSONFields.NAME));
			localization.setRadius(jsonLocalization.getDouble(JSONFields.RADIUS));
			localization.setLatLng(new LatLng(jsonLatLng.getDouble(0),jsonLatLng.getDouble(1)));
			result.setLocalization(localization);
			result.setId(jsonObject.getString(JSONFields.ID));
			result.setName(jsonObject.getString(JSONFields.NAME));
			result.setOwner(jsonObject.getString(JSONFields.OWNER));
			result.setPlayersMax(jsonObject.getInt(JSONFields.PLAYERS_MAX));
			result.setPointsMax(jsonObject.getInt(JSONFields.POINTS_MAX));
			Date timeStart = new SimpleDateFormat(Constants.DATE_FORMAT + " " + Constants.TIME_FORMAT).parse(jsonObject.getString("time_start"));
			result.setTimeStart(timeStart);
			JSONObject redTeam = jsonObject.getJSONObject(JSONFields.RED_TEAM_BASE);
			JSONObject blueTeam = jsonObject.getJSONObject(JSONFields.BLUE_TEAM_BASE);
			result.setRedTeamName(redTeam.getString(JSONFields.NAME));
			result.setBlueTeamName(blueTeam.getString(JSONFields.NAME));
			JSONArray redBase = redTeam.getJSONArray(JSONFields.LAT_LNG);
			JSONArray blueBase = blueTeam.getJSONArray(JSONFields.LAT_LNG);
			result.setRedBase(new LatLng(redBase.getDouble(0),redBase.getDouble(1)));
			result.setBlueBase(new LatLng(blueBase.getDouble(0),blueBase.getDouble(1)));
		} catch (JSONException e) {
			// if game doesn't have all required fields will be ignored
		}
		return result;
	}

	public static Long toMinutes(Long milis) {
		return milis / 60000;
	}

	public static GameModel toGameModel(JSONObject jsonObjectResult) throws Exception {
		GameModel result = new GameModel();
		if(jsonObjectResult.has(JSONFields.MARKERS)) {
			JSONArray markers = jsonObjectResult.getJSONArray(JSONFields.MARKERS);
			List<PlayerModel> players = new LinkedList<PlayerModel>();
			for (int i = 0; i < markers.length(); i++) {
				JSONObject player = markers.getJSONObject(i);
				players.add(toPlayerModel(player));
			}
			result.setPlayers(players);
			JSONObject info = jsonObjectResult.getJSONObject(JSONFields.INFO);
			result.setBlueTeamScore(info.getInt(JSONFields.BLUE_POINTS));
			result.setRedTeamScore(info.getInt(JSONFields.RED_POINTS));
			//result.setTimestamp(info.getLong(JSONFields.TIMESTAMP));
			
		}

		return result;
	}


	private static PlayerModel toPlayerModel(JSONObject player) throws Exception {
		PlayerModel result = new PlayerModel();
		JSONArray loc = player.getJSONArray(JSONFields.LOC);
		result.setLatLng(new LatLng(loc.getDouble(0),loc.getDouble(1)));
		if(player.has(JSONFields.HAS_FLAG))
			result.setHasFlag(player.getBoolean(JSONFields.HAS_FLAG));
		return result;
	}
}
