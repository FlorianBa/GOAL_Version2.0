package com.projekt;


import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.os.Environment;

import com.google.android.gms.maps.model.LatLng;
import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
 
public class KML {
	
	private static final String folderPath = "/goal_reports";
	private static final String KmlFilePath = "/kml_report.kml";
	private static final String extStoragePath = "/storage/extSdCard";
	
	private static boolean extStorage = false;
    
	
	public static void setExtStorage(boolean state){
		extStorage = state;
	}
	
	private static String transformCoordinatestoString(List<LatLng> gpsCoo){
		String result = "\n";
		if(!gpsCoo.isEmpty()){
			for(LatLng LtLg: gpsCoo){
				result = result + LtLg.latitude + "," + LtLg.longitude + "\n";
			}
			return result;
		}else{
			return "";
		}
	}
	
	private static String getStartingCoordinates(List<LatLng> gpsCoo){
		if(!gpsCoo.isEmpty()){
			return "" + gpsCoo.get(0).latitude + "," + gpsCoo.get(0).longitude;
		}else{
			return "";
		}
	}
	
	private static String getEndCoordinates(List<LatLng> gpsCoo){
		if(!gpsCoo.isEmpty()){
			return "" + gpsCoo.get(gpsCoo.size() - 1).latitude + "," + gpsCoo.get(gpsCoo.size() - 1).longitude;
		}else{
			return "";
		}
	}

    public static void createKML(List<LatLng> gpsCoo) {

    	try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
       
 
            Element rootElement = doc.createElement("kml");
            doc.appendChild(rootElement);
            
            Element docNode = doc.createElement("Document");
            rootElement.appendChild(docNode);
 
            // name 
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode("Name123"));
            docNode.appendChild(name);
 
            // description
            Element description = doc.createElement("description");
            description.appendChild(doc.createTextNode("Beschreibung123"));
            docNode.appendChild(description);
            
            //style
            Element style = doc.createElement("Style");
            style.setAttribute("id", "red");
            docNode.appendChild(style);
            
            //lineStyle
            Element lineStyle = doc.createElement("LineStyle");
            style.appendChild(lineStyle);
            
            //color
            Element color = doc.createElement("color");
            color.appendChild(doc.createTextNode("FF1400E6"));
            lineStyle.appendChild(color);
            
            //width
            Element width = doc.createElement("width");
            width.appendChild(doc.createTextNode("3"));
            lineStyle.appendChild(width);
            
            // placemark for starting point
            Element placemark1 = doc.createElement("Placemark");
            docNode.appendChild(placemark1);
            
            //name 
            Element nameStart = doc.createElement("name");
            nameStart.appendChild(doc.createTextNode("starting point"));
            placemark1.appendChild(nameStart);
            
            //styleUr
            Element styleUrl1 = doc.createElement("styleUrl");
            styleUrl1.appendChild(doc.createTextNode("#UrlStartPoint"));
            placemark1.appendChild(styleUrl1);
            
            //starting point
            Element startPoint = doc.createElement("Point");
            placemark1.appendChild(startPoint);
            
            //coordinates for starting point
            Element coordinates1 = doc.createElement("coordinates");
			coordinates1.appendChild(doc.createTextNode(KML.getStartingCoordinates(gpsCoo)));
			startPoint.appendChild(coordinates1);
			
			//placemark for endpoint
            Element placemark2 = doc.createElement("Placemark");
            docNode.appendChild(placemark2);
            
            //name 
            Element nameEnd = doc.createElement("name");
            nameEnd.appendChild(doc.createTextNode("endpoint"));
            placemark2.appendChild(nameEnd);
            
            //styleUr
            Element styleUrl2 = doc.createElement("styleUrl");
            styleUrl2.appendChild(doc.createTextNode("#UrlEndPoint"));
            placemark2.appendChild(styleUrl2);
            
            //endpoint
            Element endpoint = doc.createElement("Point");
            placemark2.appendChild(endpoint);
            
            //coordinates for endpoint
            Element coordinates2 = doc.createElement("coordinates");
			coordinates2.appendChild(doc.createTextNode(KML.getEndCoordinates(gpsCoo)));
			endpoint.appendChild(coordinates2);
            
            // placemark for lineString
            Element placemark3 = doc.createElement("Placemark");
            docNode.appendChild(placemark3);
            
            //styleUr
            Element styleUrl = doc.createElement("styleUrl");
            styleUrl.appendChild(doc.createTextNode("#red"));
            placemark3.appendChild(styleUrl);
            
            //	LineString
            Element linestring = doc.createElement("LineString");
            placemark3.appendChild(linestring);
            
            // coordinates for route
            Element coordinates3 = doc.createElement("coordinates");
			coordinates3.appendChild(doc.createTextNode(KML.transformCoordinatestoString(gpsCoo)));
            linestring.appendChild(coordinates3);
            
            // transform to xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT,"4");
            DOMSource source = new DOMSource(doc);
            
            if(!extStorage){
    			File csvFolderInt = new File(Environment.getExternalStorageDirectory() + folderPath);
    			if(!csvFolderInt.exists()){
    				csvFolderInt.mkdir();	
    			}
    			transformer.transform(source, new StreamResult(new File(Environment.getExternalStorageDirectory() + folderPath + KmlFilePath)));
    		}else{
    			File csvFolderInt = new File(extStoragePath + folderPath);
    			if(!csvFolderInt.exists()){
    				csvFolderInt.mkdir();	
    			}
    			transformer.transform(source, new StreamResult(new File(extStoragePath + folderPath + KmlFilePath)));
    		}
                
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}