package com.projekt;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class GPS_Thread implements Runnable {

	// Array mit den Simulationsdaten

	LatLng simulate[] = { new LatLng(48.9575483, 11.4002600),
			new LatLng(48.9575485, 11.4002593),
			new LatLng(48.9576183, 11.4002800),
			new LatLng(48.9576176, 11.4002796),
			new LatLng(48.9576950, 11.4003250),
			new LatLng(48.9576951, 11.4003244),
			new LatLng(48.9577800, 11.4003817),
			new LatLng(48.9577801, 11.4003824),
			new LatLng(48.9578750, 11.4004400),
			new LatLng(48.9578754, 11.4004394),
			new LatLng(48.9579817, 11.4004867),
			new LatLng(48.9579824, 11.4004863),
			new LatLng(48.9580967, 11.4005250),
			new LatLng(48.9580970, 11.4005254),
			new LatLng(48.9582150, 11.4005617),
			new LatLng(48.9582143, 11.4005621),
			new LatLng(48.9583317, 11.4006000),
			new LatLng(48.9583324, 11.4005995),
			new LatLng(48.9584483, 11.4006350),
			new LatLng(48.9584481, 11.4006356),
			new LatLng(48.9585617, 11.4006733),
			new LatLng(48.9585624, 11.4006730),
			new LatLng(48.9586750, 11.4007117),
			new LatLng(48.9586755, 11.4007121),
			new LatLng(48.9587867, 11.4007533),
			new LatLng(48.9587873, 11.4007529),
			new LatLng(48.9588983, 11.4007950),
			new LatLng(48.9588982, 11.4007946),
			new LatLng(48.9590050, 11.4008350),
			new LatLng(48.9590049, 11.4008354),
			new LatLng(48.9591027, 11.4008734),
			new LatLng(48.9591033, 11.4008733),
			new LatLng(48.9591950, 11.4009100),
			new LatLng(48.9591950, 11.4009095),
			new LatLng(48.9592800, 11.4009450),
			new LatLng(48.9592803, 11.4009452),
			new LatLng(48.9593633, 11.4009783),
			new LatLng(48.9593639, 11.4009781),
			new LatLng(48.9594433, 11.4010083),
			new LatLng(48.9594436, 11.4010086),
			new LatLng(48.9595200, 11.4010400),
			new LatLng(48.9595206, 11.4010401),
			new LatLng(48.9595950, 11.4010667),
			new LatLng(48.9595944, 11.4010660),
			new LatLng(48.9596641, 11.4010797),
			new LatLng(48.9596633, 11.4010800),
			new LatLng(48.9597283, 11.4010817),
			new LatLng(48.9597291, 11.4010817),
			new LatLng(48.9597900, 11.4010717),
			new LatLng(48.9597907, 11.4010720),
			new LatLng(48.9598517, 11.4010500),
			new LatLng(48.9598515, 11.4010504),
			new LatLng(48.9599100, 11.4010150),
			new LatLng(48.9599101, 11.4010148),
			new LatLng(48.9599650, 11.4009633),
			new LatLng(48.9599653, 11.4009631),
			new LatLng(48.9600167, 11.4009000),
			new LatLng(48.9600171, 11.4008998),
			new LatLng(48.9600683, 11.4008317),
			new LatLng(48.9600680, 11.4008316),
			new LatLng(48.9601183, 11.4007617),
			new LatLng(48.9601192, 11.4007620),
			new LatLng(48.9601700, 11.4006917),
			new LatLng(48.9601701, 11.4006910),
			new LatLng(48.9602217, 11.4006167),
			new LatLng(48.9602214, 11.4006169),
			new LatLng(48.9602733, 11.4005383),
			new LatLng(48.9602738, 11.4005392),
			new LatLng(48.9603267, 11.4004583),
			new LatLng(48.9603273, 11.4004590),
			new LatLng(48.9603817, 11.4003767),
			new LatLng(48.9603816, 11.4003769),
			new LatLng(48.9604367, 11.4002933),
			new LatLng(48.9604367, 11.4002928),
			new LatLng(48.9604917, 11.4002100),
			new LatLng(48.9604915, 11.4002095),
			new LatLng(48.9605500, 11.4001283),
			new LatLng(48.9605495, 11.4001278),
			new LatLng(48.9606133, 11.4000500),
			new LatLng(48.9606140, 11.4000493),
			new LatLng(48.9606867, 11.3999750),
			new LatLng(48.9606859, 11.3999746),
			new LatLng(48.9607667, 11.3999050),
			new LatLng(48.9607667, 11.3999046),
			new LatLng(48.9608583, 11.3998433),
			new LatLng(48.9608576, 11.3998437),
			new LatLng(48.9609583, 11.3997983),
			new LatLng(48.9609583, 11.3997982),
			new LatLng(48.9610633, 11.3997733),
			new LatLng(48.9610636, 11.3997739),
			new LatLng(48.9611750, 11.3997617),
			new LatLng(48.9611753, 11.3997612),
			new LatLng(48.9612917, 11.3997517),
			new LatLng(48.9612912, 11.3997515),
			new LatLng(48.9614133, 11.3997417),
			new LatLng(48.9614130, 11.3997423),
			new LatLng(48.9615633, 11.3997233),
			new LatLng(48.9615640, 11.3997231),
			new LatLng(48.9617533, 11.3996850),
			new LatLng(48.9617535, 11.3996846),
			new LatLng(48.9619733, 11.3996283),
			new LatLng(48.9619727, 11.3996288),
			new LatLng(48.9622200, 11.3995550),
			new LatLng(48.9622203, 11.3995544),
			new LatLng(48.9624933, 11.3994550),
			new LatLng(48.9624933, 11.3994555),
			new LatLng(48.9627833, 11.3993350),
			new LatLng(48.9627837, 11.3993344),
			new LatLng(48.9630717, 11.3992033),
			new LatLng(48.9630713, 11.3992029),
			new LatLng(48.9633500, 11.3990617),
			new LatLng(48.9633500, 11.3990623),
			new LatLng(48.9636217, 11.3989183),
			new LatLng(48.9636209, 11.3989180),
			new LatLng(48.9638850, 11.3987717),
			new LatLng(48.9638845, 11.3987714),
			new LatLng(48.9641433, 11.3986283),
			new LatLng(48.9641428, 11.3986286),
			new LatLng(48.9643950, 11.3984900),
			new LatLng(48.9643957, 11.3984903),
			new LatLng(48.9646434, 11.3983570),
			new LatLng(48.9646433, 11.3983567),
			new LatLng(48.9648867, 11.3982283),
			new LatLng(48.9648860, 11.3982287),
			new LatLng(48.9651233, 11.3981050),
			new LatLng(48.9651231, 11.3981051),
			new LatLng(48.9653564, 11.3979890),
			new LatLng(48.9653567, 11.3979883),
			new LatLng(48.9655883, 11.3978800),
			new LatLng(48.9655881, 11.3978803),
			new LatLng(48.9658183, 11.3977750),
			new LatLng(48.9658183, 11.3977755),
			new LatLng(48.9660459, 11.3976767),
			new LatLng(48.9660467, 11.3976767),
			new LatLng(48.9662717, 11.3975817),
			new LatLng(48.9662715, 11.3975820),
			new LatLng(48.9664950, 11.3974867),
			new LatLng(48.9664948, 11.3974872),
			new LatLng(48.9667167, 11.3973950),
			new LatLng(48.9667171, 11.3973946),
			new LatLng(48.9669400, 11.3973033),
			new LatLng(48.9669404, 11.3973032),
			new LatLng(48.9671650, 11.3972133),
			new LatLng(48.9671649, 11.3972132),
			new LatLng(48.9673933, 11.3971283),
			new LatLng(48.9673929, 11.3971290),
			new LatLng(48.9676233, 11.3970500),
			new LatLng(48.9676235, 11.3970497),
			new LatLng(48.9678533, 11.3969767),
			new LatLng(48.9678538, 11.3969771),
			new LatLng(48.9680800, 11.3969117),
			new LatLng(48.9680807, 11.3969122),
			new LatLng(48.9682983, 11.3968517),
			new LatLng(48.9682988, 11.3968522),
			new LatLng(48.9685033, 11.3967967),
			new LatLng(48.9685039, 11.3967974),
			new LatLng(48.9686967, 11.3967483),
			new LatLng(48.9686972, 11.3967489),
			new LatLng(48.9688817, 11.3967067),
			new LatLng(48.9688824, 11.3967067) };
	// Konfiguration Pfad
	PolylineOptions mydrawer_opt_1 = new PolylineOptions().color(Color.RED);
	PolylineOptions mydrawer_opt_2 = new PolylineOptions().color(Color.GREEN);
	PolylineOptions mydrawer_opt_3 = new PolylineOptions().color(Color.YELLOW);
	PolylineOptions mydrawer_opt_4 = new PolylineOptions().color(Color.BLACK);
	PolylineOptions mydrawer_opt_5 = new PolylineOptions().color(Color.BLUE);
	private int a=0;
	private int path_state=1; // 1 für die erste messung usw. 
	private boolean isRecPressed;
	private boolean measurement_end_flag=false;
	
	@Override
	public void run() {


		for (int i = 0; i < 159; i++) {
			a=i;
			isRecPressed = Tab_gps.mCallback.getRecState();
			//Füge die Simulationsdaten dem Pfad hinzu
			if (isRecPressed)
			{
				switch(path_state)
				{
				case 1:
					mydrawer_opt_1.add(simulate[a]);
					measurement_end_flag=true;
					break;
				case 2:
					mydrawer_opt_2.add(simulate[a]);
					measurement_end_flag=true;
					break;
				case 3:
					mydrawer_opt_3.add(simulate[a]);
					measurement_end_flag=true;
					break;
				case 4:
					mydrawer_opt_4.add(simulate[a]);
					measurement_end_flag=true;
					break;
				case 5:
					mydrawer_opt_5.add(simulate[a]);
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
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Tab_gps.handler.post(new Runnable() {
				@Override
				public void run() {
					// Füge unseren pfad maps hinzu
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
					Tab_gps.modelcar.setCenter(simulate[a]);
					
//					//Center the modelcar
//					CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(simulate[a], 17);
//					Tab_gps.map.animateCamera(cameraUpdate);
					// alternative 
//					Tab_gps.map.moveCamera(CameraUpdateFactory.newLatLng(simulate[a]));
				}
			});
			
		}
	
	}
}
