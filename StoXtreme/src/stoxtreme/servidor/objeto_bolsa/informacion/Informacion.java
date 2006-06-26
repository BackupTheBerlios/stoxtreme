package stoxtreme.servidor.objeto_bolsa.informacion;

public class Informacion {
	private InfoBursatil iBursatil;
		
	public Informacion(InfoBursatil ib){
		//balance=b;
		iBursatil=ib;
		/*Hashtable ht=ib.getParticipaciones();
		Enumeration e=ht.keys();
		while (e.hasMoreElements()){
			String i=e.nextElement().toString();
			System.out.println("Accionista: "+i+". Porcentaje: "+ht.get(i)+"%");
		}*/
		//cuenta=c;
	}
	
		public InfoBursatil getIBursatil(){
		return iBursatil;
	}
	}
