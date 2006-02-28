package stoxtreme.cliente.GUI;

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
	private float inicioSesion;
	
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
	
	public void insertarSiguienteValor(float valor){
		if(preciosSesion.size()==0)
			inicioSesion = valor;
		preciosSesion.add(new Float(valor));
		ultimaPos++;
		addOrUpdate(new Minute(c.getTime()), valor);
		fireSeriesChanged();
		c.add(Calendar.MINUTE, 1);
	}
	
	public float ultimoPrecio(){
		if(preciosSesion.size()>0)
			return ((Float)preciosSesion.get(preciosSesion.size()-1)).floatValue();
		else
			return 0.0f;
	}
	public boolean isTendenciaAlta(){
		if(preciosSesion.size() > 0){
			return ((Float)preciosSesion.get(preciosSesion.size()-1)).floatValue() >= inicioSesion;
		}
		else{
			return false;
		}
	}
}
