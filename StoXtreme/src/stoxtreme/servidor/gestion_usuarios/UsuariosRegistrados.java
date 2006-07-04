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


/**
 *  Clase que guarda los datos de los usuarios registrados en el sistema
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class UsuariosRegistrados {
	private Hashtable<String, String> registrados;
	private String rutaFichero;
	private DocumentBuilderFactory factory;
	private Document document;


	/**
	 *  Constructor del objeto UsuariosRegistrados
	 *
	 *@param  fich  Ruta del fichero .xml que contiene los datos de los usuarios registrados
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


	/**
	 *  Comprueba si ya existe un usuario con ese id
	 *
	 *@param  id  Id de usuario
	 *@return     Booleano que indica si ese id ya está cogido
	 */
	public boolean existeUsuario(String id) {
		return registrados.containsKey(id);
	}


	/**
	 *  Añade el usuario a la tabla de registrados
	 *
	 *@param  id   Id de usuario
	 *@param  psw  Contraseña
	 */
	public void insertaUsuario(String id, String psw) {
		registrados.put(id, psw);
	}

	/**
	 *  Comprueba que el password introducido es correcto
	 *
	 *@param  id   Id de usuario
	 *@param  psw  Contraseña
	 *@return      Booleano que indica si la contraseña corresponde al id
	 */
	public boolean compruebaPsw(String id, String psw) {
		return psw.equals(registrados.get(id));
	}


	/**
	 *  Inserta un nuevo elemento de tipo "usuario" en el arbol DOM
	 *
	 *@param  id   Id de usuario
	 *@param  psw  Contraseña
	 */
	public void insertaEnDOM(String id, String psw) {
		Element e = document.createElement("usuario");
		e.setAttribute("id", id);
		e.setAttribute("psw", psw);
		document.getChildNodes().item(0).appendChild(e);
	}


	/**
	 *  Rellena el fichero de registrados con los datos del arbol DOM (al finalizar la sesion)
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


	/**
	 *  Rellena la tabla hash con los datos del fichero de registrados
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
	 *  Devuelve los ids de los usuarios registrados en el sistema
	 *
	 *@return    Enumeración de los nombres de usuarios
	 */
	public Enumeration<String> dameUsuarios() {
		return registrados.keys();
	}
}
