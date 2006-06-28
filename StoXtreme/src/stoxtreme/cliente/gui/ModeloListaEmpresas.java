package stoxtreme.cliente.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ModeloListaEmpresas extends AbstractTableModel{
	
	private ArrayList listaEmpresas = new ArrayList();
	
	@SuppressWarnings("unchecked")
	public ModeloListaEmpresas(ArrayList lEmpresas){
		listaEmpresas=lEmpresas;
	}
	
	public int getRowCount() {
		return listaEmpresas.size();
	}

	public int getColumnCount() {
		return 1;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return listaEmpresas.get(rowIndex);
	}
	
	public String getColumnName(int columnIndex){
		switch(columnIndex){
		case 0: return "Empresa";
		default: return "Ver en grafico";
	}
}

}
//	private ArrayList listaEmpresas;
//	//private Hashtable colorEmpresa;
//	private ArrayList seleccionado;
//	//private Hashtable valoresAcciones;
//	//private Hashtable volumenAcciones;
//	
//	public ModeloListaEmpresas(ArrayList lEmpresas,Date fecha){
//		listaEmpresas = new ArrayList();
//		//colorEmpresa = new Hashtable();
//		seleccionado = new ArrayList();
////		valoresAcciones = new Hashtable();
////		volumenAcciones = new Hashtable();
//		if(lEmpresas.size()>1){
//			for(int i=0; i<lEmpresas.size(); i++){
//				String empresa = (String)lEmpresas.get(i);
////				valoresAcciones.put(empresa, new ValoresEmpresa(empresa,1));
////				volumenAcciones.put(empresa, new ValoresHistoricos(empresa,fecha));
//				listaEmpresas.add(empresa);
//			}
//			seleccionado.add(lEmpresas.get(0));
//		}
//	}
//	
//	public String getColumnName(int columnIndex){
//		switch(columnIndex){
//		case 0: return "Empresa";
//		case 1: return "Precio Actual";
//		default: return "Ver en grafico";
//		}
//	}
//	public int getColumnCount() {
//		return 3;
//	}
//
//	public int getRowCount() {
//		return listaEmpresas.size();
//	}
//
//	public Object getValueAt(int rowIndex, int columnIndex) {
//		String empresa = (String)listaEmpresas.get(rowIndex);
//		switch(columnIndex){
//			case 0: return empresa;
////			case 1: return new Double(((ValoresEmpresa)valoresAcciones.get(empresa)).ultimoPrecio());
//			default: return new Boolean(seleccionado.contains(empresa));
//		}
//		
//	}
//	public Class getColumnClass(int columnIndex){
//		switch(columnIndex){
//			case 0: return String.class; 
//			case 1: return Double.class; 
//			case 2: return Boolean.class;
//			default: return Object.class;
//		}
//	}
//	
//	public TableCellRenderer getRenderer(){
//		TableCellRenderer renderer = new DefaultTableCellRenderer(){
//			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//				Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//				if(column == 1 && ((ValoresEmpresa)valoresAcciones.get((String)listaEmpresas.get(row))).isTendenciaAlta()){
//					component.setForeground(Color.GREEN);
//				}
//				else{
//					component.setForeground(Color.RED);
//				}
//				return component;
//			}
//		};
//		return renderer;
//	}
//	
//	public boolean isCellEditable(int row, int colum){
//		return colum==2;
//	}
//	
//	public void cambiaSeleccion(int row){
//		System.out.println(row);
//	}
//	
//	public void setValueAt(Object value, int row, int col) {
//		if(col==2){
//			String empresa = (String)listaEmpresas.get(row);
//			if(((Boolean)value).booleanValue()){
//				seleccionado.add(empresa);
//			}
//			else{
//				seleccionado.remove(empresa);
//			}
//		}
//	}
//	
//	public void insertaValor(String empresa, double valor){
//		((ValoresEmpresa)valoresAcciones.get(empresa)).insertarSiguienteValor(valor);
//		fireTableCellUpdated(listaEmpresas.indexOf(empresa), 1);
//		if(textEscucha != null && empresaEscucha.equals(empresa)){
//			textEscucha.setText(Double.toString(valor));
//		}
//	}
//	public void insertaVolumen(String empresa, double volumen){
//		((ValoresHistoricos)volumenAcciones.get(empresa)).insertarSiguienteValor(volumen);
////		fireTableCellUpdated(listaEmpresas.indexOf(empresa),1);
////		if(textEscucha != null && empresaEscucha.equals(empresa)){
////			textEscucha.setText(Double.toString(valor));
////		}
//	}
//	public String empresaSeleccionada(){
//		if (seleccionado.size()>0){
//			return (String)seleccionado.get(0);
//		}
//		return null;
//	}
//	public TimeSeriesCollection getPreciosSeleccionados(){
//		TimeSeriesCollection series = new TimeSeriesCollection();
//		for(int i=0; i<seleccionado.size(); i++){
//			String empresa = (String)seleccionado.get(i);
//			series.addSeries((ValoresEmpresa)valoresAcciones.get(empresa));
//		}
//		return series;
//	}
//	public TimeSeriesCollection getVolumenSeleccionado(){
//		TimeSeriesCollection series = new TimeSeriesCollection();
//		for(int i=0; i<seleccionado.size(); i++){
//			String empresa = (String)seleccionado.get(i);
//			series.addSeries((ValoresHistoricos)volumenAcciones.get(empresa));
//		}
//		return series;
//	}
//	public ComboBoxModel getModeloComboBox() {
//		ComboBoxModel comboModel = new DefaultComboBoxModel(listaEmpresas.toArray());
//		return comboModel;
//	}
//
//	private String empresaEscucha = null;
//	private JTextField textEscucha = null;
//	
//	public void escuchaPrecio(String empresa, JTextField precioSeleccionado) {
//		empresaEscucha = empresa;
//		textEscucha = precioSeleccionado;
//	}
//
//	public void quitaEscuchaPrecio() {
//		empresaEscucha = null;
//		textEscucha = null;
//	}
//
//	public void ponPrecio(String empresa, JTextField precioSeleccionado) {
//		precioSeleccionado.setText(new Double(((ValoresEmpresa)valoresAcciones.get(empresa)).ultimoPrecio()).toString());
//	}
//}
