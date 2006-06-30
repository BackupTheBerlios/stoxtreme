package stoxtreme.servidor.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import stoxtreme.servidor.Servidor;
import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.eventos.Ejecutor;
import stoxtreme.servidor.eventos.ObjetoCondicion;
import stoxtreme.servidor.eventos.evaluador.ParseException;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class DialogoInsercionEvento extends JDialog implements ActionListener {

	JButton botonAceptar;
	JButton botonCancelar;
	JButton botonComprobarSintaxis;

	JTextArea textoCondicion;
	JComboBox comboAccion;
	JCheckBox checkUnaVez;
	JCheckBox checkIniciarActivo;
	private JPanel panelPrincipal;

	private String condicion;
	private String evento;
	private boolean aceptado;
	private boolean unaVez;
	private boolean iniciarActivo;


	/**
	 *  Constructor for the DialogoInsercionEvento object
	 *
	 *@param  frame  Description of Parameter
	 */
	public DialogoInsercionEvento(JFrame frame) {
		super(frame);
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
	 *  Sets the Condicion attribute of the DialogoInsercionEvento object
	 *
	 *@param  condicion  The new Condicion value
	 */
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}


	/**
	 *  Sets the Evento attribute of the DialogoInsercionEvento object
	 *
	 *@param  evento  The new Evento value
	 */
	public void setEvento(String evento) {
		this.evento = evento;
	}


	/**
	 *  Sets the IniciarActivo attribute of the DialogoInsercionEvento object
	 *
	 *@param  iniciarActivo  The new IniciarActivo value
	 */
	public void setIniciarActivo(boolean iniciarActivo) {
		this.iniciarActivo = iniciarActivo;
	}


	/**
	 *  Sets the UnaVez attribute of the DialogoInsercionEvento object
	 *
	 *@param  unaVez  The new UnaVez value
	 */
	public void setUnaVez(boolean unaVez) {
		this.unaVez = unaVez;
	}


	/**
	 *  Sets the Aceptado attribute of the DialogoInsercionEvento object
	 *
	 *@param  aceptado  The new Aceptado value
	 */
	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}


	/**
	 *  Gets the Aceptado attribute of the DialogoInsercionEvento object
	 *
	 *@return    The Aceptado value
	 */
	public boolean isAceptado() {
		return aceptado;
	}


	/**
	 *  Gets the Condicion attribute of the DialogoInsercionEvento object
	 *
	 *@return    The Condicion value
	 */
	public String getCondicion() {
		return condicion;
	}


	/**
	 *  Gets the Evento attribute of the DialogoInsercionEvento object
	 *
	 *@return    The Evento value
	 */
	public String getEvento() {
		return evento;
	}


	/**
	 *  Gets the IniciarActivo attribute of the DialogoInsercionEvento object
	 *
	 *@return    The IniciarActivo value
	 */
	public boolean isIniciarActivo() {
		return iniciarActivo;
	}


	/**
	 *  Gets the UnaVez attribute of the DialogoInsercionEvento object
	 *
	 *@return    The UnaVez value
	 */
	public boolean isUnaVez() {
		return unaVez;
	}


	/**
	 *  Description of the Method
	 */
	public void init() {
		Container panel = this.getContentPane();

		panel.add(getPanelPrincipal());

		this.aceptado = false;
		this.setModal(true);
		this.setSize(new Dimension(455, 195));
	}


	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(botonAceptar)) {
			aceptado = rellenaDatos();
			if (aceptado) {
				this.setVisible(false);
			}
		}
		else if (e.getSource().equals(botonCancelar)) {
			this.aceptado = false;
			this.setVisible(false);
		}
		else if (e.getSource().equals(botonComprobarSintaxis)) {
			boolean b = compruebaSintaxisCondicion();
			if (b) {
				JOptionPane.showMessageDialog(this, "Sintaxis de la condicion correcta", "Comprobacion sintactica", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Error en la sintaxis", "Comprobacion sintactica", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	/**
	 *  Gets the PanelPrincipal attribute of the DialogoInsercionEvento object
	 *
	 *@return    The PanelPrincipal value
	 */
	private Component getPanelPrincipal() {
		JPanel panel = new JPanel(new GridLayout(4, 1));
		panel.add(getTextCondicion());
		panel.add(getComboAccion());
		panel.add(getCheckUnaVez());
		panel.add(getPanelBotones());
		FakeInternalFrame frame = new FakeInternalFrame("INSERCION EVENTO", panel);
		return frame;
	}


	/**
	 *  Gets the TextCondicion attribute of the DialogoInsercionEvento object
	 *
	 *@return    The TextCondicion value
	 */
	private Component getTextCondicion() {
		JPanel panel = new JPanel();
		JLabel labelCondicion = new JLabel("Condicion");
		textoCondicion = new JTextArea();
		textoCondicion.setPreferredSize(new Dimension(230, 20));
		textoCondicion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		panel.add(labelCondicion);
		panel.add(textoCondicion);

		return panel;
	}


	/**
	 *  Gets the ComboAccion attribute of the DialogoInsercionEvento object
	 *
	 *@return    The ComboAccion value
	 */
	private Component getComboAccion() {
		JPanel panel = new JPanel();
		JLabel labelAccion = new JLabel("Accion del evento");
		this.comboAccion = new JComboBox(Ejecutor.acciones);
		panel.add(labelAccion);
		panel.add(comboAccion);
		return panel;
	}


	/**
	 *  Gets the CheckUnaVez attribute of the DialogoInsercionEvento object
	 *
	 *@return    The CheckUnaVez value
	 */
	private Component getCheckUnaVez() {
		JPanel panel = new JPanel();
		JLabel labelUnavez = new JLabel("¿Ejecucion unica?");
		checkUnaVez = new JCheckBox();
		JLabel labelIniciarActivo = new JLabel("¿Iniciar activo?");
		checkIniciarActivo = new JCheckBox();
		checkIniciarActivo.setSelected(true);
		panel.add(labelUnavez);
		panel.add(checkUnaVez);
		panel.add(labelIniciarActivo);
		panel.add(checkIniciarActivo);
		return panel;
	}


	/**
	 *  Gets the PanelBotones attribute of the DialogoInsercionEvento object
	 *
	 *@return    The PanelBotones value
	 */
	private Component getPanelBotones() {
		JPanel panel = new JPanel();
		botonAceptar = new JButton("InsertarEvento");
		botonAceptar.addActionListener(this);
		botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(this);
		botonComprobarSintaxis = new JButton("Comprobar sintaxis");
		botonComprobarSintaxis.addActionListener(this);

		panel.add(botonAceptar);
		panel.add(botonCancelar);
		panel.add(botonComprobarSintaxis);
		return panel;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	private boolean rellenaDatos() {
		boolean valido = false;

		valido = compruebaSintaxisCondicion();
		if (valido) {
			this.condicion = textoCondicion.getText();
		}
		this.evento = (String) comboAccion.getSelectedItem();
		this.iniciarActivo = checkIniciarActivo.isSelected();
		this.unaVez = checkUnaVez.isSelected();
		return valido;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	private boolean compruebaSintaxisCondicion() {
		String descripcion = textoCondicion.getText().toUpperCase();
		try {
			ObjetoCondicion c = new ObjetoCondicion(descripcion, Servidor.getInstance().getVariablesSistema());
		}
		catch (ParseException e) {
			return false;
		}
		return true;
	}


	/**
	 *  The main program for the DialogoInsercionEvento class
	 *
	 *@param  args  The command line arguments
	 */
	public static void main(String[] args) {
		DialogoInsercionEvento d = new DialogoInsercionEvento(new JFrame());
		d.setModal(true);
		d.setVisible(true);
		System.exit(0);
	}
}
