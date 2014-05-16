package com.projekt;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.projekt.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Tab_gps  extends Fragment {
	
	private boolean firsttime;
	private boolean isRecPressed; //Provisorische Variable, kann gelöscht werden
	MapView mapView;
	GoogleMap map;
    private MyCommunicationListener mCallback;
    
    
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
			Toast.makeText(getActivity(), "SUCCESS :)", Toast.LENGTH_SHORT).show(); //Gibt SUCCESS aus als "popup"
			mapView = (MapView) v.findViewById(R.id.map);
			mapView.onCreate(savedInstanceState); // returns the view that has the given id in the hierarchy or null
			// Gets to GoogleMap from the MapView and does initialization stuff
			if(mapView!=null)
			{
				map = mapView.getMap(); //returns the googlemap
				map.getUiSettings().setMyLocationButtonEnabled(true); //Enables or disables the my-location button.
				map.setMyLocationEnabled(true); //Enables or disables the my position in the map.
				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(48.767119114158405, 11.431743800640106), 17);
				map.animateCamera(cameraUpdate);	//zoom in the map   // oben die latitude und longitude von ingolstadt, zoom ist 17

				firsttime=true; //test für speicherproblemlösung

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

		// Updates the location and zoom of the MapView

		
		/*
		 * Für Sinan:
		 * Provisorische Variable, um zu verdeutlichen, dass mit dieser Funktion
		 * der Status des Rec-Buttons abgefragt werden kann
		 */
		isRecPressed = mCallback.getRecState();
		
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
		
		//mapView.onResume();
		//super.onResume();
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