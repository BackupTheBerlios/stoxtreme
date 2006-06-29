package stoxtreme.cliente.gui;

import javax.swing.*;

import stoxtreme.cliente.EstadoBolsa;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class PanelCotizaciones extends JPanel implements ActionListener{

	
	ArrayList<String> empresas;
	private double[] cotizaciones;
	
	private ArrayList<PrecioGraficoMoviendose> listaMovimientos;
	private int siguienteIndice;
	
	private EstadoBolsa bolsa;
	
	public PanelCotizaciones(EstadoBolsa bolsa){
		setPreferredSize(new Dimension(getWidth(), 30));
		empresas = bolsa.getEmpresas();
		this.bolsa = bolsa;
		cotizaciones = new double[empresas.size()];
		
		for(int i=0; i<empresas.size(); i++){
			cotizaciones[i] = 0.0;
		}
		
		siguienteIndice = 1;
		if(empresas.size() == 1){
			siguienteIndice = 0;
		}
		listaMovimientos = new ArrayList<PrecioGraficoMoviendose>();
		
		Timer timer = new Timer(100,this);
		timer.start();
	}

	public void paint(Graphics g){
		super.paint(g);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(listaMovimientos.size()==0){
			listaMovimientos.add(new PrecioGraficoMoviendose(empresas.get(0), bolsa.getPorcentajeDiferencia(empresas.get(0)), this));
		}
		
		for (int i=listaMovimientos.size()-1; i>=0; i--){
			PrecioGraficoMoviendose etiqueta = (PrecioGraficoMoviendose)listaMovimientos.get(i);
			etiqueta.paint(g);
			
			// Ultimo indice
			if(i==listaMovimientos.size()-1 && etiqueta.dentro()){
				listaMovimientos.add(new PrecioGraficoMoviendose(empresas.get(siguienteIndice), bolsa.getPorcentajeDiferencia(empresas.get(siguienteIndice)), this));
				siguienteIndice = (siguienteIndice+1)%empresas.size();
			}
			
			// Primer indice
			if(i==0 && etiqueta.fuera()){
				listaMovimientos.remove(0);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		this.repaint();
	}
}

