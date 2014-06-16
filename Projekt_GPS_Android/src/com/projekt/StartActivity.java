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

import java.lang.reflect.Method;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;


public class StartActivity extends Activity implements OnClickListener {
	private static final String KEY_LISTPREF = "saving";

    public WifiManager wifi;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		Button startButton = (Button)findViewById(R.id.button_start);
		Button settingsButton = (Button)findViewById(R.id.button_settings);
		startButton.setOnClickListener(this);
		settingsButton.setOnClickListener(this);

        try {
            turnHotSpotOnOff(1);
        }catch (Exception e){

            Log.d("Error", "Can't instantiate access point");
        }



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
		
		String storedPreference = pref.getString(KEY_LISTPREF, "intern");
		
		if(storedPreference.compareTo("intern") == 0){
			Report.setExtStorage(false);
		}else if(storedPreference.compareTo("extern") == 0){
			Report.setExtStorage(true);
		}
	}
    public void turnHotSpotOnOff(int i) throws Exception {
        wifi = (WifiManager) getSystemService(StartActivity.this.WIFI_SERVICE);
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "123";

        Method[] wmMethods = wifi.getClass().getDeclaredMethods();

        switch (i) {
            case 1:
                wifi.setWifiEnabled(false);

                for (Method method : wmMethods) {
                    if (method.getName().equals("setWifiApEnabled")) {
                        method.invoke(wifi, config, true);
                    }
                }
                break;

            case 2:

                for (Method method : wmMethods) {
                    if (method.getName().equals("setWifiApEnabled")) {

                        method.invoke(wifi, null, false);

                    }
                }
                break;

        }

    }

    protected void onDestroy(){
    	try {
			turnHotSpotOnOff(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	wifi.setWifiEnabled(true);
    	super.onDestroy();
    }

}

