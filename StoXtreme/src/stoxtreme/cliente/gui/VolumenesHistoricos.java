package stoxtreme.cliente.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *  Description of the Class
 *
 *@author   Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class VolumenesHistoricos extends TimeSeries {
	private ArrayList preciosSesion;
	private Calendar c;


	/**
	 *  Constructor for the VolumenesHistoricos object
	 *
	 *@param  empresa     Description of Parameter
	 *@param  cadaCuanto  Description of Parameter
	 */
	public VolumenesHistoricos(String empresa, int cadaCuanto) {
		super(empresa);
		preciosSesion = new ArrayList(cadaCuanto * 540);
		timePeriodClass = Minute.class;
		c = Calendar.getInstance();
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.HOUR, 8);
		c.set(Calendar.MINUTE, 30);

		Calendar c2 = Calendar.getInstance();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  valor  Description of Parameter
	 */
	public void insertarSiguienteValor(double valor) {
		preciosSesion.add(new Double(valor));
		addOrUpdate(new Minute(c.getTime()), valor);
		fireSeriesChanged();
		c.add(Calendar.MINUTE, 1);
	}
}
