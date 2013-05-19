package com.blstream.ctf2.domain;


public class GameDetails {

	private String id;
	private String name;
	private String description;
	private Integer duration;
	private GameLocalization localization;
	private String status;
	private String owner;
	private String timeStart;
	private Integer pointsMax;
	private Integer playersMax;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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
