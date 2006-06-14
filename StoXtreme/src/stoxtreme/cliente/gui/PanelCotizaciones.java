package stoxtreme.cliente.gui;

import javax.swing.*;

import sun.security.krb5.internal.crypto.g;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

public class PanelCotizaciones extends JPanel implements ActionListener{
	private static final String empresas[] = 
	{	"ANTENA 3","ABERTIS","ACS","ACERINOX","ALTADIS","ACCIONA","BBVA","BANKINTER","CINTRA",
		"ENDESA","ENAGAS","FCC","GRUPO FERROVIAL","GAMESA","GAS NATURAL","IBERDROLA","IBERIA",
		"INDRA SISTEMAS","INDITEX","ARCELOR","CORP MAPFRE","METROVACESA","BANCO POPULAR","PRISA",
		"RED ELECTRICA ESP","REPSOL YPF","BANCO SABADELL","BSCH","SOGECABLE","SACYR VALLEHERMOSO",
		"TELEFONICA","TELEF.MOVILES","TELECINCO","TPI","UNION FENOSA"
	};
	
	private double[] cotizaciones;
	
	private ArrayList listaMovimientos;
	private int siguienteIndice;
	
	public PanelCotizaciones(){
		setPreferredSize(new Dimension(getWidth(), 30));
		cotizaciones = new double[empresas.length];
		
		for(int i=0; i<empresas.length; i++){
			if(i%2 == 0)
				cotizaciones[i] = 0.12;
			else
				cotizaciones[i] = -0.12;
		}
		
		siguienteIndice = 1;
		listaMovimientos = new ArrayList();
		
		
		Timer timer = new Timer(100,this);
		timer.start();
	}

	public void paint(Graphics g){
		super.paint(g);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(listaMovimientos.size()==0){
			listaMovimientos.add(new PrecioGraficoMoviendose(empresas[0], cotizaciones[0], this));
		}
		
		for (int i=listaMovimientos.size()-1; i>=0; i--){
			PrecioGraficoMoviendose etiqueta = (PrecioGraficoMoviendose)listaMovimientos.get(i);
			etiqueta.paint(g);
			
			// Ultimo indice
			if(i==listaMovimientos.size()-1 && etiqueta.dentro()){
				listaMovimientos.add(new PrecioGraficoMoviendose(empresas[siguienteIndice], cotizaciones[siguienteIndice], this));
				siguienteIndice = (siguienteIndice+1)%empresas.length;
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

