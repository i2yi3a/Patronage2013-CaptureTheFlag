package com.blstream.ctf1.gamemodels;

/**
 * @author Piotr Marczycki
 */
public enum MarkerType {
	PLAYER("PLAYER"), RED_FLAG("RED_FLAG"), BLUE_FLAG("BLUE_FLAG");

	private MarkerType(final String type) {
		this.type = type;
	}

	private final String type;

	@Override
	public String toString() {
		return type;
	}
}
