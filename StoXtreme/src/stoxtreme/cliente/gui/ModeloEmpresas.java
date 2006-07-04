package stoxtreme.cliente.gui;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.DefaultComboBoxModel;

import stoxtreme.cliente.infoLocal.InfoLocal;

/**
 *  Permite representar las empresas en la interfaz gráfica
 *  
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ModeloEmpresas extends DefaultComboBoxModel {
	private Hashtable<String, ValoresEmpresa> empresas;
	private ArrayList<String> nombreEmpresas;
	private String empresaFocus;
	private ValoresEmpresa valorFocus;


	/**
	 *  Constructor de ModeloEmpresas
	 *
	 *@param  info  Información que se encuentra en el disco
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
	 *  Asigna un el elemento seleccionado
	 *
	 *@param  anItem  Nuevo objeto selecionado
	 */
	public void setSelectedItem(Object anItem) {
		empresaFocus = (String) anItem;
		valorFocus = (ValoresEmpresa) empresas.get(empresaFocus);
	}


	/**
	 *  Obtiene el elemento seleccionado
	 *
	 *@return    Objeto seleccionado
	 */
	public Object getSelectedItem() {
		return empresaFocus;
	}


	/**
	 *  Obtiene el tamaño de las empresas
	 *
	 *@return    Valor del tamaño
	 */
	public int getSize() {
		return nombreEmpresas.size();
	}


	/**
	 *  Obtengo un atributo del modelo de empresas
	 *
	 *@param  index  Indice de empresa
	 *@return        El valor del elemento
	 */
	public Object getElementAt(int index) {
		return nombreEmpresas.get(index);
	}


	/**
	 *  obtiene los valores del modelo de empresa
	 *
	 *@return    Valores del modelo
	 */
	public ValoresEmpresa getValoresFocus() {
		return valorFocus;
	}


	/**
	 *  Inserta un nuevo valor en una empresa
	 *
	 *@param  empresa  Nombre de la empresa
	 *@param  valor    Valor nuevo
	 */
	public void insertaValor(String empresa, double valor) {
		((ValoresEmpresa) empresas.get(empresa)).insertarSiguienteValor(valor);
	}
}
