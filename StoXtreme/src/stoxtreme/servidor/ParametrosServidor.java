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
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ParametrosServidor {

	private Hashtable<String, Object> parametros;
	/**
	 *  Description of the Field
	 */
	public static String FICH_EMP = "ficheroEmpresas";
	/**
	 *  Description of the Field
	 */
	public static String FICH_REG = "ficheroRegistrados";
	/**
	 *  Description of the Field
	 */
	public static String TICK = "tick";
	/**
	 *  Description of the Field
	 */
	public static String TIEMPO = "tiempo";
	/**
	 *  Description of the Field
	 */
	public static String NUMEMPRESAS = "numeroEmpresas";


	//Constructora
	/**
	 *  Constructor for the ParametrosServidor object
	 */
	public ParametrosServidor() {
		this.parametros = new Hashtable<String, Object>();
		this.leeFicheroParametros("conf/parametros.xml");
	}


	//Accesores de "parametros"
	/**
	 *  Gets the Tick attribute of the ParametrosServidor object
	 *
	 *@return    The Tick value
	 */
	public double getTick() {
		return ((Double) (this.parametros.get(TICK)));
	}


	/**
	 *  Gets the Tiempo attribute of the ParametrosServidor object
	 *
	 *@return    The Tiempo value
	 */
	public long getTiempo() {
		return ((Long) (this.parametros.get(TIEMPO)));
	}


	/**
	 *  Gets the FicheroEmpresas attribute of the ParametrosServidor object
	 *
	 *@return    The FicheroEmpresas value
	 */
	public String getFicheroEmpresas() {
		return this.parametros.get(FICH_EMP).toString();
	}


	/**
	 *  Gets the FicheroRegistrados attribute of the ParametrosServidor object
	 *
	 *@return    The FicheroRegistrados value
	 */
	public String getFicheroRegistrados() {
		return this.parametros.get(FICH_REG).toString();
	}


	/**
	 *  Gets the NumEmpresas attribute of the ParametrosServidor object
	 *
	 *@return    The NumEmpresas value
	 */
	public String getNumEmpresas() {
		return this.parametros.get(NUMEMPRESAS).toString();
	}

	//Modificador de la tabla hash "parametros"

	/**
	 *  Description of the Method
	 *
	 *@param  clave  Description of Parameter
	 *@param  valor  Description of Parameter
	 */
	public void modificarParams(String clave, Object valor) {
		parametros.put(clave, valor);
	}


	//Vuelca en "parametros" la informacion del fichero XML
	/**
	 *  Description of the Method
	 *
	 *@param  fichero  Description of Parameter
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
