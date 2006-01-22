package stoxtreme.cliente;

import interfaz_remota.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.xmlrpc.XmlRpcClientLite;
import org.apache.xmlrpc.XmlRpcException;

import stoxtreme.interfaz_remota.IAgente;
import stoxtreme.interfaz_remota.Operacion;

public class ClienteRemoto implements IAgente{
	
	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	public XmlRpcClientLite c=null;
	/**
	 * @uml.property  name="id"
	 */
	public String id;
	//Faltaria un objeto grupoAgentes que tenga la info de todos los agentes que simula el cliente
	/**
	 * @uml.property  name="opPendientes"
	 * @uml.associationEnd  qualifier="toString:java.lang.String interfaz_remota.Operacion"
	 */
	public Hashtable opPendientes;
	
	public ClienteRemoto(){
		id="";
		opPendientes=new Hashtable();
		try{
			c=new XmlRpcClientLite("http://localhost:8888");
			}catch (MalformedURLException mue){
				mue.printStackTrace();
			}
	
	}
	
	public ClienteRemoto(String ident){
		id=ident;
		opPendientes=new Hashtable();
		try{
			c=new XmlRpcClientLite("http://localhost:8888");
			}catch (MalformedURLException mue){
				mue.printStackTrace();
			}
	
	}
	
	public boolean registro(String nUsuario, String pwd){
		return true;
	}
	
	public boolean login(String nUsuario, String pwd){
		return true;
	}
	public int insertaOperacion(String id, Operacion op){
		
		Hashtable t =op.toHashtable();
		Vector params = new Vector();
		try{
			int result = -1;
			params.addElement(t);
			params.addElement(id);
			//
			result =((Integer)c.execute("operacion.insOp", params)).intValue();
			System.out.println(result);
			if (result!=-1){
				opPendientes.put(new Integer(result).toString(),op);
			}
			else
				System.out.println("Fallo al insertar la operacion");
			}catch (XmlRpcException xre){
			xre.printStackTrace();
			
		}catch (IOException ioe){
			ioe.printStackTrace();
			
		}
		// TODO devolver el valor
		return 0;
	}
	public void cancelaOperacion(int idOp){
		Vector params = new Vector();
		try{
			params.addElement(new Integer(idOp));
			c.execute("operacion.cancelOp", params);
		}catch (XmlRpcException xre){
			xre.printStackTrace();
		}catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void modificaOperacion(int idOp, Operacion nuevaOp){
		cancelaOperacion(idOp);
		insertaOperacion(id, nuevaOp);
	}
	//Este main es para probar que funciona
	public static void main(String[] args) {
		
		Cliente usuario=new Cliente("yo");
		Operacion op=new Operacion("Compra","Endesa",300,50);
		usuario.insertaOperacion(usuario.id,op);
		
	}

}
