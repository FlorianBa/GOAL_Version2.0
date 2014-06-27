package com.projekt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.jjoe64.graphview.GraphView.GraphViewData;

public class testappforOpenCSV {
	
	
	public static void openCSV(){
		OpenCSVReport oCSVR = new OpenCSVReport("/storage/emulated/0/goal_reports/csv_report.csv");
		
		List<LatLng> gpsCoo = new ArrayList<LatLng>();
        gpsCoo.add(new LatLng(11.43365423405033, 48.76677768792014));
        List<LatLng> gpsCooKal = new ArrayList<LatLng>();
        gpsCooKal.add(new LatLng(11.43365423405033, 48.76677768792014));
		
		List<GraphViewData> listAccX = oCSVR.listAccX;
    	List<GraphViewData> listAccY = oCSVR.listAccY;
    	List<GraphViewData> listAccZ = oCSVR.listAccZ;
    	List<GraphViewData> listrpm1 = oCSVR.listrpm1;
    	List<GraphViewData> listrpm2 = oCSVR.listrpm2;
    	List<GraphViewData> listrpm3 = oCSVR.listrpm3;
    	List<GraphViewData> listrpm4 = oCSVR.listrpm4;
    	List<GraphViewData> listAngleX = oCSVR.listAngleX;
    	List<GraphViewData> listAngleY = oCSVR.listAngleY;
    	List<GraphViewData> listAngleZ = oCSVR.listAngleZ;
    	
    	try {
			CSVReport.createCSVReport(listAccX, listAccY, listAccZ, listrpm1, listrpm2,
					listrpm3, listrpm4, listAngleX, listAngleY, listAngleZ, gpsCoo, gpsCooKal);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
