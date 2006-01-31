package stoxtreme.servidor.objeto_bolsa.informacion;

import stoxtreme.interfaz_remota.IInformacion;

public class Informacion {
	private Balance balance;
	private InfoBursatil iBursatil;
	private Cuenta cuenta;
	
	public Informacion(Balance b, InfoBursatil ib, Cuenta c){
		balance=b;
		iBursatil=ib;
		cuenta=c;
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
