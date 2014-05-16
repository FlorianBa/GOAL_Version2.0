package com.projekt;

import java.util.ArrayList;

import com.projekt.StorageUtils;
import com.projekt.StorageUtils.StorageInfo;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	private ListPreference listpref;
	private static final String KEY_LISTPREF = "saving";
	private ArrayList<StorageInfo> listStorage = (ArrayList<StorageInfo>) StorageUtils.getStorageList();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.settings);

		PreferenceManager.setDefaultValues(getActivity(), R.xml.settings, false);
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		sharedPref.registerOnSharedPreferenceChangeListener(this);

		// Set Summary of ListPreference
		listpref = (ListPreference)findPreference(KEY_LISTPREF);
		listpref.setSummary(listpref.getEntry());


		// Test if "extern" was selected before SD-Card is removed  
		int storageCounter = 0;
		for(@SuppressWarnings("unused") StorageInfo info: listStorage){
			storageCounter++;
		}
		if(storageCounter == 1){
			// if "extern" but no SD-Card available, "intern" will be select
			if(sharedPref.getString(KEY_LISTPREF, "kein Wert").equals("extern")){
				listpref.setSummary("intern");
				listpref.setValueIndex(0);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString(KEY_LISTPREF, "intern");
				editor.commit();
			}
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
		int storageCounter = 0;
		for(@SuppressWarnings("unused") StorageInfo info: listStorage){
			storageCounter++;
		}

		if(key.equals(KEY_LISTPREF)){
			
			if(storageCounter > 1){ // SD-Card available
				listpref.setSummary(listpref.getEntry());
			}
			else{ // No SD-Card
				if(sp.getString(key, "kein Wert").equals("extern")){
					listpref.setSummary("intern");
					listpref.setValueIndex(0);
					SharedPreferences.Editor editor = sp.edit();
					editor.putString(key, "intern");
					editor.commit();
					
					Toast.makeText(getActivity(), "No SD-Card available", Toast.LENGTH_LONG).show();
				}
				else if(sp.getString(key, "kein Wert").equals("intern")){
					listpref.setSummary("intern");				
				}
				else
					Toast.makeText(getActivity(), "SharedPref kein Wert", Toast.LENGTH_LONG).show();
			}
		}

	}

	@Override     
	public void onResume() {
		super.onResume();          
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);     
	}



	@Override
	public void onPause() {         
		super.onPause();          
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

	}
}
