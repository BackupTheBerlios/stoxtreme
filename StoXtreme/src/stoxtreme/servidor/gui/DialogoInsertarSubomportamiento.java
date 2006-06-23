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

public class DialogoInsertarSubomportamiento extends JDialog{
	private String[] tiposComportamiento;
	private PanelOpciones panelOpciones;
	
	private String id;
	private String tipoComportamiento;
	
	private boolean aceptado;
	
	public DialogoInsertarSubomportamiento(
			JFrame frame, String[] tiposC) {
		super(frame);
		this.tiposComportamiento = tiposC;
		try{
			init();
			pack();
			setModal(true);
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
		getContentPane().add(new FakeInternalFrame("Insertar Comportamiento",getPanelPrincipal()));
	}
	
	public Component getPanelPrincipal(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getPanelSuperior(),BorderLayout.CENTER);
		panel.add(getPanelBotones(),BorderLayout.SOUTH);
		return panel;
	}
	
	public Component getPanelSuperior(){
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("Identificador");
		opciones.add("Tipo de Comportamiento");
		panelOpciones = new PanelOpciones(opciones);
		panelOpciones.setOpcionalidad("Tipo de Comportamiento", tiposComportamiento);
		return panelOpciones;
	}
	public void botonAceptar_actionPerformed(){
		aceptado = true;
		id = panelOpciones.getValor("Identificador");
		tipoComportamiento = panelOpciones.getValor("Tipo de Comportamiento");
		setVisible(false);
	}
	
	public void botonCancelar_actionPerformed(){
		aceptado = false;
		setVisible(false);
	}
	
	public Component getPanelBotones(){
		JPanel panel = new JPanel();
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				botonAceptar_actionPerformed();
			}
		});
		panel.add(botonAceptar);
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				botonCancelar_actionPerformed();
			}
		});
		panel.add(botonCancelar);
		return panel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipoComportamiento() {
		return tipoComportamiento;
	}

	public void setTipoComportamiento(String tipoComportamiento) {
		this.tipoComportamiento = tipoComportamiento;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}
	
	
}
