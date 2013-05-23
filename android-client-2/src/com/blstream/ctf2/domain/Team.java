package com.blstream.ctf2.domain;

/**
 * @author Rafal Tatol
 */
public class Team {
	
	private String name;
	private Localization baseLocalization;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Localization getBaseLocalization() {
		return baseLocalization;
	}
	
	public void setBaseLocalization(Localization baseLocalization) {
		this.baseLocalization = baseLocalization;
	}
}
