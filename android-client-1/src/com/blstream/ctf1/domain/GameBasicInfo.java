package com.blstream.ctf1.domain;

/**
 * @author Adrian Swarcewicz
 */
public class GameBasicInfo {

	String id;

	String name;

	GameStatusType gameStatusType;

	String owner;

	public GameBasicInfo() {
	}
	
	public GameBasicInfo(String id, String name,
			GameStatusType gameStatusType, String owner) {
		this.id = id;
		this.name = name;
		this.gameStatusType = gameStatusType;
		this.owner = owner;
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

	@Override
	public String toString() {
		return "GameBasicInfo [id=" + id + ", name=" + name
				+ ", gameStatusType=" + gameStatusType + ", owner=" + owner
				+ "]";
	}

}
