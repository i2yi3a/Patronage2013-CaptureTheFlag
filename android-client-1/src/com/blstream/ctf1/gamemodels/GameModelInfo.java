package com.blstream.ctf1.gamemodels;

/**
 * @author Piotr Marczycki
 */
public class GameModelInfo {
	private int redPoints;
	private int bluePoints;
	private String timestamp;
	private String team;

	public GameModelInfo(int red_points, int blue_points, String timestamp, String team) {
		this.redPoints = red_points;
		this.bluePoints = blue_points;
		this.timestamp = timestamp;
		this.team = team;
	}

	public int getRedPoints() {
		return redPoints;
	}

	public void setRed_points(int red_points) {
		this.redPoints = red_points;
	}

	public int getBlue_points() {
		return bluePoints;
	}

	public void setBlue_points(int blue_points) {
		this.bluePoints = blue_points;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
}
