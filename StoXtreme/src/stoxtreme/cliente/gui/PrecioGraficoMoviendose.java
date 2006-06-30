package stoxtreme.cliente.gui;

import java.awt.*;
import javax.swing.*;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class PrecioGraficoMoviendose {

	private String empresa;
	private double precio;
	private int x;
	private JPanel panel;

	private int ts1, ts2, hs, anchoTriangulo, anchoIgual;
	private int[] xpoints;
	private int[] ypoints;
	private static final int y = 20;
	private static final Font FUENTE = new Font("Arial", Font.BOLD, 15);
	private static final int VELOCIDAD = 5;


	/**
	 *  Constructor for the PrecioGraficoMoviendose object
	 *
	 *@param  empresa  Description of Parameter
	 *@param  precio   Description of Parameter
	 *@param  panel    Description of Parameter
	 */
	public PrecioGraficoMoviendose(String empresa, double precio, JPanel panel) {
		this.empresa = empresa;
		this.precio = precio;
		this.panel = panel;
		this.x = panel.getWidth();

		FontMetrics fm = panel.getFontMetrics(FUENTE);
		String valor_str = Double.toString(precio);
		ts1 = fm.stringWidth(empresa);
		ts2 = fm.stringWidth(valor_str);
		hs = fm.getHeight() - 10;
		anchoIgual = fm.stringWidth("=");

		anchoTriangulo = (int) Math.sqrt((4.0 / 3.0) * (hs * hs));

		xpoints = new int[3];
		ypoints = new int[3];

	}


	/**
	 *  Description of the Method
	 *
	 *@param  g  Description of Parameter
	 */
	public void paint(Graphics g) {
		xpoints[0] = x + ts1 + 4;
		xpoints[1] = x + ts1 + 4 + anchoTriangulo;
		xpoints[2] = x + ts1 + 4 + anchoTriangulo / 2;

		if (precio > 0.0) {
			g.setColor(Color.GREEN);
			ypoints[0] = ypoints[1] = y;
			ypoints[2] = y - hs;
		}
		else if (precio < 0.0) {
			g.setColor(Color.RED);
			ypoints[0] = ypoints[1] = y - hs;
			ypoints[2] = y;
		}
		else {
			g.setColor(Color.WHITE);
		}

		g.setFont(FUENTE);
		g.drawString(empresa, x, y);

		if (precio != 0.0) {
			g.fillPolygon(xpoints, ypoints, 3);
			g.drawString(Double.toString(precio), x + ts1 + anchoTriangulo + 6, y);
		}
		else {
			g.drawString("=", x + ts1 + 4, y);
			g.drawString(Double.toString(precio), x + ts1 + anchoIgual + 6, y);
		}
		x -= VELOCIDAD;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public boolean dentro() {
		return (x + ts1 + ts2 + anchoTriangulo + 10) < panel.getWidth();
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public boolean fuera() {
		return (x + ts1 + ts2 + anchoTriangulo + 10) < 0;
	}
}
