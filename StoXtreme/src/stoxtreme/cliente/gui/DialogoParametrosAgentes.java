package stoxtreme.cliente.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import stoxtreme.cliente.gui.FakeInternalFrame;
import stoxtreme.cliente.gui.PanelOpciones;

/**
 *  Clase que permite Configurar los agentes de cada cliente
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
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
	 *  Constructor de la clase
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
	 *  asignamos valor a la variable aceptado
	 *
	 *@param  aceptado nuevo valor de aceptado
	 */
	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}


	/**
	 *  Introduce el numero de agentes
	 *
	 *@param  agentes valor de nagentes
	 */
	public void setNAgentes(int agentes) {
		nAgentes = agentes;
	}


	/**
	 *  Introduce e valor del tiempo de ciclo de los agentes
	 *
	 *@param  ciclo  valor de tciclo
	 */
	public void setTCiclo(int ciclo) {
		tCiclo = ciclo;
	}


	/**
	 *  Introduce e valor del tiempo de ejecución de los agentes
	 *
	 *@param  ejecucion  valor del tejecución
	 */
	public void setTEjecucion(int ejecucion) {
		tEjecucion = ejecucion;
	}


	/**
	 *  Nos crea el panel principal
	 *
	 *@return    devuelve el componente del panel principal
	 */
	public Component getPanelPrincipal() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getPanelArriba(), BorderLayout.CENTER);
		panel.add(getPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}


	/**
	 *  Nos crea un panel con botones y sus eventos asociados
	 *
	 *@return    devuelve un componente swing
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
	 *  nos crea el panel superior que se encarga de mostrar las opciones de configuración
	 *
	 *@return    devuelve un componente swing
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
	 *  nos devuelve si se ha aceptado
	 *
	 *@return    valor de la variable aceptado
	 */
	public boolean isAceptado() {
		return aceptado;
	}


	/**
	 *  nos devuelve el valor del numero de agentes
	 *
	 *@return    valor del numero de agentes
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
	 *  nos da el valor del tiempo de ejecución
	 *
	 *@return    tiempo de ejecución
	 */
	public int getTEjecucion() {
		return tEjecucion;
	}


	/**
	 *  inicialización de la ventana
	 */
	public void init() {
		setModal(true);
		getContentPane().add(getPanelPrincipal());
		setPreferredSize(new Dimension(300, 175));
		pack();
	}


	/**
	 *  cuando cancelamos el formulario
	 */
	public void botonCancelar_actionPerformed() {
		aceptado = false;
		setVisible(false);
	}


	/**
	 *  Puesta en marcha de los agentes una vez aceptado por el cliente
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
}
