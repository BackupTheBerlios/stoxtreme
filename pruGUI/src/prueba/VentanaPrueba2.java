package prueba;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;

import org.jfree.chart.*;
import org.jfree.data.*;
import org.jfree.data.xy.*;
import org.jfree.data.category.*;
import org.jfree.chart.plot.*;

public class VentanaPrueba2 extends JInternalFrame{
	public void jbInit(){
		setSize(400, 400);
		setVisible(true);
		setResizable(true);
		setTitle("Ventana Prueba 2");
		setClosable(true);
		setMaximizable(true);

		XYSeries  serie1 = new XYSeries("Prueba 1");
		for (int i=1; i< 40; i+=1) serie1.add(i, i);

		XYSeries serie2 = new XYSeries("Prueba 2");
		for (int i=1; i< 40; i+=1) serie2.add(i, i+3);
		
		XYSeriesCollection datos = new XYSeriesCollection();
		datos.addSeries(serie1);
		datos.addSeries(serie2);
		
		JFreeChart chart = 
			ChartFactory.createXYLineChart("","Meses","Sesiones", datos,	PlotOrientation.VERTICAL,false, false, true);

		ChartPanel panel = new ChartPanel(chart);
		panel.setDoubleBuffered(false);
		getContentPane().add(panel);
	}
}
