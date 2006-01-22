package stoxtreme.servidor;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.xmlrpc.WebServer;

public class ServidorRemoto {
	/**
	 * @uml.property  name="ops"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.util.Hashtable"
	 */
	public Vector ops=new Vector();
	/**
	 * @uml.property  name="turno"
	 */
	int turno=0;

	public int insOp(Hashtable op, String is){
		System.out.println(op.get("nombreEmpresa"));
		try{
			System.out.println("procesando operacion");
			if (op.get("nombreEmpresa").equals("Endesa")){
				ops.addElement(op);
				turno=turno+1;
				return turno;
			}
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	public static void main(String[] args) {
		ServidorRemoto handler = new ServidorRemoto();
		WebServer s =null;
		
		try{
			s = new WebServer(8888);
		}catch (Exception ioe){
			ioe.printStackTrace();
		}
		s.addHandler("operacion", handler);
		s.start();
	}

}
