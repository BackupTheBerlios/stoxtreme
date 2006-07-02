package stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.IntroducirOperacion;
import stoxtreme.interfaz_remota.Operacion;

public class ComportamientoAprendizaje extends ComportamientoAgente{
	private int tiempo;
	private Hashtable<String,SistClasificador> clasificadores;
	private Mundo mundoClasificador;
	
	public void configure() {
		AGenetico.creaConfiguracion();
		tiempo = 0;
		// Iniciamos el clasificador
		clasificadores = new Hashtable<String,SistClasificador>();
		mundoClasificador = new Mundo();
		ArrayList<String> empresas = estadoBolsa.getEmpresas();
		
		for(int i=0; i<empresas.size(); i++){
			clasificadores.put(empresas.get(i), new SistClasificador(10));
		}
	}
	
	public void generacionDecisiones() {
	}
	
	public void _generacionDecisiones() {
		tiempo++;
		// Revisa las operaciones pendientes para asignar los pesos 
		Enumeration<String> empresas = clasificadores.keys();

		while(empresas.hasMoreElements()){
			String empresa = empresas.nextElement();
			ArrayList<Integer> operacionesRealizadas = compruebaRealizadas(empresa);
			SistClasificador clasificador = clasificadores.get(empresa);
			for(int i=0; i<operacionesRealizadas.size(); i++){
				clasificador.asignaPesos(operacionesRealizadas.get(i));
			}
		}
		
		String empresa = estadoBolsa.dameEmpresaAleatoria();
		double precioEmpresa = estadoBolsa.getPrecioActualEmpresa(empresa);
		mundoClasificador.registraPrecio(empresa, precioEmpresa);
		
		SistClasificador clasificador = clasificadores.get(empresa);
		Regla regla = clasificador.encajaReglas(mundoClasificador);
		
		if( regla!=null && regla.getAccion() == Regla.COMPRA){
			Operacion op = generaCompra(regla);
			decisiones.add(new IntroducirOperacion(op));
		}
		else if(regla!=null && regla.getAccion() == Regla.VENTA){
			Operacion op = generaVenta(regla);
			decisiones.add(new IntroducirOperacion(op));
		}
		
		// FIXME: Para cambiar el clasificador no puede tener compras marcadas sin venta
		// o borrarlas
		if(tiempo%10 == 0){
			// Evoluciona un sistema clasificador aleatoriamente
			SistClasificador nuevo = AGenetico.generaNuevasReglas(clasificador);
			clasificadores.put(empresa, nuevo);
		}
	}
	
	private ArrayList<Integer> compruebaRealizadas(String empresa) {
		ArrayList<Integer> realizadas = new ArrayList<Integer>();
		
		return realizadas;
	}

	public Operacion generaCompra(Regla regla){
		// Tengo que asociarlo con una venta, o si no, guardarlo
		// para asociarlo posteriormente
		return null;
	}
	
	public Operacion generaVenta(Regla regla){
		// Lo asocio con una o varias compras
		return null;
	}
}	
