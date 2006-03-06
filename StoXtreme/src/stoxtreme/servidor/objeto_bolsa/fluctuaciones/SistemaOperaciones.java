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
        //private Hashtable<float, ArrayList<Object>> listaCompras;
        //private Hashtable<float, ArrayList<Object>> listaVentas;
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
                        float precio, int numAcciones) {
                // TODO Introduce la operacion en la tabla correspondiente
        }

        public void introduceVenta(int idOperacion, String agente,
                        float precio, int numAcciones) {
                // TODO Introduce la operacion en la tabla correspondiente
        }

        public void cancelaOperacion(int idOperacion, String tipoOp) {
                // TODO Tiene que BUSCAR la operacion a cancelar si hace falta
                // se almacenaran las cancelaciones y cuando pase un tiempo se efectuaran
                // todas
        }
}
