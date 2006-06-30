/**
 *  Operacion.java This file was auto-generated from WSDL by the Apache Axis
 *  1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Operacion implements java.io.Serializable {

	private int cantidad;

	private java.lang.String empresa;

	private java.lang.String idEmisor;

	private double precio;

	private int tipoOp;

	private boolean valido;

	private java.lang.Object __equalsCalc = null;

	private boolean __hashCodeCalc = false;
	/**
	 *  Description of the Field
	 */
	public static final int COMPRA = 1;
	/**
	 *  Description of the Field
	 */
	public static final int VENTA = 2;

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc =
			new org.apache.axis.description.TypeDesc(Operacion.class, true);


	/**
	 *  Constructor for the Operacion object
	 */
	public Operacion() {
		this.valido = false;
	}


	/**
	 *  Constructor for the Operacion object
	 *
	 *@param  idEmisor  Description of Parameter
	 *@param  tipoOp    Description of Parameter
	 *@param  cantidad  Description of Parameter
	 *@param  empresa   Description of Parameter
	 *@param  precio    Description of Parameter
	 */
	public Operacion(
			java.lang.String idEmisor,
			int tipoOp,
			int cantidad,
			java.lang.String empresa,
			double precio
			) {
		this.cantidad = cantidad;
		this.empresa = empresa;
		this.idEmisor = idEmisor;
		this.precio = precio;
		this.tipoOp = tipoOp;
		this.valido = true;
	}


	/**
	 *  Sets the cantidad value for this Operacion.
	 *
	 *@param  cantidad
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	/**
	 *  Sets the empresa value for this Operacion.
	 *
	 *@param  empresa
	 */
	public void setEmpresa(java.lang.String empresa) {
		this.empresa = empresa;
	}


	/**
	 *  Sets the idEmisor value for this Operacion.
	 *
	 *@param  idEmisor
	 */
	public void setIdEmisor(java.lang.String idEmisor) {
		this.idEmisor = idEmisor;
	}


	/**
	 *  Sets the precio value for this Operacion.
	 *
	 *@param  precio
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}


	/**
	 *  Sets the tipoOp value for this Operacion.
	 *
	 *@param  tipoOp
	 */
	public void setTipoOp(int tipoOp) {
		this.tipoOp = tipoOp;
	}


	/**
	 *  Sets the valido value for this Operacion.
	 *
	 *@param  valido
	 */
	public void setValido(boolean valido) {
		this.valido = valido;
	}


	/**
	 *  Gets the cantidad value for this Operacion.
	 *
	 *@return    cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}


	/**
	 *  Gets the empresa value for this Operacion.
	 *
	 *@return    empresa
	 */
	public java.lang.String getEmpresa() {
		return empresa;
	}


	/**
	 *  Gets the idEmisor value for this Operacion.
	 *
	 *@return    idEmisor
	 */
	public java.lang.String getIdEmisor() {
		return idEmisor;
	}


	/**
	 *  Gets the precio value for this Operacion.
	 *
	 *@return    precio
	 */
	public double getPrecio() {
		return precio;
	}


	/**
	 *  Gets the tipoOp value for this Operacion.
	 *
	 *@return    tipoOp
	 */
	public int getTipoOp() {
		return tipoOp;
	}


	/**
	 *  Gets the valido value for this Operacion.
	 *
	 *@return    valido
	 */
	public boolean isValido() {
		return valido;
	}


	/**
	 *  Compares this to the parameter.
	 *
	 *@param  obj  the reference object with which to compare.
	 *@return      <tt>true</tt> if this object is the same as the obj argument;
	 *      <tt>false</tt> otherwise.
	 */
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Operacion)) {
			return false;
		}
		Operacion other = (Operacion) obj;
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true &&
				this.cantidad == other.getCantidad() &&
				((this.empresa == null && other.getEmpresa() == null) ||
				(this.empresa != null &&
				this.empresa.equals(other.getEmpresa()))) &&
				((this.idEmisor == null && other.getIdEmisor() == null) ||
				(this.idEmisor != null &&
				this.idEmisor.equals(other.getIdEmisor()))) &&
				this.precio == other.getPrecio() &&
				this.tipoOp == other.getTipoOp() &&
				this.valido == other.isValido();
		__equalsCalc = null;
		return _equals;
	}


	/**
	 *  Computes a hash value for this object.
	 *
	 *@return    The hash value for this object.
	 */
	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		_hashCode += getCantidad();
		if (getEmpresa() != null) {
			_hashCode += getEmpresa().hashCode();
		}
		if (getIdEmisor() != null) {
			_hashCode += getIdEmisor().hashCode();
		}
		_hashCode += new Double(getPrecio()).hashCode();
		_hashCode += getTipoOp();
		_hashCode += (isValido() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		__hashCodeCalc = false;
		return _hashCode;
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		// COMPRA-22(Endesa, 100, 22.0)
		if (tipoOp == COMPRA) {
			buf.append("COMPRA-");
		}
		else {
			buf.append("VENTA-");
		}
		buf.append(idEmisor);
		buf.append("(");
		buf.append(empresa);
		buf.append(",");
		buf.append(cantidad);
		buf.append(",");
		buf.append(precio);
		buf.append(")");
		return buf.toString();
	}


	/**
	 *  Return type metadata object
	 *
	 *@return    The TypeDesc value
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}


	/**
	 *  Get Custom Serializer
	 *
	 *@param  mechType   Description of Parameter
	 *@param  _javaType  Description of Parameter
	 *@param  _xmlType   Description of Parameter
	 *@return            The Serializer value
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(
			java.lang.String mechType,
			java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return
				new org.apache.axis.encoding.ser.BeanSerializer(
				_javaType, _xmlType, typeDesc);
	}


	/**
	 *  Get Custom Deserializer
	 *
	 *@param  mechType   Description of Parameter
	 *@param  _javaType  Description of Parameter
	 *@param  _xmlType   Description of Parameter
	 *@return            The Deserializer value
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(
			java.lang.String mechType,
			java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return
				new org.apache.axis.encoding.ser.BeanDeserializer(
				_javaType, _xmlType, typeDesc);
	}

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "Operacion"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("cantidad");
		elemField.setXmlName(new javax.xml.namespace.QName("", "cantidad"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("empresa");
		elemField.setXmlName(new javax.xml.namespace.QName("", "empresa"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idEmisor");
		elemField.setXmlName(new javax.xml.namespace.QName("", "idEmisor"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("precio");
		elemField.setXmlName(new javax.xml.namespace.QName("", "precio"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("tipoOp");
		elemField.setXmlName(new javax.xml.namespace.QName("", "tipoOp"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("valido");
		elemField.setXmlName(new javax.xml.namespace.QName("", "valido"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}
}
