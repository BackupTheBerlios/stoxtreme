package stoxtreme.cliente.gui;

import java.awt.*;
import javax.swing.*;

public class PrecioGraficoMoviendose {
	static private final int y = 20;
	static private final Font FUENTE = new Font("Arial", Font.BOLD, 15);
	static private final int VELOCIDAD = 5;
	
	private String empresa;
	private double precio;
	private int x;
	private JPanel panel;
	
	private int ts1, ts2, hs, anchoTriangulo;
	private int[] xpoints;
	private int[] ypoints;

	public PrecioGraficoMoviendose(String empresa, double precio, JPanel panel){
		this.empresa = empresa;
		this.precio = precio;
		this.panel = panel;
		this.x = panel.getWidth();
		
		FontMetrics fm = panel.getFontMetrics(FUENTE);
		String valor_str = Double.toString(precio);
		ts1 = fm.stringWidth(empresa);
		ts2 = fm.stringWidth(valor_str);
		hs = fm.getHeight()-10;

		anchoTriangulo = (int)Math.sqrt((4.0/3.0)*(hs*hs));
		
		xpoints = new int[3];
		ypoints = new int[3];
		
		
	}	

	public void paint(Graphics g){
		xpoints[0] = x+ts1+4;
		xpoints[1] = x+ts1+4 + anchoTriangulo;
		xpoints[2] = x+ts1+4 + anchoTriangulo/2;
		
		if(precio > 0.0){
			g.setColor(Color.GREEN);
			ypoints[0] = ypoints[1] = y;
			ypoints[2] = y-hs;
		}
		else{
			g.setColor(Color.RED);
			ypoints[0] = ypoints[1] = y-hs;
			ypoints[2] = y;
		}
		
		g.setFont(FUENTE);
		g.drawString(empresa, x, y);
		g.fillPolygon(xpoints, ypoints, 3);
		g.drawString(Double.toString(precio), x+ts1+anchoTriangulo+6, y);
		
		x-=VELOCIDAD;
	}
	
	public boolean dentro(){
		return (x+ts1+ts2+anchoTriangulo+10)<panel.getWidth();
	}
	
	public boolean fuera(){
		return (x+ts1+ts2+anchoTriangulo+10)<0;
	}
}
