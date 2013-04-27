package com.blstream.ctf1;

public class Constants {
	public static final int SPLASHSCREEN_DELAY = 1000;
	
	public static final String URL_SERVER = "http://capturetheflag.blstream.com:18080/demo";
	
	public static final String URI_REGISTER_PLAYER = "/api/players/add";
	
	public static final String URI_LOGIN_PLAYER = "/oauth/token";
	
	public static final String PACKAGE_NAME = "com.blstream.ctf1";
	
	public static final String LOGIN_REQUEST_BODY = "client_id=mobile_android&client_secret=secret&grant_type=password";
	
	public static final String PREFIX_ERROR_CODE= "error_code_"; 
	
	public static final int ERROR_CODE_BAD_PASSWORD = 201;
	
	public static final int ERROR_CODE_BAD_USERNAME = 202;
	
	public static final int ERROR_CODE_BAD_TOKEN = 203;
}
