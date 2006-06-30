package stoxtreme.servidor.objeto_bolsa.informacion;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Informacion {
	private InfoBursatil iBursatil;


	/**
	 *  Constructor for the Informacion object
	 *
	 *@param  ib  Description of Parameter
	 */
	public Informacion(InfoBursatil ib) {
		//balance=b;
		iBursatil = ib;
		/*
		 *  Hashtable ht=ib.getParticipaciones();
		 *  Enumeration e=ht.keys();
		 *  while (e.hasMoreElements()){
		 *  String i=e.nextElement().toString();
		 *  System.out.println("Accionista: "+i+". Porcentaje: "+ht.get(i)+"%");
		 *  }
		 */
		//cuenta=c;
	}


	/**
	 *  Gets the IBursatil attribute of the Informacion object
	 *
	 *@return    The IBursatil value
	 */
	public InfoBursatil getIBursatil() {
		return iBursatil;
	}
}
