package stoxtreme.interfaz_remota;

public interface Stoxtreme {
    public boolean registro(String id, String pass);
    public boolean login(String id, String pass);
    public int insertarOperacion(String id, Operacion op);
    public void cancelarOperacion(String id, int op);
}
