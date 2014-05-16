package com.projekt;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.projekt.R;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/*
 * Bei diesem Fragment fehlt noch die Anbindung an einem Service der die entsprechenden Daten liefert
 */
public class Tab_rpm extends Fragment {

	private GraphViewSeries series_rpm1, series_rpm2, series_rpm3, series_rpm4;
	private Runnable mTimer1, mTimer2, mTimer3, mTimer4;
	private final int refreshRate = 200, graphDataBuffer = 1000000, delayThread = 1;
	private final boolean scrollToEnd = true;
	private final Handler mHandler = new Handler();
	private View fragmentView;
	private volatile boolean isFragAlive;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		fragmentView = inflater.inflate(R.layout.rpm_frag, container, false);

		generateGraph("rpm 1", R.id.graph_rpm_1);
		generateGraph("rpm 2", R.id.graph_rpm_2);
		generateGraph("rpm 3", R.id.graph_rpm_3);
		generateGraph("rpm 4", R.id.graph_rpm_4);

		return fragmentView;
	}

	public void onStart(){
		super.onStart();

		isFragAlive = true;
		// Wenn später die Daten durch einen Service geliefert werden, muss hier ein Reset der Series stattfinden

		appendGraphData(R.id.graph_rpm_1);
		appendGraphData(R.id.graph_rpm_2);
		appendGraphData(R.id.graph_rpm_3);
		appendGraphData(R.id.graph_rpm_4);
	}

	/*
	 *  Methode zum Anhängen von Daten an Graphen
	 */
	private void appendGraphData(int id) {

		switch(id){
		case R.id.graph_rpm_1: 
			if(mTimer1 == null){
				mTimer1 = new Runnable() {
					@Override
					public void run() {
						if(isFragAlive){
							if(((MainActivity)getActivity()).tcpService != null) {
								GraphViewData data = ((MainActivity)getActivity()).tcpService.getCurrentGraphDatarpm1();
								series_rpm1.appendData(data, scrollToEnd, graphDataBuffer);
							}
							mHandler.postDelayed(this, refreshRate);
						}
					}
				};
				mHandler.postDelayed(mTimer1, delayThread);
			}
			break;

		case R.id.graph_rpm_2: 
			if(mTimer2 == null){
				mTimer2 = new Runnable() {
					@Override
					public void run() {
						if(isFragAlive){
							if(((MainActivity)getActivity()).tcpService != null) {
								GraphViewData data = ((MainActivity)getActivity()).tcpService.getCurrentGraphDatarpm2();
								series_rpm2.appendData(data, scrollToEnd, graphDataBuffer);
							}
							mHandler.postDelayed(this, refreshRate);
						}
					}
				};
				mHandler.postDelayed(mTimer2, delayThread);
			}
			break;

		case R.id.graph_rpm_3: 
			if(mTimer3 == null){
				mTimer3 = new Runnable() {
					@Override
					public void run() {
						if(isFragAlive){
							if(((MainActivity)getActivity()).tcpService != null) {
								GraphViewData data = ((MainActivity)getActivity()).tcpService.getCurrentGraphDatarpm3();
								series_rpm3.appendData(data, scrollToEnd, graphDataBuffer);
							}
							mHandler.postDelayed(this, refreshRate);
						}
					}
				};
				mHandler.postDelayed(mTimer3, delayThread);
			}
			break;

		case R.id.graph_rpm_4: 
			if(mTimer4 == null){
				mTimer4 = new Runnable() {
					@Override
					public void run() {
						if(isFragAlive){
							if(((MainActivity)getActivity()).tcpService != null) {
								GraphViewData data = ((MainActivity)getActivity()).tcpService.getCurrentGraphDatarpm4();
								series_rpm4.appendData(data, scrollToEnd, graphDataBuffer);
							}
							mHandler.postDelayed(this, refreshRate);
						}
					}
				};
				mHandler.postDelayed(mTimer4, delayThread);
			}
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
		graphView.setManualYAxisBounds(4, 0);
		graphView.getGraphViewStyle().setNumHorizontalLabels(3);
		graphView.getGraphViewStyle().setNumVerticalLabels(3);


		LinearLayout layout = (LinearLayout) fragmentView.findViewById(id);
		layout.addView(graphView);
	}

	public void onStop(){
		super.onStop();
		isFragAlive = false;
	}
}