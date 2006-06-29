package stoxtreme.servidor.objeto_bolsa.fluctuaciones;

import java.util.*;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.servidor.Servidor;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/*
 * - Todas las operaciones de una empresa llegan aqui
 * - Esta clase tiene (al menos) una lista de compras y una lista de ventas
 *
 *  Introducir Operacion -> devuelve idOp
 *  Cancelar(idOp)
 *
 *  Paso() para el reloj. Cruza las operaciones
 *
 *  *Atributos
 *  Lista de compras y de ventas
 *
 *
 */
public class SistemaOperaciones /*implements RelojListener*/{
        private Hashtable listaCompras;
        private Hashtable listaVentas;
        private int nAccionesVenta;
        private double precioEstimado;
        private TreeSet idscompras;
        private TreeSet idsventas;
        
        public SistemaOperaciones (int nAccionesVenta){
          listaCompras=new Hashtable();
          listaVentas=new Hashtable();
          this.nAccionesVenta = nAccionesVenta;
          this.precioEstimado=1;
          idscompras = new TreeSet();
          idsventas = new TreeSet();
        }
        public SistemaOperaciones (Hashtable lC,Hashtable lV){
          this.listaCompras=lC;
          this.listaVentas=lV;
        }
        public Hashtable getCompras(){
          return listaCompras;
        }
        public Hashtable getVentas(){
          return listaVentas;
        }
        public static double calculaPrecio(double precioA,double tick,double precioCliente){
        	double auxTick=tick;
        	if (precioA>precioCliente){
        		auxTick=-auxTick;
        	   	while(!(precioA+auxTick<precioCliente)){
	        		auxTick-=tick;
	        	}
        	   	return (auxTick+precioA);
        	}
        	else{
        		while(!(precioA+auxTick>precioCliente)){
	        		auxTick+=tick;
	        	}
        	   	return (auxTick+precioA);

        	}
        }
        //TODO falta mirar a ver si tengo por variable lo que necesito
        public void introduceCompra(int idOperacion, String agente,
                        double precio, int numAcciones,double precioA,double tick) {
        	Vector compra=new Vector();
        	Integer accionesTotales;
        	Posicion p1;
        	int idOp;
        	Integer num= new Integer(numAcciones);
        	if (listaCompras.containsKey(Double.toString(calculaPrecio(precioA,tick,precio)))){
        		compra=(Vector) listaCompras.get(Double.toString(calculaPrecio(precioA,tick,precio)));
        		accionesTotales=(Integer)compra.firstElement();
        		accionesTotales=Integer.valueOf(num.intValue()+accionesTotales.intValue());
        		compra.setElementAt(accionesTotales,0);
        		p1=new Posicion(agente,numAcciones,idOperacion);
        		compra.add(p1);
        	}
        	else{
        		p1=new Posicion(agente,numAcciones,idOperacion);
        		compra.add(num);
        		compra.add(p1);
        		listaCompras.put(Double.toString(calculaPrecio(precioA,tick,precio)),compra);
        		
        	}
        	
        	idscompras.add(idOperacion);
        }

        public void introduceVenta(int idOperacion, String agente,
                        double precio, int numAcciones,double precioA,double tick) {
        	Vector venta=new Vector();
        	Integer accionesTotales;
        	Posicion p1;
        	int idOp;
        	Integer num= new Integer(numAcciones);
        	if (listaVentas.containsKey(Double.toString(calculaPrecio(precioA,tick,precio)))){
        		venta=(Vector) listaVentas.get(Double.toString(calculaPrecio(precioA,tick,precio)));
        		accionesTotales=(Integer)venta.firstElement();
        		accionesTotales=Integer.valueOf(num.intValue()+accionesTotales.intValue());
        		venta.setElementAt(accionesTotales,0);
        		p1=new Posicion(agente,numAcciones,idOperacion);
        		venta.add(p1);
        	}
        	else{
        		p1=new Posicion(agente,numAcciones,idOperacion);
        		venta.add(num);
        		venta.add(p1);
        		listaVentas.put(Double.toString(calculaPrecio(precioA,tick,precio)),venta);
        		
        	}
        	idsventas.add(idOperacion);
        }

        public void cancelaOperacion(int idOperacion, String idAgente) {
        	Hashtable operacion;
        	boolean encontrado=false;
        	boolean esCompra;
        	Vector cadena=new Vector();
        	Posicion pi;
        	if (idscompras.contains(idOperacion)){
        		operacion=this.listaCompras;
        		esCompra=true;
        		idscompras.remove(idOperacion);
        	}
        	else{
	        	if(idsventas.contains(idOperacion)){
	        		operacion=this.listaVentas;
	        		idsventas.remove(idOperacion);
	        		esCompra=false;
	        	}
	        	else{
	        		System.out.println("No existe la operacion deseada "+idOperacion);
	        		return;
	        	}
        	}
        	
        	System.err.println(listaCompras);
        	System.err.println(listaVentas);
        	Enumeration claves = operacion.keys();
        	while(claves.hasMoreElements()&&!encontrado){
        		String elemento=(String) claves.nextElement();
        		cadena=(Vector)operacion.get(elemento);
        		int i=1;
        		while(!encontrado&&i<cadena.size()){
        			pi=(Posicion)cadena.elementAt(i);
        			if (pi.getIdOperacion()==idOperacion){
        				encontrado=true;
        				//cadena.remove(i);
        				Integer acciones=(Integer)cadena.firstElement();
        				acciones=new Integer(acciones.intValue()-pi.getNumeroDeAcciones());
        				if (acciones.toString().equals("0")) {
        					cadena.removeAllElements(); 
        				    operacion.remove(elemento);
        				    
        				}
        				else{
        					cadena.setElementAt(acciones,0);
        				}
        				System.out.println("se ha eliminado la operacion "+idOperacion);
        				AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Integer.toString(idOperacion), "NOTIFICACION_CANCELACION", idAgente));
        			}
        			i++;
        		}
        	}
        	if(esCompra)
        		listaCompras=operacion;
        	else
        		listaVentas=operacion;
                // Tiene que BUSCAR la operacion a cancelar si hace falta
                // se almacenaran las cancelaciones y cuando pase un tiempo se efectuaran
                // todas
        }
		public int getAccionesVenta() {
			return nAccionesVenta;
		}
		public void setAccionesVenta(int accionesVenta) {
			nAccionesVenta = accionesVenta;
		}
		public double getPrecioEstimado() {
			return precioEstimado;
		}
		public void setPrecioEstimado(double precioEstimado) {
			this.precioEstimado = precioEstimado;
		}
		public void notificaOperacion(String idAgente, int idOperacion, int numAcciones,double precio) {
			String tipo = "NOTIFICACION_OPERACION";
			String contenido = idOperacion+","+numAcciones+","+Fluctuaciones.redondeo(precio,2);
			Mensaje m = new Mensaje(contenido, tipo, idAgente);
			AlmacenMensajes.getInstance().enviaMensaje(m);
		}
		public void setListaCompras(Hashtable listaCompras) {
			this.listaCompras = listaCompras;
		}
		public void setListaVentas(Hashtable listaVentas) {
			this.listaVentas = listaVentas;
		}
}