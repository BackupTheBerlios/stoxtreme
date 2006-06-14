package stoxtreme.servidor.gui;
import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;

import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ModeloPsicologico;

public class EditorTableModel extends AbstractTableModel{
	public static int MODELO_PSICOLOGICO = 0;
	public static int MODELO_SOCIAL = 1;
	
	private Hashtable<String, String> valores;
	private int tipo;
	
	private String[] params_psicologicos = {
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
	
	private String[] params_social = {
		"fiabilidad_rumor",
		"numero_conocidos"
	};

	public EditorTableModel(int tipo){
		this.tipo = tipo;
		valores = new Hashtable();
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
		return 2;
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
		else {
			String clave;
			if(tipo == MODELO_PSICOLOGICO){
				clave = params_psicologicos[rowIndex];
			}
			else{
				clave = params_social[rowIndex];
			}
			return valores.get(clave);
		}
	}

	public String getColumnName(int column) {
		if(column == 0){
			return "Parametro";
		}
		else{
			return "Valor";
		}
	}
}
