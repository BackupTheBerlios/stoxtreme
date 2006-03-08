package stoxtreme.servidor.gestion_usuarios;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UsuariosRegistrados {
private Hashtable  registrados ;
	
	//Constructora
	public UsuariosRegistrados(String fich){
		registrados=new Hashtable();
		vuelcaFichero(fich);

	}
	
	public void leeDatos() {
		// TODO Lee los datos de los usuarios registrados de donde
		// corresponda
	}
	
	//Comprueba si ya existe un usuario con ese id
	public boolean existeUsuario(String id){
		return registrados.containsKey(id);
	}
	
	//Añade el usuario a la tabla
	public void insertaUsuario(String id, String psw){
		registrados.put(id,psw);
	}
	
	//Comprueba que el password introducido es correcto
	public boolean compruebaPsw(String id, String psw){
		return psw.equals(registrados.get(id));
	}
	
	//Rellena la tabla hash con los datos del fichero
	public void vuelcaFichero(String fichero){
		DocumentBuilderFactory factory;
		Document document;
		factory = DocumentBuilderFactory.newInstance();
		try {
			document = factory.newDocumentBuilder().parse(new File(fichero));
			NodeList nl = document.getElementsByTagName("usuario");
			String id=null;
			String psw=null;
			//Inserto los datos del usuario en la tabla Hash
			for (int i=0; nl!=null && i<nl.getLength();i++){
				id=((Element)nl.item(i)).getAttribute("id");
				psw=((Element)nl.item(i)).getAttribute("psw");
				this.insertaUsuario(id,psw);
			}
	    } catch (SAXException sxe) {
	       // Error generated during parsing
	       Exception  x = sxe;
	       if (sxe.getException() != null)
	           x = sxe.getException();
	       x.printStackTrace();

	    } catch (ParserConfigurationException pce) {
	       // Parser with specified options can't be built
	       pce.printStackTrace();
	    } catch (IOException ioe) {
	       // I/O error
	       ioe.printStackTrace();
	    }
	}
	
	
}
