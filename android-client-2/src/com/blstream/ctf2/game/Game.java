package com.blstream.ctf2.game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.blstream.ctf2.Constants;
import com.blstream.ctf2.domain.Localization;
import com.blstream.ctf2.game.objects.Base;
import com.blstream.ctf2.game.objects.Flag;
import com.blstream.ctf2.game.objects.Gamer;
/**
 * 
 * @author Karol Firmanty
 *
 */
public class Game {
	private Map<String,Gamer> mGamersMap;
	private List<Base> mBasesList;
	private List<Flag> mFlagsList;
	private int mRedTeamScore;
	private int mBlueTeamScore;
	private String mGameId;
	public Game(String gameId){
		mGamersMap = new HashMap<String,Gamer>();
		mBasesList = new ArrayList<Base>();
		mFlagsList = new ArrayList<Flag>();
		mRedTeamScore = 0;
		mBlueTeamScore = 0;
		mGameId = gameId;
	}
	
	public void addGamer(Gamer gamer){
		mGamersMap.put(gamer.getName(), gamer);
	}
	
	public void addGamer(String name, Constants.TEAM team, Localization localization,Context context){
		mGamersMap.put(name, new Gamer(name, team, localization,context));
	}
	
	public void addFlag(Flag flag){
		mFlagsList.add(flag);
	}
	
	public void addFlag(Constants.TEAM team, Localization localization, Localization baseLocalization, Context context){
		mFlagsList.add(new Flag(team,localization, baseLocalization, context));
	}
	
	public void addBase(Base base){
		mBasesList.add(base);
	}
	
	public void addBase(Constants.TEAM team, Localization localization, Context context, long radius){
		mBasesList.add(new Base(team,localization,context,radius));
	}
	
	public Gamer getGamer(String name){
		return mGamersMap.get(name);
	}
	
	public Map<String,Gamer> getGamersMap(){
		return mGamersMap;
	}
	
	public List<Flag> getFlagsList(){
		return mFlagsList;
	}
	
	public List<Base> getBasesList(){
		return mBasesList;
	}
	
	public String getGameId(){
		return mGameId;
	}
	
	public void setTeamsScore(int redTeamScore, int blueTeamScore){
		mRedTeamScore = redTeamScore;
		mBlueTeamScore = blueTeamScore;
	}
	
	public void incrementScore(Constants.TEAM team){
		switch(team){
		case TEAM_RED:
			mRedTeamScore++;
			break;
		case TEAM_BLUE:
			mBlueTeamScore++;
			break;
		}
	}
}
