package com.blstream.ctf1;

public class Constants {
	public static final int SPLASHSCREEN_DELAY = 1000;
	
	public static final String URL_SERVER = "http://capturetheflag.blstream.com:18080/demo";
	
	public static final String URI_REGISTER_PLAYER = "/api/players/add";
	
	public static final String URI_LOGIN_PLAYER = "/oauth/token";
	
	public static final String URI_CREATE_GAME = "/api/secured/games";
	
	public static final String PACKAGE_NAME = "com.blstream.ctf1";
	
	public static final String CLIENT_ID = "mobile_android";
	
	public static final String CLIENT_SECRET = "secret";
		
	public static final String PREFIX_ERROR_CODE= "error_code_"; 
	
	public static final int ERROR_CODE_BAD_PASSWORD = 201;
	
	public static final int ERROR_CODE_BAD_USERNAME = 202;
	
	public static final int ERROR_CODE_BAD_TOKEN = 203;
}
