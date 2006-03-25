package stoxtreme.cliente.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.*;
import org.jfree.data.time.*;
import org.jfree.data.xy.*;
import org.jfree.date.SerialDateUtilities;

import stoxtreme.cliente.Cliente;
import stoxtreme.interfaz_remota.Operacion;

public class MainFrameCliente extends JFrame{
	private ModeloCartera modeloCartera;
	private ModeloOpPendientes modeloOpPendientes;
	private ModeloPrecioAccionesGrafico modeloPrecios;
	private Cliente cliente;
	
	private JTable tablaArribaIzq; // Asociada al combo de tipos
	private ChartPanel chartPanel; // Asociada al combo de empresas
	private XYPlot chartPlot;
	private JRadioButton opPend;
	private JRadioButton cartera;
	private JTextField precioSeleccionado;
	private JTextField cantidadSeleccionada;
	private JComboBox tipoSeleccionado;
	private JComboBox empresaSeleccionada;
	
	static{
		try{
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		}
		catch(Exception e){
			
		}
	}
	public MainFrameCliente(Cliente cliente,
			ModeloCartera modeloCartera,
			ModeloOpPendientes modeloOpPendientes,
			ModeloPrecioAccionesGrafico modeloPrecios){
		super("Stock Xtreme");
		this.cliente = cliente;
		this.modeloCartera = modeloCartera;
		this.modeloOpPendientes = modeloOpPendientes;
		this.modeloPrecios = modeloPrecios;
	}
	
	public void init(){
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.insertTab("Principal", null, getPanelPrincipal(), null, 0);
		tabbedPane.insertTab("Informacion", null, new JPanel(), null, 1);
		tabbedPane.insertTab("Agentes", null, new JPanel(), null, 2);
		getContentPane().add(tabbedPane);
		this.addWindowListener(new WindowListener(){
			public void windowOpened(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
				try {
					cliente.deslogea();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}
		});
	}
	
	public JSplitPane getPanelPrincipal(){
		JPanel panelIzquierda = new JPanel(new BorderLayout());
		panelIzquierda.add(getPanelIzquierdaArriba(), BorderLayout.CENTER);
		panelIzquierda.add(getPanelIzquierdaAbajo(), BorderLayout.SOUTH);

		JSplitPane panelDerecha = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				getPanelDerechaArriba(), getPanelDerechaAbajo());
		
		JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierda, panelDerecha);

		return panel;
	}

	private Component getPanelIzquierdaArriba() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelArriba = new JPanel();
		panelArriba.add(new JLabel("Operaciones pendientes"));
		opPend = new JRadioButton();
		cartera = new JRadioButton();
		opPend.setAction(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				opPend.setSelected(true);
				cartera.setSelected(false);
				tablaArribaIzq.setModel(modeloOpPendientes);
				tablaArribaIzq.getColumnModel().getColumn(0).setCellRenderer(modeloOpPendientes.getRenderer());
				tablaArribaIzq.getColumnModel().getColumn(0).setCellEditor(modeloOpPendientes.getEditor());
				tablaArribaIzq.getColumn(tablaArribaIzq.getColumnName(0)).setMaxWidth(10);
			}
		});
		
		cartera.setAction(new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				opPend.setSelected(false);
				cartera.setSelected(true);
				tablaArribaIzq.setModel(modeloCartera);
				tablaArribaIzq.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e){
						JTable src = (JTable)e.getSource();
						if(src.getModel() instanceof ModeloCartera){
							int row = src.getSelectedRow();
							String empresa = (String)src.getModel().getValueAt(row, 0);
							Integer nAcciones = (Integer)src.getModel().getValueAt(row, 1);
							empresaSeleccionada.setSelectedItem(empresa);
							cantidadSeleccionada.setText(nAcciones.toString());
							tipoSeleccionado.setSelectedItem("Venta");
						}
					}
				});
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
		tablaArribaIzq.getColumnModel().getColumn(0).setCellEditor(modeloOpPendientes.getEditor());
		tablaArribaIzq.getColumn(tablaArribaIzq.getColumnName(0)).setMaxWidth(25);
		panel.add(new JScrollPane(tablaArribaIzq));		
		return panel;
	}

	private Component getPanelDerechaArriba() {
		FakeInternalFrame frame = new FakeInternalFrame("Precio Accion", getChartPanel());
		frame.setPreferredSize(new Dimension(500, 450));
		return frame;
	}
	
	private ChartPanel getChartPanel(){
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
				ejeX, ejeY,new XYLineAndShapeRenderer(true, false));
		JFreeChart chart = new JFreeChart(chartPlot);
		ChartPanel panel = new ChartPanel(chart);
		return panel;
	}
	
	private boolean seleccionado = false;
	private Component getPanelIzquierdaAbajo() {
		JPanel panel = new JPanel(new GridLayout(5, 1));
		JPanel p = new JPanel();
		String[] e = {"Empresa1", "Empresa2"};
		p.add(new JLabel("        Empresa   "));
		empresaSeleccionada = new JComboBox(modeloPrecios.getModeloComboBox());
		empresaSeleccionada.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(seleccionado){
					modeloPrecios.escuchaPrecio(
						(String)empresaSeleccionada.getSelectedItem(),
						precioSeleccionado);
				}
				else{
					modeloPrecios.ponPrecio(
							(String)empresaSeleccionada.getSelectedItem(),
							precioSeleccionado);
				}
			}
		});
		p.add(empresaSeleccionada);
		panel.add(p);
		
		String[] tipos = {"Compra", "Venta"};
		p = new JPanel();
		p.add(new JLabel("   Tipo Operacion "));
		tipoSeleccionado = new JComboBox(tipos);
		p.add(tipoSeleccionado);
		panel.add(p);
		
		p = new JPanel();
		p.add(new JLabel("Numero de acciones"));
		cantidadSeleccionada = new JTextField(10);
		p.add(cantidadSeleccionada);
		panel.add(p);
		
		p = new JPanel();
		p.add(new JLabel("Precio de compra  "));
		precioSeleccionado = new JTextField(11);
		p.add(new JCheckBox(new AbstractAction(){
			
			public void actionPerformed(ActionEvent e) {
				seleccionado = !seleccionado;
				precioSeleccionado.setEnabled(!seleccionado);
				if(seleccionado){
					String empresa = (String)empresaSeleccionada.getSelectedItem();
					modeloPrecios.escuchaPrecio(empresa, precioSeleccionado);
				}
				else{
					modeloPrecios.quitaEscuchaPrecio();
				}
			}
		}));
		p.add(precioSeleccionado);
		panel.add(p);
		
		p = new JPanel();
		JButton insertar = new JButton("Insertar Operacion");
		insertar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					String id = cliente.getNUsuario();
					String empresa = (String)empresaSeleccionada.getSelectedItem();
					String tipoOp = ((String)tipoSeleccionado.getSelectedItem()).toLowerCase();
					int tipo = Operacion.COMPRA;
					if(tipoOp.equals("venta")) tipo = Operacion.VENTA;
					double precio = Double.parseDouble(precioSeleccionado.getText());
					int cantidad = Integer.parseInt(cantidadSeleccionada.getText());
					Operacion op = new Operacion(id,tipo, cantidad, empresa, precio );
					cliente.insertarOperacion(op);
				}
				catch(Exception ex){
					JOptionPane.showConfirmDialog(null, "Error en los datos");
				}
			}
		});
		p.add(insertar);
		JButton limpiar = new JButton("Limpiar Campos");
		limpiar.addActionListener(new ActionListener(){
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

	private Component getPanelDerechaAbajo() {
		JTable tabla = new JTable(modeloPrecios);
		tabla.getColumn(tabla.getColumnName(1)).setCellRenderer(modeloPrecios.getRenderer());
		
		tabla.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				chartPlot.setDataset(modeloPrecios.getPreciosSeleccionados());
				JTable t = (JTable)e.getSource();
				int row =  t.getSelectedRow();
				precioSeleccionado.setText(t.getModel().getValueAt(row, 1).toString());
				empresaSeleccionada.setSelectedItem(t.getModel().getValueAt(row, 0).toString());
				tipoSeleccionado.setSelectedItem("Compra");
			}
		});
		FakeInternalFrame frame = new FakeInternalFrame("Empresas", new JScrollPane(tabla));
		frame.setPreferredSize(new Dimension(500, 150));
		return frame;
	}

	
//	public static void main(String[] args){
//		ArrayList lEmpresas = new ArrayList();
//		lEmpresas.add("Empresa1");
//		lEmpresas.add("Empresa2");
//		lEmpresas.add("Empresa3");
//		ModeloOpPendientes mOpPendientes = new ModeloOpPendientes();
//		ModeloCartera mCartera = new ModeloCartera();
//		ModeloPrecioAccionesGrafico mPrecios = new ModeloPrecioAccionesGrafico(lEmpresas);
//
//		MainFrameCliente gui = new MainFrameCliente(mCartera, mOpPendientes, mPrecios);
//		gui.init();
//		gui.pack();
//		gui.setVisible(true);
//		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
		
//		mOpPendientes.insertarOperacion(new Operacion("ID1", Operacion.COMPRA, 100, "Empresa1", 10.0f), 10);
//		mOpPendientes.insertarOperacion(new Operacion("ID1", Operacion.VENTA, 100, "Empresa2", 10.0f), 50);
//		
//		mCartera.insertarAcciones("Empresa1", 100);
//		mCartera.insertarAcciones("Empresa2", 200);
//		
//		int i=0;
//		int j=270;
//		while(true){
//			i++;
//			j+=(Math.random()>0.5?1:-1);
//			if(i<540){
//				mPrecios.insertaValor("Empresa1", i);
//				mPrecios.insertaValor("Empresa2", 540-i);
//				mPrecios.insertaValor("Empresa3", j);
//				try {
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}
