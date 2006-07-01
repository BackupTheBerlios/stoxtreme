package stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador;
import java.util.ArrayList;
import java.util.Hashtable;

import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.interfaz_remota.Operacion;

@SuppressWarnings("serial")
public class ComportamientoAprendizaje extends ComportamientoAgente{
	private int tiempo;
	private Hashtable<String,SistClasificador> clasificadores;
	private Mundo mundoClasificador;
	
	public void configure() {
		tiempo = 0;
		// Iniciamos el clasificador
		clasificadores = new Hashtable<String,SistClasificador>();
		
		ArrayList<String> empresas = estadoBolsa.getEmpresas();
		
		for(int i=0; i<empresas.size(); i++){
			clasificadores.put(empresas.get(i), new SistClasificador(10));
		}
	}
	
	public void generacionDecisiones() {
		tiempo++;
		// Revisa las operaciones pendientes para asignar los pesos 
		int[] opPendientes = operacionesPendientes.getOperacionesPendientes();
		// Si se ha efectuado una venta reparto el beneficio entre sus
		// compras asociadas.
		// Si se ha efectuado una compra la marco para que la venta le de
		// beneficio.
		
		String empresa = estadoBolsa.dameEmpresaAleatoria();
		double precioEmpresa = estadoBolsa.getPrecioActualEmpresa(empresa);
		mundoClasificador.registraPrecio(empresa, precioEmpresa);
		SistClasificador clasificador = clasificadores.get(empresa);
		Decision accion = clasificador.encajaReglas(mundoClasificador);
		decisiones.add(accion);
		
		// FIXME: Para cambiar el clasificador no puede tener compras marcadas sin venta
		// o borrarlas
		if(tiempo%10 == 0){
			// Evoluciona un sistema clasificador aleatoriamente
			SistClasificador nuevo = AGenetico.generaNuevasReglas(clasificador);
			clasificadores.put(empresa, nuevo);
		}
	}
	
	public Operacion generaCompra(){
		// Tengo que asociarlo con una venta, o si no, guardarlo
		// para asociarlo posteriormente
		return null;
	}
	
	public Operacion generaVenta(){
		// Lo asocio con una o varias compras
		return null;
	}
	
	public String getNombreComportamiento() {
		return "Inteligente";
	}
}	
