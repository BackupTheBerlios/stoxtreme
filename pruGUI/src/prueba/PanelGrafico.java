package prueba;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

import org.jfree.chart.*;
import org.jfree.data.*;
import org.jfree.data.xy.*;
import org.jfree.data.category.*;
import org.jfree.chart.plot.*;

public class PanelGrafico extends JPanel{
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(crearImagen(), 0, 0, null);
	}
	
	public BufferedImage crearImagen(){
		XYSeries  serie1 = new XYSeries("Prueba 1");
		for (int i=1; i< 40; i+=1) serie1.add(i, i);

		XYSeries serie2 = new XYSeries("Prueba 2");
		for (int i=1; i< 40; i+=1) serie2.add(i, i+3);
		
		XYSeriesCollection datos = new XYSeriesCollection();
		datos.addSeries(serie1);
		datos.addSeries(serie2);
		
		JFreeChart chart = 
			ChartFactory.createXYLineChart(
					"Prueba", 
					"Meses", 
					"Sesiones", 
					datos, 
					PlotOrientation.VERTICAL,
					false, false, true);
		BufferedImage bi = chart.createBufferedImage(300,300);
		return bi;
	}
}
