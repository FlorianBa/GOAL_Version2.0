package com.projekt;


import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.CameraUpdateFactory;

public class GPS_Thread extends Tab_gps implements Runnable {


	// Konfiguration Pfad
	PolylineOptions mydrawer_opt_1 = new PolylineOptions().color(Color.RED);
	PolylineOptions mydrawer_opt_2 = new PolylineOptions().color(Color.GREEN);
	PolylineOptions mydrawer_opt_3 = new PolylineOptions().color(Color.YELLOW);
	PolylineOptions mydrawer_opt_4 = new PolylineOptions().color(Color.BLACK);
	PolylineOptions mydrawer_opt_5 = new PolylineOptions().color(Color.BLUE);

	private int path_state=1; // 1 fÃ¼r die erste messung usw. 
	private boolean isRecPressed;
	private boolean measurement_end_flag=false;

	
	@Override
	public void run() {


		while (true) {
			
			isRecPressed = Tab_gps.mCallback.getRecState();
			//FÃ¼ge die Simulationsdaten dem Pfad hinzu
			if (isRecPressed)
			{
				switch(path_state)
				{
				case 1:
					mydrawer_opt_1.add(getCurrentLocation());
					measurement_end_flag=true;
					break;
				case 2:
					mydrawer_opt_2.add(getCurrentLocation());
					measurement_end_flag=true;
					break;
				case 3:
					mydrawer_opt_3.add(getCurrentLocation());
					measurement_end_flag=true;
					break;
				case 4:
					mydrawer_opt_4.add(getCurrentLocation());
					measurement_end_flag=true;
					break;
				case 5:
					mydrawer_opt_5.add(getCurrentLocation());
					measurement_end_flag=true;
					break;
					
				}

			}
			else
			{	//dieser Flag stellt sicher, dass eine Messung gestartet und beendet wurde
				//Dann wird path_state inkrementiert und wenn erneut gemessen wird, wird der Pfad in 
				//einer anderen Farbe(andere CircleOption) markiert
				if(measurement_end_flag)
				{
					path_state++;
					if (path_state==6)
						path_state=1;
					measurement_end_flag=false;
				}
			}
//			mylocation.center(simulate[a]);
			try {
				Thread.sleep(500);
						
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Tab_gps.handler.post(new Runnable() {
				@Override
				public void run() {
					// FÃ¼ge unseren pfad maps hinzu
					if(isRecPressed)
						switch(path_state)
						{
						case 1:
							Tab_gps.map.addPolyline(mydrawer_opt_1);
							break;
						case 2:
							Tab_gps.map.addPolyline(mydrawer_opt_2);
							break;
						case 3:
							Tab_gps.map.addPolyline(mydrawer_opt_3);
							break;
						case 4:
							Tab_gps.map.addPolyline(mydrawer_opt_4);
							break;
						case 5:
							Tab_gps.map.addPolyline(mydrawer_opt_5);
						}

					// aktualiesiere die coordinaten des modellfahrzeugs
					try{
					Tab_gps.modelcar.setCenter(getCurrentLocation());
					}catch(Exception e)
					{
						System.out.println(e);
					}
					//Center the modelcar
					try{
					if (Tab_gps.center_flag)
					{
						
						Tab_gps.map.moveCamera(CameraUpdateFactory.newLatLng(getCurrentLocation()));
						Tab_gps.center_flag=false;
						
					}
					}catch(Exception e)
					{
						System.out.println(e);
					}

				}
			});
			
		}
	
	}
	
    public LatLng getCurrentLocation() {
        if (!Tab_gps.gpslist.isEmpty())
            return Tab_gps.gpslist.get(gpslist.size() - 1);
        else
            return new LatLng(0.0, 0.0);
    }
}
