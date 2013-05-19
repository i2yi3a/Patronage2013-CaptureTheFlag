package com.blstream.ctf2.domain;

/**
 * @author Rafal Tatol 
 * [mod] Marcin Sareło
 */
public class GameDetails extends Game {

	private String description;
	private Integer duration;
	private GameLocalization localization;
	private String timeStart;
	private Integer pointsMax;
	private Integer playersMax;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public GameLocalization getLocalization() {
		return localization;
	}

	public void setLocalization(GameLocalization localization) {
		this.localization = localization;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public Integer getPointsMax() {
		return pointsMax;
	}

	public void setPointsMax(Integer pointsMax) {
		this.pointsMax = pointsMax;
	}

	public Integer getPlayersMax() {
		return playersMax;
	}

	public void setPlayersMax(Integer playersMax) {
		this.playersMax = playersMax;
	}
}
