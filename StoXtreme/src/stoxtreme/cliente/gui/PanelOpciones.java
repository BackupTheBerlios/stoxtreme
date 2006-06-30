package stoxtreme.cliente.gui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class PanelOpciones extends JPanel {
	private ArrayList<String> claves;
	private ArrayList<String> password;
	private ArrayList<String> chooser;
	private Hashtable<String, JComponent> opciones;
	private Hashtable<String, String> vChooser;


	/**
	 *  Constructor for the PanelOpciones object
	 *
	 *@param  claves  Description of Parameter
	 */
	public PanelOpciones(ArrayList<String> claves) {
		this(claves, new ArrayList<String>(), new ArrayList<String>());
	}


	/**
	 *  Constructor for the PanelOpciones object
	 *
	 *@param  claves  Description of Parameter
	 *@param  passw   Description of Parameter
	 */
	public PanelOpciones(ArrayList<String> claves, ArrayList<String> passw) {
		this(claves, passw, new ArrayList<String>());
	}


	/**
	 *  Constructor for the PanelOpciones object
	 *
	 *@param  claves    Description of Parameter
	 *@param  password  Description of Parameter
	 *@param  choosers  Description of Parameter
	 */
	public PanelOpciones(
			ArrayList<String> claves,
			ArrayList<String> password,
			ArrayList<String> choosers) {
		super(new BorderLayout());
		this.claves = claves;
		this.chooser = choosers;
		this.password = password;
		opciones = new Hashtable<String, JComponent>();
		for (int i = 0; i < claves.size(); i++) {
			opciones.put(claves.get(i), new JTextField());
		}
		if (password != null) {
			for (int i = 0; i < password.size(); i++) {
				opciones.put(password.get(i), new JPasswordField());
			}
		}

		vChooser = new Hashtable<String, String>();
		if (choosers != null) {
			for (int i = 0; i < choosers.size(); i++) {
				JPanel panel = new JPanel(new BorderLayout());
				panel.add(opciones.get(choosers.get(i)), BorderLayout.CENTER);
				JButton boton = new JButton("...");
				boton.addActionListener(new ListenerOpcion(panel, choosers.get(i), opciones.get(choosers.get(i)), vChooser));
				panel.add(boton, BorderLayout.EAST);
				opciones.put(choosers.get(i), panel);
			}
		}

		try {
			init();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 *  Sets the Valor attribute of the PanelOpciones object
	 *
	 *@param  opcion  The new Valor value
	 *@param  valor   The new Valor value
	 */
	public void setValor(String opcion, String valor) {
		if (chooser.contains(opcion)) {
			vChooser.put(opcion, valor);
		}
		else if (password.contains(opcion)) {
			// No dejamos setear los password
		}
		else {
			Component comp = opciones.get(opcion);
			if (comp instanceof JComboBox) {
				((JComboBox) comp).setSelectedItem(valor);
			}
			else {
				((JTextField) comp).setText(valor);
			}
		}
	}


	/**
	 *  Sets the Opcionalidad attribute of the PanelOpciones object
	 *
	 *@param  opcion   The new Opcionalidad value
	 *@param  valores  The new Opcionalidad value
	 */
	public void setOpcionalidad(String opcion, String[] valores) {
		JComboBox combo = new JComboBox(valores);
		opciones.put(opcion, combo);
		init();
		updateUI();
	}


	/**
	 *  Sets the Opcionalidad attribute of the PanelOpciones object
	 *
	 *@param  opcion    The new Opcionalidad value
	 *@param  valores   The new Opcionalidad value
	 *@param  listener  The new Opcionalidad value
	 */
	public void setOpcionalidad(String opcion, String[] valores, ActionListener listener) {
		JComboBox combo = new JComboBox(valores);
		combo.addActionListener(listener);
		opciones.put(opcion, combo);
		init();
		updateUI();
	}


	/**
	 *  Sets the CheckBox attribute of the PanelOpciones object
	 *
	 *@param  opcion  The new CheckBox value
	 */
	public void setCheckBox(String opcion) {
		JCheckBox check = new JCheckBox();
		opciones.put(opcion, check);
		init();
		updateUI();
	}


	/**
	 *  Gets the Valor attribute of the PanelOpciones object
	 *
	 *@param  opcion  Description of Parameter
	 *@return         The Valor value
	 */
	public String getValor(String opcion) {
		String s;
		if (chooser.contains(opcion)) {
			s = vChooser.get(opcion);
		}
		else if (password.contains(opcion)) {
			s = new String(((JPasswordField) opciones.get(opcion)).getPassword());
		}
		else {
			Component comp = opciones.get(opcion);
			if (comp instanceof JCheckBox) {
				if (((JCheckBox) comp).isSelected()) {
					return "true";
				}
				else {
					return "false";
				}
			}
			else if (comp instanceof JComboBox) {
				s = (String) ((JComboBox) comp).getSelectedItem();
			}
			else {
				s = ((JTextField) comp).getText();
			}
		}
		return s;
	}


	/**
	 *  Description of the Method
	 */
	public void init() {
		add(getPanelPrincipal(), BorderLayout.CENTER);
	}


	/**
	 *  Gets the PanelPrincipal attribute of the PanelOpciones object
	 *
	 *@return    The PanelPrincipal value
	 */
	private Component getPanelPrincipal() {
		JPanel panel = new JPanel(new SpringLayout());
		for (int i = 0; i < claves.size(); i++) {
			JLabel l = new JLabel(claves.get(i), JLabel.TRAILING);
			panel.add(l);
			JComponent textField = opciones.get(claves.get(i));
			l.setLabelFor(textField);
			panel.add(textField);
		}
		makeCompactGrid(panel, opciones.size(), 2, 6, 6, 6, 6);
		return panel;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  parent    Description of Parameter
	 *@param  rows      Description of Parameter
	 *@param  cols      Description of Parameter
	 *@param  initialX  Description of Parameter
	 *@param  initialY  Description of Parameter
	 *@param  xPad      Description of Parameter
	 *@param  yPad      Description of Parameter
	 */
	public static void makeCompactGrid(Container parent, int rows, int cols,
			int initialX, int initialY, int xPad, int yPad) {
		SpringLayout layout;
		try {
			layout = (SpringLayout) parent.getLayout();
		}
		catch (ClassCastException exc) {
			System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
			return;
		}

		// Align all cells in each column and make them the same width.
		Spring x = Spring.constant(initialX);
		for (int c = 0; c < cols; c++) {
			Spring width = Spring.constant(0);
			for (int r = 0; r < rows; r++) {
				width = Spring.max(width, getConstraintsForCell(r, c, parent,
						cols).getWidth());
			}
			for (int r = 0; r < rows; r++) {
				SpringLayout.Constraints constraints = getConstraintsForCell(r,
						c, parent, cols);
				constraints.setX(x);
				constraints.setWidth(width);
			}
			x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
		}

		// Align all cells in each row and make them the same height.
		Spring y = Spring.constant(initialY);
		for (int r = 0; r < rows; r++) {
			Spring height = Spring.constant(0);
			for (int c = 0; c < cols; c++) {
				height = Spring.max(height, getConstraintsForCell(r, c, parent,
						cols).getHeight());
			}
			for (int c = 0; c < cols; c++) {
				SpringLayout.Constraints constraints = getConstraintsForCell(r,
						c, parent, cols);
				constraints.setY(y);
				constraints.setHeight(height);
			}
			y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
		}

		// Set the parent's size.
		SpringLayout.Constraints pCons = layout.getConstraints(parent);
		pCons.setConstraint(SpringLayout.SOUTH, y);
		pCons.setConstraint(SpringLayout.EAST, x);
	}


	/**
	 *  Gets the ConstraintsForCell attribute of the PanelOpciones class
	 *
	 *@param  row     Description of Parameter
	 *@param  col     Description of Parameter
	 *@param  parent  Description of Parameter
	 *@param  cols    Description of Parameter
	 *@return         The ConstraintsForCell value
	 */
	private static SpringLayout.Constraints getConstraintsForCell(int row,
			int col, Container parent, int cols) {
		SpringLayout layout = (SpringLayout) parent.getLayout();
		Component c = parent.getComponent(row * cols + col);
		return layout.getConstraints(c);
	}


//	private Component getPanelPrincipal2() {
//		JPanel panelDerecho = new JPanel(new GridLayout(opciones.size(), 1, 10, 10));
//		JPanel panelIzquierdo = new JPanel(new GridLayout(opciones.size(), 1));
//		JPanel panel = new JPanel(new BorderLayout());
//		for(int i=0; i<opciones.size(); i++){
//			panelIzquierdo.add(new JLabel(opciones.get(i)));
////			JPanel p2 = new JPanel();
////			p2.add(new JTextField(15));
//			panelDerecho.add(new JTextField(15));
//		}
//		panel.add(panelIzquierdo, BorderLayout.WEST);
//		panel.add(panelDerecho, BorderLayout.EAST);
//		return panel;
//	}
	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	private static class ListenerOpcion implements ActionListener {
		private String opcion;
		private JComponent component;
		private Hashtable<String, String> vChooser;
		private JComponent papa;


		/**
		 *  Constructor for the ListenerOpcion object
		 *
		 *@param  papa       Description of Parameter
		 *@param  opcion     Description of Parameter
		 *@param  component  Description of Parameter
		 *@param  vChooser   Description of Parameter
		 */
		public ListenerOpcion(JComponent papa, String opcion, JComponent component, Hashtable<String, String> vChooser) {
			this.opcion = opcion;
			this.component = component;
			this.vChooser = vChooser;
			this.papa = papa;
		}


		/**
		 *  Description of the Method
		 *
		 *@param  arg0  Description of Parameter
		 */
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser(".");
			if (chooser.showOpenDialog(papa) == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				vChooser.put(opcion, f.getAbsolutePath());

				if (component instanceof JTextField) {
					((JTextField) component).setText(f.getAbsolutePath());
				}
				else {
					((JPasswordField) component).setText(f.getAbsolutePath());
				}
			}
		}

	}
}
