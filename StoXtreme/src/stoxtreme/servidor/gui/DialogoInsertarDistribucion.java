package stoxtreme.servidor.gui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import stoxtreme.servidor.gui.FakeInternalFrame;
import stoxtreme.servidor.gui.PanelOpciones;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class DialogoInsertarDistribucion extends JDialog implements ActionListener {

	JButton botonAceptar = new JButton("Aceptar");
	JButton botonCancelar = new JButton("Cancelar");

	private boolean aceptado = false;
	private String tipo;
	private String id;
	private double p1;
	private double p2;

	private JPanel panelDependiente;
	private PanelOpciones panelParametros;
	private PanelOpciones panelOpciones;
	/**
	 *  Description of the Field
	 */
	public static String UNIFORME = "Distribucion Uniforme";
	/**
	 *  Description of the Field
	 */
	public static String NORMAL = "Distribucion Normal";
	/**
	 *  Description of the Field
	 */
	public static String POISSON = "Distribucion Poisson";

	private static String[] distribs = {
			UNIFORME, NORMAL, POISSON
			};


	/**
	 *  Constructor for the DialogoInsertarDistribucion object
	 *
	 *@param  frame  Description of Parameter
	 */
	public DialogoInsertarDistribucion(Frame frame) {
		super(frame, "Inserte Distribucion", true);
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
	 *  Constructor for the DialogoInsertarDistribucion object
	 *
	 *@param  frame  Description of Parameter
	 *@param  id     Description of Parameter
	 *@param  tipo   Description of Parameter
	 *@param  p1     Description of Parameter
	 *@param  p2     Description of Parameter
	 */
	public DialogoInsertarDistribucion(JFrame frame, String id, String tipo, double p1, double p2) {
		this(frame);
		// Ahora seteamos los valores antiguos

		panelOpciones.setValor("Identificador", id);

		panelDependiente.removeAll();
		if ("Uniforme".equals(tipo)) {
			panelDependiente.add(getPanelOpcionesUniforme());
			panelOpciones.setValor("Distribucion", UNIFORME);
			panelParametros.setValor("Media", Double.toString(p1));
			pack();
			panelDependiente.updateUI();
		}
		else if ("Normal".equals(tipo)) {
			panelOpciones.setValor("Distribucion", NORMAL);
			panelDependiente.add(getPanelOpcionesNormal());
			panelParametros.setValor("Media", Double.toString(p1));
			panelParametros.setValor("Desviacion tipica", Double.toString(p2));
			pack();
			panelDependiente.updateUI();
		}
		else {
			panelOpciones.setValor("Distribucion", POISSON);
			panelDependiente.add(getPanelOpcionesPoisson());
			panelParametros.setValor("Lambda", Double.toString(p1));
			pack();
			panelDependiente.updateUI();
		}

		setSize(new Dimension(300, this.getHeight()));
	}


	/**
	 *  Sets the P1 attribute of the DialogoInsertarDistribucion object
	 *
	 *@param  p1  The new P1 value
	 */
	public void setP1(double p1) {
		this.p1 = p1;
	}


	/**
	 *  Sets the P2 attribute of the DialogoInsertarDistribucion object
	 *
	 *@param  p2  The new P2 value
	 */
	public void setP2(double p2) {
		this.p2 = p2;
	}


	/**
	 *  Sets the Tipo attribute of the DialogoInsertarDistribucion object
	 *
	 *@param  tipo  The new Tipo value
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	/**
	 *  Sets the Id attribute of the DialogoInsertarDistribucion object
	 *
	 *@param  id  The new Id value
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 *  Gets the PanelPrincipal attribute of the DialogoInsertarDistribucion
	 *  object
	 *
	 *@return    The PanelPrincipal value
	 */
	public Component getPanelPrincipal() {
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("Distribucion");
		opciones.add("Identificador");
		panelOpciones = new PanelOpciones(opciones);
		panelOpciones.setOpcionalidad("Distribucion", distribs, this);

		panelDependiente = new JPanel(new BorderLayout());
		panelDependiente.add(getPanelOpcionesUniforme());

		//JPanel panelRaiz = new JPanel(new GridLayout(2,1));
		JPanel panelRaiz = new JPanel(new BorderLayout());
		panelRaiz.add(panelOpciones, BorderLayout.NORTH);
		panelRaiz.add(panelDependiente, BorderLayout.SOUTH);

		FakeInternalFrame frame = new FakeInternalFrame("Inserte valores para la distribución.", panelRaiz);
		return frame;
	}


	/**
	 *  Gets the PanelOpcionesUniforme attribute of the
	 *  DialogoInsertarDistribucion object
	 *
	 *@return    The PanelOpcionesUniforme value
	 */
	public Component getPanelOpcionesUniforme() {
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("Media");
		panelParametros = new PanelOpciones(opciones);
		return panelParametros;
	}


	/**
	 *  Gets the PanelOpcionesNormal attribute of the DialogoInsertarDistribucion
	 *  object
	 *
	 *@return    The PanelOpcionesNormal value
	 */
	public Component getPanelOpcionesNormal() {
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("Media");
		opciones.add("Desviacion tipica");
		panelParametros = new PanelOpciones(opciones);
		return panelParametros;
	}


	/**
	 *  Gets the PanelOpcionesPoisson attribute of the
	 *  DialogoInsertarDistribucion object
	 *
	 *@return    The PanelOpcionesPoisson value
	 */
	public Component getPanelOpcionesPoisson() {
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("Lambda");
		panelParametros = new PanelOpciones(opciones);
		return panelParametros;
	}


	/**
	 *  Gets the P1 attribute of the DialogoInsertarDistribucion object
	 *
	 *@return    The P1 value
	 */
	public double getP1() {
		return p1;
	}


	/**
	 *  Gets the P2 attribute of the DialogoInsertarDistribucion object
	 *
	 *@return    The P2 value
	 */
	public double getP2() {
		return p2;
	}


	/**
	 *  Gets the Tipo attribute of the DialogoInsertarDistribucion object
	 *
	 *@return    The Tipo value
	 */
	public String getTipo() {
		return tipo;
	}


	/**
	 *  Gets the Id attribute of the DialogoInsertarDistribucion object
	 *
	 *@return    The Id value
	 */
	public String getId() {
		return id;
	}


	/**
	 *  Gets the Aceptado attribute of the DialogoInsertarDistribucion object
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
		JPanel panel = new JPanel(new BorderLayout());
		getContentPane().add(panel);
		panel.add(getPanelPrincipal(), BorderLayout.CENTER);
		panel.add(getPanelBotones(), BorderLayout.SOUTH);
	}


	/**
	 *  Description of the Method
	 */
	public void botonAceptar_actionPerformed() {
		try {
			aceptado = true;
			tipo = panelOpciones.getValor("Distribucion");
			id = panelOpciones.getValor("Identificador");
			if (UNIFORME.equals(tipo)) {
				p1 = Double.parseDouble(panelParametros.getValor("Media"));
			}
			else if (NORMAL.equals(tipo)) {
				p1 = Double.parseDouble(panelParametros.getValor("Media"));
				p2 = Double.parseDouble(panelParametros.getValor("Desviacion tipica"));
			}
			else {
				p1 = Double.parseDouble(panelParametros.getValor("Lambda"));
			}
			setVisible(false);
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Error en los datos. Introduzcalos de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
		}
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
	 *
	 *@param  event  Description of Parameter
	 */
	public void actionPerformed(ActionEvent event) {
		String s = panelOpciones.getValor("Distribucion");
		if (UNIFORME.equals(s)) {
			panelDependiente.removeAll();
			panelDependiente.add(getPanelOpcionesUniforme());
			panelDependiente.updateUI();
			pack();
			setSize(new Dimension(300, this.getHeight()));
		}
		else if (NORMAL.equals(s)) {
			panelDependiente.removeAll();
			panelDependiente.add(getPanelOpcionesNormal());
			panelDependiente.updateUI();
			pack();
			setSize(new Dimension(300, this.getHeight()));
		}
		else {
			panelDependiente.removeAll();
			panelDependiente.add(getPanelOpcionesPoisson());
			panelDependiente.updateUI();
			pack();
			setSize(new Dimension(300, this.getHeight()));
		}
	}


	/**
	 *  Gets the PanelBotones attribute of the DialogoInsertarDistribucion object
	 *
	 *@return    The PanelBotones value
	 */
	private Component getPanelBotones() {
		JPanel panel = new JPanel();
		panel.add(botonAceptar);
		panel.add(botonCancelar);

		ActionListener listener =
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getSource().equals(botonAceptar)) {
						botonAceptar_actionPerformed();
					}
					else if (e.getSource().equals(botonCancelar)) {
						botonCancelar_actionPerformed();
					}
				}
			};
		botonAceptar.addActionListener(listener);
		botonCancelar.addActionListener(listener);
		return panel;
	}

}
