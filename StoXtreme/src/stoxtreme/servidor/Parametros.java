package stoxtreme.servidor;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parametros {
	private double tick;
	private long tiempo;
	private String ficheroEmpresas;
	private String ficheroRegistrados;
	
	public Parametros(){
		
	}
	
	public void setTick(double tick){
		this.tick = tick;
	}
	
	public double getTick(){
		return tick;
	}
	
	public void setTiempo(long tiempo){
		this.tiempo = tiempo;
	}
	
	public long getTiempo(){
		return tiempo;
	}

	public void setFicheroEmpresas(String s){
		ficheroEmpresas = s;
	}

	public String getFicheroEmpresas() {
		return ficheroEmpresas;
	}
	
	public void setFicheroRegistrados(String s){
		ficheroRegistrados = s;
	}

	public String getFicheroRegistrados() {
		return ficheroRegistrados;
	}
	
	public static Parametros leeFicheroParametros(String fichero) {
		DocumentBuilderFactory factory;
		Document document;
		factory = DocumentBuilderFactory.newInstance();
		Parametros param=new Parametros();
		try {
			document = factory.newDocumentBuilder().parse(new File(fichero));
			NodeList nl = document.getElementsByTagName("tick");
			param.setTick(new Double(((Element)nl.item(0)).getTextContent().trim()));
			nl = document.getElementsByTagName("tiempo");
			param.setTiempo(new Long(((Element)nl.item(0)).getTextContent().trim()));
			nl = document.getElementsByTagName("fichero_emp");
			param.setFicheroEmpresas(((Element)nl.item(0)).getTextContent().trim());
			nl = document.getElementsByTagName("fichero_reg");
			param.setFicheroRegistrados(((Element)nl.item(0)).getTextContent().trim());
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
	    return param;
	}
}
