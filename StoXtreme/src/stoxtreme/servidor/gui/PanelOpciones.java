package stoxtreme.servidor.gui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class PanelOpciones extends JPanel{
	private ArrayList<String> opciones;
	
	public PanelOpciones(ArrayList<String> opciones) {
		super(new BorderLayout());
		this.opciones = opciones;
		try{
			init();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void init(){
		add(getPanelPrincipal(), BorderLayout.CENTER);
	}
	
	private Component getPanelPrincipal() {
		JPanel panelDerecho = new JPanel(new GridLayout(opciones.size(), 1, 10, 10));
		JPanel panelIzquierdo = new JPanel(new GridLayout(opciones.size(), 1));
		JPanel panel = new JPanel(new BorderLayout());
		for(int i=0; i<opciones.size(); i++){
			panelIzquierdo.add(new JLabel(opciones.get(i)));
//			JPanel p2 = new JPanel();
//			p2.add(new JTextField(15));
			panelDerecho.add(new JTextField(15));
		}
		panel.add(panelIzquierdo, BorderLayout.WEST);
		panel.add(panelDerecho, BorderLayout.EAST);
		return panel;
	}

	protected abstract void ejecuta(String opcion);
}
