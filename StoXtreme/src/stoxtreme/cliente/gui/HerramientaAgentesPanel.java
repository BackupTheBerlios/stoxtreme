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

/**
 *  Configura la interfaz de los Agentes asociados a un Cliente
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class HerramientaAgentesPanel extends JPanel implements ConsolaAgentes {
	/**
	 *  Tabla de Agentes
	 */
	protected HerramientaAgentesTableModel modeloTabla;

	FakeInternalFrame frameAgentes;
	private JSplitPane panelPrincipal;
	private JScrollPane panelIzquierdoArriba;
	private JSplitPane panelDerecho;
	private JTable tablaAgentes;

	private StyledDocument textoNotificacion;
	private StyledDocument textoConsola;

	private JButton botonIniciarPararSistema;
	private JButton botonEliminar;
	private JButton botonEditar;
	private JButton botonReiniciar;
	private Cliente cliente;

	private JFrame frame;

	private JScrollPane scrollTextoConsola;
	private JScrollPane scrollTextoNotificacion;

	private JPanel panelBotonesNormal;

	private static final String ESTILO_IDAGENTE = "bold";
	private static final String ESTILO_NOTIFICACION = "red";
	private static final String ESTILO_ACCION = "regular";
	private static final String nl = "\n";


	/**
	 *  Constructor de HerramientaAgentesPanel
	 */
	public HerramientaAgentesPanel() {
		try {
			init();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 *  Asigna una nueva Frame
	 *
	 *@param  frame  Nuevo valor de Frame
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}


	/**
	 *  Asigna un Cliente
	 *
	 *@param  cliente  Nuevo valor de Cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	/**
	 *  Obtiene el PanelIzquierdo
	 *
	 *@return    Valor de PanelIzquierdo
	 */
	public Component getPanelIzquierdo() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getPanelIzquierdoArriba(), BorderLayout.CENTER);
		panel.add(getPanelIzquierdoAbajo(), BorderLayout.SOUTH);
		frameAgentes = new FakeInternalFrame("Agentes en el sistema. Ciclo: 0", panel);
		return frameAgentes;
	}


	/**
	 *  Obtiene el PanelIzquierdoArriba
	 *
	 *@return    Valor de PanelIzquierdoArriba
	 */
	public Component getPanelIzquierdoArriba() {
		modeloTabla = new HerramientaAgentesTableModel();
		TableSorter sorter = new TableSorter(modeloTabla);
		tablaAgentes = new JTable(sorter);
		sorter.setTableHeader(tablaAgentes.getTableHeader());
		panelIzquierdoArriba = new JScrollPane(tablaAgentes);
		return panelIzquierdoArriba;
	}


	/**
	 *  Obtiene el PanelIzquierdoAbajo
	 *
	 *@return    Valor de PanelIzquierdoAbajo
	 */
	public Component getPanelIzquierdoAbajo() {
		panelBotonesNormal = new JPanel();

		botonIniciarPararSistema = new JButton("Iniciar Sistema");
		botonEliminar = new JButton("Eliminar");
		botonEliminar.setEnabled(false);
		botonEditar = new JButton("Editar");
		botonEditar.setEnabled(false);
		botonReiniciar = new JButton("Reiniciar Sistema");
		botonReiniciar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							boton_reiniciarActionPerformed();
						}
					});
		panelBotonesNormal.add(botonIniciarPararSistema);
		panelBotonesNormal.add(botonEliminar);
		panelBotonesNormal.add(botonEditar);
		panelBotonesNormal.add(botonReiniciar);

		// Setea los botones
		botonEditar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							botonEditar_actionPerformed();
						}
					});

		botonEliminar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							botonEliminar_actionPerformed();
						}
					});

		botonIniciarPararSistema.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if ("Iniciar Sistema".equals(((JButton) e.getSource()).getText())) {
								botonIniciar_actionPerformed();
							}
							else if ("Reanudar Sistema".equals(((JButton) e.getSource()).getText())) {
								botonReanudar_actionPerformed();
							}
							else {
								botonParar_actionPerformed();
							}
						}
					});

		return panelBotonesNormal;
	}


	/**
	 *  Obtiene el PanelDerecho attribute
	 *
	 *@return    Valor de PanelDerecho
	 */
	public Component getPanelDerecho() {
		setLayout(new BorderLayout());
		panelDerecho = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				getPanelSuperiorDerecho(),
				getPanelInferiorDerecho());
		return (panelDerecho);
	}


	/**
	 *  Obtiene el PanelInferiorDerecho
	 *
	 *@return    Valor de PanelInferiorDerecho
	 */
	public Component getPanelInferiorDerecho() {
		JTextPane texto = new JTextPane();
		textoConsola = texto.getStyledDocument();
		addStylesToDocument(textoConsola);
		scrollTextoConsola = new JScrollPane(texto);
		FakeInternalFrame frame = new FakeInternalFrame("Acciones de los agentes", scrollTextoConsola);
		return frame;
	}


	/**
	 *  Obtiene el PanelSuperiorDerecho
	 *
	 *@return    Valor de PanelSuperiorDerecho
	 */
	public Component getPanelSuperiorDerecho() {
		JTextPane texto = new JTextPane();
		textoNotificacion = texto.getStyledDocument();
		addStylesToDocument(textoNotificacion);
		scrollTextoNotificacion = new JScrollPane(texto);
		FakeInternalFrame frame = new FakeInternalFrame("Notificaciones de los agentes", scrollTextoNotificacion);
		return frame;
	}


	/**
	 *  Inicia la pantalla de agentes
	 *
	 *@exception  Exception  Lanza las excepciones producidas al iniciar
	 */
	public void init() throws Exception {
		panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				getPanelIzquierdo(), getPanelDerecho());
		add(panelPrincipal);

		panelDerecho.setDividerLocation(250);
		tablaAgentes.getColumnModel().getColumn(4).setPreferredWidth(30);
		tablaAgentes.getColumnModel().getColumn(0).setMaxWidth(7);
	}


	/**
	 *  Añade a la tabla de Agentes todos los agentes que han sido almacenados en la lista
	 *
	 *@param  listaAgentes  Lista de Agentes creados
	 */
	public void addListaAgentes(ArrayList<Agente> listaAgentes) {
		modeloTabla.setAgentes(listaAgentes);
	}


	/**
	 *  Inserta una acción que ha producido un agente
	 *
	 *@param  idAgente  Id del agente que ha producido la acción
	 *@param  accion    Acción producida por el agente
	 */
	public synchronized void insertarAccion(String idAgente, String accion) {
		try {
			textoConsola.insertString(
					textoConsola.getLength(),
					idAgente + ": ",
					textoConsola.getStyle(ESTILO_IDAGENTE)
					);
			textoConsola.insertString(
					textoConsola.getLength(),
					accion + nl,
					textoConsola.getStyle(ESTILO_ACCION)
					);

			SwingUtilities.invokeLater(
						new Runnable() {
							public void run() {
								scrollAbajoConsola();
							}
						});

		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}
	}


	/**
	 *  Se realiza una notificación por parte de un agente
	 *
	 *@param  idAgente  Id del agente
	 *@param  notif     Notificación producida
	 */
	public synchronized void insertarNotificacion(String idAgente, String notif) {
		try {
			textoNotificacion.insertString(
					textoNotificacion.getLength(),
					idAgente + ": ",
					textoNotificacion.getStyle(ESTILO_IDAGENTE)
					);
			textoNotificacion.insertString(
					textoNotificacion.getLength(),
					notif + nl,
					textoNotificacion.getStyle(ESTILO_NOTIFICACION)
					);
			SwingUtilities.invokeLater(
						new Runnable() {
							public void run() {
								scrollAbajoNotificacion();
							}
						});
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}
	}


	/**
	 *  Elimina los campos de la interfaz gráfica
	 */
	public void limpiarGUI() {
		try {
			textoConsola.remove(0, textoConsola.getLength() - 1);
			textoNotificacion.remove(0, textoConsola.getLength() - 1);
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}
	}


	/**
	 *  Incrementa en cada ciclo el tick que ha sido asignado para los agentes
	 *
	 *@param  tick  Contador de ticks
	 */
	public void incrementaTick(int tick) {
		String titulo = "Agentes en el sistema. Ciclo: " + tick;
		frameAgentes.setTitle(titulo);
	}


	/**
	 *  Reinicia la herramienta de agentes cuando el Cliente lo desee
	 */
	private void boton_reiniciarActionPerformed() {
		DialogoParametrosAgentes dialogo = new DialogoParametrosAgentes(frame);
		dialogo.setVisible(true);
		if (dialogo.isAceptado()) {
			ParametrosAgentes parametros = new ParametrosAgentes();
			parametros.set(ParametrosAgentes.Parametro.NUM_AGENTES, dialogo.getNAgentes());
			parametros.set(ParametrosAgentes.Parametro.TIEMPO_ESPERA, dialogo.getTEjecucion());
			parametros.set(ParametrosAgentes.Parametro.TCICLO, dialogo.getTCiclo());
			cliente.reiniciarHerramientaAgentes(parametros);
			botonEditar.setEnabled(true);
			botonEliminar.setEnabled(true);
		}
	}


	/**
	 *  Para las acciones de los Agentes
	 */
	private void botonParar_actionPerformed() {
		cliente.detenerHerramientaAgentes();
		botonIniciarPararSistema.setText("Reanudar Sistema");
	}


	/**
	 *  Inicia la herramienta de agentes
	 */
	private void botonIniciar_actionPerformed() {
		DialogoParametrosAgentes dialogo = new DialogoParametrosAgentes(frame);
		dialogo.setVisible(true);
		if (dialogo.isAceptado()) {
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


	/**
	 *  Permite volver a los agentes a su actividad
	 */
	private void botonReanudar_actionPerformed() {
		cliente.reanudarHerramientaAgentes();
		botonIniciarPararSistema.setText("Parar Sistema");
	}


	/**
	 *  Elimina un agente que el Cliente a seleccionado
	 */
	private void botonEliminar_actionPerformed() {
		// Cogemos los agentes seleccionados y los eliminamos
		// de la bolsa
		ArrayList<Agente> selec = modeloTabla.getAgentesSeleccionados();
		for (Agente agente : selec) {
			agente.abandonarModelo();
		}
		modeloTabla.clearSeleccionados();
	}


	/**
	 *  Editamos los movimientos de un agente en exclusiva
	 */
	private void botonEditar_actionPerformed() {
		int row = tablaAgentes.getSelectedRow();
		if (row != -1) {
			Agente agente = modeloTabla.getAgente(row);
			DialogoMuestraAgente dialogo = new DialogoMuestraAgente(agente, frame);
			dialogo.setVisible(true);
		}
	}


	/**
	 *  Desplazo la barra segun se incremente el número de agentes
	 */
	private void scrollAbajoConsola() {
		scrollTextoConsola.getVerticalScrollBar().setValue(scrollTextoConsola.getVerticalScrollBar().getMaximum());
	}


	/**
	 *  Realizo desplazamientos en las notificaciones
	 */
	private void scrollAbajoNotificacion() {
		scrollTextoNotificacion.getVerticalScrollBar().setValue(scrollTextoConsola.getVerticalScrollBar().getMaximum());
	}


	/**
	 *  añadimos a lso agentes diferentes tipos de letras
	 *
	 *@param  doc  variable que nos permite cambiar el tipo de letra
	 */
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
}
