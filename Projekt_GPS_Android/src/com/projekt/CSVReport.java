package com.projekt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.jjoe64.graphview.GraphView.GraphViewData;

import android.os.Environment;

public class CSVReport extends Report {

	public static void createCSVReport(List<GraphViewData> listAccX,
			List<GraphViewData> listAccY, List<GraphViewData> listAccZ,
			List<GraphViewData> listrpm1, List<GraphViewData> listrpm2,
			List<GraphViewData> listrpm3, List<GraphViewData> listrpm4,
			List<GraphViewData> listAngleX, List<GraphViewData> listAngleY,
			List<GraphViewData> listAngleZ, List<LatLng> listCoo)
			throws IOException {

		FileWriter fw;

		if (!extStorageState) {
			File csvFolderInt = new File(
					Environment.getExternalStorageDirectory() + folderPath);
			if (!csvFolderInt.exists()) {
				csvFolderInt.mkdir();
			}
			fw = new FileWriter(Environment.getExternalStorageDirectory()
					+ folderPath + csvFilePath + getDate() + csvFileEnding);
		} else {
			File csvFolderInt = new File(extStoragePath + folderPath);
			if (!csvFolderInt.exists()) {
				csvFolderInt.mkdir();
			}
			fw = new FileWriter(extStoragePath + folderPath + csvFilePath
					+ getDate() + csvFileEnding);
		}

		PrintWriter out = new PrintWriter(fw);

		out.print("time");
		out.print(",");
		for (GraphViewData data : listAccX) {
			out.print(data.getX());
			out.print(",");
		}
		out.println("");
		out.print("Acc_X");
		out.print(",");
		for (GraphViewData data : listAccX) {
			out.print(data.getY());
			out.print(",");
		}
		out.println("");
		out.print("Acc_Y");
		out.print(",");
		for (GraphViewData data : listAccY) {
			out.print(data.getY());
			out.print(",");
		}
		out.println("");
		out.print("Acc_Z");
		out.print(",");
		for (GraphViewData data : listAccZ) {
			out.print(data.getY());
			out.print(",");
		}
		out.println("");
		out.print("rpm_1");
		out.print(",");
		for (GraphViewData data : listrpm1) {
			out.print(data.getY());
			out.print(",");
		}
		out.println("");
		out.print("rpm_2");
		out.print(",");
		for (GraphViewData data : listrpm2) {
			out.print(data.getY());
			out.print(",");
		}
		out.println("");
		out.print("rpm_3");
		out.print(",");
		for (GraphViewData data : listrpm3) {
			out.print(data.getY());
			out.print(",");
		}
		out.println("");
		out.print("rpm_4");
		out.print(",");
		for (GraphViewData data : listrpm4) {
			out.print(data.getY());
			out.print(",");
		}
		out.println("");
		out.print("angle_X");
		out.print(",");
		for (GraphViewData data : listAngleX) {
			out.print(data.getY());
			out.print(",");
		}
		out.println("");
		out.print("angle_Y");
		out.print(",");
		for (GraphViewData data : listAngleY) {
			out.print(data.getY());
			out.print(",");
		}
		out.println("");
		out.print("angle_Z");
		out.print(",");
		for (GraphViewData data : listAngleZ) {
			out.print(data.getY());
			out.print(",");
		}
		out.println("");
		out.print("latitude");
		out.print(",");
		for (LatLng data : listCoo) {
			out.print(data.latitude);
			out.print(",");
		}
		out.println("");
		out.print("longitude");
		out.print(",");
		for (LatLng data : listCoo) {
			out.print(data.longitude);
			out.print(",");
		}

		// Flush the output to the file
		out.flush();

		// Close the Print Writer
		out.close();

		// Close the File Writer
		fw.close();
	}
}
