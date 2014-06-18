package com.projekt;


import com.jjoe64.graphview.GraphView;
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


public class Tab_rpm extends Fragment {

	private GraphViewSeries series_rpm1, series_rpm2, series_rpm3, series_rpm4;
	private Runnable mTimer1, mTimer2, mTimer3, mTimer4;
	private final int refreshRate = 200, graphDataBuffer = 1000000, delayThread = 1;
	private final boolean scrollToEnd = true;
	private final Handler mHandler = new Handler();
	private View fragmentView;
	private volatile boolean isFragAlive;
	private MyCSVReportListener mCallback;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		fragmentView = inflater.inflate(R.layout.rpm_frag, container, false);

		generateGraph("RPM Rear Left", R.id.graph_rpm_1);
		generateGraph("RPM Rear Right", R.id.graph_rpm_2);
		generateGraph("RPM Front Left", R.id.graph_rpm_3);
		generateGraph("RPM Front Right", R.id.graph_rpm_4);


		return fragmentView;
	}

	public void onStart(){
		super.onStart();

		isFragAlive = true;

		if(mCallback.getIsCSVReportSelected() == false){
			// No CSV-Report is selected and the normal Measurement will start

			if(((MainActivity)getActivity()).udpService != null){
				series_rpm1.resetData(((MainActivity)getActivity()).udpService.getAllGraphDatarpm1());
				series_rpm2.resetData(((MainActivity)getActivity()).udpService.getAllGraphDatarpm2());
				series_rpm3.resetData(((MainActivity)getActivity()).udpService.getAllGraphDatarpm3());
				series_rpm4.resetData(((MainActivity)getActivity()).udpService.getAllGraphDatarpm4());
			}

			// Append Data
			appendGraphData(R.id.graph_rpm_1);
			appendGraphData(R.id.graph_rpm_2);
			appendGraphData(R.id.graph_rpm_3);
			appendGraphData(R.id.graph_rpm_4);
		}
		else{
			// CSV-Report is selected and will be shown on Graphs
			OpenCSVReport report = new OpenCSVReport(mCallback.getAbsolutCSVPath());

			series_rpm1.resetData(report.getAllGraphDatarpm1());
			series_rpm2.resetData(report.getAllGraphDatarpm2());
			series_rpm3.resetData(report.getAllGraphDatarpm3());
			series_rpm4.resetData(report.getAllGraphDatarpm4());
		}
	}

	/*
	 *  Methode zum Anh�ngen von Daten an Graphen
	 */
	private void appendGraphData(int id) {

		switch(id){
		case R.id.graph_rpm_1: 
			mTimer1 = new Runnable() {
				@Override
				public void run() {
					if(isFragAlive){
						if(((MainActivity)getActivity()).udpService != null) {
							GraphViewData data = ((MainActivity)getActivity()).udpService.getCurrentGraphDatarpm1();
							//GraphViewData data = GenerateTestData.getRandomData5();

							series_rpm1.appendData(data, scrollToEnd, graphDataBuffer);
						}
						mHandler.postDelayed(this, refreshRate);
					}
				}
			};
			mHandler.postDelayed(mTimer1, delayThread);
			break;

		case R.id.graph_rpm_2: 
			mTimer2 = new Runnable() {
				@Override
				public void run() {
					if(isFragAlive){
						if(((MainActivity)getActivity()).udpService != null) {
							GraphViewData data = ((MainActivity)getActivity()).udpService.getCurrentGraphDatarpm2();
							//GraphViewData data = GenerateTestData.getCosinusData4();

							series_rpm2.appendData(data, scrollToEnd, graphDataBuffer);
						}
						mHandler.postDelayed(this, refreshRate);
					}
				}
			};
			mHandler.postDelayed(mTimer2, delayThread);
			break;

		case R.id.graph_rpm_3: 
			mTimer3 = new Runnable() {
				@Override
				public void run() {
					if(isFragAlive){
						if(((MainActivity)getActivity()).udpService != null) {
							GraphViewData data = ((MainActivity)getActivity()).udpService.getCurrentGraphDatarpm3();
							//GraphViewData data = GenerateTestData.getRandomData6();

							series_rpm3.appendData(data, scrollToEnd, graphDataBuffer);
						}
						mHandler.postDelayed(this, refreshRate);
					}
				}
			};
			mHandler.postDelayed(mTimer3, delayThread);
			break;

		case R.id.graph_rpm_4: 
			mTimer4 = new Runnable() {
				@Override
				public void run() {
					if(isFragAlive){
						if(((MainActivity)getActivity()).udpService != null) {
							GraphViewData data = ((MainActivity)getActivity()).udpService.getCurrentGraphDatarpm4();
							//GraphViewData data = GenerateTestData.getSinusData4();

							series_rpm4.appendData(data, scrollToEnd, graphDataBuffer);
						}
						mHandler.postDelayed(this, refreshRate);
					}
				}
			};
			mHandler.postDelayed(mTimer4, delayThread);
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
		case R.id.graph_rpm_1: 
			series_rpm1 = new GraphViewSeries("rpm1", styleRed,initData);
			graphView.addSeries(series_rpm1);;
			break;

		case R.id.graph_rpm_2:
			series_rpm2 = new GraphViewSeries("rpm2", styleBlue,initData);
			graphView.addSeries(series_rpm2);
			break;

		case R.id.graph_rpm_3:
			series_rpm3 = new GraphViewSeries("rpm3", styleGreen,initData);
			graphView.addSeries(series_rpm3);
			break;

		case R.id.graph_rpm_4:
			series_rpm4 = new GraphViewSeries("rpm4", styleMagenta,initData);
			graphView.addSeries(series_rpm4);
			break;
		}

		graphView.setViewPort(0, 100);
		graphView.setScalable(true);
		graphView.setScrollable(true);
		//graphView.setManualYAxisBounds(4, 0);
		graphView.getGraphViewStyle().setNumHorizontalLabels(3);
		graphView.getGraphViewStyle().setNumVerticalLabels(3);


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