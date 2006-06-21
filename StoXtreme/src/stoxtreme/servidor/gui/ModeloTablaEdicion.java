package stoxtreme.servidor.gui;
import java.awt.Component;
import java.util.Hashtable;
import java.util.TreeSet;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ModeloPsicologico;

public abstract class ModeloTablaEdicion extends AbstractTableModel implements TableCellRenderer{
	public static int MODELO_PSICOLOGICO = 0;
	public static int MODELO_SOCIAL = 1;
	
	private String id;
	private Hashtable<String, String> valores;
	private TreeSet<String> distribuidas;
	private ComboTextoCellEditor editor;
	
	private int tipo;
	
	private static String[] columnas = {
		"Parametro","Valor","Distribucion"	
	};
	
	public static String[] params_psicologicos = {
		"tiempo_espera",
		"numero_maximo_acciones_compra",
		"numero_minimo_acciones_compra",
		"numero_maximo_acciones_venta",
		"numero_minimo_acciones_venta",
		"numero_maximo_cancelaciones",
		"porcentaje_maximo_compra",
		"porcentaje_maximo_venta",
		"porcentaje_minimo_compra",
		"porcentaje_minimo_venta",
		"porcentaje_subida_precio",
		"porcentaje_bajada_precio",
		"precio_recomendacion",
		"precio_compra_recomendacion"
	};
	
	public static String[] params_social = {
		"fiabilidad_rumor",
		"numero_conocidos"
	};
	
	private String[] distribuciones = {
		"uniforme1", "normal2", "binomial3"	
	};

	public ModeloTablaEdicion(int tipo, ComboTextoCellEditor editor2, String id){
		this.tipo = tipo;
		valores = new Hashtable<String, String>();
		distribuidas = new TreeSet<String>();
		this.editor = editor2;
		this.id = id;
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex != 0;
	}
	
	public int getRowCount() {
		if(tipo == MODELO_PSICOLOGICO){
			return params_psicologicos.length;
		}
		else{
			return params_social.length;
		}
	}

	public int getColumnCount() {
		return 3;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0){
			if(tipo == MODELO_PSICOLOGICO){
				return params_psicologicos[rowIndex];
			}
			else{
				return params_social[rowIndex];
			}
		}
		else if(columnIndex == 1){
			String clave;
			if(tipo == MODELO_PSICOLOGICO){
				clave = params_psicologicos[rowIndex];
			}
			else{
				clave = params_social[rowIndex];
			}
			return valores.get(clave);
		}
		else{
			if(tipo == MODELO_PSICOLOGICO){
				return distribuidas.contains(params_psicologicos[rowIndex]);
			}
			else{
				return distribuidas.contains(params_social[rowIndex]);
			}
		}
	}
	
	public String getColumnName(int column) {
		return columnas[column];
	}
	
	public Class<?> getColumnClass(int columnIndex) {
		return (columnIndex!=2)?Object.class:Boolean.class;
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if("".equals(aValue)){
			return;
		}
		if(columnIndex == 1){
			if(tipo == MODELO_PSICOLOGICO){
				valores.put(params_psicologicos[rowIndex], aValue.toString());
			}
			else{
				valores.put(params_social[rowIndex], aValue.toString());
			}
			System.out.println(aValue);
		}
		else if(columnIndex == 2){
			if(tipo == MODELO_PSICOLOGICO){
				if(distribuidas.contains(params_psicologicos[rowIndex])){
					distribuidas.remove(params_psicologicos[rowIndex]);
					editor.setTexto(rowIndex);
				}
				else{
					distribuidas.add(params_psicologicos[rowIndex]);
					editor.setCombo(rowIndex);
				}
			}
			else{
				if(distribuidas.contains(params_social[rowIndex])){
					distribuidas.remove(params_social[rowIndex]);
					editor.setTexto(rowIndex);
				}
				else{
					distribuidas.add(params_social[rowIndex]);
					editor.setCombo(rowIndex);
				}
			}
		}
		actualiza();
	}

	public boolean isDistribucion(int row) {
		if(tipo == MODELO_PSICOLOGICO){
			return distribuidas.contains(params_psicologicos[row]);
		}
		else{
			return distribuidas.contains(params_social[row]);
		}
	}

	public Component getTableCellRendererComponent(
			JTable table, Object value, 
			boolean isSelected, boolean hasFocus, 
			int row, int column) {
		String v;
		if(tipo == MODELO_PSICOLOGICO){
			v = valores.get(params_psicologicos[row]);
		}
		else{
			v = valores.get(params_social[row]);
		}
		return new JLabel(v);
	}
	
	public String toString(){
		return id;
	}
	
	public abstract void actualiza();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
