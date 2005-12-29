package prueba;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class VentanaPrueba1 extends JInternalFrame{
	JScrollPane scrollTablaEventos = null;
	JTable tablaEventos = null;
	
	public void jbInit(){
		// Creamos los objetos necesarios
		scrollTablaEventos = new JScrollPane();
		tablaEventos = new JTable();
		
		setSize(400, 300);
		setVisible(true);
		setResizable(true);
		setTitle("Ventana Prueba 1");
		setClosable(true);
		setMaximizable(true);
		
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		JPanel panel1 = new JPanel(new FlowLayout());
		panel1.add(new JButton("Boton 1"));
		panel1.add(new JButton("Boton 2"));
		panel1.add(new JButton("Boton 3"));
		content.add(panel1, BorderLayout.SOUTH);
		content.add(new JScrollPane(new JTextArea()), BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel(new FlowLayout());
		panel2.add(new JLabel("Introduzca su nombre"));
		panel2.add(new JTextField(10));
		String[] options = {"Opcion 1", "Opcion 2", "Opcion 3"};
		panel2.add(new JComboBox(options));
		content.add(panel2, BorderLayout.NORTH);
	}
}
