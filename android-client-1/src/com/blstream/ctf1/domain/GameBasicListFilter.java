package com.blstream.ctf1.domain;

/**
 * @author Adrian Swarcewicz
 */
public class GameBasicListFilter {

	private String name;
	private GameStatusType status;
	private Boolean myGamesOnly;

	public GameBasicListFilter() {
	}

	/**
	 * null to skip filter parameter
	 */
	public GameBasicListFilter(String name, GameStatusType status, Boolean myGamesOnly) {
		this.name = name;
		this.status = status;
		this.myGamesOnly = myGamesOnly;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameStatusType getStatus() {
		return status;
	}

	public void setStatus(GameStatusType status) {
		this.status = status;
	}

	public Boolean getMyGamesOnly() {
		return myGamesOnly;
	}

	public void setMyGamesOnly(Boolean myGames) {
		this.myGamesOnly = myGames;
	}

	@Override
	public String toString() {
		return "GameFilter [name=" + name + ", status=" + status + ", myGamesOnly=" + myGamesOnly + "]";
	}
}
