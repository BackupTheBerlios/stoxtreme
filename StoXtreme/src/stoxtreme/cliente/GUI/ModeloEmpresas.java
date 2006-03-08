package stoxtreme.cliente.GUI;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.DefaultComboBoxModel;

public class ModeloEmpresas extends DefaultComboBoxModel{
	private Hashtable empresas;
	private ArrayList nombreEmpresas;
	private String empresaFocus;
	private ValoresEmpresa valorFocus;
	
	public ModeloEmpresas(ArrayList nEmpresas){
		empresas = new Hashtable();
		nombreEmpresas = new ArrayList(nEmpresas.size());
		for(int i=0; i<nEmpresas.size(); i++){
			String nEmp = (String)nEmpresas.get(i);
			nombreEmpresas.add(nEmp);
			empresas.put(nEmp, new ValoresEmpresa(nEmp, 1));
		}
		
		empresaFocus = (String)nombreEmpresas.get(0);
		valorFocus = (ValoresEmpresa)empresas.get(nombreEmpresas.get(0));
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
