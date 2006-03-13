package stoxtreme.cliente.gui;

import java.util.ArrayList;
import java.util.Calendar;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;

public class ValoresEmpresa extends TimeSeries{
	// La sesi�n de bolsa va desde las 8.30 hasta las 17.30
	// calculando en multiplos de minuto.
	// 9 horas * 60 minutos/hora = 540 minutos/sesion
	private ArrayList preciosSesion;
	private int ultimaPos;
	private Calendar c;
	private double inicioSesion;
	
	public ValoresEmpresa(String empresa, int cadaCuanto){
		super(empresa);
		preciosSesion = new ArrayList(cadaCuanto * 540);
		ultimaPos = 0;
		timePeriodClass = Minute.class;
		c = Calendar.getInstance();
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.HOUR, 8);
		c.set(Calendar.MINUTE, 30);
		
		Calendar c2 = Calendar.getInstance();
	}
	
	public void insertarSiguienteValor(double valor){
		if(preciosSesion.size()==0)
			inicioSesion = valor;
		preciosSesion.add(new Double(valor));
		ultimaPos++;
		addOrUpdate(new Minute(c.getTime()), valor);
		fireSeriesChanged();
		c.add(Calendar.MINUTE, 1);
	}
	
	public double ultimoPrecio(){
		if(preciosSesion.size()>0)
			return ((Double)preciosSesion.get(preciosSesion.size()-1)).doubleValue();
		else
			return 0.0f;
	}
	public boolean isTendenciaAlta(){
		if(preciosSesion.size() > 0){
			return ((Double)preciosSesion.get(preciosSesion.size()-1)).doubleValue() >= inicioSesion;
		}
		else{
			return false;
		}
	}
}
