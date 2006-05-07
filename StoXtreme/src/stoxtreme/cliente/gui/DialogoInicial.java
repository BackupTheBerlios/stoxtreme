package stoxtreme.cliente.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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


public class DialogoInicial extends JFrame{
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
	private Object despertador;
	
	private void cerrarVentana() {
		synchronized (despertador) {
			despertador.notify();
		}
	}
	public DialogoInicial(Object despertador){
		super();
		this.despertador = despertador;
		this.init();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Identificacion de usuario");
		this.setSize(new Dimension(270,182));
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				cerrarVentana();
			}
		});
		
		inicial=this;
		operacion=0;
	}
	
	public void init(){
		getContentPane().add(getPanelPrincipal());
	}
	
	//Panel Principal
	private Component getPanelPrincipal(){
		principal=new JPanel(new BorderLayout());
		principal.add(getCentralPanel(), BorderLayout.CENTER);
		principal.add(this.getPButtons(),BorderLayout.SOUTH);
		FakeInternalFrame frame = new FakeInternalFrame("Conectese o cree una cuenta nueva", principal);
		return frame;
		
	}
	private Component getCentralPanel(){
		JPanel panel = new JPanel(new GridLayout(2,1));
		panel.add(this.getPUser());
		panel.add(this.getPPass());
		return panel;
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
	
	public void botonLogin_actionPerformed(ActionEvent e){
		id=user.getText();
		psw=new String(password.getPassword());
		operacion=1;
		inicial.setVisible(false);
		synchronized (despertador) {
			despertador.notify();
		}
	}
	
	public void botonRegistrar_actionPerformed(ActionEvent e){
		id=user.getText();
		psw=new String(password.getPassword());
		operacion=2;
		inicial.setVisible(false);
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
				botonLogin_actionPerformed(e);
			}
		});
		
		registrar=new JButton();
		registrar.setText("Registrar");
		registrar.setToolTipText("Registra a un nuevo usuario en el servidor y se conecta automaticamente");
		registrar.setPreferredSize(new Dimension(100,20));
		//Registro de un nuevo usuario
		registrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				botonRegistrar_actionPerformed(e);
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
	
//	private static class Ventanuco extends JFrame{
//		JButton botonAceptar = new JButton("Aceptar");
//		JButton botonCancelar = new JButton("Canclear");
//		boolean aceptado = false;
//		Object despertador;
//		
//		public Ventanuco(Object despertador) {
//			this.despertador = despertador;
//			try{
//				init();
//			}
//			catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		
//		public void init(){
//			add(getPanelPrincipal());
//		}
//		private void botonAceptar_actionPerformed(ActionEvent e) {
//			this.setVisible(false);
//			aceptado = true;
//			synchronized (despertador) {
//				despertador.notify();
//			}
//		}
//		
//		private void botonCancelar_actionPerformed(ActionEvent e) {
//			this.setVisible(false);
//			aceptado = false;
//			synchronized (despertador) {
//				despertador.notify();
//			}
//		}
//		
//		public Component getPanelPrincipal(){
//			JPanel panel = new JPanel();
//			botonAceptar.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent e){
//					botonAceptar_actionPerformed(e);
//				}
//			});
//			
//			botonCancelar.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent e){
//					botonCancelar_actionPerformed(e);
//				}
//			});
//			
//			panel.add(botonAceptar);
//			panel.add(botonCancelar);
//			return panel;
//		}
//		public boolean isAceptado(){
//			return aceptado;
//		}
//	}
//	
//	public static void main(String[] args) {
//		Object o = new Object();
//		Ventanuco f = new Ventanuco(o);
//		f.pack();
//		f.setVisible(true);
//		
//		try {
//			synchronized(o){
//				o.wait();
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		System.err.println("NOOOOO");
//		System.exit(1);
//	}
}
