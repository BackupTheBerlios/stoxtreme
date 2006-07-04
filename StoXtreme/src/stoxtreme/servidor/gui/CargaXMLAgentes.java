package stoxtreme.servidor.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import stoxtreme.servidor.gui.PanelConfigAgentes.ElementoComportamiento;
import stoxtreme.servidor.gui.PanelConfigAgentes.ElementoDistribucion;
import stoxtreme.servidor.gui.PanelConfigAgentes.Par;

import com.sun.org.apache.xpath.internal.XPathAPI;

/**
 *  Trata el archivo de configuracion de agentes
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class CargaXMLAgentes {
	/**
	 *  Parser que extrae la información del archivo de configuración de agentes
	 *  y la guarda en un objeto de tipo PanelConfigAgentes
	 *
	 *@param  fich           Ruta del fichero
	 *@param  panel          Donde se guardara la informacion del fichero
	 *@exception  Exception  Excepción al parsear
	 */
	public static void carga(File fich, PanelConfigAgentes panel) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc = factory.newDocumentBuilder().parse(fich);

		Element root = doc.getDocumentElement();
		int maxAgentes = Integer.parseInt(root.getAttribute("max_agentes"));
		int minAgentes = Integer.parseInt(root.getAttribute("min_agentes"));
		int minEspera = Integer.parseInt(root.getAttribute("min_tespera"));
		int maxEspera = Integer.parseInt(root.getAttribute("max_tespera"));
		String distribEspera = root.getAttribute("tespera_distrib");
		double maxGasto = Double.parseDouble(root.getAttribute("max_gasto"));
		double ratioRespawn = Double.parseDouble(root.getAttribute("ratio_respawn"));
		double atenuacion = Double.parseDouble(root.getAttribute("atenuacion_rumor"));

		panel.setAtributos(
				maxAgentes, minAgentes, minEspera, maxEspera,
				distribEspera, maxGasto, ratioRespawn, atenuacion
				);

		procesaDistribuciones(panel, doc);
		procesaModelosSociales(panel, doc);
		procesaModelosPsicologicos(panel, doc);
		procesaComportamientos(panel, doc);
		panel.expandeArbol();
	}
	
	
	/**
	 *  Guarda las modificaciones de configuracion en el fichero correspondiente
	 *
	 *@param  fich           Ruta del fichero
	 *@param  panel          Nueva informacion
	 *@exception  Exception  Excepción al parsear
	 */
	public static void guarda(File fich, PanelConfigAgentes panel) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc;
		doc = factory.newDocumentBuilder().newDocument();
		//Creo el nodo raiz y relleno sus atributos
		Element root=doc.createElement("agentes");
		doc.appendChild(root);
		root.setAttribute("max_agentes",panel.getAtributos().getValor("Numero máximo de agentes"));
		root.setAttribute("min_agentes",panel.getAtributos().getValor("Numero mínimo de agentes"));
		root.setAttribute("min_tespera",panel.getAtributos().getValor("Tiempo de espera mínimo"));
		root.setAttribute("max_tespera",panel.getAtributos().getValor("Tiempo de espera máximo"));
		root.setAttribute("tespera_distrib",panel.getAtributos().getValor("Distribucion del tiempo de espera"));
		root.setAttribute("max_gasto",panel.getAtributos().getValor("Gasto máximo"));
		root.setAttribute("ratio_respawn",panel.getAtributos().getValor("Ratio respawn"));
		root.setAttribute("atenuacion_rumor",panel.getAtributos().getValor("Atenuacion rumor"));
		//Creo los nodos hijos distribuciones_probabiblidad, modelos y comportamientos
		Element distrib=doc.createElement("distribuciones_probabilidad");
		root.appendChild(distrib);
		Element modelos=doc.createElement("modelos");
		root.appendChild(modelos);
		Element comps=doc.createElement("comportamientos");
		root.appendChild(comps);
		Element elem;
		//Relleno el hijo distribuciones_probabilidad
		Enumeration distris=panel.getDistribuciones().elements();
		while (distris.hasMoreElements()) {
			ElementoDistribucion el=(ElementoDistribucion)distris.nextElement();
			if(el.getTipo().equals("Poisson")){
				elem=doc.createElement("dist_poisson");
				elem.setAttribute("id",el.getId());
				elem.setAttribute("lambda",new Double(el.getP1()).toString());
				distrib.appendChild(elem);
			}
			if(el.getTipo().equals("Normal")){
				elem=doc.createElement("dist_normal");
				elem.setAttribute("id",el.getId());
				elem.setAttribute("media",new Double(el.getP1()).toString());
				elem.setAttribute("desviacion_tipica",new Double(el.getP2()).toString());
				distrib.appendChild(elem);
			}
			if(el.getTipo().equals("Uniforme")){
				elem=doc.createElement("dist_uniforme");
				elem.setAttribute("id",el.getId());
				elem.setAttribute("maximo",new Double(el.getP1()).toString());
				elem.setAttribute("minimo",new Double(el.getP2()).toString());
				distrib.appendChild(elem);
			}
		}
		//Relleno el hijo modelos (psicologicos y sociales)
		Enumeration mPsis=panel.getPsicologicos().elements();
		while (mPsis.hasMoreElements()){
			Par el=(Par)mPsis.nextElement();
			elem=doc.createElement("modelo_psicologico");
			elem.setAttribute("id",el.getModelo().getId());
			modelos.appendChild(elem);
			for(int i=0;i<ModeloTablaEdicion.params_psicologicos.length;i++){
				elem.appendChild(doc.createElement("parametro_modelo"));
			}
			NodeList nl=elem.getChildNodes();
			Element hijo;
			for(int i=0;i<ModeloTablaEdicion.params_psicologicos.length;i++){
				hijo=((Element)nl.item(i));
				hijo.setAttribute("tipo",ModeloTablaEdicion.params_psicologicos[i]);
				if(el.getModelo().isDistribucion(i)){
					hijo.appendChild(doc.createElement("distribucion"));
					((Element)hijo.getFirstChild()).setTextContent(el.getModelo().getValores().get(ModeloTablaEdicion.params_psicologicos[i]).toString());
				}
				else{
					hijo.appendChild(doc.createElement("valor"));
					((Element)hijo.getFirstChild()).setTextContent(el.getModelo().getValores().get(ModeloTablaEdicion.params_psicologicos[i]).toString());
				}
			}
			
		}
		Enumeration mSoc=panel.getSociales().elements();
		while (mSoc.hasMoreElements()){
			Par el=(Par)mSoc.nextElement();
			elem=doc.createElement("modelo_social");
			elem.setAttribute("id",el.getModelo().getId());
			modelos.appendChild(elem);
			for(int i=0;i<ModeloTablaEdicion.params_social.length;i++){
				elem.appendChild(doc.createElement("parametro_modelo"));
			}
			NodeList nl=elem.getChildNodes();
			Element hijo;
			for(int i=0;i<ModeloTablaEdicion.params_social.length;i++){
				hijo=((Element)nl.item(i));
				hijo.setAttribute("tipo",ModeloTablaEdicion.params_social[i]);
				if(el.getModelo().isDistribucion(i)){
					hijo.appendChild(doc.createElement("distribucion"));
					((Element)hijo.getFirstChild()).setTextContent(el.getModelo().getValores().get(ModeloTablaEdicion.params_social[i]).toString());
				}
				else{
					hijo.appendChild(doc.createElement("valor"));
					((Element)hijo.getFirstChild()).setTextContent(el.getModelo().getValores().get(ModeloTablaEdicion.params_social[i]).toString());
				}
			}
		}
		//Relleno el hijo comportamientos
		MutableTreeNode compRaiz=(MutableTreeNode)panel.getComportamientos().getRoot();
		Enumeration hijos=compRaiz.children();
		DefaultMutableTreeNode nodo;
		DefaultMutableTreeNode nodo2;
		Enumeration hijos2;
		while (hijos.hasMoreElements()){
			nodo=(DefaultMutableTreeNode)hijos.nextElement();
			ElementoComportamiento comp=(ElementoComportamiento)nodo.getUserObject();
			elem=doc.createElement("comportamiento");
			elem.setAttribute("id",comp.getId());
			elem.setAttribute("tipo_comportamiento",comp.getTipoComportamiento());
			elem.setAttribute("modelo_psicologico",comp.getModeloPsicologico());
			elem.setAttribute("model_social",comp.getModeloSocial());
			elem.setAttribute("porcentaje",new Double(comp.getPorcentaje()).toString());
			comps.appendChild(elem);
			hijos2=nodo.children();
			while (hijos2.hasMoreElements()){
				Element elem2=doc.createElement("comportamiento");
				nodo2=(DefaultMutableTreeNode)hijos2.nextElement();
				comp=(ElementoComportamiento)nodo2.getUserObject();
				elem2=doc.createElement("comportamiento");
				elem2.setAttribute("id",comp.getId());
				elem2.setAttribute("tipo_comportamiento",comp.getTipoComportamiento());
				elem.appendChild(elem2);
			}
		}
		//Escribo el DOM en el fichero que me pasan por parametro
		OutputFormat format = new OutputFormat(Method.XML, "UTF-8", true);
		format.setPreserveSpace(true);
		OutputStream oStream = null;
		oStream = new FileOutputStream(fich);			
		XMLSerializer serializer = new XMLSerializer(oStream, format);
		serializer.serialize(doc);
	}


	/**
	 *  Procesa el nodo Comportamientos
	 *
	 *@param  panel          Informacion
	 *@param  doc            Documento que se esta parseando
	 *@exception  Exception  Excepcion al parsear
	 */
	private static void procesaComportamientos(PanelConfigAgentes panel, Document doc) throws Exception {
		NodeIterator iterator = XPathAPI.selectNodeIterator(doc, "//comportamientos/comportamiento");
		Element actual;
		while ((actual = (Element) iterator.nextNode()) != null) {
			String id = actual.getAttribute("id");
			String modelo_social = actual.getAttribute("modelo_social");
			String modelo_psicologico = actual.getAttribute("modelo_psicologico");
			String tipo_comportamiento = actual.getAttribute("tipo_comportamiento");
			double porcentaje = Double.parseDouble(actual.getAttribute("porcentaje"));

			ArrayList<Hashtable<String, Object>> subComportamientos = procesaSubcomportamientos(doc, id);
			panel.insComportamiento(id, tipo_comportamiento, modelo_social, modelo_psicologico, porcentaje, subComportamientos);
		}
	}


	/**
	 *  Procesa los subcomportamientos
	 *
	 *@param  doc            Documento que se esta parseando
	 *@param  id             Nombre del comportamiento padre
	 *@return                Lista de subcomportamientos
	 *@exception  Exception  Excepcion al parsear
	 */
	private static ArrayList<Hashtable<String, Object>> procesaSubcomportamientos(Document doc, String id) throws Exception {
		NodeIterator iterator = XPathAPI.selectNodeIterator(doc, "//comportamiento[@id='" + id + "']/comportamiento");
		Element actual;
		ArrayList<Hashtable<String, Object>> subcomportamientos = new ArrayList<Hashtable<String, Object>>();

		while ((actual = (Element) iterator.nextNode()) != null) {
			Hashtable<String, Object> subc = new Hashtable<String, Object>();
			String idSub = actual.getAttribute("id");
			subc.put("id", idSub);
			String tipo = actual.getAttribute("tipo_comportamiento");
			subc.put("tipo_comportamiento", tipo);
			ArrayList<Hashtable<String, Object>> anidados = procesaSubcomportamientos(doc, idSub);
			subc.put("subcomportamientos", anidados);
			subcomportamientos.add(subc);
		}
		return subcomportamientos;
	}


	/**
	 *  Procesa los modelos psicologicos
	 *
	 *@param  panel          Informacion
	 *@param  doc            Documento que se esta parseando
	 *@exception  Exception  Excepcion al parsear
	 */
	private static void procesaModelosPsicologicos(PanelConfigAgentes panel, Document doc) throws Exception {
		NodeIterator iterator = XPathAPI.selectNodeIterator(doc, "//modelo_psicologico");
		Element actual;
		while ((actual = (Element) iterator.nextNode()) != null) {
			String id = actual.getAttribute("id");
			Hashtable<String, Double> pValores = new Hashtable<String, Double>();
			Hashtable<String, String> pDistribs = new Hashtable<String, String>();

			NodeIterator it2 = XPathAPI.selectNodeIterator(doc, "//modelo_psicologico[@id='" + id + "']/parametro_modelo");
			while ((actual = (Element) it2.nextNode()) != null) {
				// Actual puede tener un valor o puede tener una distribucion
				NodeList lista = actual.getElementsByTagName("valor");
				if (lista.getLength() > 0) {
					double valor = Double.parseDouble(lista.item(0).getTextContent());
					pValores.put(actual.getAttribute("tipo"), valor);
				}
				else {
					lista = actual.getElementsByTagName("distribucion");
					String distribucion = lista.item(0).getTextContent();
					pDistribs.put(actual.getAttribute("tipo"), distribucion);
				}
			}
			panel.insModeloPsicologico(id, pValores, pDistribs);
		}
	}


	/**
	 *  Procesa los modelos sociales
	 *
	 *@param  panel          Informacion
	 *@param  doc            Documento que se esta parseando
	 *@exception  Exception  Excepcion al parsear
	 */
	private static void procesaModelosSociales(PanelConfigAgentes panel, Document doc) throws Exception {
		NodeIterator iterator = XPathAPI.selectNodeIterator(doc, "//modelo_social");
		Element actual;
		while ((actual = (Element) iterator.nextNode()) != null) {
			String id = actual.getAttribute("id");
			Hashtable<String, Double> pValores = new Hashtable<String, Double>();
			Hashtable<String, String> pDistribs = new Hashtable<String, String>();

			NodeIterator it2 = XPathAPI.selectNodeIterator(doc, "//modelo_social[@id='" + id + "']/parametro_modelo");
			while ((actual = (Element) it2.nextNode()) != null) {
				// Actual puede tener un valor o puede tener una distribucion
				NodeList lista = actual.getElementsByTagName("valor");
				if (lista.getLength() > 0) {
					double valor = Double.parseDouble(lista.item(0).getTextContent());
					pValores.put(actual.getAttribute("tipo"), valor);
				}
				else {
					lista = actual.getElementsByTagName("distribucion");
					String distribucion = lista.item(0).getTextContent();
					pDistribs.put(actual.getAttribute("tipo"), distribucion);
				}
			}
			panel.insModeloSocial(id, pValores, pDistribs);
		}
	}


	/**
	 *  Procesa las distribuciones
	 *
	 *@param  panel          Informacion
	 *@param  doc            Documento que se esta parseando
	 *@exception  Exception  Excepcion al parsear
	 */
	private static void procesaDistribuciones(PanelConfigAgentes panel, Document doc) throws Exception {
		procesaNormales(panel, doc);
		procesaPoissons(panel, doc);
		procesaUniformes(panel, doc);
	}


	/**
	 *  Procesa las distribuciones uniformes
	 *
	 *@param  panel          Informacion
	 *@param  doc            Documento que se esta parseando
	 *@exception  Exception  Excepcion al parsear
	 */
	private static void procesaUniformes(PanelConfigAgentes panel, Document doc) throws Exception {
		NodeIterator iterator = XPathAPI.selectNodeIterator(doc, "//dist_uniforme");
		Element actual;
		while ((actual = (Element) iterator.nextNode()) != null) {
			String id = actual.getAttribute("id");
			double maximo = Double.parseDouble(actual.getAttribute("maximo"));
			double minimo = Double.parseDouble(actual.getAttribute("minimo"));
			panel.insDistribucion(id, "Uniforme", maximo, minimo );
		}
	}


	/**
	 *  Procesa las distribuciones poissons
	 *
	 *@param  panel          Informacion
	 *@param  doc            Documento que se esta parseando
	 *@exception  Exception  Excepcion al parsear
	 */
	private static void procesaPoissons(PanelConfigAgentes panel, Document doc) throws Exception {
		NodeIterator iterator = XPathAPI.selectNodeIterator(doc, "//dist_poisson");
		Element actual;
		while ((actual = (Element) iterator.nextNode()) != null) {
			String id = actual.getAttribute("id");
			double lambda = Double.parseDouble(actual.getAttribute("lambda"));
			panel.insDistribucion(id, "Poisson", lambda, 0.0);
		}
	}


	/**
	 *  Procesa las distribuciones normales
	 *
	 *@param  panel          Informacion
	 *@param  doc            Documento que se esta parseando
	 *@exception  Exception  Excepcion al parsear
	 */
	private static void procesaNormales(PanelConfigAgentes panel, Document doc) throws Exception {
		NodeIterator iterator = XPathAPI.selectNodeIterator(doc, "//dist_normal");
		Element actual;
		while ((actual = (Element) iterator.nextNode()) != null) {
			String id = actual.getAttribute("id");
			double media = Double.parseDouble(actual.getAttribute("media"));
			double desviacion = Double.parseDouble(actual.getAttribute("desviacion_tipica"));
			panel.insDistribucion(id, "Normal", media, desviacion);
		}
	}
}
