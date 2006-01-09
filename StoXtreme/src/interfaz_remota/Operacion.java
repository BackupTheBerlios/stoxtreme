package interfaz_remota;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import java.lang.*;

public class Operacion {
	String nombreEmpresa;
	int numAcciones;
	float precio;
	String tipoOp;
	
	public Operacion(){
		
	}
	public Operacion(String tipoOp, String nombreEmpresa, 
			int numAcciones, float precio){
		this.nombreEmpresa=nombreEmpresa;
		this.numAcciones=numAcciones;
		this.precio=precio;
		this.tipoOp=tipoOp;
	}
	
	public Operacion (Hashtable h){
		Enumeration contenido = h.elements();
		nombreEmpresa=(String)contenido.nextElement();
		numAcciones=Integer.parseInt(contenido.nextElement().toString());
		precio=new Float(contenido.nextElement().toString()).floatValue();
		tipoOp=(String)contenido.nextElement();
	}
	
	public Hashtable toHashtable(){
		Hashtable h= new Hashtable();
		return h;
	}
}
