package stoxtreme.cliente.gui;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
/**
 *  Recoge los datos hist�ricos de una empresa
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public class ValoresHistoricos extends TimeSeries {
	private ArrayList volumenesSesion;
	private Date fecha;


	/**
	 *  Constructor for the ValoresHistoricos object
	 *
	 *@param  empresa  Nombre de la empresa
	 *@param  fecha    Fecha de inicio
	 */
	public ValoresHistoricos(String empresa, Date fecha) {
		super(empresa);
		volumenesSesion = new ArrayList();
		timePeriodClass = Minute.class;
		this.fecha = fecha;
	}


	/**
	 *  Inserta un nuevo valor
	 *
	 *@param  valor  Volumen introducido
	 */
	public void insertarSiguienteValor(double valor) {
		volumenesSesion.add(new Double(valor));
	}
}
