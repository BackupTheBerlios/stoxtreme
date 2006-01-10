package interfaz_remota;

public interface IAgente {
	// Los agentes normales no se registran ni hacen login, es el usuario
	public int insertaOperacion(String id, Operacion op);
	public void cancelaOperacion(int idOp);
	public void modificaOperacion(int idOp, Operacion nuevaOp);
}
