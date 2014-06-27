package com.projekt;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * superclass for KMLReport and CSVReport
 * 
 * @author Aschenauer Dominic
 */
public abstract class Report {

	public static boolean extStorageState = false;
	public static final String extStoragePath = "/storage/extSdCard";
	public static final String folderPath = "/goal_reports";
	public static final String kmlFilePath = "/kml_report_";
	public static final String csvFilePath = "/csv_report_";
	public static final String csvFileEnding = ".csv";
	public static final String kmlFileEnding = ".kml";
	
	/**
	 * method to get the formatted date
	 * @return
	 */
	@SuppressLint("SimpleDateFormat") 
	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd_HHmmss");
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}
	
	/**
	 * method to set the storage location
	 * @param state
	 */
	public static void setExtStorage(boolean state) {
		extStorageState = state;
	}
}
