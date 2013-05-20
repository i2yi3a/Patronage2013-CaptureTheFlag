package com.blstream.ctf2.storage.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.blstream.ctf2.storage.entity.User;
import com.blstream.ctf2.storage.utils.UserSQLiteHelper;

/**
 * 
 * @author Marcin Sareło
 *
 */
public class UserDAO {

	private SQLiteDatabase db;
	private UserSQLiteHelper dbHelper;
	private Context context;

	private String[] allColumns = {
			UserSQLiteHelper.COLUMN_ID,
			UserSQLiteHelper.COLUMN_NAME,
			UserSQLiteHelper.COLUMN_TOKEN,
			UserSQLiteHelper.COLUMN_TOKEN_TYPE,
			UserSQLiteHelper.COLUMN_SCOPE,
			UserSQLiteHelper.COLUMN_CREATED,
			UserSQLiteHelper.COLUMN_LASTLOGIN,
			UserSQLiteHelper.COLUMN_VERSION
			};

	public UserDAO(Context context) {
		this.context=context;
	}

	public void open(){
		dbHelper = new UserSQLiteHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		db.close();
	}

	public User createUser(User user) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put(UserSQLiteHelper.COLUMN_NAME, user.getName());
		contentValues.put(UserSQLiteHelper.COLUMN_TOKEN, user.getToken());
		contentValues.put(UserSQLiteHelper.COLUMN_TOKEN_TYPE, user.getTokenType());
		contentValues.put(UserSQLiteHelper.COLUMN_SCOPE, user.getScope());
		contentValues.put(UserSQLiteHelper.COLUMN_CREATED,new Date().toString());
		contentValues.put(UserSQLiteHelper.COLUMN_LASTLOGIN, " ");
		contentValues.put(UserSQLiteHelper.COLUMN_VERSION, new Integer(1));

		long insertId = db.insert(UserSQLiteHelper.TABLE_USER, null,
				contentValues);

		Cursor cursor = db.query(UserSQLiteHelper.TABLE_USER, allColumns,
				UserSQLiteHelper.COLUMN_ID + " = " + insertId, null, null,
				null, null);
		Log.i("Database", "User "+user.getName()+" was added");

		cursor.moveToFirst();
		User newUser = cursorToUser(cursor);
		cursor.close();
		close();
		return newUser;
	}

	public void deleteUser(User user) {
		open();
		long id = user.getId();

		db.delete(UserSQLiteHelper.TABLE_USER, UserSQLiteHelper.COLUMN_ID
				+ " = " + id, null);
		close();
	}
	//TODO hardoced values 0,1,2.... convert to constatnts
	private User cursorToUser(Cursor cursor) {

		User user = new User();
		user = fillAllFields(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), cursor.getString(4),
				cursor.getString(5),
				cursor.getString(6),
				cursor.getInt(7));

		return user;
	}

	private User fillAllFields(Integer id, String name, String token,String tokenType,String scope,
			String created, String lastLogin, Integer version) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setToken(token);
		user.setTokenType(tokenType);
		user.setScope(scope);
		user.setCreated(created);
		user.setLastLogin(lastLogin);
		user.setVersion(version);
		return user;
	}

	public List<User> getUsers() {
		open();
		List<User> userList = new ArrayList<User>();

		Cursor cursor = db.query(UserSQLiteHelper.TABLE_USER, allColumns, null,
				null, null, null, null, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			User user = new User();
			user = fillAllFields(cursor.getInt(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7));
			userList.add(user);
			cursor.moveToNext();
		}
		close();
		return userList;
	}

	public User update(User user) {
		open();
		String where = UserSQLiteHelper.COLUMN_ID + "=" + user.getId();
		ContentValues contentValues = new ContentValues();
		contentValues.put(UserSQLiteHelper.COLUMN_NAME, user.getName());
		contentValues.put(UserSQLiteHelper.COLUMN_TOKEN, user.getToken());
		contentValues.put(UserSQLiteHelper.COLUMN_TOKEN_TYPE, user.getTokenType());
		contentValues.put(UserSQLiteHelper.COLUMN_LASTLOGIN,
				user.getLastLogin());
		contentValues.put(UserSQLiteHelper.COLUMN_VERSION,
				(user.getVersion() + 1));
		db.update(UserSQLiteHelper.TABLE_USER, contentValues, where, null);

		close();
		return user;
	}

}
