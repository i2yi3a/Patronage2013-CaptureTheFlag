package com.blstream.ctf1.domain;

/**
 * @author Adrian Swarcewicz
 */
public class GameFilter {
	private String name;
	
	private GameStatusType status;
	
	private Boolean myGames;

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

	public Boolean getMyGames() {
		return myGames;
	}

	public void setMyGames(Boolean myGames) {
		this.myGames = myGames;
	}	
}

