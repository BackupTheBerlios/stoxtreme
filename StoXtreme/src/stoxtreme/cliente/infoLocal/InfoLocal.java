package stoxtreme.cliente.infoLocal;

/* Tabla hash indexada por nombre de empresa,
 	de momento contiene el precio al iniciar la sesion
*/
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.UIManager;

import stoxtreme.cliente.infoLocal.ParserInfoLocal;

public class InfoLocal {
	private static InfoLocal _instance = null;
	
	public static InfoLocal getInstance(){
		return _instance;
	}
	public static void crearInstancia(String ruta,int numEmpresas){
		if(_instance == null)
			_instance  = new InfoLocal(ruta,numEmpresas);
	}
	//Vector[0]:precio inicial de la accion
	//Vector[1]:capital social
	//Vector[2]:valor nominal
	//Vector[3]:num ampliaciones capital
	//Vector[4]:resumen
	private Hashtable<String,Vector> info;
	private ArrayList <String>empresas;
	private Vector datos;
	
	private InfoLocal(String ruta, int numEmpresas){
		ParserInfoLocal parser=new ParserInfoLocal();
		info=parser.creaInfoLocal(ruta,numEmpresas);
		empresas = new ArrayList<String>();
		Enumeration<String> es = info.keys();
		while(es.hasMoreElements()){
			empresas.add(es.nextElement());
		}
	}
	
	public Double getPrecioInicial(String empresa){
		datos =info.get(empresa);
		return Double.valueOf(datos.elementAt(0).toString());
	}
	public Double getCapitalSocial(String empresa){
		datos =info.get(empresa);
		return Double.valueOf(datos.elementAt(1).toString());
	}
	public Double getValorNominal(String empresa){
		datos =info.get(empresa);
		return Double.valueOf(datos.elementAt(2).toString());
	}
	public int getAmpliacionesCapital(String empresa){
		datos =info.get(empresa);
		return Integer.parseInt(datos.elementAt(3).toString());
	}
	public String getDescripcion(String empresa){
		datos =info.get(empresa);
		return datos.elementAt(4).toString();
	}
	
	public ArrayList<String> getEmpresas(){
		return empresas;
	}
}
