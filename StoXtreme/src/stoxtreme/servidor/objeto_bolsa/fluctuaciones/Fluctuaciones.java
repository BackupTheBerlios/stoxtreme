package stoxtreme.servidor.objeto_bolsa.fluctuaciones;

import java.util.*;

import javax.swing.JOptionPane;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.objeto_bolsa.ObjetoBolsa;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;
/**
 * <p>T�tulo: </p>
 * <p>Descripci�n: </p>
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
  protected double tick;//o intervalo?
  protected double pActual;
  private VariablesSistema varS;
  private String empresa;
  
  public static double redondeo(double numero, int nDecimales){
	  return Math.floor((Math.pow(10, nDecimales)*numero)+0.5)/Math.pow(10, nDecimales);
  }
  
  public Fluctuaciones(VariablesSistema varSis,SistemaOperaciones sO,double nt,double pa,String emp) {
    sisOp=sO;
    tick=nt;
    pActual=pa;
    varS=varSis;
    empresa=emp;
  }
  
  public Fluctuaciones(SistemaOperaciones sO,double nt,double pa,String emp) {
	    sisOp=sO;
	    tick=nt;
	    pActual=pa;
	    empresa=emp;
  }
  public Hashtable filtrayOrdena(Hashtable vTotal){
    Hashtable vFinal=new Hashtable();
    Vector listaValores;
    Enumeration listaClaves;
    double max, min;
    String claveI;
    max= pActual + 5;
    min= pActual - 5;
    listaClaves=vTotal.keys();
    while (listaClaves.hasMoreElements()){
      claveI=(String)listaClaves.nextElement();
    //}
      if(Double.parseDouble(claveI)<=max && Double.parseDouble(claveI)>=min){
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
  //cambiar para el proyecto total
  public double paso(){
    pActual=calculaValorTitulo();
    String nomEmpresa="PRECIO_"+empresa.toUpperCase();
    varS.cambiaVariable(nomEmpresa,new Double(pActual));
    return pActual;
  }
  
  
  public double calculaValorTitulo(/*String nombreTitulo,double precioA*/){
		boolean hayOperacion=false;
	    Hashtable compra,venta;
	    Vector preciosCompra,preciosVenta;
	    Enumeration claves;
	    //Posicion aux,aux2;
	    String clave,claveFinal;
	    compra=filtrayOrdena(sisOp.getCompras());
	    venta=filtrayOrdena(sisOp.getVentas());
	    claves=compra.keys();
	    //if (compra.size() == venta.size() ){
	    int ordenesM=0;
	    int ordParcial;
	    double precioM=pActual;
	    claveFinal="";
	    while(claves.hasMoreElements()){
	      clave=(String)claves.nextElement();
	      if (venta.containsKey(clave)){
	        preciosCompra=(Vector)compra.get(clave);
	        preciosVenta=(Vector)venta.get(clave);
	        ordParcial=minimo(Integer.parseInt((String)preciosCompra.elementAt(0).toString()),
	                              Integer.parseInt((String)preciosVenta.elementAt(0).toString()));
	        //atencion esto se queda kon el primer minimo mirar a ver si keremos otros minimos
	        if (ordParcial>ordenesM){
	        	hayOperacion=true;
	            ordenesM = ordParcial;
	            precioM=Double.parseDouble(clave);
	            claveFinal=clave;

	        }
	      }
	    }
	    if (hayOperacion){
	    	preciosCompra=(Vector)compra.get(claveFinal);
	        preciosVenta=(Vector)venta.get(claveFinal);
	        int auxC=ordenesM,auxV=ordenesM;
	        int indiceC=1,indiceV=1;
	        Integer numAccionesC=(Integer)preciosCompra.elementAt(0);
	        numAccionesC=Integer.valueOf(numAccionesC.intValue()-auxC);
	        Integer numAccionesV=(Integer)preciosVenta.elementAt(0);
	        numAccionesV=Integer.valueOf(numAccionesV.intValue()-auxV);
	        preciosCompra.setElementAt(numAccionesC,0);
	        preciosVenta.setElementAt(numAccionesV,0);
	        while(auxC>0&&indiceC<preciosCompra.size()&&indiceV<preciosVenta.size()){
	        	Posicion pC=(Posicion)preciosCompra.elementAt(indiceC);
	        	Posicion pV=(Posicion)preciosVenta.elementAt(indiceV);
	        	if (pC.getNumeroDeAcciones()<=auxC){	        		
	        		auxC-=pC.getNumeroDeAcciones();
	        		sisOp.notificaOperacion(pC.getIDAgente(),pC.getIdOperacion(),pC.getNumeroDeAcciones(),precioM);
	        		preciosCompra.remove(indiceC);
	        		indiceC++;	        		
	        	}
	        	else{
	        		pC.setNumeroDeAcciones(pC.getNumeroDeAcciones()-auxC);
	        		sisOp.notificaOperacion(pC.getIDAgente(),pC.getIdOperacion(),auxC,precioM);
	        		auxC=0;
	        	}
	        	if (pV.getNumeroDeAcciones()<=auxV){
	        		auxV-=pV.getNumeroDeAcciones();
	        		sisOp.notificaOperacion(pV.getIDAgente(),pV.getIdOperacion(),pV.getNumeroDeAcciones(),precioM);
	        		preciosVenta.remove(indiceV);
	        		indiceV++;
	        	}
	        	else{
	        		pV.setNumeroDeAcciones(pV.getNumeroDeAcciones()-auxV);
	        		sisOp.notificaOperacion(pV.getIDAgente(),pV.getIdOperacion(),auxV,precioM);
	        		auxV=0;
	        	}	        	
	        }
	        if (numAccionesC.intValue()==0){
	        	compra.remove(claveFinal);
	        }
	        if (numAccionesV.intValue()==0){
	        	venta.remove(claveFinal);
	        }
	    }else{
	    	claveFinal=buscaMayorVolumen(compra,compra.keys());
	    	if (!claveFinal.equals("")&&sisOp.getAccionesVenta()>0){
		    	boolean acabado=sisOp.getAccionesVenta()==0;
		    	int i=1;
	//	    	Vector auxV=(Vector)compra.get(Double.toString
	//	    			(SistemaOperaciones.calculaPrecio
	//	    					(pActual,tick,pActual+sisOp.getPrecioEstimado())));
		    	Vector auxC=(Vector)compra.get(claveFinal);
		    	Integer accionesTotales=(Integer)auxC.elementAt(0);
		    	if (sisOp.getAccionesVenta()>accionesTotales.intValue())
		    		accionesTotales=new Integer(0);
		    	else{
		    		accionesTotales=Integer.valueOf(accionesTotales.intValue()-sisOp.getAccionesVenta());
		    		auxC.set(0,accionesTotales);
		    	}
		    	if(sisOp.getAccionesVenta()>0 && auxC!=null && auxC.size()>0){
		    		i=1;
		    		System.out.println("tama�o vector compras"+auxC.size()+"\n");
		    		while(auxC.size()>i && !acabado ){
		    			Posicion pC=(Posicion)auxC.get(i);
		    			if (pC.getNumeroDeAcciones()>sisOp.getAccionesVenta()){
		    				pC.setNumeroDeAcciones(pC.getNumeroDeAcciones()-sisOp.getAccionesVenta());
			        		System.out.println("He comprado "+(sisOp.getAccionesVenta()));
		    				sisOp.notificaOperacion(pC.getIDAgente(),pC.getIdOperacion(),sisOp.getAccionesVenta(),Double.parseDouble(claveFinal));
			        		sisOp.setAccionesVenta(0);
			        		acabado=true;
		    			}
		    			else{
		    				sisOp.setAccionesVenta(sisOp.getAccionesVenta()-pC.getNumeroDeAcciones());
			        		sisOp.notificaOperacion(pC.getIDAgente(),pC.getIdOperacion(),pC.getNumeroDeAcciones(),Double.parseDouble(claveFinal));
		    				System.out.println("He comprado "+(pC.getNumeroDeAcciones()));
			        		auxC.remove(i);
			        		//i++;
		    			}
		    				
		    		}
		    		if(accionesTotales.intValue()==0)
		    			auxC=null;
		    		Hashtable aux=sisOp.getCompras();
		    		aux.remove(claveFinal);
		    		if (auxC!=null)
		    			aux.put(claveFinal,auxC);
		    		sisOp.setListaCompras(aux);
		    		precioM=Double.parseDouble(claveFinal);
		    		return redondeo(precioM, 2);
		    	}
		    }
	    }
	    //sisOp.setListaCompras(compra);
	    //sisOp.setListaVentas(venta);
	    return redondeo(precioM, 2);
	  }	  

  private static String buscaMayorVolumen(Hashtable compra, Enumeration claves) {
	  String claveActual="";
	  String claveSalida="";
	  int volumen=0;
	  while(claves.hasMoreElements()){
		  claveActual=(String)claves.nextElement();
		  if (((Integer)((Vector)compra.get(claveActual)).elementAt(0)).intValue()>volumen)
			  claveSalida=claveActual;
	  }
	return claveSalida;
}



public static void main(String[] args) {
	SistemaOperaciones so=new SistemaOperaciones(90);
	Fluctuaciones fluc=new Fluctuaciones(so,0.3,25.3,"Sony-Ericson");
	//fluc.getSisOp().introduceCompra(1,"Agente Smith",27.6,40,fluc.getPrecioActual(),fluc.getTick());
	//fluc.getSisOp().introduceVenta(5,"Agente Rocio",27.6,30,fluc.getPrecioActual(),fluc.getTick());
	fluc.getSisOp().introduceCompra(2,"Agente Pollo",1,1,fluc.getPrecioActual(),fluc.getTick());
	fluc.getSisOp().introduceCompra(3,"Agente Pollo",1,1,fluc.getPrecioActual(),fluc.getTick());
	fluc.getSisOp().introduceCompra(4,"Agente Pollo",1,1,fluc.getPrecioActual(),fluc.getTick());
	fluc.getSisOp().introduceCompra(5,"Agente Pollo",1,1,fluc.getPrecioActual(),fluc.getTick());
	//fluc.getSisOp().introduceCompra(3,"Agente Pollo2",27.6,20,fluc.getPrecioActual(),fluc.getTick());
	//fluc.getSisOp().introduceCompra(5,"Agente Pollo3",27.6,70,fluc.getPrecioActual(),fluc.getTick());
	//fluc.getSisOp().introduceVenta(7,"Agente Yo",28.6,15,fluc.getPrecioActual(),fluc.getTick());
//	fluc.getSisOp().cancelaOperacion(9,"loko");
	//fluc.paso();
	fluc.getSisOp().cancelaOperacion(4,"Agente Pollo");
	fluc.getSisOp().cancelaOperacion(5,"Agente Pollo");
	//fluc.paso();
	/*Hashtable cT=new Hashtable();
    Hashtable vT=new Hashtable();
    //Double precio1=new Double(23.5f);
    //Double precio2=new Double(25.5);
    double precio1=(double)23.5;
    double precio2=(double)25.5;
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
    cT.put(Double.toString(precio1),compras);
    cT.put(Double.toString(precio2),compras2);
    v1=new Posicion("vendedor1",25,4);
    v2=new Posicion("vendedor2",10,5);
    v3=new Posicion("vendedor3",15,6);
    ventas.add(new Integer(25));
    ventas.add(v1);
    ventas2.add(new Integer(25));
    ventas2.add(v2);
    ventas2.add(v3);
    vT.put(Double.toString(precio1),ventas);
    vT.put(Double.toString(precio2),ventas2);
    
    SistemaOperaciones so=new SistemaOperaciones(cT,vT);
    Fluctuaciones fluctuaciones1 = new Fluctuaciones(so,(double)5.0,(double)20.5,"GESSER");
    fluctuaciones1.paso();*/
  }

public double getPrecioActual() {
	return pActual;
}

public void setPrecioActual(double actual) {
	pActual = actual;
}

public double getTick() {
	return tick;
}

public void setTick(double tick) {
	this.tick = tick;
}

public SistemaOperaciones getSisOp() {
	return sisOp;
}

public void setSisOp(SistemaOperaciones sisOp) {
	this.sisOp = sisOp;
}

}