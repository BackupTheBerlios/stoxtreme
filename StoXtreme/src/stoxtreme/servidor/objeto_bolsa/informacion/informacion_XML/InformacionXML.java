package stoxtreme.servidor.objeto_bolsa.informacion.informacion_XML;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import stoxtreme.interfaz_remota.IInformacion;
import stoxtreme.servidor.objeto_bolsa.informacion.InfoBursatil;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class InformacionXML implements IInformacion {
	/*
	 *  Actuará como un monitor de lectores escritores, creando inicialmente el hilo que escribirá
	 *  Toda la información concurrentemente y que comprobará si ha habido cambios o quizas mejor
	 *  mediante un metodo de UPDATE, los lectores leeran eventualmente la información que necesiten.
	 *
	 *  Si se omite el nombre de la empresa se supondra que el archivo esta
	 *  dedicado por entero a la empresa y por tanto no tiene que buscar el tag
	 *
	 *  FORMATO DE LOS XMLS
	 *  XML GLOBAL
	 *  <info>
	 *  <empresa nombre="NombreDeLaEmpresa" grupo="Energia/Servicios de consumo/...">
	 *  <info_bursatil>
	 *  <capital_social capital=IMPORTE valor_nominal=IMPORTE num_acciones=NUMERO_ACCIONES/>
	 *  <participaciones>
	 *  <accionista nombre=NOMBRE numero=NUMERO DE ACCIONES QUE POSEE>
	 *  </participaciones>
	 *  <ampliaciones> NUMERO_AMPLIACIONES </ampliaciones>
	 *  <archivo_historico>
	 *  FICHERO_HISTORICO.xml
	 *  </archivo_historico>
	 *  <info_bursatil>
	 *  <balances>
	 *  <balance fecha="FECHA">
	 *  <efectivo></efectivo>
	 *  <bienes></bienes>
	 *  <empleados></empleados>
	 *  </balance>
	 *  </balances>
	 *  <cuentas>
	 *  <cuenta fecha="FECHA">
	 *  <periodo> MENSUAL | TRIMESTRAL |ANUAL </periodo>
	 *  <ganancias>GANANCIAS</ganancias>
	 *  <perdidas>PERDIDAS</perdidas>
	 *  </cuenta>
	 *  </cuentas>
	 *  <perspectiva></perspectiva>
	 *  </empresa>
	 *  </info>
	 */
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;


	/**
	 *  Constructor for the InformacionXML object
	 *
	 *@param  archivo  Description of Parameter
	 *@param  empresa  Description of Parameter
	 */
	public InformacionXML(String archivo, String empresa) {
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new File(archivo));
		}
		catch (SAXException sxe) {
			// Error generated during parsing
			Exception x = sxe;
			if (sxe.getException() != null) {
				x = sxe.getException();
			}
			x.printStackTrace();

		}
		catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			pce.printStackTrace();
		}
		catch (IOException ioe) {
			// I/O error
			ioe.printStackTrace();
		}
	}


	/*
	 *  Modificamos el XML para adecuarlo a los cambios
	 *  que se han dado en la bolsa
	 */
	/**
	 *  Sets the Participaciones attribute of the InformacionXML object
	 *
	 *@param  ht  The new Participaciones value
	 */
	public void setParticipaciones(Hashtable ht) {

	}


	/*
	 *  Modificamos el XML para adecuarlo a los cambios
	 *  que se han dado en la bolsa
	 */
	/**
	 *  Sets the AmpliacionesCapital attribute of the InformacionXML object
	 *
	 *@param  v  The new AmpliacionesCapital value
	 */
	public void setAmpliacionesCapital(Vector v) {
	}


	/**
	 *  Sets the InfoBursatil attribute of the InformacionXML object
	 *
	 *@param  i  The new InfoBursatil value
	 */
	public void setInfoBursatil(InfoBursatil i) {

	}


	/**
	 *  Sets the PerspectivaFuturo attribute of the InformacionXML object
	 *
	 *@param  s  The new PerspectivaFuturo value
	 */
	public void setPerspectivaFuturo(String s) {

	}



	/*
	 *  Obtenemos los datos bursatiles:
	 *  Participaciones (nombre del accionista, numero de acciones que posee)
	 *  Capital Social (Capital, calor nominal de la accion y numero de acciones)
	 *  Ampliaciones de capital
	 *  Informacion historica
	 */
	/**
	 *  Gets the DatosBursatiles attribute of the InformacionXML object
	 *
	 *@return    The DatosBursatiles value
	 */
	public InfoBursatil getDatosBursatiles() {
		InfoBursatil ib = new InfoBursatil();
		ib.setParticipaciones(this.getParticipaciones());
		ib.setAmpliacionesCapital(this.getAmpliacionesCapital());
		ib.setCapitalSocial(this.getCapitalSocial());
		return ib;
	}


	/*
	 *  Obtenemos las participaciones.
	 *  Se guardan en una Hashtable con:
	 *  key=nombre del accionista
	 *  valor=numero de acciones que posee
	 */
	/**
	 *  Gets the Participaciones attribute of the InformacionXML object
	 *
	 *@return    The Participaciones value
	 */
	@SuppressWarnings("unchecked")
	public Hashtable getParticipaciones() {
		Hashtable part = new Hashtable();
		NodeList nl = document.getElementsByTagName("accionista");
		//Obtengo todos los accionistas y les asigno el numero de acciones
		for (int i = 0; nl != null && i < nl.getLength(); i++) {
			String nombre = ((Element) nl.item(i)).getAttribute("nombre");
			float porcentaje = new Float(((Element) nl.item(i)).getAttribute("porcentaje")).floatValue();
			part.put(nombre, porcentaje);
		}
		return part;
	}


	/*
	 *  Obtenemos la informacion de las ampliaciones de capital.
	 *
	 */
	/**
	 *  Gets the AmpliacionesCapital attribute of the InformacionXML object
	 *
	 *@return    The AmpliacionesCapital value
	 */
	@SuppressWarnings("unchecked")
	public Vector getAmpliacionesCapital() {
		Vector amps = new Vector();
		NodeList nl = document.getElementsByTagName("ampliaciones");
		amps.addElement(((Element) nl.item(0)).getTextContent().trim());
		return amps;
	}


	/**
	 *  Gets the CapitalSocial attribute of the InformacionXML object
	 *
	 *@return    The CapitalSocial value
	 */
	@SuppressWarnings("unchecked")
	public Vector getCapitalSocial() {
		Vector capSocial = new Vector();
		NodeList nl = document.getElementsByTagName("capital_social");
		capSocial.addElement(((Element) nl.item(0)).getAttribute("capital"));
		capSocial.addElement(((Element) nl.item(0)).getAttribute("valor_nominal"));
		capSocial.addElement(((Element) nl.item(0)).getAttribute("num_acciones"));
		return capSocial;
	}


	/**
	 *  Gets the PerspectivaFuturo attribute of the InformacionXML object
	 *
	 *@return    The PerspectivaFuturo value
	 */
	public String getPerspectivaFuturo() {
		return null;
	}

}
