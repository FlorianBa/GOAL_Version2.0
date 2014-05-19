package com.projekt;

import com.jjoe64.graphview.GraphView.GraphViewData;

public class GenerateTestData {
	private static int time = 0;
	private static GraphViewData data;
	private static double cos = 0;
	private static double sin = 0;
	
	public static GraphViewData getSinusData(){
		time++;
		sin += 0.1;
		data = new GraphViewData(time, Math.sin(sin)+2);
		return data;
	}
	
	public static GraphViewData getCosinusData(){
		time++;
		cos += 0.1;
		data = new GraphViewData(time, Math.cos(cos)+2);
		return data;
	}
}
