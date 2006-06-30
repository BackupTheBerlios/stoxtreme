package stoxtreme.servidor.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ModeloListaUsuariosConectados extends AbstractListModel {
	private ArrayList usuariosRegistrados;
	private Hashtable estadoUsuarios;


	/**
	 *  Constructor for the ModeloListaUsuariosConectados object
	 */
	public ModeloListaUsuariosConectados() {
		estadoUsuarios = new Hashtable();
		usuariosRegistrados = new ArrayList();
	}


	/**
	 *  Sets the EstadoUsuario attribute of the ModeloListaUsuariosConectados
	 *  object
	 *
	 *@param  usuario  The new EstadoUsuario value
	 *@param  estado   The new EstadoUsuario value
	 */
	public void setEstadoUsuario(String usuario, boolean estado) {
		estadoUsuarios.put(usuario, new Boolean(estado));
		int index = usuariosRegistrados.indexOf(usuario);
		fireContentsChanged(this, index, index);
	}


	/**
	 *  Gets the Size attribute of the ModeloListaUsuariosConectados object
	 *
	 *@return    The Size value
	 */
	public int getSize() {
		return usuariosRegistrados.size();
	}


	/**
	 *  Gets the ElementAt attribute of the ModeloListaUsuariosConectados object
	 *
	 *@param  index  Description of Parameter
	 *@return        The ElementAt value
	 */
	public Object getElementAt(int index) {
		return usuariosRegistrados.get(index);
	}


	/**
	 *  Gets the ListCellRenderer attribute of the ModeloListaUsuariosConectados
	 *  object
	 *
	 *@return    The ListCellRenderer value
	 */
	public ListCellRenderer getListCellRenderer() {
		ListCellRenderer renderer =
			new DefaultListCellRenderer() {
				public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
					Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					if (((Boolean) estadoUsuarios.get(value)).booleanValue()) {
						c.setForeground(Color.blue);
					}
					return c;
				}
			};
		return renderer;
	}


	/**
	 *  Adds a feature to the Usuario attribute of the
	 *  ModeloListaUsuariosConectados object
	 *
	 *@param  usuario  The feature to be added to the Usuario attribute
	 */
	public void addUsuario(String usuario) {
		estadoUsuarios.put(usuario, new Boolean(false));
		usuariosRegistrados.add(usuario);
		fireContentsChanged(this, usuariosRegistrados.size() - 1, usuariosRegistrados.size() - 1);
	}
}
