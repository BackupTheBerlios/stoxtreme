package stoxtreme.cliente.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import mseries.Calendar.MDateSelectorConstraints;
import mseries.Calendar.MDefaultPullDownConstraints;
import mseries.Calendar.MFieldListener;
import mseries.ui.MDateEntryField;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.*;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.*;
import org.jfree.data.xy.*;
import org.jfree.date.SerialDateUtilities;

import stoxtreme.cliente.Cliente;
import stoxtreme.cliente.infoLocal.DatoHistorico;
import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.cliente.infoLocal.InfoLocal;
import stoxtreme.cliente.infoLocal.ParserInfoLocal;
import stoxtreme.interfaz_remota.Operacion;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
@SuppressWarnings("serial")
public class MainFrameCliente extends JFrame {
	/**
	 *  permite mostrar o no el volumen de una acción
	 */
	public boolean volumen;
	/**
	 *  permite mostrar o no la gráfica del estocastico de una acción
	 */
	public boolean estocastico;
	/**
	 *  Cuenta las gráficas mostradas
	 */
	public int contadorGraficas = 1;
	double max = 0;
	double min = 1000000;
	double cierre;
	private ModeloCartera modeloCartera;
	//private JTabbedPane tabbedPane;
	private ModeloOpPendientes modeloOpPendientes;
	private ModeloPrecioAccionesGrafico modeloPrecios;
	private ModeloListaEmpresas modeloLEmpresas;
	private InfoLocal info = InfoLocal.getInstance();
	private Cliente cliente;
	private JTable tablaArribaIzq;
	// Asociada al combo de tipos
	private ChartPanel chartPanel;
	// Asociada al combo de empresas
	private XYPlot chartPlot;
	private JRadioButton opPend;
	private JRadioButton cartera;
	private JTextField precioSeleccionado;
	private JTextField cantidadSeleccionada;
	private JComboBox tipoSeleccionado;
	private JComboBox empresaSeleccionada;
	private JSplitPane split_graficas;
	private Hashtable graficas = new Hashtable();
	private HerramientaAgentesPanel hAgentes;
	private EstadoBolsa eBolsa;
	private boolean grafHistorico = false;
	private JPanel panelInfo;
	private JTextPane panelInfoEmpresa;
	private String empresaInfo;
	private JPanel p2;
	private JPanel p3;

	private boolean seleccionado = false;


	/**
	 *  Constructor de MainFrameCliente
	 *  
	 *@param  cliente             Cliente asociado
	 *@param  modeloCartera       Cartera del cliente
	 *@param  modeloOpPendientes  Operaciones pendientes del cliente
	 *@param  eBolsa              Estado de la bolsa
	 *@param  hAgentes            Herramienta de agentes asociados al cliente
	 */
	public MainFrameCliente(Cliente cliente,
			ModeloCartera modeloCartera,
			ModeloOpPendientes modeloOpPendientes,
			EstadoBolsa eBolsa, HerramientaAgentesPanel hAgentes) {
		super("Stock Xtreme");
		this.cliente = cliente;
		hAgentes.setCliente(cliente);
		this.eBolsa = eBolsa;
		this.modeloCartera = modeloCartera;
		this.modeloOpPendientes = modeloOpPendientes;
		this.modeloPrecios = eBolsa.getMAcciones();
		this.modeloLEmpresas = new ModeloListaEmpresas(info.getEmpresas());
		this.volumen = false;
		this.estocastico = false;
		this.hAgentes = hAgentes;
		this.panelInfoEmpresa = new JTextPane();
	}


	/**
	 *  Constructor de MainFrameCliente
	 *
	 *@param  cartera2      Cartera del cliente
	 *@param  opPendientes  Operaciones pendientes del cliente
	 *@param  precios       Precios actuales del servidor
	 */
	public MainFrameCliente(ModeloCartera cartera2, ModeloOpPendientes opPendientes, ModeloPrecioAccionesGrafico precios) {
		super("Stock Xtreme");
		this.cliente = null;
		this.modeloCartera = cartera2;
		this.modeloOpPendientes = opPendientes;
		this.modeloPrecios = precios;
		this.volumen = false;
		this.estocastico = false;
	}


	/**
	 *  Obtiene el valor de PanelInfo
	 *
	 *@return    Valor de PanelInfo
	 */
	public JSplitPane getPanelInfo() {
		panelInfo = new JPanel(new BorderLayout());
		panelInfo.add(getVisualizador(), BorderLayout.CENTER);
		JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getPanelEmpresas(), panelInfo);
		return panel;
	}


	/**
	 *  Obtiene el valor de PanelEmpresas attribute of the MainFrameCliente object
	 *
	 *@return    The PanelEmpresas value
	 */
	public JPanel getPanelEmpresas() {
		TableSorter sorterEmpresas = new TableSorter(modeloLEmpresas);
		final JTable tabla = new JTable(sorterEmpresas);
		sorterEmpresas.setTableHeader(tabla.getTableHeader());
		tabla.getTableHeader().setToolTipText("Pinche en cualquier empresa para ver información sobre ella");
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowSM = tabla.getSelectionModel();
		rowSM.addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							//Ignore extra messages.
							if (e.getValueIsAdjusting()) {
								return;
							}
							ListSelectionModel lsm =
									(ListSelectionModel) e.getSource();
							if (!lsm.isSelectionEmpty()) {
								int selectedRow = lsm.getMinSelectionIndex();
								empresaInfo = tabla.getValueAt(selectedRow, 0).toString();
								StyledDocument doc = panelInfoEmpresa.getStyledDocument();
								try {
									doc.remove(0, doc.getLength());
									doc.insertString(0, empresaInfo + "\n\n", null);
									doc.insertString(doc.getLength() - 1, "Sector: " + info.getSector(empresaInfo) + "\n", null);
									doc.insertString(doc.getLength() - 1, "Capital social: " + info.getCapitalSocial(empresaInfo) + "\n", null);
									doc.insertString(doc.getLength() - 1, "Valor nominal de cada accion: " + info.getValorNominal(empresaInfo) + "\n", null);
									doc.insertString(doc.getLength() - 1, "Número de ampliaciones de capital: " + info.getAmpliacionesCapital(empresaInfo) + "\n\n", null);
									doc.insertString(doc.getLength() - 1, info.getDescripcion(empresaInfo) + "\n", null);
								}
								catch (BadLocationException ex) {
									ex.printStackTrace();
								}
								panelInfoEmpresa.setCaretPosition(0);
							}
						}
					});

		JPanel panelEmpresas = new JPanel();
		panelEmpresas.setLayout(new BoxLayout(panelEmpresas, BoxLayout.Y_AXIS));
		JPanel botonHistorico = new JPanel();
		JButton historico = new JButton("Consultar Histórico");
		botonHistorico.add(historico);
		historico.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							dividirInfoScreen();
						}
					}
				);
		panelEmpresas.add(new JScrollPane(tabla));
		panelEmpresas.add(botonHistorico);
		panelEmpresas.setSize(100, 600);
		return new FakeInternalFrame("Empresas", panelEmpresas);
	}


	/**
	 *  Obtiene el valor de Visualizador attribute of the MainFrameCliente object
	 *
	 *@return    Valor de Visualizador
	 */
	public Component getVisualizador() {
		JScrollPane panelScroll = new JScrollPane(panelInfoEmpresa);
		FakeInternalFrame frame = new FakeInternalFrame("Información", panelScroll);
		return frame;
	}


	/**
	 *  Obtiene el valor de PanelPrincipal
	 *
	 *@return    Valor de PanelPrincipal
	 */
	public Component getPanelPrincipal() {
		JPanel panelIzquierda = new JPanel(new BorderLayout());
		panelIzquierda.add(getPanelIzquierdaArriba(), BorderLayout.CENTER);
		panelIzquierda.add(getPanelIzquierdaAbajo(), BorderLayout.SOUTH);

		JSplitPane panelDerecha = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				getPanelGrafico(), getPanelTablaPrecios());

		JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierda, panelDerecha);

		return panel;
	}


	/**
	 *  Inicialización de la interfaz
	 */
	public void init() {
		JPanel panel = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane = new JTabbedPane();
		tabbedPane.insertTab("Principal", null, getPanelPrincipal(), null, 0);
		tabbedPane.insertTab("Informacion", null, getPanelInfo(), null, 1);
		tabbedPane.insertTab("Agentes", null, hAgentes, null, 2);

		panel.add(tabbedPane, BorderLayout.CENTER);
		panel.add(new PanelCotizaciones(eBolsa), BorderLayout.SOUTH);
		getContentPane().add(panel);
		this.addWindowListener(
					new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							try {
								cliente.desloguea();
							}
							catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					});
	}


	/**
	 *  Configuracion de la pestaña información
	 */
	public void dividirInfoScreen() {
		grafHistorico = !grafHistorico;
		if (grafHistorico) {
			// Se muestra el split
			JSplitPane panelCortado = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
					getVisualizador(),
					new FakeInternalFrame("Histórico", getGraficaHistorica()));
			panelCortado.setSize(500, 900);
			panelCortado.setDividerLocation(0.3);
			this.panelInfo.removeAll();
			panelInfo.add(panelCortado);
			panelInfo.updateUI();
		}
		else {
			// Se quita
			panelInfo.removeAll();
			panelInfo.add(getVisualizador());
			panelInfo.updateUI();
		}
	}


	/**
	 *  Obtiene el valor de PanelHerramientas
	 *
	 *@return    Valor de PanelHerramientas
	 */
	private Component getPanelHerramientas() {
		JSplitPane panelDerecha = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				getPanelGrafico(), getPanelTablaPrecios());
		return panelDerecha;
	}


	/**
	 *  Obtiene el valor de GraficaHistorica
	 *
	 *@return    Valor de GraficaHistorica
	 */
	private JPanel getGraficaHistorica() {

		try {
			MDateEntryField entryField = new MDateEntryField();
			MDefaultPullDownConstraints c = new MDefaultPullDownConstraints();
			entryField.setConstraints(c);
			entryField.setSize(new Dimension(300, 200));
			entryField.addMFieldListener(
						new MFieldListener() {
							public void fieldEntered(FocusEvent e) {
								//System.out.println("Entered");
							}


							public void fieldExited(FocusEvent e) {
								//System.out.println("Exited");
							}
						});
			Date fechaIni;
			JOptionPane.showMessageDialog(this, entryField, "Fecha Inicial", JOptionPane.INFORMATION_MESSAGE);
			fechaIni = entryField.getValue();
			JOptionPane.showMessageDialog(this, entryField, "Fecha Final", JOptionPane.INFORMATION_MESSAGE);
			Date fechaFin = entryField.getValue();
			DateAxis ejeX = new DateAxis();
			Calendar c1 = Calendar.getInstance();
			c1.setTime(fechaIni);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(fechaFin);
			ejeX.setMaximumDate(c2.getTime());
			ejeX.setMinimumDate(c1.getTime());
			NumberAxis ejeY = new NumberAxis();
			ejeY.setRange(new Range(0, 540));
			DefaultCategoryDataset auxds = new DefaultCategoryDataset();
			String fechaAux;
			while (c1.compareTo(c2) != 0) {
				fechaAux = c1.get(Calendar.DAY_OF_MONTH) + "/" + (c1.get(Calendar.MONTH) + 1) + "/" + c1.get(Calendar.YEAR);
				//CAMBIAR EMPRESA SELCCIONADA
				DatoHistorico aux = ParserInfoLocal.getDatoHistorico(empresaInfo.toLowerCase().replace(" ", "_"), fechaAux);
				auxds.addValue(aux.getPrecioCierre(), "Precio de cierre", fechaAux.split("/")[0] + "/" + fechaAux.split("/")[1] + "/" + fechaAux.split("/")[2]);
				c1.add(Calendar.DATE, 1);
			}
			JFreeChart chart =
					ChartFactory.createLineChart(empresaInfo,
					"Fecha", "Precio de cierre", auxds, PlotOrientation.VERTICAL,
					true,
					true,
					true
			// Show legend
					);
			ChartPanel panel = new ChartPanel(chart);
			return panel;
		}
		catch (ParseException e1) {
			e1.printStackTrace();
			return null;
		}
	}


	/**
	 *  Obtiene el valor dePanelIzquierdaArriba
	 *
	 *@return    Valor de PanelIzquierdaArriba
	 */
	private Component getPanelIzquierdaArriba() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelArriba = new JPanel();
		panelArriba.add(new JLabel("Operaciones pendientes"));
		opPend = new JRadioButton();
		cartera = new JRadioButton();
		opPend.setAction(
					new AbstractAction() {
						public void actionPerformed(ActionEvent e) {
							opPend_actionPerformed(e);
						}
					});

		cartera.setAction(
					new AbstractAction() {
						public void actionPerformed(ActionEvent e) {
							cartera_actionPerformed(e);
						}
					});
		panelArriba.add(opPend);
		panelArriba.add(new JLabel("   "));
		panelArriba.add(new JLabel("Mi cartera de acciones"));
		panelArriba.add(cartera);
		panel.add(panelArriba, BorderLayout.NORTH);
		opPend.setSelected(true);
		tablaArribaIzq = new JTable(modeloOpPendientes);

		tablaArribaIzq.getColumnModel().getColumn(0).setCellRenderer(modeloOpPendientes.getRenderer());
		tablaArribaIzq.getColumnModel().getColumn(0).setCellEditor(
					new EditorOpPendientes() {
						public void borraOperacion(int fila) {
							try {
								cliente.cancelarOperacion(((Integer) modeloOpPendientes.getValueAt(fila, 1)).intValue());
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
		tablaArribaIzq.getColumn(tablaArribaIzq.getColumnName(0)).setMaxWidth(25);
		panel.add(new JScrollPane(tablaArribaIzq));
		return panel;
	}


	/**
	 *  Obtiene el valor de PanelGrafico
	 *
	 *@return    Valor de PanelGrafico
	 */
	private Component getPanelGrafico() {
		MDateEntryField entryField = new MDateEntryField();
		MDateSelectorConstraints c = new MDefaultPullDownConstraints();
		//c.firstDay = Calendar.MONDAY;
		entryField.setConstraints(c);
		entryField.addMFieldListener(
					new MFieldListener() {
						public void fieldEntered(FocusEvent e) {
						}


						public void fieldExited(FocusEvent e) {
						}
					});

		JFrame frame2 = new JFrame();
		frame2.getContentPane().add(entryField);

		//MenuShortcut s=new MenuShortcut(37);
		JMenuItem vol = new JMenuItem("Volumen");
		JMenuItem esto = new JMenuItem("Estocastico");
		JMenu indicadores = new JMenu("Indicadores");
		vol.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							pinta_actionPerformed(event);
						}
					}
				);
		esto.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							pinta_actionPerformed(event);
						}
					}
				);
		JMenu help = new JMenu("Ayuda");
		JMenuItem volA = new JMenuItem("Ayuda Volumen");
		JMenuItem estoA = new JMenuItem("Ayuda Estocastico");
		volA.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							pinta_actionPerformed(event);
						}
					}
				);
		estoA.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							pinta_actionPerformed(event);
						}
					}
				);
		indicadores.add(vol);
		indicadores.add(esto);
		help.add(volA);
		help.add(estoA);
		JMenuBar barra = new JMenuBar();
		barra.add(indicadores);
		barra.add(help);
		split_graficas = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		graficas.put("normal", getChartPanel());

		split_graficas.add(getChartPanel());
		int tam = split_graficas.size().height - split_graficas.size().height / 6;
		split_graficas.setDividerLocation(400);
		FakeInternalFrame frame = new FakeInternalFrame("Precio Accion", split_graficas);
		frame.setPreferredSize(new Dimension(500, 450));
		frame.setMenuBar(barra);
		return frame;
	}


	/**
	 *  Obtiene el valor de ChartPanel2 gráfica del volumen
	 *
	 *@return     Valor de ChartPanel2
	 */
	private JPanel getChartPanel2() {

		try {
			MDateEntryField entryField = new MDateEntryField();

			MDateSelectorConstraints c = new MDefaultPullDownConstraints();
			entryField.setConstraints(c);
			entryField.addMFieldListener(
						new MFieldListener() {
							public void fieldEntered(FocusEvent e) {
								//System.out.println("Entered");
							}


							public void fieldExited(FocusEvent e) {
								//System.out.println("Exited");
							}
						});
			Date fechaIni;
			JOptionPane.showMessageDialog(this, entryField, "Fecha Inicial", JOptionPane.INFORMATION_MESSAGE);
			fechaIni = entryField.getValue();
			JOptionPane.showMessageDialog(this, entryField, "Fecha Final", JOptionPane.INFORMATION_MESSAGE);
			Date fechaFin = entryField.getValue();
			DateAxis ejeX = new DateAxis();
			Calendar c1 = Calendar.getInstance();
			c1.setTime(fechaIni);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(fechaFin);
			ejeX.setMaximumDate(c2.getTime());
			ejeX.setMinimumDate(c1.getTime());
			DefaultCategoryDataset auxds = new DefaultCategoryDataset();
			int j = 0;
			String fechaAux;
			while (c1.compareTo(c2) != 0) {
				fechaAux = c1.get(Calendar.DAY_OF_MONTH) + "/" + (c1.get(Calendar.MONTH) + 1) + "/" + c1.get(Calendar.YEAR);
				DatoHistorico aux = ParserInfoLocal.getDatoHistorico(modeloPrecios.empresaSeleccionada().toLowerCase().replace(" ", "_"), fechaAux);
				auxds.addValue(aux.getVolumen(), "Volumen", fechaAux.split("/")[0] + "/" + fechaAux.split("/")[1]);
				c1.add(Calendar.DATE, 1);
			}
			JFreeChart chart = ChartFactory.createBarChart(modeloPrecios.empresaSeleccionada(),
					"Dias", "Volumen", auxds, PlotOrientation.VERTICAL,
					true, true, true);
			chart.clearSubtitles();
			chart.getSubtitles().clear();
			ChartPanel panel = new ChartPanel(chart);
			return panel;
		}
		catch (ParseException e1) {
			e1.printStackTrace();
			return null;
		}

	}


	/**
	 *  Obtiene el valor de ChartPanel3 Gráfica del estocastica
	 *  formula del estocastico 100*(C-Min/max-min) c es el cierre
	 *
	 *@return    Valor de ChartPanel3
	 */
	private JPanel getChartPanel3() {
		try {
			MDateEntryField entryField = new MDateEntryField();

			MDateSelectorConstraints c = new MDefaultPullDownConstraints();
			entryField.setConstraints(c);
			entryField.addMFieldListener(
						new MFieldListener() {
							public void fieldEntered(FocusEvent e) {
								//System.out.println("Entered");
							}


							public void fieldExited(FocusEvent e) {
								//System.out.println("Exited");
							}
						});
			Date fechaIni;
			JOptionPane.showMessageDialog(this, entryField, "Fecha Inicial", JOptionPane.INFORMATION_MESSAGE);
			fechaIni = entryField.getValue();
			JOptionPane.showMessageDialog(this, entryField, "Fecha Final", JOptionPane.INFORMATION_MESSAGE);
			Date fechaFin = entryField.getValue();
			DateAxis ejeX = new DateAxis();
			Calendar c1 = Calendar.getInstance();
			c1.setTime(fechaIni);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(fechaFin);
			ejeX.setMaximumDate(c2.getTime());
			ejeX.setMinimumDate(c1.getTime());
			NumberAxis ejeY = new NumberAxis();
			ejeY.setRange(new Range(0, 540));
			DefaultCategoryDataset auxds = new DefaultCategoryDataset();
			int j = 1;
			String fechaAux;
			XYSeries series = new XYSeries("Estocastico");
			while (c1.compareTo(c2) != 0) {
				calculaMaxenFecha(c1);
				fechaAux = c1.get(Calendar.DAY_OF_MONTH) + "/" + (c1.get(Calendar.MONTH) + 1) + "/" + c1.get(Calendar.YEAR);
				auxds.addValue((100 * (cierre - min)) / (max - min), "Estocastico", fechaAux.split("/")[0] + "/" + fechaAux.split("/")[1]);
				series.add(j, (100 * (cierre - min)) / (max - min));
				c1.add(Calendar.DATE, 1);

			}
			XYDataset juegoDatos = new XYSeriesCollection(series);
			JFreeChart chart =
					ChartFactory.createLineChart("Estocastico",
					"Meses", "Sesiones", auxds, PlotOrientation.VERTICAL,
					false,
					false,
					true
			// Show legend
					);
			chartPlot = new XYPlot(
					modeloPrecios.getVolumenSeleccionado(),
					ejeX, ejeY, new XYLineAndShapeRenderer(true, false));
			ChartPanel panel = new ChartPanel(chart);
			return panel;
		}
		catch (ParseException e1) {
			e1.printStackTrace();
			return null;
		}
	}


	/**
	 *  Obtiene el valor de ChartPanel Gráfico intradia de los valores
	 *
	 *@return    Valor de ChartPanel
	 */
	private ChartPanel getChartPanel() {
		DateAxis ejeX = new DateAxis();
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.AM_PM, Calendar.AM);
		c1.set(Calendar.HOUR, 8);
		c1.set(Calendar.MINUTE, 30);
		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.AM_PM, Calendar.PM);
		c2.set(Calendar.HOUR, 5);
		c2.set(Calendar.MINUTE, 30);
		ejeX.setMaximumDate(c2.getTime());
		ejeX.setMinimumDate(c1.getTime());

		NumberAxis ejeY = new NumberAxis();
		ejeY.setRange(new Range(0, 540));

		chartPlot = new XYPlot(
				modeloPrecios.getPreciosSeleccionados(),
				ejeX, ejeY, new XYLineAndShapeRenderer(true, false));
		JFreeChart chart = new JFreeChart(chartPlot);
		ChartPanel panel = new ChartPanel(chart);
		return panel;
	}


	/**
	 *  Obtiene el valor de PanelIzquierdaAbajo
	 *
	 *@return    Valor de PanelIzquierdaAbajo
	 */
	private Component getPanelIzquierdaAbajo() {
		JPanel panel = new JPanel(new GridLayout(5, 1));
		JPanel p = new JPanel();
		p.add(new JLabel("                Empresa "));
		empresaSeleccionada = new JComboBox(modeloPrecios.getModeloComboBox());
		empresaSeleccionada.setSize(new Dimension(90, empresaSeleccionada.getPreferredSize().height));

		empresaSeleccionada.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (seleccionado) {
								modeloPrecios.escuchaPrecio(
										(String) empresaSeleccionada.getSelectedItem(),
										precioSeleccionado);
							}
							else {
								modeloPrecios.ponPrecio(
										(String) empresaSeleccionada.getSelectedItem(),
										precioSeleccionado);
							}
						}
					});
		p.add(empresaSeleccionada);
		panel.add(p);

		String[] tipos = {"Compra", "Venta"};
		p = new JPanel();
		p.add(new JLabel("         Tipo Operacion "));
		tipoSeleccionado = new JComboBox(tipos);
		tipoSeleccionado.setPreferredSize(new Dimension(90, tipoSeleccionado.getPreferredSize().height));
		p.add(tipoSeleccionado);
		panel.add(p);

		p = new JPanel();
		p.add(new JLabel("  Numero de acciones "));
		cantidadSeleccionada = new JTextField(11);
		p.add(cantidadSeleccionada);
		panel.add(p);

		p = new JPanel();
		p.add(new JLabel("Precio de compra  "));
		precioSeleccionado = new JTextField(11);

		p.add(new JCheckBox(
							new AbstractAction() {

								public void actionPerformed(ActionEvent e) {
									seleccionado = !seleccionado;
									precioSeleccionado.setEnabled(!seleccionado);
									if (seleccionado) {
										String empresa = (String) empresaSeleccionada.getSelectedItem();
										modeloPrecios.escuchaPrecio(empresa, precioSeleccionado);
									}
									else {
										modeloPrecios.quitaEscuchaPrecio();
									}
								}
							}));
		p.add(precioSeleccionado);
		panel.add(p);

		p = new JPanel();
		JButton insertar = new JButton("Insertar Operacion");
		insertar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								String id = cliente.getNUsuario();
								String empresa = (String) empresaSeleccionada.getSelectedItem();
								String tipoOp = ((String) tipoSeleccionado.getSelectedItem()).toLowerCase();
								int tipo = Operacion.COMPRA;
								if (tipoOp.equals("venta")) {
									tipo = Operacion.VENTA;
								}
								double precio = Double.parseDouble(precioSeleccionado.getText());
								int cantidad = Integer.parseInt(cantidadSeleccionada.getText());
								Operacion op = new Operacion(id, tipo, cantidad, empresa, precio);
								cliente.insertarOperacion(op);
							}
							catch (Exception ex) {
								JOptionPane.showConfirmDialog(null, "Error en los datos");
							}
						}
					});
		p.add(insertar);
		JButton limpiar = new JButton("Limpiar Campos");
		limpiar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							precioSeleccionado.setText("");
							cantidadSeleccionada.setText("");
						}
					});
		p.add(limpiar);
		panel.add(p);

		FakeInternalFrame frame = new FakeInternalFrame("Insertar una Operacion", panel);
		return frame;
	}


	/**
	 *  Obtenemos el valor de PanelTablaPrecios
	 *
	 *@return    Valor de PanelTablaPrecios
	 */
	private Component getPanelTablaPrecios() {
		TableSorter sorterPrecios = new TableSorter(modeloPrecios);
		JTable tabla = new JTable(sorterPrecios);
		sorterPrecios.setTableHeader(tabla.getTableHeader());
		tabla.getColumn(tabla.getColumnName(1)).setCellRenderer(modeloPrecios.getRenderer());

		tabla.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							chartPlot.setDataset(modeloPrecios.getPreciosSeleccionados());
							JTable t = (JTable) e.getSource();
							int row = t.getSelectedRow();
							if (row != -1) {
								precioSeleccionado.setText(t.getModel().getValueAt(row, 1).toString());
								empresaSeleccionada.setSelectedItem(t.getModel().getValueAt(row, 0).toString());
								tipoSeleccionado.setSelectedItem("Compra");
							}
						}
					});
		FakeInternalFrame frame = new FakeInternalFrame("Empresas", new JScrollPane(tabla));
		frame.setPreferredSize(new Dimension(500, 150));
		return frame;
	}


	/**
	 *  Evento al introducir el Cliente una operación
	 *
	 *@param  e  Evento capturado
	 */
	private void opPend_actionPerformed(ActionEvent e) {
		opPend.setSelected(true);
		cartera.setSelected(false);
		tablaArribaIzq.setModel(modeloOpPendientes);
		tablaArribaIzq.getColumnModel().getColumn(0).setCellRenderer(modeloOpPendientes.getRenderer());
		tablaArribaIzq.getColumnModel().getColumn(0).setCellEditor(
					new EditorOpPendientes() {
						public void borraOperacion(int fila) {
							try {
								cliente.cancelarOperacion(((Integer) modeloOpPendientes.getValueAt(fila, 1)).intValue());
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
		tablaArribaIzq.getColumn(tablaArribaIzq.getColumnName(0)).setMaxWidth(10);
	}


	/**
	 *  Evento que se produce cuando se procesan las Operaciones pendientes
	 *
	 *@param  e  Evento capturado
	 */
	private void cartera_actionPerformed(ActionEvent e) {
		TableSorter sorter = new TableSorter();

		opPend.setSelected(false);
		cartera.setSelected(true);
		tablaArribaIzq.setModel(modeloCartera);
		tablaArribaIzq.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							JTable src = (JTable) e.getSource();
							if (src.getModel() instanceof ModeloCartera) {
								int row = src.getSelectedRow();
								String empresa = (String) src.getModel().getValueAt(row, 0);
								Integer nAcciones = (Integer) src.getModel().getValueAt(row, 1);
								empresaSeleccionada.setSelectedItem(empresa);
								cantidadSeleccionada.setText(nAcciones.toString());
								tipoSeleccionado.setSelectedItem("Venta");
							}
						}
					});
	}


	/**
	 *  Evento que permite dibujar las gráficas deseadas por el Cliente
	 *
	 *@param  e  Evento capturado
	 */
	private void pinta_actionPerformed(ActionEvent e) {
		//split_graficas.setDividerLocation(50);
		if (e.getActionCommand().equals("Ayuda Volumen")) {
			JTextPane menuAyuda = new JTextPane();
			menuAyuda.setSize(200, 200);
			menuAyuda.setText("Para obtener el volumen de un valor realizar:\n" +
					"1.-Seleccionar indicadores->Volumen\n2.-Elegir la fecha de" +
					" comienzo del periodo que se desee consultar\n3.-Elegir la fecha final del periodo");
			menuAyuda.setEditable(false);
			JOptionPane.showMessageDialog(this, menuAyuda, "Ayuda Herramientas", JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getActionCommand().equals("Ayuda Estocastico")) {
			JTextPane menuAyuda = new JTextPane();
			menuAyuda.setSize(200, 200);
			menuAyuda.setText("Para obtener la Gráfica del Estocástico de un valor realizar:\n" +
					"1.-Seleccionar indicadores->Volumen\n2.-Elegir la fecha de" +
					" comienzo del periodo que se desee consultar\n3.-Elegir la fecha final del periodo\n" +
					"4.-Si la gráfica esta baja significa que la acción está sobre vendida" +
					" con lo que es posible que suba\n5.- si la gráfica esta alta esta sobre comprado con lo que es posible que baje");
			menuAyuda.setEditable(false);
			JOptionPane.showMessageDialog(this, menuAyuda, "Ayuda Herramientas", JOptionPane.INFORMATION_MESSAGE);
		}
		//JOptionPane.showMessageDialog(tablaArribaIzq,null,"Has pulsado el volumen",JOptionPane.CANCEL_OPTION);
		if (e.getActionCommand().equals("Volumen")) {
			if (volumen) {
				volumen = false;
				split_graficas.remove((Component) graficas.get("volumen"));
				graficas.remove("volumen");
				if (estocastico) {
					split_graficas.remove((Component) graficas.get("estocastico"));
					graficas.remove("estocastico");
					split_graficas.remove(2);
					graficas.put("estocastico", p3);
					split_graficas.setBottomComponent(p3);
				}
			}
			else {
				volumen = true;
				p2 = getChartPanel2();
				graficas.put("volumen", p2);
				split_graficas.setBottomComponent(p2);
				split_graficas.setDividerLocation(200);

			}
		}
		if (e.getActionCommand().equals("Estocastico")) {
			if (estocastico) {
				estocastico = false;
				split_graficas.remove((Component) graficas.get("estocastico"));
				graficas.remove("estocastico");
				if (volumen) {
					split_graficas.remove((Component) graficas.get("volumen"));
					graficas.remove("volumen");
					split_graficas.remove(2);
					graficas.put("volumen", p2);
					split_graficas.setBottomComponent(p2);
				}
			}
			else {
				estocastico = true;
				p3 = getChartPanel3();
				graficas.put("estocastico", p3);
				split_graficas.setBottomComponent(p3);
				split_graficas.setDividerLocation(200);
			}
		}
		JSplitPane aux = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		if (volumen && estocastico) {
			split_graficas.setDividerLocation(175);
			aux.add(p2);
			aux.setDividerLocation(100);
			aux.setBottomComponent(p3);
			split_graficas.setBottomComponent(aux);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  f  Description of Parameter
	 */
	private void calculaMaxenFecha(Calendar f) {
		f.add(Calendar.DATE, -7);
		int i = 0;
		DatoHistorico aux;
		while (i < 7) {
			String fechaAux = f.get(Calendar.DAY_OF_MONTH) + "/" + (f.get(Calendar.MONTH) + 1) + "/" + f.get(Calendar.YEAR);
			aux = ParserInfoLocal.getDatoHistorico(modeloPrecios.empresaSeleccionada().toLowerCase().replace(" ", "_"), fechaAux);
			if (aux.getPrecioMaximo() > max) {
				max = aux.getPrecioMaximo();
			}
			if (aux.getPrecioMinimo() < min) {
				min = aux.getPrecioMinimo();
			}
			f.add(Calendar.DATE, 1);
			i++;
			if (i == 7) {
				cierre = aux.getPrecioCierre();
			}
		}
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class OyenteMenu implements ActionListener {

		/**
		 *  Description of the Method
		 *
		 *@param  e  Description of Parameter
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().compareTo("Volumen") == 0) {
				volumen = true;
			}

		}

	}
}
