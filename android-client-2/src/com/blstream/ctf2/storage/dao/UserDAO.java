package com.blstream.ctf2.storage.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blstream.ctf2.storage.entity.User;
import com.blstream.ctf2.storage.utils.UserSQLiteHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO {

	private SQLiteDatabase db;
	private UserSQLiteHelper dbHelper;

	private String[] allColumns = { 
			UserSQLiteHelper.COLUMN_ID,
			UserSQLiteHelper.COLUMN_NAME, 
			UserSQLiteHelper.COLUMN_TOKEN,
			UserSQLiteHelper.COLUMN_CREATED, 
			UserSQLiteHelper.COLUMN_LASTLOGIN,
			UserSQLiteHelper.COLUMN_VERSION };

	public UserDAO(Context context) {
		dbHelper = new UserSQLiteHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
	}

	public User createUser(String name) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(UserSQLiteHelper.COLUMN_NAME, name);
		contentValues.put(UserSQLiteHelper.COLUMN_TOKEN, "");
		contentValues.put(UserSQLiteHelper.COLUMN_CREATED,
				new Date().toString());
		contentValues.put(UserSQLiteHelper.COLUMN_LASTLOGIN, "");
		contentValues.put(UserSQLiteHelper.COLUMN_VERSION, new Integer(1));


		long insertId = db.insert(UserSQLiteHelper.TABLE_USER, null,
				contentValues);

		Cursor cursor = db.query(UserSQLiteHelper.TABLE_USER, allColumns,
				UserSQLiteHelper.COLUMN_ID + " = " + insertId, null, null,
				null, null);

		cursor.moveToFirst();
		User newUser = cursorToUser(cursor);
		cursor.close();
		return newUser;
	}
	
	
	public void deleteUser(User user) {
	    long id = user.getId();
	   
	    db.delete(UserSQLiteHelper.TABLE_USER, UserSQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	private User cursorToUser(Cursor cursor) {

		User user = new User();
//		user.setId(cursor.getInt(0));
//		user.setName(cursor.getString(1));
//		user.setToken(cursor.getString(2));
//		user.setCreated(cursor.getString(3));
//		user.setLastLogin(cursor.getString(4));
//		user.setVersion(cursor.getInt(5));
	
		user=fillAllFields(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
		
		return user;
	}
	
	private User fillAllFields(Integer id, String name, String token, String created, String lastLogin, Integer version){
		User user= new User();
		user.setId(id);
		user.setName(name);
		user.setToken(token);
		user.setCreated(created);
		user.setLastLogin(lastLogin);
		user.setVersion(version);
		return user;
	}

	public List<User> getUsers() {
		List<User> userList = new ArrayList<User>();
		
		Cursor cursor = db.query(UserSQLiteHelper.TABLE_USER, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		
	    while (!cursor.isAfterLast()) {
	    	User user = new User();
	    		user =fillAllFields(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
	    		userList.add(user);
	    	cursor.moveToNext();
	    }
		return userList;
	}
	
	public void update(User user){
		String where = UserSQLiteHelper.COLUMN_ID + user.getId();
		ContentValues contentValues = new ContentValues();
		contentValues.put(UserSQLiteHelper.COLUMN_NAME, user.getName());
		contentValues.put(UserSQLiteHelper.COLUMN_TOKEN, user.getToken());
		contentValues.put(UserSQLiteHelper.COLUMN_LASTLOGIN, user.getLastLogin());
		contentValues.put(UserSQLiteHelper.COLUMN_VERSION, user.getVersion()+1);
		db.update(UserSQLiteHelper.TABLE_USER, contentValues, where, null);		
	}
	
	
	public void createMockData() {
		createUser("Marcin");
		createUser("Marek");
		createUser("Wiesiek");
	}
}
