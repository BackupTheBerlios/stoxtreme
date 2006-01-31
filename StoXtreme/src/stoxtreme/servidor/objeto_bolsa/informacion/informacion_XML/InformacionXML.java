package stoxtreme.servidor.objeto_bolsa.informacion.informacion_XML;


import java.io.File;
import java.io.IOException;
import java.util.Date;
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
import stoxtreme.servidor.objeto_bolsa.ObjetoBolsa;
import stoxtreme.servidor.objeto_bolsa.informacion.Balance;
import stoxtreme.servidor.objeto_bolsa.informacion.Cuenta;
import stoxtreme.servidor.objeto_bolsa.informacion.InfoBursatil;

public class InformacionXML implements IInformacion{
	/* 
	 * Actuará como un monitor de lectores escritores, creando inicialmente el hilo que escribirá
	 * Toda la información concurrentemente y que comprobará si ha habido cambios o quizas mejor
	 * mediante un metodo de UPDATE, los lectores leeran eventualmente la información que necesiten.
	 * 
	 * Si se omite el nombre de la empresa se supondra que el archivo esta
	 * dedicado por entero a la empresa y por tanto no tiene que buscar el tag
	 * 
	 * FORMATO DE LOS XMLS
	 * XML GLOBAL
	 * <info>
	 * 		<empresa nombre="NombreDeLaEmpresa">
	 * 			<info_bursatil>
	 * 				<participaciones> NUMERO_PARTICIPACIONES </participaciones>
	 * 				<ampliaciones> NUMERO_AMPLIACIONES </ampliaciones>
	 * 				<capital> DINERO_CAPITAL </capital>
	 * 				<archivo_historico>
	 * 					FICHERO_HISTORICO.xml
	 * 				</archivo_historico>
	 * 			<info_bursatil>
	 * 			<balances>
	 * 				<balance fecha="FECHA">
	 * 					<efectivo></efectivo>
	 * 					<bienes></bienes>
	 * 					<empleados></empleados>
	 * 				</balance>
	 * 			</balances>
	 * 			<cuentas>
	 * 				<cuenta fecha="FECHA">
	 * 					<periodo> MENSUAL | TRIMESTRAL |ANUAL </periodo>
	 * 					<ganancias>GANANCIAS</ganancias>
	 * 					<perdidas>PERDIDAS</perdidas>
	 * 				</cuenta>
	 * 			</cuentas>
	 * 			<perspectiva></perspectiva>
	 * 		</empresa>
	 * </info>
	*/
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	private Element ele;
	
	public InformacionXML(String archivo, String empresa){
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new File(archivo));
			ele = document.getDocumentElement();
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

	public Balance getBalanceActual() {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector getBalances(Date inicio, Date fin) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setBalance(Balance b, Date fecha) {
		// TODO Auto-generated method stub
		
	}

	public Vector getCuentas(Date inicio, Date fin) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCuenta(Cuenta c, Date fecha) {
		// TODO Auto-generated method stub
		
	}

	public InfoBursatil getDatosBursatiles() {
		InfoBursatil ib=null;
		Hashtable part=new Hashtable();
		//////TODO MAL
		NodeList nl = ele.getOwnerDocument().getElementsByTagName("info_bursatil");
		int num_acciones=0;
		String nombre=null;
		//Obtengo todos los accionistas y les asigno el numero de acciones
		for (int i=0; nl!=null && i<nl.getLength();i++){
			ele=(Element)nl.item(i);
			nombre=ele.getAttribute("nombre");
			//le quitamos los /t y /n del final y del principio
			num_acciones=new Integer(ele.getTextContent().trim()).intValue();
			part.put(nombre,num_acciones);
		}
		ib.setParticipaciones(part);
		return ib;
	}

	public void setInfoBursatil(InfoBursatil i) {
		
	}

	public String getPerspectivaFuturo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPerspectivaFuturo(String s) {
		// TODO Auto-generated method stub
		
	}
}
