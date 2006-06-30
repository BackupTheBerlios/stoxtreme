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
 *@author    Chris Seguin
 */
public class DialogoInsertarComportamiento extends JDialog {
	private String[] sociales;
	private String[] psicologicos;
	private String[] tiposComportamiento;
	private PanelOpciones panelOpciones;

	private String id;
	private String tipoComportamiento;
	private String modeloPsicologico;
	private String modeloSocial;
	private double porcentaje;

	private boolean aceptado;


	/**
	 *  Constructor for the DialogoInsertarComportamiento object
	 *
	 *@param  frame         Description of Parameter
	 *@param  sociales      Description of Parameter
	 *@param  psicologicos  Description of Parameter
	 *@param  tiposC        Description of Parameter
	 */
	public DialogoInsertarComportamiento(
			JFrame frame, String[] sociales, String[] psicologicos, String[] tiposC) {
		super(frame);
		this.sociales = sociales;
		this.psicologicos = psicologicos;
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
	 *  Sets the Id attribute of the DialogoInsertarComportamiento object
	 *
	 *@param  id  The new Id value
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 *  Sets the ModeloPsicologico attribute of the DialogoInsertarComportamiento
	 *  object
	 *
	 *@param  modeloPsicologico  The new ModeloPsicologico value
	 */
	public void setModeloPsicologico(String modeloPsicologico) {
		this.modeloPsicologico = modeloPsicologico;
	}


	/**
	 *  Sets the ModeloSocial attribute of the DialogoInsertarComportamiento
	 *  object
	 *
	 *@param  modeloSocial  The new ModeloSocial value
	 */
	public void setModeloSocial(String modeloSocial) {
		this.modeloSocial = modeloSocial;
	}


	/**
	 *  Sets the Porcentaje attribute of the DialogoInsertarComportamiento object
	 *
	 *@param  porcentaje  The new Porcentaje value
	 */
	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}


	/**
	 *  Sets the TipoComportamiento attribute of the
	 *  DialogoInsertarComportamiento object
	 *
	 *@param  tipoComportamiento  The new TipoComportamiento value
	 */
	public void setTipoComportamiento(String tipoComportamiento) {
		this.tipoComportamiento = tipoComportamiento;
	}


	/**
	 *  Sets the Aceptado attribute of the DialogoInsertarComportamiento object
	 *
	 *@param  aceptado  The new Aceptado value
	 */
	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}


	/**
	 *  Gets the PanelPrincipal attribute of the DialogoInsertarComportamiento
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
	 *  Gets the PanelSuperior attribute of the DialogoInsertarComportamiento
	 *  object
	 *
	 *@return    The PanelSuperior value
	 */
	public Component getPanelSuperior() {
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("Identificador");
		opciones.add("Modelo Psicologico");
		opciones.add("Modelo Social");
		opciones.add("Tipo de Comportamiento");
		opciones.add("Porcentaje");
		panelOpciones = new PanelOpciones(opciones);
		panelOpciones.setOpcionalidad("Modelo Psicologico", psicologicos);
		panelOpciones.setOpcionalidad("Modelo Social", sociales);
		panelOpciones.setOpcionalidad("Tipo de Comportamiento", tiposComportamiento);
		return panelOpciones;
	}


	/**
	 *  Gets the PanelBotones attribute of the DialogoInsertarComportamiento
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
	 *  Gets the Id attribute of the DialogoInsertarComportamiento object
	 *
	 *@return    The Id value
	 */
	public String getId() {
		return id;
	}


	/**
	 *  Gets the ModeloPsicologico attribute of the DialogoInsertarComportamiento
	 *  object
	 *
	 *@return    The ModeloPsicologico value
	 */
	public String getModeloPsicologico() {
		return modeloPsicologico;
	}


	/**
	 *  Gets the ModeloSocial attribute of the DialogoInsertarComportamiento
	 *  object
	 *
	 *@return    The ModeloSocial value
	 */
	public String getModeloSocial() {
		return modeloSocial;
	}


	/**
	 *  Gets the Porcentaje attribute of the DialogoInsertarComportamiento object
	 *
	 *@return    The Porcentaje value
	 */
	public double getPorcentaje() {
		return porcentaje;
	}


	/**
	 *  Gets the TipoComportamiento attribute of the
	 *  DialogoInsertarComportamiento object
	 *
	 *@return    The TipoComportamiento value
	 */
	public String getTipoComportamiento() {
		return tipoComportamiento;
	}


	/**
	 *  Gets the Aceptado attribute of the DialogoInsertarComportamiento object
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
		modeloPsicologico = panelOpciones.getValor("Modelo Psicologico");
		modeloSocial = panelOpciones.getValor("Modelo Social");
		tipoComportamiento = panelOpciones.getValor("Tipo de Comportamiento");
		porcentaje = Double.parseDouble(panelOpciones.getValor("Porcentaje"));
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
