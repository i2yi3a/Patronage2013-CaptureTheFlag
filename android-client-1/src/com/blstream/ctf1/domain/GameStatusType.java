package com.blstream.ctf1.domain;

/**
 * @author Adrian Swarcewicz
 */
public enum GameStatusType {
	
	NEW("NEW"),
	IN_PROGRESS("IN_PROGRESS"),
	ON_HOLD("ON_HOLD"),
	COMPLETED("COMPLETED");

	private String type;

	private GameStatusType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static GameStatusType fromString(String type) {
		GameStatusType result = null;
		for (GameStatusType gst : GameStatusType.values()) {
			if (gst.getType().equalsIgnoreCase(type)) {
				result = gst;
			}
		}
		return result;
	}
}
