package com.example.privatelecturesharedpreferences;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Hani Fakhouri
 * Order of classes creation:
 * 		1. MainActivity.java
 * 				GUI
 * 
 * 		2. SettingsFragment.java
 * 		
 * 		3. SettingsActivity.java
 * 				Add the SettingsFragment to the activity
 * 				Add this activity to the AndroidManifest.xml
 * 				Preferences are defined the xml file res/xml/mypreferences and are
 * 				accessed by their keys
 * 				The entries/values of list preferences are defined in res/values/colors.xml
 * 				and in res/values/sortorder.xml
 * 
 * Source: http://developer.android.com/guide/topics/ui/settings.html#Defaults
 *
 */

public class MainActivity extends Activity {

	private TextView txtSortOrder, txtFontSize, txtFontColor, txtBGcolor;
	private RelativeLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtSortOrder = (TextView)findViewById(R.id.txtSortOrder);
		txtFontSize = (TextView)findViewById(R.id.txtFontSize);
		txtFontColor = (TextView)findViewById(R.id.txtFontColor);
		txtBGcolor = (TextView)findViewById(R.id.txtBGcolor);
		
		layout = (RelativeLayout) findViewById(R.id.xlayout);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

		String sort_order = settings.getString(SettingsActivity.KEY_SORT_ORDER, "");
		String font_color = settings.getString(SettingsActivity.KEY_FONT_COLOR, "");
		String font_size = settings.getString(SettingsActivity.KEY_FONT_SIZE, "");
		String bg_color = settings.getString(SettingsActivity.KEY_BG_COLOR, "");
		
		txtSortOrder.setText("Sort Order: " + sort_order);
		
		txtFontColor.setText("Font Color: " + font_color);
		txtFontColor.setTextColor(getColor(font_color));
		
		txtFontSize.setText("Font Size:" + font_size);
		txtFontSize.setTextSize(Float.valueOf(font_size));
		
		txtBGcolor.setText("BG Color: " + bg_color);
		layout.setBackgroundColor(getColor(bg_color));
		
	}
	
	private int getColor(String c) {
		if (c.equals("Red")) return Color.RED;
		if (c.equals("Blue")) return Color.BLUE;
		if (c.equals("Yellow")) return Color.YELLOW;
		if (c.equals("Green")) return Color.GREEN;
		return Color.BLACK;
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
		
		Intent settingsIntent = 
				new Intent(this, SettingsActivity.class);
		startActivity(settingsIntent);
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
