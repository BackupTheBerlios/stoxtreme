package stoxtreme.servidor.gui;
import stoxtreme.servidor.gui.ComboTextoCellEditor;
import stoxtreme.servidor.gui.EditorTableModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import stoxtreme.servidor.gui.FakeInternalFrame;

public class PanelConfigAgentes extends JPanel{
	private DefaultTableModel modeloTabla = new DefaultTableModel();
	private DefaultListModel modeloListaSocial = new DefaultListModel();
	private DefaultListModel modeloListaPsicologica = new DefaultListModel();
	private DefaultListModel modeloListaDistribucion = new DefaultListModel();
	private DefaultTreeModel modeloComportamientos = new DefaultTreeModel(new DefaultMutableTreeNode("Comportamientos"));
	
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
		panel.setDividerLocation(350);
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

	private Component getPanelBotones() {
		JPanel panel = new JPanel();
		panel.add(new JButton("Cargar Fichero"));
		panel.add(new JButton("Guardar Configuracion"));
		return panel;
	}
	
	public void actualiza2(){
		tabla.repaint();
	}
	JTable tabla;
	
	private Component getPanelEditor() {
		ComboTextoCellEditor editor = new ComboTextoCellEditor(EditorTableModel.params_psicologicos.length, EditorTableModel.params_psicologicos);
		
		EditorTableModel modelo = new EditorTableModel(EditorTableModel.MODELO_PSICOLOGICO, editor){
			public void actualiza() {
				actualiza2();
			}
		};
		
		tabla = new JTable(modelo);
		tabla.getColumnModel().getColumn(1).setCellEditor(editor);
		tabla.getColumnModel().getColumn(1).setCellRenderer(editor);
		
		tabla.getColumn(tabla.getColumnName(2)).setMaxWidth(60);

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
		panel.insertTab("Comportamientos", null, getPanelComportamientos(), "Comportamientos", 0);
		panel.insertTab("Social", null, getPanelModeloSocial(), "Modelo Social", 1);
		panel.insertTab("Psicologico", null, getPanelModeloPsicologico(), "Modelo Psicologico", 2);
		panel.insertTab("Distribuciones", null, getPanelDistribuciones(), "Distribuciones de probabilidad", 3);
		FakeInternalFrame frame = new FakeInternalFrame("Modelo", panel);
		
		return frame;
	}
	
	
	private Component getPanelComportamientos() {
		JPanel panel = new JPanel(new BorderLayout());
		JTree arbolComportamientos = new JTree(modeloComportamientos);
		JScrollPane scroll = new JScrollPane(arbolComportamientos);
		
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		botones.add(new JButton("+"));
		botones.add(new JButton("-"));
		
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(botones, BorderLayout.SOUTH);
		return panel;
	}

	private Component getPanelDistribuciones() {
		JPanel panel = new JPanel(new BorderLayout());
		JList lista = new JList(modeloListaDistribucion); 
		JScrollPane scroll = new JScrollPane(lista);
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botones.add(new JButton("+"));
		botones.add(new JButton("-"));
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(botones, BorderLayout.SOUTH);
		return panel;
	}

	private Component getPanelIzquierdaArribaArriba() {
		ArrayList<String> ops = new ArrayList<String>();
		ops.add("Numero de agentes");
		ops.add("Velocidad del sistema");
		ops.add("Dinero inicial");
		ops.add("Gasto máximo");
		ops.add("Ratio respawn");
		ops.add("Atenuacion rumor");
		ops.add("Fichero historicos");
		ops.add("Ficheros informacion");
		
		ArrayList<String> choosers = new ArrayList<String>();
		choosers.add("Fichero historicos");
		choosers.add("Ficheros informacion");
		
		PanelOpciones panel = new PanelOpciones(ops, null, choosers);
		FakeInternalFrame frame = new FakeInternalFrame("Parametros", panel);
		
		return frame;
	}

	
	private Component getPanelModeloSocial() {
		JPanel panel = new JPanel(new BorderLayout());
		JList lista = new JList(modeloListaSocial); 
		JScrollPane scroll = new JScrollPane(lista);
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botones.add(new JButton("+"));
		botones.add(new JButton("-"));
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(botones, BorderLayout.SOUTH);
		return panel;
	}

	private Component getPanelModeloPsicologico() {
		JPanel panel = new JPanel(new BorderLayout());
		JList lista = new JList(modeloListaPsicologica); 
		JScrollPane scroll = new JScrollPane(lista);
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botones.add(new JButton("+"));
		botones.add(new JButton("-"));
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(botones, BorderLayout.SOUTH);
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
