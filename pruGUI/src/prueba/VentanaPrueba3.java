package prueba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

public class VentanaPrueba3 extends JInternalFrame{
	public void jbInit(){
		setSize(450, 600);
		setVisible(true);
		setResizable(true);
		setTitle("Ventana Prueba 2");
		setClosable(true);
		setMaximizable(true);
		
		DefaultTableModel modelo = new DefaultTableModel();
		String[] columnas = {"Condicion", "Accion", "Unica vez"};
		modelo.setColumnIdentifiers(columnas);
		Vector v = new Vector();
		v.add(0, "Condicion 1"); v.add(1, "Accion 1"); v.add(2, new Boolean(true));
		modelo.addRow(v);
		v = new Vector();
		v.add("Condicion 2"); v.add("Accion 2"); v.add(new Boolean(false));
		modelo.addRow(v);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
		
		JPanel panelBotones = new JPanel(new FlowLayout());
		panelBotones.add(new JButton("Añadir Evento"));
		panelBotones.add(new JButton("Ver variables"));
		panelBotones.add(new JButton("Eliminar Evento"));
		getContentPane().add(panelBotones, BorderLayout.SOUTH);
	}
}
