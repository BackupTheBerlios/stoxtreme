package stoxtreme.servidor.gui;
import stoxtreme.servidor.gui.ComboTextoCellEditor;
import stoxtreme.servidor.gui.ModeloTablaEdicion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import stoxtreme.servidor.gui.FakeInternalFrame;

public class PanelConfigAgentes extends JPanel{
	private DefaultListModel modeloListaSocial = new DefaultListModel();
	private DefaultListModel modeloListaPsicologica = new DefaultListModel();
	private DefaultListModel modeloListaDistribucion = new DefaultListModel();
	private DefaultTreeModel modeloComportamientos = new DefaultTreeModel(new DefaultMutableTreeNode("Comportamientos"));
	private JFrame frame;
	private JList listaPsicologico;
	private JList listaDistribuciones;
	private JList listaSocial;
	private JTree arbolComportamientos;
	
	public PanelConfigAgentes(JFrame frame){
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
		tablaEdicion.repaint();
	}
	JTable tablaEdicion;
	
	private Component getPanelEditor() {
		
		
		tablaEdicion = new JTable();
		
		JScrollPane panel = new JScrollPane(tablaEdicion);
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
		arbolComportamientos = new JTree(modeloComportamientos);
		JScrollPane scroll = new JScrollPane(arbolComportamientos);
		
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		JButton boton1 = new JButton("+");
		boton1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				addComportamiento();
			}
		});
		JButton boton2 = new JButton("-");
		boton1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				subComportamiento();
			}
		});
		botones.add(boton1);
		botones.add(boton2);
		
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(botones, BorderLayout.SOUTH);
		return panel;
	}

	private Component getPanelDistribuciones() {
		JPanel panel = new JPanel(new BorderLayout());
		listaDistribuciones = new JList(modeloListaDistribucion); 
		JScrollPane scroll = new JScrollPane(listaDistribuciones);
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton boton1 = new JButton("+");
		boton1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				addDistribucion();
			}
		});
		JButton boton2 = new JButton("-");
		boton2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				subDistribucion();
			}
		});
		botones.add(boton1);
		botones.add(boton2);
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
		listaSocial = new JList(modeloListaSocial); 
		listaSocial.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() >= 2){
					listaSocial_dblClick();
				}
			}
		});
		JScrollPane scroll = new JScrollPane(listaSocial);
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton boton1 = new JButton("+");
		boton1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				addSocial();
			}
		});
		JButton boton2 = new JButton("-");
		boton2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				subSocial();
			}
		});
		botones.add(boton1);
		botones.add(boton2);
		
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(botones, BorderLayout.SOUTH);
		return panel;
	}
	
	
	private Component getPanelModeloPsicologico() {
		JPanel panel = new JPanel(new BorderLayout());
		listaPsicologico = new JList(modeloListaPsicologica); 
		listaPsicologico.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() >= 2){
					listaPsicologico_dblClick();
				}
			}
		});
		JScrollPane scroll = new JScrollPane(listaPsicologico);
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton boton1 = new JButton("+");
		boton1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				addPsicologico();
			}
		});
		JButton boton2 = new JButton("-");
		boton2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				subPsicologico();
			}
		});
		botones.add(boton1);
		botones.add(boton2);
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(botones, BorderLayout.SOUTH);
		return panel;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new PanelConfigAgentes(frame));
		frame.setSize(new Dimension(800,600));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addComportamiento(){
		
	}
	
	public void subComportamiento(){
		
	}
	
	public void addDistribucion(){
		DialogoInsertarDistribucion dialogo = new DialogoInsertarDistribucion(frame);
		dialogo.setVisible(true);
		String tipo = dialogo.getTipo();
		String id = dialogo.getId();
		double p1 = dialogo.getP1();
		double p2 = dialogo.getP2();
		
		if(DialogoInsertarDistribucion.NORMAL.equals(tipo)){
			modeloListaDistribucion.addElement(new ElementoDistribucion(id, tipo, p1, p2));
		}
		else{
			modeloListaDistribucion.addElement(new ElementoDistribucion(id, tipo, p1));
		}
	}
	
	public void subDistribucion(){
		Object[] elimina = listaDistribuciones.getSelectedValues();
		for(int i=0; i<elimina.length; i++){
			modeloListaDistribucion.removeElement(elimina[i]);
		}
	}
	
	public void addSocial(){
		ComboTextoCellEditor editor = new ComboTextoCellEditor(ModeloTablaEdicion.params_psicologicos.length, ModeloTablaEdicion.params_psicologicos);
		String id = JOptionPane.showInputDialog(this, "Introduzca identificador para el modelo");
		ModeloTablaEdicion modelo = new ModeloTablaEdicion(ModeloTablaEdicion.MODELO_SOCIAL, editor, id){
			public void actualiza() {
				actualiza2();
			}
		};
		
		modeloListaSocial.addElement(modelo);
		tablaEdicion.setModel(modelo);
		tablaEdicion.getColumnModel().getColumn(1).setCellEditor(editor);
		tablaEdicion.getColumnModel().getColumn(1).setCellRenderer(editor);
		tablaEdicion.getColumn(tablaEdicion.getColumnName(2)).setMaxWidth(60);

	}
	
	public void subSocial(){
		
	}
	
	public void listaSocial_dblClick(){
		ComboTextoCellEditor editor = new ComboTextoCellEditor(ModeloTablaEdicion.params_psicologicos.length, ModeloTablaEdicion.params_psicologicos);
		ModeloTablaEdicion modelo = (ModeloTablaEdicion)listaSocial.getSelectedValue();
		tablaEdicion.setModel(modelo);
		tablaEdicion.getColumnModel().getColumn(1).setCellEditor(editor);
		tablaEdicion.getColumnModel().getColumn(1).setCellRenderer(editor);
		tablaEdicion.getColumn(tablaEdicion.getColumnName(2)).setMaxWidth(60);

	}
	
	public void addPsicologico(){
		ComboTextoCellEditor editor = new ComboTextoCellEditor(ModeloTablaEdicion.params_psicologicos.length, ModeloTablaEdicion.params_psicologicos);
		String id = JOptionPane.showInputDialog(this, "Introduzca identificador para el modelo");
		ModeloTablaEdicion modelo = new ModeloTablaEdicion(ModeloTablaEdicion.MODELO_PSICOLOGICO, editor, id){
			public void actualiza() {
				actualiza2();
			}
		};
		
		modeloListaPsicologica.addElement(modelo);
		tablaEdicion.setModel(modelo);
		tablaEdicion.getColumnModel().getColumn(1).setCellEditor(editor);
		tablaEdicion.getColumnModel().getColumn(1).setCellRenderer(editor);
		tablaEdicion.getColumn(tablaEdicion.getColumnName(2)).setMaxWidth(60);
	}
	
	public void subPsicologico(){
		
	}
	
	public void listaPsicologico_dblClick(){
		ModeloTablaEdicion modelo = (ModeloTablaEdicion)listaPsicologico.getSelectedValue();
		ComboTextoCellEditor editor = new ComboTextoCellEditor(ModeloTablaEdicion.params_psicologicos.length, ModeloTablaEdicion.params_psicologicos);
		tablaEdicion.setModel(modelo);
		tablaEdicion.getColumnModel().getColumn(1).setCellEditor(editor);
		tablaEdicion.getColumnModel().getColumn(1).setCellRenderer(editor);
		tablaEdicion.getColumn(tablaEdicion.getColumnName(2)).setMaxWidth(60);
	}

	private class ElementoDistribucion{
		public String tipo;
		public double p1;
		public double p2;
		public int nParam;
		public String id;
		
		public ElementoDistribucion(String id, String tipo, double p1){
			nParam = 1;
			this.tipo = tipo;
			this.id = id;
			this.p1 = p1;
		}
		
		public ElementoDistribucion(String id, String tipo, double p1, double p2){
			nParam = 2;
			this.id = id;
			this.tipo = tipo;
			this.p1 = p1;
			this.p2 = p2;
		}
		
		public String toString(){
			return id +" - "+ tipo+"("+p1+((nParam==2)?(","+p2):"")+")";
		}
	}
	
	
}
