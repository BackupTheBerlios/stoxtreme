package stoxtreme.cliente.gui;
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
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.cliente.gui.FakeInternalFrame;
import stoxtreme.cliente.gui.PanelOpciones;

public class DialogoMuestraAgente extends JDialog{
	private Agente agente;
	private boolean aceptado = false;
	
	public DialogoMuestraAgente(Agente agente, JFrame frame) {
		super(frame, "Consulta agente", true);
		this.agente = agente;
		try{
			init();
			pack();
			setModal(true);
			setSize(new Dimension(400,300));
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
		getContentPane().add(getPanelPrincipal());
	}
	
	public Component getPanelPrincipal(){
		JPanel panel = new JPanel(new BorderLayout());
		FakeInternalFrame frame = new FakeInternalFrame("Agente",panel);
		panel.add(getPanelArriba(),BorderLayout.CENTER);
		panel.add(getPanelBotones(),BorderLayout.SOUTH);
		return frame;
	}
	
	public Component getPanelArriba(){
		JTextPane panel =new JTextPane();
		JScrollPane scroll = new JScrollPane(panel);
		try {
			panel.getStyledDocument().insertString(0, agente.getOutput().toString(), null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return scroll;
	}
	
	public Component getPanelBotones(){
		JPanel panel = new JPanel();
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				aceptar_actionPerformed();
			}
		});
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				cancelar_actionPerformed();
			}
		});
		panel.add(botonAceptar);
		panel.add(botonCancelar);
		return panel;
	}
	
	public void aceptar_actionPerformed(){
		setVisible(false);
		aceptado = true;
	}
	
	public void cancelar_actionPerformed(){
		setVisible(false);
		aceptado = false;
	}
	
	public Component getPanelOpciones(){
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("");
		PanelOpciones panelOpcionesAgentes = new PanelOpciones(opciones);
		return panelOpcionesAgentes;
	}
	
	public boolean isAceptado(){
		return aceptado;
	}
}