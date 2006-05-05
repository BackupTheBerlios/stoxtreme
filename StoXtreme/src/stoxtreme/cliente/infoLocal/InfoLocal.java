package stoxtreme.cliente.infoLocal;

/* Tabla hash indexada por nombre de empresa,
 	de momento contiene el precio al iniciar la sesion
*/
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import stoxtreme.cliente.infoLocal.ParserInfoLocal;

public class InfoLocal {
	private Hashtable<String,Double> info;
	private ArrayList <String>empresas;
	
	public InfoLocal(){
		ParserInfoLocal parser=new ParserInfoLocal();
		info=parser.creaInfoLocal("./conf/cliente/empresas.xml");
		
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
