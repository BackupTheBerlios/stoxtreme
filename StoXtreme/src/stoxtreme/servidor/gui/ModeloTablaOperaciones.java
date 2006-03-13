package stoxtreme.servidor.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import stoxtreme.interfaz_remota.Operacion;

public class ModeloTablaOperaciones extends AbstractTableModel{
	private ArrayList listaOperaciones;
	
	public ModeloTablaOperaciones(){
		super();
		listaOperaciones = new ArrayList();
	}
	
	public int getColumnCount() {
		return 6;
	}

	public int getRowCount() {
		return listaOperaciones.size();
	}
	public String getColumnName(int columnIndex){
		switch (columnIndex){
		case 0:
			return "ID Operacion";
		case 1:
			return "ID Usuario"; 
		case 2:
			return "ID Empresa";
		case 3:
			return "Cantidad";
		case 4:
			return "Precio";
		default:
			return "Tipo Operacion";
		}
	}
	public Object getValueAt(int rowIndex, int columnIndex) {
		Operacion o = (Operacion)listaOperaciones.get(rowIndex);
		
		switch (columnIndex){
			case 0:
				return new Integer(rowIndex);
			case 1:
				return o.getIdEmisor(); 
			case 2:
				return o.getEmpresa();
			case 3:
				return new Integer(o.getCantidad());
			case 4:
				return new Double(o.getPrecio());
			default:
				if(o.getTipoOp() == Operacion.COMPRA)
					return "Compra";
				else
					return "Venta";
		}
	}
	
	public void insertarOperacion(Operacion o){
		listaOperaciones.add(o);
		int fila = listaOperaciones.size()-1;
		fireTableRowsInserted(fila, fila);
	}
}
