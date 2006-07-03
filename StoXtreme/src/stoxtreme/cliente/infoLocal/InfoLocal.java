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
 *  Tipo de datos de una empresa
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
	 *  Constructor de InfoLocal
	 *
	 *@param  ruta         Ruta de ficheros
	 *@param  numEmpresas  Númeroi de empresas existentes 
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
	 *  Obtiene el precio inicial
	 *
	 *@param  empresa  Nombre de la empresa
	 *@return          Devuelve el precio
	 */
	public Double getPrecioInicial(String empresa) {
		datos = info.get(empresa);
		return Double.valueOf(datos.elementAt(0).toString());
	}


	/**
	 * Obtiene el CapitalSocial
	 *
	 *@param  empresa  Nombre empresa
	 *@return          Valor de CapitalSocial
	 */
	public Double getCapitalSocial(String empresa) {
		datos = info.get(empresa);
		return Double.valueOf(datos.elementAt(1).toString());
	}


	/**
	 *  Obtiene el ValorNominal
	 *
	 *@param  empresa  Nombre empresa
	 *@return          Valor de ValorNominal
	 */
	public Double getValorNominal(String empresa) {
		datos = info.get(empresa);
		return Double.valueOf(datos.elementAt(2).toString());
	}


	/**
	 *  Obtiene las AmpliacionesCapital
	 *
	 *@param  empresa  Nombre empresa
	 *@return          Valor de AmpliacionesCapital
	 */
	public int getAmpliacionesCapital(String empresa) {
		datos = info.get(empresa);
		return Integer.parseInt(datos.elementAt(3).toString());
	}


	/**
	 *  Obtiene la Descripcion
	 *
	 *@param  empresa  Nombre empresa
	 *@return          Valor de Descripcion
	 */
	public String getDescripcion(String empresa) {
		datos = info.get(empresa);
		return datos.elementAt(4).toString();
	}


	/**
	 *  Obtiene el Sector
	 *
	 *@param  empresa  Nombre empresa
	 *@return          Valor de Sector
	 */
	public String getSector(String empresa) {
		datos = info.get(empresa);
		return datos.elementAt(5).toString();
	}


	/**
	 *  Obtiene las Empresas 
	 *
	 *@return          Valor deEmpresas
	 */
	public ArrayList<String> getEmpresas() {
		return empresas;
	}


	/**
	 *  Obtiene la Instancia
	 *
	 *@return          Valor de Instance
	 */
	public static InfoLocal getInstance() {
		return _instance;
	}


	/**
	 *  Crea una instancia de la información local
	 *
	 *@param  ruta         Direccion de los ficheros
	 *@param  numEmpresas  Número de empresas disponibles
	 */
	public static void crearInstancia(String ruta, int numEmpresas) {
		if (_instance == null) {
			_instance = new InfoLocal(ruta, numEmpresas);
		}
	}
}
