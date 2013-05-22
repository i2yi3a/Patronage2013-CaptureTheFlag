package com.blstream.ctf1.domain;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Adrian Swarcewicz
 */
public class GameLocalizationListFilter {

	private LatLng latLng;
	private Double range;
	private GameStatusType gameStatusType;

	public GameLocalizationListFilter(LatLng latLng) {
		this.latLng = latLng;
	}

	public GameLocalizationListFilter(LatLng latLng, Double range, GameStatusType gameStatusType) {
		this.latLng = latLng;
		this.range = range;
		this.gameStatusType = gameStatusType;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public Double getRange() {
		return range;
	}

	public void setRange(Double range) {
		this.range = range;
	}

	public GameStatusType getGameStatusType() {
		return gameStatusType;
	}

	public void setGameStatusType(GameStatusType gameStatusType) {
		this.gameStatusType = gameStatusType;
	}

	@Override
	public String toString() {
		return "GameLocalizationListFilter [latLng=" + latLng + ", range=" + range + ", gameStatusType=" + gameStatusType + "]";
	}
}
