package prueba;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JFrame{
	JDesktopPane mainPane;
	JMenuBar menuPrincipal;
	JToolBar toolbarPrincipal;
	
	public MainGUI(){
		super("Stock Xtreme Server");
		mainPane = new JDesktopPane();
		//setContentPane(mainPane);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainPane, BorderLayout.CENTER);
		
		PanelCotizaciones panelCotizaciones = new PanelCotizaciones();
		getContentPane().add(panelCotizaciones, BorderLayout.SOUTH);
		
		toolbarPrincipal = new JToolBar();
		toolbarPrincipal.setPreferredSize(new Dimension(0, 26));
		toolbarPrincipal.setFloatable(false);
		JButton b1 = new JButton(new ImageIcon("iconoEncima.png"));
		//b1.setPreferredSize(new Dimension(26,26));
		//b1.setSize(new Dimension(26,26));
		b1.setMinimumSize(new Dimension(26,26));
		b1.setMaximumSize(new Dimension(26,26));
		toolbarPrincipal.add(b1);
		JButton b2 = new JButton(new ImageIcon("iconoPulsado.png"));
		//b2.setPreferredSize(new Dimension(26,26));
		//b2.setSize(new Dimension(26,26));
		b2.setMinimumSize(new Dimension(26,26));
		b2.setMaximumSize(new Dimension(26,26));
		toolbarPrincipal.add(b2);
		getContentPane().add(toolbarPrincipal, BorderLayout.NORTH);
		
		mainPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		
		VentanaPrueba1 f1 = 	new VentanaPrueba1();
		VentanaPrueba2 f2 = 	new VentanaPrueba2();
		VentanaPrueba3 f3 = new VentanaPrueba3();
		
		f1.jbInit();
		f1.setLocation(0,0);
		f2.jbInit();
		f2.setLocation(0, 300);
		f3.jbInit();
		f3.setLocation(450, 0);
		
		mainPane.add(f1);
		mainPane.add(f2);
		mainPane.add(f3);
		
		initialize();
	}
	
	public void initialize(){
		Dimension d = new Dimension(800, 700);
		this.setSize(d);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setLocation(30, 30);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuPrincipal = new JMenuBar();
		
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
		
		menuPrincipal.add(archivo);
		JMenu editar = new JMenu("Editar");
		menuPrincipal.add(editar);
		JMenu ver = new JMenu("Ver");
		menuPrincipal.add(ver);
		JMenu ir = new JMenu("Ir");
		menuPrincipal.add(ir);
		JMenu herramientas = new JMenu("Herramientas");
		menuPrincipal.add(herramientas);
		JMenu ayuda = new JMenu("Ayuda");
		menuPrincipal.add(ayuda);
		this.setJMenuBar(menuPrincipal);
	}
	
	
	public static void main(String[] args) {
		MainGUI m = new MainGUI();
		m.pack();
		m.setVisible(true);
	}

}
