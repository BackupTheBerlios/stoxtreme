package interfaz_remota;

import java.util.Date;
import java.util.Vector;
import servidor.objeto_bolsa.informacion.Balance;
import servidor.objeto_bolsa.informacion.Cuenta;
import servidor.objeto_bolsa.informacion.InfoBursatil;

/**
 * @author  usuario_local
 */
public interface IInformacion {
	// Datos de los recursos de la empresa
	/**
	 * @uml.property  name="balanceActual"
	 * @uml.associationEnd  
	 */
	public Balance getBalanceActual();
	public Vector getBalances(Date inicio, Date fin);
	public void setBalance(Balance b, Date fecha);
	// Datos financieros (beneficios y gastos) de la empresa
	public Vector getCuentas(Date inicio, Date fin);
	public void setCuenta(Cuenta c, Date fecha);
	// Participaciones, ampliaciones de capital, etc...
	/**
	 * @uml.property  name="datosBursatiles"
	 * @uml.associationEnd  
	 */
	public InfoBursatil getDatosBursatiles();
	public void setInfoBursatil(InfoBursatil i);
	// Valoraci�n de como se va a comportar la empresa en el futuro
	// Util para los eventos, etc.
	/**
	 * @uml.property  name="perspectivaFuturo"
	 */
	public String getPerspectivaFuturo();
	/**
	 * @param perspectivaFuturo  The perspectivaFuturo to set.
	 * @uml.property  name="perspectivaFuturo"
	 */
	public void setPerspectivaFuturo(String s);
}
