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

public class DialogoInsertarComportamiento extends JDialog{
	private String[] sociales;
	private String[]psicologicos;
	private String[] tiposComportamiento;
	private PanelOpciones panelOpciones;
	
	private String id;
	private String tipoComportamiento;
	private String modeloPsicologico;
	private String modeloSocial;
	private double porcentaje;
	
	private boolean aceptado;
	
	public DialogoInsertarComportamiento(
			JFrame frame, String[] sociales, String[]psicologicos, String[] tiposC) {
		super(frame);
		this.sociales = sociales;
		this.psicologicos = psicologicos;
		this.tiposComportamiento = tiposC;
		try{
			init();
			setModal(true);
			setPreferredSize(new Dimension(300, 250));
			pack();
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
	public void botonAceptar_actionPerformed(){
		aceptado = true;
		id = panelOpciones.getValor("Identificador");
		modeloPsicologico = panelOpciones.getValor("Modelo Psicologico");
		modeloSocial = panelOpciones.getValor("Modelo Social");
		tipoComportamiento = panelOpciones.getValor("Tipo de Comportamiento");
		porcentaje = Double.parseDouble(panelOpciones.getValor("Porcentaje"));
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
		JButton botonCancelar = new JButton("Aceptar");
		botonCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				botonCancelar_actionPerformed();
			}
		});
		panel.add(botonCancelar);
		return panel;
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		
		DialogoInsertarComportamiento dialogo = 
			new DialogoInsertarComportamiento(
				frame,
				new String[]{"asdf"},
				new String[]{"asd"},
				new String[]{"asdf"}
			);
		dialogo.setVisible(true);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModeloPsicologico() {
		return modeloPsicologico;
	}

	public void setModeloPsicologico(String modeloPsicologico) {
		this.modeloPsicologico = modeloPsicologico;
	}

	public String getModeloSocial() {
		return modeloSocial;
	}

	public void setModeloSocial(String modeloSocial) {
		this.modeloSocial = modeloSocial;
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
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
