package interfaz_remota;

public interface IAgente {
	public boolean registro(String nUsuario, String pswd);
	public boolean login(String nUsuario, String pswd);
	public int insertaOperacion(String id, Operacion op);
	public void cancelaOperacion(int idOp);
	public void modificaOperacion(int idOp, Operacion nuevaOp);
}
