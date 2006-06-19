package stoxtreme.cliente.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.MenuBar;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class FakeInternalFrame extends JPanel{
	private static final int R = 130;
	private static final int G = 130;
	private static final int B = 200;
	
	private static final Color colorRotulo = new Color(R, G, B);
	private JPanel panelPrincipal = new JPanel(new BorderLayout());
	private String titulo;
	
	public FakeInternalFrame(String titulo, Component principal){
		super(new BorderLayout());
		//super();
		this.titulo = titulo;
		this.add(getBarraTitulo(), BorderLayout.NORTH);
		panelPrincipal.add(principal);
		this.add(panelPrincipal, BorderLayout.CENTER);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public FakeInternalFrame damePanel(){return this;}
	public JPanel getBarraTitulo(){
		JPanel panel = new JPanel();
		panel.setBackground(colorRotulo);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel tituloLabel = new JLabel(titulo);
		tituloLabel.setForeground(Color.white);
		panel.add(tituloLabel);
		return panel;
	}
	
	public void setMenuBar(JMenuBar menuBar){
		panelPrincipal.add(menuBar, BorderLayout.NORTH);
	}
}