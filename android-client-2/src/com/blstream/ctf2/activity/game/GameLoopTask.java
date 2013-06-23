package com.blstream.ctf2.activity.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.Constants.TEAM;
import com.blstream.ctf2.domain.Localization;
import com.blstream.ctf2.domain.Team;
import com.blstream.ctf2.game.objects.Base;
import com.blstream.ctf2.game.objects.Gamer;
import com.google.android.gms.maps.model.Marker;

/**
 * @author Lukasz Dmitrowski
 */

public class GameLoopTask extends AsyncTask<String, Void, String> {

	GameActivity gameActivity;
	private int i;
	private Localization localization;
	private Gamer player;
	private Base base;
	public List<Marker> mTmpBasesMarkers = new ArrayList<Marker>();
	public List<Gamer> mTmpGamers = new ArrayList<Gamer>();
	public List<Base> mTmpBases = new ArrayList<Base>();

	public GameLoopTask(GameActivity gameActivity) {
		this.gameActivity = gameActivity;
		localization = new Localization();
		i = 0;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
		String result = null;
		JSONObject jsonResponse;
		JSONArray jsonMarkers;
		JSONArray jsonLocalization;
		JSONObject jsonInfo;
		JSONObject jsonActual;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(Constants.URL_MOCKUPS + params[0]);
			HttpResponse response = httpClient.execute(httpGet);
			result = EntityUtils.toString(response.getEntity());
			jsonResponse = new JSONObject(result);
			jsonMarkers = jsonResponse.getJSONArray("markers");
			jsonInfo = jsonResponse.getJSONObject("info");

			gameActivity.mRedScore = jsonInfo.get("red_points").toString();
			gameActivity.mBlueScore = jsonInfo.get("blue_points").toString();

			while (!(jsonMarkers.isNull(i))) {
				jsonActual = jsonMarkers.getJSONObject(i);
				if (jsonActual.get("type").equals("PLAYER")) {

					jsonLocalization = jsonActual.getJSONArray("loc");
					localization = new Localization(jsonLocalization.getDouble(0), jsonLocalization.getDouble(1));

					if (jsonInfo.get("team").equals("red"))
						player = new Gamer(TEAM.TEAM_RED, localization, gameActivity);
					else
						player = new Gamer(TEAM.TEAM_BLUE, localization, gameActivity);

					if (jsonActual.has("has_flag"))
						if (jsonActual.getString("has_flag").equals("true"))
							player.hasTakenFlag();

					gameActivity.mTmpGamers.add(player);

				} else if (jsonActual.get("type").equals("RED_FLAG")) {
					jsonLocalization = jsonActual.getJSONArray("loc");
					localization = new Localization(jsonLocalization.getDouble(0), jsonLocalization.getDouble(1));
					base = new Base(TEAM.TEAM_RED, localization, gameActivity, 1l);
					gameActivity.mTmpBases.add(base);

				} else if (jsonActual.get("type").equals("BLUE_FLAG")) {
					jsonLocalization = jsonActual.getJSONArray("loc");
					localization = new Localization(jsonLocalization.getDouble(0), jsonLocalization.getDouble(1));
					base = new Base(TEAM.TEAM_BLUE, localization, gameActivity, 1l);
					gameActivity.mTmpBases.add(base);
				}
				i++;
			}

		} catch (Exception e) {
			Log.e("GameLoopTaskDoInBackgroundException", e.toString());
		}

		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		gameActivity.drawAllMarkers();

	}

}
