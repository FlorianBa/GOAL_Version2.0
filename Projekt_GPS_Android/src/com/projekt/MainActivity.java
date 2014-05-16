package com.projekt;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.Gravity;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.Intent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends Activity implements OnCheckedChangeListener, Tab_gps.MyCommunicationListener {
	public static Context appContext;
	private ActionBar actionbar;
	private View customView;
	private boolean isRecPressed = false;
	public TCPService tcpService;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//ActionBar gets initiated
		actionbar = getActionBar();
		//Tell the ActionBar we want to use Tabs.
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		//initiating  tabs and set text to it.
		ActionBar.Tab all_Tab = actionbar.newTab().setText("ALL");
		ActionBar.Tab acc_Tab = actionbar.newTab().setText("ACC");
		ActionBar.Tab winkel_Tab = actionbar.newTab().setText("ANGLE");
		ActionBar.Tab rpm_Tab = actionbar.newTab().setText("RPM");
		ActionBar.Tab gps_Tab = actionbar.newTab().setText("GPS");

		//create the fragments we want to use for display content
		Tab_all all_fragment = new Tab_all();
		Tab_acc acc_fragment = new Tab_acc();
		Tab_winkel winkel_fragment = new Tab_winkel();
		Tab_rpm rpm_fragment = new Tab_rpm();
		Tab_gps gps_fragment = new Tab_gps();

		//set the Tab listener. Now we can listen for clicks.
		all_Tab.setTabListener((TabListener) new MyTabsListener(all_fragment));
		acc_Tab.setTabListener((TabListener) new MyTabsListener(acc_fragment));
		winkel_Tab.setTabListener((TabListener) new MyTabsListener(winkel_fragment));
		rpm_Tab.setTabListener((TabListener) new MyTabsListener(rpm_fragment));
		gps_Tab.setTabListener((TabListener) new MyTabsListener(gps_fragment));

		//add the tabs to the actionbar
		actionbar.addTab(all_Tab);
		actionbar.addTab(acc_Tab);
		actionbar.addTab(winkel_Tab);
		actionbar.addTab(rpm_Tab);
		actionbar.addTab(gps_Tab);

		// enables Back-Button in the ActionBar
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);

		customView = getLayoutInflater().inflate(R.layout.actionbar_button, null);
		actionbar.setCustomView(customView,
				new ActionBar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.RIGHT));
		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);

		ToggleButton button = (ToggleButton) customView.findViewById(R.id.button);
		button.setOnCheckedChangeListener(this);

		// Service start
		ServiceConnection mTCPconnection = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				tcpService = null;
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				tcpService = ((TCPService.LocalBinder) service).getService();
				tcpService.setLink(MainActivity.this);
			}
		};


		Intent TCPserviceIntent = new Intent (this, TCPService.class);

		bindService(TCPserviceIntent, mTCPconnection, Context.BIND_AUTO_CREATE);
		startService(TCPserviceIntent);
		// TCP Connection END ---------------------------------------

		//Prevent Stand-By-Mode
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public void onCheckedChanged(CompoundButton button, boolean isChecked) {
		if(isChecked){
			Toast.makeText(this, "Start Saving", Toast.LENGTH_LONG).show();
			isRecPressed = true;
			// Hier kann ein Flag im Service gesetzt werden, um Daten zu speichern
		}
		else{
			Toast.makeText(this, "Stop Saving", Toast.LENGTH_LONG).show();
			isRecPressed = false;
		}
	}

	public TCPService getTcpService(){

		return tcpService;
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

	@Override
	public boolean getRecState() {		
		return isRecPressed;
	}
}