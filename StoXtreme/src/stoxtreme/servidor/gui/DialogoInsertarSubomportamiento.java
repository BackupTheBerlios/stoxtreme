package stoxtreme.servidor.gui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import stoxtreme.servidor.gui.FakeInternalFrame;
import stoxtreme.servidor.gui.PanelOpciones;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class DialogoInsertarSubomportamiento extends JDialog {
	private String[] tiposComportamiento;
	private PanelOpciones panelOpciones;

	private String id;
	private String tipoComportamiento;

	private boolean aceptado;


	/**
	 *  Constructor for the DialogoInsertarSubomportamiento object
	 *
	 *@param  frame   Description of Parameter
	 *@param  tiposC  Description of Parameter
	 */
	public DialogoInsertarSubomportamiento(
			JFrame frame, String[] tiposC) {
		super(frame);
		this.tiposComportamiento = tiposC;
		try {
			init();
			pack();
			setModal(true);
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
	 *  Sets the Id attribute of the DialogoInsertarSubomportamiento object
	 *
	 *@param  id  The new Id value
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 *  Sets the TipoComportamiento attribute of the
	 *  DialogoInsertarSubomportamiento object
	 *
	 *@param  tipoComportamiento  The new TipoComportamiento value
	 */
	public void setTipoComportamiento(String tipoComportamiento) {
		this.tipoComportamiento = tipoComportamiento;
	}


	/**
	 *  Sets the Aceptado attribute of the DialogoInsertarSubomportamiento object
	 *
	 *@param  aceptado  The new Aceptado value
	 */
	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}


	/**
	 *  Gets the PanelPrincipal attribute of the DialogoInsertarSubomportamiento
	 *  object
	 *
	 *@return    The PanelPrincipal value
	 */
	public Component getPanelPrincipal() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getPanelSuperior(), BorderLayout.CENTER);
		panel.add(getPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}


	/**
	 *  Gets the PanelSuperior attribute of the DialogoInsertarSubomportamiento
	 *  object
	 *
	 *@return    The PanelSuperior value
	 */
	public Component getPanelSuperior() {
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("Identificador");
		opciones.add("Tipo de Comportamiento");
		panelOpciones = new PanelOpciones(opciones);
		panelOpciones.setOpcionalidad("Tipo de Comportamiento", tiposComportamiento);
		return panelOpciones;
	}


	/**
	 *  Gets the PanelBotones attribute of the DialogoInsertarSubomportamiento
	 *  object
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
		panel.add(botonAceptar);
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							botonCancelar_actionPerformed();
						}
					});
		panel.add(botonCancelar);
		return panel;
	}


	/**
	 *  Gets the Id attribute of the DialogoInsertarSubomportamiento object
	 *
	 *@return    The Id value
	 */
	public String getId() {
		return id;
	}


	/**
	 *  Gets the TipoComportamiento attribute of the
	 *  DialogoInsertarSubomportamiento object
	 *
	 *@return    The TipoComportamiento value
	 */
	public String getTipoComportamiento() {
		return tipoComportamiento;
	}


	/**
	 *  Gets the Aceptado attribute of the DialogoInsertarSubomportamiento object
	 *
	 *@return    The Aceptado value
	 */
	public boolean isAceptado() {
		return aceptado;
	}


	/**
	 *  Description of the Method
	 */
	public void init() {
		getContentPane().add(new FakeInternalFrame("Insertar Comportamiento", getPanelPrincipal()));
	}


	/**
	 *  Description of the Method
	 */
	public void botonAceptar_actionPerformed() {
		aceptado = true;
		id = panelOpciones.getValor("Identificador");
		tipoComportamiento = panelOpciones.getValor("Tipo de Comportamiento");
		setVisible(false);
	}


	/**
	 *  Description of the Method
	 */
	public void botonCancelar_actionPerformed() {
		aceptado = false;
		setVisible(false);
	}

}
