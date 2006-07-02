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
			clasificadores.put(empresas.get(i), new SistClasificador(empresas.get(i),10));
		}
	}
	
	public void generacionDecisiones() {
	}
	
	public void _generacionDecisiones() {
		// Revisa las operaciones pendientes para asignar los pesos 
		Enumeration<String> empresas = clasificadores.keys();

		while(empresas.hasMoreElements()){
			String empresa = empresas.nextElement();
			double precio = estadoBolsa.getPrecioActualEmpresa(empresa);
			mundoClasificador.registraPrecio(tiempo, empresa, precio);
		}
		
		String empresa = estadoBolsa.dameEmpresaAleatoria();
		double precioEmpresa = estadoBolsa.getPrecioActualEmpresa(empresa);
		mundoClasificador.registraPrecio(tiempo, empresa, precioEmpresa);
		
		SistClasificador clasificador = clasificadores.get(empresa);
		Regla regla = clasificador.encajaReglas(tiempo, mundoClasificador);
		
		if( regla!=null && regla.getAccion() == Regla.COMPRA){
			Operacion op = generaCompra(empresa, regla);
			decisiones.add(new IntroducirOperacion(op));
		}
		else if(regla!=null && regla.getAccion() == Regla.VENTA){
			Operacion op = generaVenta(empresa, regla);
			decisiones.add(new IntroducirOperacion(op));
		}
		
		if(clasificador.todasLasReglasEjecutadas()){
			// Evoluciona un sistema clasificador aleatoriamente
			SistClasificador nuevo = AGenetico.generaNuevasReglas(clasificador);
			clasificadores.put(empresa, nuevo);
		}
		tiempo++;
	}
	
	public Operacion generaCompra(String empresa, Regla regla){
		double precio = estadoBolsa.getPrecioActualEmpresa(empresa);
		int max = modeloPsicologico.getNumeroMaximoCompra();
		int min = modeloPsicologico.getNumeroMinimoCompra();
		int cantidad = min + (int)((max-min)*regla.getPrecision());
		
		clasificadores.get(empresa).asignaPesosCompra(regla, cantidad*precio);
		// Tengo que asociarlo con una venta, o si no, guardarlo
		// para asociarlo posteriormente
		return new Operacion(null, Operacion.COMPRA, cantidad, empresa, precio);
	}
	
	public Operacion generaVenta(String empresa, Regla regla){
		double precio = estadoBolsa.getPrecioActualEmpresa(empresa);
		int max = modeloPsicologico.getNumeroMaximoVenta();
		int min = modeloPsicologico.getNumeroMinimoVenta();
		int cantidad = min + (int)((max-min)*regla.getPrecision());
		
		clasificadores.get(empresa).asignaPesosVenta(regla, cantidad*precio);
		// Lo asocio con una o varias compras
		return new Operacion(null, Operacion.COMPRA, cantidad, empresa, precio);
	}
}	
