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

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class FakeInternalFrame extends JPanel {
	private JPanel panelPrincipal = new JPanel(new BorderLayout());
	private String titulo;
	private JLabel tituloLabel;
	private static final int R = 130;
	private static final int G = 130;
	private static final int B = 200;

	private static final Color colorRotulo = new Color(R, G, B);


	/**
	 *  Constructor for the FakeInternalFrame object
	 *
	 *@param  titulo     Description of Parameter
	 *@param  principal  Description of Parameter
	 */
	public FakeInternalFrame(String titulo, Component principal) {
		super(new BorderLayout());
		//super();
		this.titulo = titulo;
		this.add(getBarraTitulo(), BorderLayout.NORTH);
		panelPrincipal.add(principal);
		this.add(panelPrincipal, BorderLayout.CENTER);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}


	/**
	 *  Sets the MenuBar attribute of the FakeInternalFrame object
	 *
	 *@param  menuBar  The new MenuBar value
	 */
	public void setMenuBar(JMenuBar menuBar) {
		panelPrincipal.add(menuBar, BorderLayout.NORTH);
	}


	/**
	 *  Sets the Title attribute of the FakeInternalFrame object
	 *
	 *@param  title  The new Title value
	 */
	public void setTitle(String title) {
		tituloLabel.setText(title);
		this.titulo = title;
	}


	/**
	 *  Gets the BarraTitulo attribute of the FakeInternalFrame object
	 *
	 *@return    The BarraTitulo value
	 */
	public JPanel getBarraTitulo() {
		JPanel panel = new JPanel();
		panel.setBackground(colorRotulo);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		tituloLabel = new JLabel(titulo);
		tituloLabel.setForeground(Color.white);
		panel.add(tituloLabel);
		return panel;
	}


	/**
	 *  Gets the Title attribute of the FakeInternalFrame object
	 *
	 *@return    The Title value
	 */
	public String getTitle() {
		return titulo;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public FakeInternalFrame damePanel() {
		return this;
	}
}
