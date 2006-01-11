package servidor.superusuario;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class SuperusuarioGUI extends JFrame{
	/**
	 * @uml.property  name="mainPane"
	 * @uml.associationEnd  
	 */
	private JDesktopPane mainPane;
	/**
	 * @uml.property  name="menuPrincipal"
	 * @uml.associationEnd  
	 */
	private JMenuBar menuPrincipal;
	/**
	 * @uml.property  name="toolbarPrincipal"
	 * @uml.associationEnd  
	 */
	private JToolBar toolbarPrincipal;
	/**
	 * @uml.property  name="listaVentanas"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="servidor.superusuario.VentanaGUI"
	 */
	private ArrayList listaVentanas;
	
	public SuperusuarioGUI(){
		super("Stock eXtreme Server");
		listaVentanas = new ArrayList();
	}
	
	public void addVentana(VentanaGUI ventana){
		listaVentanas.add(ventana);
	}
	
	// Inicia la gui
	public void init(){
		Dimension d = new Dimension(800, 700);
		this.setSize(d);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setLocation(30, 30);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPane = new JDesktopPane();
		mainPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainPane, BorderLayout.CENTER);
		
		toolbarPrincipal = new JToolBar();
		toolbarPrincipal.setFloatable(false);
		getContentPane().add(toolbarPrincipal, BorderLayout.NORTH);
		
		menuPrincipal = creaMenu();
		this.setJMenuBar(menuPrincipal);
		
		// Ahora pone la lista de ventanas
		Iterator it = listaVentanas.iterator();
		while (it.hasNext()){
			VentanaGUI v = (VentanaGUI)it.next();
			v.init();
		}
		this.setVisible(true);
	}
	
	private JMenuBar creaMenu(){
		JMenuBar barraMenu = new JMenuBar();
		JMenu archivo = new JMenu("Archivo");
		JMenu nuevo = new JMenu("Nuevo");
		nuevo.add(new JMenuItem("Proyecto"));
		nuevo.add(new JMenuItem("Archivo"));
		nuevo.add(new JMenuItem("Cosa"));
		archivo.add(nuevo);
		archivo.add(new JMenuItem("Abrir"));
		archivo.add(new JMenuItem("Guardar"));
		archivo.add(new JMenuItem("Guardar como..."));
		archivo.add(new JMenuItem("Cerrar"));
		
		barraMenu.add(archivo);
		JMenu editar = new JMenu("Editar");
		barraMenu.add(editar);
		JMenu ver = new JMenu("Ver");
		barraMenu.add(ver);
		JMenu ir = new JMenu("Ir");
		barraMenu.add(ir);
		JMenu herramientas = new JMenu("Herramientas");
		barraMenu.add(herramientas);
		JMenu ayuda = new JMenu("Ayuda");
		barraMenu.add(ayuda);
		
		return barraMenu;
	}
}
