package stoxtreme.cliente.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import mseries.Calendar.MDateSelectorConstraints;
import mseries.Calendar.MDefaultPullDownConstraints;
import mseries.Calendar.MFieldListener;
import mseries.ui.MDateEntryField;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.*;
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

public class MainFrameCliente extends JFrame{
	private ModeloCartera modeloCartera;
	//private JTabbedPane tabbedPane;
	private ModeloOpPendientes modeloOpPendientes;
	private ModeloPrecioAccionesGrafico modeloPrecios;
	private InfoLocal info=new InfoLocal();
	private Cliente cliente;
	public boolean volumen;
	public boolean estocastico;
	public int contadorGraficas=1;
	private JTable tablaArribaIzq; // Asociada al combo de tipos
	private ChartPanel chartPanel; // Asociada al combo de empresas
	private XYPlot chartPlot;
	private JRadioButton opPend;
	private JRadioButton cartera;
	private JTextField precioSeleccionado;
	private JTextField cantidadSeleccionada;
	private JComboBox tipoSeleccionado;
	private JComboBox empresaSeleccionada;
	private JSplitPane split_graficas;
	private Hashtable graficas=new Hashtable();
	private HerramientaAgentesPanel hAgentes;
	private EstadoBolsa eBolsa;
	
	public MainFrameCliente(Cliente cliente,
			ModeloCartera modeloCartera,
			ModeloOpPendientes modeloOpPendientes,
			EstadoBolsa eBolsa,HerramientaAgentesPanel hAgentes){
		super("Stock Xtreme");
		this.cliente = cliente;
		hAgentes.setCliente(cliente);
		this.eBolsa = eBolsa;
		this.modeloCartera = modeloCartera;
		this.modeloOpPendientes = modeloOpPendientes;
		this.modeloPrecios = eBolsa.getMAcciones();
		this.volumen =false;
		this.estocastico =false;
		this.hAgentes=hAgentes;
	}
	
	public MainFrameCliente(ModeloCartera cartera2, ModeloOpPendientes opPendientes, ModeloPrecioAccionesGrafico precios) {
		super("Stock Xtreme");
		this.cliente =null;
		this.modeloCartera = cartera2;
		this.modeloOpPendientes = opPendientes;
		this.modeloPrecios = precios;
		this.volumen =false;
		this.estocastico =false;
	}

	public void init(){
		JPanel panel = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane = new JTabbedPane();
		tabbedPane.insertTab("Principal", null, getPanelPrincipal(), null, 0);
		tabbedPane.insertTab("Informacion", null, getPanelInfo(), null, 1);
		tabbedPane.insertTab("Agentes", null, hAgentes, null, 2);
		
		panel.add(tabbedPane, BorderLayout.CENTER);
		panel.add(new PanelCotizaciones(eBolsa), BorderLayout.SOUTH);
		getContentPane().add(panel);
		this.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent e) {
				try {
					cliente.deslogea();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
	}
	
	public JSplitPane getPanelInfo(){
		JSplitPane panelInfo=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,getPanelEmpresas(),getVisualizador());
	return panelInfo;
	}
	
	public JPanel getPanelEmpresas(){
		JPanel panelEmpresas=new JPanel();
		panelEmpresas.setLayout(new BoxLayout(panelEmpresas,BoxLayout.Y_AXIS));
		String[] headers = {" ","Empresa"};
		JTable tabla=new JTable(35,2);
		tabla.setSelectionBackground((new Color(130,130,200)).brighter());
		JPanel dateSelector= new JPanel();
		JPanel daySelector= new JPanel(new BorderLayout());
		JPanel monthSelector= new JPanel(new BorderLayout());
		JPanel yearSelector= new JPanel(new BorderLayout());
		JPanel botonHistorico=new JPanel();
		JPanel fechaHistorico=new JPanel();
		String[] anyos = {"2004","2005"};
		String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
				"Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		String[] dias = {"01","02","03","04","05","06","07","08","09","10","11","12",
				"13","14","15","16","17","18","19","20","21","22","23","24","25","26",
				"27","28","29","30","31"};
		JLabel a=new JLabel("Año");
		JLabel m=new JLabel("Mes");
		JLabel d=new JLabel("Dia");
		JComboBox anyo=new JComboBox(anyos);
		JComboBox mes=new JComboBox(meses);
		JComboBox dia=new JComboBox(dias);
		daySelector.add(dia,BorderLayout.SOUTH);
		daySelector.add(d,BorderLayout.CENTER);
		monthSelector.add(mes,BorderLayout.SOUTH);
		monthSelector.add(m,BorderLayout.CENTER);
		yearSelector.add(anyo,BorderLayout.SOUTH);
		yearSelector.add(a,BorderLayout.CENTER);
		fechaHistorico.add(daySelector);
		fechaHistorico.add(monthSelector);
		fechaHistorico.add(yearSelector);
		botonHistorico.add(new JButton("Consultar Historico"));
		dateSelector.add(fechaHistorico);
		dateSelector.add(botonHistorico);
		panelEmpresas.add(new JScrollPane(tabla));
		panelEmpresas.add(new FakeInternalFrame("Seleccionar Fecha",dateSelector));
		panelEmpresas.add(botonHistorico);
		panelEmpresas.setSize(200,600);
		return new FakeInternalFrame("Empresas",panelEmpresas);
	}
	
	public JPanel getVisualizador(){
		JPanel visio =new JPanel();
		visio.setSize(600,600);
		return visio;
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
	
	private void opPend_actionPerformed(ActionEvent e) {
		opPend.setSelected(true);
		cartera.setSelected(false);
		tablaArribaIzq.setModel(modeloOpPendientes);
		tablaArribaIzq.getColumnModel().getColumn(0).setCellRenderer(modeloOpPendientes.getRenderer());
		tablaArribaIzq.getColumnModel().getColumn(0).setCellEditor(new EditorOpPendientes(){
			public void borraOperacion(int fila) {
				try {
					cliente.cancelarOperacion(((Integer)modeloOpPendientes.getValueAt(fila, 1)).intValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		tablaArribaIzq.getColumn(tablaArribaIzq.getColumnName(0)).setMaxWidth(10);
	}
	
	private void pinta_actionPerformed(ActionEvent e){
		System.out.println(e.getActionCommand());
		split_graficas.setDividerLocation(300);
		//JOptionPane.showMessageDialog(tablaArribaIzq,null,"Has pulsado el volumen",JOptionPane.CANCEL_OPTION);
	    if (e.getActionCommand().equals("Volumen")){
			if(volumen) {
		    volumen = false;
		    split_graficas.remove((Component)graficas.get("volumen"));
		    graficas.remove("volumen");
		    }
		    else {
		    volumen = true;
		    
		    }
	    }
	     if (e.getActionCommand().equals("Estocastico")){
		    if(estocastico) {
			    estocastico = false;
			    split_graficas.remove((Component)graficas.get("estocastico"));
			    graficas.remove("estocastico");
			    }
			    else {
			    estocastico = true;
			    JPanel p=getChartPanel2();
			    graficas.put("estocastico",p);
			    split_graficas.setBottomComponent(p);
			    //split_graficas.add(p);
			 }
	    }
	    JSplitPane aux=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	    if (volumen && estocastico){
	    	split_graficas.setDividerLocation(200);
			aux.add(getChartPanel2());
			aux.setDividerLocation(100);
			aux.setBottomComponent(getChartPanel3());
			split_graficas.setBottomComponent(aux);
		}
	    else if (volumen){
				JPanel p=getChartPanel2();
			    graficas.put("volumen",p);
			    split_graficas.setBottomComponent(p);			
				//split_graficas.setBottomComponent(p);
			}	
			else{
				if(estocastico)
					split_graficas.setBottomComponent(getChartPanel3());
				if(!volumen && !estocastico){
//					split_graficas=null;
//					split_graficas=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
					split_graficas.remove(2);
				}
			}
	
	}
	private Component getPanelIzquierdaArriba() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelArriba = new JPanel();
		panelArriba.add(new JLabel("Operaciones pendientes"));
		opPend = new JRadioButton();
		cartera = new JRadioButton();
		opPend.setAction(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				opPend_actionPerformed(e);
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
		tablaArribaIzq.getColumnModel().getColumn(0).setCellEditor(new EditorOpPendientes(){
			public void borraOperacion(int fila) {
				try {
					cliente.cancelarOperacion(((Integer)modeloOpPendientes.getValueAt(fila, 1)).intValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		tablaArribaIzq.getColumn(tablaArribaIzq.getColumnName(0)).setMaxWidth(25);
		panel.add(new JScrollPane(tablaArribaIzq));		
		return panel;
	}

	private Component getPanelDerechaArriba() {
		MDateEntryField entryField = new MDateEntryField();
        MDateSelectorConstraints c = new MDefaultPullDownConstraints();
        //c.firstDay = Calendar.MONDAY;
        entryField.setConstraints(c);
        entryField.addMFieldListener(new MFieldListener(){
            public void fieldEntered(FocusEvent e)
            {
                System.out.println("Entered");
            }
            public void fieldExited(FocusEvent e)
            {
                System.out.println("Exited");
            }
        });

        JFrame frame2=new JFrame();
		frame2.getContentPane().add(entryField);



		//MenuShortcut s=new MenuShortcut(37);
		JMenuItem vol=new JMenuItem("Volumen");
		JMenuItem esto=new JMenuItem("Estocastico");
		JMenu indicadores= new JMenu("Indicadores");
		vol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				pinta_actionPerformed(event);
			}
		    }				    
		    );
		esto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				pinta_actionPerformed(event);
			}
		    }				    
		    );
		JMenuItem help= new JMenu("Ayuda");
		indicadores.add(vol);
		indicadores.add(esto);
		JMenuBar barra=new JMenuBar();
		barra.add(indicadores);
		barra.add(help);	
		split_graficas=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		graficas.put("normal",getChartPanel());
		
		split_graficas.add(getChartPanel());
		int tam=split_graficas.size().height-split_graficas.size().height/6;
		split_graficas.setDividerLocation(200);
		FakeInternalFrame frame = new FakeInternalFrame("Precio Accion", split_graficas);
		frame.setPreferredSize(new Dimension(500, 450));
		frame.setMenuBar(barra);
		return frame;
	}
	
private JPanel getChartPanel2(){
		
		try {
			MDateEntryField entryField = new MDateEntryField();

	        MDateSelectorConstraints c = new MDefaultPullDownConstraints();
	        entryField.setConstraints(c);
	        entryField.addMFieldListener(new MFieldListener(){
	            public void fieldEntered(FocusEvent e)
	            {
	                //System.out.println("Entered");
	            }
	            public void fieldExited(FocusEvent e)
	            {
	                //System.out.println("Exited");
	            }
	        });	        
	        Date fechaIni;
			JOptionPane.showMessageDialog(this,entryField,"Fecha de Inicial",JOptionPane.INFORMATION_MESSAGE);
			fechaIni = entryField.getValue();
			JOptionPane.showMessageDialog(this,entryField,"Fecha de Final",JOptionPane.INFORMATION_MESSAGE);
	        Date fechaFin=entryField.getValue();
	        DateAxis ejeX = new DateAxis();
			Calendar c1 = Calendar.getInstance();
			c1.setTime(fechaIni);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(fechaFin);
			ejeX.setMaximumDate(c2.getTime());
			ejeX.setMinimumDate(c1.getTime());
			System.out.println(fechaIni.toString());
			System.out.println(fechaFin.toString());
			DefaultCategoryDataset auxds = new DefaultCategoryDataset();
			int j=0;
			String fechaAux;
			//while (j<modeloPrecios.empresaSeleccionada().size()){
				//DefaultCategoryDataset auxds = new DefaultCategoryDataset();
				//for (int i=0;i<200;i++){
			    while(c1.compareTo(c2)!=0){
					fechaAux=c1.get(Calendar.DAY_OF_MONTH)+"/"+(c1.get(Calendar.MONTH)+1)+"/"+c1.get(Calendar.YEAR);
					//String fechaAux2=c2.get(Calendar.DAY_OF_MONTH)+"/"+(c2.get(Calendar.MONTH)+1)+"/"+c2.get(Calendar.YEAR);
					DatoHistorico aux=ParserInfoLocal.getDatoHistorico(modeloPrecios.empresaSeleccionada().toLowerCase(),fechaAux);
					System.err.println("Fecha: "+fechaAux+ " Volumen: "+aux.getVolumen());
					auxds.addValue(aux.getVolumen(),"Volumen",fechaAux.split("/")[0]+"/"+fechaAux.split("/")[1]);
					//modeloPrecios.insertaVolumen(modeloPrecios.empresaSeleccionada(),aux.getVolumen());
					c1.add(Calendar.DATE, 1);
				}
			//}
			JFreeChart chart = ChartFactory.createBarChart(modeloPrecios.empresaSeleccionada(), 
		            "Dias", "Volumen", auxds, PlotOrientation.VERTICAL, 
		            true, true, true); 
			chart.clearSubtitles();
			chart.getSubtitles().clear();
			ChartPanel panel = new ChartPanel(chart);
			return panel;
		} catch (ParseException e1) {
			e1.printStackTrace();
			return null;
		}
        
	}
	private JPanel getChartPanel3(){
//		DateAxis ejeX = new DateAxis();
//		Calendar c1 = Calendar.getInstance();
//		c1.set(Calendar.AM_PM, Calendar.AM);
//		c1.set(Calendar.HOUR, 8);
//		c1.set(Calendar.MINUTE, 30);
//		Calendar c2 = Calendar.getInstance();
//		c2.set(Calendar.AM_PM, Calendar.PM);
//		c2.set(Calendar.HOUR, 5);
//		c2.set(Calendar.MINUTE, 30);
//		ejeX.setMaximumDate(c2.getTime());
//		ejeX.setMinimumDate(c1.getTime());
//		
//		NumberAxis ejeY = new NumberAxis();
//		ejeY.setRange(new Range(0, 540));
//		
//		chartPlot = new XYPlot(
//				modeloPrecios.getPreciosSeleccionados(), 
//				ejeX, ejeY,new XYLineAndShapeRenderer(true, false));
//		JFreeChart chart = new JFreeChart(chartPlot);
//		ChartPanel panel = new ChartPanel(chart);
		JPanel panel = new JPanel();
		panel.add(new JButton("Pollo"));
		return panel;
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
	class OyenteMenu implements ActionListener
	{

	public void actionPerformed (ActionEvent e)
	{
	System.out.println(e.getActionCommand()); 	
	if (e.getActionCommand().compareTo("Volumen")==0) volumen=true;

	}

	}

	private boolean seleccionado = false;
	private Component getPanelIzquierdaAbajo() {
		JPanel panel = new JPanel(new GridLayout(5, 1));
		JPanel p = new JPanel();
		p.add(new JLabel("                Empresa "));
		empresaSeleccionada = new JComboBox(modeloPrecios.getModeloComboBox());
		empresaSeleccionada.setSize(new Dimension(90, empresaSeleccionada.getPreferredSize().height));
		
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

	
	public static void main(String[] args){
		ArrayList lEmpresas = new ArrayList();
		lEmpresas.add("Empresa1");
		lEmpresas.add("Empresa2");
		lEmpresas.add("Empresa3");
		ModeloOpPendientes mOpPendientes = new ModeloOpPendientes();
		ModeloCartera mCartera = new ModeloCartera();
		ModeloPrecioAccionesGrafico mPrecios = new ModeloPrecioAccionesGrafico(lEmpresas,new Date(105,7,26));
		//Cliente p=new Cliente("Pako");
		MainFrameCliente gui = new MainFrameCliente(mCartera, mOpPendientes, mPrecios);
		gui.init();
		gui.pack();
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		mOpPendientes.insertarOperacion(new Operacion("ID1", Operacion.COMPRA, 100, "Empresa1", 10.0f), 10);
		mOpPendientes.insertarOperacion(new Operacion("ID1", Operacion.VENTA, 100, "Empresa2", 10.0f), 50);
		
		mCartera.insertarAcciones("Empresa1", 100);
		mCartera.insertarAcciones("Empresa2", 200);
		
		int i=0;
		int j=270;
		while(true){
			i++;
			j+=(Math.random()>0.5?1:-1);
			if(i<540){
				mPrecios.insertaValor("Empresa1", i);
				mPrecios.insertaValor("Empresa2", 540-i);
				mPrecios.insertaValor("Empresa3", j);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
