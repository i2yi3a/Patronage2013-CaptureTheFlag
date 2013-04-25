package com.blstream.ctf1.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


/**
 * @author Adrian Swarcewicz
 */

class StorageOpenHelper extends SQLiteOpenHelper {

	public StorageOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(StorageConstants.DB_CREATE_ALL_TABLES);
		Log.d(StorageConstants.DEBUG_TAG, "Database create");
	}

	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(StorageConstants.DEBUG_TAG, "Database upgrading from "
				+ Integer.toString(oldVersion) + " to "
				+ Integer.toString(newVersion) + "...");
		db.execSQL(StorageConstants.DB_DROP_ALL_TABLES);
		Log.d(StorageConstants.DEBUG_TAG, "All data lost");
		onCreate(db);
	}
	
}
