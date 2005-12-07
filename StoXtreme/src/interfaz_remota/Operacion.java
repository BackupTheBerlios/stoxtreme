package interfaz_remota;
import java.util.Hashtable;

public class Operacion {
	String nombreEmpresa;
	int numAcciones;
	double precio;
	String tipoOp;
	
	public Operacion(String tipoOp, String nombreEmpresa, 
			int numAcciones, float precio){
		
	}
	
	public Operacion(Hashtable h){
		
	}
	
	public Hashtable toHashtable(){
		return null;
	}
}
