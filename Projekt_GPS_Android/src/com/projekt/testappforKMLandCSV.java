package com.projekt;

import java.io.IOException;
import java.util.ArrayList;
import com.jjoe64.graphview.GraphView.GraphViewData;
import java.util.List;
import com.google.android.gms.maps.model.LatLng;


public class testappforKMLandCSV {

	public static void createReports() {
		List<LatLng> gpsCoo = new ArrayList<LatLng>();
        gpsCoo.add(new LatLng(11.43365423405033, 48.76677768792014));
        gpsCoo.add(new LatLng(11.43367445055059, 48.7668420231239));
        gpsCoo.add(new LatLng(11.43378163216451, 48.76688016869116));
        gpsCoo.add(new LatLng(11.43395972980487, 48.76681659667607));
        gpsCoo.add(new LatLng(11.43399010200488, 48.7669179297429));
        gpsCoo.add(new LatLng(11.43414378155901, 48.76689750148764));
        gpsCoo.add(new LatLng(11.4343407824876, 48.76691983343154));
        gpsCoo.add(new LatLng(11.43444834351828, 48.76684710270823));
        gpsCoo.add(new LatLng(11.43453192131558, 48.76689509376232));
        gpsCoo.add(new LatLng(11.43454788292905, 48.76696250695486));
        gpsCoo.add(new LatLng(11.43469041823783, 48.76697337532911));
        gpsCoo.add(new LatLng(11.43473507300667, 48.76687325554991));
        gpsCoo.add(new LatLng(11.43493153749986, 48.76685781700431));
        List<LatLng> gpsCooKal = new ArrayList<LatLng>();
        gpsCooKal.add(new LatLng(11.4340000089386,48.76689418140901));
        gpsCooKal.add(new LatLng(11.43422217174517,48.76707996169954));
        gpsCooKal.add(new LatLng(11.4347649830117,48.76696291738272));
        KMLReport.createKML(gpsCoo, gpsCooKal);
        
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
    	
    	GraphViewData data1 = new GraphViewData(0.0, 1.2);
		GraphViewData data2 = new GraphViewData(10, 3.22);
		GraphViewData data3 = new GraphViewData(20, 2.22);
		GraphViewData data4 = new GraphViewData(30, 1.22);
		GraphViewData data5 = new GraphViewData(40.53, 1.34);
		GraphViewData data6 = new GraphViewData(50.27, 2.44);
		GraphViewData data7 = new GraphViewData(60.22, 1.2);
		GraphViewData data8 = new GraphViewData(70.2, 3.9);
		GraphViewData data9 = new GraphViewData(80.22, 3.04);
		GraphViewData data10 = new GraphViewData(90.2, 1.14);
		GraphViewData data11 = new GraphViewData(100.23, 1.2);
		GraphViewData data12 = new GraphViewData(110.27, 1.33);
		listAccX.add(data1);
		listAccX.add(data2);
		listAccX.add(data3);
		listAccX.add(data4);
		listAccY.add(data1);
		listAccY.add(data2);
		listAccY.add(data6);
		listAccY.add(data2);
		listAccZ.add(data3);
		listAccZ.add(data2);
		listAccZ.add(data1);
		listAccZ.add(data11);
		listrpm1.add(data1);
		listrpm1.add(data11);
		listrpm1.add(data9);
		listrpm1.add(data3);
		listrpm2.add(data5);
		listrpm2.add(data1);
		listrpm2.add(data3);
		listrpm2.add(data12);
		listrpm3.add(data7);
		listrpm3.add(data8);
		listrpm3.add(data10);
		listrpm3.add(data2);
		listrpm4.add(data1);
		listrpm4.add(data3);
		listrpm4.add(data6);
		listrpm4.add(data5);
		listAngleX.add(data1);
		listAngleX.add(data12);
		listAngleX.add(data8);
		listAngleX.add(data6);
		listAngleY.add(data11);
		listAngleY.add(data1);
		listAngleY.add(data9);
		listAngleY.add(data7);
		listAngleZ.add(data8);
		listAngleZ.add(data1);
		listAngleZ.add(data10);
		listAngleZ.add(data12);
		
		try {
			CSVReport.createCSVReport(listAccX, listAccY, listAccZ, listrpm1, listrpm2,
					listrpm3, listrpm4, listAngleX, listAngleY, listAngleY, gpsCoo, gpsCooKal);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
