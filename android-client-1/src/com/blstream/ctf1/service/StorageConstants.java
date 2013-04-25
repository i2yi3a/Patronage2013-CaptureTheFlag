package com.blstream.ctf1.service;



/**
 * @author Adrian Swarcewicz
 */

class StorageConstants {
	public static final String DEBUG_TAG = "Storage";
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "CAPTURE_THE_FLAG.db";
    
	// ------ LOGGED_PLAYER TABLE --------------------------------
	public static final String DB_LOGGED_PLAYER_TABLE = "LOGGED_PLAYER";
 
    public static final String KEY_ID = "LOGGED_PLAYER_ID";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY";
    public static final int ID_COLUMN = 0;
    
    public static final String KEY_LOGIN = "LOGIN";
    public static final String LOGIN_OPTIONS = "TEXT NOT NULL";
    public static final int LOGIN_COLUMN = 1;
    
    public static final String KEY_ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String ACCESS_TOKEN_OPTIONS = "TEXT NOT NULL";
    public static final int ACCESS_TOKEN_COLUMN = 2;
    
    public static final String KEY_TOKEN_TYPE = "TOKEN_TYPE";
    public static final String TOKEN_TYPE_OPTIONS = "TEXT NOT NULL";
    public static final int TOKEN_TYPE_COLUMN = 3;
    
    public static final String KEY_SCOPE = "SCOPE";
    public static final String SCOPE_OPTIONS = "TEXT NOT NULL";
    public static final int SCOPE_COLUMN = 4;
    
    public static final String DB_CREATE_LOGGED_PLAYER_TABLE =
    		"CREATE TABLE IF NOT EXISTS " + DB_LOGGED_PLAYER_TABLE
    		+ "(" + KEY_ID + " " + ID_OPTIONS + ", "
    		+ KEY_LOGIN + " " + LOGIN_OPTIONS + ", "
    		+ KEY_ACCESS_TOKEN + " " + ACCESS_TOKEN_OPTIONS + ", "
    		+ KEY_TOKEN_TYPE + " " + TOKEN_TYPE_OPTIONS + ", "
    		+ KEY_SCOPE + " " + SCOPE_OPTIONS
    		+ ");";
    
    public static final String DB_DROP_LOGGED_PLAYER_TABLE = 
    		"DROP TABLE IF EXISTS " + DB_LOGGED_PLAYER_TABLE;
    // -----------------------------------------------------------
    
    public static final String DB_CREATE_ALL_TABLES = DB_CREATE_LOGGED_PLAYER_TABLE;
    
    public static final String DB_DROP_ALL_TABLES = DB_DROP_LOGGED_PLAYER_TABLE;

}
