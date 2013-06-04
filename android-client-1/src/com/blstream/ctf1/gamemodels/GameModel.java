package com.blstream.ctf1.gamemodels;

import java.util.Date;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Piotr Marczycki
 */
public class GameModel {
	private LatLng gameCenter;
	private boolean gameStarted;
	private String id;
	private Date timestamp;
	private List<PlayerModel> players;
	private int redTeamScore;
	private int blueTeamScore;

	public GameModel() {
	}

	public GameModel(LatLng gameCenter, boolean gameStarted, String id, Date timestamp, List<PlayerModel> players, int redTeamScore, int blueTeamScore) {
		this.gameCenter = gameCenter;
		this.gameStarted = gameStarted;
		this.id = id;
		this.timestamp = timestamp;
		this.players = players;
		this.redTeamScore = redTeamScore;
		this.blueTeamScore = blueTeamScore;
	}

	public LatLng getGameCenter() {
		return gameCenter;
	}

	public void setGameCenter(LatLng gameCenter) {
		this.gameCenter = gameCenter;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<PlayerModel> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerModel> players) {
		this.players = players;
	}

	public int getRedTeamScore() {
		return redTeamScore;
	}

	public void setRedTeamScore(int redTeamScore) {
		this.redTeamScore = redTeamScore;
	}

	public int getBlueTeamScore() {
		return blueTeamScore;
	}

	public void setBlueTeamScore(int blueTeamScore) {
		this.blueTeamScore = blueTeamScore;
	}
}
