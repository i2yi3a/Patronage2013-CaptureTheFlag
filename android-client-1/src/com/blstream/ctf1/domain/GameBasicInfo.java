package com.blstream.ctf1.domain;


/**
 * @author Adrian Swarcewicz
 */
public class GameBasicInfo {

	String id;

	String name;

	GameStatusType gameStatusType;

	String owner;

	Integer playersMax, playersCount;

	String timeStart;

	Localization localization;

	public GameBasicInfo() {
	}

	public GameBasicInfo(String id, String name, GameStatusType gameStatusType, String owner, Integer playersMax, Integer playersCount, String timeStart,
			Localization localization) {
		this.id = id;
		this.name = name;
		this.gameStatusType = gameStatusType;
		this.owner = owner;
		this.playersMax = playersMax;
		this.playersCount = playersCount;
		this.timeStart = timeStart;
		this.localization = localization;
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
	
	public Integer getPlayersMax() {
		return playersMax;
	}

	public void setPlayersMax(Integer playersMax) {
		this.playersMax = playersMax;
	}
	
	public Integer getPlayersCount() {
		return playersCount;
	}

	public void setPlayersCount(Integer playersCount) {
		this.playersCount = playersCount;
	}
	
	public Localization getLocalization() {
		return localization;
	}

	public void setLocalization(Localization localization) {
		this.localization = localization;
	}
	
	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	@Override
	public String toString() {
		return "GameBasicInfo [id=" + id + ", name=" + name + ", gameStatusType=" + gameStatusType + ", owner=" + owner + "]";
	}

}
