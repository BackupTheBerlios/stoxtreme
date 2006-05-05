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


public abstract class HerramientaAgentesPanel extends JPanel implements ConsolaAgentes{
	private JSplitPane panelPrincipal;
	private JScrollPane panelIzquierdo;
	private JSplitPane panelDerecho;
	private PruebaListModel modeloLista;
	private JList listaOpConfirmar;
	private StyledDocument textoConsola;
	private JTable tablaAgentes;
	private HerramientaAgentesTableModel modeloTabla;
	
	private JButton botonIniciarParar;
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
		panelPrincipal.setSize(new Dimension(800,800));
		panelPrincipal.setDividerLocation(400);
		
	}
	
	public Component getPanelIzquierdo(){
		JPanel panel = new JPanel(new BorderLayout());
		FakeInternalFrame frame = new FakeInternalFrame("Agentes en el sistema", panel);
		panel.add(getPanelIzquierdoArriba(), BorderLayout.CENTER);
		panel.add(getPanelIzquierdoAbajo(), BorderLayout.SOUTH);
		return frame;
	}
	
	public Component getPanelIzquierdoArriba(){
		modeloTabla = new HerramientaAgentesTableModel(new ArrayList<Agente>());
		tablaAgentes = new JTable(modeloTabla);
		panelIzquierdo = new JScrollPane(tablaAgentes);
		return panelIzquierdo;
	}
	
	private JPanel panelBotonesNormal;
	
	public Component getPanelIzquierdoAbajo(){
		panelBotonesNormal = new JPanel();
		
		botonIniciarParar = new JButton("Iniciar");
		botonEliminar = new JButton("Eliminar");
		botonEliminar.setEnabled(false);
		botonEditar = new JButton("Editar");
		botonEditar.setEnabled(false);
		botonInsertar = new JButton("Insertar Agentes");
		botonInsertar.setEnabled(false);

		panelBotonesNormal.add(botonIniciarParar);
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
		
		botonIniciarParar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if("Iniciar".equals(((JButton)e.getSource()).getText())){
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
		botonIniciarParar.setText("Iniciar");
		botonEditar.setEnabled(false);
		botonEliminar.setEnabled(false);
		botonInsertar.setEnabled(false);
	}

	private void botonIniciar_actionPerformed(ActionEvent e){
		cliente.iniciarHerramientaAgentes();
		botonIniciarParar.setText("Parar");
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
	
	public void insertarOperacion(Operacion op){
		modeloLista.addElement(op);
	}
	
	public void cancelarOperacion(String id, int idOp){
		modeloLista.addElement(new Cancelacion(id, idOp));
	}
	
	private class Cancelacion{
		private String id;
		private int idOp;
		public Cancelacion(String id, int idOperacion){
			this.id = id;
			this.idOp = idOperacion;
		}
		public int getIdOp(){
			return this.idOp;
		}
		public String getID(){
			return this.id;
		}
		public String toString(){
			return "Cancelacion operacion: "+Integer.toString(idOp);
		}
	}
	private void doble_click_lista(){
		int index = listaOpConfirmar.getSelectedIndex();
		Object o = modeloLista.getElementAt(index);
		modeloLista.remove(index);
		
		if(o instanceof Operacion){
			String id = ((Operacion)o).getIdEmisor();
			//int idOp = ((Operacion)o).getIDOp();
			int cantidad = ((Operacion)o).getCantidad();
			double precio = ((Operacion)o).getPrecio();
			//modeloLista.quitaCancelacion(idOp);
			//nOperacion(id, idOp, cantidad, precio);
		}
		else{
			String id = ((Cancelacion)o).getID();
			int idOp = ((Cancelacion)o).getIdOp();
			modeloLista.quitaOperacion(idOp);
			nCancelacion(id, idOp);
		}
	}
	protected abstract void nOperacion(String id, int idOp, int cantidad, double precio);
	protected abstract void nCancelacion(String id, int idOp);
	
	public Component getPanelSuperiorDerecho(){
		modeloLista = new PruebaListModel();
		listaOpConfirmar = new JList(modeloLista);
		
		listaOpConfirmar.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(e.getClickCount()>=2){
					doble_click_lista();
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(listaOpConfirmar);
		FakeInternalFrame frame = new FakeInternalFrame("Operaciones pendientes", scrollPane);
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
			textoConsola.insertString(
					textoConsola.getLength(),
					idAgente+": ",
					textoConsola.getStyle(ESTILO_IDAGENTE)
			);
			textoConsola.insertString(
					textoConsola.getLength(),
					notif+nl,
					textoConsola.getStyle(ESTILO_NOTIFICACION)
			);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public Component getPanelInferiorDerecho(){
		JTextPane texto = new JTextPane();
		textoConsola = texto.getStyledDocument();
		addStylesToDocument(textoConsola);
		JScrollPane panel = new JScrollPane(texto);
		FakeInternalFrame frame = new FakeInternalFrame("Acciones de los agentes",panel);
		return frame;
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
	
	
	private class PruebaListModel extends AbstractListModel{
		private ArrayList<Object> lista = new ArrayList<Object>();
		
		public void addElement(Object o){
			lista.add(o);
			SwingUtilities.invokeLater(new IL(o, lista.size()-1, 2));
		}
		
		public void quitaOperacion(int idOp) {
			int i=0;
			while(	
					i<lista.size() &&
					((lista.get(i) instanceof Cancelacion) 
							//||((Operacion)lista.get(i)).getIDOp() != idOp
					)
				)i++;
			
			if(i<lista.size())
				remove(i);
		}

		public void quitaCancelacion(int idOp) {
			int i=0;
			while(	
					i<lista.size() &&
					((lista.get(i) instanceof Operacion) 
							||((Cancelacion)lista.get(i)).getIdOp() != idOp)
				)i++;
			
			if(i<lista.size())
				remove(i);
		}

		public void remove(int index){
			Object o = lista.remove(index);
			SwingUtilities.invokeLater(new IL(o, index, 1));
		}
		
		private class IL implements Runnable{
			Object o;
			int index;
			int tipo;
			
			public IL(Object o, int index, int tipo){
				this.o = o;
				this.index = index;
				this.tipo = tipo;
			}
			public void run(){
				if(tipo == 1)
					fireIntervalRemoved(o, index, index);
				else
					fireIntervalAdded(o, index, index);
			}
		}
		public Object getElementAt(int index) {
			return lista.get(index);
		}
		
		public int getSize() {
			return lista.size();
		}
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		HerramientaAgentesPanel panel = new HerramientaAgentesPanel(){
			protected void nOperacion(String id, int idOp, int cantidad, double precio) {
				System.out.println("hola");
			}
			protected void nCancelacion(String id, int idOp) {
				System.out.println("Adios");
			}
		};
		frame.add(panel);
		frame.setSize(new Dimension(800,600));
		frame.setVisible(true);
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
