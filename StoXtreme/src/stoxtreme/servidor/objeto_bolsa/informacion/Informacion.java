package stoxtreme.servidor.objeto_bolsa.informacion;

import java.util.Hashtable;
import java.util.Enumeration;

import stoxtreme.interfaz_remota.IInformacion;

public class Informacion {
	private Balance balance;
	private InfoBursatil iBursatil;
	private Cuenta cuenta;
	
	public Informacion(Balance b, InfoBursatil ib, Cuenta c){
		//balance=b;
		iBursatil=ib;
		Hashtable ht=ib.getParticipaciones();
		Enumeration e=ht.keys();
		while (e.hasMoreElements()){
			String i=e.nextElement().toString();
			System.out.println("Accionista: "+i+". Porcentaje: "+ht.get(i)+"%");
		}
		//cuenta=c;
	}
	
	public Balance getBalance(){
		return balance;
	}
	
	public InfoBursatil getIBursatil(){
		return iBursatil;
	}
	public Cuenta getCuenta(){
		return cuenta;
	}
}
