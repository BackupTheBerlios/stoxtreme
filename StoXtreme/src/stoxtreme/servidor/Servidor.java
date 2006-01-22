package stoxtreme.servidor;
import java.util.*;
import servidor.eventos.*;
import stoxtreme.servidor.eventos.SistemaEventos;
import stoxtreme.servidor.gestion_usuarios.GestionUsuarios;

public class Servidor {
	/**
	 * @uml.property  name="empresas"
	 */
	Hashtable empresas;
	/**
	 * @uml.property  name="reloj"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Reloj reloj;
	/**
	 * @uml.property  name="variables"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	VariablesSistema variables;
	/**
	 * @uml.property  name="eventos"
	 * @uml.associationEnd  readOnly="true"
	 */
	SistemaEventos eventos;
	/**
	 * @uml.property  name="gestorUsuarios"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	GestionUsuarios gestorUsuarios;
	
	public Servidor(Parametros p) throws Exception{
		// Necesita los daos de las empresas de un objeto informacion
		DatosEmpresas dEmps = new DatosEmpresas();
		empresas = dEmps.creaObjetosBolsa(p.getFicheroEmpresas());
				
		variables = new VariablesSistema(p);
		reloj = new Reloj(p.getTiempo());
		reloj.addListener(variables);
		gestorUsuarios = new GestionUsuarios();
		gestorUsuarios.leeDatos();
	}
	
	public void init(){
		
	}
}
