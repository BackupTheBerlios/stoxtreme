package stoxtreme.cliente.gui;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
public class ValoresHistoricos extends TimeSeries{
	private ArrayList volumenesSesion;
	private Date fecha;
	
	public ValoresHistoricos(String empresa, Date fecha){
		super(empresa);
		volumenesSesion = new ArrayList();
		timePeriodClass = Minute.class;
		this.fecha=fecha;
	}
	
	public void insertarSiguienteValor(int valor){
		volumenesSesion.add(new Integer(valor));
	}
}
