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

/**
 * class for creating KML-reports
 *
 * @author Aschenauer Dominic
 */
public class KMLReport extends Report {

	/**
	 * method to transform GPS coordinates to a string
	 * @param gpsCoo
	 * @return
	 */
	private static String transformCoordinatestoString(List<LatLng> gpsCoo) {
		String result = "\n";
		if (!gpsCoo.isEmpty() && gpsCoo!= null) { // != null wurde noch hinzugefügt 3 mal
			for (LatLng LtLg : gpsCoo) {
				result = result + LtLg.longitude + "," + LtLg.latitude + "\n";
			}
			return result;
		} else {
			return "";
		}
	}

	/**
	 * method to getting the start-coordinates of the gps-route
	 * @param gpsCoo
	 * @return
	 */
	private static String getStartingCoordinates(List<LatLng> gpsCoo) {
		if (!gpsCoo.isEmpty()&& gpsCoo!= null) {
			return "" + gpsCoo.get(0).longitude + "," + gpsCoo.get(0).latitude;
		} else {
			return "";
		}
	}

	/**
	 * method to getting the end-coordinates of the gps-route
	 * @param gpsCoo
	 * @return
	 */
	private static String getEndCoordinates(List<LatLng> gpsCoo) {
		if (!gpsCoo.isEmpty()&& gpsCoo!= null) {
			return "" + gpsCoo.get(gpsCoo.size() - 1).longitude + ","
					+ gpsCoo.get(gpsCoo.size() - 1).latitude;
		} else {
			return "";
		}
	}

	/**
	 * method to create the KML-file
	 * @param gpsCoo
	 * @param gpsCooKal
	 */
	public static void createKML(List<LatLng> gpsCoo, List<LatLng> gpsCooKal) {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("kml");
			doc.appendChild(rootElement);

			// document node
			Element docNode = doc.createElement("Document");
			rootElement.appendChild(docNode);

			// name
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode("GPS"));
			docNode.appendChild(name);

			// description
			Element description = doc.createElement("description");
			description.appendChild(doc
					.createTextNode("GPS Koordinaten ohne Kalmanfilter"));
			docNode.appendChild(description);

			// style
			Element style = doc.createElement("Style");
			style.setAttribute("id", "red");
			docNode.appendChild(style);

			// lineStyle
			Element lineStyle = doc.createElement("LineStyle");
			style.appendChild(lineStyle);

			// color
			Element color = doc.createElement("color");
			color.appendChild(doc.createTextNode("FF1400E6"));
			lineStyle.appendChild(color);

			// width
			Element width = doc.createElement("width");
			width.appendChild(doc.createTextNode("3"));
			lineStyle.appendChild(width);

			// placemark for starting point
			Element placemark1 = doc.createElement("Placemark");
			docNode.appendChild(placemark1);

			// name
			Element nameStart = doc.createElement("name");
			nameStart.appendChild(doc.createTextNode("starting point"));
			placemark1.appendChild(nameStart);

			// styleUr
			Element styleUrl1 = doc.createElement("styleUrl");
			styleUrl1.appendChild(doc.createTextNode("#UrlStartPoint"));
			placemark1.appendChild(styleUrl1);

			// starting point
			Element startPoint = doc.createElement("Point");
			placemark1.appendChild(startPoint);

			// coordinates for starting point
			Element coordinates1 = doc.createElement("coordinates");
			coordinates1.appendChild(doc.createTextNode(KMLReport
					.getStartingCoordinates(gpsCoo)));
			startPoint.appendChild(coordinates1);

			// placemark for endpoint
			Element placemark2 = doc.createElement("Placemark");
			docNode.appendChild(placemark2);

			// name
			Element nameEnd = doc.createElement("name");
			nameEnd.appendChild(doc.createTextNode("endpoint"));
			placemark2.appendChild(nameEnd);

			// styleUr
			Element styleUrl2 = doc.createElement("styleUrl");
			styleUrl2.appendChild(doc.createTextNode("#UrlEndPoint"));
			placemark2.appendChild(styleUrl2);

			// endpoint
			Element endpoint = doc.createElement("Point");
			placemark2.appendChild(endpoint);

			// coordinates for endpoint
			Element coordinates2 = doc.createElement("coordinates");
			coordinates2.appendChild(doc.createTextNode(KMLReport
					.getEndCoordinates(gpsCoo)));
			endpoint.appendChild(coordinates2);

			// placemark for lineString
			Element placemark3 = doc.createElement("Placemark");
			docNode.appendChild(placemark3);

			// styleUr
			Element styleUrl = doc.createElement("styleUrl");
			styleUrl.appendChild(doc.createTextNode("#red"));
			placemark3.appendChild(styleUrl);

			// LineString
			Element linestring = doc.createElement("LineString");
			placemark3.appendChild(linestring);

			// coordinates for route
			Element coordinates3 = doc.createElement("coordinates");
			coordinates3.appendChild(doc.createTextNode(KMLReport
					.transformCoordinatestoString(gpsCoo)));
			linestring.appendChild(coordinates3);

			// Kalman GPS
			// name
			Element nameKal = doc.createElement("name");
			nameKal.appendChild(doc.createTextNode("GPS Kalmann"));
			docNode.appendChild(nameKal);

			// description
			Element descriptionKal = doc.createElement("description");
			descriptionKal.appendChild(doc
					.createTextNode("GPS Koordinaten mit Kalmanfilter"));
			docNode.appendChild(descriptionKal);

			// style
			Element styleKal = doc.createElement("Style");
			styleKal.setAttribute("id", "green");
			docNode.appendChild(styleKal);

			// lineStyle
			Element lineStyleKal = doc.createElement("LineStyle");
			styleKal.appendChild(lineStyleKal);

			// color
			Element colorKal = doc.createElement("color");
			colorKal.appendChild(doc.createTextNode("ff00ff00"));
			lineStyleKal.appendChild(colorKal);

			// width
			Element widthKal = doc.createElement("width");
			widthKal.appendChild(doc.createTextNode("3"));
			lineStyleKal.appendChild(widthKal);

			// placemark for starting point
			Element placemark1Kal = doc.createElement("Placemark");
			docNode.appendChild(placemark1Kal);

			// name
			Element nameStartKal = doc.createElement("name");
			nameStartKal.appendChild(doc.createTextNode("starting point"));
			placemark1Kal.appendChild(nameStartKal);

			// styleUr
			Element styleUrl1Kal = doc.createElement("styleUrl");
			styleUrl1Kal.appendChild(doc.createTextNode("#UrlStartPoint"));
			placemark1Kal.appendChild(styleUrl1Kal);

			// starting point
			Element startPointKal = doc.createElement("Point");
			placemark1Kal.appendChild(startPointKal);

			// coordinates for starting point
			Element coordinates1Kal = doc.createElement("coordinates");
			coordinates1Kal.appendChild(doc.createTextNode(KMLReport
					.getStartingCoordinates(gpsCooKal)));
			startPointKal.appendChild(coordinates1Kal);

			// placemark for endpoint
			Element placemark2Kal = doc.createElement("Placemark");
			docNode.appendChild(placemark2Kal);

			// name
			Element nameEndKal = doc.createElement("name");
			nameEndKal.appendChild(doc.createTextNode("endpoint"));
			placemark2Kal.appendChild(nameEndKal);

			// styleUr
			Element styleUrl2Kal = doc.createElement("styleUrl");
			styleUrl2Kal.appendChild(doc.createTextNode("#UrlEndPoint"));
			placemark2Kal.appendChild(styleUrl2Kal);

			// endpoint
			Element endpointKal = doc.createElement("Point");
			placemark2Kal.appendChild(endpointKal);

			// coordinates for endpoint
			Element coordinates2Kal = doc.createElement("coordinates");
			coordinates2Kal.appendChild(doc.createTextNode(KMLReport
					.getEndCoordinates(gpsCooKal)));
			endpointKal.appendChild(coordinates2Kal);

			// placemark for lineString
			Element placemark3Kal = doc.createElement("Placemark");
			docNode.appendChild(placemark3Kal);

			// styleUr
			Element styleUrlKal = doc.createElement("styleUrl");
			styleUrlKal.appendChild(doc.createTextNode("#green"));
			placemark3Kal.appendChild(styleUrlKal);

			// LineString
			Element linestringKal = doc.createElement("LineString");
			placemark3Kal.appendChild(linestringKal);

			// coordinates for route
			Element coordinates3Kal = doc.createElement("coordinates");
			coordinates3Kal.appendChild(doc.createTextNode(KMLReport
					.transformCoordinatestoString(gpsCooKal)));
			linestringKal.appendChild(coordinates3Kal);

			// transform to xml
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(
					OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "4");
			DOMSource source = new DOMSource(doc);

			// create folder
			// save KML-file
			if (!extStorageState) {
				File csvFolderInt = new File(
						Environment.getExternalStorageDirectory() + folderPath);
				if (!csvFolderInt.exists()) {
					csvFolderInt.mkdir();
				}
				transformer.transform(source, new StreamResult(new File(
						Environment.getExternalStorageDirectory() + folderPath
								+ kmlFilePath + getDate() + kmlFileEnding)));
			} else {
				File csvFolderInt = new File(extStoragePath + folderPath);
				if (!csvFolderInt.exists()) {
					csvFolderInt.mkdir();
				}
				transformer.transform(source, new StreamResult(new File(
						extStoragePath + folderPath + kmlFilePath + getDate()
								+ kmlFileEnding)));
			}

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
