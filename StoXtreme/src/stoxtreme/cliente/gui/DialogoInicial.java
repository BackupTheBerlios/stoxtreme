package stoxtreme.cliente.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import stoxtreme.cliente.Cliente;
import stoxtreme.servidor.Servidor;


public class DialogoInicial extends JDialog{
	private JButton login;
	private JButton registrar;
	private JTextField user;
	private JPasswordField password;
	private JLabel usuario;
	private JLabel pass;
	private JPanel principal;
	private String id;
	private String psw;
	private DialogoInicial inicial;
	//0=Error (ha cerrado la ventana), 1=Login, 2=Registro
	private int operacion;
	
	
	
	public DialogoInicial(){
		super();
		this.init();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Identificacion de usuario");
		this.setSize(new Dimension(270,178));
		inicial=this;
		operacion=0;
	}
	
	public void init(){
		getContentPane().add(getPanelPrincipal());
	}
	
	//Panel Principal
	private Component getPanelPrincipal(){
		principal=new JPanel(new BorderLayout());
		principal.add(this.getPUser(),BorderLayout.NORTH);
		principal.add(this.getPPass(),BorderLayout.CENTER);
		principal.add(this.getPButtons(),BorderLayout.SOUTH);
		FakeInternalFrame frame = new FakeInternalFrame("Conectese o cree una cuenta nueva", principal);
		return frame;
		
	}
	
	//Panel del Nombre de Usuario
	private Component getPUser(){
		JPanel PUser=new JPanel(new BorderLayout());
		PUser.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		usuario=new JLabel("Nombre de usuario   ");
		user=new JTextField();
		user.setToolTipText("Introduzca su nombre de usuario");
		user.setPreferredSize(new Dimension(120,20));
		PUser.add(usuario,BorderLayout.WEST);
		PUser.add(user,BorderLayout.EAST);
		return PUser;
	}
	
	//Panel de la Contrase�a
	private Component getPPass(){
		JPanel PPass=new JPanel(new BorderLayout());
		PPass.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		pass=new JLabel("Password");
		password=new JPasswordField();
		password.setToolTipText("Introduzca su password");
		password.setPreferredSize(new Dimension(120,20));
		PPass.add(pass,BorderLayout.WEST);
		PPass.add(password,BorderLayout.EAST);
		return PPass;
	}
	
	//Panel de los botones
	private Component getPButtons(){
		JPanel PButtons=new JPanel(new BorderLayout());
		PButtons.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		login=new JButton();
		login.setText("Login");
		login.setToolTipText("Conecta a un usuario previamente registrado");
		login.setPreferredSize(new Dimension(100,20));
		//Login de un usario ya existente
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				id=user.getText();
				psw=new String(password.getPassword());
				operacion=1;
				inicial.setVisible(false);
				/*if (id.equals("")||psw.equals(""))
					JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos",
							"Revise sus datos",JOptionPane.WARNING_MESSAGE);
				else{
					try{
						boolean login=Servidor.getInstance().login(id,psw);
						if (!login)
							JOptionPane.showMessageDialog(null, "El usuario no existe o el password es erroneo",
									"Error",JOptionPane.ERROR_MESSAGE);
						else
							inicial.dispose();
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, "El servidor parece estar caido. \n Intentelo de nuevo mas tarde",
								"Error de conexion",JOptionPane.ERROR_MESSAGE);
	}*/
			}
		});
		
		registrar=new JButton();
		registrar.setText("Registrar");
		registrar.setToolTipText("Registra a un nuevo usuario en el servidor y se conecta automaticamente");
		registrar.setPreferredSize(new Dimension(100,20));
		//Registro de un nuevo usuario
		registrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				id=user.getText();
				psw=new String(password.getPassword());
				operacion=2;
				inicial.setVisible(false);
/*				if (id.equals("") ||psw.equals(""))
					JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos",
							"Revise sus datos",JOptionPane.WARNING_MESSAGE);
				else{
					try{
						boolean registro=Servidor.getInstance().registro(id,psw);
						if (!registro)
							JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre",
								"Error",JOptionPane.ERROR_MESSAGE);
						else{
							Servidor.getInstance().login(id,psw);
							inicial.dispose();
						}
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, "El servidor parece estar caido. \n Intentelo de nuevo mas tarde",
								"Error de conexion",JOptionPane.ERROR_MESSAGE);
					}
				}*/
			}
		});
		PButtons.add(login,BorderLayout.WEST);
		PButtons.add(registrar,BorderLayout.EAST);
		return PButtons;
	}

	public String getusuario() {
		return id;
	}

	public String getpass() {
		return psw;
	}

	public int getOperacion(){
		return operacion;
	}

	public void setOperacion(int i) {
		operacion=i;
		
	}
}
