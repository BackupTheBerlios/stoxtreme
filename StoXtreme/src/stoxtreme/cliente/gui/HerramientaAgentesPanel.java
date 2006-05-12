package stoxtreme.cliente.gui;

import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import stoxtreme.cliente.Cliente;
import stoxtreme.herramienta_agentes.ConsolaAgentes;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.interfaz_remota.Operacion;


public class HerramientaAgentesPanel extends JPanel implements ConsolaAgentes{
	private JSplitPane panelPrincipal;
	private JScrollPane panelIzquierdoArriba;
	private JSplitPane panelDerecho;
	private JTable tablaAgentes;
	
	private StyledDocument textoNotificacion;
	private StyledDocument textoConsola;
	private HerramientaAgentesTableModel modeloTabla;
	
	private JButton botonIniciarPararSistema;
	private JButton botonEliminar;
	private JButton botonEditar;
	private JButton botonInsertar;
	private Cliente cliente;
	
	private boolean parado = true;
	
	public HerramientaAgentesPanel() {
		try{
			init();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void init() throws Exception{
		panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				getPanelIzquierdo(), getPanelDerecho());
		add(panelPrincipal);
		
		panelDerecho.setDividerLocation(250);
		tablaAgentes.getColumnModel().getColumn(4).setPreferredWidth(30);
		tablaAgentes.getColumnModel().getColumn(0).setMaxWidth(7);
	}
	
	public Component getPanelIzquierdo(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getPanelIzquierdoArriba(), BorderLayout.CENTER);
		panel.add(getPanelIzquierdoAbajo(), BorderLayout.SOUTH);
		FakeInternalFrame frame = new FakeInternalFrame("Agentes en el sistema", panel);
		return frame;
	}
	
	public Component getPanelIzquierdoArriba(){
		modeloTabla = new HerramientaAgentesTableModel();
		tablaAgentes = new JTable(modeloTabla);
		panelIzquierdoArriba = new JScrollPane(tablaAgentes);
		return panelIzquierdoArriba;
	}
	
	private JPanel panelBotonesNormal;
	
	public Component getPanelIzquierdoAbajo(){
		panelBotonesNormal = new JPanel();
		
		botonIniciarPararSistema = new JButton("Iniciar Sistema");
		botonEliminar = new JButton("Eliminar");
		botonEliminar.setEnabled(false);
		botonEditar = new JButton("Editar");
		botonEditar.setEnabled(false);
		botonInsertar = new JButton("Insertar Agentes");
		botonInsertar.setEnabled(false);

		panelBotonesNormal.add(botonIniciarPararSistema);
		panelBotonesNormal.add(botonEliminar);
		panelBotonesNormal.add(botonEditar);
		panelBotonesNormal.add(botonInsertar);
		
		// Setea los botones
		botonEditar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				botonEditar_actionPerformed(e);
			}
		});
		
		botonEliminar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				botonEliminar_actionPerformed(e);
			}
		});
		
		botonInsertar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				botonInsertar_actionPerformed(e);
			}
		});
		
		botonIniciarPararSistema.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if("Iniciar Sistema".equals(((JButton)e.getSource()).getText())){
					botonIniciar_actionPerformed(e);
				}
				else{
					botonParar_actionPerformed(e);
				}
			}
		});
		
		return panelBotonesNormal;
	}
	
	public void botonParar_actionPerformed(ActionEvent e){
		cliente.detenerHerramientaAgentes();
		botonIniciarPararSistema.setText("Iniciar Sistema");
		botonEditar.setEnabled(false);
		botonEliminar.setEnabled(false);
		botonInsertar.setEnabled(false);
	}

	private void botonIniciar_actionPerformed(ActionEvent e){
		cliente.iniciarHerramientaAgentes();
		botonIniciarPararSistema.setText("Parar Sistema");
		botonEditar.setEnabled(true);
		botonEliminar.setEnabled(true);
		botonInsertar.setEnabled(true);
	}
	
	private void botonEliminar_actionPerformed(ActionEvent e){
		
	}
	
	private void botonEditar_actionPerformed(ActionEvent e){
		
	}
	
	private void botonInsertar_actionPerformed(ActionEvent e){
		
	}

	
	
	public void addListaAgentes(ArrayList<Agente> listaAgentes){
		modeloTabla.setAgentes(listaAgentes);
	}
	
	public Component getPanelDerecho(){
		setLayout(new BorderLayout());
		panelDerecho = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				getPanelSuperiorDerecho(),
				getPanelInferiorDerecho());
		return(panelDerecho);
	}
	
	public Component getPanelInferiorDerecho(){
		JTextPane texto = new JTextPane();
		textoConsola = texto.getStyledDocument();
		addStylesToDocument(textoConsola);
		JScrollPane panel = new JScrollPane(texto);
		FakeInternalFrame frame = new FakeInternalFrame("Acciones de los agentes",panel);
		return frame;
	}
	
	public Component getPanelSuperiorDerecho(){
		JTextPane texto = new JTextPane();
		textoNotificacion = texto.getStyledDocument();
		addStylesToDocument(textoNotificacion);
		JScrollPane panel = new JScrollPane(texto);
		FakeInternalFrame frame = new FakeInternalFrame("Notificaciones de los agentes",panel);
		return frame;
	}
	
	private static final String ESTILO_IDAGENTE = "bold";
	private static final String ESTILO_NOTIFICACION = "red";
	private static final String ESTILO_ACCION = "regular";
	private static final String nl = "\n";

	public synchronized void insertarAccion(String idAgente, String accion){
		try {
			textoConsola.insertString(
					textoConsola.getLength(),
					idAgente+": ",
					textoConsola.getStyle(ESTILO_IDAGENTE)
			);
			textoConsola.insertString(
					textoConsola.getLength(),
					accion+nl,
					textoConsola.getStyle(ESTILO_ACCION)
			);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void insertarNotificacion(String idAgente, String notif){
		try {
			textoNotificacion.insertString(
					textoNotificacion.getLength(),
					idAgente+": ",
					textoNotificacion.getStyle(ESTILO_IDAGENTE)
			);
			textoNotificacion.insertString(
					textoNotificacion.getLength(),
					notif+nl,
					textoNotificacion.getStyle(ESTILO_NOTIFICACION)
			);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = doc.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle("large", regular);
        StyleConstants.setFontSize(s, 16);
        
        s = doc.addStyle("red", regular);
        StyleConstants.setForeground(s, Color.red);
    }
	
	
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame();
		HerramientaAgentesPanel hp = new HerramientaAgentesPanel();
		frame.add(hp);
		hp.insertarNotificacion("Agente", "Notif");
		hp.insertarAccion("Agente", "Accion");
		frame.setSize(new Dimension(800,600));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
