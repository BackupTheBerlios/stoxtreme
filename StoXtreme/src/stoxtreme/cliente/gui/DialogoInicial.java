package stoxtreme.cliente.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import stoxtreme.cliente.Cliente;
import stoxtreme.servidor.Servidor;


public class DialogoInicial extends JFrame{
	private JButton login;
	private JButton registrar;
	private JTextField user;
	private JPasswordField password;
	private JLabel usuario;
	private JLabel contraseña;
	private JPanel principal;
	private Cliente cliente;
	
	static{
		try{
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		}
		catch(Exception e){
			
		}
	}
	
	public DialogoInicial(Cliente cliente){
		super ("Identificación");
		this.cliente=cliente;
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	public void init(){
		principal=(JPanel)this.getPanelPrincipal();
		this.add(principal);
	}
	
	private Component getPanelPrincipal(){
		principal=new JPanel(new BorderLayout());
		//principal.setPreferredSize(new Dimension(300,200));
		//Panel Principal
		principal.add(this.getPUser(),BorderLayout.NORTH);
		principal.add(this.getPPass(),BorderLayout.CENTER);
		principal.add(this.getPButtons(),BorderLayout.SOUTH);
		FakeInternalFrame frame = new FakeInternalFrame("Conéctese o cree una cuenta nueva", principal);
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
	
	//Panel de la Contraseña
	private Component getPPass(){
		JPanel PPass=new JPanel(new BorderLayout());
		PPass.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		contraseña=new JLabel("Contraseña");
		password=new JPasswordField();
		password.setToolTipText("Introduzca su contraseña");
		password.setPreferredSize(new Dimension(120,20));
		PPass.add(contraseña,BorderLayout.WEST);
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
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String id=user.getText();
				String psw=new String(password.getPassword());
				if (id.equals("")||psw.equals(""))
					JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos",
							"Revise sus datos",JOptionPane.WARNING_MESSAGE);
				else{
					try{
						boolean login=Servidor.getInstance().login(id,psw);
						if (!login)
							JOptionPane.showMessageDialog(null, "El usuario no existe o la contraseña es errónea",
									"Error",JOptionPane.ERROR_MESSAGE);
						else
							cliente.init(id,psw);
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, "El servidor parece estar caído. \n Inténtelo de nuevo más tarde",
								"Error de conexión",JOptionPane.ERROR_MESSAGE);
					}
				}	
			}
		});
		registrar=new JButton();
		registrar.setText("Registrar");
		registrar.setToolTipText("Registra a un nuevo usuario en el servidor y se conecta automaticamente");
		registrar.setPreferredSize(new Dimension(100,20));
		registrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String id=user.getText();
				String psw=new String(password.getPassword());
				if (id.equals("") ||psw.equals(""))
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
							cliente.init(id,psw);
						}
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, "El servidor parece estar caído. \n Inténtelo de nuevo más tarde",
								"Error de conexión",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		PButtons.add(login,BorderLayout.WEST);
		PButtons.add(registrar,BorderLayout.EAST);
		return PButtons;
	}

}
