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
	static{
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	//Vector[0]:precio inicial de la accion
	//Vector[1]:capital social
	//Vector[2]:valor nominal
	//Vector[3]:capital social
	//Vector[4]:num ampliaciones capital
	//Vector[5]:resumen
	private Hashtable<String,Vector> info;
	private ArrayList <String>empresas;
	private int numEmpresas;
	private Vector datos;
	
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
		datos =info.get(empresa);
		return Double.valueOf(datos.elementAt(0).toString());
	}
	//TODO
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
		return Integer.parseInt(datos.elementAt(4).toString());
	}
	public String getDescripcion(String empresa){
		datos =info.get(empresa);
		return datos.elementAt(5).toString();
	}
	
	public ArrayList<String> getEmpresas(){
		return empresas;
	}
}
