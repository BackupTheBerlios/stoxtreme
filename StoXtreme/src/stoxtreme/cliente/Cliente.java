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

public class Cliente implements IAgente{
	
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
	 * @uml.associationEnd  qualifier="new:java.lang.Integer interfaz_remota.Operacion"
	 */
	public Hashtable opPendientes;
	
	public Cliente(){
		id="";
		opPendientes=new Hashtable();
		try{
			c=new XmlRpcClientLite("http://localhost:8888");
			}catch (MalformedURLException mue){
				mue.printStackTrace();
			}
	
	}
	
	public Cliente(String ident){
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
			//
			result =((Integer)c.execute("operacion.insOp", params)).intValue();
			System.out.println(result);
			return result;
		}catch (XmlRpcException xre){
			xre.printStackTrace();
			return -1;
		}catch (IOException ioe){
			ioe.printStackTrace();
			return -1;
		}
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
		int result;
		result=insertaOperacion(id, nuevaOp);
		opPendientes.put(new Integer(result),nuevaOp);
	}
	
	public static void main(String[] args) {
		
		Cliente usuario=new Cliente("yo");
		Operacion op=new Operacion("Compra","Endesa",300,50);
		usuario.insertaOperacion(usuario.id,op);
		
	}

}
