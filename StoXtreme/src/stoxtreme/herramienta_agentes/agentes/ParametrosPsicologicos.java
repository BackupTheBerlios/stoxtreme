package stoxtreme.herramienta_agentes.agentes;

import java.util.EnumMap;

public class ParametrosPsicologicos {
	private EnumMap<Parametro,String> parametros = new EnumMap<Parametro,String>(Parametro.class);
	
	public enum Parametro{
		TIEMPO_ESPERA,
		NUMERO_MAXIMO_ACCIONES_COMPRA,
		NUMERO_MINIMO_ACCIONES_COMPRA,
		NUMERO_MAXIMO_ACCIONES_VENTA,
		NUMERO_MINIMO_ACCIONES_VENTA,
		NUMERO_MAXIMO_CANCELACIONES,
	
		PORCENTAJE_MAXIMO_COMPRA,
		PORCENTAJE_MAXIMO_VENTA,
		PORCENTAJE_MINIMO_COMPRA,
		PORCENTAJE_MINIMO_VENTA,
		PORCENTAJE_SUBIDA_PRECIO,
		PORCENTAJE_BAJADA_PRECIO, 
		PRECIO_RECOMENDACION, 
		PRECIO_COMPRA_RECOMENDACION,
		
	}
	
	public void setParametro(String p, String v){
		Parametro param = Parametro.valueOf(p);
		parametros.put(param, v);
	}
	
	public int getParamInt(Parametro p){
		return (int)Double.parseDouble(parametros.get(p));
	}
	public double getParamDouble(Parametro p){
		return Double.parseDouble(parametros.get(p));
	}
	public String getParamString(Parametro p){
		return parametros.get(p);
	}
//	private double tiempoEspera;
//	private int numeroMaximoAccionesCompra;
//	private int numeroMinimoAccionesCompra;
//	private int numeroMaximoAccionesVenta;
//	private int numeroMinimoAccionesVenta;
//	private int numeroMaximoCancelaciones;
//	
//	private double porcentajeMaximoCompra;
//	private double porcentajeMaximoVenta;
//	private double porcentajeMinimoCompra;
//	private double porcentajeMinimoVenta;
//	private double porcentajeSubidaPrecio;
//	private double porcentajeBajadaPrecio;
//	
//	public ParametrosPsicologicos(){
//		tiempoEspera = 0;
//	}
//	
//	// GETTERS Y SETTERS
//	public void setTiempoEspera(double tiempoEspera) {
//		this.tiempoEspera = tiempoEspera;
//	}
//
//	public double getTiempoEspera() {
//		return tiempoEspera;
//	}
//
//	public void setNumeroMaximoAccionesCompra(int numeroMaximoAccionesCompra) {
//		this.numeroMaximoAccionesCompra = numeroMaximoAccionesCompra;
//	}
//
//	public int getNumeroMaximoAccionesCompra() {
//		return numeroMaximoAccionesCompra;
//	}
//
//	public void setNumeroMaximoAccionesVenta(int numeroMaximoAccionesVenta) {
//		this.numeroMaximoAccionesVenta = numeroMaximoAccionesVenta;
//	}
//
//	public int getNumeroMaximoAccionesVenta() {
//		return numeroMaximoAccionesVenta;
//	}
//
//	public void setPorcentajeMaximoCompra(double porcentajeMaximoCompra) {
//		this.porcentajeMaximoCompra = porcentajeMaximoCompra;
//	}
//
//	public double getPorcentajeMaximoCompra() {
//		return porcentajeMaximoCompra;
//	}
//
//	public void setPorcentajeMaximoVenta(double porcentajeMaximoVenta) {
//		this.porcentajeMaximoVenta = porcentajeMaximoVenta;
//	}
//
//	public double getPorcentajeMaximoVenta() {
//		return porcentajeMaximoVenta;
//	}
//
//	public void setPorcentajeMinimoCompra(double porcentajeMinimoCompra) {
//		this.porcentajeMinimoCompra = porcentajeMinimoCompra;
//	}
//
//	public double getPorcentajeMinimoCompra() {
//		return porcentajeMinimoCompra;
//	}
//
//	public void setPorcentajeMinimoVenta(double porcentajeMinimoVenta) {
//		this.porcentajeMinimoVenta = porcentajeMinimoVenta;
//	}
//
//	public double getPorcentajeMinimoVenta() {
//		return porcentajeMinimoVenta;
//	}
//
//	public double getPorcentajeSubidaPrecio() {
//		return porcentajeSubidaPrecio;
//	}
//	
//	public void setPorcentajeSubidaPrecio(double porcentaje) {
//		this.porcentajeSubidaPrecio = porcentaje;
//	}
//	
//	public double getPorcentajeBajadaPrecio() {
//		return porcentajeBajadaPrecio;
//	}
//	
//	public void setPorcentajeBajadaPrecio(double porcentaje) {
//		this.porcentajeBajadaPrecio = porcentaje;
//	}
//
//	public void setNumeroMinimoAccionesCompra(int numeroMinimoAccionesCompra) {
//		this.numeroMinimoAccionesCompra = numeroMinimoAccionesCompra;
//	}
//
//	public int getNumeroMinimoAccionesCompra() {
//		return numeroMinimoAccionesCompra;
//	}
//
//	public void setNumeroMinimoAccionesVenta(int numeroMinimoAccionesVenta) {
//		this.numeroMinimoAccionesVenta = numeroMinimoAccionesVenta;
//	}
//
//	public int getNumeroMinimoAccionesVenta() {
//		return numeroMinimoAccionesVenta;
//	}
//
//	public int getNumeroMaximoCancelaciones() {
//		return numeroMaximoCancelaciones;
//	}
//	
//	public void setNumeroMaximoCancelaciones(int numCancelaciones) {
//		this.numeroMaximoCancelaciones = numCancelaciones;
//	}
}
