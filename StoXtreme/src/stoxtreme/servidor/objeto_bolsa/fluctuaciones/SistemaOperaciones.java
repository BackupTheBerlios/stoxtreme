package stoxtreme.servidor.objeto_bolsa.fluctuaciones;

import java.util.*;

import stoxtreme.interfaz_remota.Mensaje;
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
        //private Hashtable<Double, ArrayList<Object>> listaCompras;
        //private Hashtable<Double, ArrayList<Object>> listaVentas;
        private Hashtable listaCompras;
        private Hashtable listaVentas;
        private int nAccionesVenta;
        private double precioEstimado;
        private TreeSet idscompras;
        private TreeSet idsventas;
        
        public void paso(double nuevoPrecio){
        	// TODO: IMPLEMENTAR EL CRUCE
        	// Recorrer las operaciones a "nuevoPrecio"
        	// Lo primero es cruzar con las acciones de la empresa 
        	// ponerlo como un abanico de precios
        }
        public SistemaOperaciones (int nAccionesVenta){
          listaCompras=new Hashtable();
          listaVentas=new Hashtable();
          this.nAccionesVenta = nAccionesVenta;
          //this.precioEstimado=(nAccionesVenta%2)+0.5;
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
        	//preciodef=
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

        public void cancelaOperacion(int idOperacion) {
        	Hashtable operacion;
        	boolean encontrado=false;
        	Vector cadena=new Vector();
        	Posicion pi;
        	if (idscompras.contains(idOperacion)){
        		operacion=this.listaCompras;
        		idscompras.remove(idOperacion);
        	}
        	else{
	        	if(idsventas.contains(idOperacion)){
	        		operacion=this.listaVentas;
	        		idsventas.remove(idOperacion);
	        	}
	        	else{
	        		System.out.println("No existe la operacion deseada");
	        		return;
	        	}
        	}
        	Enumeration claves=operacion.keys();
        	while(claves.hasMoreElements()&&!encontrado){
        		String elemento=(String) claves.nextElement();
        		cadena=(Vector)operacion.get(elemento);
        		int i=1;
        		while(!encontrado&&i<cadena.size()){
        			pi=(Posicion)cadena.elementAt(i);
        			if (pi.getIdOperacion()==idOperacion){
        				encontrado=true;
        				cadena.remove(i);
        				Integer acciones=(Integer)cadena.firstElement();
        				acciones=new Integer(acciones.intValue()-pi.getNumeroDeAcciones());
        				if (acciones.toString().equals("0")) {
        					cadena.removeAllElements(); 
        					//System.out.println(elemento);
        				    operacion.remove(elemento);
        				    cadena.removeAllElements();
        				}
        				else
        					cadena.setElementAt(acciones,0);
        				System.out.println("se ha eliminado la operacion "+idOperacion);
        			}
        			i++;
        		}
        	}
                // Tiene que BUSCAR la operacion a cancelar si hace falta
                // se almacenaran las cancelaciones y cuando pase un tiempo se efectuaran
                // todas
        }
        public static void main(String[] args) {
            //Hashtable cT=new Hashtable();
            //Hashtable vT=new Hashtable();
            //Double precio1=new Double(23.5f);
            //Double precio2=new Double(25.5);
            /*double precio1=(double)23.5;
            double precio2=(double)25.5;
            Vector compras=new Vector();
            Vector compras2=new Vector();
            Posicion c1,c2,c3,v1,v2,v3;
            c1=new Posicion("comprador1",53,1);
            c2=new Posicion("comprador2",32,2);
            c3=new Posicion("comprador3",62,3);
            compras.add(Integer.toString(85));
            compras.add(c1);
            compras.add(c2);
            compras2.add(Integer.toString(62));
            compras2.add(c3);
            Vector ventas=new Vector();
            Vector ventas2=new Vector();
            cT.put(Double.toString(precio1),compras);
            cT.put(Double.toString(precio2),compras2);
            v1=new Posicion("vendedor1",25,4);
            v2=new Posicion("vendedor2",10,5);
            v3=new Posicion("vendedor3",15,6);
            ventas.add(Integer.toString(25));
            ventas.add(v1);
            ventas2.add(Integer.toString(25));
            ventas2.add(v2);
            ventas2.add(v3);
            vT.put(Double.toString(precio1),ventas);
            vT.put(Double.toString(precio2),ventas2);*/
            calculaPrecio(3.10,0.3,2.58);
            //SistemaOperaciones so=new SistemaOperaciones(cT,vT);
            //so.introduceCompra(1,"Pako",23.4,35);
            //so.cancelaOperacion(1,"Compra");
            //Fluctuaciones fluctuaciones1 = new Fluctuaciones(so,(double)5.0,(double)20.5);
            //fluctuaciones1.paso();
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
		public void notificaOperacion(String idAgente, int idOperacion, int numAcciones) {
			String tipo = "NOTIFICACION_OPERACION";
			String contenido = idAgente+","+numAcciones;
			Mensaje m = new Mensaje(contenido, tipo, idAgente);
			AlmacenMensajes.getInstance().enviaMensaje(m);
		}
}
