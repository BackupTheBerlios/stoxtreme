package stoxtreme.cliente.gui;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.DefaultComboBoxModel;

import stoxtreme.cliente.infoLocal.InfoLocal;

public class ModeloEmpresas extends DefaultComboBoxModel{
	private Hashtable<String,ValoresEmpresa> empresas;
	private ArrayList<String> nombreEmpresas;
	private String empresaFocus;
	private ValoresEmpresa valorFocus;
	
	public ModeloEmpresas(InfoLocal info){
		empresas = new Hashtable<String,ValoresEmpresa>();
		this.nombreEmpresas = info.getEmpresas();
		
		for(int i=0; i<nombreEmpresas.size(); i++){
			String nEmp = (String)nombreEmpresas.get(i);
			ValoresEmpresa vemp = new ValoresEmpresa(nEmp, 1);
			vemp.insertarSiguienteValor(info.getPrecioInicial(nEmp));
			empresas.put(nEmp, vemp);
		}
		
		empresaFocus = nombreEmpresas.get(0);
		valorFocus = empresas.get(nombreEmpresas.get(0));
	}
	
	public void insertaValor(String empresa, double valor){
		((ValoresEmpresa)empresas.get(empresa)).insertarSiguienteValor(valor);
	}

	public void setSelectedItem(Object anItem) {
		empresaFocus = (String)anItem;
		valorFocus = (ValoresEmpresa)empresas.get(empresaFocus);
	}

	public Object getSelectedItem() {
		return empresaFocus;
	}

	public int getSize() {
		return nombreEmpresas.size();
	}

	public Object getElementAt(int index) {
		return nombreEmpresas.get(index);
	}
	
	public ValoresEmpresa getValoresFocus(){
		return valorFocus;
	}
}
