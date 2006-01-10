package servidor;

import java.util.Hashtable;
/*
 * Fichero debe ser de la forma:
 * <empresas>
 * 		<emp nombre="NOMBRE" cotizacion=COTIZACION global=(TRUE|FALSE)>
 * 			FICHERO_CON_LOS_DATOS
 * 		</emp>
 * 		.....
 * <empresas>
 */
public class DatosEmpresas {
	String[] nombresEmpresas;
	
	public DatosEmpresas(){
		// Inicialmente no tiene datos hasta que no se parsea el fichero
		nombresEmpresas = new String[0];
	}
	
	public Hashtable creaObjetosBolsa(String fichero) {
		
		return null;
	}
	public String[] getNombresEmpresas(){
		return nombresEmpresas;
	}
}
