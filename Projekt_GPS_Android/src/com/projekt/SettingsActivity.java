package com.projekt;

import android.os.Bundle;
import android.app.Activity;

/*
 * This Activity is only for the SettingsFragment 
 */
public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		getFragmentManager().beginTransaction()
        .replace(R.id.settings_fragment, new SettingsFragment())
        .commit();
	}
}
