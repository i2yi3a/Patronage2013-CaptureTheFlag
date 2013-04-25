package com.blstream.ctf1.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.blstream.ctf1.domain.LoggedPlayer;



/**
 * Stores information in SQLite database. <br/>
 * Use open(), then make operation and close() connection.
 * @author Adrian Swarcewicz
 */

public class StorageService {
	
	private StorageOpenHelper mStorageOpenHelper;
	
	private Context mContext;
	
	private SQLiteDatabase mDb;
	
	
	
	
	
	public StorageService(Context context) {
		this.mContext = context;
	}
	
	
	
	/** 
	 * Open database connection to perform operations.
	 * If database operations performed close() should be call.
	 * @throws SQLiteException
	 */
	public void open() throws SQLiteException {
		mStorageOpenHelper = new StorageOpenHelper(mContext, StorageConstants.DB_NAME, 
				null, StorageConstants.DB_VERSION);
		mDb = mStorageOpenHelper.getWritableDatabase();
	}
	
	
	
	public void close() {
		mStorageOpenHelper.close();
	}
	
	
	
	public void saveLoggedPlayer(LoggedPlayer loggedPlayer) throws SQLException {
		// Only one logged player data can be saved
		deleteLoggedPlayer();
		ContentValues contentValues = new ContentValues();
		contentValues.put(StorageConstants.KEY_ID, 1);
		contentValues.put(StorageConstants.KEY_LOGIN, loggedPlayer.getLogin());
		contentValues.put(StorageConstants.KEY_ACCESS_TOKEN, loggedPlayer.getAccessToken());
		contentValues.put(StorageConstants.KEY_TOKEN_TYPE, loggedPlayer.getTokenType());
		contentValues.put(StorageConstants.KEY_SCOPE, loggedPlayer.getScope());
		mDb.insertOrThrow(StorageConstants.DB_LOGGED_PLAYER_TABLE, null, contentValues);
	}
	
	
	
	public void deleteLoggedPlayer() {
		String where = StorageConstants.KEY_ID + " = 1";
		mDb.delete(StorageConstants.DB_LOGGED_PLAYER_TABLE, where, null);
	}
	
	
	
	/**
	 * @return 
	 * Last saved logged player data or null if logged player
	 * data not found
	 */
	public LoggedPlayer getLoggedPlayer() {
		LoggedPlayer loggedPlayer = null;
		String[] columns = {StorageConstants.KEY_ID, StorageConstants.KEY_LOGIN,
				StorageConstants.KEY_ACCESS_TOKEN, StorageConstants.KEY_TOKEN_TYPE,
				StorageConstants.KEY_SCOPE};
		String where = StorageConstants.KEY_ID + " = 1";
		Cursor cursor = mDb.query(StorageConstants.DB_LOGGED_PLAYER_TABLE, columns, 
				where, null, null, null, null);
		if(cursor != null && cursor.moveToFirst()) {
			loggedPlayer = new LoggedPlayer(); 
			loggedPlayer.setLogin(cursor.getString(StorageConstants.LOGIN_COLUMN));
			loggedPlayer.setAccessToken(cursor.getString(StorageConstants.ACCESS_TOKEN_COLUMN));
			loggedPlayer.setTokenType(cursor.getString(StorageConstants.TOKEN_TYPE_COLUMN));
			loggedPlayer.setScope(cursor.getString(StorageConstants.SCOPE_COLUMN));
		}
		return loggedPlayer;
	}
}
