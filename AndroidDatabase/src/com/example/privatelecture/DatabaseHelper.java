package com.example.privatelecture;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Hani Fakhouri
 *
 */

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "mydatabase.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_NAME = "mytable";
	public static final String COL_ID = "id";
	public static final String COL_FIRST_NAME = "name";
	public static final String COL_LAST_NAME = "family";
	public static final String COL_country = "country";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String create_query =
				"create table " + TABLE_NAME + "("
				+ COL_ID + " integer primary key autoincrement, "
				+ COL_FIRST_NAME + " text not null, "
				+ COL_LAST_NAME + " text not null, "
				+ COL_country + " text not null);";
		db.execSQL(create_query);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}