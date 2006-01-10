package interfaz_remota;

public interface IUsuario {
	public boolean registro(String nUsuario, String pswd);
	public boolean login(String nUsuario, String pswd);
}
