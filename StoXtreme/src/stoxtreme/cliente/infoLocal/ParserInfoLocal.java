package stoxtreme.cliente.infoLocal;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParserInfoLocal {
	
	public ParserInfoLocal(){
	}
	
	public Hashtable<String, Double> creaInfoLocal(String fichero) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Hashtable <String,Double>ht=new Hashtable<String,Double>();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(fichero));
			NodeList nl = document.getElementsByTagName("emp");
			String nombre=null;
			double cotiz=0;
			String info=null;
			for (int i=0; nl!=null && i<nl.getLength();i++){
				nombre=((Element)nl.item(i)).getAttribute("nombre");
				cotiz=new Double(((Element)nl.item(i)).getAttribute("cotizacion"));
				//le quitamos los /t y /n del final y del principio
				info=((Element)nl.item(i)).getTextContent().trim();
				ht.put(nombre,new Double(cotiz));
			}
	    } catch (SAXException sxe) {
	       // Error generated during parsing
	       Exception  x = sxe;
	       if (sxe.getException() != null)
	           x = sxe.getException();
	       x.printStackTrace();

	    } catch (ParserConfigurationException pce) {
	       // Parser with specified options can't be built
	       pce.printStackTrace();
	    } catch (IOException ioe) {
	       // I/O error
	       ioe.printStackTrace();
	    }
		return ht;
	}
	
	public DatoHistorico getDatoHistorico(String empresa, String fecha){
		String fichero=new String("./conf/cliente/"+empresa+".xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DatoHistorico dt= new DatoHistorico();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(fichero));
			NodeList nl = document.getElementsByTagName("fecha");
			for (int i=0; nl!=null && i<nl.getLength();i++){
				if(((Element)nl.item(i)).getAttribute("fecha").trim().equals(fecha)){
					dt.setEfectivo(new Double(((Element)nl.item(i)).getElementsByTagName("efectivo").item(0).getTextContent().toString()));
					dt.setEmpresa(empresa);
					dt.setFecha(fecha);
					dt.setPrecioCierre(new Double(((Element)nl.item(i)).getElementsByTagName("precio_cierre").item(0).getTextContent().toString()));
					dt.setPrecioInicio(new Double(((Element)nl.item(i)).getElementsByTagName("precio_inicio").item(0).getTextContent().toString()));
					dt.setPrecioMaximo(new Double(((Element)nl.item(i)).getElementsByTagName("precio_maximo").item(0).getTextContent().toString()));
					dt.setPrecioMedio(new Double(((Element)nl.item(i)).getElementsByTagName("precio_medio").item(0).getTextContent().toString()));
					dt.setPrecioMinimo(new Double(((Element)nl.item(i)).getElementsByTagName("precio_minimo").item(0).getTextContent().toString()));
					dt.setVolumen(new Integer(((Element)nl.item(i)).getElementsByTagName("volumen").item(0).getTextContent().toString()));
				}
			}
	    } catch (SAXException sxe) {
	       // Error generated during parsing
	       Exception  x = sxe;
	       if (sxe.getException() != null)
	           x = sxe.getException();
	       x.printStackTrace();

	    } catch (ParserConfigurationException pce) {
	       // Parser with specified options can't be built
	       pce.printStackTrace();
	    } catch (IOException ioe) {
	       // I/O error
	       ioe.printStackTrace();
	    }
		return dt;
	}
}
