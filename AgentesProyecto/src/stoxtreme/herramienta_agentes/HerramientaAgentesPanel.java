package stoxtreme.herramienta_agentes;

import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public abstract class HerramientaAgentesPanel extends JPanel{
	private JSplitPane panelPrincipal;
	private DefaultListModel modeloLista;
	private JList listaOpConfirmar;
	private StyledDocument textoConsola;
	
	public HerramientaAgentesPanel() {
		try{
			init();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void init(){
		setLayout(new BorderLayout());
		panelPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				getPanelSuperior(),
				getPanelInferior());
		add(panelPrincipal);
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
		Object o = modeloLista.elementAt(index);
		if(o instanceof Operacion){
			String id = ((Operacion)o).getIDAgente();
			int idOp = ((Operacion)o).getIDOp();
			int cantidad = ((Operacion)o).getNumeroAcciones();
			double precio = ((Operacion)o).getPrecio();
			nOperacion(id, idOp, cantidad, precio);
		}
		else{
			String id = ((Cancelacion)o).getID();
			int idOp = ((Cancelacion)o).getIdOp();
			nCancelacion(id, idOp);
		}
		modeloLista.remove(index);
	}
	protected abstract void nOperacion(String id, int idOp, int cantidad, double precio);
	protected abstract void nCancelacion(String id, int idOp);
	
	public Component getPanelSuperior(){
		modeloLista = new DefaultListModel();
		listaOpConfirmar = new JList(modeloLista);
		
		listaOpConfirmar.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(e.getClickCount()==2){
					doble_click_lista();
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(listaOpConfirmar);
		return scrollPane;
	}
	
	private static final String ESTILO_IDAGENTE = "bold";
	private static final String ESTILO_NOTIFICACION = "red";
	private static final String ESTILO_ACCION = "regular";
	private static final String nl = "\n";

	public void insertarAccion(String idAgente, String accion){
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
	
	public void insertarNotificacion(String idAgente, String notif){
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
	
	public Component getPanelInferior(){
		JTextPane texto = new JTextPane();
		textoConsola = texto.getStyledDocument();
		addStylesToDocument(textoConsola);
		JScrollPane panel = new JScrollPane(texto);
		return panel;
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
        StyleConstants.setBackground(s, Color.red);
    }
}
