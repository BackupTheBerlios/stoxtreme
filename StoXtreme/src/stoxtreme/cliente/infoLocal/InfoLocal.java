package stoxtreme.cliente.infoLocal;

/*
 *  Tabla hash indexada por nombre de empresa,
 *  de momento contiene el precio al iniciar la sesion
 */
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.UIManager;

import stoxtreme.cliente.infoLocal.ParserInfoLocal;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class InfoLocal {
	//Vector[0]:precio inicial de la accion
	//Vector[1]:capital social
	//Vector[2]:valor nominal
	//Vector[3]:num ampliaciones capital
	//Vector[4]:resumen
	//Vector[5]:sector
	private Hashtable<String, Vector> info;
	private ArrayList<String> empresas;
	private Vector datos;
	private static InfoLocal _instance = null;


	/**
	 *  Constructor for the InfoLocal object
	 *
	 *@param  ruta         Description of Parameter
	 *@param  numEmpresas  Description of Parameter
	 */
	private InfoLocal(String ruta, int numEmpresas) {
		ParserInfoLocal parser = new ParserInfoLocal();
		info = parser.creaInfoLocal(ruta, numEmpresas);
		empresas = new ArrayList<String>();
		Enumeration<String> es = info.keys();
		while (es.hasMoreElements()) {
			empresas.add(es.nextElement());
		}
	}


	/**
	 *  Gets the PrecioInicial attribute of the InfoLocal object
	 *
	 *@param  empresa  Description of Parameter
	 *@return          The PrecioInicial value
	 */
	public Double getPrecioInicial(String empresa) {
		datos = info.get(empresa);
		return Double.valueOf(datos.elementAt(0).toString());
	}


	/**
	 *  Gets the CapitalSocial attribute of the InfoLocal object
	 *
	 *@param  empresa  Description of Parameter
	 *@return          The CapitalSocial value
	 */
	public Double getCapitalSocial(String empresa) {
		datos = info.get(empresa);
		return Double.valueOf(datos.elementAt(1).toString());
	}


	/**
	 *  Gets the ValorNominal attribute of the InfoLocal object
	 *
	 *@param  empresa  Description of Parameter
	 *@return          The ValorNominal value
	 */
	public Double getValorNominal(String empresa) {
		datos = info.get(empresa);
		return Double.valueOf(datos.elementAt(2).toString());
	}


	/**
	 *  Gets the AmpliacionesCapital attribute of the InfoLocal object
	 *
	 *@param  empresa  Description of Parameter
	 *@return          The AmpliacionesCapital value
	 */
	public int getAmpliacionesCapital(String empresa) {
		datos = info.get(empresa);
		return Integer.parseInt(datos.elementAt(3).toString());
	}


	/**
	 *  Gets the Descripcion attribute of the InfoLocal object
	 *
	 *@param  empresa  Description of Parameter
	 *@return          The Descripcion value
	 */
	public String getDescripcion(String empresa) {
		datos = info.get(empresa);
		return datos.elementAt(4).toString();
	}


	/**
	 *  Gets the Sector attribute of the InfoLocal object
	 *
	 *@param  empresa  Description of Parameter
	 *@return          The Sector value
	 */
	public String getSector(String empresa) {
		datos = info.get(empresa);
		return datos.elementAt(5).toString();
	}


	/**
	 *  Gets the Empresas attribute of the InfoLocal object
	 *
	 *@return    The Empresas value
	 */
	public ArrayList<String> getEmpresas() {
		return empresas;
	}


	/**
	 *  Gets the Instance attribute of the InfoLocal class
	 *
	 *@return    The Instance value
	 */
	public static InfoLocal getInstance() {
		return _instance;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  ruta         Description of Parameter
	 *@param  numEmpresas  Description of Parameter
	 */
	public static void crearInstancia(String ruta, int numEmpresas) {
		if (_instance == null) {
			_instance = new InfoLocal(ruta, numEmpresas);
		}
	}
}
