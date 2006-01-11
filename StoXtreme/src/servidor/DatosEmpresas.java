package servidor;

import java.util.Hashtable;
/*
 * Fichero debe ser de la forma:
 * <empresas>
 * 		<emp nombre="NOMBRE" cotizacion=COTIZACION global=(TRUE|FALSE)>
 * 			FICHERO_CON_LOS_DATOS O NOMBRE DE LA TABLA
 * 		</emp>
 * 		.....
 * <empresas>
 */
public class DatosEmpresas {
	/**
	 * @uml.property  name="nombresEmpresas" multiplicity="(0 -1)" dimension="1"
	 */
	String[] nombresEmpresas;
	
	public DatosEmpresas(){
		// Inicialmente no tiene datos hasta que no se parsea el fichero
		nombresEmpresas = new String[0];
	}
	
	public Hashtable creaObjetosBolsa(String fichero) {
		
		return null;
	}
	/**
	 * @return  Returns the nombresEmpresas.
	 * @uml.property  name="nombresEmpresas"
	 */
	public String[] getNombresEmpresas(){
		return nombresEmpresas;
	}
}
