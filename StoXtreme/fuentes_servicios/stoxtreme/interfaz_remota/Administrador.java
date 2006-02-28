package stoxtreme.interfaz_remota;

public interface Administrador {
	public void iniciarServidor();
	public void pararServidor();
	public void iniciaSesion();
	public void finalizaSesion();
	public void showGUI();
	public void hideGUI();
}
