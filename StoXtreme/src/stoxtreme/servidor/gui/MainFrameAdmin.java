package stoxtreme.servidor.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import stoxtreme.servidor.Servidor;

public class MainFrameAdmin extends JFrame{
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
	private Servidor servidor;
	
	static{
		try{
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public MainFrameAdmin(){
		super("Stock Xtreme: Administrador");
	}
	public void init(){
		JTabbedPane tabbed = new JTabbedPane();
		tabbed.insertTab("Sesion", null, getPanelSesion(), "Control de la sesion", 0);
		tabbed.insertTab("Evolución bolsa", null, getPanelControl(), "Control de los usuarios", 1);
		tabbed.insertTab("Eventos", null, getPanelEventos(), "Control de los eventos", 2);
		tabbed.insertTab("Control de Agentes", null, getPanelAgentes(), "Control de los agentes",3);
		getContentPane().add(tabbed);
		setSize(new Dimension(800, 600));
	}
	
	private Component getPanelAgentes() {
		JPanel panel = new JPanel();
		return panel;
	}
	private Component getPanelSesion() {
		JSplitPane sesion = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT,
				getListaSesiones(),
				getOpcionesSesion()
		);
		return sesion;
	}
	private Component getOpcionesSesion() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(getPanelOpcionesSuperior()), BorderLayout.CENTER);
		panel.add(getPanelOpcionesInferior(), BorderLayout.SOUTH);
		FakeInternalFrame fr = new FakeInternalFrame("Configuracion de la sesion",panel);
		
		return fr;
	}
	
	private Component getPanelOpcionesInferior() {
		JPanel panel = new JPanel();
		JButton boton1 = new JButton("Iniciar nueva sesion");
		panel.add(boton1);
		JButton boton2 = new JButton("Cerrar sesion antigua");
		panel.add(boton2);
		JButton boton3 = new JButton("Finalizar preapertura");
		panel.add(boton3);
		JButton boton4 = new JButton("Finalizar sesion");
		panel.add(boton4);
		return panel;
	}
	
	private Component getPanelOpcionesSuperior() {
		int c = 100;
		JPanel panel = new JPanel(new GridLayout(13, 1));
		panel.add(new ElementoConfig("Numero de empresas        ", false));
		panel.add(new ElementoConfig("Velocidad de simulación   ", false));
		panel.add(new ElementoConfig("Tiempo de fluctuacion     ", false));
		panel.add(new ElementoConfig("Sesión continua           ", false));
		panel.add(new ElementoConfig("Etapa de preapertura      ", false));
		panel.add(new ElementoConfig("Etapa de cierre           ", false));
		panel.add(new ElementoConfig("Directorio de Información ", true));
		return panel;
	}
	
	private class ElementoConfig extends JPanel{
		JTextField field;
		public ElementoConfig(String label, boolean filechooser) {
			add(new JLabel(label));
			field = new JTextField(50-label.length());
			add(field);
			
			if(filechooser){
				JButton boton = new JButton("...");
				add(boton);
			}
		}
	}
	
	DefaultListModel modeloSesiones;
	private Component getListaSesiones() {
		modeloSesiones = new DefaultListModel();  
		JScrollPane panel = new JScrollPane(new JList(modeloSesiones));
		FakeInternalFrame fr = new FakeInternalFrame("Historico de sesiones", panel);
		return fr;
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
		
		panelPrincipal.setDividerLocation(300);
		panelSecundario.setDividerLocation(300);
		
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
		JButton botonInsertar = new JButton(ModeloTablaEventos.INSERTAR_EVENTO);
		botonInsertar.addActionListener(modeloEventos);
		panelDerAbajo.add(botonInsertar);
		JButton botonCancelar = new JButton(ModeloTablaEventos.CANCELAR_EVENTO);
		botonCancelar.addActionListener(modeloEventos);
		panelDerAbajo.add(botonCancelar);
		
		JTable tablaEventos = new JTable(modeloEventos);
		tablaEventos.getColumn(tablaEventos.getColumnName(2)).setMaxWidth(40);

		JScrollPane panelDerArriba = new JScrollPane(tablaEventos);
		panelDer.add(panelDerAbajo, BorderLayout.SOUTH);
		panelDer.add(panelDerArriba, BorderLayout.CENTER);
		
		JSplitPane panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				new FakeInternalFrame("Variables del sistema",panelIzq), 
				new FakeInternalFrame("Control de Eventos", panelDer));
		panelPrincipal.setDividerLocation(350);
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
	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}
	public Servidor getServidor(){
		return servidor;
	}
}
