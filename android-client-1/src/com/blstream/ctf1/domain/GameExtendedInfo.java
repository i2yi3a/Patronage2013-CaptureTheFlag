package com.blstream.ctf1.domain;

import java.util.Date;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Adrian Swarcewicz
 */
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
	
	String redTeamName;
	
	String blueTeamName;
	
	LatLng redBase;
	
	LatLng blueBase;
	
	List<String> playersList;

	public GameExtendedInfo() {
	}

	public GameExtendedInfo(String id, String name, String description, Long duration, Localization localization, GameStatusType gameStatusType, String owner,
			Date timeStart, Integer pointsMax, Integer playersMax, String redTeamName,
			String blueTeamName, LatLng redBase, LatLng blueBase) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.localization = localization;
		this.gameStatusType = gameStatusType;
		this.owner = owner;
		this.timeStart = timeStart;
		this.pointsMax = pointsMax;
		this.playersMax = playersMax;
		this.redTeamName = redTeamName;
		this.blueTeamName = blueTeamName;
		this.redBase = redBase;
		this.blueBase = blueBase;
	}

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
	
	public List<String> getPlayersList() {
		return playersList;
	}

	public void setPlayersList(List<String> playersList) {
		this.playersList = playersList;
	}
	
	public String getBlueTeamName() {
		return blueTeamName;
	}
	
	public void setBlueTeamName(String blueTeamName) {
		this.blueTeamName = blueTeamName;
	}
	public String getRedTeamName() {
		return redTeamName;
	}
	
	public void setRedTeamName(String redTeamName) {
		this.redTeamName = redTeamName;
	}
	public LatLng getRedBase() {
		return redBase;
	}
	
	public void setRedBase(LatLng redBase) {
		this.redBase = redBase;
	}
	public LatLng getBlueBase() {
		return blueBase;
	}
	
	public void setBlueBase(LatLng blueBase) {
		this.blueBase = blueBase;
	}

	@Override
	public String toString() {
		return "GameExtendedInfo [id=" + id + ", name=" + name + ", description=" + description + ", duration=" + duration + ", localization=" + localization
				+ ", gameStatusType=" + gameStatusType + ", owner=" + owner + ", timeStart=" + timeStart + ", pointsMax=" + pointsMax + ", playersMax="
				+ playersMax + "]";
	}
}
