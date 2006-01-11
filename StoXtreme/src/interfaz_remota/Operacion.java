package interfaz_remota;
import java.lang.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Operacion {
	public static final String COMPRA="COMPRA";
	public static final String VENTA="VENTA";
	/**
	 * @uml.property  name="nombreEmpresa"
	 */
	private String nombreEmpresa;
	/**
	 * @uml.property  name="numAcciones"
	 */
	private int numAcciones;
	/**
	 * @uml.property  name="precio"
	 */
	private float precio;
	/**
	 * @uml.property  name="tipoOp"
	 */
	private String tipoOp;
	
	public Operacion(){
		
	}
	public Operacion(String tipoOp, String nombreEmpresa, 
			int numAcciones, float precio){
		this.setNombreEmpresa(nombreEmpresa);
		this.setNumAcciones(numAcciones);
		this.setPrecio(precio);
		this.setTipoOp(tipoOp);
	}
	
	public Operacion (Hashtable h){
		Enumeration contenido = h.elements();
		setNombreEmpresa((String)contenido.nextElement());
		setNumAcciones(Integer.parseInt(contenido.nextElement().toString()));
		setPrecio(new Float(contenido.nextElement().toString()).floatValue());
		setTipoOp((String)contenido.nextElement());
	}
	
	public Hashtable toHashtable(){
		Hashtable h= new Hashtable();
		h.put("nombreEmpresa", this.getNombreEmpresa());
		h.put("numAcciones",new Integer(this.getNumAcciones()).toString());
		h.put("precio",new Float (this.getPrecio()).toString());
		h.put("tipoOp",this.getTipoOp());
		return h;
	}
	/**
	 * @param tipoOp  The tipoOp to set.
	 * @uml.property  name="tipoOp"
	 */
	public void setTipoOp(String tipoOp) {
		this.tipoOp = tipoOp;
	}
	/**
	 * @return  Returns the tipoOp.
	 * @uml.property  name="tipoOp"
	 */
	public String getTipoOp() {
		return tipoOp;
	}
	/**
	 * @param numAcciones  The numAcciones to set.
	 * @uml.property  name="numAcciones"
	 */
	public void setNumAcciones(int numAcciones) {
		this.numAcciones = numAcciones;
	}
	/**
	 * @return  Returns the numAcciones.
	 * @uml.property  name="numAcciones"
	 */
	public int getNumAcciones() {
		return numAcciones;
	}
	/**
	 * @param nombreEmpresa  The nombreEmpresa to set.
	 * @uml.property  name="nombreEmpresa"
	 */
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	/**
	 * @return  Returns the nombreEmpresa.
	 * @uml.property  name="nombreEmpresa"
	 */
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	/**
	 * @param precio  The precio to set.
	 * @uml.property  name="precio"
	 */
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	/**
	 * @return  Returns the precio.
	 * @uml.property  name="precio"
	 */
	public float getPrecio() {
		return precio;
	}

}
