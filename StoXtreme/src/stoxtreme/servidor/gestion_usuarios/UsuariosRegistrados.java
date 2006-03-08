package stoxtreme.servidor.gestion_usuarios;

/*Al iniciarse el servidor se vuelcan los datos del fichero
 *  registrados.xml en la tabla hash. 
 **/
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
	
	public UsuariosRegistrados(){
		registrados=new Hashtable();
		vuelcaFichero("conf/registrados.xml");

	}
	
	public void leeDatos() {
		// TODO Lee los datos de los usuarios registrados de donde
		// corresponda
	}
	
	public boolean existeUsuario(String id){
		return registrados.containsKey(id);
	}
	
	public void añadeUsuario(String id, String pwd){
		registrados.put(id,pwd);
	}
	//El fichero contiene el nombre de usuario y la contraseña
	public void vuelcaFichero(String fichero){
		DocumentBuilderFactory factory;
		Document document;
		factory = DocumentBuilderFactory.newInstance();
		try {
			document = factory.newDocumentBuilder().parse(new File(fichero));
			NodeList nl = document.getElementsByTagName("usuario");
			String id=null;
			String pwd=null;
			//Inserto los datos del usuario en la tabla Hash
			for (int i=0; nl!=null && i<nl.getLength();i++){
				id=((Element)nl.item(i)).getAttribute("id");
				pwd=((Element)nl.item(i)).getAttribute("pwd");
				this.añadeUsuario(id,pwd);
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
