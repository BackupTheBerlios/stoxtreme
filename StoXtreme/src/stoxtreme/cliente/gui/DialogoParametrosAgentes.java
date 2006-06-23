package stoxtreme.cliente.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import stoxtreme.cliente.gui.FakeInternalFrame;
import stoxtreme.cliente.gui.PanelOpciones;

public class DialogoParametrosAgentes extends JDialog{
	private static String OP1 = "Numero de agentes";
	private static String OP2 = "Tiempo medio de ejecucion";
	private static String OP3 = "Tiempo de ciclo ( mseg )";
	private PanelOpciones pOpciones;
	private boolean aceptado = false;
	private int nAgentes;
	private int tEjecucion;
	private int tCiclo;
	
	public DialogoParametrosAgentes(JFrame frame) {
		super(frame, "Insertar parametros",true);
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
		setModal(true);
		getContentPane().add(getPanelPrincipal());
		setPreferredSize(new Dimension(300,175));
		pack();
	}
	
	public Component getPanelPrincipal(){
		JPanel panel =new JPanel(new BorderLayout());
		panel.add(getPanelArriba(), BorderLayout.CENTER);
		panel.add(getPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}
	
	public Component getPanelBotones(){
		JPanel panel = new JPanel();
		JButton botonAceptar = new JButton("Aceptar");
		
		botonAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				botonAceptar_actionPerformed();
			}
		});
		
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				botonCancelar_actionPerformed();
			}
		});
		panel.add(botonAceptar);
		panel.add(botonCancelar);
		return panel;
	}
	
	public Component getPanelArriba(){
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add(OP1);
		opciones.add(OP2);
		opciones.add(OP3);
		pOpciones = new PanelOpciones(opciones);
		
		FakeInternalFrame frame = new FakeInternalFrame("Parametros de los agentes", pOpciones);
		return frame;
	}
	
	public void botonCancelar_actionPerformed(){
		aceptado = false;
		setVisible(false);
	}
	
	public void botonAceptar_actionPerformed(){
		try{
			nAgentes = Integer.parseInt(pOpciones.getValor(OP1));
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(this, "Parametro "+OP1+". Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try{
			tEjecucion = Integer.parseInt(pOpciones.getValor(OP2));
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(this, "Parametro "+OP2+". Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try{
			tCiclo = Integer.parseInt(pOpciones.getValor(OP3));
			aceptado = true;
			setVisible(false);
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(this, "Parametro "+OP3+". Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DialogoParametrosAgentes dialogo = new DialogoParametrosAgentes(f);
		dialogo.setVisible(true);
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}

	public int getNAgentes() {
		return nAgentes;
	}

	public void setNAgentes(int agentes) {
		nAgentes = agentes;
	}

	public int getTCiclo() {
		return tCiclo;
	}

	public void setTCiclo(int ciclo) {
		tCiclo = ciclo;
	}

	public int getTEjecucion() {
		return tEjecucion;
	}

	public void setTEjecucion(int ejecucion) {
		tEjecucion = ejecucion;
	}
}
