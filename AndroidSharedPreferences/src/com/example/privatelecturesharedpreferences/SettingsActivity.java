package com.example.privatelecturesharedpreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public static final String KEY_SORT_ORDER 	= "pref_sort_order";
	public static final String KEY_FONT_SIZE 	= "pref_font_size";
	public static final String KEY_FONT_COLOR 	= "pref_font_color";
	public static final String KEY_BG_COLOR 	= "pref_bg_color";

	private SharedPreferences settings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction().replace(android.R.id.content,
				new SettingsFragment()).commit();
		
		settings = PreferenceManager.getDefaultSharedPreferences(this);
		settings.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// Do something when a setting is changed
		// Or simply do something in the main activity by reading the new settings
		// when onResume() is called
	}
}
