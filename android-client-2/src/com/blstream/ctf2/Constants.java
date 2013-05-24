package com.blstream.ctf2;

/**
 * Constants class. Stores variables.
 * 
 * @author Marcin Szmit [mod] Rafal Tatol
 */
public class Constants {
	public final static int SPLASH_SHOW_TIME = 2000;
	public final static String URL_SERVER = "http://capturetheflag.blstream.com:18080/demo";
	public final static String URI_PLAYERS_ADD = "/api/players/add";
	public final static String URI_LOGIN = "/oauth/token";
	public final static String URI_GAMES = "/api/secured/games";
	public final static String PLAYERS = "/players";

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
}
