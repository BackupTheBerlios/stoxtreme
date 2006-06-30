package stoxtreme.servidor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import stoxtreme.servidor.Servidor;
import stoxtreme.servidor.eventos.evaluador.ParseException;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public abstract class ModeloTablaEventos extends AbstractTableModel implements ActionListener {
	/**
	 *  Description of the Field
	 */
	protected ArrayList<Evento> listaEventos;
	private ArrayList<Boolean> eventosActivos;

	private JTable lista;
	/**
	 *  Description of the Field
	 */
	public static final String INSERTAR_EVENTO = "Insertar Evento";
	/**
	 *  Description of the Field
	 */
	public static final String CANCELAR_EVENTO = "Cancelar Evento";


	/**
	 *  Constructor for the ModeloTablaEventos object
	 */
	public ModeloTablaEventos() {
		listaEventos = new ArrayList<Evento>();
		eventosActivos = new ArrayList<Boolean>();
	}


	/**
	 *  Sets the ValueAt attribute of the ModeloTablaEventos object
	 *
	 *@param  aValue  The new ValueAt value
	 *@param  row     The new ValueAt value
	 *@param  column  The new ValueAt value
	 */
	public void setValueAt(Object aValue, int row, int column) {
		if (column == 2) {
			cambiaSeleccion(listaEventos.get(row).getCondicion(),
					listaEventos.get(row).getAccion(),
					((Boolean) aValue).booleanValue());
			eventosActivos.set(row, (Boolean) aValue);
		}
	}


	/**
	 *  Sets the Lista attribute of the ModeloTablaEventos object
	 *
	 *@param  lista  The new Lista value
	 */
	public void setLista(JTable lista) {
		this.lista = lista;
	}


	/**
	 *  Gets the ColumnName attribute of the ModeloTablaEventos object
	 *
	 *@param  columnIndex  Description of Parameter
	 *@return              The ColumnName value
	 */
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return "Condicion";
			case 1:
				return "Accion";
			default:
				return "Activo?";
		}
	}


	/**
	 *  Gets the RowCount attribute of the ModeloTablaEventos object
	 *
	 *@return    The RowCount value
	 */
	public int getRowCount() {
		return listaEventos.size();
	}


	/**
	 *  Gets the ColumnCount attribute of the ModeloTablaEventos object
	 *
	 *@return    The ColumnCount value
	 */
	public int getColumnCount() {
		return 3;
	}


	/**
	 *  Gets the ValueAt attribute of the ModeloTablaEventos object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The ValueAt value
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return listaEventos.get(rowIndex).getCondicion();
			case 1:
				return listaEventos.get(rowIndex).getAccion();
			default:
				return eventosActivos.get(rowIndex);
		}
	}


	/**
	 *  Gets the ColumnClass attribute of the ModeloTablaEventos object
	 *
	 *@param  indice  Description of Parameter
	 *@return         The ColumnClass value
	 */
	public Class getColumnClass(int indice) {
		return (indice == 2) ? Boolean.class : String.class;
	}


	/**
	 *  Gets the CellEditable attribute of the ModeloTablaEventos object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The CellEditable value
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 2;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  condicion  Description of Parameter
	 *@param  accion     Description of Parameter
	 *@param  valor      Description of Parameter
	 */
	public abstract void cambiaSeleccion(String condicion, String accion, boolean valor);


	/**
	 *  Description of the Method
	 *
	 *@param  condicion  Description of Parameter
	 *@param  accion     Description of Parameter
	 *@param  unaVez     Description of Parameter
	 *@param  activo     Description of Parameter
	 */
	public abstract void insertarEvento(String condicion, String accion, boolean unaVez, boolean activo);


	/**
	 *  Adds a feature to the Evento attribute of the ModeloTablaEventos object
	 *
	 *@param  condicion  The feature to be added to the Evento attribute
	 *@param  accion     The feature to be added to the Evento attribute
	 *@param  unavez     The feature to be added to the Evento attribute
	 *@param  activo     The feature to be added to the Evento attribute
	 */
	public void addEvento(String condicion, String accion, boolean unavez, boolean activo) {
		listaEventos.add(new Evento(condicion, accion));
		eventosActivos.add(new Boolean(activo));
		this.fireTableRowsInserted(listaEventos.size() - 1, listaEventos.size() - 1);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  evento  Description of Parameter
	 */
	public void quitarEvento(Evento evento) {
		boolean cambiado = false;
		for (int i = listaEventos.size() - 1; i >= 0; i--) {
			if (listaEventos.get(i).equals(evento)) {
				listaEventos.remove(i);
				cambiado = true;
			}
		}
		if (cambiado) {
			fireTableDataChanged();
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	public void actionPerformed(ActionEvent e) {
		if (((JButton) e.getSource()).getText().equals(INSERTAR_EVENTO)) {
			DialogoInsercionEvento diag = new DialogoInsercionEvento(Servidor.getInstance().getGUI());
			diag.setVisible(true);
			if (diag.isAceptado()) {
				insertarEvento(diag.getCondicion(), diag.getEvento(), diag.isUnaVez(), diag.isIniciarActivo());
			}
		}
		else if (((JButton) e.getSource()).getText().equals(CANCELAR_EVENTO)) {
			int[] indices = lista.getSelectedRows();
			ArrayList<Evento> descripciones = new ArrayList<Evento>();

			for (int i = 0; i < indices.length; i++) {
				descripciones.add(listaEventos.get(indices[i]));
			}

			for (int i = 0; i < descripciones.size(); i++) {
				quitarEvento(descripciones.get(i));
			}
		}
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	protected class Evento {
		private String condicion;
		private String accion;


		/**
		 *  Constructor for the Evento object
		 *
		 *@param  condicion  Description of Parameter
		 *@param  accion     Description of Parameter
		 */
		public Evento(String condicion, String accion) {
			this.accion = accion;
			this.condicion = condicion;
		}


		/**
		 *  Sets the Accion attribute of the Evento object
		 *
		 *@param  accion  The new Accion value
		 */
		public void setAccion(String accion) {
			this.accion = accion;
		}


		/**
		 *  Sets the Condicion attribute of the Evento object
		 *
		 *@param  condicion  The new Condicion value
		 */
		public void setCondicion(String condicion) {
			this.condicion = condicion;
		}


		/**
		 *  Gets the Accion attribute of the Evento object
		 *
		 *@return    The Accion value
		 */
		public String getAccion() {
			return accion;
		}


		/**
		 *  Gets the Condicion attribute of the Evento object
		 *
		 *@return    The Condicion value
		 */
		public String getCondicion() {
			return condicion;
		}
	}
}
