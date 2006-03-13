package stoxtreme.servidor.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import stoxtreme.interfaz_remota.Operacion;

public class MainFrameAdmin extends JFrame{
	/**/
	private static MainFrameAdmin _instance = new MainFrameAdmin();
	public static MainFrameAdmin getInstance(){
		return _instance;
	}
	/**/
	private ModeloTablaOperaciones modeloOperaciones; 
	private ModeloListaUsuariosConectados modeloUsuarios; 
	private ModeloTablaPrecioAcciones modeloPrecios;
	private ModeloTablaVariables modeloVariables;
	private ModeloTablaEventos modeloEventos;
	/**/
	private JSplitPane panelPrincipal;
	private JSplitPane panelSecundario;
	private FakeInternalFrame panelDerecha;
	private FakeInternalFrame panelIzqArriba;
	private FakeInternalFrame panelIzqAbajo;
	
	static{
		try{
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private MainFrameAdmin(){
		super("Stock Xtreme: Administrador");
	}
	public void init(){
		/*CREAMOS LOS MODELOS*/
		modeloOperaciones = new ModeloTablaOperaciones(); 
		modeloUsuarios = new ModeloListaUsuariosConectados(); 
		modeloPrecios = new ModeloTablaPrecioAcciones();
		modeloVariables = new ModeloTablaVariables();
		modeloEventos = new ModeloTablaEventos();
		/**/
		JTabbedPane tabbed = new JTabbedPane();
		tabbed.insertTab("Control", null, getPanelControl(), "Control de los usuarios", 0);
		tabbed.insertTab("Eventos", null, getPanelEventos(), "Control de los eventos", 1);
		getContentPane().add(tabbed);
		setSize(new Dimension(800, 600));
	}
	
	public Component getPanelControl(){
		JScrollPane panelScrollDerecha = new JScrollPane(new JTable(modeloOperaciones));
		panelScrollDerecha.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelDerecha = new FakeInternalFrame("Log de Operaciones", panelScrollDerecha);
		
		JList lista = new JList(modeloUsuarios);
		lista.setCellRenderer(modeloUsuarios.getListCellRenderer());
		
		JScrollPane panelScrollIzqArriba = new JScrollPane(lista);
		panelScrollIzqArriba.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelIzqArriba = new FakeInternalFrame("Usuarios", panelScrollIzqArriba);
		
		JTable tablaPrecios = new JTable(modeloPrecios);
		tablaPrecios.getColumn(tablaPrecios.getColumnName(1)).setCellRenderer(modeloPrecios.getRenderer());
		
		JScrollPane panelScrollIzqAbajo = new JScrollPane(tablaPrecios);
		panelScrollIzqAbajo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelIzqAbajo = new FakeInternalFrame("Precios en bolsa", panelScrollIzqAbajo);
		
		panelSecundario = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelIzqAbajo, panelIzqArriba);
		panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelSecundario, panelDerecha);
		
		panelIzqArriba.setPreferredSize(new Dimension(250, 300));
		panelIzqAbajo.setPreferredSize(new Dimension(250, 300));
		panelDerecha.setPreferredSize(new Dimension(550, 600));
		
		return panelPrincipal;
	}
	
	public Component getPanelEventos(){
		JTable tablaVariables = new JTable(modeloVariables);
		tablaVariables.getColumn(tablaVariables.getColumnName(1)).setPreferredWidth(100);
		JScrollPane panelIzq = new JScrollPane(tablaVariables);
		JPanel panelDer = new JPanel(new BorderLayout());
		JPanel panelDerAbajo = new JPanel();
		panelDerAbajo.add(new JButton("InsertarEvento"));
		panelDerAbajo.add(new JButton("CancelarEvento"));
		
		JTable tablaEventos = new JTable(modeloEventos);
		tablaEventos.getColumn(tablaEventos.getColumnName(2)).setMaxWidth(40);

		JScrollPane panelDerArriba = new JScrollPane(tablaEventos);
		panelDer.add(panelDerAbajo, BorderLayout.SOUTH);
		panelDer.add(panelDerArriba, BorderLayout.CENTER);
		
		JSplitPane panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				new FakeInternalFrame("Variables del sistema",panelIzq), 
				new FakeInternalFrame("Control de Eventos", panelDer));
		
		panelIzq.setPreferredSize(new Dimension(200, 600));
		panelDer.setPreferredSize(new Dimension(600, 600));
		return panelPrincipal;
	}
	public ModeloTablaEventos getModeloEventos() {
		return modeloEventos;
	}
	public void setModeloEventos(ModeloTablaEventos modeloEventos) {
		this.modeloEventos = modeloEventos;
	}
	public ModeloTablaOperaciones getModeloOperaciones() {
		return modeloOperaciones;
	}
	public void setModeloOperaciones(ModeloTablaOperaciones modeloOperaciones) {
		this.modeloOperaciones = modeloOperaciones;
	}
	public ModeloTablaPrecioAcciones getModeloPrecios() {
		return modeloPrecios;
	}
	public void setModeloPrecios(ModeloTablaPrecioAcciones modeloPrecios) {
		this.modeloPrecios = modeloPrecios;
	}
	public ModeloListaUsuariosConectados getModeloUsuarios() {
		return modeloUsuarios;
	}
	public void setModeloUsuarios(ModeloListaUsuariosConectados modeloUsuarios) {
		this.modeloUsuarios = modeloUsuarios;
	}
	public ModeloTablaVariables getModeloVariables() {
		return modeloVariables;
	}
	public void setModeloVariables(ModeloTablaVariables modeloVariables) {
		this.modeloVariables = modeloVariables;
	}
}
