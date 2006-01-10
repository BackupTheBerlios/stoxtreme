package interfaz_remota;

import java.util.Date;
import java.util.Vector;

import servidor.objeto_bolsa.informacion.Balance;
import servidor.objeto_bolsa.informacion.Cuenta;
import servidor.objeto_bolsa.informacion.InfoBursatil;

public interface IInformacion {
	// Datos de los recursos de la empresa
	public Balance getBalanceActual();
	public Vector getBalances(Date inicio, Date fin);
	public void setBalance(Balance b, Date fecha);
	// Datos financieros (beneficios y gastos) de la empresa
	public Vector getCuentas(Date inicio, Date fin);
	public void setCuenta(Cuenta c, Date fecha);
	// Participaciones, ampliaciones de capital, etc...
	public InfoBursatil getDatosBursatiles();
	public void setInfoBursatil(InfoBursatil i);
	// Valoraci�n de como se va a comportar la empresa en el futuro
	// Util para los eventos, etc.
	public String getPerspectivaFuturo();
	public void setPerspectivaFuturo(String s);
}
