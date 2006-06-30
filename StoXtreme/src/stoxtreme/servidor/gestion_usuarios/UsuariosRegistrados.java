package stoxtreme.servidor.gestion_usuarios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.apache.xml.serialize.XMLSerializer;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;

//import stoxtreme.servidor.gui.MainFrameAdmin;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class UsuariosRegistrados {
	private Hashtable<String, String> registrados;
	private String rutaFichero;
	private DocumentBuilderFactory factory;
	private Document document;


	//Constructora
	/**
	 *  Constructor for the UsuariosRegistrados object
	 *
	 *@param  fich  Description of Parameter
	 */
	public UsuariosRegistrados(String fich) {
		rutaFichero = fich;
		registrados = new Hashtable<String, String>();
		factory = DocumentBuilderFactory.newInstance();
		try {
			document = factory.newDocumentBuilder().parse(new File(rutaFichero));
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		vuelcaEnTabla();
	}


	//Comprueba si ya existe un usuario con ese id
	/**
	 *  Description of the Method
	 *
	 *@param  id  Description of Parameter
	 *@return     Description of the Returned Value
	 */
	public boolean existeUsuario(String id) {
		return registrados.containsKey(id);
	}


	//A�ade el usuario a la tabla
	/**
	 *  Description of the Method
	 *
	 *@param  id   Description of Parameter
	 *@param  psw  Description of Parameter
	 */
	public void insertaUsuario(String id, String psw) {
		registrados.put(id, psw);
	}


	//Comprueba que el password introducido es correcto
	/**
	 *  Description of the Method
	 *
	 *@param  id   Description of Parameter
	 *@param  psw  Description of Parameter
	 *@return      Description of the Returned Value
	 */
	public boolean compruebaPsw(String id, String psw) {
		return psw.equals(registrados.get(id));
	}


	//Inserta un nuevo elemento de tipo "usuario" en el arbol DOM
	/**
	 *  Description of the Method
	 *
	 *@param  id   Description of Parameter
	 *@param  psw  Description of Parameter
	 */
	public void insertaEnDOM(String id, String psw) {
		Element e = document.createElement("usuario");
		e.setAttribute("id", id);
		e.setAttribute("psw", psw);
		document.getChildNodes().item(0).appendChild(e);
	}


	//Rellena el fichero con los datos del arbol DOM (al finalizar la sesion)
	/**
	 *  Description of the Method
	 */
	public void vuelcaEnFichero() {
		OutputFormat format = new OutputFormat(Method.XML, "UTF-8", true);
		format.setPreserveSpace(true);
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream(rutaFichero);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XMLSerializer serializer = new XMLSerializer(oStream, format);
		try {
			serializer.serialize(this.document);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	//Rellena la tabla hash con los datos del fichero

	/**
	 *  Description of the Method
	 */
	public void vuelcaEnTabla() {
		NodeList nl = document.getElementsByTagName("usuario");
		String id = null;
		String psw = null;
		//Inserto los datos del usuario en la tabla Hash
		for (int i = 0; nl != null && i < nl.getLength(); i++) {
			id = ((Element) nl.item(i)).getAttribute("id");
			psw = ((Element) nl.item(i)).getAttribute("psw");
			this.insertaUsuario(id, psw);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public Enumeration<String> dameUsuarios() {
		return registrados.keys();
	}
}
