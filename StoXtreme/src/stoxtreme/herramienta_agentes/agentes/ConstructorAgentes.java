package stoxtreme.herramienta_agentes.agentes;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.cliente.gui.HerramientaAgentesTableModel;
import stoxtreme.herramienta_agentes.ConsolaAgentes;
import stoxtreme.herramienta_agentes.Notificador;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.interfaz_remota.Stoxtreme;

import cern.jet.random.AbstractDistribution;
import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;

import com.sun.org.apache.xpath.internal.XPathAPI;

public class ConstructorAgentes {
	public ArrayList<Agente> construyeAgentes(
			Stoxtreme conexionBolsa, 
			EstadoBolsa estado, 
			ConsolaAgentes consolaAgentes,
			Notificador notif, HerramientaAgentesTableModel modeloTabla, String fichero,
			int numAgentes
	) throws Exception{
		ArrayList<Agente> agentes = new ArrayList<Agente>();
		// Parseamos el fichero
		Document fichAgentes = inicializaDOM(fichero);
		// Primero generamos las distribuciones de probabilidad
		Hashtable<String,AbstractDistribution> distribuciones = generaDistribuciones(fichAgentes);
		// Creamos los parametros sociales y psicologicos de los modelos
		
		NodeIterator iterator = XPathAPI.selectNodeIterator(fichAgentes, "//comportamientos/comportamiento");
		
		Element actual;
		// Vamos generando por cada tipo de agentes
		while((actual=(Element)iterator.nextNode())!=null){
			int nAgentesCreacion = (int) (numAgentes * (Double.parseDouble(actual.getAttribute("porcentaje"))/100));
			for(int i=0; i<nAgentesCreacion; i++){
				ParametrosSocial ps = generaParametrosSocial(fichAgentes, actual.getAttribute("modelo_social"), distribuciones);
				ParametrosPsicologicos  pp = generaParametrosPsicologico(fichAgentes, actual.getAttribute("modelo_psicologico"), distribuciones);
				ComportamientoAgente c = (ComportamientoAgente)Class.forName(actual.getAttribute("tipo_comportamiento")).newInstance();
				procesaSubComportamientos(fichAgentes, actual.getAttribute("id"), c);
				Agente agente = new Agente(conexionBolsa,estado,consolaAgentes,modeloTabla,ps, pp);
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
			for(int i=0; i<agente.getModeloSocial().getNumConocidos(); i++){
				int r = (int)(Math.random())*agentes.size();
				agente.getModeloSocial().addConocido(agentes.get(r).getIDAgente());
			}
		}
		return agentes;
	}

	private void procesaSubComportamientos(Document d, String idComportamiento, ComportamientoAgente c) throws Exception {
		NodeIterator iterator = XPathAPI.selectNodeIterator(d, "//comportamiento[@id='"+idComportamiento+"']/comportamiento");
		Element e;
		while((e=(Element)iterator.nextNode())!=null){
			ComportamientoAgente cNuevo = (ComportamientoAgente)Class.forName(e.getAttribute("tipo_comportamiento")).newInstance();
			procesaSubComportamientos(d, e.getAttribute("id"), cNuevo);
			c.addComportamiento(cNuevo);
		}
	}
	
	private ParametrosPsicologicos generaParametrosPsicologico(Document d, String id, Hashtable<String, AbstractDistribution> distribuciones) throws Exception{
		ParametrosPsicologicos pPsicologicos = new ParametrosPsicologicos();
		NodeIterator iterator = XPathAPI.selectNodeIterator(d,"//modelo_psicologico[@id='"+id+"']/parametro_modelo");
		
		Element actual; 
		while((actual=(Element)iterator.nextNode())!=null){
			String p = actual.getAttribute("tipo").toUpperCase();
			Element valor = (Element)actual.getChildNodes().item(1);
			
			if(valor.getNodeName().equals("distribucion")){
				String clave = valor.getTextContent();
				AbstractDistribution distribucion = distribuciones.get(clave);
				double vDist = distribucion.nextDouble();
				pPsicologicos.setParametro(p, Double.toString(vDist));
			}
			else if(valor.getNodeName().equals("valor")){
				pPsicologicos.setParametro(p, valor.getTextContent());
			}
		}
		return pPsicologicos;
	}
	
	private ParametrosSocial generaParametrosSocial(Document d, String id, Hashtable<String, AbstractDistribution> distribuciones) throws Exception{
		ParametrosSocial pSocial = new ParametrosSocial();
		NodeIterator iterator = XPathAPI.selectNodeIterator(d,"//modelo_social[@id='"+id+"']/parametro_modelo");
		
		Element actual; 
		while((actual=(Element)iterator.nextNode())!=null){
			String p = actual.getAttribute("tipo").toUpperCase();
			Element valor = (Element)actual.getChildNodes().item(1);
			
			if(valor.getNodeName().equals("distribucion")){
				String clave = valor.getTextContent();
				AbstractDistribution distribucion = distribuciones.get(clave);
				double vDist = distribucion.nextDouble();
				pSocial.setParametro(p, Double.toString(vDist));
			}
			else if(valor.getNodeName().equals("valor")){
				pSocial.setParametro(p, valor.getTextContent());
			}
		}
		return pSocial;
	}

	private Hashtable<String, AbstractDistribution> generaDistribuciones(Document d)throws Exception{
		Hashtable<String, AbstractDistribution> t = new Hashtable<String, AbstractDistribution>();
		// Cogemos las uniformes
		NodeIterator iterator = XPathAPI.selectNodeIterator(d, "//dist_uniforme");
		Element actual;
		
		while((actual = (Element)iterator.nextNode())!=null){
			String clave = actual.getAttribute("id");
			double media = Double.parseDouble(actual.getAttribute("media"));
			double desvTipica = Double.parseDouble(actual.getAttribute("desviacion_tipica"));
			Normal normal = new Normal(media, desvTipica, new MersenneTwister(new Date()));
			t.put(clave, normal);
		}
		return t;
	}

	private Document inicializaDOM(String fichero)throws Exception{
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return db.parse(new File(fichero));
	}
	
//	public Agente crearNuevoAgente(ModeloBolsa modelo, String fichero, int numAgentes) throws Exception{
//		
//	}
}
