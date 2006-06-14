package stoxtreme.servidor.gui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

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
		JPanel panel = new JPanel(new SpringLayout());
		for (int i = 0; i < opciones.size(); i++) {
		    JLabel l = new JLabel(opciones.get(i), JLabel.TRAILING);
		    panel.add(l);
		    JTextField textField = new JTextField(10);
		    l.setLabelFor(textField);
		    panel.add(textField);
		}
		makeCompactGrid(panel,opciones.size(), 2, 6, 6, 6, 6);
		return panel;
	}
	private static SpringLayout.Constraints getConstraintsForCell(int row,
			int col, Container parent, int cols) {
		SpringLayout layout = (SpringLayout) parent.getLayout();
		Component c = parent.getComponent(row * cols + col);
		return layout.getConstraints(c);
	}
	
	public static void makeCompactGrid(Container parent, int rows, int cols,
			int initialX, int initialY, int xPad, int yPad) {
		SpringLayout layout;
		try {
			layout = (SpringLayout) parent.getLayout();
		} catch (ClassCastException exc) {
			System.err
					.println("The first argument to makeCompactGrid must use SpringLayout.");
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

	private Component getPanelPrincipal2() {
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
