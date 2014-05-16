package com.projekt;

import java.io.IOException;
import java.util.ArrayList;
import com.jjoe64.graphview.GraphView.GraphViewData;
import java.util.List;
import com.google.android.gms.maps.model.LatLng;


public class testappforKMLandCSV {

	public static void createReports() {
		List<LatLng> gpsCoo = new ArrayList<LatLng>();
        gpsCoo.add(new LatLng(11.43376449125764, 48.76684844783564));
        gpsCoo.add(new LatLng(11.43390892434019, 48.76689524366963));
        gpsCoo.add(new LatLng(11.43403938447936, 48.76680213918904));
        gpsCoo.add(new LatLng(11.43393259392642, 48.76678591882644));
        gpsCoo.add(new LatLng(11.43384057414879, 48.76677996095187));
        KML.createKML(gpsCoo);
        
        List<GraphViewData> listAccX = new ArrayList<GraphViewData>();
    	List<GraphViewData> listAccY = new ArrayList<GraphViewData>();
    	List<GraphViewData> listAccZ = new ArrayList<GraphViewData>();
    	List<GraphViewData> listrpm1 = new ArrayList<GraphViewData>();
    	List<GraphViewData> listrpm2 = new ArrayList<GraphViewData>();
    	List<GraphViewData> listrpm3 = new ArrayList<GraphViewData>();
    	List<GraphViewData> listrpm4 = new ArrayList<GraphViewData>();
    	List<GraphViewData> listAngleX = new ArrayList<GraphViewData>();
    	List<GraphViewData> listAngleY = new ArrayList<GraphViewData>();
    	List<GraphViewData> listAngleZ = new ArrayList<GraphViewData>();
    	
    	GraphViewData data1 = new GraphViewData(0.1, 9);
		GraphViewData data2 = new GraphViewData(4, 3.22);
		GraphViewData data3 = new GraphViewData(33.3, 4.22);
		GraphViewData data4 = new GraphViewData(2.22, 1.22);
		GraphViewData data5 = new GraphViewData(3.22, 4.444);
		listAccX.add(data1);
		listAccX.add(data2);
		listAccX.add(data3);
		listAccX.add(data4);
		listAccX.add(data5);
		listAccZ.add(data1);
		listrpm1.add(data1);
		listrpm2.add(data1);
		listrpm3.add(data1);
		listrpm4.add(data1);
		listAngleX.add(data1);
		listAngleY.add(data1);
		listAngleZ.add(data1);
		
		try {
			CSV.createCSVReport(listAccX, listAccY, listAccZ, listrpm1, listrpm2,
					listrpm3, listrpm4, listAngleX, listAngleY, listAngleY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
