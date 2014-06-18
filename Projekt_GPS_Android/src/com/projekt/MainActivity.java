package com.projekt;


import java.io.IOException;
import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.Gravity;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.Intent;

public class MainActivity extends Activity implements OnCheckedChangeListener, Tab_gps.MyCommunicationListener, MyCSVReportListener{
    public static Context appContext;
    private ActionBar actionbar;
    private View customView;
    private ActionBar.Tab  all_Tab;
    private TextView text;
    private String csvPath, absolutCSVPath;
    private boolean isCSVReportSelected = false;
    private boolean isRecPressed = false;
    public UDPService udpService;

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
        all_Tab = actionbar.newTab().setText("ALL");
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

        // Add CustomView to ActionBar
        customView = getLayoutInflater().inflate(R.layout.actionbar_button, null);
        actionbar.setCustomView(customView, new ActionBar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.LEFT));
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);

        // Find Views from CustomView
        text = (TextView)customView.findViewById(R.id.textView_actionbar);
        ToggleButton button = (ToggleButton) customView.findViewById(R.id.button);
        button.setOnCheckedChangeListener(this);

        // If Activtiy is started after the FileChooserActivity, a Path for csv-File is delivered
        absolutCSVPath = getIntent().getStringExtra("AbsolutFile"); // Absolute Path
        csvPath = getIntent().getStringExtra("File"); // Name of the Path

        // csvPath got a Path, when User choose File via FileChooserActivity
        if(csvPath == null){
            text.setText("Measurement is running");
        }
        else{
            text.setText("Selected File: " + csvPath);
            isCSVReportSelected = true;
        }

        // Service start
        ServiceConnection mTCPconnection = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                // TODO Auto-generated method stub
                udpService = null;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                // TODO Auto-generated method stub
                udpService = ((UDPService.LocalBinder) service).getService();
                udpService.setLink(MainActivity.this);
            }
        };


        Intent udpServiceIntent = new Intent (this, UDPService.class);

        bindService(udpServiceIntent, mTCPconnection, Context.BIND_AUTO_CREATE);
        startService(udpServiceIntent);
        // TCP Connection END ---------------------------------------

        //Prevent Stand-By-Mode
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        if(isChecked){
            Toast.makeText(this, "Start Saving", Toast.LENGTH_LONG).show();
            isRecPressed = true;
            udpService.clearSavingLists();
            udpService.setEnableSaving(true);
        }
        else{
            Toast.makeText(this, "Stop Saving", Toast.LENGTH_LONG).show();
            isRecPressed = false;
            udpService.setEnableSaving(false);
            KMLReport.createKML(udpService.getAllLocationssaving());
            try {
                CSVReport.createCSVReport(udpService.getListAccXsaving(),
                        udpService.getListAccYsaving(),
                        udpService.getListAccZsaving(),
                        udpService.getListrpm1saving(),
                        udpService.getListrpm2saving(),
                        udpService.getListrpm3saving(),
                        udpService.getListrpm4saving(),
                        udpService.getListAngleXsaving(),
                        udpService.getListAngleYsaving(),
                        udpService.getListAngleZsaving(),
                        udpService.getAllLocationssaving());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public UDPService getudpService(){

        return udpService;
    }

    //Listener for the ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: // Back-Button

                // Ask User before finish the Activity
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_launcher)
                        .setTitle("Do you really want to finish measurement?")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Action for Cancel-Button
                                    }
                                })
                        .show();
                return true;

            case R.id.load_file:
                startActivity(new Intent(this, FileChooserActivity.class));
                return true;

            case R.id.continue_measurement:
                if(isCSVReportSelected){
                    text.setText("Measurement is running");
                    isCSVReportSelected = false;
                    restartFragments();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /** Create an option menu from res/menu/actionbar_items.xml */
        getMenuInflater().inflate(R.menu.actionbar_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void restartFragments(){
        // Jump to All Tab
        actionbar.selectTab(all_Tab);

        // Start Tansaction
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Remove Fragment
        fragmentTransaction.remove(getFragmentManager().findFragmentById(R.id.fragment_container));

        // Reload Fragment
        Tab_all fragment = new Tab_all();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Ask User before finish the Activity
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_launcher)
                    .setTitle("Do you really want to finish measurement?")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Action for Cancel-Button
                                }
                            })
                    .show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    // For GPS Fragment
    public boolean getRecState() {
        return isRecPressed;
    }

    @Override
    // For All, Acc, Angle and RPM Fragment
    public boolean getIsCSVReportSelected() {
        return isCSVReportSelected;
    }

    @Override
    // For All, Acc, Angle and RPM Fragment
    public String getAbsolutCSVPath() {
        return absolutCSVPath;
    }
}