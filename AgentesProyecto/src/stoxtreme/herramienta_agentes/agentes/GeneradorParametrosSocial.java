package stoxtreme.herramienta_agentes.agentes;

import java.util.ArrayList;
import java.util.Iterator;

import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;

public class GeneradorParametrosSocial {
	private ArrayList<ParametrosSocial> lista;
	
	public GeneradorParametrosSocial(int numAgentes) {
		lista = new ArrayList<ParametrosSocial>(numAgentes);

		for(int i=0; i<numAgentes; i++){
			lista.add(i, new ParametrosSocial());
		}
	}

	public ParametrosSocial get(int i) {
		return lista.get(i);
	}
}
