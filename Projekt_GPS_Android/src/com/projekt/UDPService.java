package com.projekt;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.jjoe64.graphview.GraphView;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthiasuttendorfer on 5/22/14.
 */
public class UDPService extends Service {

    private final static int SOF = 0x43;


    public Socket socket;
    public DatagramSocket socketUDP;
    public static final int SERVERPORT = 4000;

    private MainActivity linkToParent;





    // Arrays to store received data

    private  List<GraphView.GraphViewData> listAccX = new ArrayList<GraphView.GraphViewData>(); // storage of acceleration values x-component
    private  List<GraphView.GraphViewData> listAccY = new ArrayList<GraphView.GraphViewData>(); // storage of acceleration values y-component
    private  List<GraphView.GraphViewData> listAccZ = new ArrayList<GraphView.GraphViewData>(); // storage of acceleration values z-component
    private  List<GraphView.GraphViewData> listrpm1 = new ArrayList<GraphView.GraphViewData>(); // storage of rounds per minute value left front wheel
    private  List<GraphView.GraphViewData> listrpm2 = new ArrayList<GraphView.GraphViewData>(); // storage of rounds per minute value right front wheel
    private  List<GraphView.GraphViewData> listrpm3 = new ArrayList<GraphView.GraphViewData>(); // storage of rounds per minute value left rear wheel
    private  List<GraphView.GraphViewData> listrpm4 = new ArrayList<GraphView.GraphViewData>(); // storage of rounds per minute value right rear wheel
    private  List<GraphView.GraphViewData> listAngleX = new ArrayList<GraphView.GraphViewData>();  // storage of roll rate of vehicle
    private  List<GraphView.GraphViewData> listAngleY = new ArrayList<GraphView.GraphViewData>();  // storage of yaw rate of vehicle
    private  List<GraphView.GraphViewData> listAngleZ = new ArrayList<GraphView.GraphViewData>();  // storage of pitch rate of vehicle
    private  List<LatLng> listLocations = new ArrayList<LatLng>();


    // Helper flag to enable external and internal persistence storing

    private static boolean enableSaving = false;


    // received data counter

   private  double timestamp = 0;





    // Binder method for connecting class to MainActivity

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        //intent.getClass()
        return myBinder;
    }


    // method to link Service

    public void setLink(Object parent) {
        linkToParent = (MainActivity) parent;
    }

    // custom binder class

    private final IBinder myBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public UDPService getService() {
            return UDPService.this;
        }
    }


    // Starting point of Service

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        socket = new Socket();
        Log.d("UDP" , "On create");
    }


    // Deinitializer

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        socket = null;
    }


    // Start Service Call

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Runnable connect = new connectSocket();

        new Thread(connect).start();
        Log.d("UDP", "on start command");
        return super.onStartCommand(intent, flags, startId);
    }


    // Class to start working Thread and bind to Logic

    class connectSocket implements Runnable {
        @Override
        public void run() {
                Log.d("UDP", "udp service is running" );
            try {
                byte[] buffer = new byte[28];
                DatagramPacket p = new DatagramPacket(buffer, buffer.length);
                socketUDP = new DatagramSocket(SERVERPORT);


                while (!Thread.currentThread().isInterrupted()) {

                    try {
                      //  Log.d("UDP", "try to receive data");
                        socketUDP.receive(p);
                      processReceivedData(p);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



            } catch (IOException e) {
                Log.d("UDP" , "run exception");
                e.printStackTrace();

            }
        }
    }


    // Callback for receiving packages over udp

    private void processReceivedData(DatagramPacket p) {

        timestamp = (timestamp+1);
        byte[] buffer = new byte[28];
        try {
           buffer =  p.getData();


           // Log.d("UDP", buffer[8]+ "das ist unser buffer du hur");
          //  if (buffer[0] == SOF) {

                switch ((buffer[8])) {

                    case 16:

//                        BO_ 16 IMU_ACC: 8 Vector__XXX
//                        SG_ Z_ACC : 32|16@1- (0.005,0) [-81.92|81.92] "m/s^2" Vector__XXX
//                        SG_ Y_ACC : 16|16@1- (0.005,0) [-81.92|81.92] "m/s^2" Vector__XXX
//                        SG_ X_ACC : 0|16@1- (0.005,0) [-81.92|81.92] "m/s^2" Vector__XXX

                       // Log.i("TCP", "current buffer" + buffer.toString());
                       // Log.d("UDP", "processing new data");
//                       double value =  (getValueFromBytes(buffer, 15, 16, false))/8000;
//                        Log.d("UDP","" + value + "at:" + timestamp);
//                        GraphView.GraphViewData graphData = new GraphView.GraphViewData(timestamp, value);
//                        listAccX.add(graphData);
                       listAccX.add(new GraphView.GraphViewData(timestamp, getValueFromBytes(buffer, 13, 14, false) *0.005)) ;
                        listAccY.add(new GraphView.GraphViewData(timestamp, getValueFromBytes(buffer, 15, 16, false) * 0.005));
                        listAccZ.add(new GraphView.GraphViewData(timestamp, getValueFromBytes(buffer, 17, 18, false) * 0.005));

                        Log.d("UDP", "" + listAccX.get(listAccX.size()-1)) ;
                        Log.d("count", " " + listAccX.size());
                        break;

                    case 18:

//                        SG_ Raddrehzahl_vr : 48|16@1+ (0.005,0) [0|327.675] "1/s" Vector__XXX
//                        SG_ Raddrehzahl_vl : 32|16@1+ (0.005,0) [0|327.675] "1/s" Vector__XXX
//                        SG_ Raddrehzahl_hr : 16|16@1+ (0.005,0) [0|327.675] "1/s" Vector__XXX
//                        SG_ Raddrehzahl_hl : 0|16@1+ (0.005,0) [0|327.675] "1/s" Vector__XXX
                        timestamp = listrpm1.size();
                        listrpm1.add(new GraphView.GraphViewData(timestamp, getValueFromBytes(buffer, 13, 14, false)*0.005));
                        listrpm2.add(new GraphView.GraphViewData(timestamp, getValueFromBytes(buffer, 15, 16, false)*0.005));
                        listrpm3.add(new GraphView.GraphViewData(timestamp, getValueFromBytes(buffer, 17, 18, false)*0.005));
                        listrpm4.add(new GraphView.GraphViewData(timestamp, getValueFromBytes(buffer, 19, 20, false)*0.005));

                        break;

                    case 22:
//                        BO_ 22 Algo_attitude_observer: 8 Vector__XXX
//                        SG_ RollAngle : 16|16@1- (1,0) [0|0] "grad" Vector__XXX
//                        SG_ PitchAngle : 0|16@1- (1,0) [0|0] "grad" Vector__XXX
                        timestamp = listAngleX.size();
                        listAngleX.add(new GraphView.GraphViewData(timestamp, getValueFromBytes(buffer, 13, 14, false) * 1.0));
                        listAngleY.add(new GraphView.GraphViewData(timestamp, getValueFromBytes(buffer, 15, 16, false) * 1.0));
                        //listAngleZ.add(new GraphView.GraphViewData(timestamp, getValueFromBytes(buffer, 17, 18, false) * 1.0));


                        break;

                    case 19:

//                        BO_ 19 GPS_lat_long: 8 Vector__XXX
//                        SG_ GPS_longitude : 32|32@1+ (1E-006,0) [0|4295] "grad" Vector__XXX
//                        SG_ GPS_latitude : 0|32@1+ (1E-006,0) [0|4295] "grad" Vector__XXX
                        listLocations.add(new LatLng( (getValueFromBytes(buffer, 13, 16, false)*1E-006) , (getValueFromBytes(buffer, 17, 20, false) *1E-006) ));

                        break;
                    case 17:
//                        BO_ 21 Steering_signals: 8 Vector__XXX
//                        SG_ Steering_Volt : 16|16@1+ (8E-005,0) [0|5.2429] "V" Vector__XXX
//                        SG_ Steering_Frontaxis : 0|16@1- (1,0) [-32768|32767] "rad" Vector__XXX

                        listAngleZ.add(new GraphView.GraphViewData(timestamp, getValueFromBytes(buffer, 17, 18, false) * 0.02));

                        break;
                }
            //}


        } catch (Exception e) {

        }
    }


    // static function: processing byte values of CAN frames


    public static Double getValueFromBytes(byte[] buffer,
                                           int startByte, int endByte, Boolean byteIsUnsigned) {

        Double result = new Double(0.0);


        for (int i = startByte; i <= endByte; i++) {
            if (!byteIsUnsigned && i == endByte) {
                result += new Double(buffer[i])
                        * Math.pow(2, 8 * (i - startByte));

            } else {
                result += new Double(unsignedByteToInt(buffer[i]))
                        * Math.pow(2, 8 * (i - startByte));

            }
        }

        return result;
    }

    private static int unsignedByteToInt(byte b) {
        return (int) b & 0xFF;
    }



    //  Link methods for retrieving stored values


    public GraphView.GraphViewData getCurrentGraphDataAccX() {

        if (!listAccX.isEmpty())

            return listAccX.get(listAccX.size() - 1);
        else
            return new GraphView.GraphViewData(0.0, 0.0);
    }

    public GraphView.GraphViewData getCurrentGraphDataAccY() {
        if (!listAccY.isEmpty())
            return listAccY.get(listAccY.size() - 1);
        else
            return new GraphView.GraphViewData(0.0, 0.0);
    }

    public GraphView.GraphViewData getCurrentGraphDataAccZ() {
        if (!listAccZ.isEmpty())
            return listAccZ.get(listAccZ.size() - 1);
        else
            return new GraphView.GraphViewData(0.0, 0.0);
    }

    public GraphView.GraphViewData getCurrentGraphDatarpm1() {
        if (!listrpm1.isEmpty())
            return listrpm1.get(listrpm1.size() - 1);
        else
            return new GraphView.GraphViewData(0.0, 0.0);
    }

    public GraphView.GraphViewData getCurrentGraphDatarpm2() {
        if (!listrpm2.isEmpty())
            return listrpm2.get(listrpm2.size() - 1);
        else
            return new GraphView.GraphViewData(0.0, 0.0);
    }

    public GraphView.GraphViewData getCurrentGraphDatarpm3() {
        if (!listrpm3.isEmpty())
            return listrpm3.get(listrpm3.size() - 1);
        else
            return new GraphView.GraphViewData(0.0, 0.0);
    }

    public GraphView.GraphViewData getCurrentGraphDatarpm4() {
        if (!listrpm4.isEmpty())
            return listrpm4.get(listrpm4.size() - 1);
        else
            return new GraphView.GraphViewData(0.0, 0.0);
    }

    public GraphView.GraphViewData getCurrentGraphDataAngleX() {
        if (!listAngleX.isEmpty())
            return listAngleX.get(listAngleX.size() - 1);
        else
            return new GraphView.GraphViewData(0.0, 0.0);
    }

    public GraphView.GraphViewData getCurrentGraphDataAngleY() {
        if (!listAngleY.isEmpty())
            return listAngleY.get(listAngleY.size() - 1);
        else
            return new GraphView.GraphViewData(0.0, 0.0);
    }

    public GraphView.GraphViewData getCurrentGraphDataAngleZ() {
        if (!listAngleZ.isEmpty())
            return listAngleZ.get(listAngleZ.size() - 1);
        else
            return new GraphView.GraphViewData(0.0, 0.0);
    }

    public LatLng getCurrentLocation() {
        if (!listLocations.isEmpty())
            return listLocations.get(listLocations.size() - 1);
        else
            return new LatLng(0.0, 0.0);
    }

    public List<LatLng> getAllLocations() {
        if (!listLocations.isEmpty())
            return listLocations;
        else {
            listLocations.add(new LatLng(0.0, 0.0));
            return listLocations;
        }
    }

    public GraphView.GraphViewData[] getAllGraphDataAccX() {
        if (!listAccX.isEmpty())
            return (GraphView.GraphViewData[]) listAccX.toArray(new GraphView.GraphViewData[listAccX.size()]);
        else {
            listAccX.add(new GraphView.GraphViewData(0.0, 0.0));
            return (GraphView.GraphViewData[]) listAccX.toArray(new GraphView.GraphViewData[listAccX.size()]);
        }
    }

    public GraphView.GraphViewData[] getAllGraphDataAccY() {
        if (!listAccY.isEmpty())
            return (GraphView.GraphViewData[]) listAccY.toArray(new GraphView.GraphViewData[listAccY.size()]);
        else {
            listAccY.add(new GraphView.GraphViewData(0.0, 0.0));
            return (GraphView.GraphViewData[]) listAccY.toArray(new GraphView.GraphViewData[listAccY.size()]);
        }
    }

    public GraphView.GraphViewData[] getAllGraphDataAccZ() {
        if (!listAccZ.isEmpty())
            return (GraphView.GraphViewData[]) listAccZ.toArray(new GraphView.GraphViewData[listAccZ.size()]);
        else {
            listAccZ.add(new GraphView.GraphViewData(0.0, 0.0));
            return (GraphView.GraphViewData[]) listAccZ.toArray(new GraphView.GraphViewData[listAccZ.size()]);
        }
    }

    public GraphView.GraphViewData[] getAllGraphDatarpm1() {
        if (!listrpm1.isEmpty())
            return (GraphView.GraphViewData[]) listrpm1.toArray(new GraphView.GraphViewData[listrpm1.size()]);
        else {
            listrpm1.add(new GraphView.GraphViewData(0.0, 0.0));
            return (GraphView.GraphViewData[]) listrpm1.toArray(new GraphView.GraphViewData[listrpm1.size()]);
        }
    }

    public GraphView.GraphViewData[] getAllGraphDatarpm2() {
        if (!listrpm2.isEmpty())
            return (GraphView.GraphViewData[]) listrpm2.toArray(new GraphView.GraphViewData[listrpm2.size()]);
        else {
            listrpm2.add(new GraphView.GraphViewData(0.0, 0.0));
            return (GraphView.GraphViewData[]) listrpm2.toArray(new GraphView.GraphViewData[listrpm2.size()]);
        }
    }

    public GraphView.GraphViewData[] getAllGraphDatarpm3() {
        if (!listrpm3.isEmpty())
            return (GraphView.GraphViewData[]) listrpm3.toArray(new GraphView.GraphViewData[listrpm3.size()]);
        else {
            listrpm3.add(new GraphView.GraphViewData(0.0, 0.0));
            return (GraphView.GraphViewData[]) listrpm3.toArray(new GraphView.GraphViewData[listrpm3.size()]);
        }
    }

    public GraphView.GraphViewData[] getAllGraphDatarpm4() {
        if (!listrpm4.isEmpty())
            return (GraphView.GraphViewData[]) listrpm4.toArray(new GraphView.GraphViewData[listrpm4.size()]);
        else {
            listrpm4.add(new GraphView.GraphViewData(0.0, 0.0));
            return (GraphView.GraphViewData[]) listrpm4.toArray(new GraphView.GraphViewData[listrpm4.size()]);
        }
    }

    public GraphView.GraphViewData[] getAllGraphDataAngleX() {
        if (!listAngleX.isEmpty())
            return (GraphView.GraphViewData[]) listAngleX.toArray(new GraphView.GraphViewData[listAngleX.size()]);
        else {
            listAngleX.add(new GraphView.GraphViewData(0.0, 0.0));
            return (GraphView.GraphViewData[]) listAngleX.toArray(new GraphView.GraphViewData[listAngleX.size()]);
        }
    }

    public GraphView.GraphViewData[] getAllGraphDataAngleY() {
        if (!listAngleY.isEmpty())
            return (GraphView.GraphViewData[]) listAngleY.toArray(new GraphView.GraphViewData[listAngleY.size()]);
        else {
            listAngleY.add(new GraphView.GraphViewData(0.0, 0.0));
            return (GraphView.GraphViewData[]) listAngleY.toArray(new GraphView.GraphViewData[listAngleY.size()]);
        }
    }

    public GraphView.GraphViewData[] getAllGraphDataAngleZ() {
        if (!listAngleZ.isEmpty())
            return (GraphView.GraphViewData[]) listAngleZ.toArray(new GraphView.GraphViewData[listAngleZ.size()]);
        else {
            listAngleZ.add(new GraphView.GraphViewData(0.0, 0.0));
            return (GraphView.GraphViewData[]) listAngleZ.toArray(new GraphView.GraphViewData[listAngleZ.size()]);
        }
    }



    // enables persistence storage


    public static void SetEnableSaving(boolean state) {
        enableSaving = state;
    }

}







