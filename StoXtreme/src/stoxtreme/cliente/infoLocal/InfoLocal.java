package stoxtreme.cliente.infoLocal;

/* Tabla hash indexada por nombre de empresa,
 	de momento contiene el precio al iniciar la sesion
*/
import java.util.Hashtable;

import stoxtreme.cliente.infoLocal.ParserInfoLocal;

public class InfoLocal {
	
	private Hashtable<String,Double> info;
	private ParserInfoLocal parser;
	
	public InfoLocal(){
		parser=new ParserInfoLocal();
		info=parser.creaInfoLocal("./conf/cliente/empresas.xml");
	}
	
	public Double getPrecioInicial(String empresa){
		return info.get(empresa);
	}
}
