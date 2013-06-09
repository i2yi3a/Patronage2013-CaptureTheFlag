package com.blstream.ctf1.gamemodels;

/**
 * @author Piotr Marczycki
 */
public enum TeamType {
	RED("red"), BLUE("blue");

	private TeamType(final String type) {
		this.type = type;
	}

	private final String type;

	@Override
	public String toString() {
		return type;
	}
}
