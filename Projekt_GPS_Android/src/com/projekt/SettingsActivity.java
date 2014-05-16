package com.projekt;

import android.os.Bundle;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.Activity;

/*
 * This Activity is only for the SettingsFragment 
 */
public class SettingsActivity extends Activity {
	private ActionBar actionbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		actionbar = getActionBar();
		
		// enables Back-Button in the ActionBar
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
		
		getFragmentManager().beginTransaction()
        .replace(R.id.settings_fragment, new SettingsFragment())
        .commit();
	}
	
	//Listener for the Back-Button in the ActionBar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
