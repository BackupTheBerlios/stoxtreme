package stoxtreme.servidor;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
//import the JAXP APIs you'll be using
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.FactoryConfigurationError;  
import javax.xml.parsers.ParserConfigurationException;
//exceptions that can be thrown when the XML document is parsed
import org.xml.sax.SAXException;  
import org.xml.sax.SAXParseException;
//read the sample XML file and identify errors
import java.io.File;
import java.io.IOException;
//W3C definition for a DOM and DOM exceptions
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.DOMException;
/*
 * Fichero debe ser de la forma:
 * <empresas>
 * 		<emp nombre="NOMBRE" cotizacion=COTIZACION global=(TRUE|FALSE)>
 * 			FICHERO_CON_LOS_DATOS O NOMBRE DE LA TABLA
 * 		</emp>
 * 		.....
 * </empresas>
 */
public class DatosEmpresas {
	Vector nombresEmpresas;
	Hashtable ObjetosBolsa;
	
	public DatosEmpresas(){
		// Inicialmente no tiene datos hasta que no se parsea el fichero
		nombresEmpresas = new Vector();
		ObjetosBolsa=creaObjetosBolsa("conf/empresas.xml");
		Enumeration e=ObjetosBolsa.keys();
		while (e.hasMoreElements()){
			nombresEmpresas.addElement(e.nextElement());			
		}
	}
	
	public Hashtable creaObjetosBolsa(String fichero) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Hashtable ht=new Hashtable();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(fichero));
			Element ele = document.getDocumentElement();
			NodeList nl = ele.getElementsByTagName("emp");
			String nombre=null;
			Double cotiz=0.0;
			//Obtengo todas las empresas y creo un objeto bolsa para cada una
			for (int i=0; nl!=null && i<nl.getLength();i++){
				ele=(Element)nl.item(i);
				nombre=ele.getAttribute("nombre");
				cotiz=new Double(ele.getAttribute("cotizacion"));
				ht.put(nombre,new ObjetoBolsa(nombre,sEventos,eMensajes,VSistema));
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
	public Vector getNombresEmpresas(){
		return nombresEmpresas;
	}
	public static void main(String[] args){
		DatosEmpresas de =new DatosEmpresas();
	}
}
