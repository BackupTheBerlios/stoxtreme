package prueba;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class VentanaPrueba2 extends JInternalFrame{
	public void jbInit(){
		setSize(300, 300);
		setVisible(true);
		setResizable(true);
		setTitle("Ventana Prueba 2");
		setClosable(true);
		setMaximizable(true);
		
		getContentPane().add(new PanelGrafico());
	}
}
