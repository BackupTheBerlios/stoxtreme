package stoxtreme.cliente.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
@SuppressWarnings("serial")
public class DialogoInicial extends JFrame {
	private JButton login;
	private JButton registrar;
	private JPanel principal;
	private String id;
	private String psw;
	private DialogoInicial inicial;
	//0=Error (ha cerrado la ventana), 1=Login, 2=Registro
	private int operacion;
	private Object despertador;

	//Panel Principal
	private PanelOpciones panelOpciones;
	private static Image LOGO = Toolkit.getDefaultToolkit().getImage("logo.png");
	private static final String NUSUARIO = "Nombre de Usuario";
	private static final String NPASS = "Contraseña";
	private static final String NURL = "Dirección del servidor";


	/**
	 *  Constructor for the DialogoInicial object
	 *
	 *@param  despertador  Description of Parameter
	 */
	public DialogoInicial(Object despertador) {
		super();
		this.despertador = despertador;
		this.init();
//		this.setResizable(false);
//		this.setLocationRelativeTo(null);
		this.setTitle("Identificacion de usuario");
		//this.setSize(new Dimension(270,182));
		pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (d.getWidth() / 2.0 - getWidth() / 2.0), (int) (d.getHeight() / 2 - getHeight() / 2));
		this.addWindowListener(
					new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							cerrarVentana();
						}
					});

		inicial = this;
		operacion = 0;
	}


	/**
	 *  Sets the Operacion attribute of the DialogoInicial object
	 *
	 *@param  i  The new Operacion value
	 */
	public void setOperacion(int i) {
		operacion = i;

	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String getusuario() {
		return id;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String getpass() {
		return psw;
	}


	/**
	 *  Gets the Operacion attribute of the DialogoInicial object
	 *
	 *@return    The Operacion value
	 */
	public int getOperacion() {
		return operacion;
	}


	/**
	 *  Gets the DireccionServidor attribute of the DialogoInicial object
	 *
	 *@return    The DireccionServidor value
	 */
	public String getDireccionServidor() {
		return panelOpciones.getValor(NURL);
	}


	/**
	 *  Description of the Method
	 */
	public void init() {
		getContentPane().add(getPanelPrincipal());
	}



	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	public void botonLogin_actionPerformed(ActionEvent e) {
		id = panelOpciones.getValor(NUSUARIO);
		psw = panelOpciones.getValor(NPASS);
		//id=user.getText();
		//psw=new String(password.getPassword());
		operacion = 1;
		inicial.setVisible(false);
		synchronized (despertador) {
			despertador.notify();
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	public void botonRegistrar_actionPerformed(ActionEvent e) {
		id = panelOpciones.getValor(NUSUARIO);
		psw = panelOpciones.getValor(NPASS);
		//id=user.getText();
		//psw=new String(password.getPassword());
		operacion = 2;
		inicial.setVisible(false);
		synchronized (despertador) {
			despertador.notify();
		}
	}


	/**
	 *  Gets the PanelPrincipal attribute of the DialogoInicial object
	 *
	 *@return    The PanelPrincipal value
	 */
	private Component getPanelPrincipal() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getPanelSuperior(), BorderLayout.CENTER);
		panel.add(getPanelInferior(), BorderLayout.SOUTH);
		return panel;
	}


	/**
	 *  Gets the PanelSuperior attribute of the DialogoInicial object
	 *
	 *@return    The PanelSuperior value
	 */
	private Component getPanelSuperior() {
		JPanel panel =
			new JPanel() {
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					g.drawImage(LOGO, 0, 0, 480, 500, this);
				}
			};
		panel.setPreferredSize(new Dimension(480, 500));
		return panel;
	}


	/**
	 *  Gets the PanelInferior attribute of the DialogoInicial object
	 *
	 *@return    The PanelInferior value
	 */
	private Component getPanelInferior() {
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add(NUSUARIO);
		opciones.add(NPASS);
		opciones.add(NURL);

		ArrayList<String> pass = new ArrayList<String>();
		pass.add(NPASS);

		panelOpciones = new PanelOpciones(opciones, pass);
		principal = new JPanel(new BorderLayout());
		panelOpciones.setValor(NURL, "http://localhost:8080");
		principal.add(panelOpciones, BorderLayout.CENTER);
		//principal.add(getCentralPanel(), BorderLayout.CENTER);
		principal.add(getPButtons(), BorderLayout.SOUTH);
		FakeInternalFrame frame = new FakeInternalFrame("Conectese o cree una cuenta nueva", principal);
		return frame;
	}


	//Panel de los botones
	/**
	 *  Gets the PButtons attribute of the DialogoInicial object
	 *
	 *@return    The PButtons value
	 */
	private Component getPButtons() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		login = new JButton();
		login.setText("Login");
		login.setToolTipText("Conecta a un usuario previamente registrado");
		login.setPreferredSize(new Dimension(100, 20));
		//Login de un usario ya existente
		login.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							botonLogin_actionPerformed(e);
						}
					});

		registrar = new JButton();
		registrar.setText("Registrar");
		registrar.setToolTipText("Registra a un nuevo usuario en el servidor y se conecta automaticamente");
		registrar.setPreferredSize(new Dimension(100, 20));
		//Registro de un nuevo usuario
		registrar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							botonRegistrar_actionPerformed(e);
						}
					});
		panel.add(login);
		panel.add(registrar);
		return panel;
	}


	/**
	 *  Description of the Method
	 */
	private void cerrarVentana() {
		synchronized (despertador) {
			despertador.notify();
		}
	}
}
