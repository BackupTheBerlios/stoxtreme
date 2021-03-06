package stoxtreme.servidor;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
//import the JAXP APIs you'll be using
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//exceptions that can be thrown when the XML document is parsed
import org.xml.sax.SAXException;
//read the sample XML file and identify errors
import java.io.File;
import java.io.IOException;
//W3C definition for a DOM and DOM exceptions
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import stoxtreme.servidor.objeto_bolsa.ObjetoBolsa;
/*
 *  Fichero debe ser de la forma:
 *  <empresas>
 *  <emp nombre="NOMBRE" cotizacion=COTIZACION global=(TRUE|FALSE)>
 *  FICHERO_CON_LOS_DATOS O NOMBRE DE LA TABLA
 *  </emp>
 *  .....
 *  </empresas>
 */
/**
 *  Parser que inicializa los ObjetoBolsa con los datos de empresas.xml
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public class DatosEmpresas {
	ArrayList<String> nombresEmpresas;


	/**
	 *  Constructor del objeto DatosEmpresas
	 */
	public DatosEmpresas() {
		// Inicialmente no tiene datos hasta que no se parsea el fichero
		nombresEmpresas = new ArrayList<String>();
	}


	/**
	 *  Accesor del atributo NombresEmpresas
	 *
	 *@return    El valor de NombresEmpresas
	 */
	public ArrayList<String> getNombresEmpresas() {
		return nombresEmpresas;
	}


	/**
	 *  Crea los ObjetosBolsa con sus datos iniciales (nombre de empresa, cotizaci�n de t�tulo, n�mero de acciones)
	 *
	 *@param  parServ  Los par�metros del servidor
	 *@return          Una tabla hash de ObjetoBolsa, con clave nombre de la empresa
	 */
	public Hashtable<String, ObjetoBolsa> creaObjetosBolsa(ParametrosServidor parServ) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Hashtable<String, ObjetoBolsa> ht = new Hashtable<String, ObjetoBolsa>();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(parServ.getFicheroEmpresas()));
			NodeList nl = document.getElementsByTagName("emp");
			String nombre = null;
			double cotiz = 0;
			String info = null;
			//Obtengo todas las empresas y creo un objeto bolsa para cada una
			for (int i = 0; nl != null && i < nl.getLength() && i < Integer.parseInt(parServ.getNumEmpresas()); i++) {
				nombre = ((Element) nl.item(i)).getAttribute("nombre");
				cotiz = new Double(((Element) nl.item(i)).getAttribute("cotizacion"));
				//le quitamos los /t y /n del final y del principio
				info = ((Element) nl.item(i)).getTextContent().trim();
				//el numero de acciones inicial se obtiene de infoBursatil
				ht.put(nombre, new ObjetoBolsa(nombre, cotiz, info));
			}
		}
		catch (SAXException sxe) {
			// Error generated during parsing
			Exception x = sxe;
			if (sxe.getException() != null) {
				x = sxe.getException();
			}
			x.printStackTrace();

		}
		catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			pce.printStackTrace();
		}
		catch (IOException ioe) {
			// I/O error
			ioe.printStackTrace();
		}
		int i = 0;
		Enumeration e = ht.keys();
		while (e.hasMoreElements() && i < (Integer.parseInt(parServ.getNumEmpresas()))) {
			nombresEmpresas.add(i, e.nextElement().toString());
			i++;
		}
		return ht;
	}

}
