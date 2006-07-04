package stoxtreme.servidor;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *  Guarda los parámetros de la simulación actual
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ParametrosServidor {

	private Hashtable<String, Object> parametros;
	/**
	 *  Fichero con datos generales de las empresas que pueden cotizar
	 */
	public static String FICH_EMP = "ficheroEmpresas";
	/**
	 *  Fichero con los datos de los usuarios ya registrados
	 */
	public static String FICH_REG = "ficheroRegistrados";
	/**
	 *  Máxima variación de un paso a otro en el precio de una acción
	 */
	public static String TICK = "tick";
	/**
	 *  Indica cada cuanto tiempo se producce un paso
	 */
	public static String TIEMPO = "tiempo";
	/**
	 *  Número de empresas que cotizan en la sesión actual
	 */
	public static String NUMEMPRESAS = "numeroEmpresas";


	/**
	 *  Constructor del objeto ParametrosServidor
	 */
	public ParametrosServidor() {
		this.parametros = new Hashtable<String, Object>();
		this.leeFicheroParametros("conf/parametros.xml");
	}


	/**
	 *  Accesor del atributo Tick
	 *
	 *@return    El valor del Tick
	 */
	public double getTick() {
		return ((Double) (this.parametros.get(TICK)));
	}


	/**
	 *  Accesor del atributo Tiempo
	 *
	 *@return    El valor del Tiempo
	 */
	public long getTiempo() {
		return ((Long) (this.parametros.get(TIEMPO)));
	}


	/**
	 *  Accesor del atributo FicheroEmpresas
	 *
	 *@return   El valor de FicheroEmpresas
	 */
	public String getFicheroEmpresas() {
		return this.parametros.get(FICH_EMP).toString();
	}


	/**
	 *  Accesor del atributo FicheroRegistrados
	 *
	 *@return    El valor de FicheroRegistrados
	 */
	public String getFicheroRegistrados() {
		return this.parametros.get(FICH_REG).toString();
	}


	/**
	 *  Accesor del atributo NumEmpresas
	 *
	 *@return   El valor de NumEmpresas
	 */
	public String getNumEmpresas() {
		return this.parametros.get(NUMEMPRESAS).toString();
	}

	/**
	 *  Modificador de parámetros
	 *
	 *@param  clave  Nombre del parámetro
	 *@param  valor  Nuevo valor del parámetro
	 */
	public void modificarParams(String clave, Object valor) {
		parametros.put(clave, valor);
	}


	/**
	 *  Parser que carga la información del fichero parametros.xml en un objeto de esta clase
	 *
	 *@param  fichero  Ruta del fichero parametros.xml
	 */
	public void leeFicheroParametros(String fichero) {
		DocumentBuilderFactory factory;
		Document document;
		factory = DocumentBuilderFactory.newInstance();
		try {
			document = factory.newDocumentBuilder().parse(new File(fichero));
			NodeList nl = document.getElementsByTagName("tick");
			this.modificarParams(TICK, new Double(((Element) nl.item(0)).getTextContent().trim()));
			nl = document.getElementsByTagName("tiempo");
			this.modificarParams(TIEMPO, new Long(((Element) nl.item(0)).getTextContent().trim()));
			nl = document.getElementsByTagName("fichero_emp");
			this.modificarParams(FICH_EMP, ((Element) nl.item(0)).getTextContent().trim());
			nl = document.getElementsByTagName("fichero_reg");
			this.modificarParams(FICH_REG, ((Element) nl.item(0)).getTextContent().trim());
			nl = document.getElementsByTagName("num_empresas");
			this.modificarParams(NUMEMPRESAS, ((Element) nl.item(0)).getTextContent().trim());
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
	}
}
