package com.example.privatelecture;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author Hani Fakhouri
 *
 */

public class DatabaseManager {

	private SQLiteDatabase db;
	private DatabaseHelper helper;
	private String[] columns = new String[]{
			DatabaseHelper.COL_ID,
			DatabaseHelper.COL_FIRST_NAME,
			DatabaseHelper.COL_LAST_NAME,
			DatabaseHelper.COL_country
	};
	
	public DatabaseManager(Context context) {
		helper = new DatabaseHelper(context);
	}
	
	public void open() {
		db = helper.getWritableDatabase();
	}
	
	public void close() {
		helper.close();
	}
	
	public Person createPerson(String fName, String lName, String country) {
		
		ContentValues values = new ContentValues();
		
		values.put(DatabaseHelper.COL_FIRST_NAME, fName);
		values.put(DatabaseHelper.COL_LAST_NAME, lName);
		values.put(DatabaseHelper.COL_country, country);
		
		long id = db.insert(
				DatabaseHelper.TABLE_NAME, 
				null, 
				values);
		
		String order = DatabaseHelper.COL_country + " ASC";
		
		Cursor cursor = db.query(
				DatabaseHelper.TABLE_NAME, 
				columns, 
				DatabaseHelper.COL_ID + " = " + id,
				null, null, null, order);
		
		cursor.moveToFirst();
		Person newPerson = cursorToPerson(cursor);
		cursor.close();
		
		return newPerson;		
	}
	
	public void deletePerson(Person person) {
		long id = person.getID();
		db.delete(
				DatabaseHelper.TABLE_NAME, 
				DatabaseHelper.COL_ID + " = " + id,
				null);
	}
	
	public List<Person> getAllPersons(String order) {
		List<Person> people = new ArrayList<Person>();
		
		if (!order.isEmpty())
			order = DatabaseHelper.COL_country + " ASC";
		
		Cursor cursor = db.query(
				DatabaseHelper.TABLE_NAME, 
				columns, 
				null, null, null, null, order);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Person person = cursorToPerson(cursor);
			people.add(person);
			cursor.moveToNext();
		}
		cursor.close();
		
		return people;
	}
	
	private Person cursorToPerson(Cursor cursor) {
		long pID = cursor.getLong(0);
		String pFName = cursor.getString(1);
		String pLName = cursor.getString(2);
		String pCountry = cursor.getString(3);
		return new Person(pID, pFName, pLName, pCountry);
	}
	
}