package com.projekt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jjoe64.graphview.GraphView.GraphViewData;

import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;


public class CreateCSVReport {
	
	private static boolean extStorage = false;
	
	private Random random = new Random();
	private List<GraphViewData> listAccX = new ArrayList<GraphViewData>();
	private List<GraphViewData> listAccY = new ArrayList<GraphViewData>();
	private List<GraphViewData> listAccZ = new ArrayList<GraphViewData>();
	private List<GraphViewData> listrpm1 = new ArrayList<GraphViewData>();
	private List<GraphViewData> listrpm2 = new ArrayList<GraphViewData>();
	private List<GraphViewData> listrpm3 = new ArrayList<GraphViewData>();
	private List<GraphViewData> listrpm4 = new ArrayList<GraphViewData>();
	private List<GraphViewData> listAngelX = new ArrayList<GraphViewData>();
	private List<GraphViewData> listAngelY= new ArrayList<GraphViewData>();
	private List<GraphViewData> listAngelZ = new ArrayList<GraphViewData>();
	
	public CreateCSVReport(){
		GraphViewData data1 = new GraphViewData(random.nextDouble()*3.5+0.1, random.nextDouble()*3.5+0.1);
		GraphViewData data2 = new GraphViewData(random.nextDouble()*3.5+0.1, random.nextDouble()*3.5+0.1);
		GraphViewData data3 = new GraphViewData(random.nextDouble()*3.5+0.1, random.nextDouble()*3.5+0.1);
		GraphViewData data4 = new GraphViewData(random.nextDouble()*3.5+0.1, random.nextDouble()*3.5+0.1);
		GraphViewData data5 = new GraphViewData(random.nextDouble()*3.5+0.1, random.nextDouble()*3.5+0.1);
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
		listAngelX.add(data1);
		listAngelY.add(data1);
		listAngelZ.add(data1);
		try {
			createCSVReport(listAccX, listAccY, listAccZ, listrpm1, listrpm2,
					listrpm3, listrpm4, listAngelX, listAngelY, listAngelZ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setExtStorage(boolean state){
		extStorage = state;
	}
		
	
	public void createCSVReport(List<GraphViewData> listAccX2, List<GraphViewData> listAccY2,
			List<GraphViewData> listAccZ2, List<GraphViewData> listrpm12,
			List<GraphViewData> listrpm22, List<GraphViewData> listrpm32,
			List<GraphViewData> listrpm42, List<GraphViewData> listAngelX2,
			List<GraphViewData> listAngelY2, List<GraphViewData> listAngelZ2)
			throws IOException {

		String folderPath = "/csv_reports";
		String csvFilePath = "/csv_report.csv";
		String extStoragePath = "/storage/extSdCard";
		FileWriter fw;
		
		if(!extStorage){
			File csvFolderInt = new File(Environment.getExternalStorageDirectory() + folderPath);
			if(!csvFolderInt.exists()){
				csvFolderInt.mkdir();	
			}
			fw = new FileWriter(Environment.getExternalStorageDirectory() + folderPath + csvFilePath);
		}else{
			File csvFolderInt = new File(extStoragePath + folderPath);
			if(!csvFolderInt.exists()){
				csvFolderInt.mkdir();	
			}
			fw = new FileWriter(extStoragePath + folderPath + csvFilePath);
		}
		
		
		PrintWriter out = new PrintWriter(fw);
		
		out.print("time");
		out.print(";");
		for (GraphViewData data : listAccX2) {
			out.print(data.getX());
			out.print(";");
		}
		out.println("");
		out.print("Acc_X");
		out.print(";");
		for (GraphViewData data : listAccX2) {
			out.print(data.getY());
			out.print(";");
		}
		out.println("");
		out.print("Acc_Y");
		out.print(";");
		for (GraphViewData data : listAccY2) {
			out.print(data.getY());
			out.print(";");
		}
		out.println("");
		out.print("Acc_Z");
		out.print(";");
		for (GraphViewData data : listAccZ2) {
			out.print(data.getY());
			out.print(";");
		}
		out.println("");
		out.print("rpm_1");
		out.print(";");
		for (GraphViewData data : listrpm12) {
			out.print(data.getY());
			out.print(";");
		}
		out.println("");
		out.print("rpm_2");
		out.print(";");
		for (GraphViewData data : listrpm22) {
			out.print(data.getY());
			out.print(";");
		}
		out.println("");
		out.print("rpm_3");
		out.print(";");
		for (GraphViewData data : listrpm32) {
			out.print(data.getY());
			out.print(";");
		}
		out.println("");
		out.print("rpm_4");
		out.print(";");
		for (GraphViewData data : listrpm42) {
			out.print(data.getY());
			out.print(";");
		}
		out.println("");
		out.print("angel_X");
		out.print(";");
		for (GraphViewData data : listAngelX2) {
			out.print(data.getY());
			out.print(";");
		}
		out.println("");
		out.print("angel_Y");
		out.print(";");
		for (GraphViewData data : listAngelY2) {
			out.print(data.getY());
			out.print(";");
		}
		out.println("");
		out.print("angel_Z");
		out.print(";");
		for (GraphViewData data : listAngelZ2) {
			out.print(data.getY());
			out.print(";");
		}

		// Flush the output to the file
		out.flush();

		// Close the Print Writer
		out.close();

		// Close the File Writer
		fw.close();
	}
}
