package com.blstream.ctf2.storage.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Marcin Sareło
 *
 */


public class UserSQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_USER = "user";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TOKEN = "token";
	public static final String COLUMN_CREATED = "created";
	public static final String COLUMN_LASTLOGIN = "lastLogin";
	public static final String COLUMN_VERSION = "version";
	private static final String DATABASE_NAME = "ctf2.db";
	private static final int DATABASE_VERSION = 1;


	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_USER + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
			", " + COLUMN_NAME + " TEXT NOT NULL" +
			", " + COLUMN_TOKEN + " TEXT NOT NULL" +
			", " + COLUMN_CREATED + " TEXT NOT NULL" +
			", " + COLUMN_LASTLOGIN + " TEXT NOT NULL" +
			", " + COLUMN_VERSION + " INTEGER NOT NULL);";

	public UserSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS"+DATABASE_NAME);

		onCreate(db);

	}

}
