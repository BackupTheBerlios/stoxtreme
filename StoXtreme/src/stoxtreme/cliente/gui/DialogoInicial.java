package stoxtreme.cliente.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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

//TODO Casi todo, solo estan puestos los componentes

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
				String psw=password.getPassword().toString();
				boolean login=Servidor.getInstance().login(id,psw);
				if (!login){
					JOptionPane.showConfirmDialog(null, "Error al realizar el login");
				}
				else
					try{
						cliente.init(id,psw);
					}
					catch(Exception ex){
						ex.printStackTrace();
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
				String psw=password.getPassword().toString();
				if (id.equals(null)||psw.equals(null))
					JOptionPane.showConfirmDialog(null, "Por favor, rellene todos los campos");
				else{
					boolean registro=Servidor.getInstance().registro(id,psw);
					if (!registro){
						JOptionPane.showConfirmDialog(null, "Ya existe un usuario con ese nombre");
					}
					else
						try{
							cliente.init(id,psw);
						}
						catch(Exception ex){
							ex.printStackTrace();
						}
				}
			}
		});
		PButtons.add(login,BorderLayout.WEST);
		PButtons.add(registrar,BorderLayout.EAST);
		return PButtons;
	}
}
