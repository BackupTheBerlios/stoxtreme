package stoxtreme.cliente.gui;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.DefaultComboBoxModel;

import stoxtreme.cliente.infoLocal.InfoLocal;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ModeloEmpresas extends DefaultComboBoxModel {
	private Hashtable<String, ValoresEmpresa> empresas;
	private ArrayList<String> nombreEmpresas;
	private String empresaFocus;
	private ValoresEmpresa valorFocus;


	/**
	 *  Constructor for the ModeloEmpresas object
	 *
	 *@param  info  Description of Parameter
	 */
	public ModeloEmpresas(InfoLocal info) {
		empresas = new Hashtable<String, ValoresEmpresa>();
		this.nombreEmpresas = info.getEmpresas();

		for (int i = 0; i < nombreEmpresas.size(); i++) {
			String nEmp = (String) nombreEmpresas.get(i);
			ValoresEmpresa vemp = new ValoresEmpresa(nEmp, 1);
			vemp.insertarSiguienteValor(info.getPrecioInicial(nEmp));
			empresas.put(nEmp, vemp);
		}

		empresaFocus = nombreEmpresas.get(0);
		valorFocus = empresas.get(nombreEmpresas.get(0));
	}


	/**
	 *  Sets the SelectedItem attribute of the ModeloEmpresas object
	 *
	 *@param  anItem  The new SelectedItem value
	 */
	public void setSelectedItem(Object anItem) {
		empresaFocus = (String) anItem;
		valorFocus = (ValoresEmpresa) empresas.get(empresaFocus);
	}


	/**
	 *  Gets the SelectedItem attribute of the ModeloEmpresas object
	 *
	 *@return    The SelectedItem value
	 */
	public Object getSelectedItem() {
		return empresaFocus;
	}


	/**
	 *  Gets the Size attribute of the ModeloEmpresas object
	 *
	 *@return    The Size value
	 */
	public int getSize() {
		return nombreEmpresas.size();
	}


	/**
	 *  Gets the ElementAt attribute of the ModeloEmpresas object
	 *
	 *@param  index  Description of Parameter
	 *@return        The ElementAt value
	 */
	public Object getElementAt(int index) {
		return nombreEmpresas.get(index);
	}


	/**
	 *  Gets the ValoresFocus attribute of the ModeloEmpresas object
	 *
	 *@return    The ValoresFocus value
	 */
	public ValoresEmpresa getValoresFocus() {
		return valorFocus;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 *@param  valor    Description of Parameter
	 */
	public void insertaValor(String empresa, double valor) {
		((ValoresEmpresa) empresas.get(empresa)).insertarSiguienteValor(valor);
	}
}
