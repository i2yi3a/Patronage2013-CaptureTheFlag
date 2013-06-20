package com.blstream.ctf2;

/**
 * Constants class. Stores variables.
 * 
 * @author Marcin Szmit [mod] Rafal Tatol
 * [mod] Karol Firmanty 
 */
public class Constants {
	public final static int SPLASH_SHOW_TIME = 2000;
	public final static String PACKAGE = "com.blstream.ctf2";
	public final static String URL_SERVER = "http://capturetheflag.blstream.com:18080/demo";
	public final static String URI_PLAYERS_ADD = "/api/players/add";
	public final static String URI_LOGIN = "/oauth/token";
	public final static String URI_GAMES = "/api/secured/games";
	
	public final static String GAME_STATUS_NEW = "NEW";
	public final static String GAME_STATUS_IN_PROGRESS = "IN_PROGRESS";
	public final static String GAME_STATUS_COMPLETED = "COMPLETED";
	
	public final static String URI_GAMES_ALL=URI_GAMES;
	public final static String URI_GAMES_MY=URI_GAMES+"?myGamesOnly=true";
	public final static String URI_GAMES_COMPLETED=URI_GAMES+"?status="+GAME_STATUS_COMPLETED;
	public final static String URI_GAMES_NEW=URI_GAMES+"?status="+GAME_STATUS_NEW;
	public final static String URI_GAMES_IN_PROGRESS=URI_GAMES+"?status="+GAME_STATUS_IN_PROGRESS;
	public final static String URI_GAMES_NEAREST=URI_GAMES+"/nearest";

	
	public final static String PLAYERS = "/players";
	public final static String SIGN_IN = "/signIn";


	public final static String CONTENT_TYPE = "Content-Type";
	public final static String ACCEPT = "Accept";
	public final static String APPLICATION_JSON = "application/json";
	public final static String AUTHORIZATION = "Authorization";

	public final static String RESPONSE_ERROR_CODE = "error_code";
	public final static String RESPONSE_ERROR = "error";
	public final static String RESPONSE_DESCRIPTION = "error_description";
	public final static String PLAYER_USERNAME = "username";
	public final static String PLAYER_PASSWORD = "password";
	public final static int MIN_LENGTH_LOGIN = 5;
	public final static int MIN_LENGTH_PASSWORD = 5;
	public final static String ERROR = "error";
	public final static String ERROR_CODE = "error_code";
	public final static String ERROR_DESCRIPTION = "error_description";
	public final static int ERROR_CODE_SUCCESS = 0;
	public final static int ERROR_CODE_CANNOT_CREATE_NEW_PLAYER = 100;
	public final static int ERROR_CODE_PLAYER_ALREADY_EXISTS = 101;
	public final static String ID = "id";
	
	public final static String KEY_GAME_AREA = "gameArea";
	public final static String KEY_GAME_DETAILS = "gameDetails";
	public final static String KEY_TEAM_BLUE = "teamBlue";
	public final static String KEY_TEAM_RED = "teamRed";
	public final static String KEY_PLAYERS = "players";
	public final static String KEY_GAME_NAME = "game name";
	
	public final static String URL_MOCKUPS = "http://patronage.tk/json/";

	public final static String ERROR_CODE_PREFIX = "error_code_";
	
	public static enum TEAM{
		TEAM_RED,
		TEAM_BLUE
	}
	public static enum GAME_OBJECT_TYPE{
		FLAG,
		GAMER,
		BASE
	}
}