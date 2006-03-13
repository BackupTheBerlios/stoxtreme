package stoxtreme.servidor;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParametrosServidor {
	public static String FICH_EMP="ficheroEmpresas";
	public static String FICH_REG="ficheroRegistrados";
	public static String TICK="tick";
	public static String TIEMPO="tiempo";
	
	private Hashtable <String,Object> parametros;
	
	//Constructora
	public ParametrosServidor(){
		this.parametros=new Hashtable <String,Object>();
		this.leeFicheroParametros("conf/parametros.xml");
	}
	
	//Accesores de "parametros"
	public double getTick(){
		return ((Double)(this.parametros.get(TICK)));
	}
	
	public long getTiempo(){
		return ((Long)(this.parametros.get(TIEMPO)));
	}
	
	public String getFicheroEmpresas() {
		return this.parametros.get(FICH_EMP).toString();
	}
	
	
	public String getFicheroRegistrados() {
		return this.parametros.get(FICH_REG).toString();
	}
	
	//Modificador de la tabla hash "parametros"
	public void modificarParams(String clave,Object valor){
		parametros.put(clave,valor);
	}
	
	//Vuelca en "parametros" la informacion del fichero XML
	public void leeFicheroParametros(String fichero) {
		DocumentBuilderFactory factory;
		Document document;
		factory = DocumentBuilderFactory.newInstance();
		try {
			document = factory.newDocumentBuilder().parse(new File(fichero));
			NodeList nl = document.getElementsByTagName("tick");
			this.modificarParams(TICK,new Double(((Element)nl.item(0)).getTextContent().trim()));
			nl = document.getElementsByTagName("tiempo");
			this.modificarParams(TIEMPO,new Long(((Element)nl.item(0)).getTextContent().trim()));
			nl = document.getElementsByTagName("fichero_emp");
			this.modificarParams(FICH_EMP,((Element)nl.item(0)).getTextContent().trim());
			nl = document.getElementsByTagName("fichero_reg");
			this.modificarParams(FICH_REG,((Element)nl.item(0)).getTextContent().trim());
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
