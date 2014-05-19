package com.projekt;

import java.util.ArrayList;

import com.projekt.StorageUtils.StorageInfo;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class StartActivity extends Activity implements OnClickListener {
	private static final String KEY_LISTPREF = "saving";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		ArrayList<StorageInfo> listStorage = (ArrayList<StorageInfo>) StorageUtils.getStorageList();
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		// Test if "extern" in Settings was selected before SD-Card is removed  
		int storageCounter = 0;
		for(@SuppressWarnings("unused") StorageInfo info: listStorage){
			storageCounter++;
		}
		if(storageCounter == 1){
			// if "extern" but no SD-Card available, "intern" will be select
			if(pref.getString(KEY_LISTPREF, "kein Wert").equals("extern")){
				SharedPreferences.Editor editor = pref.edit();
				editor.putString(KEY_LISTPREF, "intern");
				editor.commit();
			}
		}
		
		Button startButton = (Button)findViewById(R.id.button_start);
		Button settingsButton = (Button)findViewById(R.id.button_settings);
		startButton.setOnClickListener(this);
		settingsButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {

		switch(view.getId()){
		case R.id.button_start: 
			startActivity(new Intent(this, MainActivity.class));
			break;
		case R.id.button_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			break;
		}
	}

	@Override
	protected void onStart(){
		super.onStart();
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		String storedPreference = pref.getString(KEY_LISTPREF, "intern");
		
		if(storedPreference.compareTo("intern") == 0){
			Report.setExtStorage(false);
		}else if(storedPreference.compareTo("extern") == 0){
			Report.setExtStorage(true);
		}
	}
}
