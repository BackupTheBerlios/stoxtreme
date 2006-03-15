package stoxtreme.cliente.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

//TODO Casi todo, solo estan puestos los componentes

public class DialogoInicial extends JFrame{
	private JButton login;
	private JButton registrar;
	private JTextField user;
	private JPasswordField password;
	private JLabel usuario;
	private JLabel contraseña;
	private JPanel principal;
	
	static{
		try{
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		}
		catch(Exception e){
			
		}
	}
	
	public DialogoInicial(){
		super ("Conectese o cree una cuenta nueva");
		this.setSize(new Dimension(300,200));
	}
	
	public void init(){
		principal=(JPanel)this.getPanelPrincipal();
		this.add(principal);
	}
	
	private Component getPanelPrincipal(){
		principal=new JPanel(new BorderLayout());
		//principal.setPreferredSize(new Dimension(300,200));
		//Panel del Nombre de Usuario
		JPanel PUser=new JPanel(new BorderLayout());
		//PUser.setPreferredSize(new Dimension(300, 50));
		usuario=new JLabel("Nombre de usuario");
		usuario.setLocation(10,10);
		user=new JTextField();
		user.setToolTipText("Introduzca su nombre de usuario");
		user.setPreferredSize(new Dimension(190,20));
		PUser.add(usuario,BorderLayout.WEST);
		PUser.add(user,BorderLayout.EAST);
		//Panel de la Contraseña
		JPanel PPass=new JPanel(new BorderLayout());
		//PPass.setPreferredSize(new Dimension(300,50));
		contraseña=new JLabel("Contraseña");
		password=new JPasswordField();
		password.setToolTipText("Introduzca su contraseña");
		password.setPreferredSize(new Dimension(190,20));
		PPass.add(contraseña,BorderLayout.WEST);
		PPass.add(password,BorderLayout.EAST);
		//Panel de los botones
		JPanel PButtons=new JPanel(new BorderLayout());
		//PButtons.setPreferredSize(new Dimension(300,50));
		login=new JButton();
		login.setText("Login");
		login.setToolTipText("Conecta a un usuario previamente registrado");
		login.setPreferredSize(new Dimension(100,20));
		registrar=new JButton();
		registrar.setText("Registrar");
		registrar.setToolTipText("Registra a un nuevo usuario en el servidor");
		registrar.setPreferredSize(new Dimension(100,20));
		PButtons.add(login,BorderLayout.WEST);
		PButtons.add(registrar,BorderLayout.EAST);
		//Panel Principal
		principal.add(PUser,BorderLayout.NORTH);
		principal.add(PPass,BorderLayout.CENTER);
		principal.add(PButtons,BorderLayout.SOUTH);
		return principal;
		
	}
}
