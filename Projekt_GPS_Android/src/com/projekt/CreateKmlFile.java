package com.projekt;

import java.io.File;

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

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;

 
public class CreateKmlFile {
	
	private static final String folderPath = "/goal_reports";
	private static final String KmlFilePath = "/kml_report.kml";
	private static final String extStoragePath = "/storage/extSdCard";
	
	private static boolean extStorage = false;
    
	
	public static void setExtStorage(boolean state){
		extStorage = state;
	}
	
	public static void CreateKML() {
 
        try {
            
        	//Erzeugung eines Dokuments
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
            
            // placemark
            Element placemark = doc.createElement("Placemark");
            docNode.appendChild(placemark);
            
            //	LineString
            Element linestring = doc.createElement("LineString");
            placemark.appendChild(linestring);
            
            // coordinates
            Element coordinates = doc.createElement("coordinates");
            coordinates.appendChild(doc.createTextNode("11.7510443037428,48.81697078023203"));
            coordinates.appendChild(doc.createTextNode(" "));
            coordinates.appendChild(doc.createTextNode("11.75105664991592,48.81699827178274"));
            linestring.appendChild(coordinates);
            
            // als KML schreiben
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            //Formatierung der KML Datei
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