package stoxtreme.servidor.superusuario.GUI;

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

public class MainFrameAdmin extends JFrame{
	private JSplitPane panelPrincipal;
	private JSplitPane panelSecundario;
	private FakeInternalFrame panelDerecha;
	private JTable tablaDerecha;
	private FakeInternalFrame panelIzqArriba;
	private JList listaIzqArriba;
	private FakeInternalFrame panelIzqAbajo;
	private JList listaIzqAbajo;
	
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
	public void init(
			ModeloTablaOperaciones modeloOperaciones, 
			ModeloListaUsuariosConectados modeloUsuarios, 
			ModeloTablaPrecioAcciones modeloPrecios,
			ModeloTablaVariables modeloVariables,
			ModeloTablaEventos modeloEventos){
		JTabbedPane tabbed = new JTabbedPane();
		tabbed.insertTab("Control", null, getPanelControl(modeloOperaciones, modeloUsuarios, modeloPrecios), "Control de los usuarios", 0);
		tabbed.insertTab("Eventos", null, getPanelEventos(modeloEventos, modeloVariables), "Control de los eventos", 1);
		getContentPane().add(tabbed);
	}
	
	public Component getPanelControl(ModeloTablaOperaciones modeloOperaciones, 
			ModeloListaUsuariosConectados modeloUsuarios, 
			ModeloTablaPrecioAcciones modeloPrecios){
		
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
	
	public Component getPanelEventos(
			ModeloTablaEventos modeloEventos,
			ModeloTablaVariables modeloVariables){
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
	public static void main(String[] args){
		MainFrameAdmin mf = new MainFrameAdmin();
		ModeloTablaOperaciones mo = new ModeloTablaOperaciones();
		ModeloListaUsuariosConectados lusr = new ModeloListaUsuariosConectados();
		ModeloTablaPrecioAcciones pacc = new ModeloTablaPrecioAcciones();
		ModeloTablaVariables vars = new ModeloTablaVariables();
		ModeloTablaEventos evn = new ModeloTablaEventos();
		
		mf.init(mo, lusr, pacc, vars, evn);
		mf.pack();
		mf.setVisible(true);
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		vars.insertarVariable("Tiempo", new Integer(10));
		vars.insertarVariable("Espacio", new Float(100.432f));
		vars.insertarVariable("VarBool", new Boolean(true));
		vars.cambiaVariable("Tiempo", new Integer(100));
		
		evn.insertarEvento(new Evento("Condicion1", "Accion1"), true);
		evn.insertarEvento(new Evento("Condicion2", "Accion2"), false);
		evn.setEventoActivo(0, false);
		evn.setEventoActivo(1, true);
		
		int i = 0;
		//*/while(true){
			mo.insertarOperacion(new Operacion("Cliente"+i, "Empresa"+i, 
					(int)(Math.random()*200), (float)Math.random(), 
					(Math.random()>0.5)?Operacion.COMPRA:Operacion.VENTA));

			lusr.registraUsuario("Cliente"+i);
			lusr.setEstadoUsuario("Cliente"+i, (Math.random()>0.5)?true:false);
			
			pacc.insertarEmpresa("Empresa"+i);
			pacc.cambiaPrecioAccion("Empresa"+i, (float)Math.random());
			pacc.cambiaColor("Empresa"+i, (Math.random()>0.5)?Color.GREEN:Color.RED);
			i++;
		/*	
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}*/
	}
}
