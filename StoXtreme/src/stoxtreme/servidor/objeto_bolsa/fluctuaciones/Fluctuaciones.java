package stoxtreme.servidor.objeto_bolsa.fluctuaciones;
import java.util.*;
/**
 * <p>Título: </p>
 * <p>Descripción: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Empresa: </p>
 * @author sin atribuir
 * @version 1.0
 */
//OJO EL PRIMER ELEMENTO QUE LLEVA EL NUMERO DE ACCIONES ES UN STRING AUNK SEA UN NUMERO
public class Fluctuaciones {
  //protected Hashtable entorno=new Hashtable();
  //protected Hashtable compraT,ventaT;
  private SistemaOperaciones sisOp;
  protected float tick;//o intervalo?
  protected float pActual;
  public Fluctuaciones(SistemaOperaciones sO,float nt,float pa) {
    sisOp=sO;
    tick=nt;
    pActual=pa;
  }
  public Hashtable filtrayOrdena(Hashtable vTotal){
    Hashtable vFinal=new Hashtable();
    Vector listaValores;
    Enumeration listaClaves;
    float max, min;
    String claveI;
    max= pActual + tick;
    min= pActual - tick;
    int j=0;
    listaClaves=vTotal.keys();
    while (listaClaves.hasMoreElements()){
      claveI=(String)listaClaves.nextElement();
    //}
      if(Float.parseFloat(claveI)<=max && Float.parseFloat(claveI)>=min){
        listaValores=(Vector)vTotal.get(claveI);
        if (!vFinal.containsKey(claveI)){
          vFinal.put(claveI,listaValores);
        }
      }
      //listaClaves.nextElement();
    }
    return vFinal;
  }
  private int minimo(int ordC, int ordV){
    if (ordC<ordV)
      return ordC;
    else
      return ordV;
  }
  public void paso(){
    float nuevoValor=calculaValorTitulo();
    System.out.print("El nuevo valor es="+nuevoValor);
  }
  public float calculaValorTitulo(/*String nombreTitulo,float precioA*/){

    Hashtable compra,venta;
    Vector preciosCompra,preciosVenta;
    Enumeration claves;
    //Posicion aux,aux2;
    String clave;
    compra=filtrayOrdena(sisOp.getCompras());
    venta=filtrayOrdena(sisOp.getVentas());
    claves=compra.keys();
    //if (compra.size() == venta.size() ){
      int ordenesM=0;
      int ordParcial;
      float precioM=pActual;
      while(claves.hasMoreElements()){
        clave=(String)claves.nextElement();
        if (venta.containsKey(clave)){
          preciosCompra=(Vector)compra.get(clave);
          preciosVenta=(Vector)venta.get(clave);
            ordParcial=minimo(Integer.parseInt((String)preciosCompra.elementAt(0).toString()),
                              Integer.parseInt((String)preciosVenta.elementAt(0).toString()));
           //atencion esto se queda kon el primer minimo mirar a ver si keremos otros minimos
            if (ordParcial>ordenesM){
              ordenesM = ordParcial;
              precioM=Float.parseFloat(clave);

            }

        }
    }
    return precioM;
  }
  public static void main(String[] args) {
    Hashtable cT=new Hashtable();
    Hashtable vT=new Hashtable();
    //Float precio1=new Float(23.5f);
    //Float precio2=new Float(25.5);
    float precio1=(float)23.5;
    float precio2=(float)25.5;
    Vector compras=new Vector();
    Vector compras2=new Vector();
    Posicion c1,c2,c3,v1,v2,v3;
    c1=new Posicion("comprador1",53,1);
    c2=new Posicion("comprador2",32,2);
    c3=new Posicion("comprador3",62,3);
    compras.add(new Integer(85));
    compras.add(c1);
    compras.add(c2);
    compras2.add(new Integer(62));
    compras2.add(c3);
    Vector ventas=new Vector();
    Vector ventas2=new Vector();
    cT.put(Float.toString(precio1),compras);
    cT.put(Float.toString(precio2),compras2);
    v1=new Posicion("vendedor1",25,4);
    v2=new Posicion("vendedor2",10,5);
    v3=new Posicion("vendedor3",15,6);
    ventas.add(Integer.toString(25));
    ventas.add(c1);
    ventas2.add(Integer.toString(25));
    ventas2.add(c2);
    ventas2.add(c3);
    vT.put(Float.toString(precio1),ventas);
    vT.put(Float.toString(precio2),ventas2);
    SistemaOperaciones so=new SistemaOperaciones(cT,vT);
    Fluctuaciones fluctuaciones1 = new Fluctuaciones(so,(float)5.0,(float)20.5);
    fluctuaciones1.paso();
  }

}