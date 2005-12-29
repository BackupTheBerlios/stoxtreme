package prueba;

import javax.swing.*;
import java.awt.*;
public class PanelGrafico extends JPanel{
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
	}
}
