package servidor.objeto_bolsa.informacion.infoXML;

import java.util.Date;
import java.util.Vector;

import servidor.objeto_bolsa.informacion.*;

public class InformacionXML implements IInformacion{
	// Actuará como un monitor de lectores escritores, creando inicialmente el hilo que escribirá
	// Toda la información concurrentemente y que comprobará si ha habido cambios o quizas mejor
	// mediante un metodo de UPDATE, los lectores leeran eventualmente la información que necesiten.
	public InformacionXML(String archivo){
		
	}

	public Balance getBalanceActual() {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector getBalances(Date inicio, Date fin) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setBalance(Balance b, Date fecha) {
		// TODO Auto-generated method stub
		
	}

	public Vector getCuentas(Date inicio, Date fin) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCuenta(Cuenta c, Date fecha) {
		// TODO Auto-generated method stub
		
	}

	public InfoBursatil getDatosBursatiles() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInfoBursatil(InfoBursatil i) {
		// TODO Auto-generated method stub
		
	}

	public String getPerspectivaFuturo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPerspectivaFuturo(String s) {
		// TODO Auto-generated method stub
		
	}
}
