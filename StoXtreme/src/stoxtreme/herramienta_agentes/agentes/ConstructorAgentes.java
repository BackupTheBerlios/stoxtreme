package stoxtreme.herramienta_agentes.agentes;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.traversal.NodeIterator;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.cliente.gui.HerramientaAgentesTableModel;
import stoxtreme.herramienta_agentes.ConsolaAgentes;
import stoxtreme.herramienta_agentes.Notificador;
import stoxtreme.herramienta_agentes.ParametrosAgentes;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.interfaz_remota.Stoxtreme;

import cern.jet.random.AbstractDistribution;
import cern.jet.random.Normal;
import cern.jet.random.Poisson;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;

import com.sun.org.apache.xpath.internal.XPathAPI;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ConstructorAgentes {
	public static RandomEngine random = new MersenneTwister(new Date());
	private Hashtable<String,Integer> numAgentesComportamiento;
	private Hashtable<String,Double> porcentajesComportamientos;
	private Hashtable<String,Class> classesComportamientos;
	private Hashtable<String,String> psiComportamientos;
	private Hashtable<String,String> socComportamientos;
	
	double max_agentes;
	double min_agentes;
	double min_tespera;
	double max_tespera;
	String tespera_distrib;
	double max_gasto;
	double ratio_respawn;
	double atenuacion_rumor;
	int numAgentes;
	
	private Stoxtreme conexion;
	private EstadoBolsa estadoBolsa;
	private ConsolaAgentes consolaAgentes;
	private Notificador notif;
	private HerramientaAgentesTableModel modeloTabla;
	private ParametrosAgentes parametros;
	private Document document;
	private Hashtable<String,AbstractDistribution> distribuciones;
	private Normal normalTEspera;
	
	/**
	 *  Description of the Method
	 *
	 *@param  conexionBolsa   Description of Parameter
	 *@param  estado          Description of Parameter
	 *@param  consolaAgentes  Description of Parameter
	 *@param  notif           Description of Parameter
	 *@param  modeloTabla     Description of Parameter
	 *@param  fichero         Description of Parameter
	 *@param  parametros      Description of Parameter
	 *@return                 Description of the Returned Value
	 *@exception  Exception   Description of Exception
	 */
	public ArrayList<Agente> construyeAgentes(
			Stoxtreme conexionBolsa,
			EstadoBolsa estado,
			ConsolaAgentes consolaAgentes,
			Notificador notif, 
			HerramientaAgentesTableModel modeloTabla, 
			String fichero,
			ParametrosAgentes parametros
	) throws Exception {
		
		this.conexion = conexionBolsa;
		this.estadoBolsa = estado;
		this.consolaAgentes = consolaAgentes;
		this.notif = notif;
		this.modeloTabla = modeloTabla;
		this.parametros = parametros;
		ArrayList<Agente> agentes = new ArrayList<Agente>();
		// Parseamos el fichero
		document = inicializaDOM(fichero);
		// Conseguimos los datos de la herramienta
		Element raiz = document.getDocumentElement();
		
		this.max_agentes = Double.parseDouble(raiz.getAttribute("max_agentes"));
		this.min_agentes = Double.parseDouble(raiz.getAttribute("min_agentes"));
		this.max_tespera = Double.parseDouble(raiz.getAttribute("max_tespera"));
		this.min_tespera = Double.parseDouble(raiz.getAttribute("min_tespera"));
		this.atenuacion_rumor = Double.parseDouble(raiz.getAttribute("atenuacion_rumor"));
		this.ratio_respawn = Double.parseDouble(raiz.getAttribute("ratio_respawn"));
		this.tespera_distrib = raiz.getAttribute("tespera_distrib");
		
		// Generamos las distribuciones de probabilidad
		distribuciones = generaDistribuciones(document);
		// Creamos los parametros sociales y psicologicos de los modelos
		NodeIterator iterator = XPathAPI.selectNodeIterator(document, "//comportamientos/comportamiento");
		Element actual;
		numAgentes = parametros.getInt(ParametrosAgentes.Parametro.NUM_AGENTES);
		int tiempo = parametros.getInt(ParametrosAgentes.Parametro.TIEMPO_ESPERA);
		normalTEspera = new Normal(tiempo, tiempo / 4.0, random);
		numAgentesComportamiento = new Hashtable<String,Integer>();
		porcentajesComportamientos = new Hashtable<String,Double>();
		classesComportamientos = new Hashtable<String,Class>();
		psiComportamientos = new Hashtable<String,String>();
		socComportamientos = new Hashtable<String,String>();
		
		// Vamos generando por cada tipo de agentes
		while ((actual = (Element) iterator.nextNode()) != null) {
			String identificador = actual.getAttribute("id");
			double porcentaje = Double.parseDouble(actual.getAttribute("porcentaje"))/100;
			int nAgentesCreacion = (int) (numAgentes * porcentaje);
			Class tipoComportamiento = Class.forName(actual.getAttribute("tipo_comportamiento"));
			String social = actual.getAttribute("modelo_social");
			String psicologico = actual.getAttribute("modelo_psicologico");
			
			numAgentesComportamiento.put(identificador,nAgentesCreacion);
			porcentajesComportamientos.put(identificador, porcentaje);
			classesComportamientos.put(identificador, tipoComportamiento);
			psiComportamientos.put(identificador, psicologico);
			socComportamientos.put(identificador, social);
			
			for (int i = 0; i < nAgentesCreacion; i++) {
				ParametrosSocial ps = generaParametrosSocial(document, social, distribuciones);
				ParametrosPsicologicos pp = generaParametrosPsicologico(document, psicologico, distribuciones);
				int tEspera = normalTEspera.nextInt();
				pp.setParametro(ParametrosPsicologicos.Parametro.TIEMPO_ESPERA.toString(), Integer.toString(tEspera));
				ComportamientoAgente c = (ComportamientoAgente) tipoComportamiento.newInstance();
				c.setIdentificador(identificador);
				procesaSubComportamientos(document, identificador, c);
				Agente agente = new Agente(conexionBolsa, estado, consolaAgentes, modeloTabla, ps, pp);
				agente.addComportamiento(c);
				//double dineroInicial = java.lang.Double.parseDouble(modelo.getDineroInicial());
				agentes.add(agente);
				notif.addListener(agente.getIDString(), agente.getPerceptor());
				// System.out.println(agente.getIDString()+" "+agente.getModeloPsicologico().getTiempoEspera());
				agente.start();
			}
		}

		// Rellenamos las matrices de conocidos
		for (Agente agente : agentes) {
			for (int i = 0; i < agente.getModeloSocial().getNumConocidos(); i++) {
				int r = (int) (Math.random()) * agentes.size();
				agente.getModeloSocial().addConocido(agentes.get(r).getIDAgente());
			}
		}
		return agentes;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  d                 Description of Parameter
	 *@param  idComportamiento  Description of Parameter
	 *@param  c                 Description of Parameter
	 *@exception  Exception     Description of Exception
	 */
	private void procesaSubComportamientos(Document d, String idComportamiento, ComportamientoAgente c) throws Exception {
		NodeIterator iterator = XPathAPI.selectNodeIterator(d, "//comportamiento[@id='" + idComportamiento + "']/comportamiento");
		Element e;
		while ((e = (Element) iterator.nextNode()) != null) {
			ComportamientoAgente cNuevo = (ComportamientoAgente) Class.forName(e.getAttribute("tipo_comportamiento")).newInstance();
			procesaSubComportamientos(d, e.getAttribute("id"), cNuevo);
			c.addComportamiento(cNuevo);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  d               Description of Parameter
	 *@param  id              Description of Parameter
	 *@param  distribuciones  Description of Parameter
	 *@return                 Description of the Returned Value
	 *@exception  Exception   Description of Exception
	 */
	private ParametrosPsicologicos generaParametrosPsicologico(Document d, String id, Hashtable<String, AbstractDistribution> distribuciones) throws Exception {
		ParametrosPsicologicos pPsicologicos = new ParametrosPsicologicos();
		NodeIterator iterator = XPathAPI.selectNodeIterator(d, "//modelo_psicologico[@id='" + id + "']/parametro_modelo");

		Element actual;
		while ((actual = (Element) iterator.nextNode()) != null) {
			String p = actual.getAttribute("tipo").toUpperCase();
			Element valor = (Element) actual.getChildNodes().item(1);

			if (valor.getNodeName().equals("distribucion")) {
				String clave = valor.getTextContent();
				AbstractDistribution distribucion = distribuciones.get(clave);
				double vDist = distribucion.nextDouble();
				pPsicologicos.setParametro(p, Double.toString(vDist));
			}
			else if (valor.getNodeName().equals("valor")) {
				pPsicologicos.setParametro(p, valor.getTextContent());
			}
		}
		return pPsicologicos;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  d               Description of Parameter
	 *@param  id              Description of Parameter
	 *@param  distribuciones  Description of Parameter
	 *@return                 Description of the Returned Value
	 *@exception  Exception   Description of Exception
	 */
	private ParametrosSocial generaParametrosSocial(Document d, String id, Hashtable<String, AbstractDistribution> distribuciones) throws Exception {
		ParametrosSocial pSocial = new ParametrosSocial();
		NodeIterator iterator = XPathAPI.selectNodeIterator(d, "//modelo_social[@id='" + id + "']/parametro_modelo");

		Element actual;
		while ((actual = (Element) iterator.nextNode()) != null) {
			String p = actual.getAttribute("tipo").toUpperCase();
			Element valor = (Element) actual.getChildNodes().item(1);

			if (valor.getNodeName().equals("distribucion")) {
				String clave = valor.getTextContent();
				AbstractDistribution distribucion = distribuciones.get(clave);
				double vDist = distribucion.nextDouble();
				pSocial.setParametro(p, Double.toString(vDist));
			}
			else if (valor.getNodeName().equals("valor")) {
				pSocial.setParametro(p, valor.getTextContent());
			}
		}
		return pSocial;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  d              Description of Parameter
	 *@return                Description of the Returned Value
	 *@exception  Exception  Description of Exception
	 */
	private Hashtable<String, AbstractDistribution> generaDistribuciones(Document d) throws Exception {
		Hashtable<String, AbstractDistribution> t = new Hashtable<String, AbstractDistribution>();
		// Cogemos las normales
		NodeIterator iterator = XPathAPI.selectNodeIterator(d, "//dist_normal");
		Element actual;

		while ((actual = (Element) iterator.nextNode()) != null) {
			String clave = actual.getAttribute("id");
			double media = Double.parseDouble(actual.getAttribute("media"));
			double desvTipica = Double.parseDouble(actual.getAttribute("desviacion_tipica"));
			Normal normal = new Normal(media, desvTipica, random);
			t.put(clave, normal);
		}

		// Cogemos las poisson
		iterator = XPathAPI.selectNodeIterator(d, "//dist_poisson");

		while ((actual = (Element) iterator.nextNode()) != null) {
			String clave = actual.getAttribute("id");
			double lambda = Double.parseDouble(actual.getAttribute("lambda"));
			Poisson poisson = new Poisson(lambda, random);
			t.put(clave, poisson);
		}

		// Cogemos las uniformes
		iterator = XPathAPI.selectNodeIterator(d, "//dist_uniforme");

		while ((actual = (Element) iterator.nextNode()) != null) {
			String clave = actual.getAttribute("id");
			double minimo = Double.parseDouble(actual.getAttribute("minimo"));
			double maximo = Double.parseDouble(actual.getAttribute("maximo"));
			Uniform uniforme = new Uniform(minimo, maximo, random);
			t.put(clave, uniforme);
		}
		return t;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  fichero        Description of Parameter
	 *@return                Description of the Returned Value
	 *@exception  Exception  Description of Exception
	 */
	private static Document inicializaDOM(String fichero) throws Exception {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return db.parse(new File(fichero));
	}


	public double getMaxAgentes() {
		return max_agentes;
	}


	public double getMinAgentes() {
		return min_agentes;
	}


	public double getMinTEspera() {
		return min_tespera;
	}


	public double getMaxTEspera() {
		return max_tespera;
	}


	public String getDistribucionEspera() {
		return tespera_distrib;
	}


	public double getMaxGasto() {
		return max_gasto;
	}


	public double getRatioRewspanwn() {
		return ratio_respawn;
	}


	public double getAtenuacionRumor() {
		return atenuacion_rumor;
	}

	public Agente nuevoAgente() {
		Enumeration<String> claves = classesComportamientos.keys();
		numAgentes++;
		Agente agente = null;
		
		double min = Double.MAX_VALUE;
		String minId = null;
		while(claves.hasMoreElements()){
			String id = claves.nextElement();
			int num = numAgentesComportamiento.get(id);
			double porcentaje = porcentajesComportamientos.get(id);
			// Buscamos el que tiene mayor diferencia
			double actual = (((double)numAgentes)/((double)num))-porcentaje ;
			if(actual < min){
				min = actual;
				minId = id;
			}
		}
		System.err.println("Nuevo agente, comportamiento: " + minId);
		ParametrosPsicologicos pp;
		ParametrosSocial ps;
		try {
			pp = generaParametrosPsicologico(document, psiComportamientos.get(minId), distribuciones);
			ps = generaParametrosSocial(document, socComportamientos.get(minId), distribuciones);
			int tEspera = normalTEspera.nextInt();
			pp.setParametro(ParametrosPsicologicos.Parametro.TIEMPO_ESPERA.toString(), Integer.toString(tEspera));
			ComportamientoAgente c = (ComportamientoAgente) classesComportamientos.get(minId).newInstance();
			c.setIdentificador(minId);
			procesaSubComportamientos(document, minId, c);
			agente = new Agente(
					conexion, estadoBolsa, consolaAgentes, modeloTabla,
					ps, pp);
			agente.addComportamiento(c);
			notif.addListener(agente.getIDString(), agente.getPerceptor());
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return agente;
	}
}
