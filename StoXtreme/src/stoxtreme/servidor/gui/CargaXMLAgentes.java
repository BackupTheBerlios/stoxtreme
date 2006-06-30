package stoxtreme.servidor.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import com.sun.org.apache.xpath.internal.XPathAPI;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class CargaXMLAgentes {
	/**
	 *  Description of the Method
	 *
	 *@param  fich           Description of Parameter
	 *@param  panel          Description of Parameter
	 *@exception  Exception  Description of Exception
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
	 *  Description of the Method
	 *
	 *@param  panel          Description of Parameter
	 *@param  doc            Description of Parameter
	 *@exception  Exception  Description of Exception
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
	 *  Description of the Method
	 *
	 *@param  doc            Description of Parameter
	 *@param  id             Description of Parameter
	 *@return                Description of the Returned Value
	 *@exception  Exception  Description of Exception
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
	 *  Description of the Method
	 *
	 *@param  panel          Description of Parameter
	 *@param  doc            Description of Parameter
	 *@exception  Exception  Description of Exception
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
	 *  Description of the Method
	 *
	 *@param  panel          Description of Parameter
	 *@param  doc            Description of Parameter
	 *@exception  Exception  Description of Exception
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
	 *  Description of the Method
	 *
	 *@param  panel          Description of Parameter
	 *@param  doc            Description of Parameter
	 *@exception  Exception  Description of Exception
	 */
	private static void procesaDistribuciones(PanelConfigAgentes panel, Document doc) throws Exception {
		procesaNormales(panel, doc);
		procesaPoissons(panel, doc);
		procesaUniformes(panel, doc);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  panel          Description of Parameter
	 *@param  doc            Description of Parameter
	 *@exception  Exception  Description of Exception
	 */
	private static void procesaUniformes(PanelConfigAgentes panel, Document doc) throws Exception {
		NodeIterator iterator = XPathAPI.selectNodeIterator(doc, "//dist_uniforme");
		Element actual;
		while ((actual = (Element) iterator.nextNode()) != null) {
			String id = actual.getAttribute("id");
			double minimo = Double.parseDouble(actual.getAttribute("minimo"));
			double maximo = Double.parseDouble(actual.getAttribute("maximo"));
			panel.insDistribucion(id, "Uniforme", minimo, maximo);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  panel          Description of Parameter
	 *@param  doc            Description of Parameter
	 *@exception  Exception  Description of Exception
	 */
	private static void procesaPoissons(PanelConfigAgentes panel, Document doc) throws Exception {
		NodeIterator iterator = XPathAPI.selectNodeIterator(doc, "//dist_poisson");
		Element actual;
		while ((actual = (Element) iterator.nextNode()) != null) {
			String id = actual.getAttribute("id");
			double lambda = Double.parseDouble(actual.getAttribute("lambda"));
			panel.insDistribucion(id, "Poison", lambda, 0.0);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  panel          Description of Parameter
	 *@param  doc            Description of Parameter
	 *@exception  Exception  Description of Exception
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
