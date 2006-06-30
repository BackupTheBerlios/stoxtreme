package stoxtreme.cliente.gui;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ValoresHistoricos extends TimeSeries {
	private ArrayList volumenesSesion;
	private Date fecha;


	/**
	 *  Constructor for the ValoresHistoricos object
	 *
	 *@param  empresa  Description of Parameter
	 *@param  fecha    Description of Parameter
	 */
	public ValoresHistoricos(String empresa, Date fecha) {
		super(empresa);
		volumenesSesion = new ArrayList();
		timePeriodClass = Minute.class;
		this.fecha = fecha;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  valor  Description of Parameter
	 */
	public void insertarSiguienteValor(double valor) {
		volumenesSesion.add(new Double(valor));
	}
}
