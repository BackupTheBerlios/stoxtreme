package stoxtreme.servidor.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DialogoInsercionEvento extends JDialog{
	private JPanel panelPrincipal;
	private DialogoInsercionEvento este;
	
	public DialogoInsercionEvento(Frame owner){
		super(owner);
		este = this;
		Container panel = this.getContentPane();
		panelPrincipal = new JPanel(new GridLayout(3, 1));
		panel.add(panelPrincipal);
		panelPrincipal.add(new JLabel("Hola"));
		JButton boton = new JButton("Cerrar");
		boton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				este.setVisible(false);
			}
		});
		panelPrincipal.add(boton);
		
		this.setSize(new Dimension(300, 200));
	}
	
	public static void main(String[] args){
		DialogoInsercionEvento d = new DialogoInsercionEvento(new JFrame());
		d.setModal(true);
		d.setVisible(true);
		System.exit(0);
	}
}
