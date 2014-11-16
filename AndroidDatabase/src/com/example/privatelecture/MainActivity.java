package com.example.privatelecture;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

/**
 * 
 * @author Hani Fakhouri
 * Order of class creation:
 *		1.	MainActivity.java
 * 				Application GUI
 * 
 * 		2.	Person.java
 * 				Creates a Person object that is going to be represented in the database
 * 
 * 		3.	DatabaseHelper.java
 * 				Creates the Database and the table(s)
 * 
 * 		4.	DatabaseManager.java
 * 				Manages the database e.g. adds/deletes new person from the database
 * 
 * Source: http://www.vogella.com/tutorials/AndroidSQLite/article.html#sqliteoverview
 *
 */

public class MainActivity extends ListActivity {

	private DatabaseManager manager;
	private Button btnAddNew, btnDeleteFirst, btnSortByCountry;
	
	private int counter = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		manager = new DatabaseManager(this);
		manager.open();
		
		List<Person> people = manager.getAllPersons("");
		
		ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(
				this, 
				android.R.layout.simple_list_item_1,
				people);
		setListAdapter(adapter);
		
		btnAddNew = (Button)findViewById(R.id.btnAdd);
		btnDeleteFirst = (Button)findViewById(R.id.btnDelete);
		btnSortByCountry = (Button)findViewById(R.id.btnSort);
		
		btnAddNew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    @SuppressWarnings("unchecked")
				ArrayAdapter<Person> adapter = (ArrayAdapter<Person>) getListAdapter();
			    Person person = null;
			    
			    switch (counter) {
			    case 0:
			    	person = manager.createPerson("Michal", "Jordan", "C");
			    	break;
			    case 1:
			    	person = manager.createPerson("Lionel", "Messi", "D");
			    	break;
			    case 2:
			    	person = manager.createPerson("Michal", "Schumacher", "A");
			    	break;
			    case 3:
			    	person = manager.createPerson("Barack", "Obama", "B");
			    	break;
			    default:
			    	person = manager.createPerson("Osama", "Bin Laden", "E");
			    	counter = -1;
			    	break;
			    }
			    
			    counter++;
			    adapter.add(person);
			    adapter.notifyDataSetChanged();
			}
		});
		
		btnDeleteFirst.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				@SuppressWarnings("unchecked")
				ArrayAdapter<Person> adapter = (ArrayAdapter<Person>) getListAdapter();
				if (getListAdapter().getCount() > 0) {
					Person person = (Person)getListAdapter().getItem(0);
					manager.deletePerson(person);
					adapter.remove(person);
					counter--;
				}
				adapter.notifyDataSetChanged();
			}
		});
		
		btnSortByCountry.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				List<Person> people = manager.getAllPersons(DatabaseHelper.COL_country);
				ArrayAdapter<Person> adapter = (ArrayAdapter<Person>) getListAdapter(); 
				adapter.clear();
				adapter.addAll(people);
				adapter.notifyDataSetChanged();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
