package prueba;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JFrame{
	JDesktopPane mainPane;
	
	public MainGUI(){
		super("Stock Xtreme");
		mainPane = new JDesktopPane();
		this.setContentPane(mainPane);
		mainPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		
		JInternalFrame f = 
			new JInternalFrame("Probando", true, true, true, true);

		f.setSize(300, 300);
		f.setVisible(true);
		
		mainPane.add(f);
		initialize();
	}
	
	public void initialize(){
		Dimension d = new Dimension(600, 600);
		this.setSize(d);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		
		this.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						System.exit(0);
					}
				}
		);
	}
	
	public static void main(String[] args) {
		MainGUI m = new MainGUI();
		m.pack();
		m.show();
	}

}
