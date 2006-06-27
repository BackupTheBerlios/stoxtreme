package stoxtreme.cliente.infoLocal;

/* Tabla hash indexada por nombre de empresa,
 	de momento contiene el precio al iniciar la sesion
*/
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

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
	static{
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	private Hashtable<String,Double> info;
	private ArrayList <String>empresas;
	private int numEmpresas;
	
	private InfoLocal(String ruta, int numEmpresas){
		this.numEmpresas=numEmpresas;
		ParserInfoLocal parser=new ParserInfoLocal();
		info=parser.creaInfoLocal(ruta,numEmpresas);
		empresas = new ArrayList<String>();
		Enumeration<String> es = info.keys();
		
		
		while(es.hasMoreElements()){
			empresas.add(es.nextElement());
		}
	}
	
	public Double getPrecioInicial(String empresa){
		return info.get(empresa);
	}
	
	public ArrayList<String> getEmpresas(){
		return empresas;
	}
}
