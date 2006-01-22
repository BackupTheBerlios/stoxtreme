package stoxtreme.servidor;
import java.util.*;
import stoxtreme.servidor.eventos.*;
import stoxtreme.servidor.eventos.SistemaEventos;
import stoxtreme.servidor.gestion_usuarios.GestionUsuarios;

public class Servidor {
	Hashtable empresas;
	Reloj reloj;
	VariablesSistema variables;
	SistemaEventos eventos;
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
