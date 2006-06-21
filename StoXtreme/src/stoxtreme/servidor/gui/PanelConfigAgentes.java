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
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import stoxtreme.servidor.gui.FakeInternalFrame;

public class PanelConfigAgentes extends JPanel{
	// TODO esto que lo pille por la entrada
	private static String[] tiposComportamientos ={
		"stoxtreme.herramienta_agentes.agentes.comportamiento.informadores.AgenteAnalisisFundamental",
		"stoxtreme.herramienta_agentes.agentes.comportamiento.informadores.AgenteAnalisisTecnico",
		"stoxtreme.herramienta_agentes.agentes.comportamiento.informadores.AgentePublicidad",
		"stoxtreme.herramienta_agentes.agentes.comportamiento.informadores.AgenteRumores",
		
		"stoxtreme.herramienta_agentes.agentes.comportamiento.inversores.ComportamientoAleatorio",
		"stoxtreme.herramienta_agentes.agentes.comportamiento.inversores.ComportamientoCompraRecomendacion",
		"stoxtreme.herramienta_agentes.agentes.comportamiento.inversores.ComportamientoRumor",
	};
	
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
		listaDistribuciones.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() >= 2){
					listaDistribuciones_dblClick();
				}
			}
		});
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
		if(	modeloListaPsicologica.size() != 0 &&
			modeloListaSocial.size() != 0){
//			String[] distribuciones = new String[modeloListaDistribucion.size()];
//			for(int i=0; i<modeloListaDistribucion.size(); i++){
//				distribuciones[i] = ((ElementoDistribucion)modeloListaDistribucion.getElementAt(i)).getId();
//			}
			String[] psicologicos = new String[modeloListaPsicologica.size()];
			for(int i=0; i<modeloListaPsicologica.size(); i++){
				psicologicos[i] = ((Par)modeloListaPsicologica.getElementAt(i)).getModelo().getId();
			}
			String[] sociales = new String[modeloListaSocial.size()];
			for(int i=0; i<modeloListaSocial.size(); i++){
				sociales[i] = ((Par)modeloListaSocial.getElementAt(i)).getModelo().getId();
			}
			
			DialogoInsertarComportamiento dialogo = new DialogoInsertarComportamiento(
					frame,
					sociales,
					psicologicos,
					tiposComportamientos
			);
			
			dialogo.setVisible(true);
			ElementoComportamiento elemento = 
				new ElementoComportamiento(dialogo.getId(), dialogo.getTipoComportamiento(), 
						dialogo.getModeloPsicologico(), dialogo.getModeloSocial(), dialogo.getPorcentaje());
			
			TreePath path = arbolComportamientos.getSelectionPath();
			MutableTreeNode nodo = (MutableTreeNode)path.getLastPathComponent();
			modeloComportamientos.insertNodeInto(new DefaultMutableTreeNode(elemento),nodo, 0);
			path = path.pathByAddingChild(nodo);
			arbolComportamientos.expandPath(path);
		}
		else{
			if(modeloListaPsicologica.size() == 0){
				JOptionPane.showMessageDialog(frame, "Error. No hay modelos psicologicos", "Error",JOptionPane.ERROR_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(frame, "Error. No hay modelos sociales", "Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void subComportamiento(){
		
	}
	
	public void addDistribucion(){
		DialogoInsertarDistribucion dialogo = new DialogoInsertarDistribucion(frame);
		dialogo.setVisible(true);
		
		if(dialogo.isAceptado()){
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
	}
	
	public void subDistribucion(){
		Object[] elimina = listaDistribuciones.getSelectedValues();
		for(int i=0; i<elimina.length; i++){
			modeloListaDistribucion.removeElement(elimina[i]);
		}
	}
	
	public void listaDistribuciones_dblClick(){
		ElementoDistribucion dist = (ElementoDistribucion)listaDistribuciones.getSelectedValue();
		DialogoInsertarDistribucion dialogo = 
			new DialogoInsertarDistribucion(frame, dist.getId(), dist.getTipo(), dist.getP1(), dist.getP2());
		dialogo.setVisible(true);
		
		if(dialogo.isAceptado()){
			String tipo = dialogo.getTipo();
			String id = dialogo.getId();
			double p1 = dialogo.getP1();
			double p2 = dialogo.getP2();
			
			int index = listaDistribuciones.getSelectedIndex();
			if(DialogoInsertarDistribucion.NORMAL.equals(tipo)){
				modeloListaDistribucion.setElementAt(new ElementoDistribucion(id, tipo, p1, p2),index);
			}
			else{
				modeloListaDistribucion.setElementAt(new ElementoDistribucion(id, tipo, p1),index);
			}
		}
	}
	
	public void pararEdicion(){
		if(tablaEdicion.getColumnModel().getColumnCount()>0){
			ComboTextoCellEditor editor = 
				(ComboTextoCellEditor)tablaEdicion.getColumnModel().getColumn(1).getCellEditor();
			editor.paraEdicion();
		}
	}
	public void addSocial(){
		ComboTextoCellEditor editor = new ComboTextoCellEditor(ModeloTablaEdicion.params_psicologicos.length, modeloListaDistribucion);
		String id = JOptionPane.showInputDialog(this, "Introduzca identificador para el modelo");
		ModeloTablaEdicion modelo = new ModeloTablaEdicion(ModeloTablaEdicion.MODELO_SOCIAL, editor, id){
			public void actualiza() {
				actualiza2();
			}
		};
		pararEdicion();
		modeloListaSocial.addElement(new Par(modelo, editor));
		tablaEdicion.setModel(modelo);
		tablaEdicion.getColumnModel().getColumn(1).setCellEditor(editor);
		tablaEdicion.getColumnModel().getColumn(1).setCellRenderer(editor);
		tablaEdicion.getColumn(tablaEdicion.getColumnName(2)).setMaxWidth(60);

	}
	
	public void subSocial(){
		Object[] elimina = listaSocial.getSelectedValues();
		for(int i=0; i<elimina.length; i++){
			modeloListaSocial.removeElement(elimina[i]);
		}
	}
	
	public void listaSocial_dblClick(){
		pararEdicion();
		ModeloTablaEdicion modelo = ((Par)listaSocial.getSelectedValue()).getModelo();
		ComboTextoCellEditor editor = ((Par)listaSocial.getSelectedValue()).getEditor();
		tablaEdicion.setModel(modelo);
		tablaEdicion.getColumnModel().getColumn(1).setCellEditor(editor);
		tablaEdicion.getColumnModel().getColumn(1).setCellRenderer(editor);
		tablaEdicion.getColumn(tablaEdicion.getColumnName(2)).setMaxWidth(60);

	}
	
	public void addPsicologico(){
		pararEdicion();
		ComboTextoCellEditor editor = new ComboTextoCellEditor(ModeloTablaEdicion.params_psicologicos.length, modeloListaDistribucion);
		String id = JOptionPane.showInputDialog(this, "Introduzca identificador para el modelo");
		ModeloTablaEdicion modelo = new ModeloTablaEdicion(ModeloTablaEdicion.MODELO_PSICOLOGICO, editor, id){
			public void actualiza() {
				actualiza2();
			}
		};
		modeloListaPsicologica.addElement(new Par(modelo,editor));
		tablaEdicion.setModel(modelo);
		tablaEdicion.getColumnModel().getColumn(1).setCellEditor(editor);
		tablaEdicion.getColumnModel().getColumn(1).setCellRenderer(editor);
		tablaEdicion.getColumn(tablaEdicion.getColumnName(2)).setMaxWidth(60);
	}
	
	public void subPsicologico(){
		Object[] elimina = listaPsicologico.getSelectedValues();
		for(int i=0; i<elimina.length; i++){
			modeloListaPsicologica.removeElement(elimina[i]);
		}
	}
	
	public void listaPsicologico_dblClick(){
		pararEdicion();
		ModeloTablaEdicion modelo = ((Par)listaPsicologico.getSelectedValue()).getModelo();
		ComboTextoCellEditor editor = ((Par)listaPsicologico.getSelectedValue()).getEditor();
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
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public double getP1() {
			return p1;
		}

		public void setP1(double p1) {
			this.p1 = p1;
		}

		public double getP2() {
			return p2;
		}

		public void setP2(double p2) {
			this.p2 = p2;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

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
	
	private class ElementoComportamiento{
		private String id;
		private String modeloPsicologico;
		private String modeloSocial;
		private String tipoComportamiento;
		private double porcentaje;
		
		public ElementoComportamiento(String id, String comportamiento, String psicologico, String social, double porcentaje) {
			this.id = id;
			modeloPsicologico = psicologico;
			modeloSocial = social;
			this.porcentaje = porcentaje;
			tipoComportamiento = comportamiento;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getModeloPsicologico() {
			return modeloPsicologico;
		}
		public void setModeloPsicologico(String modeloPsicologico) {
			this.modeloPsicologico = modeloPsicologico;
		}
		public String getModeloSocial() {
			return modeloSocial;
		}
		public void setModeloSocial(String modeloSocial) {
			this.modeloSocial = modeloSocial;
		}
		public double getPorcentaje() {
			return porcentaje;
		}
		public void setPorcentaje(double porcentaje) {
			this.porcentaje = porcentaje;
		}
		public String getTipoComportamiento() {
			return tipoComportamiento;
		}
		public void setTipoComportamiento(String tipoComportamiento) {
			this.tipoComportamiento = tipoComportamiento;
		}
		
		public String toString(){
			StringBuffer buf = new StringBuffer();
			buf.append(id);
			buf.append("(");
			buf.append(tipoComportamiento);
			buf.append(",");
			buf.append(modeloSocial);
			buf.append(",");
			buf.append(modeloPsicologico);
			buf.append(",");
			buf.append(porcentaje);
			buf.append(")");
			return buf.toString();
		}
	}
	
	private class Par{
		private ModeloTablaEdicion modelo;
		private ComboTextoCellEditor editor;
		
		public Par(ModeloTablaEdicion modelo, ComboTextoCellEditor editor){
			this.modelo = modelo;
			this.editor = editor;
		}

		public ComboTextoCellEditor getEditor() {
			return editor;
		}

		public void setEditor(ComboTextoCellEditor editor) {
			this.editor = editor;
		}

		public ModeloTablaEdicion getModelo() {
			return modelo;
		}

		public void setModelo(ModeloTablaEdicion modelo) {
			this.modelo = modelo;
		}
		
		public String toString(){
			return modelo.toString();
		}
	}
	
}
