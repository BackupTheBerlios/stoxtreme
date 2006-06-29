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

public class DialogoInsercionEvento extends JDialog implements ActionListener{
	private JPanel panelPrincipal;
	
	private String condicion;
	private String evento;
	private boolean aceptado;
	private boolean unaVez;
	private boolean iniciarActivo;
	
	JButton botonAceptar;
	JButton botonCancelar;
	JButton botonComprobarSintaxis;
	
	JTextArea textoCondicion;
	JComboBox comboAccion;
	JCheckBox checkUnaVez;
	JCheckBox checkIniciarActivo;
	
	public DialogoInsercionEvento(JFrame frame){
		super(frame);
		try{
			init();
			pack();
			setModal(true);
			setSize(new Dimension(300, this.getHeight()));
			setLocation(
					frame.getLocationOnScreen().x+ (frame.getWidth()/2)-getWidth()/2,
					frame.getLocationOnScreen().y+ (frame.getHeight()/2)-getHeight()/2
			);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void init(){
		Container panel = this.getContentPane();
		
		panel.add(getPanelPrincipal());
		
		this.aceptado = false;
		this.setModal(true);
		this.setSize(new Dimension(455, 195));
	}
	
	public boolean isAceptado(){
		return aceptado;
	}
	
	private Component getPanelPrincipal(){
		JPanel panel = new JPanel(new GridLayout(4, 1));
		panel.add(getTextCondicion());
		panel.add(getComboAccion());
		panel.add(getCheckUnaVez());
		panel.add(getPanelBotones());
		FakeInternalFrame frame = new FakeInternalFrame("INSERCION EVENTO", panel);
		return frame;
	}
	
	private Component getTextCondicion(){
		JPanel panel = new JPanel();
		JLabel labelCondicion = new JLabel("Condicion");
		textoCondicion = new JTextArea();
		textoCondicion.setPreferredSize(new Dimension(230, 20));
		textoCondicion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		panel.add(labelCondicion);
		panel.add(textoCondicion);
		
		return panel;
	}
	
	private Component getComboAccion(){
		JPanel panel = new JPanel();
		JLabel labelAccion = new JLabel("Accion del evento");
		this.comboAccion = new JComboBox(Ejecutor.acciones);
		panel.add(labelAccion);
		panel.add(comboAccion);
		return panel;
	}
	private Component getCheckUnaVez(){
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
	private Component getPanelBotones(){
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
	public static void main(String[] args){
		DialogoInsercionEvento d = new DialogoInsercionEvento(new JFrame());
		d.setModal(true);
		d.setVisible(true);
		System.exit(0);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(botonAceptar)){
			aceptado = rellenaDatos();
			if(aceptado)
				this.setVisible(false);
		}
		else if(e.getSource().equals(botonCancelar)){
			this.aceptado = false;
			this.setVisible(false);
		}
		else if(e.getSource().equals(botonComprobarSintaxis)){
			boolean b = compruebaSintaxisCondicion();
			if(b){
				JOptionPane.showMessageDialog(this, "Sintaxis de la condicion correcta", "Comprobacion sintactica", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(this, "Error en la sintaxis", "Comprobacion sintactica", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private boolean rellenaDatos(){
		boolean valido = false;
		
		valido = compruebaSintaxisCondicion();
		if(valido){
			this.condicion = textoCondicion.getText();
		}
		this.evento = (String)comboAccion.getSelectedItem();
		this.iniciarActivo =  checkIniciarActivo.isSelected();
		this.unaVez =  checkUnaVez.isSelected();
		return valido;
	}
	
	private boolean compruebaSintaxisCondicion(){
		String descripcion = textoCondicion.getText().toUpperCase();
		try {
			ObjetoCondicion c = new ObjetoCondicion(descripcion, Servidor.getInstance().getVariablesSistema());
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public boolean isIniciarActivo() {
		return iniciarActivo;
	}

	public void setIniciarActivo(boolean iniciarActivo) {
		this.iniciarActivo = iniciarActivo;
	}

	public boolean isUnaVez() {
		return unaVez;
	}

	public void setUnaVez(boolean unaVez) {
		this.unaVez = unaVez;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}
}
