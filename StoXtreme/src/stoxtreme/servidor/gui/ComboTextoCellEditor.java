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

public class ComboTextoCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
	private ArrayList<Component> lista;  
	private int actual;
	private DefaultListModel modelo;
	
	public ComboTextoCellEditor(int nParam, DefaultListModel modeloDistros){
		this.modelo = modeloDistros;
		lista = new ArrayList<Component>(nParam);
		for(int i=0; i<nParam; i++){
			lista.add(new JTextField());
		}
	}
	
	public Component getTableCellEditorComponent(
			JTable tabla, Object valor, boolean seleccionado, int row, int column) {
		actual = row;
		return lista.get(row);
	}

	public Object getCellEditorValue() {
		Component c = lista.get(actual);
		if (c instanceof JComboBox) {
			return ((JComboBox)c).getSelectedItem();
		}
		else{
			return ((JTextField)c).getText();
		}
	}
	
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int row, int arg5) {
		return lista.get(row);
	}

	public void setTexto(int rowIndex) {
		lista.set(rowIndex, new JTextField());
	}

	public void setCombo(int rowIndex) {
		Vector<Object> v = new Vector(modelo.size());
		for(int i=0; i<modelo.size(); i++){
			v.add(modelo.getElementAt(i));
		}
		
		JComboBox combo =  new JComboBox(v);
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				parar_seleccion();
			}
		});
		lista.set(rowIndex, combo);
	}
	
	private void parar_seleccion() {
		fireEditingStopped();
	}

	public void paraEdicion() {
		for(int i=0;i<lista.size(); i++){
			Component comp = lista.get(i);
			if(comp instanceof JTextField){
				String s = ((JTextField)comp).getText();
				((JTextField)comp).setText(s);
			}
		}
	}
	
	public void setValor(int fila, String valor){
		lista.set(fila, new JTextField(valor));
	}
	
	public void setDistribucion(int fila, String distribucion){
		setCombo(fila);
		((JComboBox)lista.get(fila)).setSelectedItem(distribucion);
	}
}
