package com.blstream.ctf2.game.objects.image;

import com.blstream.ctf2.Constants;

/**
 * 
 * @author Karol Firmanty
 * 
 */
public interface GameObjectImage {
	public abstract void loadGamerImage(Constants.TEAM team);
	public abstract void loadBaseImage(Constants.TEAM team);
	public abstract void loadFlagImage(Constants.TEAM team);
	public abstract void changeGamerImage(Constants.TEAM team, boolean hasFlag);
}
