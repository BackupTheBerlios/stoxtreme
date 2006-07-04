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
 *  Configura el marco de la pantalla inicial
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
	 *  Constructor de FakeInternalFrame
	 *
	 *@param  titulo     Titulo de la ventana
	 *@param  principal  Componente incluido en la ventana
	 */
	public FakeInternalFrame(String titulo, Component principal) {
		super(new BorderLayout());
		this.titulo = titulo;
		this.add(getBarraTitulo(), BorderLayout.NORTH);
		panelPrincipal.add(principal);
		this.add(panelPrincipal, BorderLayout.CENTER);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}


	/**
	 *  Añade un MenuBar a la ventana
	 *
	 *@param  menuBar  Nuevo valor de MenuBar
	 */
	public void setMenuBar(JMenuBar menuBar) {
		panelPrincipal.add(menuBar, BorderLayout.NORTH);
	}


	/**
	 *  Añade un Título a la ventana
	 *
	 *@param  title  Nuevo valor de Tílulo
	 */
	public void setTitle(String title) {
		tituloLabel.setText(title);
		this.titulo = title;
	}


	/**
	 *  Obtiene la  BarraTitulo
	 *
	 *@return    Valor de la BarraTitulo
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
	 *  Obtiene la Título de la ventana
	 *
	 *@return    Valor del Title
	 */
	public String getTitle() {
		return titulo;
	}


	/**
	 *  Obtenemos la ventana completa
	 *
	 *@return    este Objeto
	 */
	public FakeInternalFrame damePanel() {
		return this;
	}
}
