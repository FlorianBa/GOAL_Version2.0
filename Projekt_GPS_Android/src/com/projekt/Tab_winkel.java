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
public class Tab_winkel extends Fragment {

	private GraphViewSeries series_x, series_y, series_z;
	private Runnable mTimerX, mTimerY, mTimerZ;
	private final int refreshRate = 200, graphDataBuffer = 1000000, delayThread = 1;
	private final boolean scrollToEnd = true;
	private final Handler mHandler = new Handler();
	private View fragmentView;
	private volatile boolean isFragAlive;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		fragmentView = inflater.inflate(R.layout.winkel_frag, container, false);

		generateGraph("Angle X", R.id.graph_angle_x);
		generateGraph("Angle y", R.id.graph_angle_y);
		generateGraph("Angle z", R.id.graph_angle_z);

		return fragmentView;
	}

	public void onStart(){
		super.onStart();

		isFragAlive = true;
		// Wenn später die Daten durch einen Service geliefert werden, muss hier ein Reset der Series stattfinden

		appendGraphData(R.id.graph_angle_x);
		appendGraphData(R.id.graph_angle_y);
		appendGraphData(R.id.graph_angle_z);
	}

	/*
	 *  Methode zum Anhängen von Daten an Graphen
	 */
	private void appendGraphData(int id) {

		switch(id){
		case R.id.graph_angle_x: 
			if(mTimerX == null){
				mTimerX = new Runnable() {
					@Override
					public void run() {
						if(isFragAlive){
						if(((MainActivity)getActivity()).tcpService != null) {
							GraphViewData data = ((MainActivity)getActivity()).tcpService.getCurrentGraphDataAngleX();			
							series_x.appendData(data, scrollToEnd, graphDataBuffer);
						}
						mHandler.postDelayed(this, refreshRate);
						}
					}
				};
				mHandler.postDelayed(mTimerX, delayThread);
			}
			break;

		case R.id.graph_angle_y: 
			if(mTimerY == null){
				mTimerY = new Runnable() {
					@Override
					public void run() {
						if(isFragAlive){
						if(((MainActivity)getActivity()).tcpService != null) {
							GraphViewData data = ((MainActivity)getActivity()).tcpService.getCurrentGraphDataAngleY();						
							series_y.appendData(data, scrollToEnd, graphDataBuffer);
						}
						mHandler.postDelayed(this, refreshRate);
						}
					}
				};
				mHandler.postDelayed(mTimerY, delayThread);
			}
			break;

		case R.id.graph_angle_z: 
			if(mTimerZ == null){
				mTimerZ = new Runnable() {
					@Override
					public void run() {
						if(isFragAlive){
						if(((MainActivity)getActivity()).tcpService != null) {
							GraphViewData data = ((MainActivity)getActivity()).tcpService.getCurrentGraphDataAngleZ();						
							series_z.appendData(data, scrollToEnd, graphDataBuffer);
						}
						mHandler.postDelayed(this, refreshRate);
						}
					}
				};
				mHandler.postDelayed(mTimerZ, delayThread);
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

		switch(id){
		case R.id.graph_angle_x: 
			series_x = new GraphViewSeries("x", styleRed,initData);
			graphView.addSeries(series_x);;
			break;

		case R.id.graph_angle_y:
			series_y = new GraphViewSeries("y", styleBlue,initData);
			graphView.addSeries(series_y);
			break;

		case R.id.graph_angle_z:
			series_z = new GraphViewSeries("z", styleGreen,initData);
			graphView.addSeries(series_z);
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