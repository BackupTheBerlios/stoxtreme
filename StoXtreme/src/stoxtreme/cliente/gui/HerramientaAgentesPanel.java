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
import stoxtreme.herramienta_agentes.ParametrosAgentes;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.interfaz_remota.Operacion;


public class HerramientaAgentesPanel extends JPanel implements ConsolaAgentes{
	private JSplitPane panelPrincipal;
	private JScrollPane panelIzquierdoArriba;
	private JSplitPane panelDerecho;
	private JTable tablaAgentes;
	
	private StyledDocument textoNotificacion;
	private StyledDocument textoConsola;
	protected HerramientaAgentesTableModel modeloTabla;
	
	private JButton botonIniciarPararSistema;
	private JButton botonEliminar;
	private JButton botonEditar;
	private JButton botonReiniciar;
	private Cliente cliente;
	
	private boolean parado = true;
	private JFrame frame;
	
	private JScrollPane scrollTextoConsola;
	private JScrollPane scrollTextoNotificacion;
	
	public HerramientaAgentesPanel() {
		try{
			init();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setFrame(JFrame frame){
		this.frame = frame;
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
		TableSorter sorter = new TableSorter(modeloTabla);
		tablaAgentes = new JTable(sorter);
		sorter.setTableHeader(tablaAgentes.getTableHeader());
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
		botonReiniciar = new JButton("Reiniciar Sistema");
		botonReiniciar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				boton_reiniciarActionPerformed();
			}
		});
		panelBotonesNormal.add(botonIniciarPararSistema);
		panelBotonesNormal.add(botonEliminar);
		panelBotonesNormal.add(botonEditar);
		panelBotonesNormal.add(botonReiniciar);
		
		// Setea los botones
		botonEditar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				botonEditar_actionPerformed();
			}
		});
		
		botonEliminar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				botonEliminar_actionPerformed();
			}
		});
		
		botonIniciarPararSistema.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if("Iniciar Sistema".equals(((JButton)e.getSource()).getText())){
					botonIniciar_actionPerformed();
				}
				else if("Reanudar Sistema".equals(((JButton)e.getSource()).getText())){
					botonReanudar_actionPerformed();
				}
				else{
					botonParar_actionPerformed();
				}
			}
		});
		
		return panelBotonesNormal;
	}
	
	private void boton_reiniciarActionPerformed(){
		DialogoParametrosAgentes dialogo = new DialogoParametrosAgentes(frame);
		dialogo.setVisible(true);
		if(dialogo.isAceptado()){
			ParametrosAgentes parametros = new ParametrosAgentes();
			parametros.set(ParametrosAgentes.Parametro.NUM_AGENTES, dialogo.getNAgentes());
			parametros.set(ParametrosAgentes.Parametro.TIEMPO_ESPERA, dialogo.getTEjecucion());
			parametros.set(ParametrosAgentes.Parametro.TCICLO, dialogo.getTCiclo());
			cliente.reiniciarHerramientaAgentes(parametros);
			botonEditar.setEnabled(true);
			botonEliminar.setEnabled(true);
		}
	}
	
	private void botonParar_actionPerformed(){
		cliente.detenerHerramientaAgentes();
		botonIniciarPararSistema.setText("Reanudar Sistema");
	}

	private void botonIniciar_actionPerformed(){
		DialogoParametrosAgentes dialogo = new DialogoParametrosAgentes(frame);
		dialogo.setVisible(true);
		if(dialogo.isAceptado()){
			ParametrosAgentes parametros = new ParametrosAgentes();
			parametros.set(ParametrosAgentes.Parametro.NUM_AGENTES, dialogo.getNAgentes());
			parametros.set(ParametrosAgentes.Parametro.TIEMPO_ESPERA, dialogo.getTEjecucion());
			parametros.set(ParametrosAgentes.Parametro.TCICLO, dialogo.getTCiclo());
			cliente.iniciarHerramientaAgentes(parametros);
			botonIniciarPararSistema.setText("Parar Sistema");
			botonEditar.setEnabled(true);
			botonEliminar.setEnabled(true);
		}
	}
	
	private void botonReanudar_actionPerformed() {
		cliente.reanudarHerramientaAgentes();
		botonIniciarPararSistema.setText("Parar Sistema");
	}
	
	private void botonEliminar_actionPerformed(){
		// Cogemos los agentes seleccionados y los eliminamos
		// de la bolsa
		ArrayList<Agente> selec = modeloTabla.getAgentesSeleccionados();
		for (Agente agente : selec) {
			agente.abandonarModelo();
		}
		modeloTabla.clearSeleccionados();
	}
	
	private void botonEditar_actionPerformed(){
		int row = tablaAgentes.getSelectedRow();
		if(row != -1){
			Agente agente = modeloTabla.getAgente(row);
			DialogoMuestraAgente dialogo = new DialogoMuestraAgente(agente, frame);
			dialogo.setVisible(true);
		}
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
		scrollTextoConsola = new JScrollPane(texto);
		FakeInternalFrame frame = new FakeInternalFrame("Acciones de los agentes",scrollTextoConsola);
		return frame;
	}
	
	
	public Component getPanelSuperiorDerecho(){
		JTextPane texto = new JTextPane();
		textoNotificacion = texto.getStyledDocument();
		addStylesToDocument(textoNotificacion);
		scrollTextoNotificacion = new JScrollPane(texto);
		FakeInternalFrame frame = new FakeInternalFrame("Notificaciones de los agentes",scrollTextoNotificacion);
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
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					scrollAbajoConsola();
				}
			});
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	private void scrollAbajoConsola(){
		scrollTextoConsola.getVerticalScrollBar().setValue(scrollTextoConsola.getVerticalScrollBar().getMaximum());
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
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					scrollAbajoNotificacion();
				}
			});
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	private void scrollAbajoNotificacion(){
		scrollTextoNotificacion.getVerticalScrollBar().setValue(scrollTextoConsola.getVerticalScrollBar().getMaximum());
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
		
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void limpiarGUI(){
		try {
			textoConsola.remove(0, textoConsola.getLength()-1);
			textoNotificacion.remove(0, textoConsola.getLength()-1);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
