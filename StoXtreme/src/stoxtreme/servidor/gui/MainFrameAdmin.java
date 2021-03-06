package stoxtreme.servidor.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import stoxtreme.servidor.Servidor;

/**
 *  Description of the Class
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
@SuppressWarnings("serial")
public class MainFrameAdmin extends JFrame {

	JTabbedPane tabbed;

	JList listaUsuarios;

	DefaultListModel modeloSesiones;
	private ModeloTablaOperaciones modeloOperaciones;
	private ModeloListaUsuariosConectados modeloUsuarios;
	private ModeloTablaPrecioAcciones modeloPrecios;
	private ModeloTablaVariables modeloVariables;
	private ModeloTablaEventos modeloEventos;
	private PanelOpciones opciones;
	/*
	 *
	 */
	private JSplitPane panelPrincipal;
	private FakeInternalFrame panelDerecha;
	private FakeInternalFrame panelIzqAbajo;
	private Servidor servidor;
	private JButton botonFinalizarSimulacion;
	private static final String FICH_CONFAGENTES = "./conf/confAgentes.xml";
	private static Image LOGO = Toolkit.getDefaultToolkit().getImage("logoPeke.png");


	/**
	 *  Constructor for the MainFrameAdmin object
	 */
	public MainFrameAdmin() {
		super("Stock Xtreme: Administrador");
	}


	/**
	 *  Sets the ModeloEventos attribute of the MainFrameAdmin object
	 *
	 *@param  modeloEventos  The new ModeloEventos value
	 */
	public void setModeloEventos(ModeloTablaEventos modeloEventos) {
		this.modeloEventos = modeloEventos;
	}


	/**
	 *  Sets the ModeloOperaciones attribute of the MainFrameAdmin object
	 *
	 *@param  modeloOperaciones  The new ModeloOperaciones value
	 */
	public void setModeloOperaciones(ModeloTablaOperaciones modeloOperaciones) {
		this.modeloOperaciones = modeloOperaciones;
	}


	/**
	 *  Sets the ModeloPrecios attribute of the MainFrameAdmin object
	 *
	 *@param  modeloPrecios  The new ModeloPrecios value
	 */
	public void setModeloPrecios(ModeloTablaPrecioAcciones modeloPrecios) {
		this.modeloPrecios = modeloPrecios;
	}


	/**
	 *  Sets the ModeloUsuarios attribute of the MainFrameAdmin object
	 *
	 *@param  modeloUsuarios  The new ModeloUsuarios value
	 */
	public void setModeloUsuarios(ModeloListaUsuariosConectados modeloUsuarios) {
		listaUsuarios.setModel(modeloUsuarios);
		listaUsuarios.setCellRenderer(modeloUsuarios.getListCellRenderer());
		this.modeloUsuarios = modeloUsuarios;
	}


	/**
	 *  Sets the ModeloVariables attribute of the MainFrameAdmin object
	 *
	 *@param  modeloVariables  The new ModeloVariables value
	 */
	public void setModeloVariables(ModeloTablaVariables modeloVariables) {
		this.modeloVariables = modeloVariables;
	}


	/**
	 *  Sets the Servidor attribute of the MainFrameAdmin object
	 *
	 *@param  servidor  The new Servidor value
	 */
	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}


	/**
	 *  Sets the Opciones attribute of the MainFrameAdmin object
	 *
	 *@param  opciones  The new Opciones value
	 */
	public void setOpciones(PanelOpciones opciones) {
		this.opciones = opciones;
	}


	/**
	 *  Gets the PanelControl attribute of the MainFrameAdmin object
	 *
	 *@return    The PanelControl value
	 */
	public Component getPanelControl() {
		TableSorter sorterOperaciones = new TableSorter(modeloOperaciones);
		JTable tablaOperaciones = new JTable(sorterOperaciones);
		sorterOperaciones.setTableHeader(tablaOperaciones.getTableHeader());
		JScrollPane panelScrollDerecha = new JScrollPane(tablaOperaciones);
		panelScrollDerecha.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelDerecha = new FakeInternalFrame("Log de Operaciones", panelScrollDerecha);

		TableSorter sorterPrecios = new TableSorter(modeloPrecios);
		JTable tablaPrecios = new JTable(sorterPrecios);
		sorterPrecios.setTableHeader(tablaPrecios.getTableHeader());
		tablaPrecios.getColumn(tablaPrecios.getColumnName(1)).setCellRenderer(modeloPrecios.getRenderer());

		JScrollPane panelScrollIzqAbajo = new JScrollPane(tablaPrecios);
		panelScrollIzqAbajo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelIzqAbajo = new FakeInternalFrame("Precios en bolsa", panelScrollIzqAbajo);

		//panelSecundario = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelIzqAbajo, panelIzqArriba);
		panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzqAbajo, panelDerecha);

		panelPrincipal.setDividerLocation(300);
		//panelSecundario.setDividerLocation(300);

		panelIzqAbajo.setPreferredSize(new Dimension(250, 300));
		panelDerecha.setPreferredSize(new Dimension(550, 600));

		return panelPrincipal;
	}


	/**
	 *  Gets the PanelEventos attribute of the MainFrameAdmin object
	 *
	 *@return    The PanelEventos value
	 */
	public Component getPanelEventos() {
		TableSorter sorterVariables = new TableSorter(modeloVariables);
		JTable tablaVariables = new JTable(sorterVariables);
		sorterVariables.setTableHeader(tablaVariables.getTableHeader());

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
		modeloEventos.setLista(tablaEventos);
		tablaEventos.getColumn(tablaEventos.getColumnName(2)).setMaxWidth(40);

		JScrollPane panelDerArriba = new JScrollPane(tablaEventos);
		panelDer.add(panelDerAbajo, BorderLayout.SOUTH);
		panelDer.add(panelDerArriba, BorderLayout.CENTER);

		JSplitPane panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new FakeInternalFrame("Variables del sistema", panelIzq),
				new FakeInternalFrame("Control de Eventos", panelDer));
		panelPrincipal.setDividerLocation(350);
		panelIzq.setPreferredSize(new Dimension(200, 600));
		panelDer.setPreferredSize(new Dimension(600, 600));
		return panelPrincipal;
	}


	/**
	 *  Gets the ModeloEventos attribute of the MainFrameAdmin object
	 *
	 *@return    The ModeloEventos value
	 */
	public ModeloTablaEventos getModeloEventos() {
		return modeloEventos;
	}


	/**
	 *  Gets the ModeloOperaciones attribute of the MainFrameAdmin object
	 *
	 *@return    The ModeloOperaciones value
	 */
	public ModeloTablaOperaciones getModeloOperaciones() {
		return modeloOperaciones;
	}


	/**
	 *  Gets the ModeloPrecios attribute of the MainFrameAdmin object
	 *
	 *@return    The ModeloPrecios value
	 */
	public ModeloTablaPrecioAcciones getModeloPrecios() {
		return modeloPrecios;
	}


	/**
	 *  Gets the ModeloUsuarios attribute of the MainFrameAdmin object
	 *
	 *@return    The ModeloUsuarios value
	 */
	public ModeloListaUsuariosConectados getModeloUsuarios() {
		return modeloUsuarios;
	}


	/**
	 *  Gets the ModeloVariables attribute of the MainFrameAdmin object
	 *
	 *@return    The ModeloVariables value
	 */
	public ModeloTablaVariables getModeloVariables() {
		return modeloVariables;
	}


	/**
	 *  Gets the Servidor attribute of the MainFrameAdmin object
	 *
	 *@return    The Servidor value
	 */
	public Servidor getServidor() {
		return servidor;
	}


	/**
	 *  Gets the Opciones attribute of the MainFrameAdmin object
	 *
	 *@return    The Opciones value
	 */
	public PanelOpciones getOpciones() {
		return opciones;
	}


	/**
	 *  Description of the Method
	 */
	public void init() {
		tabbed = new JTabbedPane();
		tabbed.insertTab("Sesion", null, getPanelSesion(), "Control de la sesion", 0);
		tabbed.insertTab("Evoluci�n bolsa", null, new JPanel(), "Control de los usuarios", 1);
		tabbed.insertTab("Eventos", null, new JPanel(), "Control de los eventos", 2);
		tabbed.insertTab("Control de Agentes", null, getPanelAgentes(), "Control de los agentes", 3);
		getContentPane().add(tabbed);
		tabbed.setEnabledAt(1, false);
		tabbed.setEnabledAt(2, false);
		setSize(new Dimension(800, 600));
	}


	/**
	 *  Description of the Method
	 */
	public void iniciaGUISesion() {
		tabbed.removeTabAt(1);
		tabbed.removeTabAt(1);
		tabbed.insertTab("Evoluci�n bolsa", null, getPanelControl(), "Control de los usuarios", 1);
		tabbed.insertTab("Eventos", null, getPanelEventos(), "Control de los eventos", 2);
	}


	/**
	 *  Gets the PanelAgentes attribute of the MainFrameAdmin object
	 *
	 *@return    The PanelAgentes value
	 */
	private Component getPanelAgentes() {
		File fich = new File(FICH_CONFAGENTES);
		PanelConfigAgentes panel = new PanelConfigAgentes(fich, this);
		return panel;
	}


	/**
	 *  Gets the PanelSesion attribute of the MainFrameAdmin object
	 *
	 *@return    The PanelSesion value
	 */
	private Component getPanelSesion() {
		JPanel panelIzquierdo = new JPanel(new GridLayout(2, 1, 20, 20));

		JPanel grafico =
			new JPanel() {
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(LOGO, 0, 0, 239, 250, this);
				}
			};
		grafico.setPreferredSize(new Dimension(240, 250));
		grafico.setMaximumSize(new Dimension(240, 250));
		grafico.setMinimumSize(new Dimension(240, 250));
		JPanel sesion = new JPanel(new BorderLayout());
		panelIzquierdo.add(grafico);
		panelIzquierdo.add(getPanelOpcionesInferior());
		sesion.add(panelIzquierdo, BorderLayout.WEST);
		sesion.add(getOpcionesSesion());

		return sesion;
	}


	/**
	 *  Gets the OpcionesSesion attribute of the MainFrameAdmin object
	 *
	 *@return    The OpcionesSesion value
	 */
	private Component getOpcionesSesion() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(getPanelOpcionesSuperior()), BorderLayout.CENTER);
		//panel.add(getPanelOpcionesInferior(), BorderLayout.SOUTH);
		FakeInternalFrame fr = new FakeInternalFrame("Configuracion de la sesion", panel);
		return fr;
	}


	/**
	 *  Gets the PanelOpcionesInferior attribute of the MainFrameAdmin object
	 *
	 *@return    The PanelOpcionesInferior value
	 */
	private Component getPanelOpcionesInferior() {
		JPanel panel = new JPanel(new BorderLayout());
		listaUsuarios = new JList(new DefaultListModel());
//		listaUsuarios.setCellRenderer(modeloUsuarios.getListCellRenderer());
		JScrollPane panelScrollIzqArriba = new JScrollPane(listaUsuarios);

		panelScrollIzqArriba.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		FakeInternalFrame panelIzqArriba = new FakeInternalFrame("Usuarios", panelScrollIzqArriba);

		panel.add(panelIzqArriba);

		panelIzqArriba.setPreferredSize(new Dimension(250, 300));
		JButton botonIniciarParar = new JButton("Iniciar Simulacion");
		botonIniciarParar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JButton src = (JButton) e.getSource();
							iniciarPausar_actionPerformed(src);
						}
					});
		botonFinalizarSimulacion = new JButton("Finalizar Simulacion");
		botonFinalizarSimulacion.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							botonFinalizar_actionPerformed();
						}
					});
		JPanel panelBotones = new JPanel();
		panelBotones.add(botonIniciarParar);
		panelBotones.add(botonFinalizarSimulacion);
		panel.add(panelBotones, BorderLayout.SOUTH);

		return panel;
	}


	/**
	 *  Gets the PanelOpcionesSuperior attribute of the MainFrameAdmin object
	 *
	 *@return    The PanelOpcionesSuperior value
	 */
	private Component getPanelOpcionesSuperior() {
		JPanel panel = new JPanel(new BorderLayout());
		ArrayList<String> ops = new ArrayList<String>();
		ops.add("         Numero de empresas");
		ops.add("Tiempo de fluctuacion");
		ops.add("Fichero de empresas");
		ops.add("Fichero de usuarios");
		ops.add("Tick");
		//ops.add("Sesi�n continua");

		ArrayList<String> chooser = new ArrayList<String>();
		chooser.add("Fichero de empresas");
		chooser.add("Fichero de usuarios");

		opciones = new PanelOpciones(ops, null, chooser);
		//opciones.setCheckBox("Sesi�n continua");

		panel.add(opciones, BorderLayout.NORTH);
		JPanel panelBlanco = new JPanel();
		panelBlanco.setPreferredSize(new Dimension(350, 300));
		panel.add(panelBlanco);

		return panel;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  src  Description of Parameter
	 */
	private void iniciarPausar_actionPerformed(JButton src) {
		try {
			if ("Iniciar Simulacion".equals(src.getText())) {
				src.setText("Pausar Simulacion");
				servidor.iniciaSesion();
			}
			else if ("Pausar Simulacion".equals(src.getText())) {
				src.setText("Reanudar Simulacion");
				servidor.pausarSesion();
			}
			else if ("Reanudar Simulacion".equals(src.getText())) {
				src.setText("Pausar Simulacion");
				servidor.reanudarSesion();
			}
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}


	/**
	 *  Description of the Method
	 */
	private void botonFinalizar_actionPerformed() {
		try {
			servidor.finalizaSesion();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	static {
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
