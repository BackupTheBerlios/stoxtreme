package stoxtreme.servidor.superusuario.GUI;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class ModeloTablaPrecioAcciones extends AbstractTableModel{
	private Hashtable precioAcciones;
	private ArrayList listaEmpresas;
	private Hashtable colorEmpresa;
	
	public ModeloTablaPrecioAcciones(){
		precioAcciones = new Hashtable();
		listaEmpresas = new ArrayList();
		colorEmpresa = new Hashtable();
	}
	
	public String getColumnName(int columnIndex){
		switch(columnIndex){
		case 0: return "Empresa";
		default: return "Precio Actual";
		}
	}
	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return listaEmpresas.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		String empresa = (String)listaEmpresas.get(rowIndex);
		if(columnIndex == 0){
			return empresa;
		}
		else{
			return precioAcciones.get(empresa);
		}
	}
	
	public void insertarEmpresa(String empresa){
		listaEmpresas.add(empresa);
		precioAcciones.put(empresa, new Double(0.0));
		colorEmpresa.put(empresa, Color.black);
		this.fireTableRowsInserted(listaEmpresas.size()-1, listaEmpresas.size()-1);
	}
	
	public void cambiaPrecioAccion(String empresa, double nuevoPrecio){
		precioAcciones.put(empresa, new Double(nuevoPrecio));
		int index = listaEmpresas.indexOf(empresa);
		this.fireTableCellUpdated(index, 1);
	}
	
	public void cambiaColor(String empresa, Color color){
		colorEmpresa.put(empresa, color);
		int index = listaEmpresas.indexOf(empresa);
		this.fireTableCellUpdated(index, 1);
	}
	
	public TableCellRenderer getRenderer(){
		TableCellRenderer renderer = new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if(column == 1){
					component.setForeground((Color)colorEmpresa.get(listaEmpresas.get(row)));
				}
				return component;
			}
		};
		return renderer;
	}
}
