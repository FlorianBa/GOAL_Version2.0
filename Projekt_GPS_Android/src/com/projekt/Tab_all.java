package com.projekt;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.projekt.R;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class Tab_all extends Fragment {

	private GraphViewSeries series_acc_x,
	series_acc_y,
	series_acc_z,
	series_angle_x,
	series_angle_y,
	series_angle_z,
	series_rpm_1,
	series_rpm_2,
	series_rpm_3,
	series_rpm_4;

	private Runnable mTimerAcc, mTimerAngle, mTimerRPM;
	private final int refreshRate = 200, graphDataBuffer = 1000000, delayThread = 1;
	private final boolean scrollToEnd = true;
	private final Handler mHandler = new Handler();
	private View fragmentView;
	private volatile boolean isFragAlive;
	private MyCSVReportListener mCallback;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		fragmentView = inflater.inflate(R.layout.all_frag, container, false);

		generateGraph("Acceleration", R.id.graph_all_acc);
		generateGraph("Angle", R.id.graph_all_angle);
		generateGraph("RPM", R.id.graph_all_rpm);

		return fragmentView;
	}

	public void onStart(){
		super.onStart();
		isFragAlive = true;

		if(mCallback.getIsCSVReportSelected() == false){
			// No CSV-Report is selected and the normal Measurement will start

			if(((MainActivity)getActivity()).udpService != null){
				// Acceleration
				series_acc_x.resetData(((MainActivity)getActivity()).udpService.getAllGraphDataAccX());
				series_acc_y.resetData(((MainActivity)getActivity()).udpService.getAllGraphDataAccY());
				series_acc_z.resetData(((MainActivity)getActivity()).udpService.getAllGraphDataAccZ());

				// Angle
				series_angle_x.resetData(((MainActivity)getActivity()).udpService.getAllGraphDataAngleX());
				series_angle_y.resetData(((MainActivity)getActivity()).udpService.getAllGraphDataAngleY());
				series_angle_z.resetData(((MainActivity)getActivity()).udpService.getAllGraphDataAngleZ());

				// RPM
				series_rpm_1.resetData(((MainActivity)getActivity()).udpService.getAllGraphDatarpm1());
				series_rpm_2.resetData(((MainActivity)getActivity()).udpService.getAllGraphDatarpm2());
				series_rpm_3.resetData(((MainActivity)getActivity()).udpService.getAllGraphDatarpm3());
				series_rpm_4.resetData(((MainActivity)getActivity()).udpService.getAllGraphDatarpm4());
			}
			
			// Append Data
			appendGraphData(R.id.graph_all_acc);
			appendGraphData(R.id.graph_all_angle);
			appendGraphData(R.id.graph_all_rpm);
		}
		else{
			// CSV-Report is selected and will be shown on Graphs
			OpenCSVReport report = new OpenCSVReport(mCallback.getAbsolutCSVPath());

			// Acceleration
			series_acc_x.resetData(report.getAllGraphDataAccX());
			series_acc_y.resetData(report.getAllGraphDataAccY());
			series_acc_z.resetData(report.getAllGraphDataAccZ());

			// Angle
			series_angle_x.resetData(report.getAllGraphDataAngleX());
			series_angle_y.resetData(report.getAllGraphDataAngleY());
			series_angle_z.resetData(report.getAllGraphDataAngleZ());

			// RPM
			series_rpm_1.resetData(report.getAllGraphDatarpm1());
			series_rpm_2.resetData(report.getAllGraphDatarpm2());
			series_rpm_3.resetData(report.getAllGraphDatarpm3());
			series_rpm_4.resetData(report.getAllGraphDatarpm4());
		}
	}


	/*
	 *  Methode zum Anh�ngen von Daten an Graphen
	 */
	private void appendGraphData(int id) {

		switch(id){
		case R.id.graph_all_acc: 
			mTimerAcc= new Runnable() {
				@Override
				public void run() {
					if(isFragAlive){
						if(((MainActivity)getActivity()).udpService != null) {
							GraphViewData dataX = ((MainActivity)getActivity()).udpService.getCurrentGraphDataAccX();
							GraphViewData dataY = ((MainActivity)getActivity()).udpService.getCurrentGraphDataAccY();
							GraphViewData dataZ = ((MainActivity)getActivity()).udpService.getCurrentGraphDataAccZ();

							//GraphViewData dataX = GenerateTestData.getSinusData1();
							//GraphViewData dataY = GenerateTestData.getCosinusData1();
							//GraphViewData dataZ = GenerateTestData.getRandomData1();

							series_acc_x.appendData(dataX, scrollToEnd, graphDataBuffer);
							series_acc_y.appendData(dataY, scrollToEnd, graphDataBuffer);
							series_acc_z.appendData(dataZ, scrollToEnd, graphDataBuffer);
						}
						mHandler.postDelayed(this, refreshRate);
					}

				}
			};
			mHandler.postDelayed(mTimerAcc, delayThread);
			break;

		case R.id.graph_all_angle: 
			mTimerAngle= new Runnable() {
				@Override
				public void run() {
					if(isFragAlive){
						if(((MainActivity)getActivity()).udpService != null) {
							GraphViewData dataX = ((MainActivity)getActivity()).udpService.getCurrentGraphDataAngleX();
							GraphViewData dataY = ((MainActivity)getActivity()).udpService.getCurrentGraphDataAngleY();
							GraphViewData dataZ = ((MainActivity)getActivity()).udpService.getCurrentGraphDataAngleZ();

							//GraphViewData dataY = GenerateTestData.getSinusData2();
							//GraphViewData dataZ = GenerateTestData.getCosinusData2();
							//GraphViewData dataX = GenerateTestData.getRandomData2();

							series_angle_x.appendData(dataX, scrollToEnd, graphDataBuffer);
							series_angle_y.appendData(dataY, scrollToEnd, graphDataBuffer);
							series_angle_z.appendData(dataZ, scrollToEnd, graphDataBuffer);
						}
						mHandler.postDelayed(this, refreshRate);
					}

				}
			};
			mHandler.postDelayed(mTimerAngle, delayThread);
			break;

		case R.id.graph_all_rpm: 
			mTimerRPM= new Runnable() {
				@Override
				public void run() {
					if(isFragAlive){
						if(((MainActivity)getActivity()).udpService != null) {
							GraphViewData data1 = ((MainActivity)getActivity()).udpService.getCurrentGraphDatarpm1();
							GraphViewData data2 = ((MainActivity)getActivity()).udpService.getCurrentGraphDatarpm2();
							GraphViewData data3 = ((MainActivity)getActivity()).udpService.getCurrentGraphDatarpm3();
							GraphViewData data4 = ((MainActivity)getActivity()).udpService.getCurrentGraphDatarpm4();

							//GraphViewData data1 = GenerateTestData.getSinusData3();
							//GraphViewData data2 = GenerateTestData.getCosinusData3();
							//GraphViewData data3 = GenerateTestData.getRandomData3();
							//GraphViewData data4 = GenerateTestData.getRandomData4();

							series_rpm_1.appendData(data1, scrollToEnd, graphDataBuffer);
							series_rpm_2.appendData(data2, scrollToEnd, graphDataBuffer);
							series_rpm_3.appendData(data3, scrollToEnd, graphDataBuffer);
							series_rpm_4.appendData(data4, scrollToEnd, graphDataBuffer);
						}
						mHandler.postDelayed(this, refreshRate);
					}

				}
			};
			mHandler.postDelayed(mTimerRPM, delayThread);
			break;



		}
	}


	/*
	 *  Methode zum Initialisieren eines Graphen
	 */
	private void generateGraph(String name, int id){
		GraphView graphView = new LineGraphView(this.getActivity(), name);
		GraphViewData[] initData = new GraphViewData[]{new GraphViewData(0, 0)};
		GraphViewSeries.GraphViewSeriesStyle styleRed = new GraphViewSeries.GraphViewSeriesStyle(Color.RED,2);  
		GraphViewSeries.GraphViewSeriesStyle styleBlue = new GraphViewSeries.GraphViewSeriesStyle(Color.BLUE,2); 
		GraphViewSeries.GraphViewSeriesStyle styleGreen = new GraphViewSeries.GraphViewSeriesStyle(Color.GREEN,2); 
		GraphViewSeries.GraphViewSeriesStyle styleMagenta = new GraphViewSeries.GraphViewSeriesStyle(Color.MAGENTA,2); 

		switch(id){
		case R.id.graph_all_acc: 
			series_acc_x = new GraphViewSeries("x", styleRed,initData);
			series_acc_y = new GraphViewSeries("y", styleBlue,initData);
			series_acc_z = new GraphViewSeries("z", styleGreen,initData);
			graphView.addSeries(series_acc_x);
			graphView.addSeries(series_acc_y);
			graphView.addSeries(series_acc_z);
			break;

		case R.id.graph_all_angle:
			series_angle_x = new GraphViewSeries("x", styleRed,initData);
			series_angle_y = new GraphViewSeries("y", styleBlue,initData);
			series_angle_z = new GraphViewSeries("z", styleGreen,initData);
			graphView.addSeries(series_angle_x);
			graphView.addSeries(series_angle_y);
			graphView.addSeries(series_angle_z);
			break;

		case R.id.graph_all_rpm:
			series_rpm_1 = new GraphViewSeries("1", styleRed,initData);
			series_rpm_2 = new GraphViewSeries("2", styleBlue,initData);
			series_rpm_3 = new GraphViewSeries("3", styleGreen,initData);
			series_rpm_4 = new GraphViewSeries("4", styleMagenta,initData);
			graphView.addSeries(series_rpm_1);
			graphView.addSeries(series_rpm_2);
			graphView.addSeries(series_rpm_3);
			graphView.addSeries(series_rpm_4);
			break;
		}

		graphView.setViewPort(0, 100);
		graphView.setScalable(true);
		graphView.setScrollable(true);
		graphView.setManualYAxisBounds(4, 0);
		graphView.getGraphViewStyle().setNumHorizontalLabels(3);
		graphView.getGraphViewStyle().setNumVerticalLabels(3);
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.BOTTOM);

		LinearLayout layout = (LinearLayout) fragmentView.findViewById(id);
		layout.addView(graphView);
	}

	public void onStop(){
		super.onStop();
		isFragAlive = false;
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
			mCallback = (MyCSVReportListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement MyCSVReportListener");
		}
	}

}