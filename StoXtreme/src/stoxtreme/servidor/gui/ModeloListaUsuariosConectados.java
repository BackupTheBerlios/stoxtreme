package stoxtreme.servidor.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ModeloListaUsuariosConectados extends AbstractListModel{
	private ArrayList usuariosRegistrados;
	private Hashtable estadoUsuarios;
	
	public ModeloListaUsuariosConectados(){
		estadoUsuarios = new Hashtable();
		usuariosRegistrados = new ArrayList();
	}
	
	public void addUsuario(String usuario){
		estadoUsuarios.put(usuario, new Boolean(false));
		usuariosRegistrados.add(usuario);
		fireContentsChanged(this, usuariosRegistrados.size()-1, usuariosRegistrados.size()-1);
	}
	
	public void setEstadoUsuario(String usuario, boolean estado){
		estadoUsuarios.put(usuario, new Boolean(estado));
		int index = usuariosRegistrados.indexOf(usuario);
		fireContentsChanged(this, index, index);
	}

	public int getSize() {
		return usuariosRegistrados.size();
	}

	public Object getElementAt(int index) {
		return usuariosRegistrados.get(index);
	}

	public ListCellRenderer getListCellRenderer(){
		ListCellRenderer renderer = new DefaultListCellRenderer(){
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if(((Boolean)estadoUsuarios.get(value)).booleanValue())
					c.setForeground(Color.blue);
				return c;
			}
		};
		return renderer;
	}
}
