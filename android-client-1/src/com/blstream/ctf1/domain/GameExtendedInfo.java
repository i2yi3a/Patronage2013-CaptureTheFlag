package com.blstream.ctf1.domain;

import java.util.Date;

public class GameExtendedInfo {
	String id;
	
	String name;
	
	String description;
	
	Long duration;
	
	Localization localization;
	
	GameStatusType gameStatusType;
	
	String owner;
	
	Date timeStart; // maybe to change if will be problems with json
	
	Integer pointsMax;
	
	Integer playersMax;

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

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Localization getLocalization() {
		return localization;
	}

	public void setLocalization(Localization localization) {
		this.localization = localization;
	}

	public GameStatusType getGameStatusType() {
		return gameStatusType;
	}

	public void setGameStatusType(GameStatusType gameStatusType) {
		this.gameStatusType = gameStatusType;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
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
