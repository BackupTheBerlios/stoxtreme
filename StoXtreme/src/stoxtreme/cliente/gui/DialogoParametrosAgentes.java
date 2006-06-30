package stoxtreme.cliente.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import stoxtreme.cliente.gui.FakeInternalFrame;
import stoxtreme.cliente.gui.PanelOpciones;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class DialogoParametrosAgentes extends JDialog {
	private PanelOpciones pOpciones;
	private boolean aceptado = false;
	private int nAgentes;
	private int tEjecucion;
	private int tCiclo;
	private static String OP1 = "Numero de agentes";
	private static String OP2 = "Tiempo medio de ejecucion";
	private static String OP3 = "Tiempo de ciclo ( mseg )";


	/**
	 *  Constructor for the DialogoParametrosAgentes object
	 *
	 *@param  frame  Description of Parameter
	 */
	public DialogoParametrosAgentes(JFrame frame) {
		super(frame, "Insertar parametros", true);
		try {
			init();
			pack();
			setModal(true);
			setSize(new Dimension(300, this.getHeight()));
			setLocation(
					frame.getLocationOnScreen().x + (frame.getWidth() / 2) - getWidth() / 2,
					frame.getLocationOnScreen().y + (frame.getHeight() / 2) - getHeight() / 2
					);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 *  Sets the Aceptado attribute of the DialogoParametrosAgentes object
	 *
	 *@param  aceptado  The new Aceptado value
	 */
	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}


	/**
	 *  Sets the NAgentes attribute of the DialogoParametrosAgentes object
	 *
	 *@param  agentes  The new NAgentes value
	 */
	public void setNAgentes(int agentes) {
		nAgentes = agentes;
	}


	/**
	 *  Sets the TCiclo attribute of the DialogoParametrosAgentes object
	 *
	 *@param  ciclo  The new TCiclo value
	 */
	public void setTCiclo(int ciclo) {
		tCiclo = ciclo;
	}


	/**
	 *  Sets the TEjecucion attribute of the DialogoParametrosAgentes object
	 *
	 *@param  ejecucion  The new TEjecucion value
	 */
	public void setTEjecucion(int ejecucion) {
		tEjecucion = ejecucion;
	}


	/**
	 *  Gets the PanelPrincipal attribute of the DialogoParametrosAgentes object
	 *
	 *@return    The PanelPrincipal value
	 */
	public Component getPanelPrincipal() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getPanelArriba(), BorderLayout.CENTER);
		panel.add(getPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}


	/**
	 *  Gets the PanelBotones attribute of the DialogoParametrosAgentes object
	 *
	 *@return    The PanelBotones value
	 */
	public Component getPanelBotones() {
		JPanel panel = new JPanel();
		JButton botonAceptar = new JButton("Aceptar");

		botonAceptar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							botonAceptar_actionPerformed();
						}
					});

		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							botonCancelar_actionPerformed();
						}
					});
		panel.add(botonAceptar);
		panel.add(botonCancelar);
		return panel;
	}


	/**
	 *  Gets the PanelArriba attribute of the DialogoParametrosAgentes object
	 *
	 *@return    The PanelArriba value
	 */
	public Component getPanelArriba() {
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add(OP1);
		opciones.add(OP2);
		opciones.add(OP3);
		pOpciones = new PanelOpciones(opciones);

		FakeInternalFrame frame = new FakeInternalFrame("Parametros de los agentes", pOpciones);
		return frame;
	}


	/**
	 *  Gets the Aceptado attribute of the DialogoParametrosAgentes object
	 *
	 *@return    The Aceptado value
	 */
	public boolean isAceptado() {
		return aceptado;
	}


	/**
	 *  Gets the NAgentes attribute of the DialogoParametrosAgentes object
	 *
	 *@return    The NAgentes value
	 */
	public int getNAgentes() {
		return nAgentes;
	}


	/**
	 *  Gets the TCiclo attribute of the DialogoParametrosAgentes object
	 *
	 *@return    The TCiclo value
	 */
	public int getTCiclo() {
		return tCiclo;
	}


	/**
	 *  Gets the TEjecucion attribute of the DialogoParametrosAgentes object
	 *
	 *@return    The TEjecucion value
	 */
	public int getTEjecucion() {
		return tEjecucion;
	}


	/**
	 *  Description of the Method
	 */
	public void init() {
		setModal(true);
		getContentPane().add(getPanelPrincipal());
		setPreferredSize(new Dimension(300, 175));
		pack();
	}


	/**
	 *  Description of the Method
	 */
	public void botonCancelar_actionPerformed() {
		aceptado = false;
		setVisible(false);
	}


	/**
	 *  Description of the Method
	 */
	public void botonAceptar_actionPerformed() {
		try {
			nAgentes = Integer.parseInt(pOpciones.getValor(OP1));
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Parametro " + OP1 + ". Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			tEjecucion = Integer.parseInt(pOpciones.getValor(OP2));
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Parametro " + OP2 + ". Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			tCiclo = Integer.parseInt(pOpciones.getValor(OP3));
			aceptado = true;
			setVisible(false);
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Parametro " + OP3 + ". Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}


	/**
	 *  The main program for the DialogoParametrosAgentes class
	 *
	 *@param  args  The command line arguments
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DialogoParametrosAgentes dialogo = new DialogoParametrosAgentes(f);
		dialogo.setVisible(true);
	}
}
