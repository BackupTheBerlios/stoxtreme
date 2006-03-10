package stoxtreme.servidor.objeto_bolsa.fluctuaciones;

import java.util.*;

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
        public void paso(){

        }
        public SistemaOperaciones (){
          listaCompras=new Hashtable();
          listaVentas=new Hashtable();
        }
        public SistemaOperaciones (Hashtable lC,Hashtable lV){
          listaCompras=lC;
          listaVentas=lV;
        }
        public Hashtable getCompras(){
          return listaCompras;
        }
        public Hashtable getVentas(){
          return listaVentas;
        }

        public void introduceCompra(int idOperacion, String agente,
                        double precio, int numAcciones) {
        	Vector compra=new Vector();
        	Integer accionesTotales;
        	Posicion p1;
        	int idOp;
        	Integer num= new Integer(numAcciones);
        	if (listaCompras.containsKey(Double.toString(precio))){
        		compra=(Vector) listaCompras.get(Double.toString(precio));
        		accionesTotales=(Integer)compra.firstElement();
        		accionesTotales=Integer.valueOf(num.intValue()+accionesTotales.intValue());
        		compra.setElementAt(accionesTotales,0);
        		//TODO problema con el id op
        		//idOp=lista
        		p1=new Posicion(agente,numAcciones,4);
        	}
        	else{
        		p1=new Posicion(agente,numAcciones,4);
        		compra.add(num);
        		compra.add(p1);
        		listaCompras.put(Double.toHexString(precio),compra);
        		
        	}
        }

        public void introduceVenta(int idOperacion, String agente,
                        double precio, int numAcciones) {
                // TODO Introduce la operacion en la tabla correspondiente
        }

        public void cancelaOperacion(int idOperacion, String tipoOp) {
                // TODO Tiene que BUSCAR la operacion a cancelar si hace falta
                // se almacenaran las cancelaciones y cuando pase un tiempo se efectuaran
                // todas
        }
}
