package com.projekt;

import java.util.Random;

import com.jjoe64.graphview.GraphView.GraphViewData;

public class GenerateTestData {
	private static int time1 = 0, time2 = 0, time3 = 0, time4 = 0, time5 = 0, time6 = 0, time7 = 0, time8 = 0,
			time9 = 0, time10 = 0, time11 = 0, time12 = 0, time13 = 0, time14 = 0, time15 = 0, time16 = 0,
	 		time17 = 0, time18 = 0, time19 = 0, time20 = 0;
	private static GraphViewData data;
	private static double cos = 0;
	private static double sin = 0;
	
	public static GraphViewData getSinusData1(){
		time1++;
		sin += 0.1;
		data = new GraphViewData(time1, Math.sin(sin)*1.7+2);
		return data;
	}
	
	public static GraphViewData getSinusData2(){
		time5++;
		sin += 0.1;
		data = new GraphViewData(time5, Math.sin(sin)*1.2+2);
		return data;
	}
	
	public static GraphViewData getSinusData3(){
		time6++;
		sin += 0.1;
		data = new GraphViewData(time6, Math.sin(sin)*1.2+2);
		return data;
	}
	
	public static GraphViewData getSinusData4(){
		time7++;
		sin += 0.1;
		data = new GraphViewData(time7, Math.sin(sin)+2);
		return data;
	}
	
	public static GraphViewData getSinusData5(){
		time8++;
		sin += 0.1;
		data = new GraphViewData(time8, Math.sin(sin)+2);
		return data;
	}
	
	public static GraphViewData getSinusData6(){
		time19++;
		sin += 0.1;
		data = new GraphViewData(time19, Math.sin(sin)+2);
		return data;
	}
	
	
	
	
	
	
	public static GraphViewData getCosinusData1(){
		time2++;
		cos += 0.1;
		data = new GraphViewData(time2, Math.cos(cos)*1.3+2);
		return data;
	}
	
	public static GraphViewData getCosinusData2(){
		time9++;
		cos += 0.1;
		data = new GraphViewData(time9, Math.cos(cos)*1.5+2);
		return data;
	}
	
	public static GraphViewData getCosinusData3(){
		time10++;
		cos += 0.1;
		data = new GraphViewData(time10, Math.cos(cos)*1.4+2);
		return data;
	}
	
	public static GraphViewData getCosinusData4(){
		time11++;
		cos += 0.1;
		data = new GraphViewData(time11, Math.cos(cos)+2);
		return data;
	}
	
	public static GraphViewData getCosinusData5(){
		time12++;
		cos += 0.1;
		data = new GraphViewData(time12, Math.cos(cos)+2);
		return data;
	}
	
	public static GraphViewData getCosinusData6(){
		time20++;
		cos += 0.1;
		data = new GraphViewData(time20, Math.cos(cos)+2);
		return data;
	}
	
	
	
	
	
	public static GraphViewData getRandomData1(){
		Random r = new Random();
		time3++;
		data = new GraphViewData(time3, r.nextDouble()*2.1+1);
		return data;
	}
	
	public static GraphViewData getRandomData2(){
		Random r = new Random();
		time4++;
		data = new GraphViewData(time4, r.nextDouble()*2.4+1);
		return data;
	}
	
	public static GraphViewData getRandomData3(){
		Random r = new Random();
		time13++;
		data = new GraphViewData(time13, r.nextDouble()*2.2+1);
		return data;
	}
	
	public static GraphViewData getRandomData4(){
		Random r = new Random();
		time14++;
		data = new GraphViewData(time14, r.nextDouble()*2.5+1);
		return data;
	}
	
	public static GraphViewData getRandomData5(){
		Random r = new Random();
		time15++;
		data = new GraphViewData(time15, r.nextDouble()*2+1);
		return data;
	}
	
	public static GraphViewData getRandomData6(){
		Random r = new Random();
		time16++;
		data = new GraphViewData(time16, r.nextDouble()*2+1);
		return data;
	}
	
	public static GraphViewData getRandomData7(){
		Random r = new Random();
		time17++;
		data = new GraphViewData(time17, r.nextDouble()*2+1);
		return data;
	}
	
	public static GraphViewData getRandomData8(){
		Random r = new Random();
		time18++;
		data = new GraphViewData(time18, r.nextDouble()*2+1);
		return data;
	}
}
