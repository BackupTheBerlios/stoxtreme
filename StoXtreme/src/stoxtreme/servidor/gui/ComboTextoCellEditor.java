package stoxtreme.servidor.gui;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ComboTextoCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private ArrayList<Component> lista;
	private int actual;
	private DefaultListModel modelo;


	/**
	 *  Constructor for the ComboTextoCellEditor object
	 *
	 *@param  nParam         Description of Parameter
	 *@param  modeloDistros  Description of Parameter
	 */
	public ComboTextoCellEditor(int nParam, DefaultListModel modeloDistros) {
		this.modelo = modeloDistros;
		lista = new ArrayList<Component>(nParam);
		for (int i = 0; i < nParam; i++) {
			lista.add(new JTextField());
		}
	}


	/**
	 *  Sets the Texto attribute of the ComboTextoCellEditor object
	 *
	 *@param  rowIndex  The new Texto value
	 */
	public void setTexto(int rowIndex) {
		lista.set(rowIndex, new JTextField());
	}


	/**
	 *  Sets the Combo attribute of the ComboTextoCellEditor object
	 *
	 *@param  rowIndex  The new Combo value
	 */
	public void setCombo(int rowIndex) {
		Vector<Object> v = new Vector(modelo.size());
		for (int i = 0; i < modelo.size(); i++) {
			v.add(modelo.getElementAt(i));
		}

		JComboBox combo = new JComboBox(v);
		combo.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							parar_seleccion();
						}
					});
		lista.set(rowIndex, combo);
	}


	/**
	 *  Sets the Valor attribute of the ComboTextoCellEditor object
	 *
	 *@param  fila   The new Valor value
	 *@param  valor  The new Valor value
	 */
	public void setValor(int fila, String valor) {
		lista.set(fila, new JTextField(valor));
	}


	/**
	 *  Sets the Distribucion attribute of the ComboTextoCellEditor object
	 *
	 *@param  fila          The new Distribucion value
	 *@param  distribucion  The new Distribucion value
	 */
	public void setDistribucion(int fila, String distribucion) {
		setCombo(fila);
		((JComboBox) lista.get(fila)).setSelectedItem(distribucion);
	}


	/**
	 *  Gets the TableCellEditorComponent attribute of the ComboTextoCellEditor
	 *  object
	 *
	 *@param  tabla         Description of Parameter
	 *@param  valor         Description of Parameter
	 *@param  seleccionado  Description of Parameter
	 *@param  row           Description of Parameter
	 *@param  column        Description of Parameter
	 *@return               The TableCellEditorComponent value
	 */
	public Component getTableCellEditorComponent(
			JTable tabla, Object valor, boolean seleccionado, int row, int column) {
		actual = row;
		return lista.get(row);
	}


	/**
	 *  Gets the CellEditorValue attribute of the ComboTextoCellEditor object
	 *
	 *@return    The CellEditorValue value
	 */
	public Object getCellEditorValue() {
		Component c = lista.get(actual);
		if (c instanceof JComboBox) {
			return ((JComboBox) c).getSelectedItem();
		}
		else {
			return ((JTextField) c).getText();
		}
	}


	/**
	 *  Gets the TableCellRendererComponent attribute of the ComboTextoCellEditor
	 *  object
	 *
	 *@param  arg0  Description of Parameter
	 *@param  arg1  Description of Parameter
	 *@param  arg2  Description of Parameter
	 *@param  arg3  Description of Parameter
	 *@param  row   Description of Parameter
	 *@param  arg5  Description of Parameter
	 *@return       The TableCellRendererComponent value
	 */
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int row, int arg5) {
		return lista.get(row);
	}


	/**
	 *  Description of the Method
	 */
	public void paraEdicion() {
		for (int i = 0; i < lista.size(); i++) {
			Component comp = lista.get(i);
			if (comp instanceof JTextField) {
				String s = ((JTextField) comp).getText();
				((JTextField) comp).setText(s);
			}
		}
	}


	/**
	 *  Description of the Method
	 */
	private void parar_seleccion() {
		fireEditingStopped();
	}
}
