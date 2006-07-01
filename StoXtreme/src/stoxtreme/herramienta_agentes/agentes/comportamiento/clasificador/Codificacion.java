package stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador;
import java.util.Date;

import cern.jet.random.engine.MersenneTwister;

public class Codificacion {
	private static int NUMBITS = 10;
	private static MersenneTwister random = new MersenneTwister(new Date());
	private char[] cadena;
	
	public Codificacion() {
		cadena = new char[NUMBITS];
	}
	
	public String toString(){
		String s = "[";
		for(int i=0; i<cadena.length; i++){
			s+=cadena[i];
		}
		s+="]";
		return s;
	}
	
	public void rellenaAleatorio(){
		for(int i=0; i<cadena.length; i++){
			int r = (int)(random.nextDouble()*3.0);
			switch(r){
				case 0: cadena[i] = '0'; break;
				case 1: cadena[i] = '1'; break;
				case 2: cadena[i] = '#'; break;
			}
		}
	}
	
	public void cambiaCaracter(int index) {
		switch(cadena[index]){
		case '0': case '1': cadena[index] = '#';
		case '#': 
			int r = (int)(random.nextDouble()*2.0);
			cadena[index] = (r==0)?'0':'1';
		}
	}

	public int size() {
		return cadena.length;
	}

	public char getCharAt(int i) {
		return cadena[i];
	}
}
