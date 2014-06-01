package com.projekt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jjoe64.graphview.GraphView.GraphViewData;

public class OpenCSVReport {

	public List<GraphViewData> listAccX = new ArrayList<GraphViewData>();
	public List<GraphViewData> listAccY = new ArrayList<GraphViewData>();
	public List<GraphViewData> listAccZ = new ArrayList<GraphViewData>();
	public List<GraphViewData> listrpm1 = new ArrayList<GraphViewData>();
	public List<GraphViewData> listrpm2 = new ArrayList<GraphViewData>();
	public List<GraphViewData> listrpm3 = new ArrayList<GraphViewData>();
	public List<GraphViewData> listrpm4 = new ArrayList<GraphViewData>();
	public List<GraphViewData> listAngleX = new ArrayList<GraphViewData>();
	public List<GraphViewData> listAngleY = new ArrayList<GraphViewData>();
	public List<GraphViewData> listAngleZ = new ArrayList<GraphViewData>();

	public OpenCSVReport(String pathCsvFile) {
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] data = null;

		try {

			br = new BufferedReader(new FileReader(pathCsvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				data = line.split(cvsSplitBy);
				
				//save data in correct list
				if(data[0].compareTo("Acc_X") == 0){
					for(String s: data){
						if(s.compareTo("Acc_X") != 0){
							try {
								listAccX.add(new GraphViewData(0, Double.parseDouble(s)));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(data[0].compareTo("Acc_Y") == 0){
					for(String s: data){
						if(s.compareTo("Acc_Y") != 0){
							try {
								listAccY.add(new GraphViewData(0, Double.parseDouble(s)));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(data[0].compareTo("Acc_Z") == 0){
					for(String s: data){
						if(s.compareTo("Acc_Y") != 0){
							try {
								listAccZ.add(new GraphViewData(0, Double.parseDouble(s)));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(data[0].compareTo("rpm_1") == 0){
					for(String s: data){
						if(s.compareTo("rpm_1") != 0){
							try {
								listrpm1.add(new GraphViewData(0, Double.parseDouble(s)));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(data[0].compareTo("rpm_2") == 0){
					for(String s: data){
						if(s.compareTo("rpm_2") != 0){
							try {
								listrpm2.add(new GraphViewData(0, Double.parseDouble(s)));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(data[0].compareTo("rpm_3") == 0){
					for(String s: data){
						if(s.compareTo("rpm_3") != 0){
							try {
								listrpm3.add(new GraphViewData(0, Double.parseDouble(s)));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(data[0].compareTo("rpm_4") == 0){
					for(String s: data){
						if(s.compareTo("rpm_4") != 0){
							try {
								listrpm4.add(new GraphViewData(0, Double.parseDouble(s)));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(data[0].compareTo("angle_X") == 0){
					for(String s: data){
						if(s.compareTo("angle_X") != 0){
							try {
								listAngleX.add(new GraphViewData(0, Double.parseDouble(s)));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(data[0].compareTo("angle_Y") == 0){
					for(String s: data){
						if(s.compareTo("angle_Y") != 0){
							try {
								listAngleY.add(new GraphViewData(0, Double.parseDouble(s)));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(data[0].compareTo("angle_Z") == 0){
					for(String s: data){
						if(s.compareTo("angle_Z") != 0){
							try {
								listAngleZ.add(new GraphViewData(0, Double.parseDouble(s)));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public GraphViewData getCurrentGraphDataAccX(){
		if(!listAccX.isEmpty())
			return listAccX.get(listAccX.size() - 1);
		else 
			return new GraphViewData(0.0,0.0);
	}

	public GraphViewData getCurrentGraphDataAccY(){
		if(!listAccY.isEmpty())
			return listAccY.get(listAccY.size() - 1);
		else 
			return new GraphViewData(0.0,0.0);
	}

	public GraphViewData getCurrentGraphDataAccZ(){
		if(!listAccZ.isEmpty())
			return listAccZ.get(listAccZ.size() - 1);
		else 
			return new GraphViewData(0.0,0.0);
	}

	public GraphViewData getCurrentGraphDatarpm1(){
		if(!listrpm1.isEmpty())
			return listrpm1.get(listrpm1.size() - 1);
		else 
			return new GraphViewData(0.0,0.0);
	}

	public GraphViewData getCurrentGraphDatarpm2(){
		if(!listrpm2.isEmpty())
			return listrpm2.get(listrpm2.size() - 1);
		else 
			return new GraphViewData(0.0,0.0);
	}

	public GraphViewData getCurrentGraphDatarpm3(){
		if(!listrpm3.isEmpty())
			return listrpm3.get(listrpm3.size() - 1);
		else 
			return new GraphViewData(0.0,0.0);
	}

	public GraphViewData getCurrentGraphDatarpm4(){
		if(!listrpm4.isEmpty())
			return listrpm4.get(listrpm4.size() - 1);
		else 
			return new GraphViewData(0.0,0.0);
	}

	public GraphViewData getCurrentGraphDataAngleX(){
		if(!listAngleX.isEmpty())
			return listAngleX.get(listAngleX.size() - 1);
		else 
			return new GraphViewData(0.0,0.0);
	}

	public GraphViewData getCurrentGraphDataAngleY(){
		if(!listAngleY.isEmpty())
			return listAngleY.get(listAngleY.size() - 1);
		else 
			return new GraphViewData(0.0,0.0);
	}

	public GraphViewData getCurrentGraphDataAngleZ(){
		if(!listAngleZ.isEmpty())
			return listAngleZ.get(listAngleZ.size() - 1);
		else 
			return new GraphViewData(0.0,0.0);
	}

	public GraphViewData[] getAllGraphDataAccX(){
		if(!listAccX.isEmpty())
			return (GraphViewData[]) listAccX.toArray( new GraphViewData[listAccX.size()] );
		else{
			listAccX.add(new GraphViewData(0.0,0.0));
			return (GraphViewData[]) listAccX.toArray( new GraphViewData[listAccX.size()] );
		}
	}

	public GraphViewData[] getAllGraphDataAccY(){
		if(!listAccY.isEmpty())
			return (GraphViewData[]) listAccY.toArray( new GraphViewData[listAccY.size()] );
		else{
			listAccY.add(new GraphViewData(0.0,0.0));
			return (GraphViewData[]) listAccY.toArray( new GraphViewData[listAccY.size()] );
		}
	}

	public GraphViewData[] getAllGraphDataAccZ(){
		if(!listAccZ.isEmpty())
			return (GraphViewData[]) listAccZ.toArray( new GraphViewData[listAccZ.size()] );
		else{
			listAccZ.add(new GraphViewData(0.0,0.0));
			return (GraphViewData[]) listAccZ.toArray( new GraphViewData[listAccZ.size()] );
		}
	}

	public GraphViewData[] getAllGraphDatarpm1(){
		if(!listrpm1.isEmpty())
			return (GraphViewData[]) listrpm1.toArray( new GraphViewData[listrpm1.size()] );
		else{
			listrpm1.add(new GraphViewData(0.0,0.0));
			return (GraphViewData[]) listrpm1.toArray( new GraphViewData[listrpm1.size()] );
		}
	}

	public GraphViewData[] getAllGraphDatarpm2(){
		if(!listrpm2.isEmpty())
			return (GraphViewData[]) listrpm2.toArray( new GraphViewData[listrpm2.size()] );
		else{
			listrpm2.add(new GraphViewData(0.0,0.0));
			return (GraphViewData[]) listrpm2.toArray( new GraphViewData[listrpm2.size()] );
		}
	}

	public GraphViewData[] getAllGraphDatarpm3(){
		if(!listrpm3.isEmpty())
			return (GraphViewData[]) listrpm3.toArray( new GraphViewData[listrpm3.size()] );
		else{
			listrpm3.add(new GraphViewData(0.0,0.0));
			return (GraphViewData[]) listrpm3.toArray( new GraphViewData[listrpm3.size()] );
		}
	}

	public GraphViewData[] getAllGraphDatarpm4(){
		if(!listrpm4.isEmpty())
			return (GraphViewData[]) listrpm4.toArray( new GraphViewData[listrpm4.size()] );
		else{
			listrpm4.add(new GraphViewData(0.0,0.0));
			return (GraphViewData[]) listrpm4.toArray( new GraphViewData[listrpm4.size()] );
		}
	}

	public GraphViewData[] getAllGraphDataAngleX(){
		if(!listAngleX.isEmpty())
			return (GraphViewData[]) listAngleX.toArray( new GraphViewData[listAngleX.size()] );
		else{
			listAngleX.add(new GraphViewData(0.0,0.0));
			return (GraphViewData[]) listAngleX.toArray( new GraphViewData[listAngleX.size()] );
		}
	}

	public GraphViewData[] getAllGraphDataAngleY(){
		if(!listAngleY.isEmpty())
			return (GraphViewData[]) listAngleY.toArray( new GraphViewData[listAngleY.size()] );
		else{
			listAngleY.add(new GraphViewData(0.0,0.0));
			return (GraphViewData[]) listAngleY.toArray( new GraphViewData[listAngleY.size()] );
		}
	}

	public GraphViewData[] getAllGraphDataAngleZ(){
		if(!listAngleZ.isEmpty())
			return (GraphViewData[]) listAngleZ.toArray( new GraphViewData[listAngleZ.size()] );
		else{
			listAngleZ.add(new GraphViewData(0.0,0.0));
			return (GraphViewData[]) listAngleZ.toArray( new GraphViewData[listAngleZ.size()] );
		}
	}
}
