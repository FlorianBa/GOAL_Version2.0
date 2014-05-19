package com.projekt;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import com.projekt.R;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Tab_gps  extends Fragment {
	
	private boolean firsttime;
	static MapView mapView;
	static GoogleMap map;
    static MyCommunicationListener mCallback;
    static Location MyLocation = new Location("My location");
    static Handler handler;
    static Circle modelcar;
	
    private Thread t; // update location of modelcar
  
    // MainActivity must implement this interface
    // For Communication between Fragment and MainActivity
    public interface MyCommunicationListener {
        public boolean getRecState();
    }

    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.gps_frag, container, false);
		// Gets the MapView from the XML layout and creates it

		try {
			MapsInitializer.initialize(getActivity());
		} catch (Exception e) {
			Log.e("Address Map", "Could not initialize google play", e);
		}

		switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) )
		{
		case ConnectionResult.SUCCESS:
			Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show(); //Gibt SUCCESS aus als "popup"
			mapView = (MapView) v.findViewById(R.id.map);
			
			mapView.onCreate(savedInstanceState); // returns the view that has the given id in the hierarchy or null
			// Gets to GoogleMap from the MapView and does initialization stuff
			if(mapView!=null)
			{
				map = mapView.getMap(); //returns the googlemap
				map.getUiSettings().setMyLocationButtonEnabled(true); //Enables or disables the my-location button.
				map.setMyLocationEnabled(false); //Enables or disables the my position in the map.
				
				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(48.9575483,11.4002600), 17);
				map.animateCamera(cameraUpdate);	//animate and zoom in the map  

				firsttime=true; //test für speicherproblemlösung (recbuttonstatus)

				// Showing the current location in Google Map, erst wenn wir die gps daten erhalten!!!!
				//googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

			}
			break;
		case ConnectionResult.SERVICE_MISSING: 
			Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
			break;
		case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED: 
			Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
			break;
		default: Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
		}

		//modelcar is an Circle. It shows our current location of the modelcar
		//mylocation is the configuration for the Circle
		CircleOptions mylocation = new CircleOptions().radius(3)
				.strokeColor(Color.BLUE).fillColor(Color.WHITE).zIndex(100).center(new LatLng(48.9575483, 11.4002600));
		
		modelcar=Tab_gps.map.addCircle(mylocation);
//				
		//Thread to draw the current position and path if rec is pressed
		if(t==null)
		{
			handler=new Handler();
			t=new Thread(new GPS_Thread());
			t.start();
		}
//					
		return v;
	}
		

	
	@Override
	public void onResume() {
		Toast.makeText(getActivity(), "Onresume :)", Toast.LENGTH_SHORT).show();
		if(firsttime)
		{
			onPause();
			firsttime=false;
		}

		mapView.onResume();
		super.onResume();

	}
	
	@Override
	public void onDestroy() {

		super.onDestroy();
		mapView.onDestroy();
	}
	
	@Override
	public void onLowMemory() {

		super.onLowMemory();
		mapView.onLowMemory();
	}
	
	
	/*
	 * Initialise "mCallback"
	 * With "mCallback" you can use methodes from the main Activity
	 */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the MainActivity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (MyCommunicationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MyCommunicationListener");
        }
    }
}
