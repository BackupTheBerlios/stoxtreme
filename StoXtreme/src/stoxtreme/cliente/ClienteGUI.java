package stoxtreme.cliente;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.tree.DefaultTreeModel;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class ClienteGUI extends JFrame{
	public ClienteGUI(){
		super();
	}
	
	public void init(){
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPanelSur(), BorderLayout.SOUTH);
		getContentPane().add(getPanelLista(), BorderLayout.CENTER);
	}
	
	private JPanel getPanelSur(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JButton("Introduce Compra"));
		panel.add(new JButton("Introduce Venta"));
		return panel;
	}
	
	private JPanel getPanelCentro(){
		JPanel panel = new JPanel();
		panel.add(getPanelLista());
		return panel;
	}
	
	private JScrollPane getPanelLista(){
		JScrollPane panel = new JScrollPane();
		DefaultListModel m = new DefaultListModel(); 
		m.addElement("HOla 1");
		m.addElement("HOla 2");
		m.addElement("HOla 3");
		JList lista = new JList(m);
		panel.add(lista);
		return panel;
	}
}
