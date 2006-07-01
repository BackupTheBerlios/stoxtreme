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
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class PanelConfigAgentes extends JPanel {
	JTable tablaEdicion;
	private PanelOpciones atribModelo;
	private DefaultListModel modeloListaSocial = new DefaultListModel();
	private DefaultListModel modeloListaPsicologica = new DefaultListModel();
	private DefaultListModel modeloListaDistribucion = new DefaultListModel();
	private DefaultTreeModel modeloComportamientos = new DefaultTreeModel(new DefaultMutableTreeNode("Comportamientos"));
	private JFrame frame;
	private JList listaPsicologico;
	private JList listaDistribuciones;
	private JList listaSocial;
	private JTree arbolComportamientos;
	// TODO: esto que lo pille por la entrada
	private static String[] tiposComportamientos = {
			"stoxtreme.herramienta_agentes.agentes.comportamiento.informadores.AgenteAnalisisFundamental",
			"stoxtreme.herramienta_agentes.agentes.comportamiento.informadores.AgenteAnalisisTecnico",
			"stoxtreme.herramienta_agentes.agentes.comportamiento.informadores.AgentePublicidad",
			"stoxtreme.herramienta_agentes.agentes.comportamiento.informadores.AgenteRumores",
			"stoxtreme.herramienta_agentes.agentes.comportamiento.inversores.ComportamientoAleatorio",
			"stoxtreme.herramienta_agentes.agentes.comportamiento.inversores.ComportamientoCompraRecomendacion",
			"stoxtreme.herramienta_agentes.agentes.comportamiento.inversores.ComportamientoRumor",
			"stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador.ComportamientoAprendizaje"
			};


	/**
	 *  Constructor for the PanelConfigAgentes object
	 *
	 *@param  fichConf  Description of Parameter
	 *@param  frame     Description of Parameter
	 */
	public PanelConfigAgentes(File fichConf, JFrame frame) {
		try {
			this.frame = frame;
			init();
			loadXML(fichConf);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}


	/**
	 *  Sets the Atributos attribute of the PanelConfigAgentes object
	 *
	 *@param  maxAgentes     The new Atributos value
	 *@param  minAgentes     The new Atributos value
	 *@param  minEspera      The new Atributos value
	 *@param  maxEspera      The new Atributos value
	 *@param  distribEspera  The new Atributos value
	 *@param  maxGasto       The new Atributos value
	 *@param  ratioRespawn   The new Atributos value
	 *@param  atenuacion     The new Atributos value
	 */
	public void setAtributos(int maxAgentes, int minAgentes, int minEspera, int maxEspera, String distribEspera, double maxGasto, double ratioRespawn, double atenuacion) {
		atribModelo.setValor("Numero máximo de agentes", Integer.toString(maxAgentes));
		atribModelo.setValor("Numero mínimo de agentes", Integer.toString(minAgentes));
		atribModelo.setValor("Tiempo de espera mínimo", Integer.toString(maxEspera));
		atribModelo.setValor("Tiempo de espera máximo", Integer.toString(minEspera));
		atribModelo.setValor("Distribucion del tiempo de espera", distribEspera);
		atribModelo.setValor("Gasto máximo", Double.toString(maxGasto));
		atribModelo.setValor("Ratio respawn", Double.toString(ratioRespawn));
		atribModelo.setValor("Atenuacion rumor", Double.toString(atenuacion));
	}


	/**
	 *  Description of the Method
	 */
	public void carga_actionPerformed() {
		JFileChooser chooser = new JFileChooser(new File("."));
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File fich = chooser.getSelectedFile();
			try {
				loadXML(fich);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Fallo en la carga del XML", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	/**
	 *  Description of the Method
	 */
	public void guarda_actionPerformed() {
		JFileChooser chooser = new JFileChooser(new File("."));
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File fich = chooser.getSelectedFile();
			try {
				saveXML(fich);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Fallo en la carga del XML", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	/**
	 *  Description of the Method
	 */
	public void actualiza2() {
		tablaEdicion.repaint();
	}


	/**
	 *  Adds a feature to the Comportamiento attribute of the PanelConfigAgentes
	 *  object
	 */
	public void addComportamiento() {
		if (modeloListaPsicologica.size() != 0 && modeloListaSocial.size() != 0) {
			String[] psicologicos = new String[modeloListaPsicologica.size()];
			for (int i = 0; i < modeloListaPsicologica.size(); i++) {
				psicologicos[i] = ((Par) modeloListaPsicologica.getElementAt(i)).getModelo().getId();
			}
			String[] sociales = new String[modeloListaSocial.size()];
			for (int i = 0; i < modeloListaSocial.size(); i++) {
				sociales[i] = ((Par) modeloListaSocial.getElementAt(i)).getModelo().getId();
			}

			TreePath path = arbolComportamientos.getSelectionPath();
			ElementoComportamiento elemento = null;
			if (path == null || modeloComportamientos.getRoot().equals(path.getLastPathComponent())) {
				DialogoInsertarComportamiento dialogo = new DialogoInsertarComportamiento(
						frame,
						sociales,
						psicologicos,
						tiposComportamientos
						);
				dialogo.setVisible(true);
				if (dialogo.isAceptado()) {
					elemento =
							new ElementoComportamiento(dialogo.getId(), dialogo.getTipoComportamiento(),
							dialogo.getModeloPsicologico(), dialogo.getModeloSocial(), dialogo.getPorcentaje());
				}
			}
			else {
				DialogoInsertarSubomportamiento dialogo = new DialogoInsertarSubomportamiento(
						frame,
						tiposComportamientos
						);
				dialogo.setVisible(true);
				if (dialogo.isAceptado()) {
					elemento =
							new ElementoComportamiento(dialogo.getId(), dialogo.getTipoComportamiento());
				}
			}

			if (elemento != null) {
				if (path != null) {
					MutableTreeNode nodo = (MutableTreeNode) path.getLastPathComponent();
					modeloComportamientos.insertNodeInto(new DefaultMutableTreeNode(elemento), nodo, 0);
					path = path.pathByAddingChild(nodo);
					arbolComportamientos.expandPath(path);
				}
				else {
					modeloComportamientos.insertNodeInto(new DefaultMutableTreeNode(elemento), (MutableTreeNode) modeloComportamientos.getRoot(), 0);
					path = new TreePath((MutableTreeNode) modeloComportamientos.getRoot());
					arbolComportamientos.expandPath(path);
				}
			}
		}
		else {
			if (modeloListaPsicologica.size() == 0) {
				JOptionPane.showMessageDialog(frame, "Error. No hay modelos psicologicos", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(frame, "Error. No hay modelos sociales", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	/**
	 *  Description of the Method
	 */
	public void subComportamiento() {
		TreePath path = arbolComportamientos.getSelectionPath();
		MutableTreeNode nodo = (MutableTreeNode) path.getLastPathComponent();
		if (!modeloComportamientos.getRoot().equals(nodo)) {
			modeloComportamientos.removeNodeFromParent(nodo);
		}
	}


	/**
	 *  Adds a feature to the Distribucion attribute of the PanelConfigAgentes
	 *  object
	 */
	public void addDistribucion() {
		DialogoInsertarDistribucion dialogo = new DialogoInsertarDistribucion(frame);
		dialogo.setVisible(true);
		if (dialogo.isAceptado()) {
			String tipo = dialogo.getTipo();
			String id = dialogo.getId();
			double p1 = dialogo.getP1();
			double p2 = dialogo.getP2();

			if (DialogoInsertarDistribucion.NORMAL.equals(tipo)) {
				modeloListaDistribucion.addElement(new ElementoDistribucion(id, tipo, p1, p2));
			}
			else {
				modeloListaDistribucion.addElement(new ElementoDistribucion(id, tipo, p1));
			}
		}
	}


	/**
	 *  Description of the Method
	 */
	public void subDistribucion() {
		Object[] elimina = listaDistribuciones.getSelectedValues();
		for (int i = 0; i < elimina.length; i++) {
			modeloListaDistribucion.removeElement(elimina[i]);
		}
	}


	/**
	 *  Description of the Method
	 */
	public void listaDistribuciones_dblClick() {
		ElementoDistribucion dist = (ElementoDistribucion) listaDistribuciones.getSelectedValue();
		DialogoInsertarDistribucion dialogo =
				new DialogoInsertarDistribucion(frame, dist.getId(), dist.getTipo(), dist.getP1(), dist.getP2());
		dialogo.setVisible(true);

		if (dialogo.isAceptado()) {
			String tipo = dialogo.getTipo();
			String id = dialogo.getId();
			double p1 = dialogo.getP1();
			double p2 = dialogo.getP2();

			int index = listaDistribuciones.getSelectedIndex();
			if (DialogoInsertarDistribucion.NORMAL.equals(tipo)) {
				modeloListaDistribucion.setElementAt(new ElementoDistribucion(id, tipo, p1, p2), index);
			}
			else {
				modeloListaDistribucion.setElementAt(new ElementoDistribucion(id, tipo, p1), index);
			}
		}
	}


	/**
	 *  Description of the Method
	 */
	public void pararEdicion() {
		if (tablaEdicion.getColumnModel().getColumnCount() > 0) {
			ComboTextoCellEditor editor =
					(ComboTextoCellEditor) tablaEdicion.getColumnModel().getColumn(1).getCellEditor();
			editor.paraEdicion();
		}
	}


	/**
	 *  Adds a feature to the Social attribute of the PanelConfigAgentes object
	 */
	public void addSocial() {
		ComboTextoCellEditor editor = new ComboTextoCellEditor(ModeloTablaEdicion.params_psicologicos.length, modeloListaDistribucion);
		String id = JOptionPane.showInputDialog(this, "Introduzca identificador para el modelo");
		ModeloTablaEdicion modelo =
			new ModeloTablaEdicion(ModeloTablaEdicion.MODELO_SOCIAL, editor, id) {
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


	/**
	 *  Description of the Method
	 */
	public void subSocial() {
		Object[] elimina = listaSocial.getSelectedValues();
		for (int i = 0; i < elimina.length; i++) {
			modeloListaSocial.removeElement(elimina[i]);
		}
	}


	/**
	 *  Description of the Method
	 */
	public void listaSocial_dblClick() {
		pararEdicion();
		ModeloTablaEdicion modelo = ((Par) listaSocial.getSelectedValue()).getModelo();
		ComboTextoCellEditor editor = ((Par) listaSocial.getSelectedValue()).getEditor();
		tablaEdicion.setModel(modelo);
		tablaEdicion.getColumnModel().getColumn(1).setCellEditor(editor);
		tablaEdicion.getColumnModel().getColumn(1).setCellRenderer(editor);
		tablaEdicion.getColumn(tablaEdicion.getColumnName(2)).setMaxWidth(60);

	}


	/**
	 *  Adds a feature to the Psicologico attribute of the PanelConfigAgentes
	 *  object
	 */
	public void addPsicologico() {
		pararEdicion();
		ComboTextoCellEditor editor = new ComboTextoCellEditor(ModeloTablaEdicion.params_psicologicos.length, modeloListaDistribucion);
		String id = JOptionPane.showInputDialog(this, "Introduzca identificador para el modelo");
		ModeloTablaEdicion modelo =
			new ModeloTablaEdicion(ModeloTablaEdicion.MODELO_PSICOLOGICO, editor, id) {
				public void actualiza() {
					actualiza2();
				}
			};
		modeloListaPsicologica.addElement(new Par(modelo, editor));
		tablaEdicion.setModel(modelo);
		tablaEdicion.getColumnModel().getColumn(1).setCellEditor(editor);
		tablaEdicion.getColumnModel().getColumn(1).setCellRenderer(editor);
		tablaEdicion.getColumn(tablaEdicion.getColumnName(2)).setMaxWidth(60);
	}


	/**
	 *  Description of the Method
	 */
	public void subPsicologico() {
		Object[] elimina = listaPsicologico.getSelectedValues();
		for (int i = 0; i < elimina.length; i++) {
			modeloListaPsicologica.removeElement(elimina[i]);
		}
	}


	/**
	 *  Description of the Method
	 */
	public void listaPsicologico_dblClick() {
		pararEdicion();
		ModeloTablaEdicion modelo = ((Par) listaPsicologico.getSelectedValue()).getModelo();
		ComboTextoCellEditor editor = ((Par) listaPsicologico.getSelectedValue()).getEditor();
		tablaEdicion.setModel(modelo);
		tablaEdicion.getColumnModel().getColumn(1).setCellEditor(editor);
		tablaEdicion.getColumnModel().getColumn(1).setCellRenderer(editor);
		tablaEdicion.getColumn(tablaEdicion.getColumnName(2)).setMaxWidth(60);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  fich           Description of Parameter
	 *@exception  Exception  Description of Exception
	 */
	public void loadXML(File fich) throws Exception {
		borraAnterior();
		CargaXMLAgentes.carga(fich, this);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  fich           Description of Parameter
	 *@exception  Exception  Description of Exception
	 */
	public void saveXML(File fich) throws Exception {

	}


	/**
	 *  Description of the Method
	 *
	 *@param  id    Description of Parameter
	 *@param  tipo  Description of Parameter
	 *@param  p1    Description of Parameter
	 *@param  p2    Description of Parameter
	 */
	public void insDistribucion(String id, String tipo, double p1, double p2) {
		ElementoDistribucion dist;
		if ("Poisson".equals(tipo)) {
			dist = new ElementoDistribucion(id, tipo, p1);
		}
		else {
			dist = new ElementoDistribucion(id, tipo, p1, p2);
		}
		modeloListaDistribucion.addElement(dist);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  id        Description of Parameter
	 *@param  valores   Description of Parameter
	 *@param  distribs  Description of Parameter
	 */
	public void insModeloSocial(
			String id,
			Hashtable<String, Double> valores,
			Hashtable<String, String> distribs
			) {
		Enumeration<String> claves = valores.keys();
		ComboTextoCellEditor editor = new ComboTextoCellEditor(ModeloTablaEdicion.params_social.length, modeloListaDistribucion);
		ModeloTablaEdicion modeloEdicion =
			new ModeloTablaEdicion(ModeloTablaEdicion.MODELO_SOCIAL, editor, id) {
				public void actualiza() {
					actualiza2();
				}
			};

		while (claves.hasMoreElements()) {
			String actual = claves.nextElement();
			double valor = valores.get(actual);
			int fila = modeloEdicion.getFilaSocial(actual);
			editor.setValor(fila, Double.toString(valor));
			modeloEdicion.setValorNormal(actual, valor);
		}

		claves = distribs.keys();
		while (claves.hasMoreElements()) {
			String actual = claves.nextElement();
			String valor = distribs.get(actual);
			int fila = modeloEdicion.getFilaSocial(actual);
			editor.setDistribucion(fila, valor);
			modeloEdicion.setValorDistribuido(actual, valor);
		}
		Par par = new Par(modeloEdicion, editor);
		modeloListaSocial.addElement(par);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  id        Description of Parameter
	 *@param  valores   Description of Parameter
	 *@param  distribs  Description of Parameter
	 */
	public void insModeloPsicologico(
			String id,
			Hashtable<String, Double> valores,
			Hashtable<String, String> distribs
			) {
		Enumeration<String> claves = valores.keys();
		ComboTextoCellEditor editor = new ComboTextoCellEditor(ModeloTablaEdicion.params_psicologicos.length, modeloListaDistribucion);
		ModeloTablaEdicion modeloEdicion =
			new ModeloTablaEdicion(ModeloTablaEdicion.MODELO_PSICOLOGICO, editor, id) {
				public void actualiza() {
					actualiza2();
				}
			};

		while (claves.hasMoreElements()) {
			String actual = claves.nextElement();
			double valor = valores.get(actual);
			int fila = modeloEdicion.getFilaPsicologico(actual);
			editor.setValor(fila, Double.toString(valor));
			modeloEdicion.setValorNormal(actual, valor);
		}

		claves = distribs.keys();
		while (claves.hasMoreElements()) {
			String actual = claves.nextElement();
			String valor = distribs.get(actual);
			int fila = modeloEdicion.getFilaPsicologico(actual);
			editor.setDistribucion(fila, valor);
			modeloEdicion.setValorDistribuido(actual, valor);
		}
		Par par = new Par(modeloEdicion, editor);
		modeloListaPsicologica.addElement(par);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  id                   Description of Parameter
	 *@param  tipo_comportamiento  Description of Parameter
	 *@param  modelo_social        Description of Parameter
	 *@param  modelo_psicologico   Description of Parameter
	 *@param  porcentaje           Description of Parameter
	 *@param  subComportamientos   Description of Parameter
	 */
	public void insComportamiento(
			String id,
			String tipo_comportamiento,
			String modelo_social,
			String modelo_psicologico,
			double porcentaje,
			ArrayList<Hashtable<String, Object>> subComportamientos
			) {
		ElementoComportamiento comportamiento =
				new ElementoComportamiento(id, tipo_comportamiento, modelo_psicologico, modelo_social, porcentaje);
		DefaultMutableTreeNode nodoComportamiento = new DefaultMutableTreeNode(comportamiento);

		for (int i = 0; i < subComportamientos.size(); i++) {
			MutableTreeNode subnodo = getNodoSubcomportamientos(subComportamientos.get(i));
			nodoComportamiento.insert(subnodo, 0);
		}
		((MutableTreeNode) modeloComportamientos.getRoot()).insert(nodoComportamiento, 0);
	}


	/**
	 *  Description of the Method
	 */
	public void expandeArbol() {
		TreePath path = new TreePath(modeloComportamientos.getRoot());
		arbolComportamientos.expandPath(path);
	}


	/**
	 *  Gets the PanelArriba attribute of the PanelConfigAgentes object
	 *
	 *@return    The PanelArriba value
	 */
	private Component getPanelArriba() {
		JSplitPane panel = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT,
				getPanelIzquierdo(),
				getPanelDerecho()
				);
		panel.setDividerLocation(325);
		return panel;
	}


	/**
	 *  Gets the PanelDerecho attribute of the PanelConfigAgentes object
	 *
	 *@return    The PanelDerecho value
	 */
	private Component getPanelDerecho() {
//		JSplitPane split = new JSplitPane(
//				JSplitPane.VERTICAL_SPLIT,
//				new FakeInternalFrame("Edicion",getPanelEditor()),
//				getPanelDerechaAbajo()
//		);
//		split.setDividerLocation(300);
//		return split;
		return new FakeInternalFrame("Edicion", getPanelEditor());
	}


	/**
	 *  Gets the PanelBotones attribute of the PanelConfigAgentes object
	 *
	 *@return    The PanelBotones value
	 */
	private Component getPanelBotones() {
		JPanel panel = new JPanel();
		JButton botonCarga = new JButton("Cargar Fichero");
		botonCarga.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							carga_actionPerformed();
						}
					});
		panel.add(botonCarga);
		JButton botonGuardar = new JButton("Guardar Configuracion");
		botonGuardar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							guarda_actionPerformed();
						}
					});
		panel.add(botonGuardar);
		return panel;
	}


	/**
	 *  Gets the PanelEditor attribute of the PanelConfigAgentes object
	 *
	 *@return    The PanelEditor value
	 */
	private Component getPanelEditor() {
		tablaEdicion = new JTable();
		JScrollPane panel = new JScrollPane(tablaEdicion);
		return panel;
	}


	/**
	 *  Gets the PanelIzquierdo attribute of the PanelConfigAgentes object
	 *
	 *@return    The PanelIzquierdo value
	 */
	private Component getPanelIzquierdo() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getPanelIzquierdaArriba(), BorderLayout.CENTER);
		panel.add(getPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}


	/**
	 *  Gets the PanelIzquierdaArriba attribute of the PanelConfigAgentes object
	 *
	 *@return    The PanelIzquierdaArriba value
	 */
	private Component getPanelIzquierdaArriba() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(getPanelIzquierdaArribaArriba());
		panel.add(getPanelIzquierdaArribaAbajo());
		return panel;
	}


	/**
	 *  Gets the PanelIzquierdaArribaAbajo attribute of the PanelConfigAgentes
	 *  object
	 *
	 *@return    The PanelIzquierdaArribaAbajo value
	 */
	private Component getPanelIzquierdaArribaAbajo() {
		JTabbedPane panel = new JTabbedPane();
		panel.insertTab("Comportamientos", null, getPanelComportamientos(), "Comportamientos", 0);
		panel.insertTab("Social", null, getPanelModeloSocial(), "Modelo Social", 1);
		panel.insertTab("Psicologico", null, getPanelModeloPsicologico(), "Modelo Psicologico", 2);
		panel.insertTab("Distribuciones", null, getPanelDistribuciones(), "Distribuciones de probabilidad", 3);
		FakeInternalFrame frame = new FakeInternalFrame("Modelo", panel);

		return frame;
	}


	/**
	 *  Gets the PanelComportamientos attribute of the PanelConfigAgentes object
	 *
	 *@return    The PanelComportamientos value
	 */
	private Component getPanelComportamientos() {
		JPanel panel = new JPanel(new BorderLayout());
		arbolComportamientos = new JTree(modeloComportamientos);
		JScrollPane scroll = new JScrollPane(arbolComportamientos);

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JButton boton1 = new JButton("+");
		boton1.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							addComportamiento();
						}
					});
		JButton boton2 = new JButton("-");
		boton2.addActionListener(
					new ActionListener() {
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


	/**
	 *  Gets the PanelDistribuciones attribute of the PanelConfigAgentes object
	 *
	 *@return    The PanelDistribuciones value
	 */
	private Component getPanelDistribuciones() {
		JPanel panel = new JPanel(new BorderLayout());
		listaDistribuciones = new JList(modeloListaDistribucion);
		listaDistribuciones.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() >= 2) {
								listaDistribuciones_dblClick();
							}
						}
					});
		JScrollPane scroll = new JScrollPane(listaDistribuciones);
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton boton1 = new JButton("+");
		boton1.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							addDistribucion();
						}
					});
		JButton boton2 = new JButton("-");
		boton2.addActionListener(
					new ActionListener() {
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


	/**
	 *  Gets the PanelIzquierdaArribaArriba attribute of the PanelConfigAgentes
	 *  object
	 *
	 *@return    The PanelIzquierdaArribaArriba value
	 */
	private Component getPanelIzquierdaArribaArriba() {
		ArrayList<String> ops = new ArrayList<String>();
		ops.add("Numero máximo de agentes");
		ops.add("Numero mínimo de agentes");
		ops.add("Tiempo de espera mínimo");
		ops.add("Tiempo de espera máximo");
		ops.add("Distribucion del tiempo de espera");
		ops.add("Gasto máximo");
		ops.add("Ratio respawn");
		ops.add("Atenuacion rumor");
		atribModelo = new PanelOpciones(ops);
		FakeInternalFrame frame = new FakeInternalFrame("Parametros", new JScrollPane(atribModelo));
		return frame;
	}


	/**
	 *  Gets the PanelModeloSocial attribute of the PanelConfigAgentes object
	 *
	 *@return    The PanelModeloSocial value
	 */
	private Component getPanelModeloSocial() {
		JPanel panel = new JPanel(new BorderLayout());
		listaSocial = new JList(modeloListaSocial);
		listaSocial.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() >= 2) {
								listaSocial_dblClick();
							}
						}
					});
		JScrollPane scroll = new JScrollPane(listaSocial);
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton boton1 = new JButton("+");
		boton1.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							addSocial();
						}
					});
		JButton boton2 = new JButton("-");
		boton2.addActionListener(
					new ActionListener() {
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


	/**
	 *  Gets the PanelModeloPsicologico attribute of the PanelConfigAgentes
	 *  object
	 *
	 *@return    The PanelModeloPsicologico value
	 */
	private Component getPanelModeloPsicologico() {
		JPanel panel = new JPanel(new BorderLayout());
		listaPsicologico = new JList(modeloListaPsicologica);
		listaPsicologico.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() >= 2) {
								listaPsicologico_dblClick();
							}
						}
					});
		JScrollPane scroll = new JScrollPane(listaPsicologico);
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton boton1 = new JButton("+");
		boton1.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							addPsicologico();
						}
					});
		JButton boton2 = new JButton("-");
		boton2.addActionListener(
					new ActionListener() {
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


	/**
	 *  Gets the NodoSubcomportamientos attribute of the PanelConfigAgentes
	 *  object
	 *
	 *@param  subcomportamiento  Description of Parameter
	 *@return                    The NodoSubcomportamientos value
	 */
	private MutableTreeNode getNodoSubcomportamientos(Hashtable<String, Object> subcomportamiento) {
		String id = (String) subcomportamiento.get("id");
		String tipoComportamiento = (String) subcomportamiento.get("tipo_comportamiento");
		ArrayList<Hashtable<String, Object>> hijos = (ArrayList<Hashtable<String, Object>>) subcomportamiento.get("subcomportamientos");
		ElementoComportamiento comportamiento = new ElementoComportamiento(id, tipoComportamiento);
		DefaultMutableTreeNode nodoComportamiento = new DefaultMutableTreeNode(comportamiento);

		for (int i = 0; i < hijos.size(); i++) {
			MutableTreeNode subnodo = getNodoSubcomportamientos(hijos.get(i));
			nodoComportamiento.insert(subnodo, 0);
		}
		return nodoComportamiento;
	}


	/**
	 *  Description of the Method
	 */
	private void init() {
		this.setLayout(new BorderLayout());
		add(getPanelArriba(), BorderLayout.CENTER);
	}


	/**
	 *  Description of the Method
	 */
	private void borraAnterior() {
		modeloComportamientos = new DefaultTreeModel(new DefaultMutableTreeNode("Comportamientos"));
		arbolComportamientos.setModel(modeloComportamientos);
		modeloListaDistribucion.clear();
		modeloListaPsicologica.clear();
		modeloListaSocial.clear();
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	private class ElementoDistribucion {
		/**
		 *  Description of the Field
		 */
		public String tipo;
		/**
		 *  Description of the Field
		 */
		public double p1;
		/**
		 *  Description of the Field
		 */
		public double p2;
		/**
		 *  Description of the Field
		 */
		public int nParam;
		/**
		 *  Description of the Field
		 */
		public String id;


		/**
		 *  Constructor for the ElementoDistribucion object
		 *
		 *@param  id    Description of Parameter
		 *@param  tipo  Description of Parameter
		 *@param  p1    Description of Parameter
		 */
		public ElementoDistribucion(String id, String tipo, double p1) {
			nParam = 1;
			this.tipo = tipo;
			this.id = id;
			this.p1 = p1;
		}


		/**
		 *  Constructor for the ElementoDistribucion object
		 *
		 *@param  id    Description of Parameter
		 *@param  tipo  Description of Parameter
		 *@param  p1    Description of Parameter
		 *@param  p2    Description of Parameter
		 */
		public ElementoDistribucion(String id, String tipo, double p1, double p2) {
			nParam = 2;
			this.id = id;
			this.tipo = tipo;
			this.p1 = p1;
			this.p2 = p2;
		}


		/**
		 *  Sets the Id attribute of the ElementoDistribucion object
		 *
		 *@param  id  The new Id value
		 */
		public void setId(String id) {
			this.id = id;
		}


		/**
		 *  Sets the P1 attribute of the ElementoDistribucion object
		 *
		 *@param  p1  The new P1 value
		 */
		public void setP1(double p1) {
			this.p1 = p1;
		}


		/**
		 *  Sets the P2 attribute of the ElementoDistribucion object
		 *
		 *@param  p2  The new P2 value
		 */
		public void setP2(double p2) {
			this.p2 = p2;
		}


		/**
		 *  Sets the Tipo attribute of the ElementoDistribucion object
		 *
		 *@param  tipo  The new Tipo value
		 */
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}


		/**
		 *  Gets the Id attribute of the ElementoDistribucion object
		 *
		 *@return    The Id value
		 */
		public String getId() {
			return id;
		}


		/**
		 *  Gets the P1 attribute of the ElementoDistribucion object
		 *
		 *@return    The P1 value
		 */
		public double getP1() {
			return p1;
		}


		/**
		 *  Gets the P2 attribute of the ElementoDistribucion object
		 *
		 *@return    The P2 value
		 */
		public double getP2() {
			return p2;
		}


		/**
		 *  Gets the Tipo attribute of the ElementoDistribucion object
		 *
		 *@return    The Tipo value
		 */
		public String getTipo() {
			return tipo;
		}


		/**
		 *  Converts to a String representation of the object.
		 *
		 *@return    A string representation of the object.
		 */
		public String toString() {
			return id + " - " + tipo + "(" + p1 + ((nParam == 2) ? ("," + p2) : "") + ")";
		}
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	private class ElementoComportamiento {
		private String id;
		private String modeloPsicologico;
		private String modeloSocial;
		private String tipoComportamiento;
		private double porcentaje;
		private boolean padre;


		/**
		 *  Constructor for the ElementoComportamiento object
		 *
		 *@param  id              Description of Parameter
		 *@param  comportamiento  Description of Parameter
		 */
		public ElementoComportamiento(String id, String comportamiento) {
			padre = false;
			this.id = id;
			this.tipoComportamiento = comportamiento;
		}


		/**
		 *  Constructor for the ElementoComportamiento object
		 *
		 *@param  id              Description of Parameter
		 *@param  comportamiento  Description of Parameter
		 *@param  psicologico     Description of Parameter
		 *@param  social          Description of Parameter
		 *@param  porcentaje      Description of Parameter
		 */
		public ElementoComportamiento(String id, String comportamiento, String psicologico, String social, double porcentaje) {
			padre = true;
			this.id = id;
			modeloPsicologico = psicologico;
			modeloSocial = social;
			this.porcentaje = porcentaje;
			tipoComportamiento = comportamiento;
		}


		/**
		 *  Sets the Id attribute of the ElementoComportamiento object
		 *
		 *@param  id  The new Id value
		 */
		public void setId(String id) {
			this.id = id;
		}


		/**
		 *  Sets the ModeloPsicologico attribute of the ElementoComportamiento
		 *  object
		 *
		 *@param  modeloPsicologico  The new ModeloPsicologico value
		 */
		public void setModeloPsicologico(String modeloPsicologico) {
			this.modeloPsicologico = modeloPsicologico;
		}


		/**
		 *  Sets the ModeloSocial attribute of the ElementoComportamiento object
		 *
		 *@param  modeloSocial  The new ModeloSocial value
		 */
		public void setModeloSocial(String modeloSocial) {
			this.modeloSocial = modeloSocial;
		}


		/**
		 *  Sets the Porcentaje attribute of the ElementoComportamiento object
		 *
		 *@param  porcentaje  The new Porcentaje value
		 */
		public void setPorcentaje(double porcentaje) {
			this.porcentaje = porcentaje;
		}


		/**
		 *  Sets the TipoComportamiento attribute of the ElementoComportamiento
		 *  object
		 *
		 *@param  tipoComportamiento  The new TipoComportamiento value
		 */
		public void setTipoComportamiento(String tipoComportamiento) {
			this.tipoComportamiento = tipoComportamiento;
		}


		/**
		 *  Gets the Id attribute of the ElementoComportamiento object
		 *
		 *@return    The Id value
		 */
		public String getId() {
			return id;
		}


		/**
		 *  Gets the ModeloPsicologico attribute of the ElementoComportamiento
		 *  object
		 *
		 *@return    The ModeloPsicologico value
		 */
		public String getModeloPsicologico() {
			return modeloPsicologico;
		}


		/**
		 *  Gets the ModeloSocial attribute of the ElementoComportamiento object
		 *
		 *@return    The ModeloSocial value
		 */
		public String getModeloSocial() {
			return modeloSocial;
		}


		/**
		 *  Gets the Porcentaje attribute of the ElementoComportamiento object
		 *
		 *@return    The Porcentaje value
		 */
		public double getPorcentaje() {
			return porcentaje;
		}


		/**
		 *  Gets the TipoComportamiento attribute of the ElementoComportamiento
		 *  object
		 *
		 *@return    The TipoComportamiento value
		 */
		public String getTipoComportamiento() {
			return tipoComportamiento;
		}


		/**
		 *  Converts to a String representation of the object.
		 *
		 *@return    A string representation of the object.
		 */
		public String toString() {
			StringBuffer buf = new StringBuffer();
			buf.append(id);
			buf.append("(");
			buf.append(tipoComportamiento);
			if (padre) {
				buf.append(",");
				buf.append(modeloSocial);
				buf.append(",");
				buf.append(modeloPsicologico);
				buf.append(",");
				buf.append(porcentaje);
				buf.append(")");
			}
			else {
				buf.append(")");
			}
			return buf.toString();
		}
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	private class Par {
		private ModeloTablaEdicion modelo;
		private ComboTextoCellEditor editor;


		/**
		 *  Constructor for the Par object
		 *
		 *@param  modelo  Description of Parameter
		 *@param  editor  Description of Parameter
		 */
		public Par(ModeloTablaEdicion modelo, ComboTextoCellEditor editor) {
			this.modelo = modelo;
			this.editor = editor;
		}


		/**
		 *  Sets the Editor attribute of the Par object
		 *
		 *@param  editor  The new Editor value
		 */
		public void setEditor(ComboTextoCellEditor editor) {
			this.editor = editor;
		}


		/**
		 *  Sets the Modelo attribute of the Par object
		 *
		 *@param  modelo  The new Modelo value
		 */
		public void setModelo(ModeloTablaEdicion modelo) {
			this.modelo = modelo;
		}


		/**
		 *  Gets the Editor attribute of the Par object
		 *
		 *@return    The Editor value
		 */
		public ComboTextoCellEditor getEditor() {
			return editor;
		}


		/**
		 *  Gets the Modelo attribute of the Par object
		 *
		 *@return    The Modelo value
		 */
		public ModeloTablaEdicion getModelo() {
			return modelo;
		}


		/**
		 *  Converts to a String representation of the object.
		 *
		 *@return    A string representation of the object.
		 */
		public String toString() {
			return modelo.toString();
		}
	}
}
