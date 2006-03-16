package stoxtreme.servidor.objeto_bolsa.fluctuaciones;

/**
 * <p>T�tulo: </p>
 * <p>Descripci�n: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Empresa: </p>
 * @author sin atribuir
 * @version 1.0
 */
public class Posicion {
  String idAgente;
  int numeroDeAcciones;
  int idOp;
  public Posicion(String id, int nA,int iO) {
    idAgente=id;
    numeroDeAcciones=nA;
    idOp=iO;
  }
  public String getPrecio(){return idAgente;}
  public int getNumeroDeAcciones(){return numeroDeAcciones;}
  public int getIdOperacion(){return idOp;}

  public void setPrecio(String id){idAgente=id;}
  public void setNumeroDeAcciones(int nA){numeroDeAcciones=nA;}
  public void setIdOperacion(int iO){idOp=iO;}


}