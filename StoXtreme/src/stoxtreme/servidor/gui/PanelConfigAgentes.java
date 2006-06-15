package stoxtreme.servidor.gui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import stoxtreme.servidor.gui.FakeInternalFrame;

public class PanelConfigAgentes extends JPanel{
	DefaultTableModel modeloTabla = new DefaultTableModel();
	
	public PanelConfigAgentes(){
		try{
			init();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		add(getPanelArriba(), BorderLayout.CENTER);
	}

	private Component getPanelArriba() {
		JSplitPane panel = new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT,
			getPanelIzquierdo(),
			getPanelDerecho()
		);
		return panel;
	}
	
	private Component getPanelDerecho() {
//		JSplitPane split = new JSplitPane(
//				JSplitPane.VERTICAL_SPLIT,
//				new FakeInternalFrame("Edicion",getPanelEditor()),
//				getPanelDerechaAbajo()
//		);
//		split.setDividerLocation(300);
//		return split;
		return new FakeInternalFrame("Edicion",getPanelEditor());
	}

	private Component getPanelDerechaAbajo() {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(new FakeInternalFrame("Modelo Psicologico",getPanelModeloPsicologico()));
		panel.add(new FakeInternalFrame("Modelo Social",getPanelModeloSocial()));
		return panel;
	}

	private Component getPanelBotones() {
		JPanel panel = new JPanel();
		panel.add(new JButton("Cargar Fichero"));
		panel.add(new JButton("Guardar Configuracion"));
		return panel;
	}
	
	private Component getPanelEditor() {
		JTable tabla = new JTable(new EditorTableModel(EditorTableModel.MODELO_PSICOLOGICO));
		JScrollPane panel = new JScrollPane(tabla);
		return panel;
	}

	private Component getPanelIzquierdo() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getPanelIzquierdaArriba(), BorderLayout.CENTER);
		panel.add(getPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}

	private Component getPanelIzquierdaArriba() {
		
//		JSplitPane panel = new JSplitPane(
//			JSplitPane.VERTICAL_SPLIT,
//			getPanelIzquierdaArribaArriba(),
//			getPanelIzquierdaArribaAbajo()
//		);
		JPanel panel = new JPanel(new GridLayout(2,1));
		panel.add(getPanelIzquierdaArribaArriba());
		panel.add(getPanelIzquierdaArribaAbajo());
		return panel;
	}
	
	private Component getPanelIzquierdaArribaAbajo() {
		JTabbedPane panel = new JTabbedPane();
		panel.insertTab("Social", null, getPanelModeloSocial(), "Modelo Social", 0);
		panel.insertTab("Psicologico", null, getPanelModeloSocial(), "Modelo Psicologico", 1);
		panel.insertTab("Distribuciones", null, getPanelDistribuciones(), "Distribuciones de probabilidad", 2);
		FakeInternalFrame frame = new FakeInternalFrame("Modelo", panel);
		
		return frame;
	}

	private Component getPanelDistribuciones() {
		JPanel panel = new JPanel();
		
		return panel;
	}

	private Component getPanelIzquierdaArribaArriba() {
		ArrayList<String> ops = new ArrayList<String>();
		ops.add("Numero de agentes");
		ops.add("Fichero de historicos");
		ops.add("Mas opciones");
		ops.add("Cosas varias");
		ops.add("Otra opcion");
		ops.add("Otra opcion");
		ops.add("Otra opcion");
		ops.add("Otra opcion");
		
		PanelOpciones panel = new PanelOpciones(ops){
			protected void ejecuta(String opcion) {
				
			}
		};
		FakeInternalFrame frame = new FakeInternalFrame("Parametros", panel);
		
		return frame;
	}

	private DefaultListModel modeloListaSocial = new DefaultListModel();
	private DefaultListModel modeloListaPsicologica = new DefaultListModel();
	
	private Component getPanelModeloSocial() {
		JList lista = new JList(modeloListaSocial); 
		JScrollPane panel = new JScrollPane(lista);
		
		return panel;
	}

	private Component getPanelModeloPsicologico() {
		JList lista = new JList(modeloListaPsicologica);
		JScrollPane panel = new JScrollPane(lista);
		
		return panel;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new PanelConfigAgentes());
		frame.setSize(new Dimension(800,600));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
